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
package icom.jpql.csi;

import icom.QLException;
import icom.jpql.csi.filters.EntityPredicateFactory;
import icom.jpql.csi.filters.PredicateFactoryTable;
import icom.jpql.stack.ConditionalFactor;
import icom.jpql.stack.ConditionalTerm;
import icom.jpql.stack.Expression;
import icom.jpql.stack.NegateExpression;
import icom.jpql.stack.Operand;
import icom.jpql.stack.OrderByItem;
import icom.jpql.stack.PrimaryOperand;
import icom.jpql.stack.SimpleExpression;
import icom.ql.clause.WhereClauseNode;
import icom.ql.clause.select.OrderByClauseNode;
import icom.ql.clause.select.fromdomain.JoinDeclarationNode;
import icom.ql.clause.select.orderby.OrderByItemNode;
import icom.ql.clause.select.orderby.SortDirectionNode;
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
import icom.ql.parser.TypeHelper;
import icom.ql.parser.antlr.JPQLParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import oracle.csi.Category;
import oracle.csi.CollabId;
import oracle.csi.Entity;
import oracle.csi.HeterogeneousFolder;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Label;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.EntityListFilter;
import oracle.csi.filters.Predicate;
import oracle.csi.filters.SortCriteria;
import oracle.csi.projections.Projection;

public class CsiQueryContextImpl implements CsiQueryContext {

	String abstractSchemaName;
	IdentifiableHandle container;
	IdentifiableHandle marker;
	IdentifiableHandle workspace;
	Predicate finalPredicate;
	EntityListFilter<Entity> listFilter;
	
	Stack<Expression> expressionStack;
	SimpleExpression currentExpression;
	Vector<SortCriteria> orderByItems;
	
	int maxResult = -1;
	int startPosition = -1;
	Projection projection = Projection.BASIC;
	Map<String, Object> parameterValues;
	
	TypeHelper helper;
	
	EntityPredicateFactory predicateFactory;
	
	boolean shouldNotHaveMoreThanOneParentPredicate = false;
	boolean shouldNotHaveMoreThanOneCategoryPredicate = false;
	boolean shouldNotHaveMoreThanOneWorkspacePredicate = false;
	
    JPQLParser parser;
    
    public JPQLParser getParser() {
        return parser;
    }
    
    public void setParser(JPQLParser parser) {
        this.parser = parser;
    }
	
	private Set<Predicate> createSet() {
		return new HashSet<Predicate>();
		/*
		return new TreeSet( 
				new Comparator<Predicate>()  {
					
					private static final long serialVersionUID = 1L;
			    	
			    	public int compare(Predicate o1, Predicate o2) {
			    		return (o1 == o2) ? 0 : 1; 
			    	}
			    	
			    	public boolean equals(Object obj) {
			            return this == obj;
			        }
		
			    }
		);
		*/
	}
	
	public CsiQueryContextImpl() {
		helper = TypeHelper.getInstance();
		parameterValues = new HashMap<String, Object>();
		expressionStack = new Stack<Expression>();
		orderByItems = new Vector<SortCriteria>();
		
	}
	
	public void prepareContextForAbstractSchema(String abstractSchemaName) {
		this.abstractSchemaName = abstractSchemaName;
		predicateFactory = PredicateFactoryTable.getInstance().getPredicateFactory(abstractSchemaName);
		listFilter = predicateFactory.getEntityListFilter();
	}
	
	public void prepareContextForAbstractSchema(String firstFromAbstractSchemaName, String selectAbstractSchemaName) {
		this.abstractSchemaName = selectAbstractSchemaName;
		String joinSchemaName = firstFromAbstractSchemaName;
		if (! firstFromAbstractSchemaName.contains("Join")) {
			joinSchemaName = firstFromAbstractSchemaName + "Join" + selectAbstractSchemaName;
		}
		predicateFactory = PredicateFactoryTable.getInstance().getPredicateFactory(joinSchemaName);
		listFilter = predicateFactory.getEntityListFilter();
		
	}

    public void setParameter(String name, Object value) {
    	parameterValues.put(name, value);
    }
    
