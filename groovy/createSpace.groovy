
import icom.Actor
import icom.Community
import icom.Space
import icom.Person
import icom.beehive.BeehiveOrganizationUser
import icom.jpa.rt.EntityManagerFactoryProvider

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

def listProjectSpaces(Actor actor) {
    Set<Space> accessibleSpaces = actor.getAccessibleSpaces();
    for (Space teamSpace : accessibleSpaces) {
        println teamSpace.getName();
    }
}

actor = session.getActor();

System.out.println("Listing accessible spaces")
listProjectSpaces(actor)

Space findProject(Session session, String projectName) {
    Actor actor = session.getActor();
    BeehiveOrganizationUser user = (BeehiveOrganizationUser) actor;
    Space project = Scopes.findSpace(user, projectName);
    return project;
}

input = new BufferedReader(new InputStreamReader(System.in))
System.out.print("Enter space name: ")
System.out.flush()
projectName = input.readLine()
if (projectName == null || projectName.length() == 0) {
    projectName = "Project Space for " + actor.getName();  
}

project = findProject(session, projectName)
if (project == null) {
    project = createProject(session, projectName)
}

System.out.print("Listing accessible spaces")
listProjectSpaces(actor)

System.exit(1)