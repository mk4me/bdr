package motion.database.ws;

import motion.database.ws.basicUpdateService.ArrayOfInt;

public class DatabaseArrayOfInteger extends ArrayOfInt {
	
	public DatabaseArrayOfInteger(int[] ints)
	{
		super();
		for (int s: ints)
			super.getInt().add(s);
	}

}