    public Object getParameter(String name) {
    	return parameterValues.get(name);
    }
    
    public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public void setMaxResults(int maxResult) {
    	this.maxResult = maxResult;
    }

    public void setFirstResult(int startPosition) {	
    	this.startPosition = startPosition;
    }
    
    public List<Object> getResultList(Object cacheContext) {
		if (maxResult >= 0) {
			predicateFactory.setMaxCountInListFilter(maxResult, listFilter);
		}
		predicateFactory.setProjectionInListFilter(projection, listFilter);
		Class<?>[] args = predicateFactory.getMethodSignatureForListMethod(listFilter, this);
		List<Object> list = predicateFactory.invokeListMethod(listFilter, this, args);
		return list;
	}
    
    public IdentifiableHandle getContainer() {
    	return container;
    }
    
    public IdentifiableHandle getWorkspace() {
    	return workspace;
    }
    
    public IdentifiableHandle getMarker() {
    	return marker;
    }
    
    public Node visitNode(WhereClauseNode node, AbstractNode parent) {
    	if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		if (expressionStack.size() != 1) {
			throw new QLException("Not Supported");
		}
		Expression lastExpression = expressionStack.pop();
		if (lastExpression instanceof ConditionalTerm) {
			ConditionalTerm term = (ConditionalTerm) lastExpression;
			Set<Predicate> matchAnyPredicateList = createSet();
			for (Expression expr : term.expressions) {
				matchAnyPredicateList.add((Predicate) expr.predicate);
			}
			term.predicate = predicateFactory.createMatchAnyPredicate(listFilter, matchAnyPredicateList);
		} else if (lastExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = (ConditionalFactor) lastExpression;
			Set<Predicate> matchAllPredicateList = createSet();
			for (Expression expr : factor.expressions) {
				if (expr.predicate != null) {
					matchAllPredicateList.add((Predicate) expr.predicate);
				}
			}
			if (matchAllPredicateList.size() == 1) {
				factor.predicate = matchAllPredicateList.iterator().next();
			} else {
				factor.predicate = predicateFactory.createMatchAllPredicate(listFilter, matchAllPredicateList);
			}
		}
		finalPredicate = (Predicate) lastExpression.predicate;	
		predicateFactory.setPredicateInListFilter(finalPredicate, listFilter);
		return node;
	}
    
