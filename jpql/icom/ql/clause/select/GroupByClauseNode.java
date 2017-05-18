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
package icom.ql.clause.select;

import icom.ql.ParseTreeVisitor;
import icom.ql.clause.ClauseNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/*
derived from

select_statement ::= select_clause from_clause [where_clause] [groupby_clause] [having_clause] [orderby_clause]
*/

/*
groupby_clause ::= GROUP BY groupby_item {, groupby_item}*

groupby_item ::= single_valued_path_expression | identification_variable

having_clause ::= HAVING conditional_expression

The GROUP BY construct enables the aggregation of values according to a set of properties. The HAVING
construct enables conditions to be specified that further restrict the query result. Such conditions
are restrictions upon the groups.

If a query contains both a WHERE clause and a GROUP BY clause, the effect is that of first applying
the where clause, and then forming the groups and filtering them according to the HAVING clause. The
HAVING clause causes those groups to be retained that satisfy the condition of the HAVING clause.
The requirements for the SELECT clause when GROUP BY is used follow those of SQL: namely, any
item that appears in the SELECT clause (other than as an argument to an aggregate function) must also
appear in the GROUP BY clause. In forming the groups, null values are treated as the same for grouping
purposes.

Grouping by an entity is permitted. In this case, the entity must contain no serialized state fields or
lob-valued state fields. The HAVING clause must specify search conditions over the grouping items or 
aggregate functions that apply to grouping items.

If there is no GROUP BY clause and the HAVING clause is used, the result is treated as a single group,
and the select list can only consist of aggregate functions.

     SELECT s.template.name, COUNT(s)
          FROM Subscription s
          GROUP BY s.template
          HAVING COUNT(s.template.name) > 10

     SELECT s.template.name, count(e)
          FROM Entity e JOIN Subscription s
          WHERE s.attachedTo = e AND
                e.scope = :workspace
          GROUP BY s.template
          HAVING s.template IN (SELECT t FROM SubscriptionTemplate t WHERE t.createdBy = :user)

*/

public class GroupByClauseNode extends ClauseNode {

    List groupByItems = null;

    public GroupByClauseNode() {
        super();
    } 

    public List getGroupByItems() {
        if (groupByItems == null) {
            setGroupByItems(new Vector());
        }
        return groupByItems;
    }

    public void setGroupByItems(List newItems) {
        groupByItems = newItems;
    }

    private void addGroupByItem(Object theNode) {
        getGroupByItems().add(theNode);
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	for (int i = 0; i < groupByItems.size(); i++) {
    		Node item = (Node)groupByItems.get(i);
    		Node newItem = item.accept(visitor, this);
    		if (newItem != item) {
    			groupByItems.set(i, newItem);
            }
    	}
        return visitor.visitNode(this, parent);
    }
    
    /** 
     * Get the string representation of this node. 
     */
    public String getAsString() {
        StringBuffer repr = new StringBuffer();
        for (Iterator i = groupByItems.iterator(); i.hasNext(); ) {
            Node expr = (Node)i.next();
            if (repr.length() > 0) {
                repr.append(", ");
            }
            repr.append(expr.getAsString());
        }
        return "GROUP BY " + repr.toString();
    }
    
}
