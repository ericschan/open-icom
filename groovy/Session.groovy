

import icom.Actor
import icom.Community
import icom.Entity
import icom.Extent
import icom.Folder
import icom.FolderContainer
import icom.FreeBusy
import icom.HeterogeneousFolder
import icom.Id
import icom.Scope
import icom.Service
import icom.Space
import icom.UnifiedMessage
import icom.Person
import icom.Version
import icom.VersionSeries
import icom.VersionTypeEnum
import icom.Versionable
import icom.jpa.rt.LifeCycleManager
import icom.jpa.rt.PersistenceContext
import icom.jpa.rt.ServiceManager
import icom.jpa.rt.TransactionManager
import icom.jpa.rt.UserContext
import icom.jpa.rt.UserContextManager

import java.util.Date

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction


public class Session {
	
	EntityManager manager;
	UserContext userContext;
	Extent extent;
	
	public Session(EntityManager manager) {
		this.manager = manager;
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setupUserContext(String username, char[] password) {
		try {
			UserContextManager pc = (UserContextManager) manager.getDelegate();
			if (userContext == null) {
				userContext = pc.createUserContext(username, password);
				pc.attachUserContext(userContext);
			} else {
				pc.attachUserContext(userContext);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public Actor getActor() {
		Id id = new Id(userContext.getActorId().toString());
		Actor actor = manager.find(Actor.class, id);
		return actor;
	}
	
	public Space getPersonalSpace() {
		Id id = new Id(userContext.getActorId().toString());
		Actor actor = manager.find(Actor.class, id);
		Space space = ((Person)actor).getPersonalSpace();
		return space;
	}
	
	public Extent getExtent() {
		if (extent == null) {
			return getPersonalSpace();
		}
		return extent;
	}

	public void setExtent(Extent extent) {
		this.extent = extent;
	}
		
	public void changeExtent(String extentName) {
		if (extentName == null || extentName.length() == 0) {
			Space space = getPersonalSpace();
			setExtent(space);
		} else {
			if (extentName.equals(".")) {

			} else if (extentName.equals("..")) {
				Extent extent = getExtent();
				if (extent == null) {
					Space space = getPersonalSpace();
					setExtent(space);
				} else {
					Extent parent = (Extent) extent.getParent();
					if (parent != null) {
						setExtent(parent);
					}
				}
			} else {
				Extent extent = getExtent();
				if (extent instanceof Community) {
					Community community = (Community) extent;
					Scope scope = Extents.findScope(community, extentName);
					if (scope != null) {
						setExtent(scope);
					}
				} else if (extent instanceof FolderContainer) {
					FolderContainer folderContainer = (FolderContainer) extent;
					Folder folder = Extents.findFolder(folderContainer, extentName);
					if (folder != null) {
						setExtent(folder);
					}
				}
			}
		}
	}
    
    public HeterogeneousFolder getDocumentFolder() {
        Space space = getPersonalSpace();
        return Extents.findFolder(space, "Documents");
    }
    
    public HeterogeneousFolder getInbox() {
        Space space = getPersonalSpace();
        return Extents.findFolder(space, "INBOX");
    }
    
    public HeterogeneousFolder getDraftFolder() {
        Space space = getPersonalSpace();
        return Extents.findFolder(space, "Drafts");
    }
    
    public HeterogeneousFolder getSentFolder() {
        Space space = getPersonalSpace();
        return Extents.findFolder(space, "Sent Items");
    }
	
	public void commit() {
		try {
			TransactionManager tm = (TransactionManager) manager.getDelegate();
			EntityTransaction et = tm.getEntityTransaction();
			et.commit();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
    
    public void flush(Entity entity) {
        try {
            TransactionManager tm = (TransactionManager) manager.getDelegate();
            tm.flush(entity);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	public void prepareToDetach(Entity entity, boolean full) {
		LifeCycleManager lm = (LifeCycleManager) manager.getDelegate();
		lm.prepareToDetach(entity, full);
	}

	public void send(UnifiedMessage message, HeterogeneousFolder sentFolder) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
        PersistenceContext context = sm.getPersistenceContext();
		service.sendEmail(context, message, sentFolder);
	}
	
	public void sendDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		service.sendDispositionNotification(message);
	}
	
	public void sendNotReadDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		service.sendNotReadDispositionNotification(message);
	}

	public Version checkout(Versionable representativeCopyOfVersionable, String checkoutComments) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy
				|| representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.NonVersionControlledCopy)) {
			throw new IllegalArgumentException("Check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		return (Version) service.checkoutRepresentativeCopyOfVersionable(representativeCopyOfVersionable, checkoutComments);
	}
	
	public Version checkin(Versionable privateWorkingCopyOfVersionable, String versionName) {
		if (! (privateWorkingCopyOfVersionable.getVersionType() == VersionTypeEnum.PrivateWorkingCopy)) {
			throw new IllegalArgumentException("Checkin argument must be private working copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		return (Version) service.checkinPrivateWorkingCopyOfVersionable(privateWorkingCopyOfVersionable, versionName);
	}
	
	public void cancelCheckout(Versionable representativeCopyOfVersionable) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy)) {
			throw new IllegalArgumentException("Cancel check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		service.cancelCheckout(representativeCopyOfVersionable);
	}
	
	public FreeBusy loadFreeBusyOfActor(Actor actor, Date startDate, Date endDate) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		Service service = sm.getService();
		return (FreeBusy) service.loadFreeBusyOfActor(actor, startDate, endDate);
	}
	

}
