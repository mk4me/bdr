package motion.applet.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import motion.applet.dialogs.ExceptionDialog;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.TrialStaticAttributes;

public class QueryTableModel extends BasicTableModel {
	private List<GenericDescription> result;
	
	public QueryTableModel(List<GenericDescription> result) {
		this.result = result;
		if (result.size() > 0) {
			this.entityKind = result.get(0).entityKind;
			getTableContents();
		}
	}
	
	private void getTableContents() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					//TODO: Needs improvement.
					//BasicTable.this.tableName = TableNamesInstance.toTableName(result.get(0).entityKind.toString());
					//BasicTable.this.tableName = TableNamesInstance.SESSION;
					if (entityKind != null) {
						// Don't filter attributes.
						ArrayList<EntityAttribute> attributes = entityKind.getAllAttributeCopies();
						for (EntityAttribute a : attributes) {
							attributeNames.add(a.name);
							classes.add(a.getAttributeClass());
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
							for (GenericDescription<?> r : result) {
								ArrayList<Object> cellList = new ArrayList<Object>();
								cellList.add(new Boolean(false));	// checkboxes initially unchecked
								for (EntityAttribute a : attributes) {
									EntityAttribute entityAttribute = r.get(a.name);
									if (entityAttribute != null) {
										cellList.add(entityAttribute.value);
									} else {
										cellList.add(null);
									}
								}
								recordIds.add(r.getId());
								contents.add(cellList);
							}
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
				fireTableStructureChanged();
			}
		};
		worker.execute();
	}
	
	public void refresh() {
		super.refresh();
		getTableContents();
		fireTableStructureChanged();
	}
}
