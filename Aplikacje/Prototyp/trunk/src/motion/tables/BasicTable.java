package motion.tables;

import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import motion.database.Connector;

public class BasicTable extends AbstractTableModel {
	private Object[][] contents;
	private String[] columnNames;
	private Class[] columnClasses;
	private java.sql.Connection connection;
	private String tableName;
	private String databaseName;
	
	public BasicTable(Connector connector, String tableName) throws SQLException {
		super();
		this.tableName = tableName;
		this.databaseName = connector.getDatabaseName();
		this.connection = connector.openConnection();
		getTableContents();
		connector.closeConnection();
	}
	
	private void getTableContents() throws SQLException {
		DatabaseMetaData databaseMetaData = this.connection.getMetaData();
		ResultSet resultSet = databaseMetaData.getColumns(this.databaseName, null, this.tableName, null);
		ArrayList<String> columnNameList = new ArrayList<String>();
		ArrayList<Object> columnClassList = new ArrayList<Object>();
		while (resultSet.next()) {
			columnNameList.add(resultSet.getString("COLUMN_NAME"));
			int dataType = resultSet.getInt("DATA_TYPE");
			
			switch (dataType) {
			case Types.INTEGER:
				columnClassList.add(Integer.class);
				break;
			case Types.FLOAT:
				columnClassList.add(Float.class);
				break;
			case Types.DOUBLE:
			case Types.REAL:
				columnClassList.add(String.class);
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				columnClassList.add(Date.class);
				break;
			default:
				columnClassList.add(String.class);
				break;
			}
		}
		this.columnNames = new String[columnNameList.size()];
		columnNameList.toArray(this.columnNames);
		this.columnClasses = new Class[columnClassList.size()];
		columnClassList.toArray(this.columnClasses);
		Statement statement = this.connection.createStatement();
		resultSet = statement.executeQuery("SELECT * FROM " + tableName);
		ArrayList<Object> rowList = new ArrayList<Object>();
		while (resultSet.next()) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			
			for (int i = 0; i < this.columnClasses.length; i++) {
				Object cellValue = null;
				if (this.columnClasses[i] == String.class) {
					cellValue = resultSet.getString(this.columnNames[i]);
				} else if (this.columnClasses[i] == Integer.class) {
					cellValue = new Integer(resultSet.getInt(this.columnNames[i]));
				} else if (this.columnClasses[i] == Float.class) {
					cellValue = new Float(resultSet.getFloat(this.columnNames[i]));	//getInt
				} else if (this.columnClasses[i] == Double.class) {
					cellValue = new Double(resultSet.getDouble(this.columnNames[i]));
				} else if (this.columnClasses[i] == java.sql.Date.class) {
					cellValue = resultSet.getDate(this.columnNames[i]);
				} else {
					//
				}
				cellList.add (cellValue);
			}
			Object[] cells = cellList.toArray();
			rowList.add(cells);
		}
		this.contents = new Object[rowList.size()] [];
		for (int i = 0; i < this.contents.length; i++) {
			this.contents[i] = (Object[]) rowList.get(i);
		}
		
		// 1. Close the ResultSet
		// 2. Close the Statement
		// 3. Close the Connection
		resultSet.close();
		statement.close();
	}

	@Override
	public int getColumnCount() {
		if (this.contents.length == 0) {
			return 0;
		} else {
			return this.contents[0].length;
		}
	}

	@Override
	public int getRowCount() {
		
		return this.contents.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return this.contents[row][column];
	}
	
	public Class getColumnClass(int column) {
		return this.columnClasses[column];
	}
	
	public String getColumnName(int column) {
		return this.columnNames[column];
	}
}
