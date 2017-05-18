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
import icom.ql.clause.delete.DeleteClauseNode;
import icom.ql.clause.select.FromClauseNode;
import icom.ql.clause.select.GroupByClauseNode;
import icom.ql.clause.select.HavingClauseNode;
import icom.ql.clause.select.OrderByClauseNode;
import icom.ql.clause.select.SelectClauseNode;
import icom.ql.clause.select.fromdomain.CollectionMemberDeclarationNode;
import icom.ql.clause.select.fromdomain.FetchJoinNode;
import icom.ql.clause.select.fromdomain.IdentificationVariableDeclarationNode;
import icom.ql.clause.select.fromdomain.JoinDeclarationNode;
import icom.ql.clause.select.fromdomain.RangeVariableDeclarationNode;
import icom.ql.clause.select.orderby.OrderByItemNode;
import icom.ql.clause.select.orderby.SortDirectionNode;
import icom.ql.clause.update.EqualsAssignmentNode;
import icom.ql.clause.update.SetClauseNode;
import icom.ql.clause.update.UpdateClauseNode;
import icom.ql.expression.LiteralNode;
import icom.ql.expression.ParameterNode;
import icom.ql.expression.aggregate.AvgNode;
import icom.ql.expression.aggregate.CountNode;
import icom.ql.expression.aggregate.MaxNode;
import icom.ql.expression.aggregate.MinNode;
import icom.ql.expression.aggregate.SumNode;
import icom.ql.expression.arithmetic.DivideNode;
import icom.ql.expression.arithmetic.MinusNode;
import icom.ql.expression.arithmetic.MultiplyNode;
import icom.ql.expression.arithmetic.PlusNode;
import icom.ql.expression.arithmetic.UnaryMinusNode;
import icom.ql.expression.arithmetic.function.AbsNode;
import icom.ql.expression.arithmetic.function.LocateNode;
import icom.ql.expression.arithmetic.function.ModNode;
import icom.ql.expression.arithmetic.function.SizeNode;
import icom.ql.expression.arithmetic.function.SqrtNode;
import icom.ql.expression.arithmetic.literal.DoubleLiteralNode;
import icom.ql.expression.arithmetic.literal.FloatLiteralNode;
import icom.ql.expression.arithmetic.literal.IntegerLiteralNode;
import icom.ql.expression.arithmetic.literal.LongLiteralNode;
import icom.ql.expression.bool.literal.BooleanLiteralNode;
import icom.ql.expression.conditional.AndNode;
import icom.ql.expression.conditional.NotNode;
import icom.ql.expression.conditional.OrNode;
import icom.ql.expression.conditional.comparison.EqualsNode;
import icom.ql.expression.conditional.comparison.GreaterThanEqualToNode;
import icom.ql.expression.conditional.comparison.GreaterThanNode;
import icom.ql.expression.conditional.comparison.LessThanEqualToNode;
import icom.ql.expression.conditional.comparison.LessThanNode;
import icom.ql.expression.conditional.comparison.NotEqualsNode;
import icom.ql.expression.conditional.primary.BetweenNode;
import icom.ql.expression.conditional.primary.EmptyCollectionComparisonNode;
import icom.ql.expression.conditional.primary.ExistsNode;
import icom.ql.expression.conditional.primary.InNode;
import icom.ql.expression.conditional.primary.LikeNode;
import icom.ql.expression.conditional.primary.MemberOfNode;
import icom.ql.expression.conditional.primary.NullComparisonNode;
import icom.ql.expression.conditional.primary.SimpleConditionalExpressionNode;
import icom.ql.expression.conditional.primary.like.EscapeNode;
import icom.ql.expression.date.function.DateFunctionNode;
import icom.ql.expression.path.AttributeNode;
import icom.ql.expression.path.DotNode;
import icom.ql.expression.path.VariableNode;
import icom.ql.expression.select.ConstructorNode;
import icom.ql.expression.string.function.ConcatNode;
import icom.ql.expression.string.function.LengthNode;
import icom.ql.expression.string.function.LowerNode;
import icom.ql.expression.string.function.StringFunctionNode;
import icom.ql.expression.string.function.SubstringNode;
import icom.ql.expression.string.function.TrimNode;
import icom.ql.expression.string.function.UpperNode;
import icom.ql.expression.string.literal.StringLiteralNode;
import icom.ql.expression.subquery.AllNode;
import icom.ql.expression.subquery.AnyNode;
import icom.ql.expression.subquery.SomeNode;
import icom.ql.expression.subquery.SubqueryNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;
import icom.ql.statement.DeleteStatement;
import icom.ql.statement.SelectStatement;
import icom.ql.statement.UpdateStatement;

public class AbstractVisitor implements ParseTreeVisitor {
	
	ParseTreeContext context;
	
	public AbstractVisitor(ParseTreeContext context) {
		this.context = context;
	}
	
	public ParseTreeContext getParseTreeContext() {
		return context;
	}
	
	public void setParseTreeContext(ParseTreeContext context) {
		this.context = context;
	}
	
	public JPQLParseTree visitNode(JPQLParseTree node, AbstractNode parent) {
		return node;
	}
	
	public SelectStatement visitNode(SelectStatement node, AbstractNode parent) {
		return node;
	}
	
	public UpdateStatement visitNode(UpdateStatement node, AbstractNode parent) {
		return node;
	}
	
	public DeleteStatement visitNode(DeleteStatement node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SelectClauseNode node, AbstractNode parent) {
		return node;
	}

	public Node visitNode(WhereClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(GroupByClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(HavingClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(OrderByClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(UpdateClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SetClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DeleteClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(FromClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(IdentificationVariableDeclarationNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(CollectionMemberDeclarationNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(JoinDeclarationNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(RangeVariableDeclarationNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(FetchJoinNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(OrderByItemNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SortDirectionNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(EqualsAssignmentNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DotNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(VariableNode node, AbstractNode parent) {
		return node;
	}

	public Node visitNode(AttributeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ParameterNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(AvgNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(CountNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(MaxNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(MinNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SumNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DivideNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(MinusNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(MultiplyNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(PlusNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(UnaryMinusNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(AbsNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LengthNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LocateNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ModNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SizeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SqrtNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DoubleLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(FloatLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(IntegerLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LongLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(BooleanLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(AndNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(NotNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(OrNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(EscapeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(EqualsNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(GreaterThanEqualToNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(GreaterThanNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(NotEqualsNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LessThanEqualToNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LessThanNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(BetweenNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(EmptyCollectionComparisonNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ExistsNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(InNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LikeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(MemberOfNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(NullComparisonNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SimpleConditionalExpressionNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DateFunctionNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ConstructorNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ConcatNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(LowerNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(StringFunctionNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SubstringNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(TrimNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(UpperNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(StringLiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(AllNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(AnyNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SomeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(SubqueryNode node, AbstractNode parent) {
		return node;
	}

}
