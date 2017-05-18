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
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JpaMethodAdapter extends MethodAdapter implements Opcodes, Descriptors {
    
    ClassDefinition classDefn;
    int oprandStackIncrement;
    
    String methodName;
    String methodDescriptor;
    
    public JpaMethodAdapter(MethodVisitor mv, ClassDefinition classDefn, 
            String methodName, String methodDescriptor) {
        super(mv);
        this.classDefn = classDefn;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
    }
    
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (opcode == GETFIELD) {
            interceptGetField(opcode, owner, name, desc);
        } else if (opcode == PUTFIELD) {
            interceptPutField(opcode, owner, name, desc);
        } else {
            super.visitFieldInsn(opcode, owner, name, desc);
        }
    }
    
    public boolean isCurrentMethodAccessorOfField(String fieldName) {
        return false;   
    }

 // TODO need to support boxing of primitive types before calling pre and post methods
    public void interceptGetField(int opcode, String owner, String name, String desc) {
        FieldDefinition fieldDefn = classDefn.managedFields.get(name);
        if (fieldDefn != null && !isCurrentMethodAccessorOfField(name)) {
            /*  inject preGet("fieldX") before GETFIELD
            */
         // at this point stack has (..., [object]) which is ready for GETFIELD
         // DUP [object]
            mv.visitInsn(DUP);
         // stack has (..., [object], [object])  
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., [object], [object], [this])
            Label label = new Label();
         // if ([object] == [this])
            mv.visitJumpInsn(IF_ACMPNE, label);
         // stack has (..., [this]) because (object == this)
         // DUP [this]
            mv.visitInsn(DUP);
         // stack has (..., [this], [this])
         // push "fieldX"
            mv.visitLdcInsn(name);
         // stack has (..., [this], [this], "fieldX")
         // invoke preGet("fieldX")
            mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                    preGetMethodName, preGetMethodDesc);
         // stack has (..., [this])
         // label:
            mv.visitLabel(label);
            // Frame TODO
            // mv.visitFrame(??, 0, null, 0, null);
         // stack has (..., [object])
            if (oprandStackIncrement < 3) {
                oprandStackIncrement = 3;
            }
        }
     // stack has (..., [object])
     // GETFIELD
        super.visitFieldInsn(opcode, owner, name, desc);
    }
    
 // TODO need to support boxing of primitive types before calling pre and post methods
    public void interceptPutField(int opcode, String owner, String name, String desc) {
        FieldDefinition fieldDefn = classDefn.managedFields.get(name);
        if (fieldDefn != null && !isCurrentMethodAccessorOfField(name)) {
            if (fieldDefn.isCollection() && (fieldDefn.isEntity() || fieldDefn.isEmbedded())) {
                super.visitFieldInsn(opcode, owner, name, desc);
                return;
            }
            char[] field = name.toCharArray();
            field[0] = Character.toUpperCase(field[0]);
            String getterMethodName = "get" + String.valueOf(field);
            String getterMethodDesc = "()" + desc;
            
            /*  inject preSet before PUTFIELD and postSet after PUTFIELD
            */        
         // at this point stack has (..., [this], [fieldX]) which is ready for PUTFIELD  
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., [this], [fieldX], [this])
            mv.visitInsn(DUP2);
         // stack has (..., [this], [fieldX], [this], [fieldX], [this])
         // push "fieldX"
            mv.visitLdcInsn(name);
         // stack has (..., [this], [fieldX], [this], [fieldX], [this], "fieldX")
            mv.visitInsn(DUP_X2);
         // stack has (..., [this], [fieldX], [this], "fieldX", [fieldX], [this], "fieldX")
         // pop                         
            mv.visitInsn(POP);
         // stack has (..., [this], [fieldX], [this], "fieldX", [fieldX], [this])
         // invoke getFieldX()
            mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, getterMethodName, getterMethodDesc);
         // stack has (..., [this], [fieldX], [this], "fieldX", [fieldX], getFieldX())
            mv.visitInsn(SWAP);
         // stack has (..., [this], [fieldX], [this], "fieldX", getFieldX(), [fieldX])
            if (fieldDefn.isEntity()) {
             // invoke preSet("fieldX", getFieldX(), [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetMethodName, preSetMethodDesc);
            } else if (fieldDefn.isEmbedded()) {
             // invoke preSetEmbedded("fieldX", getFieldX(), [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetEmbeddedMethodName, preSetEmbeddedMethodDesc);
            } else if (fieldDefn.isBasic()) {
                // invoke preSetBasic("fieldX", getFieldX(), [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetBasicMethodName, preSetBasicMethodDesc);
            } else {
             // invoke preSetAmbiguous("fieldX", getFieldX(), [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preSetAmbiguousMethodName, preSetAmbiguousMethodDesc);
            }
         // stack has (..., [this], [fieldX], preSet())
         // DUP IRETURN value of preSet() beneath third word
            mv.visitInsn(DUP_X2);
         // stack has (..., preSet(), [this], [fieldX], preSet())
            mv.visitInsn(POP);
         // stack has (..., preSet(), [this], [fieldX])
            mv.visitInsn(DUP_X2);
         // stack has (..., [fieldX], preSet(), [this], [fieldX])
            
         // PUTFIELD in [this].[fieldX]
            super.visitFieldInsn(opcode, owner, name, desc);
         // stack has (..., [fieldX], preSet())
            
            /* inject postSet after PUTFIELD
               if (IRETURN value of preSet() is false && [this].[fieldX] == [fieldX])
                  postSet...
            */
            Label label1 = new Label();
         // stack has (..., [fieldX], preSet())
         // if true goto label, there is not change by PUTFIELD
            mv.visitJumpInsn(IFGT, label1);
         // stack has (..., [fieldX])
            mv.visitInsn(DUP);
         // stack has (..., [fieldX], [fieldX])
         // push [this]
            mv.visitVarInsn(ALOAD, 0);  
         // stack has (..., [fieldX], [fieldX], [this])
         // get [this].[fieldX]
            mv.visitFieldInsn(GETFIELD, classDefn.internalClassName, name, desc);
         // stack has (..., [fieldX], [fieldX], [this].[fieldX])
         // if ([this].[fieldX] == [fieldX])
            mv.visitJumpInsn(IF_ACMPNE, label1);  // jump if !=
         // stack has (..., [fieldX])
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has (..., [fieldX], [this])
            mv.visitInsn(SWAP);
         // stack has (..., [this], [fieldX])
         // push "fieldX"
            mv.visitLdcInsn(name);
         // stack has (..., [this], [fieldX], "fieldX")
            mv.visitInsn(SWAP);
         // stack has (..., [this], "fieldX", [fieldX])
            if (fieldDefn.isEntity()) {
             // stack has (..., [this], "fieldX", [fieldX])
                mv.visitInsn(POP);
             // stack has (..., [this], "fieldX") 
             // invoke postSet("fieldX")
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetMethodName, postSetMethodDesc);
             // stack has (...)
            } else if (fieldDefn.isEmbedded()) {
             // stack has (..., [this], "fieldX", [fieldX])
             // invoke postSetEmbedded("fieldX", [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetEmbeddedMethodName, postSetEmbeddedMethodDesc);
             // stack has (...)
            } else if (fieldDefn.isBasic()) {
             // stack has (..., [this], "fieldX", [fieldX])
             // invoke postSetBasic("fieldX", [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetBasicMethodName, postSetBasicMethodDesc);
             // stack has (...)
            } else {
             // stack has (..., [this], "fieldX", [fieldX])
             // invoke postSetAmbiguous("fieldX", [fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        postSetAmbiguousMethodName, postSetAmbiguousMethodDesc);
             // stack has (...)
            }
            Label label2 = new Label();
            mv.visitJumpInsn(GOTO, label2);
         // label1:
            mv.visitLabel(label1);
         // stack has (..., [fieldX])
            mv.visitInsn(POP);
         // stack has (...)
         // label2:
            mv.visitLabel(label2);
         // stack has (...)

         // Frame TODO
         // mv.visitFrame(F_SAME, 0, null, 0, null);
            if (oprandStackIncrement < 9) {
                oprandStackIncrement = 9;
            }
            return;
        }
        super.visitFieldInsn(opcode, owner, name, desc);
    }
    
    public void visitMaxs(int maxStack, int maxLocals) {
        //maxStack += oprandStackIncrement;
        super.visitMaxs(maxStack, maxLocals);
    }

}
