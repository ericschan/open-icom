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
// $ANTLR 2.7.3: "EJBQLParser.g" -> "EJBQLParser.java"$
package icom.ql.parser.antlr;


public interface JPQLTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int ABS = 4;
	int ALL = 5;
	int AND = 6;
	int ANY = 7;
	int AS = 8;
	int ASC = 9;
	int AVG = 10;
	int BETWEEN = 11;
	int BOTH = 12;
	int BY = 13;
	int CONCAT = 14;
	int COUNT = 15;
	int CURRENT_DATE = 16;
	int CURRENT_TIME = 17;
	int CURRENT_TIMESTAMP = 18;
	int DESC = 19;
	int DELETE = 20;
	int DISTINCT = 21;
	int EMPTY = 22;
	int ESCAPE = 23;
	int EXISTS = 24;
	int FALSE = 25;
	int FETCH = 26;
	int FROM = 27;
	int GROUP = 28;
	int HAVING = 29;
	int IN = 30;
	int INNER = 31;
	int IS = 32;
	int JOIN = 33;
	int LEADING = 34;
	int LEFT = 35;
	int LENGTH = 36;
	int LIKE = 37;
	int LOCATE = 38;
	int LOWER = 39;
	int MAX = 40;
	int MEMBER = 41;
	int MIN = 42;
	int MOD = 43;
	int NEW = 44;
	int NOT = 45;
	int NULL = 46;
	int OBJECT = 47;
	int OF = 48;
	int OR = 49;
	int ORDER = 50;
	int OUTER = 51;
	int SELECT = 52;
	int SET = 53;
	int SIZE = 54;
	int SQRT = 55;
	int SOME = 56;
	int SUBSTRING = 57;
	int SUM = 58;
	int TRAILING = 59;
	int TRIM = 60;
	int TRUE = 61;
	int UNKNOWN = 62;
	int UPDATE = 63;
	int UPPER = 64;
	int WHERE = 65;
	int IDENT = 66;
	int COMMA = 67;
	int EQUALS = 68;
	int LEFT_ROUND_BRACKET = 69;
	int RIGHT_ROUND_BRACKET = 70;
	int DOT = 71;
	int NOT_EQUAL_TO = 72;
	int GREATER_THAN = 73;
	int GREATER_THAN_EQUAL_TO = 74;
	int LESS_THAN = 75;
	int LESS_THAN_EQUAL_TO = 76;
	int PLUS = 77;
	int MINUS = 78;
	int MULTIPLY = 79;
	int DIVIDE = 80;
	int NUM_INT = 81;
	int NUM_LONG = 82;
	int NUM_FLOAT = 83;
	int NUM_DOUBLE = 84;
	int STRING_LITERAL_DOUBLE_QUOTED = 85;
	int STRING_LITERAL_SINGLE_QUOTED = 86;
	int POSITIONAL_PARAM = 87;
	int NAMED_PARAM = 88;
	int HEX_DIGIT = 89;
	int WS = 90;
	int TEXTCHAR = 91;
	int EXPONENT = 92;
	int FLOAT_SUFFIX = 93;
}