    public Node visitNode(AndNode node, AbstractNode parent) {
    	if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		if (expressionStack.size() < 2) {
			throw new QLException("Not Supported");
		}
		
		Expression currentExpression = expressionStack.pop();
		Expression lastExpression = expressionStack.pop();
		
		if (lastExpression instanceof ConditionalFactor && currentExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = new ConditionalFactor(this);
			factor.expressions = new Vector<Expression>();
			factor.expressions.addAll(((ConditionalFactor)lastExpression).expressions);
			if (lastExpression.shouldNotHaveInterveningOrNodes) {
				factor.shouldNotHaveInterveningOrNodes = true;
			}
			factor.expressions.addAll(((ConditionalFactor)currentExpression).expressions);
			if (currentExpression.shouldNotHaveInterveningOrNodes) {
				factor.shouldNotHaveInterveningOrNodes = true;
			}
			expressionStack.push(factor);
			return node;
		} 

		if (lastExpression instanceof ConditionalTerm) {
			ConditionalTerm term = (ConditionalTerm) lastExpression;
			Set<Predicate> matchAnyPredicateList = createSet();
			for (Expression expr : term.expressions) {
				if (expr.predicate != null) {
					matchAnyPredicateList.add((Predicate) expr.predicate);
				}
			}
			if (matchAnyPredicateList.size() == 1) {
				term.predicate = matchAnyPredicateList.iterator().next();
			} else {
				term.predicate = predicateFactory.createMatchAnyPredicate(listFilter, matchAnyPredicateList);
			}
		}
		
		if (currentExpression instanceof ConditionalTerm) {
			ConditionalTerm term = (ConditionalTerm) currentExpression;
			Set<Predicate> matchAnyPredicateList = createSet();
			for (Expression expr : term.expressions) {
				if (expr.predicate != null) {
					matchAnyPredicateList.add((Predicate) expr.predicate);
				}
			}
			if (matchAnyPredicateList.size() == 1) {
				term.predicate = matchAnyPredicateList.iterator().next();
			} else {
				term.predicate = predicateFactory.createMatchAnyPredicate(listFilter, matchAnyPredicateList);
			}
		}
		
		if (lastExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = (ConditionalFactor) lastExpression;
			factor.expressions.add(currentExpression);
			if (currentExpression.shouldNotHaveInterveningOrNodes) {
				factor.shouldNotHaveInterveningOrNodes = true;
			}
			expressionStack.push(factor);
			return node;
		}
		
		if (currentExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = new ConditionalFactor(this);
			factor.expressions = new Vector<Expression>();
			factor.expressions.add(lastExpression);
			if (lastExpression.shouldNotHaveInterveningOrNodes) {
				factor.shouldNotHaveInterveningOrNodes = true;
			}
			factor.expressions.addAll(((ConditionalFactor)currentExpression).expressions);
			if (currentExpression.shouldNotHaveInterveningOrNodes) {
				factor.shouldNotHaveInterveningOrNodes = true;
			}
			expressionStack.push(factor);
			return node;
		}
				
		ConditionalFactor factor = new ConditionalFactor(this);
		factor.expressions = new Vector<Expression>();
		if (lastExpression.shouldNotHaveInterveningOrNodes) {
			factor.shouldNotHaveInterveningOrNodes = true;
		}
		if (lastExpression.predicate != null) {
			factor.expressions.add(lastExpression);	
		}
		if (currentExpression.shouldNotHaveInterveningOrNodes) {
			factor.shouldNotHaveInterveningOrNodes = true;
		}
		if (currentExpression.predicate != null) {
			factor.expressions.add(currentExpression);	
		}
		expressionStack.push(factor);
		return node;
	}
	
	public Node visitNode(OrNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		if (expressionStack.size() < 2) {
			throw new QLException("Not Supported");
		}
		
		Expression currentExpression = expressionStack.pop();
		Expression lastExpression = expressionStack.pop();
		
		if (lastExpression instanceof ConditionalTerm && currentExpression instanceof ConditionalTerm) {
			ConditionalTerm term = (ConditionalTerm) lastExpression;
			term.expressions.addAll(((ConditionalTerm)currentExpression).expressions);
			expressionStack.push(term);
			return node;
		}
		
		if (lastExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = (ConditionalFactor) lastExpression;
			if (factor.shouldNotHaveInterveningOrNodes) {
				// the parent, category, and workspace equality predicates should not appear in the OR terms
				throw new QLException("Not Supported");
			}
			Set<Predicate> matchAllPredicateList = createSet();
			for (Expression expr : factor.expressions) {
				if (expr.predicate != null) {
					matchAllPredicateList.add((Predicate) expr.predicate);
				}
			}
			if (matchAllPredicateList.size() == 1) {
				factor.predicate = matchAllPredicateList.iterator().next();
			} else {
				factor.predicate = predicateFactory.createMatchAllPredicate(listFilter, matchAllPredicateList);
			}
		}
		
		if (currentExpression instanceof ConditionalFactor) {
			ConditionalFactor factor = (ConditionalFactor) currentExpression;
			if (factor.shouldNotHaveInterveningOrNodes) {
				// the parent, category, and workspace equality predicates should not appear in the OR terms
				throw new QLException("Not Supported");
			}
			Set<Predicate> matchAllPredicateList = createSet();
			for (Expression expr : factor.expressions) {
				if (expr.predicate != null) {	
					matchAllPredicateList.add((Predicate) expr.predicate);
				}
			}
			if (matchAllPredicateList.size() == 1) {
				factor.predicate = matchAllPredicateList.iterator().next();
			} else {
				factor.predicate = predicateFactory.createMatchAllPredicate(listFilter, matchAllPredicateList);
			}
		}
		
		if (currentExpression instanceof ConditionalTerm) {
			ConditionalTerm term = new ConditionalTerm(this);
			term.expressions = new Vector<Expression>();
			term.expressions.add(lastExpression);
			term.expressions.addAll(((ConditionalTerm)currentExpression).expressions);
			expressionStack.push(term);
			return node;
		}
		
		if (lastExpression instanceof ConditionalTerm) {
			ConditionalTerm term = (ConditionalTerm) lastExpression;
			term.expressions.add(currentExpression);
			expressionStack.push(term);
			return node;
		}
		
		ConditionalTerm term = new ConditionalTerm(this);
		term.expressions = new Vector<Expression>();
		if (lastExpression.shouldNotHaveInterveningOrNodes) {
			// the parent, category, and workspace equality predicates should not appear in the OR terms
			throw new QLException("Not Supported");
		}
		if (lastExpression.predicate != null) {
			term.expressions.add(lastExpression);
		}
		if (currentExpression.shouldNotHaveInterveningOrNodes) {
			// the parent, category, and workspace equality predicates should not appear in the OR terms
			throw new QLException("Not Supported");
		}
		if (currentExpression.predicate != null) {
			term.expressions.add(currentExpression);
		}
		expressionStack.push(term);
		return node;
	}
	
