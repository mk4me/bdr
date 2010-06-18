package motion.database.model;



@SuppressWarnings("serial")
public class DatabaseFile extends GenericDescription<DatabaseFileStaticAttributes>{

	public DatabaseFile() {
		super(DatabaseFileStaticAttributes.fileID.name(), EntityKind.file);
	}
}