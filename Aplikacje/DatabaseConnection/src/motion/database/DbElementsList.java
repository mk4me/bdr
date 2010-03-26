package motion.database;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class DbElementsList<T extends GenericDescription<?>> extends ArrayList<T>
{
	public T findById(int id)
	{
		for (T s : this)			
			if (s.getId() == id)
				return s;
		return null;
	}
	
	public String toString()
	{
		StringBuffer output = new StringBuffer();
		for (GenericDescription<?> g : this)
			output.append( g.toString() );
		return output.toString();
	}
}