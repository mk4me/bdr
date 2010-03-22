package motion.applet.filter.model;

public class PredicateComposition {
	public static final String[] logicalOperators = {"AND", "OR"};
	
	private String logicalOperator;
	private Predicate predicate;
	
	public PredicateComposition(String logicalOperator, Predicate predicate) {
		this.logicalOperator = logicalOperator;
		this.predicate = predicate;
	}
}
