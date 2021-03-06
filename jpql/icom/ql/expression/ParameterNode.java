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
package icom.ql.expression;

import icom.ql.ParseTreeVisitor;
import icom.ql.parser.AbstractNode;
import icom.ql.parser.Node;

/*
Derived from

simple_entity_expression ::= identification_variable |
					input_parameter

arithmetic_primary ::= state_field_path_expression |
					numeric_literal |
					(simple_arithmetic_expression) |
					input_parameter |
					functions_returning_numerics |
					aggregate_expression
					
string_primary ::= state_field_path_expression |
					string_literal |
					input_parameter |
					functions_returning_strings

datetime_primary ::= state_field_path_expression |
					input_parameter |
					functions_returning_datetime |
					aggregate_expression

boolean_primary ::= state_field_path_expression |
					boolean_literal |
					input_parameter

enum_primary ::= state_field_path_expression |
					enum_literal |
					input_parameter

*/

/*

Represents input parameter ?1 or :obj_id

*/

public class ParameterNode extends Node {

    private String name;

    public ParameterNode() {
        super();
    }

    public ParameterNode(String newParameterName) {
        setParameterName(newParameterName);
    }

    public String getParameterName() {
        return name;
    }

    public void setParameterName(String name) {
        this.name = name;
    }

    public boolean isParameterNode() {
        return true;
    }
    
    public Node accept(ParseTreeVisitor visitor, AbstractNode parent) {
        return visitor.visitNode(this, parent);
    }

    public String getAsString() {
        return getParameterName();
    }


}
