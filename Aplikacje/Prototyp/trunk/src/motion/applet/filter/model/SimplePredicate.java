package motion.applet.filter.model;

public class SimplePredicate extends Predicate {
	private String feature;
	private String operator;
	private String value;
	
	public SimplePredicate(String contextEntity, String feature, String operator, String value) {
		this.contextEntity = contextEntity;
		this.feature = feature;
		this.operator = operator;
		this.value = value;
	}
	
	public SimplePredicate(String contextEntity, String feature, String operator, String value, String logicalOperator, Predicate previousPredicate) {
		this(contextEntity, feature, operator, value);
		PredicateComposition predicateComposition = new PredicateComposition(logicalOperator, previousPredicate);
		this.previous = predicateComposition;
	}
	
	public void setNextPredicate(String logicalOperator, Predicate nextPredicate) {
		PredicateComposition nextPredicateComposition = new PredicateComposition(logicalOperator, nextPredicate);
		this.setNextPredicateComposition(nextPredicateComposition);
		nextPredicate.setPreviousPredicate(logicalOperator, this);
	}
	
	protected void setPreviousPredicate(String logicalOperator, Predicate previousPredicate) {
		PredicateComposition previousPredicateComposition = new PredicateComposition(logicalOperator, previousPredicate);
		this.setPreviousPredicateComposition(previousPredicateComposition);
	}
	
	private void setNextPredicateComposition(PredicateComposition nextPredicateComposition) {
		this.next = nextPredicateComposition;
	}
	
	private void setPreviousPredicateComposition(PredicateComposition previousPredicateComposition) {
		this.previous = previousPredicateComposition;
	}
}
