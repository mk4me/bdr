package motion.database.model;

import java.util.ArrayList;

import motion.database.ws.basicQueriesService.FilterPredicate;

public class Filter {
	private String name;
	private boolean selected;
	private SimplePredicate predicate;
	private static int id = 1;
	
	public Filter(String name) {
		this.name = name;
	}
	
	public Filter(String name, SimplePredicate predicate) {
		this.name = name;
		this.predicate = predicate;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public SimplePredicate getPredicate() {
		
		return this.predicate;
	}
	
	public boolean isSelected() {
		
		return this.selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	private static int newId() {
		
		return id++;
	}
	
	public FilterPredicate[] toFilterPredicate() {
		ArrayList<FilterPredicate> resultFilterPredicates = new ArrayList<FilterPredicate>();
		resultFilterPredicates.add(fillFilterPredicate(this.predicate, null, null));
		
		SimplePredicate currentPredicate = this.predicate;
		if (currentPredicate.getNextComposition() != null) {
			do {
				currentPredicate = (SimplePredicate) currentPredicate.getNextComposition().getPredicate();
				resultFilterPredicates.add(fillFilterPredicate(currentPredicate, null, resultFilterPredicates.get(resultFilterPredicates.size()-1)));
			} while (currentPredicate.getNextComposition() != null);
		}
		
		return resultFilterPredicates.toArray(new FilterPredicate[1]);
	}
	
	private FilterPredicate fillFilterPredicate(SimplePredicate currentPredicate, FilterPredicate parentFilterPredicate, FilterPredicate previousFilterPredicate) {
		FilterPredicate filter = new FilterPredicate();
		filter.setPredicateID(Filter.newId());
		if (parentFilterPredicate == null) {
			filter.setParentPredicate(0);
		} else {
			filter.setParentPredicate(parentFilterPredicate.getPredicateID());
		}
		filter.setContextEntity(currentPredicate.getContextEntity().toLowerCase());
		if (previousFilterPredicate == null) {
			filter.setPreviousPredicate(0);
		} else {
			filter.setPreviousPredicate(previousFilterPredicate.getPredicateID());
		}
		if (currentPredicate.getPreviousComposition() == null) {
			filter.setNextOperator("");
		} else {
			//filter.setNextOperator(currentPredicate.getPreviousComposition().getLogicalOperator());
			previousFilterPredicate.setNextOperator(currentPredicate.getPreviousComposition().getLogicalOperator());
			filter.setNextOperator("");
		}
		filter.setFeatureName(currentPredicate.getFeature().toString());
		filter.setOperator(currentPredicate.getOperator());
		filter.setValue(currentPredicate.getValue());
		filter.setAggregateEntity("");
		filter.setAggregateFunction("");
		
		return filter;
	}
}
