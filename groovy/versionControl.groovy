import java.io.FileOutputStream;
import java.io.IOException;

import javax.ejb.SessionContext;
import javax.persistence.PersistenceException;

import icom.Actor
import icom.Document
import icom.Folder
import icom.HeterogeneousFolder
import icom.Item
import icom.SimpleContent
import icom.Space
import icom.SpaceItem
import icom.VersionTypeEnum
import icom.beehive.BeehiveOrganizationUser
import icom.jpa.rt.EntityManagerFactoryProvider

import java.io.File
import java.io.InputStream

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

UserCredential getCredential() {
    UserCredential credential = UserCredential.getCredential();
    return credential
}

// prompt user name and password
credential = getCredential()

Session signon(UserCredential credential) {
    System.out.println("Signing on to : " + credential.hostName);
    System.out.println("User id : " + credential.userName);

    String emName = "bdkEntityManager";
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("VendorBeanUtil", "icom.beehive.BeehiveBeanUtil");
    map.put("DataAccessConnectorFactory", "icom.jpa.bdk.BdkConnectorFactory");
    map.put("Host", credential.hostName);
    map.put("Port", "443");
    map.put("Protocol", "https");

    EntityManagerFactoryProvider provider = new EntityManagerFactoryProvider();
    EntityManagerFactory factory = provider.createEntityManagerFactory(emName, map);
    EntityManager manager = factory.createEntityManager();
    Session session = new Session(manager);
    session.setupUserContext(credential.userName, credential.password);
    return session;
}

session = signon(credential)

def greetings(Session session) {
    Actor actor = session.getActor();
    String actorName = actor.getName();

    Space space = session.getPersonalSpace();
    String spaceName = space.getName();

    println "Hello $actorName"
    println "Welcome to $spaceName"
}

greetings session

def listProjectSpaces(Actor actor) {
    Set<Space> accessibleSpaces = actor.getAccessibleSpaces();
    for (Space teamSpace : accessibleSpaces) {
        println teamSpace.getName();
    }
}

Space findProject(Session session, String projectName) {
    Actor actor = session.getActor();
    BeehiveOrganizationUser user = (BeehiveOrganizationUser) actor;
    Space project = Scopes.findSpace(user, projectName);
    return project;
}

actor = session.getActor();

System.out.println()
System.out.println()

System.out.println("Listing accessible spaces")
listProjectSpaces(actor)

System.out.println()
System.out.println()

input = new BufferedReader(new InputStreamReader(System.in))
System.out.print("Enter space name: ")
System.out.flush()
projectName = input.readLine()
if (projectName == null || projectName.length() == 0) {
    projectName = "Project Space for " + actor.getName();
}

System.out.println("Locate space: " + projectName)
project = findProject(session, projectName)

def listSpaceItems(Space space) {
    Collection<SpaceItem> spaceItems = space.getElements();
    for (SpaceItem item : spaceItems) {
        println item.getName();
    }
}

System.out.println()
System.out.println()

System.out.println("Listing space items")
listSpaceItems project

System.out.println()
System.out.println()

def listFolderItems(Folder folder) {
    Collection<Item> items = folder.getElements();
    for (Item item : items) {
        println item.getName();
    }
}

Document addDocument(Session session, HeterogeneousFolder folder, File file) {
    Document doc = Artifacts.createDocument(folder, file.getName(), file);
    session.commit();
    return doc;
}

