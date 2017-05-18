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
package icom.jpql.bdk.filters;

import icom.QLException;
import icom.info.MarkerInfo;
import icom.info.TagApplicationInfo;
import icom.info.beehive.BeehiveTagApplicationInfo;
import icom.jpa.bdk.dao.LabelDAO;
import icom.jpql.stack.SimpleExpression;

import java.util.Map;

import com.oracle.beehive.Actor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.Label;
import com.oracle.beehive.LabelAppCreatorPredicate;
import com.oracle.beehive.LabelAppTypePredicate;
import com.oracle.beehive.LabelApplicationType;
import com.oracle.beehive.Predicate;
import com.oracle.beehive.Workspace;

public class LabeledEntityPredicateFactory extends MarkedEntityPredicateFactory {
	
	// select entity from Tag tag join tag.markedEntities entity
	//   where tag = :tag and appliedBy = :actor and type = 'PRIVATE'
	//   order by entity.name ASC";
	
	{
		attributeTypeMapper.put("Tag".toLowerCase(), Label.class);
		attributeTypeMapper.put("Space".toLowerCase(), Workspace.class);
		attributeTypeMapper.put(MarkerInfo.Attributes.markedEntities.name().toLowerCase(), Entity.class);
		attributeTypeMapper.put(TagApplicationInfo.Attributes.appliedBy.name().toLowerCase(), Actor.class);
		attributeTypeMapper.put(BeehiveTagApplicationInfo.Attributes.type.name().toLowerCase(), LabelApplicationType.class);
	}
	
	{
		equalsPredicateMapper.put(TagApplicationInfo.Attributes.appliedBy.name().toLowerCase(), LabelAppCreatorPredicate.class);
		equalsPredicateMapper.put(BeehiveTagApplicationInfo.Attributes.type.name().toLowerCase(), LabelAppTypePredicate.class);
	} 
	
	static LabeledEntityPredicateFactory singleton = new LabeledEntityPredicateFactory();
	
	public static LabeledEntityPredicateFactory getInstance() {
		return singleton;
	}
	
	public LabeledEntityPredicateFactory() {
	}
	
	public String getResourceId(BeeId id) {
		return "/entities/" + id.getId();
	}
	
	public String getResourceType() {
		return LabelDAO.getInstance().getResourceType();
	}
	
	public String resolveDataAccessStateObjectClassName() {
		return Entity.class.getSimpleName();
	}
	
	{predicateConstructorMapper.put(
			
		LabelAppTypePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				LabelAppTypePredicate predicate = new LabelAppTypePredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setLabelApplicationType((LabelApplicationType) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper != equalsPredicateMapper) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				Argument argument = new Argument();
				if (! (expression.firstOperand.value instanceof LabelApplicationType)) {
					if (expression.firstOperand.value instanceof String) {
						argument.value = LabelApplicationType.valueOf((String) expression.firstOperand.value);
					} else {
						throw new QLException("Not Supported");
					}
				}
				argument.type = LabelApplicationType.class;
				arguments.firstArgument = argument;
				return createPredicate(arguments);
			}
		}
	);}

	public boolean hasWorkspaceAsArgumentOfListMethod() {
		return true;
	}
	
	public boolean hasMarkerAsArgumentOfListMethod() {
		return true;
	}

}
