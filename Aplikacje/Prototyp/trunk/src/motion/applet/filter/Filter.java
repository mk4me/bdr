package motion.applet.filter;

import motion.applet.filter.model.SimplePredicate;

public class Filter {
	private String name;
	private boolean selected;
	private SimplePredicate predicate;
	
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
}
