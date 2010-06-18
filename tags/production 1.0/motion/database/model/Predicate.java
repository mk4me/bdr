package motion.database.model;

public abstract class Predicate {
	public static final String[] integerOperators = {"=", "<>", ">", "<", ">=", "<="};
	public static final String[] stringOperators = {"=", "<>", ">", "<", ">=", "<=", "LIKE", "NOT LIKE"};
	public static final String[] dateOperators = {"=", "<>", ">", "<", ">=", "<=", "LIKE", "NOT LIKE"};
	
	protected PredicateComposition next;
	protected PredicateComposition previous;
	protected String contextEntity;
	
	protected abstract void setNextPredicate(String logicalOperator, Predicate nextPredicate);
	protected abstract void setPreviousPredicate(String logicalOperator, Predicate previousPredicate);
}
