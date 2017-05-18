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
package icom.weaver;

public class PropertyNameUtil {
    
    /*
     *  nouns become plural with -s except for 
     *  nouns ending with a hiss sound (s, z, x, ch, sh), add -es  
     *  nouns ending with a vowel (a, e, i, o, or u) and y, add -s  
     *  nouns ending with a consonant (b, c, d, f, g, h, j, k, l, m, n, p, q, r, s ,t, v, w, x, y, and z) 
     *      and y, change -y to -ies  
     *  nouns ending with -is, change to -es  
     *  nouns ending with -f or -fe, change to -ves, including the exceptional case of roof  
     *  nouns ending with -o, add either -s or -es (sometimes both endings are correct)  
     *  irregular and mutating nouns (man, child, foot...)  
     *  compound nouns with left-handed heads (e.g. brother-in-law)  
     *  irregular nouns from Greek or Latin (cactus, alumnus, syllabus...)  
     *  less common irregular nouns from Greek or Latin (gnus, larva, synopsis...)  
     *  nouns with identical singular and plural form (fish, corps, aircraft...)  
     */
    static public String convertToPlural(String singularName) {
        int length = singularName.length();
        char last = singularName.charAt(length - 1);
        if (last == 's' || last == 'z' || last == 'x') {
            return singularName + "es";
        } else if (last == 'h') {
            if (length > 1) {
                char secondLast = singularName.charAt(length - 2);
                if (secondLast == 'c' || secondLast == 's') {
                    return singularName + "es";
                } else {
                    return singularName + "s";
                }
            }
        } else if (last == 'y') {
            if (length > 1) {
                char secondLast = singularName.charAt(length - 2);
                if (secondLast == 'a' || secondLast == 'e' || secondLast == 'i' 
                    || secondLast == 'o' || secondLast == 'u') {
                    return singularName + "s";
                } else {
                    return singularName.substring(0, length - 1) + "ies";
                }
            }
        } else if (last == 'f') {
            return singularName.substring(0, length - 1) + "ves";
        } else if (last == 'e') {
            if (length > 2) {
                char secondLast = singularName.charAt(length - 2);
                if (secondLast == 'f') {
                    return singularName.substring(0, length - 2) + "ves";
                }
            } else {
                return singularName + "s";
            }
        } else if (last == 'o') {
            return singularName + "s";
        }
        return singularName + "s";
    }

}
