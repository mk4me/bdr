package motion.database.ws;

import motion.database.ws.basicQueriesService.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesService.FilterPredicate;

public class DatabaseArrayOfFilterPredicate extends ArrayOfFilterPredicate
{
	public DatabaseArrayOfFilterPredicate(FilterPredicate []filters)
	{
		for( FilterPredicate f : filters)
			super.getFilterPredicate().add( f );
	}
}