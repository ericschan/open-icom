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
package icom.jpa.bdk.dao;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.PersonalWorkspace;
import com.oracle.beehive.User;
import com.oracle.beehive.UserUpdater;
import com.oracle.beehive.Workspace;

import icom.info.PersonInfo;
import icom.info.PersonalInfo;
import icom.info.beehive.BeehiveOrganizationUserInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;


public abstract class UserDAO extends ActorDAO {
	
	{
		basicAttributes.add(PersonalInfo.Attributes.givenName);
		basicAttributes.add(PersonalInfo.Attributes.middleName);
		basicAttributes.add(PersonalInfo.Attributes.familyName);
		basicAttributes.add(PersonalInfo.Attributes.prefix);
		basicAttributes.add(PersonalInfo.Attributes.suffix);
		basicAttributes.add(PersonalInfo.Attributes.nicknames);
		basicAttributes.add(PersonalInfo.Attributes.company);
		basicAttributes.add(PersonalInfo.Attributes.department);
		basicAttributes.add(PersonalInfo.Attributes.profession);
		basicAttributes.add(PersonalInfo.Attributes.jobTitle);
		basicAttributes.add(PersonalInfo.Attributes.officeLocation);
		basicAttributes.add(PersonalInfo.Attributes.timeZone);
	}
	
	{
		fullAttributes.add(PersonInfo.Attributes.personalSpace);
		fullAttributes.add(BeehiveOrganizationUserInfo.Attributes.accessibleSpaces);
		fullAttributes.add(BeehiveOrganizationUserInfo.Attributes.favoriteSpaces);
	}
	
	protected UserDAO() {
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        User bdkUser = (User)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(PersonalInfo.Attributes.givenName.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.givenName.name(),
                                     bdkUser.getGivenName());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.middleName.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.middleName.name(),
                                     bdkUser.getMiddleName());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.familyName.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name(),
                                     bdkUser.getFamilyName());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.prefix.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.prefix.name(), bdkUser.getPrefix());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.suffix.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.suffix.name(), bdkUser.getSuffix());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.nicknames.name(), lastLoadedProjection, proj)) {
            try {
                String nickname = bdkUser.getNickname();
                Collection<String> nicknames = new Vector<String>(1);
                if (nickname != null) {
                    nicknames.add(nickname);
                }
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.nicknames.name(), nicknames);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.jobTitle.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.jobTitle.name(), bdkUser.getJobTitle());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.department.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.department.name(),
                                     bdkUser.getDepartment());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.officeLocation.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.officeLocation.name(),
                                     bdkUser.getOfficeLocation());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.company.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.company.name(), bdkUser.getCompany());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.profession.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.profession.name(),
                                     bdkUser.getProfession());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonalInfo.Attributes.timeZone.name(), lastLoadedProjection, proj)) {
            try {
                // not supported
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(PersonInfo.Attributes.personalSpace.name(), lastLoadedProjection, proj)) {
            try {
                PersonalWorkspace bdkPersonalWorkspace = bdkUser.getPersonalWorkspace();
                marshallAssignEntity(obj, PersonInfo.Attributes.personalSpace.name(), bdkPersonalWorkspace);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.accessibleSpaces.name(), lastLoadedProjection,
                                 proj)) {
            try {
                List<Workspace> awSet = bdkUser.getAccessibleWorkspaces();
                marshallMergeAssignEntities(obj, BeehiveOrganizationUserInfo.Attributes.accessibleSpaces.name(), awSet,
                                            HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.favoriteSpaces.name(), lastLoadedProjection,
                                 proj)) {
            try {
                // not supported
            } catch (Exception ex) {
                // ignore
            }
        }

    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		UserUpdater updater = (UserUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		if (isChanged(obj, PersonalInfo.Attributes.givenName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.givenName.name());
			updater.setGivenName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.middleName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.middleName.name());
			updater.setMiddleName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.familyName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name());
			updater.setFamilyName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.nicknames.name())) {
			Collection<Object> nicknames = (Collection<Object>) getObjectCollection(pojoIdentifiable, PersonalInfo.Attributes.nicknames.name());
			if (nicknames != null) {
				updater.setNickname((String) nicknames.iterator().next());
			} else {
				updater.setNickname((String) null);
			}
		}
		if (isChanged(obj, PersonalInfo.Attributes.prefix.name())) {
			String prefix = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.prefix.name());
			updater.setPrefix(prefix);
		}
		if (isChanged(obj, PersonalInfo.Attributes.suffix.name())) {
			String suffix = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.suffix.name());
			updater.setSuffix(suffix);
		}
		if (isChanged(obj, PersonalInfo.Attributes.company.name())) {
			String company = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.company.name());
			updater.setCompany(company);
		}
		if (isChanged(obj, PersonalInfo.Attributes.department.name())) {
			String department = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.department.name());
			updater.setDepartment(department);
		}
		if (isChanged(obj, PersonalInfo.Attributes.jobTitle.name())) {
			String jobTitle = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.jobTitle.name());
			updater.setJobTitle(jobTitle);
		}
		if (isChanged(obj, PersonalInfo.Attributes.officeLocation.name())) {
			String officeLocation = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.officeLocation.name());
			updater.setOfficeLocation(officeLocation);
		}
		if (isChanged(obj, PersonalInfo.Attributes.profession.name())) {
			String profession = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.profession.name());
			updater.setProfession(profession);
		}
		if (isChanged(obj, PersonalInfo.Attributes.timeZone.name())) {
			// not supported
		}
		if (isChanged(obj, PersonInfo.Attributes.personalSpace.name())) {
			Persistent personalWorkspace = (Persistent) getAttributeValue(pojoIdentifiable, PersonInfo.Attributes.personalSpace.name());
			if (personalWorkspace != null) {
				BeeId personalWorkspaceId = getBeeId(((ManagedIdentifiableProxy)personalWorkspace.getManagedObjectProxy()).getObjectId().toString());
				updater.setPersonalWorkspace(personalWorkspaceId);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

}
