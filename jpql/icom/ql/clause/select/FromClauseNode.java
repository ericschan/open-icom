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
import icom.ql.clause.select.fromdomain.RangeVariableDeclarationNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

import java.util.List;


/*
from_clause ::=	FROM identification_variable_declaration
	{, {identification_variable_declaration | collection_member_declaration}}*
	
identification_variable_declaration ::= range_variable_declaration { join | fetch_join }*

range_variable_declaration ::= abstract_schema_name [AS] identification_variable

join ::= join_spec join_association_path_expression [AS] identification_variable

fetch_join ::= join_spec FETCH join_association_path_expression

join_spec ::= [ LEFT [OUTER] | INNER ] JOIN

join_association_path_expression ::= join_collection_valued_path_expression | join_single_valued_association_path_expression

join_collection_valued_path_expression ::= identification_variable.collection_valued_association_field

join_single_valued_association_path_expression ::= identification_variable.single_valued_association_field

collection_member_declaration ::= IN (collection_valued_path_expression) [AS] identification_variable

single_valued_path_expression ::= state_field_path_expression | single_valued_association_path_expression

state_field_path_expression ::= {identification_variable | single_valued_association_path_expression}.state_field

single_valued_association_path_expression ::= identification_variable.{single_valued_association_field.}* single_valued_association_field

collection_valued_path_expression ::= identification_variable.{single_valued_association_field.}*collection_valued_association_field

state_field ::= {embedded_class_state_field.}*simple_state_field

The FROM clause of a query defines the domain of the query by declaring identification variables. An
identification variable is an identifier declared in the FROM clause of a query. The domain of the query
may be constrained by path expressions. Identification variables designate instances of a particular entity 
abstract schema type. The FROM clause can contain multiple identification variable declarations separated 
by a comma (,).

*/

public class FromClauseNode extends ClauseNode {

    private List declarations;

    public String getFirstVariable() {
        String variable = null;
        if ((declarations != null) && (declarations.size() > 0)) {
        	RangeVariableDeclarationNode node = (RangeVariableDeclarationNode) declarations.get(0);
            variable = node.getCanonicalVariableName();
        }
        return variable;
    }
    
    public String getFirstAbstractSchemaName() {
    	
        String abstractSchemaName = null;
        if ((declarations != null) && (declarations.size() > 0)) {
        	RangeVariableDeclarationNode node = (RangeVariableDeclarationNode) declarations.get(0);
        	abstractSchemaName = node.getAbstractSchemaName();
        }
        return abstractSchemaName;
    }

    public List getDeclarations() {
        return declarations;
    }
    
    public void setDeclarations(List decls) {
        declarations = decls;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
    	for (int i = 0; i < declarations.size(); i++) {
    		Node decl = (Node)declarations.get(i);
    		Node newDecl = decl.accept(visitor, this);
    		if (newDecl != decl) {
            	declarations.set(i, newDecl);
            }
    	}
        return visitor.visitNode(this, parent);
    }
    
}
