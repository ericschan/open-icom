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
package icom.ql.expression.arithmetic.function;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

/*
functions_returning_numerics ::=
	LENGTH(string_primary) |
	LOCATE(string_primary, string_primary[, simple_arithmetic_expression]) |
	ABS(simple_arithmetic_expression) |
	SQRT(simple_arithmetic_expression) |
	MOD(simple_arithmetic_expression, simple_arithmetic_expression) |
	SIZE(collection_valued_path_expression)
*/

public class LocateNode extends ArithmeticFunctionNode {
	
    private Node find = null;
    private Node findIn = null;
    private Node startPosition = null;

    public LocateNode() {
        super();
    }

    public Node getFind() {
        return find;
    }

    public Node getFindIn() {
        return findIn;
    }

    public void setFind(Node newFind) {
        find = newFind;
    }

    public void setFindIn(Node newFindIn) {
        findIn = newFindIn;
    }

    public Node getStartPosition() {
        return startPosition;
    }
    
    public void setStartPosition(Node newStartPosition) {
        startPosition = newStartPosition;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        if (findIn != null) {
        	findIn = findIn.accept(visitor, this);
        }
        if (find != null) {
        	find = find.accept(visitor, this);
        }
        if (startPosition != null) {
        	startPosition = startPosition.accept(visitor, this);
        }
        return visitor.visitNode(this, parent);
    }
    
}