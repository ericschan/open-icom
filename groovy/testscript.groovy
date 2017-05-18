

import icom.Actor
import icom.Community
import icom.Document
import icom.Entity
import icom.Folder
import icom.HeterogeneousFolder
import icom.Item
import icom.MimeConvertible
import icom.Space
import icom.SpaceItem
import icom.Tag
import icom.UnifiedMessage
import icom.UnifiedMessageParticipant
import icom.Person
import icom.VersionSeries
import icom.VersionTypeEnum
import icom.beehive.BeehiveOrganizationUser
import icom.jpa.rt.EntityManagerFactoryProvider

import java.io.Console
import java.io.File
import java.util.ArrayList
import java.util.Collection

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

UserCredential getCredential() {
    UserCredential credential = UserCredential.getCredential();
    return credential
}

// prompt user name and password
credential = getCredential()

UserCredential prompt() {
    UserCredential credential = new UserCredential();

    Console cons = System.console();
    if (cons != null) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter host name: ");
        System.out.flush();
        credential.hostName = input.readLine();
        System.out.println(credential.hostName);

        System.out.print("Enter user id: ");
        System.out.flush();
        credential.userName = input.readLine();
        System.out.println(credential.userName);

        System.out.print("Enter password: ");
        System.out.flush();
        credential.password = input.readLine().toCharArray();
        System.out.println("*********");
    }

    return credential;
}

// prompt user name and password
credential = prompt()

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

def listSpaceItems(Space space) {
    Collection<SpaceItem> spaceItems = space.getElements();
    for (SpaceItem item : spaceItems) {
        println item.getName();
    }
}

listSpaceItems session.getPersonalSpace()

//---------------------------------------------

session.changeExtent "INBOX"
subject = "Join my network on LinkedIn"
messages = Messages.findMessagesBySubject(session.getManager(), session.getExtent(), subject);
session.changeExtent ".."

def listItems(Collection items) {
    for (Entity item : items) {
        println item.getName();
    }
}

listItems messages

def tagEntities(Session session, ArrayList entities, String tagName) {
    Space space = session.getPersonalSpace();

    Tag tag = Tags.getTag(space, tagName);
    if (tag == null) {
        tag = Tags.createTag(space, tagName);
    }

    for (Entity entity : entities) {
        Tags.applyTag(tag, entity);
    }

    session.commit();
}

tagName = "LinkedIn Invite"
tagEntities session, messages, tagName

//---------------------------------------------

Space createProject(Session session, String projectName) {
    Space space = session.getPersonalSpace();
    Community community = space.getParent();
    Actor actor = session.getActor();

    Collection<Person> members = new Vector<Person>();
    members.add(actor);

    Space project = Projects.createProjectSpace(community, projectName, actor, members);
    session.commit();
    return project;
}

Space findProject(Session session, String projectName) {
    Actor actor = session.getActor();
    BeehiveOrganizationUser user = (BeehiveOrganizationUser) actor;
    Space project = Scopes.findSpace(user, projectName);
    return project;
}

// create a project space
projectName = "Project Space"
project = findProject(session, projectName)
if (project == null) {
    project = createProject(session, projectName)
}

Folder addProjectFolder(Session session, Space project, String folderName) {
    Folder documents = Extents.findFolder(project, "Documents");
    Item item = Extents.findItem(documents, folderName);
    if (item instanceof Folder) {
        return (Folder) item;
    } else {
        HeterogeneousFolder folder = Artifacts.createHeterogeneousFolder(documents, folderName);
        session.commit();
        return folder;
    }
}

folderName = "Project Library"
folder = addProjectFolder(session, project, folderName)

Document addDocument(Session session, HeterogeneousFolder folder, File file) {
    Document doc = Artifacts.createDocument(folder, file.getName(), file);
    session.commit();
    return doc;
}

docName = "OpenICOM Overview.pdf"

currentDir = System.getProperty("user.dir")
fullFilePath = currentDir + "/" + docName
file = new File(fullFilePath)
doc = addDocument(session, folder, file)

//---------------------------------------------

UnifiedMessage sendMessage(Session session, Collection<Actor> toActors, 
    String subject, File body, Collection<MimeConvertible> attachments)  {
    
    Actor fromActor = session.getActor(); 
    HeterogeneousFolder sentFolder = session.getSentFolder();
   
    Collection<UnifiedMessageParticipant> tos = new ArrayList<UnifiedMessageParticipant>();
    for (Actor toActor : toActors) {
        UnifiedMessageParticipant to = new UnifiedMessageParticipant(toActor);
        tos.add(to);
    }
    
    Collection<UnifiedMessageParticipant> ccs = null;

    Collection<UnifiedMessageParticipant> bccs = new ArrayList<UnifiedMessageParticipant>();
    UnifiedMessageParticipant bcc = new UnifiedMessageParticipant(fromActor);
    bccs.add(bcc);
    
    UnifiedMessage message = Messages.sendMessage(session, fromActor, tos, ccs, bccs, sentFolder, subject, body, attachments);
    return message;
}

