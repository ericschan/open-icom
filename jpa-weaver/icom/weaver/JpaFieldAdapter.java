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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;

public class JpaFieldAdapter implements FieldVisitor, Descriptors {

    FieldVisitor fv;
    JpaClassAdapter ca;
    FieldDefinition fieldDefn;

    public JpaFieldAdapter(FieldVisitor fv, JpaClassAdapter ca, FieldDefinition fieldDefn) {
        super();
        this.fv = fv;
        this.ca = ca;
        this.fieldDefn = fieldDefn;
    }

    // Visits an annotation of the field
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        RelationshipMode mode = JpaClassAdapter.relationshipModes.get(desc);
        if (mode != null) {
            fieldDefn.annotated = true;
            fieldDefn.entity = true;
            fieldDefn.relationshipMode = mode;
        } else if (embeddedAnnotationDesc.equals(desc)) {
            fieldDefn.annotated = true;
            fieldDefn.embedded = true;
        } else if (basicAnnotationDesc.equals(desc)) {
            fieldDefn.annotated = true;
            fieldDefn.basic = true;
        } else if (elementCollectionAnnotationDesc.equals(desc)) {
            fieldDefn.annotated = true;
        } else if (idAnnotationDesc.equals(desc)) {
            fieldDefn.id = true;
        } else if (versionAnnotationDesc.equals(desc)) {
            fieldDefn.version = true;
        } else if (icomIdAnnotationDesc.equals(desc)) {
            fieldDefn.id = true;
        } else if (icomVersionAnnotationDesc.equals(desc)) {
            fieldDefn.version = true;
        } else if (deferLoadOnAddRemove.equals(desc)) {
            fieldDefn.deferLoadOnAddRemove = true;
        }
        
        CollectionType type = JpaClassAdapter.collectionTypes.get(fieldDefn.getFieldDesc());
        if (type != null) {
            fieldDefn.collection = true;
        }
        
        AnnotationVisitor av = fv.visitAnnotation(desc, visible);
        AnnotationVisitor fav = new JpaFieldAnnotationAdapter(av, ca, fieldDefn);
        return fav;
    }

    // Visits a non standard attribute of the field
    public void visitAttribute(Attribute attr) {
        fv.visitAttribute(attr);
    }

    // Visits the end of the field
    public void visitEnd() {
        fv.visitEnd();
    }

}
