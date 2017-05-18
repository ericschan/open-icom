package icom.beehive;

import icom.Group;
import icom.Id;
import icom.Scope;
import icom.Space;

import java.util.Date;

import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveGroup", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveGroup extends Group {
    
    /**
     * @$clientQualifier teamSpace
     * @associates Space
     */
    @OneToOne
    Space teamSpace;

	private static final long serialVersionUID = 1L;

	BeehiveGroup() {
		super();
	}

	public BeehiveGroup(Id id, Scope parent, Date createdOn) {
		super(id, parent, createdOn);
	}
	
	public BeehiveGroup(Scope parent, Date createdOn) {
		super(parent, createdOn);
	}
		   
    public Space getTeamSpace() {
        return teamSpace;
    }

    public void setTeamSpace(Space teamSpace) {
        this.teamSpace = teamSpace;
    }
    
}
