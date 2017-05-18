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

public interface Descriptors {
    
    String injectableCodeOwnerName = "icom/ManagedObject";
    
    String icomObjectId = "icom/Id";
    String icomVersionId = "icom/ChangeToken";

    String icomIndentifiable = "icom/Identifiable";
    String icomEntity = "icom/Entity";
    String icomAbstractEntity = "icom/AbstractEntity";
    String icomAbstractIdentifiable = "icom/AbstractIdentifiable";
    String icomAbstractNonIdentifiable = "icom/AbstractNonIdentifiable";
    String icomDependent = "icom/jpa/Dependent";
    
    String icomParticipant = "icom/Participant";
    String icomEntityAddress = "icom/EntityAddress";
    String icomContentStream = "icom/ContentStream";
    String icomMimeConvertible = "icom/MimeConvertible";
    
    String jpaObjectId = "icom/ObjectIdTrait";
    String jpaVersionId = "icom/VersionIdTrait";
    
    String entityAnnotationDesc = "Ljavax/persistence/Entity;";
    String embeddableAnnotationDesc = "Ljavax/persistence/Embeddable;";
    String mappedSuperclassAnnotationDesc = "Ljavax/persistence/MappedSuperclass;";
    
    String idAnnotationDesc = "Ljavax/persistence/Id;";
    String versionAnnotationDesc = "Ljavax/persistence/Version;";
    
    String icomIdAnnotationDesc = "Licom/annotation/ObjectIdentifier;";
    String icomVersionAnnotationDesc = "Licom/annotation/VersionIdentifier;";

    String basicAnnotationDesc = "Ljavax/persistence/Basic;";
    String embeddedAnnotationDesc = "Ljavax/persistence/Embedded;";
    String elementCollectionAnnotationDesc = "Ljavax/persistence/ElementCollection;";
    
    String manyToManyAnnotationDesc = "Ljavax/persistence/ManyToMany;";
    String manyToOneAnnotationDesc = "Ljavax/persistence/ManyToOne;";
    String oneToOneAnnotationDesc = "Ljavax/persistence/OneToOne;";
    String oneToManyAnnotationDesc = "Ljavax/persistence/OneToMany;";
    
    String deferLoadOnAddRemove = "Licom/annotation/DeferLoadOnAddRemove;";
    
    String collectionDesc = "Ljava/util/Collection;";
    String setDesc = "Ljava/util/Set;";
    String listDesc = "Ljava/util/List;";
    String mapDesc = "Ljava/util/Map;";
    
    String preGetMethodName = "preGet";
    String preGetMethodDesc = "(Ljava/lang/String;)V";
    
    String preSetMethodName = "preSet";
    String preSetMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)Z";
    
    String postSetMethodName = "postSet";
    String postSetMethodDesc = "(Ljava/lang/String;)V";
    
    String preSetEmbeddedMethodName = "preSetEmbedded";
    String preSetEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)Z";
    
    String postSetEmbeddedMethodName = "postSetEmbedded";
    String postSetEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String preSetBasicMethodName = "preSetBasic";
    String preSetBasicMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)Z";
    
    String postSetBasicMethodName = "postSetBasic";
    String postSetBasicMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String preSetAmbiguousMethodName = "preSetAmbiguous";
    String preSetAmbiguousMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)Z";
    
    String postSetAmbiguousMethodName = "postSetAmbiguous";
    String postSetAmbiguousMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String preFilterCollectionMethodName = "preFilterCollection";
    String preFilterCollectionMethodDesc = "(Ljava/lang/String;Ljava/util/Collection;)V";
    
    String preAddReferenceMethodName = "preAddReference";
    String preAddReferenceMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String postAddReferenceMethodName = "postAddReference";
    String postAddReferenceMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    
    String preAddEmbeddedMethodName = "preAddEmbedded";
    String preAddEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String postAddEmbeddedMethodName = "postAddEmbedded";
    String postAddEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    
    String preRemoveReferenceMethodName = "preRemoveReference";
    String preRemoveReferenceMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String postRemoveReferenceMethodName = "postRemoveReference";
    String postRemoveReferenceMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    
    String preRemoveEmbeddedMethodName = "preRemoveEmbedded";
    String preRemoveEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String postRemoveEmbeddedMethodName = "postRemoveEmbedded";
    String postRemoveEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    
    String preModifyEmbeddedMethodName = "preModifyEmbedded";
    String preModifyEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;)V";
    
    String postModifyEmbeddedMethodName = "postModifyEmbedded";
    String postModifyEmbeddedMethodDesc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
}
