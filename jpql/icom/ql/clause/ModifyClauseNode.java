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

import icom.ql.clause.select.fromdomain.IdentificationVariableDeclarationNode;

/*
 * ModifyNode is the superclass for UpdateNode and DeleteNode
 */
public abstract class ModifyClauseNode extends QueryClauseNode {

    private String abstractSchemaIdentifier;
    private String abstractSchemaName;

    public String getAbstractSchemaName() {
        return abstractSchemaName;
    }

    public void setAbstractSchemaName(String abstractSchemaName) {
        this.abstractSchemaName = abstractSchemaName;
    }

    public String getAbstractSchemaIdentifier() {
        return abstractSchemaIdentifier;
    }

    public void setAbstractSchemaIdentifier(String identifierName) {
        abstractSchemaIdentifier = identifierName;
    }
    
    /**
     * Returns the canonical name of abstract schema identifier. 
     * If the identifier is not specified(unqualified attribute scenario),
     * the canonical name of abstract schema is returned. 
     */
    public String getCanonicalAbstractSchemaIdentifier() {
        String variable = abstractSchemaIdentifier != null ?
                abstractSchemaIdentifier : abstractSchemaName;
        return IdentificationVariableDeclarationNode.calculateCanonicalName(variable);
    }

    
}
