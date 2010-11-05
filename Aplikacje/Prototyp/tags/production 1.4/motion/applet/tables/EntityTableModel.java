package motion.applet.tables;

import javax.swing.table.AbstractTableModel;

import motion.database.DbElementsList;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;

public class EntityTableModel extends AbstractTableModel {

	private DbElementsList<? extends GenericDescription<?>> data;
	EntityKind entityKind;
	String [] columns;
	String [] visibleColumns;
	
	public EntityTableModel(DbElementsList<? extends GenericDescription<?>> data) {
		this.data = data;
		if (data.size()>0)
		{
			entityKind = data.get(0).entityKind;
			columns = entityKind.getKeys();
		}
		else
			columns = new String[0];
		visibleColumns = columns;
	}
	
	public EntityTableModel(DbElementsList<? extends GenericDescription<?>> data,
			String[] visibleColumns) {
		this(data);
		setVisibleColumns(visibleColumns);
	}

	public void setVisibleColumns(String[] visibleColumnNames)
	{
		this.visibleColumns = visibleColumnNames;
	}

	@Override
	public int getColumnCount() {
		return visibleColumns.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get( rowIndex ).get( visibleColumns[columnIndex] ).value;
	}

}
