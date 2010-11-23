package motion.applet.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import motion.applet.tables.AttributeTableModel;
import motion.applet.tables.BasicTableModel;
import motion.database.model.EntityKind;

public class EntityAssignmentPanel extends JPanel {
	private JTable entityTable;
	private EntityKind entityKind;
	
	public EntityAssignmentPanel(EntityKind entityKind) {
		super();
		entityTable = new JTable();
		entityTable.setModel(new AttributeTableModel(entityKind));
		
		this.add(new JScrollPane(entityTable));
	}
	
	public int[] getSelectedRecords() {
		
		return ((BasicTableModel) entityTable.getModel()).getCheckedRecordIds();
	}
	
	public void setSelectedRecords(int[] recordIds) {
		((BasicTableModel) entityTable.getModel()).setCheckedRecords(recordIds);
	}
	
	public void refreshTable() {
		((AttributeTableModel) entityTable.getModel()).refresh();
	}
}