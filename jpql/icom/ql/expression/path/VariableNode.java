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
package icom.ql.expression.path;

import icom.ql.ParseTreeVisitor;
import icom.ql.clause.select.fromdomain.IdentificationVariableDeclarationNode;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.GenerationContext;
import icom.ql.parser.Node;
import icom.ql.parser.ParseTreeContext;

/*
Derived from

from_clause ::= FROM identification_variable_declaration {, {identification_variable_declaration | collection_member_declaration}}*

identification_variable_declaration ::= range_variable_declaration { join | fetch_join }*

range_variable_declaration ::= abstract_schema_name [AS] identification_variable

join ::= join_spec join_association_path_expression [AS] identification_variable

*/

/*

Represents the identification_variable in

join_collection_valued_path_expression ::= identification_variable.collection_valued_association_field

join_single_valued_association_path_expression ::= identification_variable.single_valued_association_field

state_field_path_expression ::= {identification_variable | single_valued_association_path_expression}.state_field

single_valued_association_path_expression ::= identification_variable.{single_valued_association_field.}* single_valued_association_field

collection_valued_path_expression ::= identification_variable.{single_valued_association_field.}*collection_valued_association_field

*/

public class VariableNode extends Node {

    private String variableName;
    
    private String canonicalName;

    public VariableNode() {
        super();
    }

    public VariableNode(String newVariableName) {
        setVariableName(newVariableName);
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String newVariableName) {
        variableName = newVariableName;
        canonicalName = IdentificationVariableDeclarationNode.calculateCanonicalName(newVariableName);
    }

    public String getCanonicalVariableName() {
        return canonicalName;
    }

    public boolean isVariableNode() {
        return true;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        return visitor.visitNode(this, parent);
    }

    public boolean isAlias(ParseTreeContext context) {
        String classNameForAlias = context.schemaForVariable(getCanonicalVariableName());
        return classNameForAlias != null;
    }

    public Node getNodeForAlias(GenerationContext context) {
        //Node node = context.getParseTreeContext().nodeForIdentifier(getCanonicalVariableName());
        //return node != null ? ((IdentificationVariableDeclNode)node).getPath() : null;
        return context.getParseTreeContext().pathForVariable(getCanonicalVariableName());
    }

    /**
     * isAlias: Answer true if this variable represents an alias in the FROM clause.
     * i.e. "FROM Employee emp" declares "emp" as an alias
     */
    public boolean isAlias(GenerationContext context) {
        return isAlias(context.getParseTreeContext());
    }

    public String toString(int indent) {
        StringBuffer buffer = new StringBuffer();
        toStringIndent(indent, buffer);
        buffer.append(toStringDisplayName() + "[" + getVariableName() + "]");
        return buffer.toString();
    }

    public String getAsString() {
        return getVariableName();
    }

}
