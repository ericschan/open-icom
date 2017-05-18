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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

import oracle.csi.Entity;
import oracle.csi.controls.Control;
import oracle.csi.controls.ControlLocator;
import oracle.csi.filters.EntityListFilter;
import oracle.csi.filters.Predicate;
import oracle.csi.filters.SortCriteria;

public class ListFilterHelper {
	
	static ListFilterHelper singleton = new ListFilterHelper();
	
	static Hashtable<String, Object> csiFactories = new Hashtable<String, Object>();
	static Hashtable<String, Class<? extends Object>> csiListFilterClasses = new Hashtable<String, Class<? extends Object>>();
		
	public static ListFilterHelper getInstance() {
		return singleton;
	}
	
	public Control getControl(String controlClassName) {
		try {
			Class<? extends Control> controlClass = 
			  (Class<? extends Control>) Class.forName(controlClassName, 
			                                           true, 
			                                           Thread.currentThread().getContextClassLoader());
			try {
				Control control = ControlLocator.getInstance().getControl(controlClass);
				return control;
			} catch (IllegalStateException ex) {
				throw new QLException("Cannot locate control class " + controlClassName, ex);
			}
		} catch (ClassNotFoundException ex) {
			throw new QLException("Cannot locate control class " + controlClassName, ex);
		} catch (Throwable ex) {
			throw new QLException("Cannot locate control class " + controlClassName, ex);
		}
	}
	
