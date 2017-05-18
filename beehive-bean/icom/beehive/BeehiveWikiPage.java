package icom.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import icom.WikiPage;

@javax.persistence.Entity
@XmlType(name="BeehiveWikiPage", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveWikiPage extends WikiPage {
	
	private static final long serialVersionUID = 1L;

	Object renderedData;
	
	public Object getRenderedData() {
		return renderedData;
	}
	
}
