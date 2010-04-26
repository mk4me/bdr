package motion.database;

@SuppressWarnings("serial")
public class GenericResult extends GenericDescription<GenericEntityStaticAttributes> {

	public GenericResult() {
		super( "emptyID", EntityKind.result );
		
		this.put( GenericEntityStaticAttributes.emptyID, -1 );
	}

}
