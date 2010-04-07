package motion.database;


@SuppressWarnings("serial")
public class DatabaseFile extends GenericDescription<DatabaseFileStaticAttributes>{

	public DatabaseFile() {
		super(DatabaseFileStaticAttributes.fileID.name(), EntityKind.file);
	}
}