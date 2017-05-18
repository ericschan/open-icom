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

import oracle.csi.Category;
import oracle.csi.CategoryHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Workspace;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.Control;
import oracle.csi.filters.CategorizedEntityListFilter;
import oracle.csi.filters.ListFilter;

public class CategorizedEntityPredicateFactory extends MarkedEntityPredicateFactory {
	
	{
		attributeTypeMapper.put("Category".toLowerCase(), Category.class);
		attributeTypeMapper.put("Workspace".toLowerCase(), Workspace.class);
	}
	
	static CategorizedEntityPredicateFactory singleton = new CategorizedEntityPredicateFactory();
	
	public static CategorizedEntityPredicateFactory getInstance() {
		return singleton;
	}
	
	public CategorizedEntityPredicateFactory() {
		controlClassName = "oracle.csi.controls.CategoryControl";
		factoryClassName = "oracle.csi.controls.CategoryFactory";
		listFilterCreateMethodName = "createCategorizedEntityListFilter";
		listMethodName = "listCategorizedEntities";
		listFilter = "oracle.csi.filters.CategorizedEntityListFilter";
	}
	
	public String resolveClassName() {
		return "Entity";
	}
	
	public Class<? extends Object>[] getMethodSignatureForListMethod(ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle category = context.getMarker();
		if (category == null) {
			throw new QLException("Not Supported");
		}
		Class<?>[] args = null;
		int listFilterArgPosition = 1;
		
		IdentifiableHandle workspace = context.getWorkspace();
		if (workspace == null) {
			args = new Class[2];
		} else {
			args = new Class[3];
			listFilterArgPosition = 2;
		}
		
		Class<?>[] categoryFaces = category.getClass().getInterfaces();
		for (Class<? extends Object> face : categoryFaces) {
			if (CategoryHandle.class.isAssignableFrom(face)) {
				args[0] = face;
				break;
			}
		}
		if (args[0] == null) {
			throw new QLException("Not Supported");
		}
		
		if (workspace != null) {
			Class<?>[] workspaceFaces = workspace.getClass().getInterfaces();
			for (Class<? extends Object> face : workspaceFaces) {
				if (WorkspaceHandle.class.isAssignableFrom(face)) {
					args[1] = WorkspaceHandle.class;
					break;
				}
			}
			if (args[1] == null) {
				throw new QLException("Not Supported");
			}
		}	
		
		args[listFilterArgPosition] = CategorizedEntityListFilter.class;
		
		return args;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle category = context.getMarker();
		if (category == null) {
			throw new QLException("Not Supported");
		}
		Object[] args = null;
		int listFilterArgPosition = 1;
		
		IdentifiableHandle workspace = context.getWorkspace();
		if (workspace == null) {
			args = new Object[2];
		} else {
			args = new Object[3];
			listFilterArgPosition = 2;
		}
		
		args[0] = category;
		if (workspace != null) {
			args[1] = workspace;
		}
		args[listFilterArgPosition] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
	}

	
	public boolean hasWorkspaceAsArgumentOfListMethod() {
		return true;
	}
	
	public boolean hasMarkerAsArgumentOfListMethod() {
		return true;
	}

}
