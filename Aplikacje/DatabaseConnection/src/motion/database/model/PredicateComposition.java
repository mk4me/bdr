package motion.database.model;

public class PredicateComposition {
	public static final String andOperator = "AND";
	public static final String orOperator = "OR";
	public static final String emptyOperator = "";
	public static final String[] logicalOperators = {andOperator, orOperator};
	
	private String logicalOperator;
	// FIXME: change to private after removing printPredicate() from SimplePredicate
	protected Predicate predicate;
	private boolean filterGroup = false;
	
	public PredicateComposition(String logicalOperator, Predicate predicate) {
		this.logicalOperator = logicalOperator;
		this.predicate = predicate;
	}
	
	public PredicateComposition(String logicalOperator, Predicate predicate, boolean filterGroup) {
		this.logicalOperator = logicalOperator;
		this.predicate = predicate;
		this.filterGroup = filterGroup;
	}
	
	public String getLogicalOperator() {
		
		return this.logicalOperator;
	}
	
	public Predicate getPredicate() {
		
		return this.predicate;
	}
	
	public boolean isFilterGroup() {
		
		return filterGroup;
	}
}
