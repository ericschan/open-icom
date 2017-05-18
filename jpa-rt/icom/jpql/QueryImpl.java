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
package icom.jpql;

import icom.ql.QueryContext;
import icom.ql.SchemaHelper;
import icom.ql.parser.ParseTreeContext;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TransactionRequiredException;

public class QueryImpl extends AbstractQueryImpl {

	public QueryImpl(String queryText, SchemaHelper schemaHelper, QueryContext queryContext) {
		super(queryText, schemaHelper, queryContext);
	}
	
    /**
     * Bind an instance of java.util.Date to a named parameter.
     * @param name
     * @param value
     * @param temporalType
     * (java.sql.Date) DATE, (java.sql.Time) TIME, (java.sql.Timestamp) TIMESTAMP
     * @return the same query instance
     * @throws IllegalArgumentException if parameter name does not
     *    correspond to parameter in query string
     */
    public Query setParameter(String name, Date value, TemporalType temporalType) {
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		queryContext.setParameter(name, value);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this;
    }

    /**
     * Bind an instance of java.util.Calendar to a named parameter.
     * @param name
     * @param value
     * @param temporalType
     * @return the same query instance
     * @throws IllegalArgumentException if parameter name does not
     *    correspond to parameter in query string
     */
    public Query setParameter(String name, Calendar value, TemporalType temporalType) {
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		queryContext.setParameter(name, value);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this;
    }
    
    /**
     * Set the maximum number of results to retrieve.
     * @param maxResult
     * @return the same query instance
     * @throws IllegalArgumentException if argument is negative
     */
    public Query setMaxResults(int maxResult) {
    	queryContext.setMaxResults(maxResult);
    	return this;
    }

    /**
     * Set the position of the first result to retrieve.
     * @param startPosition the start position of the first result, numbered from 0
     * @return the same query instance
     * @throws IllegalArgumentException if argument is negative
     */
    public Query setFirstResult(int startPosition) {	
    	queryContext.setFirstResult(startPosition);
    	return this;
    }

    /**
     * Set an implementation-specific hint.
     * If the hint name is not recognized, it is silently ignored.
     * @param hintName
     * @param value
     * @return the same query instance
     * @throws IllegalArgumentException if the second argument is not
     *    valid for the implementation
     */
    public Query setHint(String hintName, Object value) {
    	return this;
    }

    /**
     * Bind an argument to a named parameter.
     * @param name the parameter name
     * @param value
     * @return the same query instance
     * @throws IllegalArgumentException if parameter name does not
     *    correspond to parameter in query string
     *    or argument is of incorrect type
     */
    public Query setParameter(String name, Object value) {
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		Object type = treeContext.getParameterType(name);
    		Object resolvedValue = schemaHelper.resolveParameterValueType(cacheContext, value, type);
    		queryContext.setParameter(name, resolvedValue);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this;
    }

    /**
     * Bind an argument to a positional parameter.
     * @param position
     * @param value
     * @return the same query instance
     * @throws IllegalArgumentException if position does not
     *    correspond to positional parameter of query
     *    or argument is of incorrect type
     */
    public Query setParameter(int position, Object value) {
    	String name = "?" + Integer.toString(position);
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		Object type = treeContext.getParameterType(name);
    		Object resolvedValue = schemaHelper.resolveParameterValueType(cacheContext, value, type);
    		queryContext.setParameter(name, resolvedValue);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this;
    }

    /**
     * Bind an instance of java.util.Date to a positional parameter.
     * @param position
     * @param value
     * @param temporalType
     * @return the same query instance
     * @throws IllegalArgumentException if position does not
     *    correspond to positional parameter of query
     */
    public Query setParameter(int position, Date value, TemporalType temporalType) {
    	String name = "?" + Integer.toString(position);
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		queryContext.setParameter(name, value);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this;
    }

    /**
     * Bind an instance of java.util.Calendar to a positional parameter.
     * @param position
     * @param value
     * @param temporalType
     * @return the same query instance
     * @throws IllegalArgumentException if position does not
     *    correspond to positional parameter of query
     */
    public Query setParameter(int position, Calendar value, TemporalType temporalType) {
    	String name = "?" + Integer.toString(position);
    	ParseTreeContext treeContext = tree.getContext();
    	if (treeContext.getParameterNames().contains(name)) {
    		queryContext.setParameter(name, value);
    	} else {
    		throw new IllegalArgumentException("Invalid parameter name " + name);
    	}
    	return this; 
    }

    /**
     * Set the flush mode type to be used for the query execution.
     * The flush mode type applies to the query regardless of the
     * flush mode type in use for the entity manager.
     * @param flushMode
     */
    public Query setFlushMode(FlushModeType flushMode) {
    	return this;
    }
    
    /**
     * Execute a SELECT query that returns a single result.
     * @return the result
     * @throws NoResultException if there is no result
     * @throws NonUniqueResultException if more than one result
     * @throws IllegalStateException if called for a Java 
     *    Persistence query language UPDATE or DELETE statement
     */
    public Object getSingleResult() {
    	ParseTreeInterpreter interpreter = new ParseTreeInterpreter(tree.getContext(), queryContext);
    	tree.accept(interpreter, this);
    	return null; // TODO
    }

    /**
     * Execute an update or delete statement.
     * @return the number of entities updated or deleted
     * @throws IllegalStateException if called for a Java 
     *    Persistence query language SELECT statement
     * @throws TransactionRequiredException if there is
     *    no transaction
     */
    public int executeUpdate() {
    	return 0;
    }
	
}
