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
	
identification_variable_declaration ::= range_variable_declaration { join | fetch_join }*
*/

/*
range_variable_declaration ::= abstract_schema_name [AS] identification_variable

The syntax for declaring an identification variable as a range variable is similar 
to that of SQL; optionally, it uses the AS keyword. Range variable declarations allow 
the developer to designate a “root” for objects which may not be reachable by navigation.

In order to select values by comparing more than one instance of an entity 
abstract schema type, more than one identification variable ranging over the 
abstract schema type is needed in the FROM clause. The following query returns artifacts 
whose last modified time is later than the creation time of an artifact, which is a label.

     SELECT DISTINCT a1
         FROM Artifact a1, Artifact a2
         WHERE a1.modifiedOn > a2.createdOn AND
               a2 = :label

*/

public class RangeVariableDeclarationNode extends IdentificationVariableDeclarationNode {

    private String abstractSchemaName;
    
    public String getAbstractSchemaName() {
        return abstractSchemaName;
    }
    
    public void setAbstractSchemaName(String name) {
        abstractSchemaName = name;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        return visitor.visitNode(this, parent);
    }
}
