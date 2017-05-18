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
package icom.ql.expression.path;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.GenerationContext;
import icom.ql.parser.Node;

/*
Derived from

single_valued_path_expression ::= state_field_path_expression | single_valued_association_path_expression

collection_member_declaration ::= IN (collection_valued_path_expression) [AS] identification_variable

*/

/*

join_collection_valued_path_expression ::= identification_variable.collection_valued_association_field

join_single_valued_association_path_expression ::= identification_variable.single_valued_association_field

state_field_path_expression ::= {identification_variable | single_valued_association_path_expression}.state_field

single_valued_association_path_expression ::= identification_variable.{single_valued_association_field.}* single_valued_association_field

collection_valued_path_expression ::= identification_variable.{single_valued_association_field.}*collection_valued_association_field

state_field ::= {embedded_class_state_field.}*simple_state_field

*/

public class DotNode extends Node {

    private Object enumConstant;

    public boolean isDotNode() {
        return true;
    }

    public Object getEnumConstant() {
		return enumConstant;
	}

	public void setEnumConstant(Object enumConstant) {
		this.enumConstant = enumConstant;
	}
	
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        if (left != null) {
            Node newLeft = left.accept(visitor, this);
            if (newLeft != left) {
            	setLeft(newLeft);
            }
        }
        if (right != null) {
            Node newRight = right.accept(visitor, this);
            if (newRight != right) {
            	setRight(newRight);
            }
        }
        return visitor.visitNode(this, parent);
    }

	public String getAsString() {
        return left.getAsString() + "." + right.getAsString();
    }

    /**
     * Return the left most node of a dot expr, so return 'a' for 'a.b.c'.
     */
    public Node getLeftMostNode() {
        return left.isDotNode() ? ((DotNode)left).getLeftMostNode() : left;
    }

    /**
     * Answer the name of the attribute which is represented by the receiver's
     * right node.
     */
    public String resolveAttribute() {
        return ((AttributeNode)getRight()).getAttributeName();
    }
    
    public Class resolveClass(GenerationContext context) {
        Class leftClass = getLeft().resolveClass(context);
        return getRight().resolveClass(context, leftClass);
    }

    /**
     * Returns the attribute type if the right represents a direct-to-field mapping.
     */
    public Class getTypeOfDirectToField(GenerationContext context) {
        // isDirectToFieldMapping()) {
        //getAttributeClassification();
        return null;
    }

    /**
     * Answer true if the node has a left and right, and the right represents
     * a collection mapping.
     */
    public boolean endsWithCollectionField(GenerationContext context) {
        // isCollectionMapping();
    	return false;
    }
    
    /**
     * Answer true if the SELECTed node has a left and right, and the right represents
     * a direct-to-field mapping.
     */
    public boolean endsWithDirectToField(GenerationContext context) {
        // isDirectToFieldMapping();
    	return false;
    }
}
