package motion.database.model;

public class PredicateComposition {
	public static final String[] logicalOperators = {"AND", "OR"};
	
	private String logicalOperator;
	// FIXME: change to private after removing printPredicate() from SimplePredicate
	protected Predicate predicate;
	
	public PredicateComposition(String logicalOperator, Predicate predicate) {
		this.logicalOperator = logicalOperator;
		this.predicate = predicate;
	}
	
	public String getLogicalOperator() {
		
		return this.logicalOperator;
	}
	
	public Predicate getPredicate() {
		
		return this.predicate;
	}
}
