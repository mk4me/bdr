package motion.database;

public class Filter {
	private String name;
	private boolean selected;
	
	public Filter(String name) {
		this.name = name;
	}
	
	public Filter(String name, boolean selected) {
		this.name = name;
		this.selected = selected;
	}
	
	public String toString() {
		
		return this.name;
	}
	
	public boolean isSelected() {
		
		return this.selected;
	}
}
