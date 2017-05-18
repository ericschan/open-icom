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
import icom.ql.clause.QueryClauseNode;
import icom.ql.expression.path.AttributeNode;
import icom.ql.expression.path.DotNode;
import icom.ql.expression.path.VariableNode;
import icom.ql.expression.select.ConstructorNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.GenerationContext;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;
import icom.ql.statement.SelectStatement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/*
derived from

QL_statement ::= select_statement | update_statement | delete_statement
select_statement ::= select_clause from_clause [where_clause] [groupby_clause] [having_clause] [orderby_clause]

*/

/*
select_clause ::= SELECT [DISTINCT] select_expression {, select_expression}*
select_expression ::= single_valued_path_expression |
		aggregate_expression |
		identification_variable |
		OBJECT(identification_variable) |
		constructor_expression
constructor_expression ::= NEW constructor_name ( constructor_item {, constructor_item}* )
 
 The SELECT statement determines the return type of an JPQL query.
 The SELECT may also determine the distinct state of a query
 
 A SELECT can be one of the following:
  1. SELECT OBJECT(someObject)... This query will return a collection of objects
  2. SELECT anObject.anAttribute ... This will return a collection of anAttribute
  3. SELECT aggregateFunction; ... This will return a single value
      The allowable aggregateFunctions are: AVG, COUNT, MAX, MIN, SUM
          SELECT AVG(artifact.size)... Returns the average of all the artifact sizes
          SELECT COUNT(user)... Returns a count of the users
          SELECT COUNT(user.firstName)... Returns a count of the user's firstNames
          SELECT MAX(artifact.size)... Returns the maximum artifact size
          SELECT MIN(artifact.size)... Returns the minimum artifact size
          SELECT SUM(artifact.size)... Returns the sum of all the artifact sizes
*/

public class SelectClauseNode extends QueryClauseNode {

    private List selectExpressions = new ArrayList();
    private boolean distinct = false;
    
    public SelectClauseNode() {
    }

    public List getSelectExpressions() {
        return selectExpressions;
    }

    public void setSelectExpressions(List exprs) {
        selectExpressions = exprs;
    }

    public boolean usesDistinct() {
        return distinct;
    }
    
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    
    /** 
     * Returns the first select expression node.
     */
    private Node getFirstSelectExpressionNode() {
        return selectExpressions.size() > 0 ? 
            (Node)selectExpressions.get(0) : null;
    }

    private boolean isSingleSelectExpression() {
        return selectExpressions.size() == 1;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	for (int i = 0; i < selectExpressions.size(); i++) {
            Node item = (Node)selectExpressions.get(i);
            Node newItem = item.accept(visitor, this);
            if (newItem != item) {
            	selectExpressions.set(i, newItem);
            }
            
        }
        return visitor.visitNode(this, parent);
    }

    /*
     * Returns true if the SELECT clause consists of a single expression
     * returning the base identification variable of the query and if the base
     * variable is defined as a range variable w/o FETCH JOINs.
     */
    public boolean isReadAllQuery(ParseTreeContext context) {
        if (!isSingleSelectExpression()) {
            // multiple expressions in the select clause
            return false;
        }
        
        Node node = getFirstSelectExpressionNode();
        if (!node.isVariableNode()) {
            // Does not select an identification variable (e.g. projection or
            // aggregate function)
            return false;
        }
        String variable = ((VariableNode)node).getCanonicalVariableName();
        
        // Note, the base variable in ParseTreeContext is not yet set
        // calculate it
        SelectStatement statement = (SelectStatement) getParseTree().getStatement();
        String baseVariable = statement.getFromClauseNode().getFirstVariable();
        if (!context.isRangeVariable(baseVariable) || 
            (context.getFetchJoins(baseVariable) != null)) {
            // Query's base variable is not a range variable or the base
            // variable has FETCH JOINs
            return false;
        }
        
        // Use ReadAllQuery if the variable of the SELECT clause expression is
        // the base variable
        return baseVariable.equals(variable);
    }

    public boolean hasOneToOneSelected(GenerationContext context) {
        // Iterate the select expression and return true if one of it has a
        // oneToOne selected.
        for (Iterator i = selectExpressions.iterator(); i.hasNext();) {
            Node node = (Node)i.next();
            if (hasOneToOneSelected(node, context)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Answer true if there is a one-to-one relationship selected.
     * This includes a chain of relationships.
     * True: SELECT user.name FROM ..... //Simple 1:1
     * True: SELECT artifact.parent.parent.parent FROM ..... //where child.parent are all 1:1.
     * False: SELECT OBJECT(employee) FROM ..... //simple SELECT
     * False: SELECT phoneNumber.areaCode FROM ..... //direct-to-field
     **/
    private boolean hasOneToOneSelected(Node node, GenerationContext context) {
        if (node.isCountNode()) {
            return false;
        }

        if (node.isAggregateNode()) {
            // delegate to aggregate expression
            return hasOneToOneSelected(node.getLeft(), context);
        }
         
        if (node.isVariableNode()){
            return false; // TODO !nodeRefersToObject(node, context);
        }

        if (node.isConstructorNode()) {
            List args = ((ConstructorNode)node).getConstructorItems();
            for (Iterator i = args.iterator(); i.hasNext();) {
                Node arg = (Node)i.next();
                if (hasOneToOneSelected(arg, context)) {
                    return true;
                }
            }
            return false;
        }
      
        // check whether it is a direct-to-field mapping
        return !selectingDirectToField(node, context);
    }

    /**
    * Answer true if the variable name given as argument is SELECTed.
    * True: "SELECT OBJECT(emp) ...." & variableName = "emp"
    * False: "SELECT OBJECT(somethingElse) ..." & variableName = "emp"
    */
    public boolean isSelected(String variableName) {
        for (Iterator i = selectExpressions.iterator(); i.hasNext();) {
            Node node = (Node)i.next();
            //Make sure we've SELECted a VariableNode
            if (node.isVariableNode() && 
                ((VariableNode)node).getCanonicalVariableName().equals(variableName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelectNode() {
        return true;
    }

    /**
     * Answer true if a variable in the IN clause is SELECTed
     */
    public boolean isVariableInINClauseSelected(GenerationContext context) {
        for (Iterator i = selectExpressions.iterator(); i.hasNext();) {
            Node node = (Node)i.next();
        
            if (node.isVariableNode()) {
                String variableNameForLeft = ((VariableNode)node).getCanonicalVariableName();
                if (!context.getParseTreeContext().isRangeVariable(variableNameForLeft)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isRelationship(Object ownerClass, String attribute) {
    	/*
        isObjectReferenceMapping() || 
        isOneToManyMapping() || 
        isManyToManyMapping());
        */
    	return true;
    }


    private boolean selectingRelationshipField(Node node) {
        if ((node == null) || !node.isDotNode()) {
            return false;
        }
        Node path = node.getLeft();
        AttributeNode attribute = (AttributeNode)node.getRight();
        return isRelationship(path.getType(), attribute.getAttributeName());
    }

    /**
     * Answer true if the SELECT ends in a direct-to-field.
     * true: SELECT phone.areaCode
     * false: SELECT employee.address
     */
    private boolean selectingDirectToField(Node node, GenerationContext context) {

        if ((node == null) || !node.isDotNode()) {     
            return false;
        }
        return ((DotNode)node).endsWithDirectToField(context);
    }
    
}
