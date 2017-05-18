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
package icom.ql.parser.antlr;

import icom.ql.JPQLException;
import icom.ql.JPQLParseTree;
import icom.ql.QueryContext;
import icom.ql.parser.NodeFactory;
import icom.ql.parser.NodeFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import persistence.antlr.ANTLRException;
import persistence.antlr.LLkParser;
import persistence.antlr.MismatchedCharException;
import persistence.antlr.MismatchedTokenException;
import persistence.antlr.NoViableAltException;
import persistence.antlr.NoViableAltForCharException;
import persistence.antlr.ParserSharedInputState;
import persistence.antlr.RecognitionException;
import persistence.antlr.Token;
import persistence.antlr.TokenBuffer;
import persistence.antlr.TokenStream;
import persistence.antlr.TokenStreamException;
import persistence.antlr.TokenStreamRecognitionException;

/**
 * EJBQLParser is the superclass of the ANTLR generated parser.
 */
public abstract class AbstractJPQLParser extends LLkParser {

    /** The ANTLR end of file character. */
    private static final int EOF_CHAR = 65535; // = (char) -1 = EOF

    /** List of errors. */
    private List errors = new ArrayList();

    /** The name of the query being compiled. 
     *  The variable is null for dynamic queries. 
     */
    private String queryName = null;
    
    /** The text of the query being compiled. */
    private String queryText = null;

    /** The factory to create parse tree nodes. */
    protected NodeFactory factory;
    
    protected AbstractJPQLParser(TokenBuffer tokenBuf, int k_) {
        super(tokenBuf, k_);
    }

    protected AbstractJPQLParser(ParserSharedInputState state, int k_) {
        super(state, k_);
    }

    protected AbstractJPQLParser(TokenStream lexer, int k) {
        super(lexer, k);
    }

    /**
     * INTERNAL
     * Returns the ANTLR version currently used.
     */
    public static String ANTLRVersion() throws Exception {
        return "2.7.3";
    }

    /**
     * INTERNAL
     * Builds a parser, parses the specified query string and returns the
     * parse tree. Any error in the query text results in an EJBQLException.
     * This method is used for dynamic queries.
     */
    public static JPQLParseTree buildParseTree(String queryText, QueryContext queryContext) 
        throws JPQLException {
        return buildParseTree(null, queryText, queryContext);
    }
    
    /**
     * INTERNAL
     * Builds a parser, parses the specified query string and returns the
     * parse tree. Any error in the query text results in an EJBQLException.
     */
    public static JPQLParseTree buildParseTree(String queryName, String queryText, QueryContext queryContext) 
        throws JPQLException {
        JPQLParser parser = buildParserFor(queryName, queryText, queryContext);
        return parser.parse();
    }

    /**
     * INTERNAL
     * Creates a parser for the specified query string. The query string is
     * not parsed (see method parse).      
     * This method is used for dynamic queries.
     */
    public static JPQLParser buildParserFor(String queryText, QueryContext queryContext) 
        throws JPQLException {
        return buildParserFor(null, queryText, queryContext);
    }
        
    /**
     * INTERNAL
     * Creates a parser for the specified query string. The query string is
     * not parsed (see method parse).
     */
    public static JPQLParser buildParserFor(String queryName, String queryText, QueryContext queryContext) 
        throws JPQLException {
        try {
            JPQLParser parser = JPQLParserBuilder.buildParser(queryText);
            queryContext.setParser(parser);
            parser.setQueryName(queryName);
            parser.setQueryText(queryText);
            parser.setNodeFactory(new NodeFactoryImpl(parser.getQueryInfo()));
            return parser;
        } catch (Exception ex) {
            throw JPQLException.generalParsingException(queryText, ex);
        }
    }

    /**
     * INTERNAL
     * Parse the query string that was specified on parser creation.
     */
    public JPQLParseTree parse() 
        throws JPQLException {
        try {
            document();
        } catch (Exception e) {
            addError(e);
        }
        
        // Handle any errors generated by the Parser
        if (hasErrors()) {
            throw generateException();
        }

        // return the parser tree
        return getParseTree();
    }

    /**
     * INTERNAL
     * Returns the parse tree created by a successful run of the parse
     * method. 
     */
    public JPQLParseTree getParseTree() {
        return (JPQLParseTree)getRootNode();
    }
    
    /**
     * INTERNAL
     * Return the text of the current query being compiled.
     */
    public String getQueryText() {
        return queryText;
    }

    /**
     * INTERNAL
     * Set the text of the current query being compiled. 
     * Please note, setting the query text using this method is for error 
     * handling and debugging purposes.
     */
    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
    
    /**
     * INTERNAL
     * Return the name of the current query being compiled. This method returns 
     * <code>null</code> if the current query is a dynamic query and not a named
     * query.
     */
    public String getQueryName() {
        return queryText;
    }

    /**
     * INTERNAL
     * Set the name of the current query being compiled. 
     * Please note, setting the query name using this method is for error 
     * handling and debugging purposes.
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * INTERNAL
     * Return the the query text prefixed by the query name in case of a 
     * named query. The method returns just the query text in case of a dynamic
     * query.
     */
    public String getQueryInfo() {
        return (queryName == null) ? queryText :
            queryName + ": " + queryText;
    }
    
