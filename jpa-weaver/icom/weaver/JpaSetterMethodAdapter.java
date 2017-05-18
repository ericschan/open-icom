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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class JpaSetterMethodAdapter extends JpaMethodAdapter {
    
    FieldDefinition fieldDefn;
    boolean wellFormedAccessor;
    String argumentDesc;
    
    String getterMethodName;
    String getterMethodDesc;

    public JpaSetterMethodAdapter(MethodVisitor mv, ClassDefinition classDefn, FieldDefinition fieldDefn, 
            String methodName, String methodDescriptor) {
        super(mv, classDefn, methodName, methodDescriptor);
        this.fieldDefn = fieldDefn;

        MethodType methodType = new MethodType(methodDescriptor);
        Type[] argTypes = methodType.getArgumentTypes();
        if (argTypes.length == 1) {
            argumentDesc = argTypes[0].getDescriptor();
            //if (argumentDesc.equals(fieldDefn.getFieldDesc())) {
                wellFormedAccessor = true;
                char[] field = fieldDefn.getFieldName().toCharArray();
                field[0] = Character.toUpperCase(field[0]);
                getterMethodName = "get" + String.valueOf(field);
                getterMethodDesc = "()" + argumentDesc;
            //}
        }
    }
    
 // TODO need to support boxing of primitive types before calling pre and post methods
    public void visitCode() {
        if (wellFormedAccessor) {
            /* inject the following code in the context of setFieldX(Object argX)
               if (preSet("fieldX", getFieldX(), [argX]))
                   return;
            */
         // stack has ()
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has ([this])
         // push "fieldX"
            mv.visitLdcInsn(fieldDefn.getFieldName());
         // stack has ([this], "fieldX")
         // push [this]
            mv.visitVarInsn(ALOAD, 0);  
         // stack has ([this], "fieldX", [this])
         // invoke getFieldX()
            mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, getterMethodName, getterMethodDesc);
         // stack has ([this], "fieldX", getFieldX())
         // push [argX]                           
            mv.visitVarInsn(ALOAD, 1);
         // stack has ([this], "fieldX", getFieldX(), [argX])
            if (fieldDefn.isEntity()) {
             // invoke preSet("fieldX", getFieldX(), [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetMethodName, preSetMethodDesc);
            } else if (fieldDefn.isEmbedded()) {
             // invoke preSetEmbedded("fieldX", getFieldX(), [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetEmbeddedMethodName, preSetEmbeddedMethodDesc);
            } else if (fieldDefn.isBasic()) {
             // invoke preSetBasic("fieldX", getFieldX(), [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetBasicMethodName, preSetBasicMethodDesc);
            } else {
             // invoke preSetAmbiguous("fieldX", getFieldX(), [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetAmbiguousMethodName, preSetAmbiguousMethodDesc);
            }
         // stack has (preSet())
            Label label = new Label();
         // if false goto label
            mv.visitJumpInsn(IFEQ, label);
         // stack has ()
         // return
            mv.visitInsn(RETURN);
         // label:
            mv.visitLabel(label);
         // stack has ()
         // Frame TODO
         // mv.visitFrame(F_SAME, 0, null, 0, null);
            if (oprandStackIncrement < 4) {
                oprandStackIncrement = 4;
            }
            if (classDefn.icomTopLevelEntityClass && methodName.equals("setParent")) {
             // stack has ()
             // push [this]
                mv.visitVarInsn(ALOAD, 0);
             // push [parent]
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKESPECIAL, icomAbstractEntity, methodName, methodDescriptor);
             // stack has ()
            }
        }
        super.visitCode();
    }

 // TODO need to support boxing of primitive types before calling pre and post methods
    public void visitInsn(int code) {
        if (code != RETURN) {
            super.visitInsn(code);
            return;
        }
        if (wellFormedAccessor) {
            /* inject the following code before RETURN
               if ([this].[fieldX] == [fieldX])
                   postSet("fieldX");
             */
         // stack has (..., )
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., [this])
         // get [this].[fieldX]
            mv.visitFieldInsn(GETFIELD, classDefn.internalClassName, fieldDefn.getFieldName(), fieldDefn.getFieldDesc());
         // stack has (..., [this].[fieldX])
         // push [argX]
            mv.visitVarInsn(ALOAD, 1);
         // stack has (..., [this].[fieldX], [argX])
         // if ([this].[fieldX] == [argX])
            Label label = new Label();
            mv.visitJumpInsn(IF_ACMPNE, label);
         // stack has (..., )
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., [this])
         // push "fieldX"
            mv.visitLdcInsn(fieldDefn.getFieldName());
         // stack has (..., [this], "fieldX")
            if (fieldDefn.isEntity()) {
             // invoke postSet("fieldX")
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetMethodName, postSetMethodDesc);
            } else if (fieldDefn.isEmbedded()) {
             // push [argX]
                mv.visitVarInsn(ALOAD, 1);
             // stack has (..., [this], "fieldX", [argX])
             // invoke postSetEmbedded("fieldX", [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetEmbeddedMethodName, postSetEmbeddedMethodDesc);
            } else if (fieldDefn.isBasic()) {
             // push [argX]
                mv.visitVarInsn(ALOAD, 1);
             // stack has (..., [this], "fieldX", [argX])
             // invoke postSetBasic("fieldX", [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetBasicMethodName, postSetBasicMethodDesc);   
            } else {
             // push [argX]
                mv.visitVarInsn(ALOAD, 1);
             // stack has (..., [this], "fieldX", [argX])
             // invoke postSetAmbiguous("fieldX", [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetAmbiguousMethodName, postSetAmbiguousMethodDesc);
            }
         // stack has (..., )
         // label: 
            mv.visitLabel(label);
         // stack has (..., )
         // Frame TODO
         // mv.visitFrame(F_SAME, 0, null, 0, null);
            if (oprandStackIncrement < 3) {
                oprandStackIncrement = 3;
            }
        }
        super.visitInsn(code);
    }
    
    public boolean isCurrentMethodAccessorOfField(String fieldName) {
        FieldDefinition fd = classDefn.managedFields.get(fieldName);
        if (fd == fieldDefn) {
            if (wellFormedAccessor) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }

}
