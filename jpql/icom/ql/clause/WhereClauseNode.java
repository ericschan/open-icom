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
package icom.ql.clause;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;
import icom.ql.clause.ClauseNode;

/*

where_clause ::= WHERE conditional_expression

conditional_expression ::= conditional_term | conditional_expression OR conditional_term
conditional_term ::= conditional_factor | conditional_term AND conditional_factor
conditional_factor ::= [ NOT ] conditional_primary
conditional_primary ::= simple_cond_expression | (conditional_expression)
simple_cond_expression ::= comparison_expression |
						between_expression |
						like_expression |
						in_expression |
						null_comparison_expression |
						empty_collection_comparison_expression |
						collection_member_expression |
						exists_expression
between_expression ::=
			arithmetic_expression [NOT] BETWEEN arithmetic_expression AND arithmetic_expression |
			string_expression [NOT] BETWEEN string_expression AND string_expression |
			datetime_expression [NOT] BETWEEN datetime_expression AND datetime_expression

The WHERE clause of a query consists of a conditional expression used to select objects or values that
satisfy the expression. The WHERE clause restricts the result of a select statement or the scope of an
update or delete operation.

*/

public class WhereClauseNode extends ClauseNode {

	public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        if (left != null) {
            Node newLeft = left.accept(visitor, this);
            if (newLeft != left) {
            	setLeft(newLeft);
            }
        }
        return visitor.visitNode(this, parent);
    }

}
