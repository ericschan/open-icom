/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */
package icom.jpql;

import icom.ql.JPQLException;
import icom.ql.JPQLParseTree;
import icom.ql.QueryContext;
import icom.ql.SchemaHelper;
import icom.ql.clause.select.SelectClauseNode;
import icom.ql.expression.path.VariableNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.ParseTreeContext;
import icom.ql.parser.antlr.JPQLParser;
import icom.ql.statement.SelectStatement;
import icom.ql.statement.Statement;

import javax.persistence.Query;

import java.util.List;

public abstract class AbstractQueryImpl extends AbstractNode implements Query {
	
	protected JPQLParseTree tree = null;
	protected QueryContext queryContext = null;
	protected SchemaHelper schemaHelper;
	protected Object cacheContext = null;
	
	String abstractSchemaName;
	
	public AbstractQueryImpl(String queryText, SchemaHelper schemaHelper, QueryContext queryContext) {
		this.schemaHelper = schemaHelper;
		this.queryContext = queryContext;
		tree = JPQLParser.buildParseTree(queryText, queryContext);
	}
	
	protected AbstractQueryImpl() {
		
	}
	
	public void setCacheContext(Object cacheContext) {
		this.cacheContext = cacheContext;
	}
	
	public void validate() {
		tree.validate(schemaHelper);
	}

	/**
     * Execute a SELECT query and return the query results
     * as a List.
     * @return a list of the results
     * @throws IllegalStateException if called for a Non-Select statement
     */   
    public List<Object> getResultList() {
    	Statement statement = tree.getStatement();
    	if (statement.isSelectStatement()) {
    		SelectStatement selectStatement = (SelectStatement) statement;
    		SelectClauseNode selectClause = selectStatement.getQueryClauseNode();
			if (selectClause.getSelectExpressions().size() == 1) { 
				VariableNode node = (VariableNode) selectClause.getSelectExpressions().get(0);
    			Class<Object> clazz = (Class<Object>) node.getType();
    			abstractSchemaName = clazz.getSimpleName();
    			String firstFromAbstractSchemaName = selectStatement.getFromClauseNode().getFirstAbstractSchemaName();
    			if (! firstFromAbstractSchemaName.equalsIgnoreCase(abstractSchemaName)) {
    				queryContext.prepareContextForAbstractSchema(firstFromAbstractSchemaName, abstractSchemaName);
    			} else {
    				queryContext.prepareContextForAbstractSchema(abstractSchemaName);
    			}
			} else {
				throw new RuntimeException("Only queries with one select expression are supported");
			}
    		
	    	ParseTreeInterpreter interpreter = new ParseTreeInterpreter(tree.getContext(), queryContext);
	    	try {
	    	    tree.accept(interpreter, this);
	    	} catch (JPQLException ex) {
	    	    ex.printFullStackTrace();
	    	    throw ex;
	    	}
	    	List<Object> list = queryContext.getResultList(cacheContext);
	    	List<Object> resolvedList = schemaHelper.resolveResultList(queryContext, cacheContext, list);
	    	return resolvedList;
    	} else {
    		throw new IllegalStateException("Can only execute SELECT statement using this method");
    	}
    }
    
    public void setParameterByName(String name, Object value) {
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		Object type = treeContext.getParameterType(name);
    		Object resolvedValue = schemaHelper.resolveParameterValueType(cacheContext, value, type);
    		queryContext.setParameter(name, resolvedValue);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    }
    
    public void setParameterByPosition(int position, Object value) {
    	String name = "?" + Integer.toString(position);
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		Object type = treeContext.getParameterType(name);
    		Object resolvedValue = schemaHelper.resolveParameterValueType(cacheContext, value, type);
    		queryContext.setParameter(name, resolvedValue);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    }

    public Query setMaxResults(int maxResults) {
      queryContext.setMaxResults(maxResults);
      return this;
    }
    
    public Query setFirstResult(int startPosition) {
      queryContext.setFirstResult(startPosition);
      return this;
    }
}
