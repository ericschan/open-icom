import icom.Actor
import icom.EntityAddress
import icom.Space
import icom.Person
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

def listUsers(Collection users) {
    Users.listUsers(users);
}

input = new BufferedReader(new InputStreamReader(System.in))

System.out.print("Enter user name pattern (or just hit enter for default pattern %): ")
System.out.flush()
def name
name = input.readLine()
if (name == null || name.length() == 0) {
    name = "%"
}

users = Users.findUsersLikeName(session.getManager(), name)
listUsers users

System.out.print("Enter family name pattern (or just hit enter for default pattern %): ")
System.out.flush()
def familyNamePattern
familyNamePattern = input.readLine()

System.out.print("Enter given name pattern (or just hit enter for default pattern %): ")
System.out.flush()
def givenNamePattern
givenNamePattern = input.readLine()

if (familyNamePattern != null && familyNamePattern.length() != 0 &&
    givenNamePattern != null && givenNamePattern.length() != 0) {
    users = Users.findUsersLikeFullName(session.getManager(), givenNamePattern, familyNamePattern)
    listUsers users
} else if ((familyNamePattern == null || familyNamePattern.length() == 0) &&
    givenNamePattern != null && givenNamePattern.length() != 0) {
    users = Users.findUsersLikeGivenName(session.getManager(), givenNamePattern)
    listUsers users
}  else if (familyNamePattern != null && familyNamePattern.length() != 0 &&
    (givenNamePattern == null || givenNamePattern.length() == 0)) {
    users = Users.findUsersLikeFamilyName(session.getManager(), familyNamePattern)
    listUsers users
}
    
System.out.print("Enter email address (or just hit enter for default address): ")
System.out.flush()
def address
address = input.readLine()

users = Users.findUsersByAddress(session.getManager(), address)
listUsers users

System.out.print("Enter email address (or just hit enter for default address): ")
System.out.flush()
address = input.readLine()

users = Users.findUsersByEntityAddress(session.getManager(), address)
listUsers users


System.exit(1)