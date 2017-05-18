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
package icom.ql.parser;

import icom.ql.JPQLParseTree;
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
import icom.ql.expression.conditional.primary.like.EscapeNode;
import icom.ql.expression.date.function.DateFunctionNode;
import icom.ql.expression.path.AttributeNode;
import icom.ql.expression.path.DotNode;
import icom.ql.expression.path.VariableNode;
import icom.ql.expression.select.ConstructorNode;
import icom.ql.expression.string.function.ConcatNode;
import icom.ql.expression.string.function.LengthNode;
import icom.ql.expression.string.function.LowerNode;
import icom.ql.expression.string.function.SubstringNode;
import icom.ql.expression.string.function.TrimNode;
import icom.ql.expression.string.function.UpperNode;
import icom.ql.expression.string.literal.StringLiteralNode;
import icom.ql.expression.subquery.AllNode;
import icom.ql.expression.subquery.AnyNode;
import icom.ql.expression.subquery.SomeNode;
import icom.ql.expression.subquery.SubqueryNode;
import icom.ql.statement.DeleteStatement;
import icom.ql.statement.SelectStatement;
import icom.ql.statement.UpdateStatement;

import java.util.List;

/**
 * This interface specifies methods to create parse trees and parse tree nodes. 
 * Used by the JPQLParser to create an internal representation of an
 * JPQL query. The parse tree is created in a bottom-up fashion. All methods takes any
 * child nodes for the parse tree node to be created as arguments. It is the
 * responsibility of the new<XXX> method to set the parent-child relationship
 * between the returned node and any of the child nodes passed as arguments.
 * This implementation manages the state and a list of parameter names for the 
 * current parse tree in the parse tree context. This state needs
 * to be initialized before the same node factory implementation instance may
 * be used to create a second parse tree (see methods initContext and
 * initParameters).
 * </ul>
 */
public class NodeFactoryImpl implements NodeFactory {

    /** The parse tree context. */
    private ParseTreeContext context;

    /** */
    private String currentIdentificationVariable;

    public NodeFactoryImpl(String queryInfo) {
        this.context = new ParseTreeContext(this, queryInfo);
    }

    // ------------------------------------------
    // Trees
    // ------------------------------------------

    /** */
    public Object newSelectStatement(int line, int column, 
                                     Object select, Object from, 
                                     Object where, Object groupBy, 
                                     Object having, Object orderBy) {
        SelectClauseNode queryClauseNode = (SelectClauseNode)select;
        SelectStatement statement = new SelectStatement();
        statement.setQueryClauseNode(queryClauseNode);
        statement.setFromClauseNode((FromClauseNode)from);
        statement.setWhereClauseNode((WhereClauseNode)where);
        statement.setGroupByClauseNode((GroupByClauseNode)groupBy);
        statement.setHavingClauseNode((HavingClauseNode)having);
        statement.setOrderByClauseNode((OrderByClauseNode)orderBy);
        JPQLParseTree tree = new JPQLParseTree();
        tree.setContext(context);
        tree.setStatement(statement);
        queryClauseNode.setParseTree(tree);
        return tree;
    }
    
    /** */
    public Object newUpdateStatement(int line, int column, 
                                     Object update, Object set, Object where) {
        UpdateClauseNode queryClauseNode = (UpdateClauseNode)update;
        UpdateStatement statement = new UpdateStatement();
        statement.setQueryClauseNode(queryClauseNode);
        statement.setSetClauseNode((SetClauseNode)set);
        statement.setWhereClauseNode((WhereClauseNode)where);
        JPQLParseTree tree = new JPQLParseTree();
        tree.setContext(context);
        tree.setStatement(statement);
        queryClauseNode.setParseTree(tree);
        return tree;
    }

    /** */
    public Object newDeleteStatement(int line, int column, 
                                     Object delete, Object where) {
        DeleteClauseNode queryClauseNode = (DeleteClauseNode)delete;
        DeleteStatement statement = new DeleteStatement();
        statement.setQueryClauseNode(queryClauseNode);
        statement.setWhereClauseNode((WhereClauseNode)where);
        JPQLParseTree tree = new JPQLParseTree();
        tree.setContext(context);
        tree.setStatement(statement);
        queryClauseNode.setParseTree(tree);
        return tree;
    }

    // ------------------------------------------
    // Major nodes
    // ------------------------------------------

