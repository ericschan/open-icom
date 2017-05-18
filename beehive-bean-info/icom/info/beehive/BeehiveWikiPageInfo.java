package icom.info.beehive;

import icom.info.WikiPageInfo;

public class BeehiveWikiPageInfo extends WikiPageInfo {
	
	static BeehiveWikiPageInfo singleton = new BeehiveWikiPageInfo();

	public static WikiPageInfo getInstance() {
		return singleton;
	}
	
	public enum Attributes {
		renderedData
	}
	
	{
		referencedObjects.add(Attributes.renderedData.name());
	}

	protected BeehiveWikiPageInfo() {
	}
	
	public int getClassOrdinal() {
		return WikiPageInfo.getInstance().getClassOrdinal();
	}
}
