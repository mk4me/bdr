package motion.applet.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import motion.database.model.EntityKind;

public class BasicTableModel extends AbstractTableModel {
	protected ArrayList<ArrayList<Object>> contents = new ArrayList<ArrayList<Object>>();
	protected ArrayList<String> attributeNames = new ArrayList<String>();
	protected ArrayList<Class> classes = new ArrayList<Class>();
	protected ArrayList<Integer> recordIds = new ArrayList<Integer>();
	protected EntityKind entityKind;
	public int recordId;
	private int CHECKBOX_COLUMN = 0;
	
	public BasicTableModel() {
		
	}
	
	public EntityKind getEntityKind() {
		
		return this.entityKind;
	}
	
	protected void addCheckboxColumn() {
		this.attributeNames.add("");	// checkbox column
		this.classes.add(Boolean.class);
	}
	
	@Override
	public int getColumnCount() {
		if (this.contents.size() == 0) {
			return 0;
		} else {
			return this.contents.get(0).size();
		}
	}
	
	@Override
	public int getRowCount() {
		
		return this.contents.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		return this.contents.get(row).get(column);
	}
	
	public Class getColumnClass(int column) {
		
		return this.classes.get(column);
	}
	
	public String getColumnName(int column) {
		
		return this.attributeNames.get(column);
	}
	
	public int getRecordId(int row) {
		
		return this.recordIds.get(row);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return (columnIndex == CHECKBOX_COLUMN);	// enable columns for editing/checkboxes  
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == CHECKBOX_COLUMN) {
			this.contents.get(rowIndex).set(columnIndex, value);
		}
		//super.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public int[] getCheckedRecordIds() {
		int[] checkedRecordIds = new int[this.getRowCount()];
		int i = 0;
		int j = 0;
		for (ArrayList<Object> row : this.contents) {
			if ((Boolean) row.get(CHECKBOX_COLUMN) == Boolean.TRUE) {
				checkedRecordIds[j] = getRecordId(i);
				j++;
			}
			i++;
		}
		
		return Arrays.copyOf(checkedRecordIds, j);
	}
	
	public void setCheckedRecords(int[] recordIds) {
		if (this.contents.isEmpty() == false) {
			int j = 0;
			for (int id : recordIds) {
				for (int i = 0; i < recordIds.length; i++) {
					if (recordIds[i] == id) {
						contents.get(j).set(CHECKBOX_COLUMN, Boolean.TRUE);
					}
				}
				j++;
			}
		}
	}
	
	public void removeCheckedRecords() {
		Iterator<ArrayList<Object>> i = this.contents.iterator();
		Iterator<Integer> j = this.recordIds.iterator();
		while (i.hasNext()) {
			ArrayList<Object> row = i.next();
			j.next();
			if ((Boolean) row.get(CHECKBOX_COLUMN) == Boolean.TRUE) {
				i.remove();
				j.remove();
			}
		}
		this.fireTableDataChanged();
	}
	
	public void refresh() {
		contents.clear();
		attributeNames.clear();
		classes.clear();
		recordIds.clear();
	}
}
