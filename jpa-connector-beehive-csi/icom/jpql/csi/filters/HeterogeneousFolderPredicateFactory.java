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
package icom.jpql.csi.filters;

import icom.QLException;
import icom.jpql.csi.CsiQueryContext;

import java.lang.reflect.Method;
import java.util.List;

import oracle.csi.Artifact;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.Control;
import oracle.csi.filters.HeterogeneousFolderListFilter;
import oracle.csi.filters.ListFilter;

public class HeterogeneousFolderPredicateFactory extends FolderPredicateFactory {
	
	{
		attributeTypeMapper.put("Elements".toLowerCase(), Artifact.class);
	}
	
	{
		sortCriteriaMethodMapper.put("createdBy".toLowerCase(), "createCreatedBySortCriteria");
		sortCriteriaMethodMapper.put("lastModifiedBy".toLowerCase(), "createModifiedBySortCriteria");
		sortCriteriaMethodMapper.put("creationDate".toLowerCase(), "createCreatedOnSortCriteria");
	}

	static HeterogeneousFolderPredicateFactory singleton = new HeterogeneousFolderPredicateFactory();
	
	public static HeterogeneousFolderPredicateFactory getInstance() {
		return singleton;
	}
	
	public HeterogeneousFolderPredicateFactory() {
		controlClassName = "oracle.csi.controls.HeterogeneousFolderControl";
		factoryClassName = "oracle.csi.controls.HeterogeneousFolderFactory";
		listFilterCreateMethodName = "createHeterogeneousFolderListFilter";
		listMethodName = "listSubFolders";
		listFilter = "oracle.csi.filters.HeterogeneousFolderListFilter";
	}
	
	public String resolveClassName() {
		return "HeterogeneousFolder";
	}
	
	public Class<? extends Object>[] getMethodSignatureForListMethod(ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle container = context.getContainer();
		if (container == null) {
			throw new QLException("Not Supported");
		}
		
		Class<?>[] args = new Class[2];
		
		Class<?>[] containerFaces = container.getClass().getInterfaces();
		for (Class<? extends Object> face : containerFaces) {
			if (HeterogeneousFolderHandle.class.isAssignableFrom(face)) {
				args[0] = HeterogeneousFolderHandle.class;
				break;
			} else if (WorkspaceHandle.class.isAssignableFrom(face)) {
				args[0] = WorkspaceHandle.class;
				break;
			}
		}
		if (args[0] == null) {
			throw new QLException("Not Supported");
		}
		args[1] = HeterogeneousFolderListFilter.class;
		return args;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle container = context.getContainer();
		if (container == null) {
			throw new QLException("Not Supported");
		}
		Object[] args = new Object[2];
		args[0] = container;
		args[1] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
	}
	
	public boolean hasContainerAsArgumentOfListMethod() {
		return true;
	}
	
}
