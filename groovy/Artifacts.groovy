import icom.Document
import icom.Extent
import icom.HeterogeneousFolder
import icom.SimpleContent
import icom.Version
import icom.VersionSeries
import icom.VersionTypeEnum
import icom.Versionable
import icom.beehive.BeehiveHeterogeneousFolder

import java.io.File

class Artifacts {

	static public HeterogeneousFolder createHeterogeneousFolder(Extent parent, String name) {
		Date dt = new Date();
		HeterogeneousFolder folder = new BeehiveHeterogeneousFolder(parent, dt, dt);
		folder.setName(name);
		folder.setDescription("A folder created from OpenICOM JPA");
		return folder;
	}
	
	static public Document createDocument(HeterogeneousFolder folder, String documentName, File file) throws IOException {
		Date dt = new Date();
		Document doc = new Document(folder, dt, dt);
		doc.setName(documentName);
		doc.setDescription("A document created from OpenICOM JPA");
		doc.setUserLastModificationDate(dt);

		SimpleContent content = new SimpleContent();
		content.setDataFile(file);
		content.setMediaType("text/html");
		content.setContentLanguage(Locale.getDefault());
		content.setCharacterEncoding("UTF-8");
		content.setContentEncoding("8bit");

		doc.setContent(content);
		return doc;
	}
    
    static public Document createDocument(String documentName, File file) throws IOException {
        Date dt = new Date();
        Document doc = new Document(dt, dt);
        doc.setName(documentName);
        doc.setDescription("A document created from OpenICOM JPA");
        doc.setUserLastModificationDate(dt);

        SimpleContent content = new SimpleContent();
        content.setDataFile(file);
        content.setMediaType("text/html");
        content.setContentLanguage(Locale.getDefault());
        content.setCharacterEncoding("UTF-8");
        content.setContentEncoding("8bit");

        doc.setContent(content);
        return doc;
    }
    
    static public Version beginVersionSeries(Session session, Document representativeCopy) {
        try {
            VersionTypeEnum type = representativeCopy.getVersionType();
            if (type == VersionTypeEnum.NonVersionControlledCopy) {
                Version version = (Version) session.checkout(representativeCopy, "Start a new version series from OpenICOM JPA");
                if (version == null) {
                    VersionSeries versionSeries = representativeCopy.getVersionControlMetadata();
                    Versionable privateWorkingCopy = versionSeries.getPrivateWorkingCopy();
                    if (privateWorkingCopy != null) {
                        version = privateWorkingCopy.getVersionControlMetadata();
                    }
                }
                return version;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	static public Version checkout(Session session, Document representativeCopy) {
        try {
            VersionTypeEnum type = representativeCopy.getVersionType();
            if (type == VersionTypeEnum.RepresentativeCopy) {
        		Version version = (Version) session.checkout(representativeCopy, "Check out from OpenICOM JPA");
        		if (version == null) {
                    VersionSeries versionSeries = representativeCopy.getVersionControlMetadata();
                    Versionable privateWorkingCopy = versionSeries.getPrivateWorkingCopy();
                    if (privateWorkingCopy != null) {
        				version = privateWorkingCopy.getVersionControlMetadata();
        			}
        		}
                return version;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}
    
    static public Version checkIn(Session session, Document privateWorkingCopy) {
        try {
            VersionTypeEnum type = privateWorkingCopy.getVersionType();
            if (type == VersionTypeEnum.PrivateWorkingCopy) {
                session.flush(privateWorkingCopy);  
                String versionName;
                Version pwcVersion = (Version) privateWorkingCopy.getVersionControlMetadata();
                if (pwcVersion != null) {
                    Date dt = new Date();
                    pwcVersion.setCheckinComment("Check in from OpenICOM JPA at " + dt);
                    versionName = pwcVersion.getName();
                }
                if (versionName == null || versionName.equals("Working copy")) {
                    versionName = "Version name assigned from OpenICOM JPA";
                }
                Version version = session.checkin(privateWorkingCopy, versionName);
                return version;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    static public void cancelCheckout(Session session, Versionable representativeCopy) {
        try {
            session.cancelCheckout(representativeCopy);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}