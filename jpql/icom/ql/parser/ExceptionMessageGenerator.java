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

import icom.ql.AbstractJPQLException;
import icom.ql.JPQLExceptionResource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;


/**
 * Utility class to generate exception messages using ResourceBundles.
 */
public class ExceptionMessageGenerator {

    /**
     * Return the message for the given exception class and error number.
     */
    public static String buildMessage(Class exceptionClass, int errorNumber, Object[] arguments) {
        final String CR = System.getProperty("line.separator");

        String shortClassName = exceptionClass.getSimpleName(); // TODO getShortClassName(exceptionClass);
        String packageName = exceptionClass.getPackage().getName();
        String message = "";
        ResourceBundle bundle = null;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] == null) {
                arguments[i] = "null";
            }
        }
        
        bundle = ResourceBundle.getBundle(packageName + "." + shortClassName + "Resource", Locale.getDefault());

        try {
            message = bundle.getString(String.valueOf(errorNumber));
        } catch (java.util.MissingResourceException mre) {
            // Found bundle, but couldn't find exception translation.
            // Get the current language's NoExceptionTranslationForThisLocale message.
            bundle = ResourceBundle.getBundle("oracle.bom.ExceptionResource", Locale.getDefault());
            String noTranslationMessage = bundle.getString("NoExceptionTranslationForThisLocale");
            Object[] args = { CR };
            return format(message, arguments) + format(noTranslationMessage, args);
        }
        return format(message, arguments);
    }

    /**
     * Return the formatted message for the given exception class and error number.
     */
    protected static String format(String message, Object[] arguments) {
        try {
            return MessageFormat.format(message, arguments);
        } catch (AbstractJPQLException ex) {
            ResourceBundle bundle = null;
            bundle = ResourceBundle.getBundle("oracle.bom.ExceptionResource", Locale.getDefault());
            String errorMessage = bundle.getString("ErrorFormattingMessage");
            Vector vec = new Vector();
            if (arguments != null) {
                for (int index = 0; index < arguments.length; index++) {
                    try {
                        vec.add(arguments[index].toString());
                    } catch (AbstractJPQLException ex2) {
                        vec.add(ex2);
                    }
                }
            }
            return MessageFormat.format(errorMessage, new Object[] {message, vec});
        }
    }

    /**
     * Get one of the generic headers used for the exception's toString().
     *
     * E.g., "EXCEPTION DESCRIPTION: ", "ERROR CODE: ", etc.
     */
    public static String getHeader(String headerLabel) {
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle("oracle.bom.ExceptionResource", Locale.getDefault());
            return bundle.getString(headerLabel);
        } catch (java.util.MissingResourceException mre) {
            return "[" + headerLabel + "]";
        }
    }
}
