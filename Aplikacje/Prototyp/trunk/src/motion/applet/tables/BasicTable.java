package motion.applet.tables;

import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import motion.applet.database.Connector;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.AttributeName;
import motion.database.model.EntityAttribute;
import motion.database.model.Performer;
import motion.database.model.Session;

public class BasicTable extends AbstractTableModel {
	private Object[][] contents;
	private String[] columnNames;
	private Class[] columnClasses;
	private ArrayList<ArrayList<Object>> contents2 = new ArrayList<ArrayList<Object>>();
	private java.sql.Connection connection;
	private TableName tableName;
	private String databaseName;
	
	public BasicTable(TableName tableName) { //public BasicTable(Connector connector, TableName tableName) {
		super();
		this.tableName = tableName;
		//this.databaseName = connector.getDatabaseName();
		//this.connection = connector.openConnection();
		//getTableContents();
		getTableContentsFromAttributes();
		//connector.closeConnection();
	}
	
	private void getTableContents() throws SQLException {
		DatabaseMetaData databaseMetaData = this.connection.getMetaData();
		ResultSet resultSet = databaseMetaData.getColumns(this.databaseName, null, this.tableName.getTableName(), null);
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
		resultSet = statement.executeQuery("SELECT * FROM " + tableName.getTableName());
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
	
	private void getTableContentsFromAttributes() {
		if (tableName.equals(TableNamesInstance.PERFORMER)) {
			listPerformers();
		} else if (tableName.equals(TableNamesInstance.SESSION)) {
			listSessions();
		}
	}
	
	private void listPerformers() {
		try {
			DbElementsList<Performer> performers = WebServiceInstance.getDatabaseConnection().listPerformersWithAttributes();
			for (Performer p : performers) {
				ArrayList<Object> cellList = new ArrayList<Object>();
				for (AttributeName a : tableName.getAllAttributes()) {
					EntityAttribute entityAttribute = p.get(a.toString());
					if (entityAttribute != null) {
						cellList.add(entityAttribute.value);
					} else {
						cellList.add(null);
					}
				}
				this.contents2.add(cellList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listSessions() {
		try {
			DbElementsList<Session> sessions = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(1);
			for (Session s : sessions) {
				ArrayList<Object> cellList = new ArrayList<Object>();
				for (AttributeName a : tableName.getAllAttributes()) {
					EntityAttribute entityAttribute = s.get(a.toString());
					if (entityAttribute != null) {
						cellList.add(entityAttribute.value);
					} else {
						cellList.add(null);
					}
				}
				this.contents2.add(cellList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public int getColumnCount() {
		if (this.contents2.size() == 0) {
			return 0;
		} else {
			return this.contents2.get(0).size();
		}
	}
	
	@Override
	public int getRowCount() {
		
		return this.contents2.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		return this.contents2.get(row).get(column);
	}
	/*
	public Class getColumnClass(int column) {
		
		return this.columnClasses[column];
	}
	*/
	
	public String getColumnName(int column) {
		
		return this.tableName.getAllAttributes().get(column).toString();
	}
	
	/*
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
	}*/
}
