
import icom.Actor
import icom.Entity
import icom.Extent;
import icom.Participant;
import icom.PriorityEnum
import icom.Space
import icom.SpaceItem
import icom.UnifiedMessageChannelEnum
import icom.UnifiedMessageParticipant
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
        println "\"" + item.getName() + "\" delivered at: " + item.getDeliveredTime();
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
    folderName = "INBOX"
}

System.out.println("Locate folder: " + folderName)
folder = Extents.findFolder(session.getPersonalSpace(), folderName)

pattern = "Join my network%"
System.out.println("Querying messages subject like: $pattern")
messages = Messages.findMessagesLikeSubject(session.getManager(), folder, pattern)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

size = 10000000
System.out.println("Querying messages by size over: $size")
messages = Messages.findMessagesBySizeOver(session.getManager(), folder, size)

System.out.println("Found the following message(s): ")
listItems3 messages

System.out.println()
System.out.println()

System.out.println("Querying messages by size below: $size")
messages = Messages.findMessagesBySizeBelow(session.getManager(), folder, size)

System.out.println("Found the following message(s): ")
listItems3 messages

System.out.println()
System.out.println()

size1 = 10000
size2 = 20000
System.out.println("Querying messages by size between $size1 and $size2")
messages = Messages.findMessagesBySizeRange(session.getManager(), folder, size1, size2)

System.out.println("Found the following message(s): ")
listItems3 messages

System.out.println()
System.out.println()

size = 10000000
System.out.println("Querying messages by size: $size")
messages = Messages.findMessagesBySize(session.getManager(), folder, size)

System.out.println("Found the following message(s): ")
listItems3 messages

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

System.out.println("Querying messages by delivered times since $after and before $before inclusive")
messages = Messages.findMessagesByDeliveredTimes(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -1)
after = calendar.getTime()

System.out.println("Querying messages by delivered times between $after and $before")
messages = Messages.findMessagesByDeliveredTimesBetween(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -1)
after = calendar.getTime()

System.out.println("Querying messages by delivered times since $after and before $before inclusive")
messages = Messages.findMessagesByDeliveredTimes(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -3)
after = calendar.getTime()

System.out.println("Querying messages by delivered times between $after and $before")
messages = Messages.findMessagesByDeliveredTimesBetween(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -3)
after = calendar.getTime()

System.out.println("Querying messages by delivered times since $after and before $before inclusive")
messages = Messages.findMessagesByDeliveredTimes(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -3)
after = calendar.getTime()

System.out.println("Querying messages by delivered times between $after and $before")
messages = Messages.findMessagesByDeliveredTimesBetween(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

before = after
calendar.add(Calendar.DAY_OF_MONTH, -3)
after = calendar.getTime()

System.out.println("Querying messages by delivered times since $after and before $before inclusive")
messages = Messages.findMessagesByDeliveredTimes(session.getManager(), folder, after, before)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

System.out.println("Querying messages by priority")
messages = Messages.findMessagesByPriority(session.getManager(), folder, PriorityEnum.High)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

System.out.println("Querying messages by voice channel")
messages = Messages.findMessagesByChannel(session.getManager(), folder, UnifiedMessageChannelEnum.Voice)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

System.out.println("Querying messages by notification channel")
messages = Messages.findMessagesByChannel(session.getManager(), folder, UnifiedMessageChannelEnum.Notification)

System.out.println("Found the following message(s): ")
listItems messages

System.out.println()
System.out.println()

System.out.print("Enter email address of sender: ")
System.out.flush()
def sender
sender = input.readLine()

System.out.println("Querying messages by a sender")
messages = Messages.findMessagesBySender(session.getManager(), folder, sender)
listItems messages

def user
user = Users.selectUser(session.getManager())
def participant
participant = new UnifiedMessageParticipant(user);

System.out.println("Querying messages by a sender")
messages = Messages.findMessagesBySender(session.getManager(), folder, participant)
listItems messages

System.out.println()
System.out.println()

System.out.println("Querying messages by a participant who is sender or receiver")
messages = Messages.findMessagesByParticipant(session.getManager(), folder, participant)
listItems messages

System.out.println()
System.out.println()

System.out.println("Querying messages by a receiver")
messages = Messages.findMessagesByReceiver(session.getManager(), folder, participant)
listItems messages

System.exit(1)