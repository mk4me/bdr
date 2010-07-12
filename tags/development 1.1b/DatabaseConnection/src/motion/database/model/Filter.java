package motion.database.model;

import java.util.ArrayList;

import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;

public class Filter {
	private String name;
	private boolean selected;
	private SimplePredicate predicate;
	private static int id = 1;
	private ArrayList<FilterPredicate> filterPredicates = new ArrayList<FilterPredicate>();
	
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
	@Deprecated
	public SimplePredicate getPredicate() {	// Why deprecated?
		
		return this.predicate;
	}
	
	@Deprecated
	// CheckBoxNode has selection instead.
	public boolean isSelected() {
		
		return this.selected;
	}
	
	@Deprecated
	// CheckBoxNode has selection instead.
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	private static int newId() {
		
		return id++;
	}
	
	public ArrayList<FilterPredicate> getFilterPredicatesWCF() {
		if (this.filterPredicates.isEmpty()) {
			toFilterPredicateWCF();
		}
		
		return this.filterPredicates;
	}
	
	public void removeFilterPredicatesWCF() {
		this.filterPredicates = new ArrayList<FilterPredicate>();
	}
	
	private void toFilterPredicateWCF() {
		// Array of FilterPredicates, order of elements doesn't matter, except the first element which should be the GROUP predicate.
		// Create a GROUP for the filter (first predicate in the array).
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate parentPredicate = fillGroupFilterPredicateWCF();
		filterPredicates.add(parentPredicate);
		// Add filter predicates.
		filterPredicates.add(fillFilterPredicateWCF(this.predicate, parentPredicate, null));
		
		SimplePredicate currentPredicate = this.predicate;
		if (currentPredicate.getNextComposition() != null) {
			do {
				currentPredicate = (SimplePredicate) currentPredicate.getNextComposition().getPredicate();
				filterPredicates.add(fillFilterPredicateWCF(currentPredicate, parentPredicate, filterPredicates.get(filterPredicates.size()-1)));
			} while (currentPredicate.getNextComposition() != null);
		}
	}


	private motion.database.ws.basicQueriesServiceWCF.FilterPredicate fillFilterPredicateWCF(SimplePredicate currentPredicate, motion.database.ws.basicQueriesServiceWCF.FilterPredicate parentFilterPredicate, motion.database.ws.basicQueriesServiceWCF.FilterPredicate previousFilterPredicate) {
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate filter = new motion.database.ws.basicQueriesServiceWCF.FilterPredicate();
		filter.setPredicateID(Filter.newId());
		if (parentFilterPredicate == null) {
			filter.setParentPredicate(0);	// Not used anymore, parent not null.
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
	
	private motion.database.ws.basicQueriesServiceWCF.FilterPredicate fillGroupFilterPredicateWCF() {
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate groupFilter = new motion.database.ws.basicQueriesServiceWCF.FilterPredicate();
		groupFilter.setPredicateID(Filter.newId());
		groupFilter.setContextEntity("GROUP");
		groupFilter.setFeatureName(this.getName());
		groupFilter.setOperator("");
		groupFilter.setValue("");
		groupFilter.setAggregateFunction("");
		groupFilter.setAggregateEntity("");
		groupFilter.setNextOperator("");
		
		return groupFilter;
	}
	
	public void linkChildGroupFilterPredicates(
			motion.database.ws.basicQueriesServiceWCF.FilterPredicate branch,
			motion.database.ws.basicQueriesServiceWCF.FilterPredicate childBranch,
			ArrayList<FilterPredicate> filterPredicates) {
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate groupX = this.filterPredicates.get(0);
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate groupG = new motion.database.ws.basicQueriesServiceWCF.FilterPredicate();
		groupG.setPredicateID(Filter.newId());
		groupG.setContextEntity("GROUP");
		groupG.setFeatureName("SIBLING");
		groupG.setOperator("");
		groupG.setValue("");
		groupG.setAggregateFunction("");
		groupG.setAggregateEntity("");
		groupG.setNextOperator("");
		groupG.setParentPredicate(branch.getPredicateID());
		groupG.setPreviousPredicate(groupX.getPredicateID());
		
		groupX.setNextOperator("AND");
		childBranch.setParentPredicate(groupG.getPredicateID());
		
		groupX.setParentPredicate(branch.getPredicateID());
		
		filterPredicates.add(groupG);
	}
	
	public static motion.database.ws.basicQueriesServiceWCF.FilterPredicate createBranchGroup(motion.database.ws.basicQueriesServiceWCF.FilterPredicate parent) {
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate branch = new motion.database.ws.basicQueriesServiceWCF.FilterPredicate();
		branch.setPredicateID(Filter.newId());
		branch.setContextEntity("GROUP");
		branch.setFeatureName("BRANCH");
		branch.setOperator("");
		branch.setValue("");
		branch.setAggregateFunction("");
		branch.setAggregateEntity("");
		branch.setNextOperator("");
		branch.setParentPredicate(parent.getParentPredicate());
		
		return branch;
	}
	
	public static void linkSiblingBranches(motion.database.ws.basicQueriesServiceWCF.FilterPredicate previousBranch,
			motion.database.ws.basicQueriesServiceWCF.FilterPredicate branch) {
		previousBranch.setNextOperator("OR");
		branch.setPreviousPredicate(previousBranch.getPredicateID());
		branch.setParentPredicate(previousBranch.getParentPredicate());
	}
}