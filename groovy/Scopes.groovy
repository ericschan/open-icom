import icom.Community
import icom.Parental
import icom.Space
import icom.beehive.BeehiveOrganizationUser
import icom.beehive.BeehiveTeamSpace


class Scopes {

	static public Space createTeamSpace(Community organization, String name) {
		Date dt = new Date();
		BeehiveTeamSpace teamSpace = new BeehiveTeamSpace(organization, dt);
		teamSpace.setName(name);
		return teamSpace;
	}
	
	static public Space findSpace(Parental container, String name) {
		while (! (container instanceof Community)) {
			container = container.getParent();
		}
		Community community = (Community) container;
		Set<Space> spaces = community.getSpaces();
		for (Space aSpace : spaces) {
			if (aSpace.getName().equals(name)) {
				return aSpace;
			}
		}
		return null;
	}
	
	static public Space findSpace(BeehiveOrganizationUser user, String name) {
		Set<Space> accessibleSpaces = user.getAccessibleSpaces();
		Space project;
		for (Space teamSpace : accessibleSpaces) {
			if (teamSpace.getName() != null && teamSpace.getName().length() != 0) {
				if (name.equals(teamSpace.getName())) {
					project = teamSpace;
				}
			}
		}
		return project;
	}
	
}
