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
package icom.ql.clause.select.fromdomain;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

/*
derived from

from_clause ::= FROM identification_variable_declaration
	{, {identification_variable_declaration | collection_member_declaration}}*
*/

/*
identification_variable_declaration ::= range_variable_declaration { join | fetch_join }*

An identification variable is a valid identifier declared in the FROM clause of a query.
An identification variable ranges over the abstract schema type of an entity. 
An identification variable designates an instance of an entity abstract schema type or 
an element of a collection of entity abstract schema type instances. Identification 
variables are existentially quantified in a query.


An identification variable evaluates to a value of the type of the expression used in 
declaring the variable. For example, consider the query:

	SELECT DISTINCT f
		FROM Workspace w JOIN w.elements f
		WHERE f.name = ‘Inbox’
		
In the FROM clause declaration w.elements f, the identification variable f evaluates to any
Folder value directly reachable from Workspace. The association-field elements is a collection of
instances of the abstract schema type Folder and the identification variable f refers to an instance
of Folder. The type of f is the abstract schema type Folder.

*/

public abstract class IdentificationVariableDeclarationNode extends Node {

    /** The variable name as specified. */
    private String name;
    
    /** The canonical representation of the variable name. */
    private String canonicalName;
    
    /** */
    public String getVariableName() {
        return name;
    }
    
    /** */
    public void setVariableName(String name) {
        this.name = name;
        this.canonicalName = calculateCanonicalName(name);
    }

    /** */
    public String getCanonicalVariableName() {
        return canonicalName;
    }

    /** */
    public static String calculateCanonicalName(String name) {
        return name.toLowerCase();
    }

    /** */
    public Node getPath() {
        return null;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        return visitor.visitNode(this, parent);
    }
    
}
