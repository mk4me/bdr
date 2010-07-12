package motion.applet.trees;

import motion.database.model.Filter;

public class FilterNode extends CheckBoxNode {
	private Filter filter;
	
	public FilterNode(Filter filter) {
		super(filter.getName(), false);
		this.filter = filter;
	}
	
	public Filter getFilter() {
		
		return this.filter;
	}
}