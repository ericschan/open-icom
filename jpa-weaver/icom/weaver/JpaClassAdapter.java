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
package icom.weaver;

import java.beans.Introspector;
import java.util.HashMap;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JpaClassAdapter extends ClassAdapter implements Opcodes, Descriptors {
    
    static HashMap<String, PersistenceClassType> persistenceClassTypes = new HashMap<String, PersistenceClassType>();
    
    static {
        persistenceClassTypes.put(entityAnnotationDesc, PersistenceClassType.Entity);
        persistenceClassTypes.put(embeddableAnnotationDesc, PersistenceClassType.Embeddable);
        persistenceClassTypes.put(mappedSuperclassAnnotationDesc, PersistenceClassType.MappedSuperclass);
    }
    
    static HashMap<String, RelationshipMode> relationshipModes = new HashMap<String, RelationshipMode>();
    
    static {
        relationshipModes.put(manyToManyAnnotationDesc, RelationshipMode.ManyToMany);
        relationshipModes.put(manyToOneAnnotationDesc, RelationshipMode.ManyToOne);
        relationshipModes.put(oneToOneAnnotationDesc, RelationshipMode.OneToOne);
        relationshipModes.put(oneToManyAnnotationDesc, RelationshipMode.OneToMany);
    }
    
    static HashMap<String, CollectionType> collectionTypes = new HashMap<String, CollectionType>();
    
    static {
        collectionTypes.put(collectionDesc, CollectionType.Collection);
        collectionTypes.put(setDesc, CollectionType.Set);
        collectionTypes.put(listDesc, CollectionType.List);
        collectionTypes.put(mapDesc, CollectionType.Map);
    }
    
    static HashMap<String, String> persistenceClassTraits = new HashMap<String, String>();
    
    static {
        persistenceClassTraits.put(icomObjectId, jpaObjectId);
        persistenceClassTraits.put(icomVersionId, jpaVersionId);
        persistenceClassTraits.put(icomMimeConvertible, icomDependent);
        persistenceClassTraits.put(icomEntity, icomEntity + "Trait");
        persistenceClassTraits.put(icomParticipant, icomParticipant + "Trait");
        persistenceClassTraits.put(icomEntityAddress, icomEntityAddress + "Trait");
        persistenceClassTraits.put(icomContentStream, icomContentStream + "Trait");       
    }
    
    ClassDefinition classDefn;
    
    public JpaClassAdapter(ClassVisitor cv, String className) {
        super(cv);
        classDefn = new ClassDefinition();
        classDefn.className = className;
        classDefn.internalClassName = className.replace('.', '/');
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        classDefn.version = version;
        classDefn.access = access;
        classDefn.name = name;
        classDefn.signature = signature;
        classDefn.superName = superName;
        classDefn.interfaces = interfaces;
        if ( (access & ACC_INTERFACE) == 0) {
            // it is a class
            if (name.equals(icomEntity)) {
                if (superName == null || superName.equals("java/lang/Object")) {
                    superName = icomAbstractEntity;
                    classDefn.icomTopLevelEntityClass = true;
                }
            } else if (name.equals(icomContentStream)) {
                classDefn.icomContentStreamClass = true;
            } else {
                boolean isAnIdentifiable = false;
                for (String anInterface : interfaces) {
                    if (anInterface.equals(icomIndentifiable)) {
                        if (superName == null || superName.equals("java/lang/Object")) {
                            superName = icomAbstractIdentifiable;
                            classDefn.icomTopLevelIdentifiableClass = true;
                        }
                        isAnIdentifiable = true;
                    }
                }
                if (!isAnIdentifiable) {
                    if (superName == null || superName.equals("java/lang/Object")) {
                        superName = icomAbstractNonIdentifiable;
                        classDefn.icomTopLevelNonIdentifiableClass = true;
                    }
                    if (name.equals(icomParticipant)) {
                        classDefn.icomParticipantClass = true;
                    } else if (name.equals(icomObjectId)) {
                        classDefn.icomObjectIdClass = true;
                    } else if (name.equals(icomVersionId)) {
                        classDefn.icomVersionIdClass = true;
                    }
                }
            }
        } else {
            if (name.equals(icomMimeConvertible)) {
                classDefn.icomMimeConvertibleClass = true;
            } 
        }
        // add trait among interfaces, e.g. Entity implements EntityTrait
        String[] interfacesPlus = null;
        String trait = persistenceClassTraits.get(name);
        if (trait != null) {
            interfacesPlus = new String[interfaces.length + 1];
            for (int i = 0; i < interfaces.length; i++) {
                interfacesPlus[i] = interfaces[i];
            }
            interfacesPlus[interfaces.length] = trait;
        } else {
            interfacesPlus = interfaces;
        }
        super.visit(version, access, name, signature, superName, interfacesPlus);
    }
    
    public void visitSource(String file, String debug) {
        classDefn.file = file;
        classDefn.debug = debug;
        super.visitSource(file, debug);
    }
    
    public void visitOuterClass(String outerClassName, String enclosingMethodName, String enclosingMethodDescriptor) {
        classDefn.outerClassName = outerClassName;
        classDefn.enclosingMethodName = enclosingMethodName;
        classDefn.enclosingMethodDescriptor = enclosingMethodDescriptor;
        super.visitOuterClass(outerClassName, enclosingMethodName, enclosingMethodDescriptor);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor av = super.visitAnnotation(desc, visible);
        AnnotationVisitor jav = new JpaClassAnnotationAdapter(av);
        return jav;
    }

    public FieldVisitor visitField(int access, String name, String desc,
            String signature, Object value) {
        FieldVisitor fv = super.visitField(access, name, desc, signature, value);
        if ((access & (ACC_TRANSIENT | ACC_STATIC)) == 0) {
            FieldDefinition fieldDefn = new FieldDefinition();
            fieldDefn.fieldName = name;
            fieldDefn.fieldDesc = desc;
            classDefn.managedFields.put(name, fieldDefn);
            FieldVisitor jfv = new JpaFieldAdapter(fv, this, fieldDefn);
            return jfv;
        }
        return fv;
    }

    public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, 
            String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, methodName, methodDescriptor, signature, exceptions);
        if ( (access & ACC_ABSTRACT) != 0) {
            return mv;
        }
        if (classDefn.icomContentStreamClass) {
            return mv;
        } else if (classDefn.icomObjectIdClass) {
            return mv;
        } else if (classDefn.icomVersionIdClass) {
            return mv;
        } 
        
        if (methodName.startsWith("get")) {
            String fieldName = Introspector.decapitalize(methodName.substring(3));
            FieldDefinition fieldDefn = classDefn.managedFields.get(fieldName);
            if (fieldDefn != null) {
                if (fieldDefn.isId() || fieldDefn.isVersion()) {
                    return mv;
                }
                MethodVisitor jmv = new JpaGetterMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("is")) {
            String fieldName = Introspector.decapitalize(methodName.substring(2));
            FieldDefinition fieldDefn = classDefn.managedFields.get(fieldName);
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaGetterMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("set")) {
            String fieldName = Introspector.decapitalize(methodName.substring(3));
            FieldDefinition fieldDefn = classDefn.managedFields.get(fieldName);
            if (fieldDefn != null) {
                if (fieldDefn.isId() || fieldDefn.isVersion()) {
                    return mv;
                }
                MethodVisitor jmv = new JpaSetterMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("modify")) {
            String fieldName = Introspector.decapitalize(methodName.substring(6));
            String pluralFieldName = PropertyNameUtil.convertToPlural(fieldName);
            FieldDefinition fieldDefn = classDefn.managedFields.get(pluralFieldName);
            if (fieldDefn == null) {
                fieldDefn = classDefn.managedFields.get(fieldName);
            }
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaModifyMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("add")) {
            String fieldName = Introspector.decapitalize(methodName.substring(3));
            String pluralFieldName = PropertyNameUtil.convertToPlural(fieldName);
            FieldDefinition fieldDefn = classDefn.managedFields.get(pluralFieldName);
            if (fieldDefn == null) {
                fieldDefn = classDefn.managedFields.get(fieldName);
            }
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaAdderMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("attach")) {
            String fieldName = Introspector.decapitalize(methodName.substring(6));
            String pluralFieldName = PropertyNameUtil.convertToPlural(fieldName);
            FieldDefinition fieldDefn = classDefn.managedFields.get(pluralFieldName);
            if (fieldDefn == null) {
                fieldDefn = classDefn.managedFields.get(fieldName);
            }
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaAdderMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("remove")) {
            String fieldName = Introspector.decapitalize(methodName.substring(6));
            String pluralFieldName = PropertyNameUtil.convertToPlural(fieldName);
            FieldDefinition fieldDefn = classDefn.managedFields.get(pluralFieldName);
            if (fieldDefn == null) {
                fieldDefn = classDefn.managedFields.get(fieldName);
            }
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaRemoverMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.startsWith("detach")) {
            String fieldName = Introspector.decapitalize(methodName.substring(6));
            String pluralFieldName = PropertyNameUtil.convertToPlural(fieldName);
            FieldDefinition fieldDefn = classDefn.managedFields.get(pluralFieldName);
            if (fieldDefn == null) {
                fieldDefn = classDefn.managedFields.get(fieldName);
            }
            if (fieldDefn != null) {
                MethodVisitor jmv = new JpaRemoverMethodAdapter(mv, classDefn, fieldDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.equals("<init>")) {
            if (classDefn.icomTopLevelEntityClass || classDefn.icomTopLevelIdentifiableClass 
                    || classDefn.icomTopLevelNonIdentifiableClass) {
                MethodVisitor jmv = new JpaTopLevelConstructorMethodAdapter(mv, classDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (methodName.equals("delete")) {
            if (classDefn.icomTopLevelEntityClass) {
                MethodVisitor jmv = new JpaTopLevelDeleteMethodAdapter(mv, classDefn, methodName, methodDescriptor);
                return jmv;
            }
        } else if (! methodName.equals("clone")) {
            MethodVisitor jmv = new JpaMethodAdapter(mv, classDefn, methodName, methodDescriptor);
            return jmv;
        }
        
        return mv;
    }

    public void visitEnd() { 
        if (classDefn.icomTopLevelEntityClass || classDefn.icomTopLevelIdentifiableClass) {
            MethodVisitor getObjectId = super.visitMethod(ACC_PUBLIC, "getObjectId", "()L" + jpaObjectId + ";", null, null);
            getObjectId.visitCode();
         // stack has ()
         // push [this]
            getObjectId.visitVarInsn(ALOAD, 0);
         // stack has ([this])
         // invoke getObjectId()Licom/Id;
            getObjectId.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, "getId", "()Licom/Id;");
         // stack has ([this.getObjectId()])
         // return
            getObjectId.visitInsn(ARETURN);
            getObjectId.visitEnd();
            getObjectId.visitMaxs(1, 0);
            
            MethodVisitor getVersionId = super.visitMethod(ACC_PUBLIC, "getVersionId", "()L" + jpaVersionId + ";", null, null);
            getVersionId.visitCode();
         // stack has ()
         // push [this]
            getVersionId.visitVarInsn(ALOAD, 0);
         // stack has ([this])
         // invoke getChangeToken();
            getVersionId.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, "getChangeToken", "()Licom/ChangeToken;");
         // stack has ([this.getChangeToken()])
         // return
            getVersionId.visitInsn(ARETURN);
            getVersionId.visitEnd();
            getVersionId.visitMaxs(1, 0);
        } else if (classDefn.icomParticipantClass) {
            MethodVisitor getParticipant = super.visitMethod(ACC_PUBLIC, "getParticipant", "()Ljava/lang/Object;", null, null);
            getParticipant.visitCode();
         // stack has ()
         // push [this]
            getParticipant.visitVarInsn(ALOAD, 0);
         // stack has ([this])
         // invoke getParticipant();
            getParticipant.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, "getParticipant", "()Licom/Addressable;");
         // stack has ([getParticipant()])
         // return
            getParticipant.visitInsn(ARETURN);
            getParticipant.visitEnd();
            getParticipant.visitMaxs(1, 0);
        }
        super.visitEnd();
    }

}