File saveDocumentToFile(Session session, Document doc, String fileName, boolean reloadContent) {
    File file = new File(fileName);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    
    session.manager.refresh(doc);
    
    StringBuffer out = new StringBuffer();
    SimpleContent content = (SimpleContent) doc.getContent();
    println "Reading document content"
    InputStream input = content.getInputStream();
    try {
        int len1 = 10240; // input.available();
        byte[] data = new byte[len1];
        int readDataLength = input.read(data);
        while (readDataLength > 0) {
            println "content length read is " + readDataLength;
            fileOutputStream.write(data, 0, readDataLength);
            readDataLength = input.read(data);
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        throw ex;
    } finally {
        input.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    return file;
}

def appendDataToFile(File file, String data) {
    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
    try {
        fileOutputStream.write(data.getBytes());
    } catch (IOException ex) {
        ex.printStackTrace();
        throw ex;
    } finally {
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}

def setFileInContent(Document doc, File file) {
    SimpleContent content = (SimpleContent) doc.getContent();
    content.setDataFile(file);
}

System.out.print("Enter folder name: ")
System.out.flush()
def folderName
folderName = input.readLine()
if (folderName == null || folderName.length() == 0) {
    folderName = "Documents"
}

def container = project

while (folderName != null && folderName.length() != 0) {
    System.out.println("Locate folder: " + folderName);
    container = Extents.findFolder(container, folderName);
    listFolderItems(container);
    System.out.println()
    System.out.println()
    System.out.print("Enter folder name: ")
    System.out.flush()
    folderName = input.readLine()
}

folder = container

System.out.print("Enter document name: ")
System.out.flush()
docName = input.readLine()
if (docName == null || docName.length() == 0) {
    docName = "TestDocument.txt"
}

def currentDir
currentDir = System.getProperty("user.dir")

def checkouts
checkouts = currentDir + "/checkouts"
checkoutsDir = new File(checkouts);
checkoutsDir.mkdir();

System.out.println("Locate document: " + docName);
def item
item = Extents.findItem(folder, docName)

if (item == null) {
    System.out.println("Document $docName not found in server");
    System.out.println("Will upload the document");
    fullFilePath = currentDir + "/" + docName
    file = new File(fullFilePath)
    if (! file.exists()) {
        System.out.print("creating the document");
        fullFilePath = checkouts + "/" + docName
        file = new File(fullFilePath)
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            dt = new Date()
            fileOutputStream.write("A test document created at $dt by OpenICOM Groovy script".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }
    item = addDocument(session, folder, file)
}

System.out.println()
System.out.println()

def representativeCopy
def version_0
type = ((Document)item).getVersionType()
if (type == VersionTypeEnum.RepresentativeCopy) {
    representativeCopy = item
} else if (type == VersionTypeEnum.NonVersionControlledCopy) {
    System.out.println("Begin version series"); 
    version_0 = Artifacts.beginVersionSeries(session, item)
    System.out.println("Get representative copy of a new version series");
    representativeCopy = version_0.getRepresentativeCopy()
    assert representativeCopy.getVersionType() == VersionTypeEnum.RepresentativeCopy
} else {
    version_0 = ((Document)item).getVersionControlMetadata()
    System.out.println("Get representative copy of a current versioned copy");
    representativeCopy = version_0.getRepresentativeCopy()
    assert representativeCopy.getVersionType() == VersionTypeEnum.RepresentativeCopy
}

fullFilePath = checkouts + "/" + "representativeCopy_0.txt"
saveDocumentToFile(session, representativeCopy, fullFilePath, false)

System.out.println("Get version metadata");
def versionSeries
versionSeries = representativeCopy.getVersionControlMetadata()

def checkedOutVersion_1
def privateWorkingCopy_1
if (! versionSeries.isVersionSeriesCheckedOut()) {
    System.out.println("Check out 1");
    checkedOutVersion_1 = Artifacts.checkout(session, representativeCopy)
    System.out.println("Get private working copy 1");
    privateWorkingCopy_1 = checkedOutVersion_1.getVersionedOrPrivateWorkingCopy()
} else {
    version_0 = versionSeries.getLatestVersion()
    privateWorkingCopy_1 = versionSeries.getPrivateWorkingCopy()
    checkedOutVersion_1 = privateWorkingCopy_1.getVersionControlMetadata()
}

dt = new Date()
fullFilePath = checkouts + "/" + "privateWorkingCopy_1.txt"
pwc_1_File = saveDocumentToFile(session, privateWorkingCopy_1, fullFilePath, false)
appendDataToFile(pwc_1_File, "\nA new line appended at $dt")
setFileInContent(privateWorkingCopy_1, pwc_1_File)
privateWorkingCopy_1.setDescription("Version checked in from OpenICOM JPA at " + dt)
checkedOutVersion_1.setCheckinComment("Check in from OpenICOM JPA at " + dt);

System.out.println("Check in 1")
def checkedInVersion_1
checkedInVersion_1 = Artifacts.checkIn(session, privateWorkingCopy_1)
assert checkedInVersion_1 == checkedOutVersion_1

System.out.println("Get versiond copy 1")
def versionedCopy_1
versionedCopy_1 = checkedInVersion_1.getVersionedOrPrivateWorkingCopy()

def latestVersionedCopy
latestVersionedCopy = versionSeries.getLatestVersionedCopy()
assert versionedCopy_1 == latestVersionedCopy

fullFilePath = checkouts + "/" + "versionedCopy_1.txt"
saveDocumentToFile(session, versionedCopy_1, fullFilePath, false)

fullFilePath = checkouts + "/" + "representativeCopy_1.txt"
saveDocumentToFile(session, representativeCopy, fullFilePath, false)

System.out.println("Check out 2")
def checkedOutVersion_2
checkedOutVersion_2 = Artifacts.checkout(session, representativeCopy)

System.out.println("Get private working copy 2")
def privateWorkingCopy_2
privateWorkingCopy_2 = checkedOutVersion_2.getVersionedOrPrivateWorkingCopy()

dt = new Date()
fullFilePath = checkouts + "/" + "privateWorkingCopy_2.txt"
pwc_2_File = saveDocumentToFile(session, privateWorkingCopy_2, fullFilePath, false)
appendDataToFile(pwc_2_File, "\nA new line appended at $dt")
setFileInContent(privateWorkingCopy_2, pwc_2_File)
privateWorkingCopy_2.setDescription("Version checked in from OpenICOM JPA at " + dt)
checkedOutVersion_2.setCheckinComment("Check in from OpenICOM JPA at " + dt);

System.out.println("Check in 2")
def checkedInVersion_2
checkedInVersion_2 = Artifacts.checkIn(session, privateWorkingCopy_2)
assert checkedInVersion_2 == checkedOutVersion_2

System.out.println("Get versiond copy 2")
def versionedCopy_2
versionedCopy_2 = checkedInVersion_2.getVersionedOrPrivateWorkingCopy()
assert versionedCopy_2 == versionSeries.getLatestVersionedCopy()

fullFilePath = checkouts + "/" + "versionedCopy_2.txt"
saveDocumentToFile(session, versionedCopy_2, fullFilePath, false)

System.out.println("Check out 3");
def checkedOutVersion_3
checkedOutVersion_3 = Artifacts.checkout(session, representativeCopy)

assert versionSeries.isVersionSeriesCheckedOut()

System.out.println("Cancel checkout")
Artifacts.cancelCheckout session, representativeCopy

versionSeries = representativeCopy.getVersionControlMetadata()

assert ! versionSeries.isVersionSeriesCheckedOut()

fullFilePath = checkouts + "/" + "representativeCopy_2.txt"
saveDocumentToFile(session, representativeCopy, fullFilePath, false)

System.exit(1)
