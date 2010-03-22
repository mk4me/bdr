package motion.applet.filter;

import motion.applet.filter.model.SimplePredicate;

public class Filter {
	private String name;
	private boolean selected;
	private SimplePredicate predicate;
	
	public Filter(String name) {
		this.name = name;
	}
	
	public Filter(String name, boolean selected) {
		this.name = name;
		this.selected = selected;
	}
	
	public Filter(String name, SimplePredicate predicate) {
		this.name = name;
		this.predicate = predicate;
	}
	
	public String toString() {
		
		return this.name;
	}
	
	public boolean isSelected() {
		
		return this.selected;
	}
}
