
import icom.Entity
import icom.Space
import icom.Tag
import icom.jpa.rt.EntityManagerFactoryProvider

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

input = new BufferedReader(new InputStreamReader(System.in))
System.out.print("Enter subject: ")
System.out.flush()
subject = input.readLine()
if (subject == null || subject.length() == 0) {
    subject = "Join my network on LinkedIn"
}

space = session.getPersonalSpace()

System.out.print("Enter folder name: ")
System.out.flush()
folderName = input.readLine()
if (folderName == null || folderName.length() == 0) {
    folderName = "INBOX"
}

System.out.println("Locating message folder: " + folderName)
folder = Extents.findFolder(space, folderName)

System.out.println("Querying messages by subject: " + subject)
messages = Messages.findMessagesBySubject(session.getManager(), folder, subject)

def listItems(Collection items) {
    for (Entity item : items) {
        println "\"" + item.getName() + "\" delivered at: " + item.getDeliveredTime();
    }
}

System.out.println("Found the following message(s): ")
listItems messages

def tagEntities(Session session, ArrayList entities, String tagName) {
    Space space = session.getPersonalSpace();

    System.out.println("Locating the tag: " + tagName);
    Tag tag = Tags.getTag(space, tagName);
    if (tag == null) {
        System.out.println("Tag not found, creating the tag: " + tagName);
        tag = Tags.createTag(space, tagName);
    }

    System.out.println("Applying the tag");
    for (Entity entity : entities) {
        Tags.applyTag(tag, entity);
    }

    System.out.println("Commiting the tags");
    session.commit();
}

System.out.print("Enter tag name: ")
System.out.flush()
tagName = input.readLine()
if (tagName == null || tagName.length() == 0) {
    tagName = "LinkedIn Invite"
}

tagEntities session, messages, tagName

System.exit(1)