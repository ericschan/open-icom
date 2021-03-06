
import icom.Actor
import icom.Document;
import icom.HeterogeneousFolder;
import icom.Space
import icom.SpaceItem
import icom.beehive.BeehiveOrganizationUser
import icom.jpa.rt.EntityManagerFactoryProvider

import java.io.File;
import java.util.Collection

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

System.out.println("Listing accessible spaces")
listProjectSpaces(actor)

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

listSpaceItems project

System.out.print("Enter folder name: ")
System.out.flush()
folderName = input.readLine()
if (folderName == null || folderName.length() == 0) {
    folderName = "Documents"
}

System.out.println("Locate folder: " + folderName);
folder = Extents.findFolder(project, folderName)

Document addDocument(Session session, HeterogeneousFolder folder, File file) {
    Document doc = Artifacts.createDocument(folder, file.getName(), file);
    session.commit();
    return doc;
}

System.out.print("Enter document name: ")
System.out.flush()
docName = input.readLine()
if (docName == null || docName.length() == 0) {
    docName = "TestDocument.txt"
}

System.out.print("Uploading document: " + docName)
currentDir = System.getProperty("user.dir")
fullFilePath = currentDir + "/" + docName
file = new File(fullFilePath)
doc = addDocument(session, folder, file)

System.exit(1)