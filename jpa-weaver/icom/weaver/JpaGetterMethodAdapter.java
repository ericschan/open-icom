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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class JpaGetterMethodAdapter extends JpaMethodAdapter {
    
    FieldDefinition fieldDefn;
    boolean wellFormedAccessor;

    public JpaGetterMethodAdapter(MethodVisitor mv, ClassDefinition classDefn, FieldDefinition fieldDefn,
            String methodName, String methodDescriptor) {
        super(mv, classDefn, methodName, methodDescriptor);
        this.fieldDefn = fieldDefn;
        
        MethodType methodType = new MethodType(methodDescriptor);
        Type[] argTypes = methodType.getArgumentTypes();
        //Type retType = methodType.getReturnType();
        if (argTypes.length == 0) {
            wellFormedAccessor = true;
        }
    }
    
 // TODO need to support boxing of primitive types before calling pre and post methods
    public void visitCode() {
        if (wellFormedAccessor) {
            /* inject the following code
               preGet("fieldX");
            */
         // stack has ()
         // push [this]
            mv.visitVarInsn(ALOAD, 0);
         // stack has ([this])
         // push "fieldX"
            mv.visitLdcInsn(fieldDefn.getFieldName());
         // stack has ([this], "fieldX")
         // invoke preGet("fieldX")
            mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                    preGetMethodName, preGetMethodDesc);
         // stack has ()
            if (oprandStackIncrement < 2) {
                oprandStackIncrement = 2;
            }
            
            if (fieldDefn.isCollection() && (fieldDefn.isEntity() || fieldDefn.isEmbedded())) {
                /* inject the following code
                   preFilterCollection("fieldX", [this].[fieldX])
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
             // get [this].[fieldX]
                mv.visitFieldInsn(GETFIELD, classDefn.internalClassName, fieldDefn.getFieldName(), fieldDefn.getFieldDesc());
             // stack has ([this], "fieldX", [this].[fieldX])
             // invoke preFilterCollection("fieldX", [this].[fieldX])
                mv.visitMethodInsn(INVOKEVIRTUAL, classDefn.internalClassName, 
                        preFilterCollectionMethodName, preFilterCollectionMethodDesc);
             // stack has ()
                if (oprandStackIncrement < 3) {
                    oprandStackIncrement = 3;
                }
            }
        }
        super.visitCode();
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
