

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

import icom.Community;
import icom.Container;
import icom.Extent;
import icom.Folder;
import icom.FolderContainer;
import icom.Item;
import icom.Scope;
import icom.Space;

public class Extents {
	
	static public Scope findScope(Community community, String scopeName) {
		Collection<Community> subCommunities = community.getCommunities();
		for (Community subCommunity : subCommunities) {
			if (subCommunity.getName() != null && subCommunity.getName().length() != 0) {
				if (scopeName.equals(subCommunity.getName())) {
					return(subCommunity);
				}
			}
		}
		Collection<Space> spaces = community.getSpaces();
		for (Space space : spaces) {
			if (space.getName() != null && space.getName().length() != 0) {
				if (scopeName.equals(space.getName())) {
					return(space);
				}
			}
		}
		return null;
	}
	
	static public Folder findFolder(FolderContainer folderContainer, String folderName) {
		Collection<? extends Item> items = folderContainer.getElements();
		for (Item item : items) {
			if (item.getName() != null && item.getName().length() != 0) {
				if (item instanceof Folder) {
					if (folderName.equals(item.getName())) {
						return((Folder) item);
					}
				}
			}
		}
		return null;
	}
	
	static public Item findItem(Container container, String itemName) {
		Collection<? extends Item> items = container.getElements();
		for (Item item : items) {
			if (item.getName() != null && item.getName().length() != 0) {
				if (itemName.equals(item.getName())) {
					return(item);
				}
			}
		}
		return null;
	}

	static public <T extends Item> Collection<T> getRecentArtifacts(Container container, int count, Class<T> clazz) {
		TreeSet<T> sorter = new TreeSet<T>(new ObjectsComparator<T>());
		Collection<? extends Item> items = container.getElements();
		for (Item item : items) {
        	if (clazz.isAssignableFrom(item.getClass())) {
    			sorter.add((T) item);
        	}
        }
        Collection<T> results = new ArrayList<T>();
        int i = 0;
        for (T item : sorter) {
        	if (++i > count) {
        		break;
        	}
        	results.add((T) item);
        }
        return results;
	}

}


class ObjectsComparator<T extends Item> implements Comparator<T>  {

   	public ObjectsComparator() {
    }

    public int compare(T o1, T o2) {
        if (o1 == o2) return 0;
        Date d1 = o1.getLastModificationDate();
        Date d2 = o2.getLastModificationDate();
        if (d1 != d2) {
        	if (d1 == null) {
        		return 1;
        	} else if (d2 == null) {
        		return 0;
        	}
        	if (d1.before(d2)) {
        		return 1;
        	} else if (d1.after(d2)) {
        		return -1;
        	} else {
        		return 0;
        	}
        } else {
        	return 0;
        }
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

}
