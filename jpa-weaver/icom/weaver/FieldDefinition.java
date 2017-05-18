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

public class FieldDefinition {
    
    String fieldName;
    String fieldDesc;
    
    boolean annotated = false;
    boolean id = false;
    boolean version = false;
    boolean basic = false;
    boolean embedded = false;
    boolean entity = false;
    boolean collection = false;
    
    RelationshipMode relationshipMode;
    String targetDesc;  // derived from generic signature or JPA annotation
    boolean mappedByThisField = false;
    
    boolean deferLoadOnAddRemove = false;
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public RelationshipMode getRelationshipMode() {
        return relationshipMode;
    }

    public void setRelationshipMode(RelationshipMode relationshipMode) {
        this.relationshipMode = relationshipMode;
    }

    public String getTargetDesc() {
        return targetDesc;
    }

    public void setTargetDesc(String targetDesc) {
        this.targetDesc = targetDesc;
    }

    public boolean isMappedByThisField() {
        return mappedByThisField;
    }
    
    public void setMappedByThisField(boolean mappedByThisField) {
        this.mappedByThisField = mappedByThisField;
    }

    public boolean isAnnotated() {
        return annotated;
    }

    public void setAnnotated(boolean annotated) {
        this.annotated = annotated;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public boolean isEntity() {
        return entity;
    }

    public void setEntity(boolean entity) {
        this.entity = entity;
    }

    public boolean isEmbedded() {
        return embedded;
    }
    
    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }
    
    public boolean isCollection() {
        return collection;
    }
    
    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isDeferLoadForAddRemove() {
        return deferLoadOnAddRemove;
    }

    public void setDeferLoadForAddRemove(boolean deferLoadForAddRemove) {
        this.deferLoadOnAddRemove = deferLoadForAddRemove;
    }

}
