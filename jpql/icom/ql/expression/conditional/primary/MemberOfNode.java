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
package icom.ql.expression.conditional.primary;

import icom.ql.ParseTreeVisitor;
import icom.ql.expression.BinaryOperatorNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

/*
Derived from

simple_cond_expression ::=
	comparison_expression |
	between_expression |
	like_expression |
	in_expression |
	null_comparison_expression |
	empty_collection_comparison_expression |
	collection_member_expression |
	exists_expression
*/

/*

collection_member_expression ::= entity_expression [NOT] MEMBER [OF] collection_valued_path_expression

*/
public class MemberOfNode extends BinaryOperatorNode {
	
    private boolean notIndicated = false;

    //If we're dealing with a NOT, we store the expression for the left. 
    //When we get to the one-to-many on the right, it will handle the noneOf using 
    //the receiver stored in the context. 
    //(i.e. secondLastRightExpression.noneOf(lastRightVariable, leftExpression)
    //private Expression leftExpression = null;

    public MemberOfNode() {
        super();
    }
    public void indicateNot() {
        notIndicated = true;
    }

    public boolean notIndicated() {
        return notIndicated;
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

    /**
     * INTERNAL makeNodeOneToMany:
     * Traverse to the leaf on theNode and mark as one to many
     */
    public void makeNodeOneToMany(Node theNode) {
        Node currentNode = theNode;
        do {
            if (!currentNode.hasRight()) {
                // TODO ((AttributeNode)currentNode).setRequiresCollectionAttribute(true);
                return;
            }
            currentNode = currentNode.getRight();
        } while (true);
    }

}