    /** 
     * INTERNAL
     * Set the factory used by the parser to create a parse tree and parse
     * tree nodes.
     */
    public void setNodeFactory(NodeFactory factory) {
        this.factory = factory;
    }

    /** 
     * INTERNAL
     * Returns the factory used by the parser to create a parse tree and parse
     * tree nodes. 
     */
    public NodeFactory getNodeFactory() {
        return factory;
    }

    /**
     * INTERNAL
     * Returns the list of errors found during the parsing process.
     */
    public List getErrors() {
        return errors;
    }

    /**
     * INTERNAL
     * Returns true if there were errors during the parsing process.
     */
    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    /**
     * INTERNAL
     * Add the exception to the list of errors.
     */
    public void addError(Exception e) {
        if (e instanceof ANTLRException) {
            e = handleANTLRException((ANTLRException)e);
        } else if (!(e instanceof JPQLException)) {
            e = JPQLException.generalParsingException(getQueryInfo(), e);
        }
        errors.add(e);
    }

    /**
     * INTERNAL
     * Generate an exception which encapsulates all the exceptions generated
     * by this parser. Special case where the first exception is an
     * EJBQLException. 
     */
    protected JPQLException generateException() {
        //Handle exceptions we expect (such as expressionSotSupported)
        Exception firstException = (Exception)getErrors().get(0);
        if (firstException instanceof JPQLException) {
            return (JPQLException)firstException;
        }

        //Handle general exceptions, such as NPE
        JPQLException exception = 
            JPQLException.generalParsingException(getQueryInfo());
        exception.setInternalExceptions(getErrors());
        return exception;
    }

    /**
     * INTERNAL
     * Map an exception thrown by the ANTLR generated code to an
     * EJBQLException. 
     */
    //gf1166 Wrap ANTLRException inside EJBQLException
    protected JPQLException handleANTLRException(ANTLRException ex) {
        JPQLException result = null;
        if (ex instanceof MismatchedCharException) {
            MismatchedCharException mismatched = (MismatchedCharException)ex;
            if (mismatched.foundChar == EOF_CHAR) {
                result = JPQLException.unexpectedEOF(getQueryInfo(), 
                    mismatched.getLine(), mismatched.getColumn(), ex);
            } else if (mismatched.mismatchType == MismatchedCharException.CHAR) {
                result = JPQLException.expectedCharFound(getQueryInfo(), 
                    mismatched.getLine(), mismatched.getColumn(), 
                    String.valueOf((char)mismatched.expecting), 
                    String.valueOf((char)mismatched.foundChar), 
                    ex);
            }
        }
        else if (ex instanceof MismatchedTokenException) {
            MismatchedTokenException mismatched = (MismatchedTokenException)ex;
            Token token = mismatched.token;
            if (token != null) {
                if (token.getType() == Token.EOF_TYPE) {
                    result = JPQLException.unexpectedEOF(getQueryInfo(), 
                        mismatched.getLine(), mismatched.getColumn(), ex);
                }
                else {
                    result = JPQLException.syntaxErrorAt(getQueryInfo(),
                        mismatched.getLine(), mismatched.getColumn(),
                        token.getText(), ex);
                }
            }
        }
        else if (ex instanceof NoViableAltException) {
            NoViableAltException noviable = (NoViableAltException)ex;
            Token token = noviable.token;
            if (token != null) {
                if (token.getType() == Token.EOF_TYPE) {
                    result = JPQLException.unexpectedEOF(getQueryInfo(),
                        noviable.getLine(), noviable.getColumn(), ex);
                }
                else {
                    result = JPQLException.unexpectedToken(getQueryInfo(), 
                        noviable.getLine(), noviable.getColumn(), 
                        token.getText(), ex);
                }
            }
        }
        else if (ex instanceof NoViableAltForCharException) {
            NoViableAltForCharException noViableAlt = (NoViableAltForCharException)ex;
            result = JPQLException.unexpectedChar(getQueryInfo(),
                noViableAlt.getLine(), noViableAlt.getColumn(),
                String.valueOf((char)noViableAlt.foundChar), ex);
        }
        else if (ex instanceof TokenStreamRecognitionException) {
            result = handleANTLRException(((TokenStreamRecognitionException)ex).recog);
        }
        
        if (result == null) {
            // no special handling from aboves matches the exception if this
            // line is reached => make it a syntax error
            result = JPQLException.syntaxError(getQueryInfo(), ex);
        }
        return result;
    }

    /**
     * Method called by the ANTLR generated code in case of an error.
     */
    public void reportError(RecognitionException ex) {
        addError(ex);
    }

    /** 
     * This is the parser start method. It will be implemented by the ANTLR
     * generated subclass.
     */
    public abstract void document() throws RecognitionException, TokenStreamException;

    /**
     * Returns the root node after representing the parse tree for the current
     * query string. It will be implemented by the ANTLR generated subclass.
     */
    public abstract Object getRootNode();

}
