package motion.applet.tables;

import java.util.ArrayList;
import java.util.List;

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
import motion.database.model.GenericResult;
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
	
	public BasicTable(List<GenericResult> result) {
		getTableContentsFromResult(result);
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
		ArrayList<AttributeName> attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedPerformerAttributes());
		for (AttributeName a : attributes) {
			this.attributeNames.add(a.toString());
		}
		for (Performer p : performers) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : attributes) {
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
		ArrayList<AttributeName> attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedSessionAttributes());
		for (AttributeName a : attributes) {
			this.attributeNames.add(a.toString());
		}
		if (this.recordId > -1) {
			sessions = WebServiceInstance.getDatabaseConnection().listPerformerSessionsWithAttributes(this.recordId);
		} else {
			sessions = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
		}
		for (Session s : sessions) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : attributes) {
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
		ArrayList<AttributeName> attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedTrialAttributes());
		for (AttributeName a : attributes) {
			this.attributeNames.add(a.toString());
		}
		if (this.recordId > -1) {
			trials = WebServiceInstance.getDatabaseConnection().listSessionTrialsWithAttributes(this.recordId);
		}
		
		for (Trial t : trials) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			for (AttributeName a : attributes) {
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
		
		ArrayList<AttributeName> attributes = tableName.getAllAttributes();
		for (AttributeName a : attributes) {
			this.attributeNames.add(a.toString());
		}
		for (DatabaseFile f : files) {
			ArrayList<Object> cellList = new ArrayList<Object>();
			
			for (AttributeName a : attributes) {
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
	
	private void getTableContentsFromResult(final List<GenericResult> result) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try { //TODO: Needs improvement.
					//BasicTable.this.tableName = TableNamesInstance.toTableName(result.get(0).entityKind.toString());
					//BasicTable.this.tableName = TableNamesInstance.SESSION;
					if (result.get(0).keySet().contains("FirstName")) {
						BasicTable.this.tableName = TableNamesInstance.PERFORMER;
					} else if (result.get(0).keySet().contains("SessionDate")) {
						BasicTable.this.tableName = TableNamesInstance.SESSION;
					} else if (result.get(0).keySet().contains("Duration")) {
						BasicTable.this.tableName = TableNamesInstance.TRIAL;
					}
					
					// Don't filter attributes.
					ArrayList<AttributeName> attributes = tableName.getAllAttributes();
					for (AttributeName a : attributes) {
						//TODO: sessionID / SessionID
						BasicTable.this.attributeNames.add(a.toString().toLowerCase());
					}
					
					/*//Filter attributes from view configuration.
					ArrayList<AttributeName> attributes = null;
					if (tableName.equals(TableNamesInstance.PERFORMER)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedPerformerAttributes());
					} else if (tableName.equals(TableNamesInstance.SESSION)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedSessionAttributes());
					} else if (tableName.equals(TableNamesInstance.TRIAL)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedTrialAttributes());
					}*/
					
					if (attributes != null) {
						/*//Filter attributes from view configuration.
						for (String key : result.get(0).keySet()) {
							for (AttributeName a : attributes) {
								//TODO: sessionID / SessionID
								if (a.toString().toLowerCase().equals(key.toLowerCase())) {
									BasicTable.this.attributeNames.add(key);
								}
							}
						}
						*/
						
						for (GenericResult r : result) {
							Object[] cellList = new Object[attributeNames.size()];
							
							for (String key : r.keySet()) {
								//TODO: sessionID / SessionID
								int i = attributeNames.indexOf(key.toLowerCase());
								if (i >= 0)
									cellList[i] = r.get(key).value;
							}
							//TODO: sessionID / SessionID
							if (tableName.equals(TableNamesInstance.PERFORMER)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("PerformerID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.SESSION)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("SessionID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.TRIAL)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("TrialID").value.toString()));
							}
							ArrayList<Object> list = new ArrayList<Object>();
							for (int i = 0; i < cellList.length; i++) {
								list.add(cellList[i]);
							}
							BasicTable.this.contents.add(list);
						}
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
