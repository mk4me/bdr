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
		setPreviousPredicate(logicalOperator, previousPredicate);
	}
	
	protected void setNextPredicate(String logicalOperator, Predicate nextPredicate) {
		PredicateComposition nextPredicateComposition = new PredicateComposition(logicalOperator, nextPredicate);
		this.setNextPredicateComposition(nextPredicateComposition);
	}
	
	protected void setPreviousPredicate(String logicalOperator, Predicate previousPredicate) {
		PredicateComposition previousPredicateComposition = new PredicateComposition(logicalOperator, previousPredicate);
		this.setPreviousPredicateComposition(previousPredicateComposition);
		previousPredicate.setNextPredicate(logicalOperator, this);
	}
	
	private void setNextPredicateComposition(PredicateComposition nextPredicateComposition) {
		this.next = nextPredicateComposition;
	}
	
	private void setPreviousPredicateComposition(PredicateComposition previousPredicateComposition) {
		this.previous = previousPredicateComposition;
	}
	
	public PredicateComposition getPreviousComposition() {
		
		return this.previous;
	}
	
	public PredicateComposition getNextComposition() {
		
		return this.next;
	}
	
	public String getFeature() {
		
		return this.feature;
	}
	
	public String getOperator() {
		
		return this.operator;
	}
	
	public String getValue() {
		
		return this.value;
	}
	
	public String getContextEntity() {
		
		return this.contextEntity;
	}
	
	// FIXME: remove this method.
	public void printPredicate() {
		System.out.println(contextEntity + " " + feature + " " + operator + " " + value + " n: " + next + " p: " + previous);
		if (next != null) {
			((SimplePredicate) next.predicate).printPredicate();
		}
	}
}
