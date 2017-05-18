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

public class JpaAdderMethodAdapter extends JpaMethodAdapter implements Descriptors {
    
    FieldDefinition fieldDefn;
    boolean wellFormedAccessor;
    String argumentDesc;
    int argumentPosition;
    
    public JpaAdderMethodAdapter(MethodVisitor mv, ClassDefinition classDefn, FieldDefinition fieldDefn,
            String methodName, String methodDescriptor) {
        super(mv, classDefn, methodName, methodDescriptor);
        this.fieldDefn = fieldDefn;
        
        MethodType methodType = new MethodType(methodDescriptor);
        Type[] argTypes = methodType.getArgumentTypes();
        if (argTypes.length == 1) {
            argumentDesc = argTypes[0].getDescriptor();
            argumentPosition = 1;
            //if (argumentDesc.equals(fieldDefn.getTargetDesc())) {
                wellFormedAccessor = true;
            //}
        } else if (argTypes.length == 2) {
            String arg0Desc = argTypes[0].getDescriptor();
            if (arg0Desc.equals("I") 
              || arg0Desc.equals("J")
              || arg0Desc.equals("S")) {
                argumentDesc = argTypes[1].getDescriptor();
                argumentPosition = 2;
              //if (argumentDesc.equals(fieldDefn.getTargetDesc())) {
                wellFormedAccessor = true;
              //}
            }
        }
    }
    
 // TODO need to support boxing of primitive types before calling pre and post methods
    public void visitCode() {
        if (wellFormedAccessor) {
            if (! fieldDefn.deferLoadOnAddRemove) {
                /* inject the following code
                   preAddReference("fieldX", [argX])
                */
             // stack has ()
             // push [this]
                mv.visitVarInsn(ALOAD, 0);
             // stack has ([this])
             // push "fieldX"
                mv.visitLdcInsn(fieldDefn.getFieldName());
             // stack has ([this], "fieldX")
             // push [argX]                           
                mv.visitVarInsn(ALOAD, argumentPosition);
             // stack has ([this], "fieldX", [argX])
                if (fieldDefn.isEntity()) {
                 // invoke preAddReference("fieldX", [argX])
                    mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                            preAddReferenceMethodName, preAddReferenceMethodDesc);
                } else if (fieldDefn.isEmbedded()) {
                 // invoke preAddEmbedded("fieldX", [argX])
                    mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                            preAddEmbeddedMethodName, preAddEmbeddedMethodDesc);
                } else {
                 // invoke preAddReference("fieldX", [argX])
                    mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                            preAddReferenceMethodName, preAddReferenceMethodDesc);
                }
             // stack has ()
                if (oprandStackIncrement < 3) {
                    oprandStackIncrement = 3;
                }
            }
        }
        super.visitCode();
    }
    
 // TODO need to support boxing of primitive types before calling pre and post methods
    public void visitInsn(int code) {
        if (code != IRETURN) {
            super.visitInsn(code);
            return;
        }
        if (wellFormedAccessor) {
            /* inject the following code before return(r)
               if (r)
                  postAddReference("fieldX", [argX], [argX])
             */
         // stack has (..., r)
         // DUP r
            mv.visitInsn(DUP);
         // stack has (..., r, r)
            Label label = new Label();
         // if [r] is true
            mv.visitJumpInsn(IFEQ, label);
         // stack has (..., r)
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., r, [this])
         // push "fieldX"
            mv.visitLdcInsn(fieldDefn.getFieldName());
         // stack has (..., r, [this], "fieldX")
         // push [argX]                           
            mv.visitVarInsn(ALOAD, argumentPosition);
         // stack has (..., r, [this], "fieldX", [argX])
         // push [argX]                           
            mv.visitVarInsn(ALOAD, argumentPosition); 
         // stack has (..., r, [this], "fieldX", [argX], [argX])
            if (fieldDefn.isEntity()) {
            // invoke postAddReference("fieldX", [argX], [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                       postAddReferenceMethodName, postAddReferenceMethodDesc);
            } else if (fieldDefn.isEmbedded()) {
            // invoke postAddEmbedded("fieldX", [argX], [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                       postAddEmbeddedMethodName, postAddEmbeddedMethodDesc);
            } else {
            // invoke postAddReference("fieldX", [argX], [argX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                       postAddReferenceMethodName, postAddReferenceMethodDesc);
            }
         // stack has (..., r)
         // label:
            mv.visitLabel(label);
         // stack has (..., r)
         // Frame TODO
         // mv.visitFrame(F_SAME, 0, null, 0, null);
            if (oprandStackIncrement < 5) {
                oprandStackIncrement = 5;
            }
        }
     // return(r)
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
