package icom.info.beehive;

import icom.info.AccessorInfo;
import icom.info.GroupInfo;

public class BeehiveGroupInfo extends GroupInfo {

	static BeehiveGroupInfo singleton = new BeehiveGroupInfo();
	
	public static BeehiveGroupInfo getInstance() {
		return singleton;
	}
    
    public enum Attributes {
        teamSpace,
    }

    {
        referencedObjects.add(Attributes.teamSpace.name());
    }
	
	{
		creationPredecessorCollections.remove(AccessorInfo.Attributes.assignedGroups.name());
		creationPredecessorCollections.remove(AccessorInfo.Attributes.assignedRoles.name());
		creationPredecessorCollections.add(GroupInfo.Attributes.memberGroups.name());
		creationPredecessorCollections.add(GroupInfo.Attributes.memberActors.name());
	}
	
	{
		inverseOfOwnedProperties.remove(GroupInfo.Attributes.memberActors.name());
		inverseOfOwnedProperties.remove(GroupInfo.Attributes.memberGroups.name());
	}
	
	protected BeehiveGroupInfo() {
	}
	
	public int getClassOrdinal() {
		return GroupInfo.getInstance().getClassOrdinal();
	}
	
}
