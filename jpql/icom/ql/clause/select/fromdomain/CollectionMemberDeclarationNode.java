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
import icom.ql.clause.select.fromdomain.IdentificationVariableDeclarationNode;

/*
derived from

from_clause ::= FROM identification_variable_declaration
	{, {identification_variable_declaration | collection_member_declaration}}*
*/

/*
collection_member_declaration ::=
	IN (collection_valued_path_expression) [AS] identification_variable

An identification variable declared by a collection_member_declaration ranges over values of a collection
obtained by navigation using a path expression. Such a path expression represents a navigation
involving the association-fields of an entity abstract schema type. Because a path expression can be
based on another path expression, the navigation can use the association-fields of related entities.
An identification variable of a collection member declaration is declared using a special operator, the
reserved identifier IN. The argument to the IN operator is a collection-valued path expression. The path
expression evaluates to a collection type specified as a result of navigation to a collection-valued association-
field of an entity abstract schema type.

For example, the query
       
    SELECT DISTINCT w
		FROM Workspace w JOIN w.participants p JOIN p.addressable a
		WHERE a.name = ‘John Smith’

may equivalently be expressed as follows, using the IN operator:

    SELECT DISTINCT w
		FROM Workspace AS w, IN(w.participants) AS p
		WHERE p.addressable.name = ‘John Smith’

*/

public class CollectionMemberDeclarationNode extends IdentificationVariableDeclarationNode {

    private Node path;
    
    public Node getPath() {
        return path;
    }
    
    public void setPath(Node node) {
        path = node;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        if (path != null) {
            Node newPath = path.accept(visitor, this);
            if (newPath != path) {
            	setPath(newPath);
            }
        }
        return visitor.visitNode(this, parent);
    }

}
