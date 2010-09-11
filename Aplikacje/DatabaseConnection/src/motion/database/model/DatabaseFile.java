package motion.database.model;



@SuppressWarnings("serial")
public class DatabaseFile extends GenericDescription<DatabaseFileStaticAttributes>{

	public DatabaseFile() {
		super(DatabaseFileStaticAttributes.FileID.name(), EntityKind.file);
	}
}