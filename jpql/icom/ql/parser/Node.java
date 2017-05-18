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
package icom.ql.parser;

import icom.ql.ParseTreeVisitor;


public abstract class Node extends AbstractNode {
	
    private int line;
    private int column;
    protected Node left = null;
    protected Node right = null;
    private Object type;

    /**
     * Return a new Node.
     */
    public Node() {
        super();
    }

    /**
     * Get the string representation of this node.
     * By default return toString()
     */
    public String getAsString() {
        return toString();
    }
    
    public abstract Node accept(ParseTreeVisitor visitor, AbstractNode parent);
    /*
    {
        if (left != null) {
            left.accept(visitor, parent);
        }
        if (right != null) {
            right.accept(visitor, parent);
        }
    }
    */

    public Node getLeft() {
        return left;
    }
    
    public void setLeft(Node newLeft) {
        left = newLeft;
    }
    
    public Node getRight() {
        return right;
    }

    public void setRight(Node newRight) {
        right = newRight;
    }

    public boolean hasLeft() {
        return getLeft() != null;
    }

    public boolean hasRight() {
        return getRight() != null;
    }
    
    public int getLine() {
        return line;
    }
    
    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }
    
    public void setColumn(int column) {
        this.column = column;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    /**
     * Is this node an Aggregate node
     */
    public boolean isAggregateNode() {
        return false;
    }

    /**
     * Is this node a Dot node
     */
    public boolean isDotNode() {
        return false;
    }

    /**
     * Is this a literal node
     */
    public boolean isLiteralNode() {
        return false;
    }

    /**
     * Is this node a Multiply node
     */
    public boolean isMultiplyNode() {
        return false;
    }

    /**
     * Is this node a Not node
     */
    public boolean isNotNode() {
        return false;
    }

    /**
     * Is this a Parameter node
     */
    public boolean isParameterNode() {
        return false;
    }

    /**
     * Is this node a Divide node
     */
    public boolean isDivideNode() {
        return false;
    }

    /**
     * Is this node a Plus node
     */
    public boolean isPlusNode() {
        return false;
    }

    /**
     * Is this node a Minus node
     */
    public boolean isMinusNode() {
        return false;
    }

    /**
     * Is this node a VariableNode
     */
    public boolean isVariableNode() {
        return false;
    }

    /**
     * Is this node an AttributeNode
     */
    public boolean isAttributeNode() {
        return false;
    }

    /**
     * Is this node a CountNode
     */
    public boolean isCountNode() {
        return false;
    }
    
    /**
     * Is this node a SqrtNode
     */
    public boolean isSqrtNode() {
        return false;
    }
    
    /**
     * Is this node a UnaryMinusNode
     */
    public boolean isUnaryMinusNode() {
        return false;
    }
    
    /**
     * Is this node a LengthNode
     */
    public boolean isLengthNode() {
        return false;
    }

    /**
     * Is this node a ConstructorNode
     */
    public boolean isConstructorNode() {
        return false;
    }

    /**
     * Is this node a SubqueryNode
     */
    public boolean isSubqueryNode() {
        return false;
    }

    /**
     * Is this an escape node
     */
    public boolean isEscape() {
        return false;// no it is not
    }

    /**
     * resolveAttribute(): Answer the name of the attribute which is represented by the receiver.
     * Subclasses should override this.
     */
    public String resolveAttribute() {
        return "";
    }
    
    /**
     * resolveClass: Answer the class associated with the content of this node. Default is to return null.
     * Subclasses should override this.
     */
    public Class resolveClass(GenerationContext context) {
        return null;
    }
    
    /**
     * resolveClass: Answer the class associated with the content of this node. Default is to return null.
     * Subclasses should override this.
     */
    public Class resolveClass(GenerationContext context, Class ownerClass) {
        return null;
    }

    public String toString() {
        try {
            return toString(1);
        } catch (Throwable t) {
            return t.toString();
        }
    }

    public String toString(int indent) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(toStringDisplayName());
        buffer.append("\r\n");
        toStringIndent(indent, buffer);
        if (hasLeft()) {
            buffer.append("Left: " + getLeft().toString(indent + 1));
        } else {
            buffer.append("Left: null");
        }

        buffer.append("\r\n");
        toStringIndent(indent, buffer);
        if (hasRight()) {
            buffer.append("Right: " + getRight().toString(indent + 1));
        } else {
            buffer.append("Right: null");
        }
        return buffer.toString();
    }

    public String toStringDisplayName() {
        return getClass().toString().substring(getClass().toString().lastIndexOf('.') + 1, getClass().toString().length());
    }

    public void toStringIndent(int indent, StringBuffer buffer) {
        for (int i = 0; i < indent; i++) {
            buffer.append("  ");
        }
        ;
    }
}