currentDir = System.getProperty("user.dir")
fullFilePath = currentDir + "/" + "TestMessage.txt"
file = new File(fullFilePath)

subject = "A test message from OpenICOM groovy script"
toActors = new ArrayList<Actor>()
toActors.add(session.getActor())
sentMessage = sendMessage(session, toActors, subject, file, null)

sentFolder = session.getSentFolder();
messages = Messages.findMessagesBySubject(session.getManager(), sentFolder, subject);
doc = Artifacts.createDocument(file.getName(), file);

attachments = new ArrayList<MimeConvertible>();
attachments.add doc
for (MimeConvertible msg : messages) {
    attachments.add msg
}

subject = "A test message from OpenICOM groovy script"
toActors = new ArrayList<Actor>()
toActors.add(session.getActor())
sentMessage = sendMessage(session, toActors, subject, file, attachments)

// repeat
messages = Messages.findMessagesBySubject(session.getManager(), sentFolder, subject);
attachments = new ArrayList<MimeConvertible>();
attachments.add doc
for (MimeConvertible msg : messages) {
    attachments.add msg
}
sentMessage = sendMessage(session, toActors, subject, file, attachments)

session.commit()

//---------------------------------------------

projectName = "Project Space"
project = findProject(session, projectName)

folderName = "Project Library"

docName = "OpenICOM Overview.pdf"

documents = Extents.findFolder(project, "Documents")
projectLibrary = Extents.findFolder(documents, folderName)
def item
item = Extents.findItem(projectLibrary, docName)

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
    representativeCopy = version_0.getRepresentativeCopy()
    assert representativeCopy.getVersionType() == VersionTypeEnum.RepresentativeCopy
}

def versionSeries
versionSeries = representativeCopy.getVersionControlMetadata()

def checkedOutVersion_1
def privateWorkingCopy_1
if (! versionSeries.isVersionSeriesCheckedOut()) {
    checkedOutVersion_1 = Artifacts.checkout(session, representativeCopy)
    privateWorkingCopy_1 = checkedOutVersion_1.getVersionedOrPrivateWorkingCopy()
} else {
    version_0 = versionSeries.getLatestVersion()
    privateWorkingCopy_1 = versionSeries.getPrivateWorkingCopy()
    checkedOutVersion_1 = privateWorkingCopy_1.getVersionControlMetadata()
}

dt = new Date();
privateWorkingCopy_1.setDescription("Version checked in from OpenICOM JPA at " + dt)
checkedOutVersion_1.setCheckinComment("Check in from OpenICOM JPA at " + dt);

def checkedInVersion_1
checkedInVersion_1 = Artifacts.checkIn(session, privateWorkingCopy_1)
assert checkedInVersion_1 == checkedOutVersion_1

def versionedCopy_1
versionedCopy_1 = checkedInVersion_1.getVersionedOrPrivateWorkingCopy()

def latestVersionedCopy
latestVersionedCopy = versionSeries.getLatestVersionedCopy()
assert versionedCopy_1 == latestVersionedCopy

def checkedOutVersion_2
checkedOutVersion_2 = Artifacts.checkout(session, representativeCopy)

def privateWorkingCopy_2
privateWorkingCopy_2 = checkedOutVersion_2.getVersionedOrPrivateWorkingCopy()

dt = new Date()
privateWorkingCopy_2.setDescription("Version checked in from OpenICOM JPA at " + dt)
checkedOutVersion_2.setCheckinComment("Check in from OpenICOM JPA at " + dt);

def checkedInVersion_2
checkedInVersion_2 = Artifacts.checkIn(session, privateWorkingCopy_2)
assert checkedInVersion_2 == checkedOutVersion_2

def versionedCopy_2
versionedCopy_2 = checkedInVersion_2.getVersionedOrPrivateWorkingCopy()
assert versionedCopy_2 == versionSeries.getLatestVersionedCopy()

def checkedOutVersion_3
checkedOutVersion_3 = Artifacts.checkout(session, representativeCopy)

assert versionSeries.isVersionSeriesCheckedOut()

Artifacts.cancelCheckout session, representativeCopy

versionSeries = representativeCopy.getVersionControlMetadata()

assert ! versionSeries.isVersionSeriesCheckedOut()


