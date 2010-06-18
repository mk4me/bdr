package motion.applet.tables;

import java.util.ArrayList;

import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.BottomSplitPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.AttributeName;
import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttribute;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;

public class BasicTable extends AbstractTableModel {
	private ArrayList<ArrayList<Object>> contents = new ArrayList<ArrayList<Object>>();
	private ArrayList<String> attributeNames = new ArrayList<String>();
	private ArrayList<Integer> recordIds = new ArrayList<Integer>();
	public TableName tableName;
	public int recordId;
	public TableName fromTableName;	// Used for listing files for different tables.
	
	public BasicTable(TableName tableName) {
		super();
		this.tableName = tableName;
		this.recordId = -1;
		
		getTableContentsFromAttributes();
	}
	
	public BasicTable(TableName tableName, int recordId) {
		super();
		this.tableName = tableName;
		this.recordId = recordId;
		
		getTableContentsFromAttributes();
	}
	
	public BasicTable(TableName tableName, int recordId, TableName fromTableName) {
		super();
		this.tableName = tableName;
		this.recordId = recordId;
		this.fromTableName = fromTableName;
		
		getTableContentsFromAttributes();
	}
	
	private void getTableContentsFromAttributes() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					if (tableName.equals(TableNamesInstance.PERFORMER)) {
						listPerformers();
					} else if (tableName.equals(TableNamesInstance.SESSION)) {
						listSessions();
					} else if (tableName.equals(TableNamesInstance.TRIAL)) {
						listTrials();
					} else if (tableName.equals(TableNamesInstance.FILE)) {
						listFiles();
					}
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				BasicTable.this.fireTableStructureChanged();
			}
		};
		worker.execute();
	}
	
	private void listPerformers() throws Exception {
		DbElementsList<Performer> performers = WebServiceInstance.getDatabaseConnection().listLabPerformersWithAttributes(AppletToolBar.getLabId());
		for (Performer p : performers) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : tableName.getSelectedAttributes(BottomSplitPanel.getCheckedPerformerAttributes())) {
				this.attributeNames.add(a.toString());
				EntityAttribute entityAttribute = p.get(a.toString());
				if (entityAttribute != null) {
					cellList.add(entityAttribute.value);
				} else {
					cellList.add(null);
				}
			}
			this.recordIds.add(Integer.parseInt(p.get(PerformerStaticAttributes.performerID.toString()).value.toString()));
			this.contents.add(cellList);
		}
	}
	
	private void listSessions() throws Exception {
		DbElementsList<Session> sessions = new DbElementsList<Session>();
		if (this.recordId > -1) {
			sessions = WebServiceInstance.getDatabaseConnection().listPerformerSessionsWithAttributes(this.recordId);
		} else {
			sessions = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
		}
		for (Session s : sessions) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : tableName.getSelectedAttributes(BottomSplitPanel.getCheckedSessionAttributes())) {
				this.attributeNames.add(a.toString());
				EntityAttribute entityAttribute = s.get(a.toString());
				if (entityAttribute != null) {
					cellList.add(entityAttribute.value);
				} else {
					cellList.add(null);
				}
			}
			this.recordIds.add(Integer.parseInt(s.get(SessionStaticAttributes.sessionID.toString()).value.toString()));
			this.contents.add(cellList);
		}
	}
	
	private void listTrials() throws Exception {
		DbElementsList<Trial> trials = new DbElementsList<Trial>();
		if (this.recordId > -1) {
			trials = WebServiceInstance.getDatabaseConnection().listSessionTrialsWithAttributes(this.recordId);
		}
		
		for (Trial t : trials) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : tableName.getSelectedAttributes(BottomSplitPanel.getCheckedTrialAttributes())) {
				this.attributeNames.add(a.toString());
				EntityAttribute entityAttribute = t.get(a.toString());
				if (entityAttribute != null) {
					cellList.add(entityAttribute.value);
				} else {
					cellList.add(null);
				}
			}
			this.recordIds.add(Integer.parseInt(t.get(TrialStaticAttributes.trialID.toString()).value.toString()));
			this.contents.add(cellList);
		}
	}
	
	private void listFiles() throws Exception {
		DbElementsList<DatabaseFile> files = new DbElementsList<DatabaseFile>();
		if (this.recordId > -1) {
			if (fromTableName.equals(TableNamesInstance.PERFORMER)) {
				files = WebServiceInstance.getDatabaseConnection().listPerformerFiles(this.recordId);
			} else if (fromTableName.equals(TableNamesInstance.SESSION)) {
				files = WebServiceInstance.getDatabaseConnection().listSessionFiles(this.recordId);
			} else if (fromTableName.equals(TableNamesInstance.TRIAL)) {
				files = WebServiceInstance.getDatabaseConnection().listTrialFiles(this.recordId);
			}
		}
		
		for (DatabaseFile f : files) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			
			for (AttributeName a : tableName.getAllAttributes()) {
				this.attributeNames.add(a.toString());
				EntityAttribute entityAttribute = f.get(a.toString());
				if (entityAttribute != null) {
					cellList.add(entityAttribute.value);
				} else {
					cellList.add(null);
				}
			}
			this.recordIds.add(Integer.parseInt(f.get(DatabaseFileStaticAttributes.fileID.toString()).value.toString()));
			this.contents.add(cellList);
		}
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
	/*
	public Class getColumnClass(int column) {
		
		return this.columnClasses[column];
	}
	*/
	
	public String getColumnName(int column) {
		
		return this.attributeNames.get(column);
	}
	
	public int getRecordId(int row) {
		
		return this.recordIds.get(row);
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
