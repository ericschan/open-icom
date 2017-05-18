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
package icom.ql;

import icom.ql.clause.WhereClauseNode;
import icom.ql.clause.select.OrderByClauseNode;
import icom.ql.clause.select.fromdomain.JoinDeclarationNode;
import icom.ql.clause.select.orderby.OrderByItemNode;
import icom.ql.expression.FunctionalExpressionNode;
import icom.ql.expression.LiteralNode;
import icom.ql.expression.ParameterNode;
import icom.ql.expression.aggregate.AggregateNode;
import icom.ql.expression.arithmetic.BinaryArithmeticOperatorNode;
import icom.ql.expression.arithmetic.UnaryMinusNode;
import icom.ql.expression.conditional.AndNode;
import icom.ql.expression.conditional.NotNode;
import icom.ql.expression.conditional.OrNode;
import icom.ql.expression.conditional.comparison.ComparisonExpressionNode;
import icom.ql.expression.conditional.comparison.EqualsNode;
import icom.ql.expression.conditional.comparison.GreaterThanEqualToNode;
import icom.ql.expression.conditional.comparison.GreaterThanNode;
import icom.ql.expression.conditional.comparison.LessThanEqualToNode;
import icom.ql.expression.conditional.comparison.LessThanNode;
import icom.ql.expression.conditional.comparison.NotEqualsNode;
import icom.ql.expression.conditional.primary.BetweenNode;
import icom.ql.expression.conditional.primary.InNode;
import icom.ql.expression.conditional.primary.LikeNode;
import icom.ql.expression.conditional.primary.MemberOfNode;
import icom.ql.expression.path.AttributeNode;
import icom.ql.expression.path.VariableNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.parser.antlr.JPQLParser;

import java.util.List;

public interface QueryContext {
    
    public JPQLParser getParser();
    
    public void setParser(JPQLParser parser);
	
	public void prepareContextForAbstractSchema(String abstractSchemaName);
	
	public void prepareContextForAbstractSchema(String firstFromAbstractSchemaName, String selectAbstractSchemaName);
	
    public void setParameter(String name, Object value);
    
    public Object getParameter(String name);
    
    public void setMaxResults(int maxResult);

    public void setFirstResult(int startPosition);	
    
    public List<Object> getResultList(Object cacheContext);
    
    public Node visitNode(WhereClauseNode node, AbstractNode parent);
    
    public Node visitNode(AndNode node, AbstractNode parent);
	
	public Node visitNode(OrNode node, AbstractNode parent);
	
	public Node visitNode(NotNode node, AbstractNode parent);
    
    public Node visitNode(ComparisonExpressionNode node, AbstractNode parent);
    
	public Node visitNode(EqualsNode node, AbstractNode parent);
	
	public Node visitNode(NotEqualsNode node, AbstractNode parent);
	
	public Node visitNode(LikeNode node, AbstractNode parent);
	
	public Node visitNode(BetweenNode node, AbstractNode parent);
	
	public Node visitNode(GreaterThanEqualToNode node, AbstractNode parent);
	
	public Node visitNode(GreaterThanNode node, AbstractNode parent);
	
	public Node visitNode(LessThanEqualToNode node, AbstractNode parent);
	
	public Node visitNode(LessThanNode node, AbstractNode parent);
	
	public Node visitNode(MemberOfNode node, AbstractNode parent);
	
	public Node visitNode(InNode node, AbstractNode parent);
	
	public Node visitNode(JoinDeclarationNode node, AbstractNode parent);
	
	public Node visitNode(VariableNode node, AbstractNode parent);
    
	public Node visitNode(AttributeNode node, AbstractNode parent);
	
	public Node visitNode(ParameterNode node, AbstractNode parent);
    
	public Node visitNode(AggregateNode node, AbstractNode parent);
	
	public Node visitNode(LiteralNode node, AbstractNode parent);
	
	public Node visitNode(FunctionalExpressionNode node, AbstractNode parent);
	
	public Node visitNode(BinaryArithmeticOperatorNode node, AbstractNode parent);
	
	public Node visitNode(UnaryMinusNode node, AbstractNode parent);
	
	public Node visitNode(Node node, AbstractNode parent);
	
	public Node visitNode(OrderByClauseNode node, AbstractNode parent);
	
	public Node visitNode(OrderByItemNode node, AbstractNode parent);

}
