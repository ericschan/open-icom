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
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;
import icom.ql.parser.TypeHelper;
import icom.ql.statement.DeleteStatement;
import icom.ql.statement.SelectStatement;
import icom.ql.statement.UpdateStatement;

import java.util.Iterator;
import java.util.List;

public class SchemaValidator implements ParseTreeVisitor {
	
	ParseTreeContext context;

	SchemaHelper schemaHelper;
	
	public SchemaValidator(ParseTreeContext context, SchemaHelper schemaHelper) {
		this.context = context;
		this.schemaHelper = schemaHelper;
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
	
	public Node visitNode(FromClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(WhereClauseNode node, AbstractNode parent) {
		node.setType(context.getTypeHelper().getBooleanType());
		return node;
	}
	
	public Node visitNode(GroupByClauseNode node, AbstractNode parent) {
		List selectExprs = ((SelectClauseNode)parent).getSelectExpressions();
        // check select expressions
        for (Iterator i = selectExprs.iterator(); i.hasNext(); ) {
            Node selectExpr = (Node)i.next();
            if (!isValidSelectExpr(node, selectExpr)) {
            	throw JPQLException.invalidSelectForGroupByQuery(
                    context.getQueryInfo(), 
                    selectExpr.getLine(), selectExpr.getColumn(), 
                    selectExpr.getAsString(), node.getAsString());
            }
        }
        return node;
	}
	
	/** 
     * Returns true if the specified expr is a valid SELECT clause expression.
     */    
    private boolean isValidSelectExpr(GroupByClauseNode node, Node expr) {
        if (expr.isAggregateNode()) {
            return true;
        } else if (expr.isConstructorNode()) {
            List args = ((ConstructorNode)expr).getConstructorItems();
            for (Iterator i = args.iterator(); i.hasNext(); ) {
                Node arg = (Node)i.next();
                if (!isValidSelectExpr(node, arg)) {
                    return false;
                }
            }
            return true;
        }
        return isGroupbyItem(node, expr);
    }
    
    /**
     * Return true if the specified expr is a groupby item.
     */    
    private boolean isGroupbyItem(GroupByClauseNode node, Node expr) {
        if (expr.isDotNode() || expr.isVariableNode()) {
            String exprRepr = expr.getAsString();
            for (Iterator i = node.getGroupByItems().iterator(); i.hasNext();) {
                Node item = (Node)i.next();
                String itemRepr = item.getAsString();
                if (exprRepr.equals(itemRepr)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /** 
     * Returns true if the specified expr is a valid HAVING clause expression.
     */
    public boolean isValidHavingExpr(GroupByClauseNode node, Node expr) {
        if (expr.isDotNode() || expr.isVariableNode()) {
            return isGroupbyItem(node, expr);
        } else {
            // delegate to child node if any
            Node left = expr.getLeft();
            Node right = expr.getRight();
            return ((left == null) || isValidHavingExpr(node, left)) &&
                ((right == null) || isValidHavingExpr(node, right));
        }
    }
	
	public Node visitNode(HavingClauseNode node, AbstractNode parent) {
		SelectStatement select = (SelectStatement) parent;
		GroupByClauseNode groupByClauseNode = select.getGroupByClauseNode();
		if ((groupByClauseNode != null) && !isValidHavingExpr(groupByClauseNode, node.getHaving())) {
        	throw JPQLException.invalidHavingExpression(
                context.getQueryInfo(), node.getHaving().getLine(), node.getHaving().getColumn(),
                node.getHaving().getAsString(), groupByClauseNode.getAsString());
        }
		return node;
	}
	
	public Node visitNode(OrderByClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(UpdateClauseNode node, AbstractNode parent) {
		String baseVariable = node.getCanonicalAbstractSchemaIdentifier();
		context.setBaseVariable(baseVariable);
		
		UpdateStatement statement = (UpdateStatement) parent;
		UpdateClauseNode updateClauseNode = statement.getQueryClauseNode();
        if (updateClauseNode.getAbstractSchemaIdentifier() == null) {
        	/*
             * This method handles any unqualified field access in bulk UPDATE and
             * DELETE statements. A UPDATE or DELETE statement may not define an
             * identification variable. In this case any field accessed from the
             * current class is not qualified with an identification variable, e.g.
             *   UPDATE Document SET name = :newname WHERE name is null
             * The method goes through the expressions of the SET clause and the WHERE
             * clause of such an DELETE and UPDATE statement and qualifies the field
             * access using the abstract schema name as qualifier.
             */
        	QualifyAttributeVisitor qaa = new QualifyAttributeVisitor(context);
        	SetClauseNode setClauseNode = statement.getSetClauseNode();
            if (setClauseNode != null) {
                setClauseNode.accept(qaa, node);
            }
            WhereClauseNode whereClauseNode = statement.getWhereClauseNode();
            if (whereClauseNode != null) {
                whereClauseNode.accept(qaa, node);
            }
        }
        
		return node;
	}
	
	public Node visitNode(SetClauseNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DeleteClauseNode node, AbstractNode parent) {
		String baseVariable = node.getCanonicalAbstractSchemaIdentifier();
		context.setBaseVariable(baseVariable);
		
		DeleteStatement statement = (DeleteStatement) parent;
		DeleteClauseNode deleteClauseNode = statement.getQueryClauseNode();
		if (deleteClauseNode.getAbstractSchemaIdentifier() == null) {
		    /*
		     * This method handles any unqualified field access in bulk UPDATE and
		     * DELETE statements. A UPDATE or DELETE statement may not define an
		     * identification variable. In this case any field accessed from the
		     * current class is not qualified with an identification variable, e.g.
		     *   DELETE Document WHERE name is null
		     * The method goes through the expressions of the SET clause and the WHERE
		     * clause of such an DELETE and UPDATE statement and qualifies the field
		     * access using the abstract schema name as qualifier.
		     */
			WhereClauseNode whereClauseNode = statement.getWhereClauseNode();
            if (whereClauseNode != null) {
            	QualifyAttributeVisitor qaa = new QualifyAttributeVisitor(context);
                whereClauseNode.accept(qaa, node);
            }
        }
		return node;
	}
	
	public Node visitNode(IdentificationVariableDeclarationNode node, AbstractNode parent) {
		context.setScopeOfVariable(node.getCanonicalVariableName());
		return node;
	}
	
	public Node visitNode(CollectionMemberDeclarationNode node, AbstractNode parent) {
		context.setScopeOfVariable(node.getCanonicalVariableName());
		Node path = node.getPath();
        if (path != null) {
            node.setType(path.getType());
        }
		return node;
	}
	
	public Node visitNode(JoinDeclarationNode node, AbstractNode parent) {
		context.setScopeOfVariable(node.getCanonicalVariableName());
		Node path = node.getPath();
        if (path != null) {
            node.setType(path.getType());

            // join of embedded attribute is not supported.
            if (path.isDotNode()) {
                //TypeHelper typeHelper = context.getTypeHelper();
                VariableNode left = (VariableNode)path.getLeft();
                AttributeNode right = (AttributeNode)path.getRight(); 
                if ((left != null) && (right != null)) {
                    if (schemaHelper.isEmbeddedAttribute(left.getType(), right.getAttributeName()))
                        throw JPQLException.unsupportJoinArgument(
                            context.getQueryInfo(), node.getLine(), node.getColumn(), 
                            "Join", node.getType().toString());
                } 
            }
        }
		return node;
	}
	
	public Node visitNode(RangeVariableDeclarationNode node, AbstractNode parent) {
		context.setScopeOfVariable(node.getCanonicalVariableName());
        Object type = schemaHelper.resolveSchema(node.getAbstractSchemaName());
        if (type == null) {
            throw JPQLException.unknownAbstractSchemaType2(
                context.getQueryInfo(), node.getLine(), node.getColumn(), node.getAbstractSchemaName());
        }
        node.setType(type);
		return node;
	}
	
	public Node visitNode(FetchJoinNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(OrderByItemNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
		Node orderByItem = node.getOrderByItem();
        if (orderByItem != null) {
            Object type = orderByItem.getType();
            node.setType(type);
            if (!typeHelper.isOrderableType(type)) {
            	if (!schemaHelper.isOrderableType(type)) {
	                throw JPQLException.expectedOrderableOrderByItem(
	                    context.getQueryInfo(), orderByItem.getLine(), orderByItem.getColumn(), 
	                    orderByItem.getAsString(), typeHelper.getTypeName(type));
            	}
            }
        }
		return node;
	}
	
	public Node visitNode(SortDirectionNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(EqualsAssignmentNode node, AbstractNode parent) {
		if ((node.getLeft() != null) && (node.getRight() != null)) {
			validateParameter(node.getLeft(), node.getRight().getType());
            validateParameter(node.getRight(), node.getLeft().getType());
        }
		validateTarget(node);
		return node;
	}
	
	private void validateTarget(Node node) {
        if (node.isDotNode()) {
            Node path = node.getLeft();
            Object type = path.getType();
            AttributeNode attributeNode = (AttributeNode)node.getRight();
            String attribute = attributeNode.getAttributeName();
            if (schemaHelper.isSingleValuedRelationship(type, attribute) || 
            		schemaHelper.isSimpleStateAttribute(type, attribute)) {
                validateNavigation(path);
            } else {
                throw JPQLException.invalidSetClauseTarget(
                    context.getQueryInfo(), attributeNode.getLine(), 
                    attributeNode.getColumn(), path.getAsString(), attribute);
            }
        }
    }

    private void validateNavigation(Node qualifier) {
        if (qualifier.isDotNode()) {
            Node left = qualifier.getLeft();
            AttributeNode attributeNode = (AttributeNode)qualifier.getRight();
            String attribute = attributeNode.getAttributeName();
            Object type = left.getType();
            if (!schemaHelper.isEmbeddedAttribute(type, attribute)) {
                throw JPQLException.invalidSetClauseNavigation(
                    context.getQueryInfo(), attributeNode.getLine(), 
                    attributeNode.getColumn(), qualifier.getAsString(), attribute);
            }
            validateNavigation(left);
        }
    }
	
	public Node visitNode(DotNode node, AbstractNode parent) {
        String name = ((AttributeNode)node.getRight()).getAttributeName();
        // check for fully qualified type names
        Node leftMost = node.getLeftMostNode();
        if (isDeclaredVariable(leftMost, context)) {
        	checkDotNodeNavigation(node, context);
            Object type = schemaHelper.resolveAttribute(node.getLeft().getType(), name);
            if (type == null) {
                // could not resolve attribute
                throw JPQLException.unknownAttribute(
                    context.getQueryInfo(), node.getRight().getLine(), node.getRight().getColumn(), 
                    name, schemaHelper.getTypeName(node.getLeft().getType()));
            }
            node.setType(type);
            node.getRight().setType(type);
        } else {
            // Check for enum literal access
            String typeName = node.getLeft().getAsString();
            Object type = schemaHelper.resolveEnumTypeName(typeName);
            if ((type != null)) {
                Object enumConstant = schemaHelper.resolveEnumConstant(type, name);
                if (enumConstant == null) {
                    throw JPQLException.invalidEnumLiteral(context.getQueryInfo(),
                    		node.getRight().getLine(), node.getRight().getColumn(), typeName, name);
                }
                node.setEnumConstant(enumConstant);
            } else {
                // left most node is not an identification variable and
                // dot expression does not denote an enum literal access =>
                // unknown identification variable
                throw JPQLException.aliasResolutionException(
                    context.getQueryInfo(), leftMost.getLine(), 
                    leftMost.getColumn(), leftMost.getAsString());
            }
            node.setType(type);
            node.getRight().setType(type);
        }
        return node;
    }
	
	private boolean isDeclaredVariable(Node node, ParseTreeContext context) {
        if (node.isVariableNode()) {
            String name = ((VariableNode)node).getCanonicalVariableName();
            return context.isVariable(name);
        }
        return false;
    }
	
    private void checkDotNodeNavigation(DotNode dotNode, ParseTreeContext context) {
    	// Checks whether the type of the dot node allows a navigation.
    	Node node = dotNode.getLeft();
        Object type = node.getType();   
        if (!schemaHelper.isEntityClass(type) && 
            !schemaHelper.isEmbeddable(type) &&
            !schemaHelper.isEnumType(type)) {
            throw JPQLException.invalidNavigation(
                context.getQueryInfo(), node.getLine(), node.getColumn(),
                dotNode.getAsString(), node.getAsString(), 
                schemaHelper.getTypeName(type));
        }
        // Special check to disallow collection valued relationships
        if (node.isDotNode()) {
            Node left = node.getLeft();
            AttributeNode right = (AttributeNode)node.getRight();
            if (schemaHelper.isCollectionValuedRelationship(
                    left.getType(), right.getAttributeName())) {
                throw JPQLException.invalidCollectionNavigation(
                    context.getQueryInfo(), right.getLine(), right.getColumn(),
                    dotNode.getAsString(), right.getAttributeName());
            }
        }
    }

	public Node visitNode(VariableNode node, AbstractNode parent) {
		String name = node.getCanonicalVariableName();
        if (context.isRangeVariable(name)) {
            String schema = context.schemaForVariable(name);
            node.setType(schemaHelper.resolveSchema(schema));
        } else {
            Node path = context.pathForVariable(name);
            if (path == null) {
                throw JPQLException.aliasResolutionException(
                    context.getQueryInfo(), node.getLine(), node.getColumn(), name);
            } else {
                node.setType(path.getType());
            }
        }
        context.usedVariable(name);
        if (context.isDeclaredInOuterScope(name)) {
            context.registerOuterScopeVariable(name);
        }
        return node;
	}

	public Node visitNode(AttributeNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(ParameterNode node, AbstractNode parent) {
		return node;
	}
	
	public void validateParameter(Node node, Object contextType) {
		if (node.isParameterNode()) {
			ParameterNode parameterNode = (ParameterNode) node;
			context.defineParameterType(parameterNode.getParameterName(), contextType, node.getLine(), node.getColumn());
			node.setType(context.getParameterType(parameterNode.getParameterName()));
		} else if (node.isSqrtNode()) {
			validateParameter(node.getLeft(), contextType);
		} else if (node.isUnaryMinusNode()) {
			validateParameter(node.getLeft(), contextType);
		} else if (node.isLengthNode()) {
			validateParameter(node.getLeft(), context.getTypeHelper().getStringType());
		}
	}
	
	public Node visitNode(AvgNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getDoubleClassType());
		return node;
	}
	
	public Node visitNode(CountNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getLongClassType());
		return node;
	}
	
	public Node visitNode(MaxNode node, AbstractNode parent) {
		node.setType(node.getLeft().getType());
		return node;
	}
	
	public Node visitNode(MinNode node, AbstractNode parent) {
		node.setType(node.getLeft().getType());
		return node;
	}
	
	public Node visitNode(SumNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(calculateReturnType(node.getLeft().getType(), typeHelper));
		return node;
	}
	
    protected Object calculateReturnType(Object argType, TypeHelper helper) {
        Object returnType = null;
        if (helper.isIntegralType(argType)) {
            returnType = helper.getLongClassType();
        } else if (helper.isFloatingPointType(argType)) {
            returnType = helper.getDoubleClassType();
        } else if (helper.isBigIntegerType(argType)) {
            returnType = helper.getBigIntegerType();
        } else if (helper.isBigDecimalType(argType)) {
            returnType = helper.getBigDecimalType();
        }
        return returnType;
    }
    
	public Node visitNode(DivideNode node, AbstractNode parent) {
		if ((node.getLeft() != null) && (node.getRight() != null)) {
            TypeHelper typeHelper = context.getTypeHelper();
            node.setType(typeHelper.extendedBinaryNumericPromotion(
            		node.getLeft().getType(), node.getRight().getType()));
        }
		return node;
	}
	
	public Node visitNode(MinusNode node, AbstractNode parent) {
		if ((node.getLeft() != null) && (node.getRight() != null)) {
            TypeHelper typeHelper = context.getTypeHelper();
            node.setType(typeHelper.extendedBinaryNumericPromotion(
            		node.getLeft().getType(), node.getRight().getType()));
        }
		return node;
	}
	
	public Node visitNode(MultiplyNode node, AbstractNode parent) {
		if ((node.getLeft() != null) && (node.getRight() != null)) {
            TypeHelper typeHelper = context.getTypeHelper();
            node.setType(typeHelper.extendedBinaryNumericPromotion(
            		node.getLeft().getType(), node.getRight().getType()));
        }
		return node;
	}
	
	public Node visitNode(PlusNode node, AbstractNode parent) {
		if ((node.getLeft() != null) && (node.getRight() != null)) {
            TypeHelper typeHelper = context.getTypeHelper();
            node.setType(typeHelper.extendedBinaryNumericPromotion(
            		node.getLeft().getType(), node.getRight().getType()));
        }
		return node;
	}
	
	public Node visitNode(UnaryMinusNode node, AbstractNode parent) {
		node.setType(node.getLeft().getType());
		return node;
	}
	
	public Node visitNode(AbsNode node, AbstractNode parent) {
		node.setType(node.getLeft().getType());
		return node;
	}
	
	public Node visitNode(LengthNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
	    validateParameter(node.getLeft(), typeHelper.getStringType());
	    node.setType(typeHelper.getIntType());
		return node;
	}
	
	public Node visitNode(LocateNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node findIn = node.getFindIn();
        if (findIn != null) {
            validateParameter(findIn, typeHelper.getStringType());
        }
        Node find = node.getFind();
        if (find != null) {
            validateParameter(find, typeHelper.getStringType());
        }
        Node startPosition = node.getStartPosition();
        if (startPosition != null) {
            validateParameter(startPosition, typeHelper.getIntType());
        }
        node.setType(typeHelper.getIntType());
		return node;
	}
	
	public Node visitNode(ModNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getIntType());
            Object type = left.getType();
            if (!typeHelper.isIntegralType(type))
                throw JPQLException.invalidFunctionArgument(
                    context.getQueryInfo(), left.getLine(), left.getColumn(), 
                    "MOD", left.getAsString(), "integral type");
        }
        Node denominator = node.getDenominator();
        if (denominator != null) {
            validateParameter(denominator, typeHelper.getIntType());
            Object denominatorType = denominator.getType();
            if (!typeHelper.isIntegralType(denominatorType))
                throw JPQLException.invalidFunctionArgument(
                    context.getQueryInfo(), denominator.getLine(), denominator.getColumn(), 
                    "MOD", denominator.getAsString(), "integral type");
        }
        node.setType(typeHelper.getIntType());
		return node;
	}
	
	public Node visitNode(SizeNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getIntType());
		return node;
	}
	
	public Node visitNode(SqrtNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
		node.setType(typeHelper.getDoubleType());
		return node;
	}
	
	public Node visitNode(LiteralNode node, AbstractNode parent) {
		return node;
	}
	
	public Node visitNode(DoubleLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getDoubleType());
		return node;
	}
	
	public Node visitNode(FloatLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getFloatType());
		return node;
	}
	
	public Node visitNode(IntegerLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getIntType());
		return node;
	}
	
	public Node visitNode(LongLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getLongType());
		return node;
	}
	
	public Node visitNode(BooleanLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(EscapeNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
		Node left = node.getLeft();
		if (left != null) {
            validateParameter(left, typeHelper.getCharType());
        }
        node.setType(node.getType());
		return node;
	}
	
	public Node visitNode(AndNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
        if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
        
        TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(NotNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getBooleanType());
        }
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(OrNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
        TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(EqualsNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }		
		TypeHelper typeHelper = context.getTypeHelper();
        Object leftType = left.getType();
        Object rightType = right.getType();
        if (typeHelper.isEnumType(leftType) && !typeHelper.isEnumType(rightType)) {
        	if (! typeHelper.isStringType(rightType)) {
	            throw JPQLException.invalidEnumEqualExpression( 
	                context.getQueryInfo(), node.getLine(), node.getColumn(), 
	                typeHelper.getTypeName(leftType), typeHelper.getTypeName(rightType));
        	}
        } else if (typeHelper.isEnumType(rightType) && !typeHelper.isEnumType(leftType)) {
        	if (! typeHelper.isStringType(leftType)) {
	            throw JPQLException.invalidEnumEqualExpression( 
	                context.getQueryInfo(), node.getLine(), node.getColumn(),
	                typeHelper.getTypeName(rightType), typeHelper.getTypeName(leftType));
        	}
        }
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(GreaterThanEqualToNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(GreaterThanNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(NotEqualsNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(LessThanEqualToNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(LessThanNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
		if ((left != null) && (right != null)) {
            validateParameter(left, right.getType());
            validateParameter(right, left.getType());
        }
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(BetweenNode node, AbstractNode parent) {
        Object type = null;
        Node left = node.getLeft();
        if (left != null) {
            type = left.getType();
        }
        Node rightForBetween = node.getRightForBetween(); 
        if (rightForBetween != null) {
            validateParameter(rightForBetween, type);
        }
        Node rightForAnd = node.getRightForAnd();
        if (rightForAnd != null) {
            validateParameter(rightForAnd, type);
        }
        TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(EmptyCollectionComparisonNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(ExistsNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(InNode node, AbstractNode parent) {
        Object leftType = null;
        Node left = node.getLeft();
        if (left != null) {
            leftType = left.getType();
        }
        TypeHelper typeHelper = context.getTypeHelper();
        for (Iterator i = node.getTheObjects().iterator(); i.hasNext();) {
            Node item = (Node)i.next();
            validateParameter(item, leftType);
            Object itemType = item.getType();
            if ((leftType != null) && !typeHelper.isAssignableFrom(leftType, itemType)) {
            	throw JPQLException.invalidExpressionArgument(
                    context.getQueryInfo(), item.getLine(), item.getColumn(),
                    "IN", item.getAsString(), typeHelper.getTypeName(leftType));
            }
        }
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(LikeNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getStringType());
        }
        Node right = node.getRight();
        if (right != null) {
            validateParameter(right, typeHelper.getStringType());
        } 
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(MemberOfNode node, AbstractNode parent) {
		Node left = node.getLeft();
		Node right = node.getRight();
        if (left.isVariableNode() && ((VariableNode)left).isAlias(context)) {
            context.usedVariable(((VariableNode)left).getCanonicalVariableName());
        }
        validateParameter(left, right.getType());
        TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(NullComparisonNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getObjectType());
        }
        node.setType(typeHelper.getBooleanType());
		return node;
	}
	
	public Node visitNode(DateFunctionNode node, AbstractNode parent) {
		node.setType(node.getDateFunctionType());
		return node;
	}
	
	public Node visitNode(ConstructorNode node, AbstractNode parent) {
        String constructorClassName = node.getConstructorClassName();
        Object type = schemaHelper.resolveTypeName(constructorClassName);
        if (type == null) {
            String name = constructorClassName;
            // check for inner classes
            int index = name.lastIndexOf('.');
            if (index != -1) {
                name = name.substring(0, index) + '$' + name.substring(index+1);
                type = schemaHelper.resolveTypeName(name);
            }
        }
        node.setType(type);
		return node;
	}
	
	public Node visitNode(ConcatNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
		Node left = node.getLeft();
		Node right = node.getRight();
        if ((left != null) && (right != null)) {
            validateParameter(left, typeHelper.getStringType());
            validateParameter(right, typeHelper.getStringType());
        }
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(LowerNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
		Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getStringType());
        }
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(SubstringNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getStringType());
        }
        Node startPosition = node.getStartPosition();
        if (startPosition != null) {
            validateParameter(startPosition, typeHelper.getIntType());
        }
        Node stringLength = node.getStringLength();
        if (stringLength != null) {
            validateParameter(stringLength, typeHelper.getIntType());
        }
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(TrimNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getStringType());
        }
        Node trimChar = node.getTrimChar();
        if (trimChar != null) {
            validateParameter(trimChar, typeHelper.getCharType());
        }
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(UpperNode node, AbstractNode parent) {
        TypeHelper typeHelper = context.getTypeHelper();
        Node left = node.getLeft();
        if (left != null) {
            validateParameter(left, typeHelper.getStringType());
        }
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(StringLiteralNode node, AbstractNode parent) {
		TypeHelper typeHelper = context.getTypeHelper();
        node.setType(typeHelper.getStringType());
		return node;
	}
	
	public Node visitNode(AllNode node, AbstractNode parent) {
		Node left = node.getLeft();
        if (left != null) {
            node.setType(left.getType());
        }
		return node;
	}
	
	public Node visitNode(AnyNode node, AbstractNode parent) {
		Node left = node.getLeft();
        if (left != null) {
            node.setType(left.getType());
        }
		return node;
	}
	
	public Node visitNode(SomeNode node, AbstractNode parent) {
		Node left = node.getLeft();
        if (left != null) {
            node.setType(left.getType());
        }
		return node;
	}
	
	public Node visitNode(SubqueryNode node, AbstractNode parent) {
		return node;
	}
	
}	


