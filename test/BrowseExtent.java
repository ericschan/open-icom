/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER
 * 
 * Copyright (c) 2010, Oracle Corporation All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can obtain
 * a copy of the License at http://openjdk.java.net/legal/gplv2+ce.html.
 * See the License for the specific language governing permission and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at openICOM/bootstrap/legal/LICENSE.txt.
 * Oracle designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [ ] replaced by your own
 * identifying information:  "Portions Copyrighted [year]
 * [name of copyright owner].
 *
 * Contributor(s): Oracle Corporation
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

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

public class BrowseExtent {
	
	static public void changeExtent(Session session, String extentName) {
		if (extentName == null || extentName.length() == 0) {
			Space space = session.getPersonalSpace();
    		session.setExtent(space);
		} else {
			if (extentName.equals(".")) {

			} else if (extentName.equals("..")) {
				Extent extent = session.getExtent();
				if (extent == null) {
					Space space = session.getPersonalSpace();
		    		session.setExtent(space);
				} else {
					Extent parent = (Extent) extent.getParent();
					if (parent != null) {
						session.setExtent(parent);
					}
				}
			} else {
				Extent extent = session.getExtent();
				if (extent instanceof Community) {
					Community community = (Community) extent;
					Scope scope = findScope(community, extentName);
					if (scope != null) {
						session.setExtent(scope);
					}
				} else if (extent instanceof FolderContainer) {
					FolderContainer folderContainer = (FolderContainer) extent;
					Folder folder = findFolder(folderContainer, extentName);
					if (folder != null) {
						session.setExtent(folder);
					}
				}
			}
		}
	}
	
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