	public Node visitNode(NotNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		if (expressionStack.isEmpty()) {
			throw new QLException("Not Supported");
		}
		Expression expression = expressionStack.pop();
		Predicate predicate = predicateFactory.createNotPredicate(listFilter, (Predicate) expression.predicate);
		NegateExpression negateExpression = new NegateExpression(this);
		negateExpression.expression = expression;
		negateExpression.predicate = predicate;
		expressionStack.push(negateExpression);
		return node;
	}
    
    public Node visitNode(ComparisonExpressionNode node, AbstractNode parent) {
    	throw new QLException("Not Supported");
    }
    
	public Node visitNode(EqualsNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = null;
		if (currentExpression.primary.simpleAttributeName.equalsIgnoreCase("parent") &&
				predicateFactory.hasContainerAsArgumentOfListMethod()) { 
			if (shouldNotHaveMoreThanOneParentPredicate) {
				throw new QLException("Not Supported");
			}
			if (currentExpression.firstOperand.value instanceof IdentifiableHandle) {
				container = (IdentifiableHandle) currentExpression.firstOperand.value;
			} else if (currentExpression.firstOperand.value instanceof String) {
				String str = (String) currentExpression.firstOperand.value;
		    	CollabId collabId = CollabId.parseCollabId(str);
		    	container = EntityUtils.getInstance().createHandle(collabId);
			}
			currentExpression.shouldNotHaveInterveningOrNodes = true;
			shouldNotHaveMoreThanOneParentPredicate = true;
		} else if (currentExpression.primary.simpleAttributeName.equalsIgnoreCase("category") &&
				predicateFactory.hasMarkerAsArgumentOfListMethod()) { 
			if (shouldNotHaveMoreThanOneCategoryPredicate) {
				throw new QLException("Not Supported");
			}
			if (currentExpression.firstOperand.value instanceof IdentifiableHandle) {
				marker = (IdentifiableHandle) currentExpression.firstOperand.value;
			} else if (currentExpression.firstOperand.value instanceof String) {
				String str = (String) currentExpression.firstOperand.value;
		    	CollabId collabId = CollabId.parseCollabId(str);
		    	marker = EntityUtils.getInstance().createHandle(collabId);
			}
			currentExpression.shouldNotHaveInterveningOrNodes = true;
			shouldNotHaveMoreThanOneCategoryPredicate = true;
		} else if (currentExpression.primary.simpleAttributeName.equalsIgnoreCase("label") &&
				predicateFactory.hasMarkerAsArgumentOfListMethod()) { 
			if (shouldNotHaveMoreThanOneCategoryPredicate) {
				throw new QLException("Not Supported");
			}
			if (currentExpression.firstOperand.value instanceof IdentifiableHandle) {
				marker = (IdentifiableHandle) currentExpression.firstOperand.value;
			} else if (currentExpression.firstOperand.value instanceof String) {
				String str = (String) currentExpression.firstOperand.value;
		    	CollabId collabId = CollabId.parseCollabId(str);
		    	marker = EntityUtils.getInstance().createHandle(collabId);
			}
			currentExpression.shouldNotHaveInterveningOrNodes = true;
			shouldNotHaveMoreThanOneCategoryPredicate = true;
		} else if (currentExpression.primary.simpleAttributeName.equalsIgnoreCase("workspace") &&
				predicateFactory.hasWorkspaceAsArgumentOfListMethod()) { 
			if (shouldNotHaveMoreThanOneWorkspacePredicate) {
				throw new QLException("Not Supported");
			}
			if (currentExpression.firstOperand.value instanceof IdentifiableHandle) {
				workspace = (IdentifiableHandle) currentExpression.firstOperand.value;
			} else if (currentExpression.firstOperand.value instanceof String) {
				String str = (String) currentExpression.firstOperand.value;
		    	CollabId collabId = CollabId.parseCollabId(str);
		    	workspace = EntityUtils.getInstance().createHandle(collabId);
			}
			currentExpression.shouldNotHaveInterveningOrNodes = true;
			shouldNotHaveMoreThanOneWorkspacePredicate = true;
		} else {
			predicate = predicateFactory.createEqualsPredicate(listFilter, currentExpression);
		}
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(NotEqualsNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createNotEqualsPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(LikeNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createLikePredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(BetweenNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.secondOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createBetweenPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(GreaterThanEqualToNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createGreaterThanOrEqualsPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(LessThanEqualToNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createLessThanOrEqualsPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(GreaterThanNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createGreaterThanPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(LessThanNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createLessThanPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(MemberOfNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createMemberOfPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(InNode node, AbstractNode parent) {
		if (currentExpression == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.primary == null) {
			throw new QLException("Not Supported");
		} else if (currentExpression.firstOperand == null) {
			throw new QLException("Not Supported");
		}
		Predicate predicate = predicateFactory.createInPredicate(listFilter, currentExpression);
		currentExpression.predicate = predicate;
		expressionStack.push(currentExpression);
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(JoinDeclarationNode node, AbstractNode parent) {
		currentExpression = null;
		return node;
	}
	
	public Node visitNode(VariableNode node, AbstractNode parent) {
		if (parent instanceof EqualsNode) {
			Object type = node.getType();
			if (Category.class.isAssignableFrom((Class<?>) type)) {
				if (currentExpression == null) {
					currentExpression = new SimpleExpression(this);
				}
				PrimaryOperand primary = new PrimaryOperand(); 
				primary.simpleAttributeName = "category";
				if (currentExpression.firstOperand == null) {
					primary.isLeftNode = true;
				} else {
					primary.isLeftNode = false;
				}
				currentExpression.primary = primary;
			} else if (Label.class.isAssignableFrom((Class<?>) type)) {
				if (currentExpression == null) {
					currentExpression = new SimpleExpression(this);
				}
				PrimaryOperand primary = new PrimaryOperand(); 
				primary.simpleAttributeName = "label";
				if (currentExpression.firstOperand == null) {
					primary.isLeftNode = true;
				} else {
					primary.isLeftNode = false;
				}
				currentExpression.primary = primary;
			} else if (HeterogeneousFolder.class.isAssignableFrom((Class<?>) type)) {
				if (currentExpression == null) {
					currentExpression = new SimpleExpression(this);
				}
				PrimaryOperand primary = new PrimaryOperand(); 
				primary.simpleAttributeName = "parent";
				if (currentExpression.firstOperand == null) {
					primary.isLeftNode = true;
				} else {
					primary.isLeftNode = false;
				}
				currentExpression.primary = primary;
			}  
		}
		return node;
	}
	
	public Node visitNode(AttributeNode node, AbstractNode parent) {
		if (currentExpression != null) {
			if (currentExpression.primary != null) {
				if (currentExpression.primary.pathExpressionAttributeNames == null) {
					currentExpression.primary.pathExpressionAttributeNames = new Vector<String>();
					currentExpression.primary.pathExpressionAttributeNames.add(currentExpression.primary.simpleAttributeName);
				}
				currentExpression.primary.pathExpressionAttributeNames.add(node.getAttributeName());
				return node;
			}
		} else {
			currentExpression = new SimpleExpression(this);
		}
		PrimaryOperand primary = new PrimaryOperand(); 
		primary.simpleAttributeName = node.getAttributeName();
		if (currentExpression.firstOperand == null) {
			primary.isLeftNode = true;
		} else {
			primary.isLeftNode = false;
		}
		currentExpression.primary = primary;
		return node;
	}
	
	public Node visitNode(ParameterNode node, AbstractNode parent) {
		if (currentExpression == null) {
			currentExpression = new SimpleExpression(this);
		}
		Operand operand = new Operand();
		operand.name = node.getParameterName();
		operand.value = getParameter(node.getParameterName());
		operand.type = node.getType();
		if (currentExpression.firstOperand == null) {
			currentExpression.firstOperand = operand;
		} else if (currentExpression.secondOperand == null) {
			currentExpression.secondOperand = operand;
		} else if (currentExpression.thirdOperand == null) {
			currentExpression.thirdOperand = operand;
		} else {
			// the in operator supports extended arguments
			if (currentExpression.extendedOperands == null) {
				currentExpression.extendedOperands = new Vector<Operand>();
			}
			currentExpression.extendedOperands.add(operand);
		}
		return node;
	}
	
	public Node visitNode(LiteralNode node, AbstractNode parent) {
		if (currentExpression == null) {
			currentExpression = new SimpleExpression(this);
		}
		Operand operand = new Operand();
		operand.name = "literal";
		operand.value = node.getLiteral();
		operand.type = node.getType();
		operand.isLiteral = true;
		if (currentExpression.firstOperand == null) {
			currentExpression.firstOperand = operand;
		} else if (currentExpression.secondOperand == null) {
			currentExpression.secondOperand = operand;
		} else if (currentExpression.thirdOperand == null) {
			currentExpression.thirdOperand = operand;
		} else {
			// the in operator supports extended arguments
			if (currentExpression.extendedOperands == null) {
				currentExpression.extendedOperands = new Vector<Operand>();
			}
			currentExpression.extendedOperands.add(operand);
		}
		return node;
	}
    
	public Node visitNode(AggregateNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		return node;
	}
	
	public Node visitNode(FunctionalExpressionNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		return node;
	}
	
	public Node visitNode(BinaryArithmeticOperatorNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		return node;
	}
	
	public Node visitNode(UnaryMinusNode node, AbstractNode parent) {
		if (currentExpression != null) {
			throw new QLException("Not Supported");
		}
		return node;
	}
	
    public Node visitNode(Node node, AbstractNode parent) {
    	return node;
    }
    
    public Node visitNode(OrderByClauseNode node, AbstractNode parent) {
    	if (orderByItems == null) {
			throw new QLException("Not Supported");
		}
    	SortCriteria sortCriteria = null;
		if (orderByItems.size() == 1) {
			sortCriteria = orderByItems.firstElement();
		} else if (orderByItems.size() > 1) {
			sortCriteria = predicateFactory.createSortCriteria(listFilter, orderByItems);
		} else {
			throw new QLException("Not Supported");
		}
		predicateFactory.setSortCriteriaInListFilter(sortCriteria, listFilter);
		return node;
	}
    
    public Node visitNode(OrderByItemNode node, AbstractNode parent) {
    	OrderByItem item = new OrderByItem();
    	item.simpleAttributeName = node.getOrderByItem().resolveAttribute();
    	SortDirectionNode dir = node.getDirection();
    	if (SortDirectionNode.Ascending == dir.getSortDirection()) {
    		item.ascending = true;
    	} else {
    		item.ascending = false;
    	}
    	SortCriteria sortCriteria = predicateFactory.createSortCriteria(listFilter, item);
    	orderByItems.add(sortCriteria);
    	return node;
	}

}
