import icom.Community
import icom.Group
import icom.Role
import icom.RoleDefinition
import icom.Space
import icom.Person
import icom.beehive.BeehiveGroup
import icom.beehive.BeehivePrivilegeEnum
import icom.beehive.BeehiveRole
import icom.beehive.BeehiveRoleDefinition
import icom.beehive.BeehiveTeamSpace


class Projects {

	static public Space createProjectSpace(Community community, String name, Person owner, Collection<Person> members) {
		Date dt = new Date();
		BeehiveTeamSpace space = new BeehiveTeamSpace(community, owner, dt);
		space.setName(name);
		space.setDescription("A project space created from OpenICOM JPA");
		space.setOwner(owner);

		RoleDefinition adminRoleDef = new BeehiveRoleDefinition(space, dt);
		adminRoleDef.setName("AdminRoleDefinition");
		adminRoleDef.setDescription("A role definition created from OpenICOM JPA");
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.WORKSPACE_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.FORUM_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.WIKI_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.ADDRESS_BOOK_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.CALENDAR_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.CONTENT_MGR);
		adminRoleDef.addPrivilege(BeehivePrivilegeEnum.CONF_MGR);
		adminRoleDef.setOwner(owner);

		RoleDefinition memberRoleDef = new BeehiveRoleDefinition(space, dt);
		memberRoleDef.setName("MemberRoleDefinition");
		memberRoleDef.setDescription("A role definition created from OpenICOM JPA");
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.WIKI_USER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.FORUM_USER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.FORUM_WRITER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.ADDRESS_BOOK_USER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.CALENDAR_USER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.CONTENT_USER);
		memberRoleDef.addPrivilege(BeehivePrivilegeEnum.CONF_USER);
		memberRoleDef.setOwner(owner);

		Group administrators = new BeehiveGroup(space, dt);
		administrators.setName("Coordinators");
		administrators.setDescription("A group created from OpenICOM JPA");
		administrators.setOwner(owner);
		owner.addAssignedGroup(administrators);

		Role adminRole = new BeehiveRole(space, adminRoleDef, dt);
		adminRole.setName("AdminRole");
		adminRole.setDescription("A role created from OpenICOM JPA");
		adminRole.setAssignedScope(space);
		adminRole.setOwner(owner);
		administrators.addAssignedRole(adminRole);

		Group participants = new BeehiveGroup(space, dt);
		participants.setName("Members");
		participants.setDescription("A group created from OpenICOM JPA");
		participants.setOwner(owner);
		owner.addAssignedGroup(participants);
		for (Person user : members) {
			user.addAssignedGroup(participants);
		}

		Role memberRole = new BeehiveRole(space, adminRoleDef, dt);
		memberRole.setName("MemberRole");
		memberRole.setDescription("A role created from OpenICOM JPA");
		memberRole.setAssignedScope(space);
		memberRole.setOwner(owner);
		participants.addAssignedRole(memberRole);

		return space;
	}

}
