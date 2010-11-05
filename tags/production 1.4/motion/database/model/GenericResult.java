package motion.database.model;

@SuppressWarnings("serial")
public class GenericResult extends GenericDescription<GenericResultStaticAttributes> {

	public GenericResult() {
		super( "emptyID", EntityKind.result );
		
		this.put( GenericResultStaticAttributes.emptyID, -1 );
	}

}
