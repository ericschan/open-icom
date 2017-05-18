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
package icom.ql;

import java.io.*;

/*
 */
public abstract class AbstractJPQLException extends RuntimeException {
	
    protected Throwable internalException;
    protected static Boolean shouldPrintInternalException = null;
    protected String indentationString;
    protected int errorCode;
    protected static final String CR = System.getProperty("line.separator");
    protected boolean hasBeenLogged;

    public AbstractJPQLException() {
        this("");
    }

    public AbstractJPQLException(String theMessage) {
        super(theMessage);
        this.indentationString = "";
        hasBeenLogged = false;
    }

    public AbstractJPQLException(String message, Throwable internalException) {
        this(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getIndentationString() {
        return indentationString;
    }

    /**
     * Return the internal native exception.
     * The internal exception can still be accessed if required.
     */
    public Throwable getInternalException() {
        return internalException;
    }

    /**
     * Error messages are multi-line so that detail descriptions of the exception are given.
     */
    public String getMessage() {
        StringWriter writer = new StringWriter(100);
        writer.write("[" + getErrorCode() + "] ");
        writer.write(super.getMessage());
        return writer.toString();
    }

    /**
     * Return if this exception has been logged to avoid being logged more than once.
     */
    public boolean hasBeenLogged() {
        return hasBeenLogged;
    }
    
    /**
     * Set this flag to avoid logging an exception more than once.
     */
    public void setHasBeenLogged(boolean logged) {
        this.hasBeenLogged = logged;
    }

    /**
     * Print both the normal and internal stack traces.
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * Print both the normal and internal stack traces.
     */
    public void printStackTrace(PrintStream outStream) {
        printStackTrace(new PrintWriter(outStream));
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Used to print things nicely in the testing tool.
     */
    public void setIndentationString(String indentationString) {
        this.indentationString = indentationString;
    }

    /**
     * If print internal exception is false, the internal exception will not be included
     * in the stack trace or the exception message of BomQLExceptions
     */
    public static boolean shouldPrintInternalException() {
        if (shouldPrintInternalException == null) {
            shouldPrintInternalException = new Boolean(false);
        }
        return shouldPrintInternalException.booleanValue();
    }
    
    /**
     * If print internal exception is false, the internal exception will not be included
     * in the stack trace or the exception message of BomQLExceptions
     */
    public static void setShouldPrintInternalException(boolean printException) {
        shouldPrintInternalException = new Boolean(printException);
    }

    public String toString() {
        return getIndentationString() + getClass().getName() + " " + getMessage();
    }
}
