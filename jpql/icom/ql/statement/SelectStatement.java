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
package icom.ql.statement;

import icom.ql.ParseTreeVisitor;
import icom.ql.clause.WhereClauseNode;
import icom.ql.clause.select.FromClauseNode;
import icom.ql.clause.select.GroupByClauseNode;
import icom.ql.clause.select.HavingClauseNode;
import icom.ql.clause.select.OrderByClauseNode;
import icom.ql.clause.select.SelectClauseNode;
import icom.ql.parser.AbstractNode;

public class SelectStatement extends Statement {
	
	private SelectClauseNode selectClauseNode;
    private FromClauseNode fromClauseNode;
    private WhereClauseNode whereClauseNode;
    private OrderByClauseNode orderByClauseNode = null;
    private GroupByClauseNode groupByClauseNode = null;
    private HavingClauseNode havingClauseNode = null;
    
    public SelectStatement() {
        super();
    }
    
    public SelectStatement accept(ParseTreeVisitor visitor, AbstractNode parent) {
        if (fromClauseNode != null) {
            fromClauseNode.accept(visitor, this);
        }
        selectClauseNode.accept(visitor, this);
        if (whereClauseNode != null) {
            whereClauseNode.accept(visitor, this);
        }
        if (hasOrderBy()) {
            orderByClauseNode.accept(visitor, selectClauseNode);
        }
        if (hasGroupBy()) {
            groupByClauseNode.accept(visitor, selectClauseNode);
        }
        if (hasHaving()) {
            havingClauseNode.accept(visitor, groupByClauseNode);
        }
        return visitor.visitNode(this, parent);
    }
    
    public SelectClauseNode getQueryClauseNode() {
        return selectClauseNode;
    }
    
    public void setQueryClauseNode(SelectClauseNode newQueryNode) {
        selectClauseNode = newQueryNode;
    }

    public FromClauseNode getFromClauseNode() {
        return fromClauseNode;
    }
    
    public void setFromClauseNode(FromClauseNode fromClauseNode) {
        this.fromClauseNode = fromClauseNode;
    }

    public WhereClauseNode getWhereClauseNode() {
        return whereClauseNode;
    }

    public void setWhereClauseNode(WhereClauseNode newWhereNode) {
        whereClauseNode = newWhereNode;
    }
    
    public GroupByClauseNode getGroupByClauseNode() {
        return groupByClauseNode;
    }

    public void setGroupByClauseNode(GroupByClauseNode newGroupByNode) {
        groupByClauseNode = newGroupByNode;
    }
    
    public HavingClauseNode getHavingClauseNode() {
        return havingClauseNode;
    }

    public void setHavingClauseNode(HavingClauseNode newHavingNode) {
        havingClauseNode = newHavingNode;
    }

    public OrderByClauseNode getOrderByClauseNode() {
        return orderByClauseNode;
    }
    
    public void setOrderByClauseNode(OrderByClauseNode newOrderByNode) {
        orderByClauseNode = newOrderByNode;
    }    

    public boolean hasOrderBy() {
        return getOrderByClauseNode() != null;
    }

    public boolean hasGroupBy() {
        return getGroupByClauseNode() != null;
    }

    public boolean hasHaving() {
        return getHavingClauseNode() != null;
    }
    
    public boolean isSelectStatement() {
		return true;
	}

}
