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

import java.util.List;
import java.util.Vector;


/*
derived from

select_statement ::= select_clause from_clause [where_clause] [groupby_clause] [having_clause] [orderby_clause]
*/


/*
orderby_clause ::= ORDER BY orderby_item {, orderby_item}*

orderby_item ::= state_field_path_expression [ASC | DESC]

The ORDER BY clause allows the objects or values that are returned by the query to be ordered.

When the ORDER BY clause is used in a query, each element of the SELECT clause of the query must
be one of the following:

   1. an identification variable x, optionally denoted as OBJECT(x)
   2. a single_valued_association_path_expression
   3. a state_field_path_expression

In the first two cases, each orderby_item must be an orderable state-field of the entity abstract schema
type value returned by the SELECT clause. In the third case, the orderby_item must evaluate to the same
state-field of the same entity abstract schema type as the state_field_path_expression in the SELECT
clause.

For example, the first two queries below are legal, but the third and fourth are not.

     SELECT g
          FROM Group g, IN(g.memberActors) m
          WHERE m.id = :uid
          ORDER BY g.name

     SELECT g.name, g.description
          FROM Group g, IN(g.memberActors) m
          WHERE m.id = :uid
          ORDER BY g.name
          
The following two queries are not legal because the orderby_item is not reflected in the SELECT clause
of the query.

     SELECT g.description
          FROM Group g, IN(g.memberActors) m
          WHERE m.id = :uid
          ORDER BY g.name

     SELECT OBJECT(g.scope)
          FROM Group g, IN(g.memberActors) m
          WHERE m.id = :uid
          ORDER BY g.name

*/

public class OrderByClauseNode extends ClauseNode {

    List orderByItems = null;

    public OrderByClauseNode() {
        super();
    }

    public List getOrderByItems() {
        if (orderByItems == null) {
            setOrderByItems(new Vector());
        }
        return orderByItems;
    }

    public void setOrderByItems(List newItems) {
        orderByItems = newItems;
    }
    
    private void addOrderByItem(Object theNode) {
        getOrderByItems().add(theNode);
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	for (int i = 0; i < orderByItems.size(); i++) {
    		Node item = (Node)orderByItems.get(i);
    		Node newItem = item.accept(visitor, this);
    		if (newItem != item) {
    			orderByItems.set(i, newItem);
            }
    	}
        return visitor.visitNode(this, parent);
    }

}
