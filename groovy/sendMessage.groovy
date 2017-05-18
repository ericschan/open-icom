import icom.Person;


import icom.Actor
import icom.HeterogeneousFolder
import icom.Message
import icom.MimeConvertible
import icom.Space
import icom.UnifiedMessage
import icom.UnifiedMessageParticipant
import icom.jpa.rt.EntityManagerFactoryProvider

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

Session signon(UserCredential credential) {
    System.out.println("Siging on to : " + credential.hostName);
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
System.out.println("Sending a message for subject: " + subject)
toActors = new ArrayList<Actor>()
def to
to = Users.selectUser(session.getManager())
System.out.println("Sending message to " + to.getPrimaryAddress().getAddress().toString());
toActors.add(to)
sentMessage = sendMessage(session, toActors, subject, file, null)
System.out.println("Message sent");

System.out.println("Querying sent messages for subject: " + subject)
sentFolder = session.getSentFolder();
messages = Messages.findMessagesBySubject(session.getManager(), sentFolder, subject)
System.out.println("Found the following messages:");
for (Message msg : messages) {
    System.out.println("\"" + msg.getSubject() + "\" delivered at " + msg.getDeliveredTime())
}

doc = Artifacts.createDocument(file.getName(), file);

attachments = new ArrayList<MimeConvertible>();
attachments.add doc
for (MimeConvertible msg : messages) {
    attachments.add msg
}

System.out.println("Sending another message with attachments for subject: " + subject)
subject = "A test message from OpenICOM groovy script"
sentMessage = sendMessage(session, toActors, subject, file, attachments)
System.out.println("Message sent");

// repeat
System.out.println("Querying new sent messages for subject: " + subject)
messages = Messages.findMessagesBySubject(session.getManager(), sentFolder, subject);
attachments = new ArrayList<MimeConvertible>();
attachments.add doc
for (MimeConvertible msg : messages) {
    attachments.add msg
}
System.out.println("Sending yet another message with attachments for subject: " + subject)
sentMessage = sendMessage(session, toActors, subject, file, attachments)
System.out.println("Message sent");

session.commit() // commit forwarded flags if set

System.exit(1)