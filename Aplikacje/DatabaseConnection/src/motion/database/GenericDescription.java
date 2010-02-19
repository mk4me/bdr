package motion.database;

import java.util.HashMap;

@SuppressWarnings("serial")
class GenericDescription<T extends Enum<T>> extends HashMap<String, Object>
{
	public Object get(T key) {
		return super.get(key.name());
	}
	
	public Object put(T key, Object arg) {
		return super.put( key.name(), arg );
	}
}
