import icom.Community
import icom.Entity
import icom.IllegalAttributionException
import icom.Space
import icom.Tag
import icom.beehive.BeehiveEnterprise
import icom.beehive.BeehiveTag
import icom.beehive.BeehiveTagApplication
import icom.beehive.BeehiveTagApplicationType
import icom.beehive.BeehiveTagType


class Tags {

	static public Tag createTag(Space space, String tagName) {
		Date dt = new Date();
		BeehiveTag tag = new BeehiveTag(space, dt, dt);
		tag.setName(tagName);
		if (tagName.indexOf("Shared") >= 0) {
			tag.setType(BeehiveTagType.SHARED);
		} else {
			tag.setType(BeehiveTagType.USER_DEFINED);
		}
		tag.setDescription("Tag created from OpenICOM JPA");
		
		return tag;
	}
	
	static public Tag getTag(Space space, String tagName) {
		BeehiveEnterprise enterprise = null;
		Community community = space.getParent();
		while (! (community instanceof BeehiveEnterprise)) {
			community = community.getParent();
		}
		enterprise = (BeehiveEnterprise) community;
		Set<Tag> tags = enterprise.getAvailableTags();
		for (Tag tag : tags) {
			if (tag.getName().equals(tagName)) {
				return tag;
			}
		}
		return null;
	}
	
	static public void applyTag(Tag tag, Entity entity) {
		try {
			BeehiveTagApplication tagApp = new BeehiveTagApplication(tag, entity);
			tagApp.setType(BeehiveTagApplicationType.PUBLIC);
		} catch (IllegalAttributionException ex) {
			ex.printStackTrace();
		}
	}

}