    /** */
    public Object newSelectClause(int line, int column, 
                                  boolean distinct, List selectExprs) {
        SelectClauseNode node = new SelectClauseNode();
        node.setContext(context);
        node.setSelectExpressions(selectExprs);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newFromClause(int line, int column, List decls) {
        FromClauseNode node = new FromClauseNode();
        node.setContext(context);
        node.setDeclarations(decls);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newWhereClause(int line, int column, Object condition) {
        WhereClauseNode node = new WhereClauseNode();
        node.setContext(context);
        node.setLeft((Node)condition);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newGroupByClause(int line, int column, List items) {
        GroupByClauseNode node = new GroupByClauseNode();
        node.setContext(context);
        node.setGroupByItems(items);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newHavingClause(int line, int column, Object arg) {
        HavingClauseNode node = new HavingClauseNode();
        node.setContext(context);
        node.setHaving((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newOrderByClause(int line, int column, List items) {
        OrderByClauseNode node = new OrderByClauseNode();
        node.setContext(context);
        node.setOrderByItems(items);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newUpdateClause(int line, int column, 
                                  String schema, String variable) {
        UpdateClauseNode node = new UpdateClauseNode();
        node.setContext(context);
        node.setAbstractSchemaName(schema);
        node.setAbstractSchemaIdentifier(variable);
        setPosition(node, line, column);
        registerSchema(calculateCanonicalName(variable), schema, line, column);
        return node;
    }

    /** */
    public Object newDeleteClause(int line, int column, 
                                  String schema, String variable) {
        DeleteClauseNode node = new DeleteClauseNode();
        node.setContext(context);
        node.setAbstractSchemaName(schema);
        node.setAbstractSchemaIdentifier(variable);
        setPosition(node, line, column);
        registerSchema(calculateCanonicalName(variable), schema, line, column);
        return node;
    }

    // ------------------------------------------
    // Variable declaration nodes
    // ------------------------------------------

    /** */
    public Object newRangeVariableDecl(int line, int column, 
                                       String schema, String variable) {
        RangeVariableDeclarationNode node = new RangeVariableDeclarationNode();
        node.setAbstractSchemaName(schema);
        node.setVariableName(variable);
        setPosition(node, line, column);
        registerSchema(node.getCanonicalVariableName(), schema, line, column);
        currentIdentificationVariable = variable;
        return node;
    }

    /** */
    public Object newJoinVariableDecl(int line, int column, boolean outerJoin, 
                                      Object path, String variable) {
        DotNode dotNode = (DotNode)path;
        AttributeNode rightNode = (AttributeNode)dotNode.getRight();
        rightNode.setOuterJoin(outerJoin);
        JoinDeclarationNode node = new JoinDeclarationNode();
        node.setPath(dotNode);
        node.setVariableName(variable);
        node.setOuterJoin(outerJoin);
        setPosition(node, line, column);
        context.registerJoinVariable(node.getCanonicalVariableName(), dotNode, line, column);
        currentIdentificationVariable = variable;
        return node;
    }

    /** */
    public Object newFetchJoin(int line, int column, 
                               boolean outerJoin, Object path) {
        DotNode dotNode = (DotNode)path;
        AttributeNode rightNode = (AttributeNode)dotNode.getRight();
        rightNode.setOuterJoin(outerJoin);
        // register the dot expression to be added as joined attribute
        FetchJoinNode node = new FetchJoinNode();
        node.setPath(dotNode);
        node.setOuterJoin(outerJoin);
        setPosition(node, line, column);
        context.registerFetchJoin(dotNode.getLeft().getAsString(), dotNode);
        return node;
    }

    /** */
    public Object newCollectionMemberVariableDecl(int line, int column, 
                                                  Object path, String variable) {
        DotNode dotNode = (DotNode)path;
        AttributeNode rightNode = (AttributeNode)dotNode.getRight();
        // The IN-clause expression must be a collection valued path expression
        // TODO rightNode.setRequiresCollectionAttribute(true);
        CollectionMemberDeclarationNode node = new CollectionMemberDeclarationNode();
        node.setPath(dotNode);
        node.setVariableName(variable);
        setPosition(node, line, column);
        context.registerJoinVariable(node.getCanonicalVariableName(), dotNode, line, column);
        currentIdentificationVariable = variable;
        return node;
    }

    /** */
    public Object newVariableDecl(int line, int column, 
                                  Object path, String variable) {
        DotNode dotNode = (DotNode)path;
        AttributeNode rightNode = (AttributeNode)dotNode.getRight();
        JoinDeclarationNode node = new JoinDeclarationNode();
        node.setPath(dotNode);
        node.setVariableName(variable);
        setPosition(node, line, column);
        context.registerJoinVariable(node.getCanonicalVariableName(), dotNode, line, column);
        currentIdentificationVariable = variable;
        return node;
    }

    // ------------------------------------------
    // Identifier and path expression nodes
    // ------------------------------------------

    /** */
    public Object newDot(int line, int column, Object left, Object right) {
        DotNode node = new DotNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newVariableAccess(int line, int column, String identifier) {
        VariableNode node = new VariableNode(identifier);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newAttribute(int line, int column, String identifier) {
        AttributeNode node = new AttributeNode(identifier);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newQualifiedAttribute(int line, int column, 
                                        String variable, String attribute) {
        Object varNode = newVariableAccess(line, column, variable);
        Object attrNode = newAttribute(line, column, attribute);
        return newDot(line, column, varNode, attrNode);
    }

    // ------------------------------------------
    // Aggregate nodes
    // ------------------------------------------

    /** */
    public Object newAvg(int line, int column, boolean distinct, Object arg) {
        AvgNode node = new AvgNode();
        node.setLeft((Node)arg);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMax(int line, int column, boolean distinct, Object arg) {
        MaxNode node = new MaxNode();
        node.setLeft((Node)arg);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMin(int line, int column, boolean distinct, Object arg) {
        MinNode node = new MinNode();
        node.setLeft((Node)arg);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSum(int line, int column, boolean distinct, Object arg) {
        SumNode node = new SumNode();
        node.setLeft((Node)arg);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newCount(int line, int column, boolean distinct, Object arg) {
        CountNode node = new CountNode();
        node.setLeft((Node)arg);
        node.setDistinct(distinct);
        setPosition(node, line, column);
        return node;
    }

    // ------------------------------------------
    // Binary expression nodes
    // ------------------------------------------

    /** */
    public Object newOr(int line, int column, Object left, Object right) {
        OrNode node = new OrNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newAnd(int line, int column, Object left, Object right) {
        AndNode node = new AndNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newEquals(int line, int column, Object left, Object right) {
        EqualsNode node = new EqualsNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newNotEquals(int line, int column, Object left, Object right) {
        NotEqualsNode node = new NotEqualsNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newGreaterThan(int line, int column, 
                                 Object left, Object right) {
        GreaterThanNode node = new GreaterThanNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newGreaterThanEqual(int line, int column, 
                                      Object left, Object right) {
        GreaterThanEqualToNode node = new GreaterThanEqualToNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newLessThan(int line, int column, Object left, Object right) {
        LessThanNode node = new LessThanNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newLessThanEqual(int line, int column, 
                                   Object left, Object right) {
        LessThanEqualToNode node = new LessThanEqualToNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newPlus(int line, int column, Object left, Object right) {
        PlusNode node = new PlusNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMinus(int line, int column, Object left, Object right) {
        MinusNode node = new MinusNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMultiply(int line, int column, Object left, Object right) {
        MultiplyNode node = new MultiplyNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newDivide(int line, int column, Object left, Object right) {
        DivideNode node = new DivideNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Unary expression nodes
    // ------------------------------------------

    /** */
    public Object newUnaryPlus(int line, int column, Object arg) {
        return arg;
    }
    
    /** */
    public Object newUnaryMinus(int line, int column, Object arg) {
        UnaryMinusNode node = new UnaryMinusNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newNot(int line, int column, Object arg) {
        NotNode node = new NotNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Conditional expression nodes
    // ------------------------------------------
    
    /** */
    public Object newBetween(int line, int column, boolean not, Object arg, 
                             Object lower, Object upper) {
        BetweenNode node = new BetweenNode();
        node.setLeft((Node)arg);
        node.setRightForBetween((Node)lower);
        node.setRightForAnd((Node)upper);
        setPosition(node, line, column);
        return not? newNot(line, column, node) : node;
    }

    /** */
    public Object newLike(int line, int column, boolean not, Object string, 
                          Object pattern, Object escape)  {
        LikeNode node = new LikeNode();
        node.setLeft((Node)string);
        node.setRight((Node)pattern);
        node.setEscapeNode((EscapeNode)escape);
        setPosition(node, line, column);
        return not ? newNot(line, column, node) : node;
    }

    /** */
    public Object newEscape(int line, int column, Object arg) {
        EscapeNode node = new EscapeNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newIn(int line, int column, 
                        boolean not, Object expr, List items) {
        InNode node = new InNode();
        if (not) node.indicateNot();
        node.setLeft((Node)expr);
        node.setTheObjects(items);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newIsNull(int line, int column, boolean not, Object expr) {
        NullComparisonNode node = new NullComparisonNode();
        node.setLeft((Node)expr);
        setPosition(node, line, column);
        return not ? newNot(line, column, node) : node;
    }

    /** */
    public Object newIsEmpty(int line, int column, boolean not, Object expr)  {
        EmptyCollectionComparisonNode node = 
            new EmptyCollectionComparisonNode();
        node.setLeft((Node)expr);
        if (not) node.indicateNot();
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMemberOf(int line, int column, 
                              boolean not, Object expr, Object collection)  {
        MemberOfNode node = new MemberOfNode();
        node.setLeft((Node)expr);
        node.setRight((Node)collection);
        if (not) node.indicateNot();
        setPosition(node, line, column);
        return node;
    }

    // ------------------------------------------
    // Parameter nodes
    // ------------------------------------------
 
    /** */
    public Object newPositionalParameter(int line, int column, String position) {
        ParameterNode node = new ParameterNode(position);
        context.addParameter(position);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newNamedParameter(int line, int column, String name) {
        ParameterNode node = new ParameterNode(name);
        context.addParameter(name);
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Literal nodes
    // ------------------------------------------

    /** */
    public Object newBooleanLiteral(int line, int column, Object value) {
        BooleanLiteralNode node = new BooleanLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newIntegerLiteral(int line, int column, Object value) {
        IntegerLiteralNode node = new IntegerLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newLongLiteral(int line, int column, Object value) {
        LongLiteralNode node = new LongLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newFloatLiteral(int line, int column, Object value) {
        FloatLiteralNode node = new FloatLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newDoubleLiteral(int line, int column, Object value) {
        DoubleLiteralNode node = new DoubleLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newStringLiteral(int line, int column, Object value) {
        StringLiteralNode node = new StringLiteralNode();
        node.setLiteral(value);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newNullLiteral(int line, int column) {
        LiteralNode node = new LiteralNode();
        node.setLiteral(null);
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Objects for functions returning strings
    // ------------------------------------------

    /** */
    public Object newConcat(int line, int column, Object left, Object right) {
        ConcatNode node = new ConcatNode();
        node.setLeft((Node)left);
        node.setRight((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSubstring(int line, int column, 
                               Object string, Object start, Object length) {
        SubstringNode node = new SubstringNode();
        node.setLeft((Node)string);
        node.setStartPosition((Node)start);
        node.setStringLength((Node)length);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newTrim(int line, int column, TrimSpecification trimSpec, 
                          Object trimChar, Object string) {
        TrimNode node = new TrimNode();
        node.setLeft((Node)string);
        node.setTrimChar((Node)trimChar);
        switch (trimSpec) {
        case LEADING:
            node.setLeading(true);
            break;
        case TRAILING:
            node.setTrailing(true);
            break;
        case BOTH:
            node.setBoth(true);
            break;
        }
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newLower(int line, int column, Object arg) {
        LowerNode node = new LowerNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newUpper(int line, int column, Object arg) {
        UpperNode node = new UpperNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    // ------------------------------------------
    // Objects for functions returning numerics
    // ------------------------------------------

    /** */
    public Object newLocate(int line, int column, 
                            Object pattern, Object arg, Object startPos) {
        LocateNode node = new LocateNode();
        node.setFind((Node)pattern);
        node.setFindIn((Node)arg);
        node.setStartPosition((Node)startPos);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newLength(int line, int column, Object arg) {
        LengthNode node = new LengthNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newAbs(int line, int column, Object arg) {
        AbsNode node = new AbsNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSqrt(int line, int column, Object arg) {
        SqrtNode node = new SqrtNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newMod(int line, int column, Object left, Object right) {
        ModNode node = new ModNode();
        node.setLeft((Node)left);
        node.setDenominator((Node)right);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSize(int line, int column, Object arg) {
        SizeNode node = new SizeNode();
        node.setLeft((Node)arg);
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Objects for functions returning datetime
    // ------------------------------------------

    /** */
    public Object newCurrentDate(int line, int column) {
        DateFunctionNode node = new DateFunctionNode();
        node.useCurrentDate();
        setPosition(node, line, column);
        return node;
    }
    
    /** */
    public Object newCurrentTime(int line, int column) {
        DateFunctionNode node = new DateFunctionNode();
        node.useCurrentTime();
        setPosition(node, line, column);

        return node;
    }
    
    /** */
    public Object newCurrentTimestamp(int line, int column) {
        DateFunctionNode node = new DateFunctionNode();
        node.useCurrentTimestamp();
        setPosition(node, line, column);
        return node;
    }
    
    // ------------------------------------------
    // Subquery nodes
    // ------------------------------------------

    /** */
    public Object newSubquery(int line, int column, 
                              Object select, Object from, Object where, 
                              Object groupBy, Object having) {
        SelectClauseNode queryClauseNode = (SelectClauseNode)select;
        SelectStatement statement = new SelectStatement();
        statement.setQueryClauseNode(queryClauseNode);
        statement.setFromClauseNode((FromClauseNode)from);
        statement.setWhereClauseNode((WhereClauseNode)where);
        statement.setGroupByClauseNode((GroupByClauseNode)groupBy);
        statement.setHavingClauseNode((HavingClauseNode)having);
        JPQLParseTree tree = new JPQLParseTree();
        tree.setContext(context);
        tree.setStatement(statement);
        queryClauseNode.setParseTree(tree);
        SubqueryNode node = new SubqueryNode();
        node.setParseTree(tree);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newExists(int line, int column, boolean not, Object subquery) {
        ExistsNode node = new ExistsNode();
        if (not) node.indicateNot();
        node.setLeft((Node)subquery);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newIn(int line, int column, 
                        boolean not, Object expr, Object subquery) {
        InNode node = new InNode();
        if (not) node.indicateNot();
        node.setLeft((Node)expr);
        node.addNodeToTheObjects((Node)subquery);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newAll(int line, int column, Object subquery) {
        AllNode node = new AllNode();
        node.setLeft((Node)subquery);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newAny(int line, int column, Object subquery) {
        AnyNode node = new AnyNode();
        node.setLeft((Node)subquery);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSome(int line, int column, Object subquery) {
        SomeNode node = new SomeNode();
        node.setLeft((Node)subquery);
        setPosition(node, line, column);
        return node;
    }

    // ------------------------------------------
    // Miscellaneous nodes
    // ------------------------------------------

    /** */
    public Object newAscOrdering(int line, int column, Object arg) {
        OrderByItemNode node = new OrderByItemNode();
        SortDirectionNode sortDirection = new SortDirectionNode();
        sortDirection.useAscending();
        node.setDirection(sortDirection);
        node.setOrderByItem((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newDescOrdering(int line, int column, Object arg) {
        OrderByItemNode node = new OrderByItemNode();
        SortDirectionNode sortDirection = new SortDirectionNode();
        sortDirection.useDescending();
        node.setDirection(sortDirection);
        node.setOrderByItem((Node)arg);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newConstructor(int line, int column, 
                                 String className, List args) {
        ConstructorNode node = new ConstructorNode(className);
        node.setConstructorItems(args);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSetClause(int line, int column, List assignments) {
        SetClauseNode node = new SetClauseNode();
        node.setAssignmentNodes(assignments);
        setPosition(node, line, column);
        return node;
    }

    /** */
    public Object newSetAssignmentClause(int line, int column, 
                                         Object target, Object value) {
        EqualsAssignmentNode node = new EqualsAssignmentNode();
        node.setLeft((Node)target);
        node.setRight((Node)value);
        return node;
    }

    // ------------------------------------------
    // Helper methods
    // ------------------------------------------

    /** */
    private void setPosition(Node node, int line, int column) {
        node.setLine(line);
        node.setColumn(column);
    }

    /** */
    private void registerSchema(String variable, String schema, int line, int column) {
        if (variable != null) {
            context.registerSchema(variable, schema, line, column);
        }
        else {
            // UPDATE and DELETE may not define a variable =>
            // use schema name as variable
            context.registerSchema(calculateCanonicalName(schema), schema, line, column);
        }
    }

    /** */
    private String calculateCanonicalName(String name) {
        return (name == null) ? null :
            IdentificationVariableDeclarationNode.calculateCanonicalName(name);
    }


}