	public Object getFactory(String factoryClassName) {
		Object factory = csiFactories.get(factoryClassName);
		if (factory != null) {
			return factory;
		}
		try {
			Class<? extends Object> factoryClass = (Class<? extends Object>) Class.forName(factoryClassName);
			Method method = null;
			try {
				Class<?>[] args = new Class[0];
				method = factoryClass.getMethod("getInstance", args);
			} catch (NoSuchMethodException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
			try {
				Object[] args = new Object[0];
				factory = method.invoke(args);
				csiFactories.put(factoryClassName, factory);
				return factory;
			} catch (IllegalAccessException ex) {
				throw new QLException("illegal access exception of method", ex);
			} catch (IllegalArgumentException ex) {
				throw new QLException("illegal access exception of method", ex);
			} catch (InvocationTargetException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
		} catch (ClassNotFoundException ex) {
			throw new QLException("Cannot locate factory class " + factoryClassName, ex);
		} catch (Throwable ex) {
			throw new QLException("Cannot locate factory class " + factoryClassName, ex);
		}
	}
	
	public EntityListFilter<Entity> getListFilter(Object factory, String listFilterCreateMethodName) {
		try {
			Method method = null;
			try {
				Class<?>[] args = new Class[0];
				method = factory.getClass().getMethod(listFilterCreateMethodName, args);
			} catch (NoSuchMethodException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
			try {
				Object[] args = new Object[0];
				EntityListFilter<Entity> listFilter = (EntityListFilter<Entity>) method.invoke(factory, args);
				Class<? extends Object> listFilterClass = csiListFilterClasses.get(listFilterCreateMethodName);
				if (listFilterClass == null) {
					csiListFilterClasses.put(listFilterCreateMethodName, listFilter.getClass());
				}
				return listFilter;
			} catch (IllegalAccessException ex) {
				throw new QLException("illegal access exception of method", ex);
			} catch (IllegalArgumentException ex) {
				throw new QLException("illegal access exception of method", ex);
			} catch (InvocationTargetException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
		} catch (Throwable ex) {
			throw new QLException("Cannot create list filter with " + listFilterCreateMethodName, ex);
		}
	}
	
	public Method getListMethod(Control control, Class<?> args[], String listMethodName) {
		Method method = null;
		Class<?> cl = control.getClass();
		try {
			method = cl.getDeclaredMethod(listMethodName, args);
		} catch (NoSuchMethodException ex) {
			if (args.length == 2) {
				Class<?>[] myargs = new Class[2];
				Class<?>[] faces = args[0].getInterfaces(); // get the super class EntityHandle
				myargs[0] = faces[0];
				myargs[1] = args[1];
				if (myargs[0] == Object.class) {
					throw new QLException("illegal access exception of method", ex);
				} else {
					return getListMethod(control, myargs, listMethodName);
				}
			} else {
				throw new QLException("illegal access exception of method", ex);
			}
		} catch (SecurityException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		return method;
	}
	
	public Method getPredicateMethod(Class<? extends Object> listFilterClass, String methodName) {
		Method method = null;
		Class<? extends Object> cl = listFilterClass;
		boolean success = false;
		while (cl != Object.class) {
			try {
				Method[] methods = cl.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getName().equalsIgnoreCase(methodName)) {
						success = true;
						method = m;
						break;
					}
				}
			} catch (SecurityException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
			if (success) {
				break;
			}
			cl = cl.getSuperclass();
		}
		if (! success) {
			throw new QLException("failed to get the predicate: class=" + listFilterClass.getName() + ", methodName=" + methodName);
		}
		return method;
	}
	
	public Method getPredicateMethod(Object listFilter, String methodName) {
		Class<? extends Object> cl = listFilter.getClass();
		return getPredicateMethod(cl, methodName);
	}
	
	public Predicate createPredicate(Method method, Object listFilter, Object[] args) {
		try {
			Predicate predicate = (Predicate) method.invoke(listFilter, args);
			return predicate;
		} catch (IllegalAccessException ex) {
		  String methodEx = getMethodSignatureString(method, listFilter, args);
			throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (IllegalArgumentException ex) {
      String methodEx = getMethodSignatureString(method, listFilter, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (InvocationTargetException ex) {
      String methodEx = getMethodSignatureString(method, listFilter, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		}
	}
	
	public Method getSortCriteriaMethod(Class<? extends Object> listFilterClass, String methodName) {
		Method method = null;
		Class<? extends Object> cl = listFilterClass;
		boolean success = false;
		while (cl != Object.class) {
			try {
				Method[] methods = cl.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getName().equalsIgnoreCase(methodName)) {
						success = true;
						method = m;
						break;
					}
				}
			} catch (SecurityException ex) {
				throw new QLException("illegal access exception of method", ex);
			}
			if (success) {
				break;
			}
			cl = cl.getSuperclass();
		}
		if (! success) {
			throw new QLException("failed to get the sort criteria: class=" + listFilterClass.getName() + ", methodName=" + methodName);
		}
		return method;
	}
	
	public Method getSortCriteriaMethod(Object listFilter, String methodName) {
		Class<? extends Object> cl = listFilter.getClass();
		return getSortCriteriaMethod(cl, methodName);
	}
	
	public SortCriteria createSortCriteria(Method method, Object listFilter, Object[] args) {
		try {
			SortCriteria sortCriteria = (SortCriteria) method.invoke(listFilter, args);
			return sortCriteria;
		} catch (IllegalAccessException ex) {
      String methodEx = getMethodSignatureString(method, listFilter, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (IllegalArgumentException ex) {
      String methodEx = getMethodSignatureString(method, listFilter, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (InvocationTargetException ex) {
      String methodEx = getMethodSignatureString(method, listFilter, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		}
	}
	
	public List<Object> invokeListMethod(Control control, Method listMethod, Object[] args) {
		try {
			List<Object> list = (List<Object>) listMethod.invoke(control, args);
			return list;
		} catch (IllegalAccessException ex) {
      String methodEx = getControlSignatureString(control, listMethod, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (IllegalArgumentException ex) {
      String methodEx = getControlSignatureString(control, listMethod, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		} catch (InvocationTargetException ex) {
      String methodEx = getControlSignatureString(control, listMethod, args);
      throw new QLException("illegal access exception of method: " + methodEx, ex);
		}
	}
	
	private String getControlSignatureString(Control control, Method method, Object[] args) {
    StringBuilder sb = new StringBuilder();
    sb.append("control=");
    if (control == null) {
      sb.append("null");
    }
    else {
      sb.append(control.getClass().getName());
    }
    sb.append(" method=");
    if (method == null) {
      sb.append("null");
    }
    else {
      sb.append(method.getName());
    }
    return getSignatureStringInternal(sb, args);
  }

	private String getMethodSignatureString(Method method, Object listFilter, Object[] args) {
	  StringBuilder sb = new StringBuilder();
	  sb.append("method=");
	  if (method == null) {
	    sb.append("null");
	  }
	  else {
	    sb.append(method.getName());
	  }
	  sb.append(" listFilterClass=");
	  if (listFilter == null) {
	    sb.append("null");
	  }
	  else {
	    sb.append(listFilter.getClass().getName());
	  }
	    
	  return getSignatureStringInternal(sb, args);
	}
	
	private String getSignatureStringInternal(StringBuilder sb, Object[] args) {
    sb.append(" argsLength=");
    if (args == null) {
      sb.append("null");
    }
    else {
      sb.append(args.length);
      
      sb.append(" argTypes=");
      for (int i = 0; i < args.length; i++) {
        if (i > 0) sb.append("|");
        if (args[i] == null) {
          sb.append("null");
        }
        else {
          sb.append(args[i].getClass().getName());
        }
      }
    }
    
    return sb.toString();
  }
}
