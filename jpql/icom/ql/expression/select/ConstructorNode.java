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
package icom.ql.expression.select;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

import java.util.ArrayList;
import java.util.List;


/*
select_expression ::= single_valued_path_expression |
					aggregate_expression |
					identification_variable |
					OBJECT(identification_variable) |
					constructor_expression
*/

/*
constructor_expression ::= NEW constructor_name ( constructor_item {, constructor_item}* )

constructor_item ::= single_valued_path_expression | aggregate_expression

A constructor may be used in the SELECT list to return one or more Java instances. The specified class
is not required to be an entity or to be mapped to the database. The constructor name must be fully qualified.

If an entity class name is specified in the SELECT NEW clause, the resulting entity instances are in the
new state.

     SELECT NEW oracle.bom.rest.WorkspaceSummary(w.name, w.description, w.participants, w.createdBy)
		FROM Organization org JOIN org.workspaces w
		WHERE w.participants.size > 1

*/

public class ConstructorNode extends Node {

    /** The name of the constructor class. */
    private String className = null;
    
    /** The list of constructor call argument nodes */
    public List constructorItems = new ArrayList();

    public ConstructorNode(String className) {
        this.className = className;
    }

    public String getConstructorClassName() {
		return className;
	}

	public void setConstructorClassName(String className) {
		this.className = className;
	}

	public void addConstructorItem(Object theNode) {
        constructorItems.add(theNode);
    }
    
    public List getConstructorItems() {
        return this.constructorItems;
    }

    public void setConstructorItems(List items) {
        this.constructorItems = items;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        List constructorItems = getConstructorItems();
    	for (int i = 0; i < constructorItems.size(); i++) {
            Node item = (Node)constructorItems.get(i);
            Node newItem = item.accept(visitor, this);
            if (newItem != item) {
            	constructorItems.set(i, newItem);
            }
        }
        return visitor.visitNode(this, parent);
    }
    
    public boolean isConstructorNode() {
        return true;
    }





}
