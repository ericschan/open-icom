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
package icom.ql.expression.conditional;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;

import java.util.Set;

/*

conditional_expression ::= conditional_term | conditional_expression OR conditional_term

*/

public class OrNode extends LogicalOperatorNode {

    private Set leftOuterScopeVariables = null;
    private Set rightOuterScopeVariables = null;

    public OrNode() {
        super();
    }

    public Set getLeftOuterScopeVariables() {
		return leftOuterScopeVariables;
	}

	public void setLeftOuterScopeVariables(Set leftOuterScopeVariables) {
		this.leftOuterScopeVariables = leftOuterScopeVariables;
	}

	public Set getRightOuterScopeVariables() {
		return rightOuterScopeVariables;
	}

	public void setRightOuterScopeVariables(Set rightOuterScopeVariables) {
		this.rightOuterScopeVariables = rightOuterScopeVariables;
	}
	
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	ParseTreeContext context = visitor.getParseTreeContext();
    	Set saved = context.getOuterScopeVariables();
        if (left != null) {
        	context.resetOuterScopeVariables();
            Node newLeft = left.accept(visitor, this);
            if (newLeft != left) {
            	setLeft(newLeft);
            }
            leftOuterScopeVariables = context.getOuterScopeVariables();
        }
        if (right != null) {
        	context.resetOuterScopeVariables();
            Node newRight = right.accept(visitor, this);
            if (newRight != right) {
            	setRight(newRight);
            }
            rightOuterScopeVariables = context.getOuterScopeVariables();
        }
        context.resetOuterScopeVariables(saved);
        return visitor.visitNode(this, parent);
    }

    /*
    public Expression generateExpression(GenerationContext context) {
        // Get the left expression
        Expression leftExpr = getLeft().generateExpression(context);
        leftExpr = appendOuterScopeVariableJoins(
            leftExpr, leftOuterScopeVariables, context);

        Expression rightExpr = getRight().generateExpression(context);
        rightExpr = appendOuterScopeVariableJoins(
            rightExpr, rightOuterScopeVariables, context);
        
        // Or it with whatever the right expression is
        return leftExpr.or(rightExpr);
    }

    private Expression appendOuterScopeVariableJoins(
        Expression expr, Set outerScopeVariables, GenerationContext context) {
        if ((outerScopeVariables == null) || outerScopeVariables.isEmpty()) {
            // no outer scope variables => nothing to be done
            return expr;
        }
        Expression joins = context.joinVariables(outerScopeVariables);
        return appendExpression(expr, joins);
    }
    */
    
}
