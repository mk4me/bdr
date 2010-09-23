package motion.applet.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import motion.applet.tables.AttributeTableModel;
import motion.applet.tables.BasicTableModel;
import motion.database.model.EntityKind;

public class PerformerAssignmentPanel extends JPanel {
	private JTable performerTable;
	
	public PerformerAssignmentPanel() {
		super();
		performerTable = new JTable();
		performerTable.setModel(new AttributeTableModel(EntityKind.performer));
		
		this.add(new JScrollPane(performerTable));
	}
	
	public int[] getSelectedPerformers() {
		
		return ((BasicTableModel) performerTable.getModel()).getCheckedRecordIds();
	}
}