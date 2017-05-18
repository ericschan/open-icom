import icom.Actor
import icom.Entity
import icom.Space
import icom.SpaceItem
import icom.jpa.rt.EntityManagerFactoryProvider

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

def listSpaceItems(Space space) {
    Collection<SpaceItem> spaceItems = space.getElements();
    for (SpaceItem item : spaceItems) {
        println item.getName();
    }
}

def listItems(Collection items) {
    for (Entity item : items) {
        println "\"" + item.getName() + "\" created at: " + item.getCreationDate()
    }
}

def listItems2(Collection items) {
    for (Entity item : items) {
        println "\"" + item.getName() + "\" last modified at: " + item.getLastModificationDate()
    }
}

def listItems3(Collection items) {
    for (Entity item : items) {
        println "\"" + item.getName() + "\" size: " + item.getSize()
    }
}

System.out.println("You have the following folders in your personal workspace")
System.out.println()

listSpaceItems session.getPersonalSpace()

System.out.println()
System.out.println()

input = new BufferedReader(new InputStreamReader(System.in))

System.out.print("Enter folder name (or just hit enter for default folder): ")
System.out.flush()
def folderName
folderName = input.readLine()
if (folderName == null || folderName.length() == 0) {
    folderName = "Documents"
}

System.out.println("Locate folder: " + folderName)
folder = Extents.findFolder(session.getPersonalSpace(), folderName)

System.out.print("Enter document name (or just hit enter for default document): ")
System.out.flush()
def name
name = input.readLine()
if (name == null || name.length() == 0) {
    name = "OpenICOM Overview.pdf"
}

System.out.println("Querying documents by name: $name")
documents = Documents.findDocumentsByName(session.getManager(), folder, name)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

name = "%.doc"
System.out.println("Querying documents like name: $name")
documents = Documents.findDocumentsLikeName(session.getManager(), folder, name)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

size = 10000000
System.out.println("Querying documents by size over: $size")
documents = Documents.findDocumentsBySizeOver(session.getManager(), folder, size)

System.out.println("Found the following document(s): ")
listItems3 documents

System.out.println()
System.out.println()

System.out.println("Querying documents by size below: $size")
documents = Documents.findDocumentsBySizeBelow(session.getManager(), folder, size)

System.out.println("Found the following document(s): ")
listItems3 documents

System.out.println()
System.out.println()

size1 = 10000
size2 = 20000
System.out.println("Querying documents by size between $size1 and $size2")
documents = Documents.findDocumentsBySizeRange(session.getManager(), folder, size1, size2)

System.out.println("Found the following document(s): ")
listItems3 documents

System.out.println()
System.out.println()

size = 44
System.out.println("Querying documents by size: $size")
documents = Documents.findDocumentsBySize(session.getManager(), folder, size)

System.out.println("Found the following document(s): ")
listItems3 documents

System.out.println()
System.out.println()

def actor = session.getActor()
System.out.println("Querying documents by actor: " + actor.getName())
documents = Documents.findDocumentsByActor(session.getManager(), folder, actor)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

dt = new Date()
calendar = new GregorianCalendar()
calendar.setTime dt
calendar.add(Calendar.DAY_OF_MONTH, -1)
calendar.set(Calendar.HOUR, 0);
calendar.set(Calendar.MINUTE, 0);
calendar.set(Calendar.SECOND, 0);
calendar.set(Calendar.MILLISECOND, 0);
after = calendar.getTime()
before = dt

System.out.println("Querying documents by creation dates between $after and $before")
documents = Documents.findDocumentsByCreationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

System.out.println("Querying documents by last modification dates between $after and $before")
documents = Documents.findDocumentsByLastModificationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems2 documents

System.out.println()
System.out.println()

before = after
calendar.set Calendar.MONTH, 0
calendar.set Calendar.DATE, 1
calendar.set Calendar.HOUR, 0
calendar.set Calendar.MINUTE, 0
calendar.set Calendar.SECOND, 0
calendar.set Calendar.MILLISECOND, 0
after = calendar.getTime()

System.out.println("Querying documents by creation dates between $after and $before")
documents = Documents.findDocumentsByCreationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

System.out.println("Querying documents by last modification dates between $after and $before")
documents = Documents.findDocumentsByLastModificationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems2 documents

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.YEAR, -1)
after = calendar.getTime()

System.out.println("Querying documents by creation dates between $after and $before")
documents = Documents.findDocumentsByCreationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

System.out.println("Querying documents by last modification dates between $after and $before")
documents = Documents.findDocumentsByLastModificationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems2 documents

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.YEAR, -1)
after = calendar.getTime()

System.out.println("Querying documents by creation dates between $after and $before")
documents = Documents.findDocumentsByCreationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

System.out.println("Querying documents by last modification dates between $after and $before")
documents = Documents.findDocumentsByLastModificationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems2 documents

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.YEAR, -1)
after = calendar.getTime()

System.out.println("Querying documents by creation dates between $after and $before")
documents = Documents.findDocumentsByCreationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems documents

System.out.println()
System.out.println()

System.out.println("Querying documents by last modification dates between $after and $before")
documents = Documents.findDocumentsByLastModificationDates(session.getManager(), folder, after, before)

System.out.println("Found the following document(s): ")
listItems2 documents

System.exit(1)