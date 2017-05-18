package icom.jpql.bdk.filters;

import java.util.Map;

import icom.jpql.stack.SimpleExpression;

import com.oracle.beehive.Predicate;

public interface PredicateConstructor {

	public Predicate createPredicate(ArgumentCollection arguments);
	
	public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper);
	
}
