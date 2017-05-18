package icom.jpa.dao;

import icom.jpa.Persistent;

import java.util.ArrayList;


public class AttributeChangeSet {
    
    public ArrayList<Persistent> modifiedPojoObjects = new ArrayList<Persistent>();
    public ArrayList<Persistent> addedPojoObjects = new ArrayList<Persistent>();
    public ArrayList<Persistent> removedPojoObjects = new ArrayList<Persistent>();
    
    public AttributeChangeSet() {
        super();
    }
    
}
