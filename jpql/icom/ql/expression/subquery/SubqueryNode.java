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
package icom.ql.expression.subquery;

import icom.ql.JPQLParseTree;
import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;

import java.util.Set;


public class SubqueryNode extends Node {

    private JPQLParseTree subqueryParseTree;

    // Set of names of variables declared in an outer scope and used in the subquery.
    private Set outerVars;

    public SubqueryNode() {
        super();
    }

    /** 
     * INTERNAL 
     * If called the subquery is part of the WHERE clause of an UPDATE or
     * DELETE statement that does not define an identification variable. 
     * The method checks the clauses of the subquery for unqualified fields
     * accesses. 
     */
    /*
    public Node qualifyAttributeAccess(ParseTreeContext context) {
        subqueryParseTree.getFromClauseNode().qualifyAttributeAccess(context);
        subqueryParseTree.getQueryClauseNode().qualifyAttributeAccess(context);
        if (subqueryParseTree.getWhereClauseNode() != null) {
            subqueryParseTree.getWhereClauseNode().qualifyAttributeAccess(context);
        }
        if (subqueryParseTree.getGroupByClauseNode() != null) {
            subqueryParseTree.getGroupByClauseNode().qualifyAttributeAccess(context);
        }
        if (subqueryParseTree.getHavingClauseNode() != null) {
            subqueryParseTree.getHavingClauseNode().qualifyAttributeAccess(context);
        }
        return this;
    }
    */
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	subqueryParseTree.accept(visitor, this);
        return visitor.visitNode(this, parent);
    }
    
    public void validate(ParseTreeContext context) {
    	/*
        subqueryParseTree.validate(context);
        outerVars = context.getOuterScopeVariables();
        SelectClauseNode selectClauseNode = (SelectClauseNode)subqueryParseTree.getQueryClauseNode();
        // Get the select expression, subqueries only have one
        Node selectExpr = (Node)selectClauseNode.getSelectExpressions().get(0);
        setType(selectExpr.getType());
        */
    }

    /*
    public Expression generateExpression(GenerationContext context) {
        Expression base = context.getBaseExpression();
        ReportQuery innerQuery = getReportQuery(context);
        return base.subQuery(innerQuery);
        return null;
    }
    */
    
    public boolean isSubqueryNode() {
        return true;
    }

    /** */
    public void setParseTree(JPQLParseTree parseTree) {
        this.subqueryParseTree = parseTree;
    }

    /** */
    public JPQLParseTree getParseTree() {
        return subqueryParseTree;
    }
    
}

