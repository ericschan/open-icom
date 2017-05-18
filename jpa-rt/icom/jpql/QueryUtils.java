package icom.jpql;

import icom.ql.QueryContext;
import icom.ql.SchemaHelper;

public interface QueryUtils {
	
	public QueryContext createQueryContext();
	
	public QueryImpl createQuery(String jpqlString, SchemaHelper schemaHelper, QueryContext context);

}
