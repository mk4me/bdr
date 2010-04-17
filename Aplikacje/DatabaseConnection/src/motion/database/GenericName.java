package motion.database;

public class GenericName {

	public String name;
	public int id;

	public GenericName(int motionKindID, String motionKindName) {
		this.id = motionKindID;
		this.name = motionKindName;
	}

	public String toString()
	{
		return name + " (" + id + ")";
	}
}
