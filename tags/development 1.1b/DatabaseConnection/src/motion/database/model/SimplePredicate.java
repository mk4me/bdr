package motion.database.model;


public class SimplePredicate extends Predicate {
	private AttributeName feature;
	private String operator;
	private String value;
	
	public SimplePredicate(String contextEntity, AttributeName feature, String operator, String value) {
		this.contextEntity = contextEntity;
		this.feature = feature;
		this.operator = operator;
		this.value = value;
	}
	
	public SimplePredicate(String contextEntity, AttributeName feature, String operator, String value, String logicalOperator, Predicate previousPredicate) {
		this(contextEntity, feature, operator, value);
		if (previousPredicate != null) {	//new
			setPreviousPredicate(logicalOperator, previousPredicate);
		}
	}
	
	public SimplePredicate(String contextEntity, AttributeName feature, String operator, String value, String logicalOperator, Predicate previousPredicate, Predicate parentPredicate) {
		this(contextEntity, feature, operator, value, logicalOperator, previousPredicate);
		this.parent = parentPredicate;
	}
	
	protected void setNextPredicate(String logicalOperator, Predicate nextPredicate) {
		PredicateComposition nextPredicateComposition = new PredicateComposition(logicalOperator, nextPredicate);
		this.setNextPredicateComposition(nextPredicateComposition);
	}
	
	public void setPreviousPredicate(String logicalOperator, Predicate previousPredicate) {
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
	
	public AttributeName getFeature() {
		
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
	public String toString() {
		
		return "\n*predicate(entity[" + contextEntity +
			"], feature[" + feature +
			"], operator[" + operator +
			"], value[" + value +
			"], par[" + parent +
			"], n[" + next +
			"])";
	}
	
	// FIXME: remove this method.
	public String printPredicate() {
		String text = "\n";
		text += this.toString();
		
		return text;
	}
}
