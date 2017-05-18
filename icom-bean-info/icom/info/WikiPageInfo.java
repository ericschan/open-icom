package icom.info;

public class WikiPageInfo extends DocumentInfo {

	static WikiPageInfo singleton = new WikiPageInfo();
	
	public static WikiPageInfo getInstance() {
		return singleton;
	}
	
	public enum Attributes {
		contentDescriptor,
		renderedPage
	}
	
	{
		referencedObjects.add(Attributes.renderedPage.name());
	}

	protected WikiPageInfo() {
		
	}
	
	public int getClassOrdinal() {
		return IcomBeanEnumeration.WikiPage.ordinal();
	}
	
}
