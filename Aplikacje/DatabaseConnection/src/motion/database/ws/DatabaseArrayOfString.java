package motion.database.ws;

import motion.database.ws.basicQueriesService.ArrayOfString;

public class DatabaseArrayOfString extends ArrayOfString {
	
	public DatabaseArrayOfString(String[] strings)
	{
		super();
		for (String s: strings)
			super.getString().add(s);
	}

}
