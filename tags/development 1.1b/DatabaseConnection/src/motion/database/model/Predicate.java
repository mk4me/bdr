package motion.database.model;

public abstract class Predicate {
	public static final String[] integerOperators = {"=", "<>", ">", "<", ">=", "<="};
	public static final String[] stringOperators = {"=", "<>", ">", "<", ">=", "<=", "LIKE", "NOT LIKE"};
	public static final String[] dateOperators = {"=", "<>", ">", "<", ">=", "<=", "LIKE", "NOT LIKE"};
	
	protected PredicateComposition next;
	protected PredicateComposition previous;
	protected String contextEntity;
	
	protected Predicate parent;
	
	protected abstract void setNextPredicate(String logicalOperator, Predicate nextPredicate);
	protected abstract void setPreviousPredicate(String logicalOperator, Predicate previousPredicate);
	
	protected Predicate getLastPredicate() {
		Predicate returnPredicate = this;
		if (next != null) {
			returnPredicate = next.predicate.getLastPredicate();
		}
		
		return returnPredicate;
	}
}
