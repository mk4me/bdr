package motion.applet.tables;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.BottomSplitPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;

public class AttributeTableModel extends BasicTableModel {
	public EntityKind fromEntityKind;	// Used for listing files for different tables.
	
	public AttributeTableModel(EntityKind entityKind) {
		this.entityKind = entityKind;
		this.recordId = -1;
		getTableContents();
	}
	
	public AttributeTableModel(EntityKind entityKind, int recordId) {
		this.entityKind = entityKind;
		this.recordId = recordId;
		
		getTableContents();
	}
	
	public AttributeTableModel(EntityKind entityKind, int recordId, EntityKind fromEntityKind) {
		this.entityKind = entityKind;
		this.recordId = recordId;
		this.fromEntityKind = fromEntityKind;
		
		getTableContents();
	}
	
	private void getTableContents() {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					DbElementsList<? extends GenericDescription<?>> records = new DbElementsList<GenericDescription<?>>();
					ArrayList<String> selectedAttributes = BottomSplitPanel.getCheckedAttributes(entityKind);
					ArrayList<EntityAttribute> attributes;
					if (selectedAttributes != null) {
						attributes = entityKind.getSelectedAttributeCopies(selectedAttributes);
					} else {
						attributes = entityKind.getAllAttributeCopies();
					}
					
					for (EntityAttribute a : attributes) {
						attributeNames.add(a.name);
						classes.add(a.getAttributeClass());
					}
					
					if (entityKind.equals(EntityKind.performer)) {
						if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listSessionPerformersWithAttributes(recordId);
						} else {
							//records = WebServiceInstance.getDatabaseConnection().listLabPerformersWithAttributes(AppletToolBar.getLabId());	//Performers not grouped by labs.
							records = WebServiceInstance.getDatabaseConnection().listPerformersWithAttributes();
						}
					} else if (entityKind.equals(EntityKind.session)) {
						if (recordId > -1 && fromEntityKind != null) {
							if (fromEntityKind.equals(EntityKind.sessionGroup)) {
								records = WebServiceInstance.getDatabaseConnection().listGroupSessions(recordId);
							}
						} else if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listPerformerSessionsWithAttributes(recordId);
						} else {
							records = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
						}
					} else if (entityKind.equals(EntityKind.trial)) {
						if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listSessionTrialsWithAttributes(recordId);
						}
					} else if (entityKind.equals(EntityKind.file)) {
						if (recordId > -1 && fromEntityKind != null) {
							records = WebServiceInstance.getDatabaseConnection().listFiles(recordId, fromEntityKind);
						}
					} else if (entityKind.equals(EntityKind.measurement_conf)) {
						records = WebServiceInstance.getDatabaseConnection().listMeasurementConfigurationsWithAttributes();
					} else if (entityKind.equals(EntityKind.sessionGroup)) {
						if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listSessionSessionGroups(recordId);
						} else {
							records = WebServiceInstance.getDatabaseConnection().listSessionGroupsDefined();
						}
					} else if (entityKind.equals(EntityKind.measurement)) {
						if (recordId > -1 && fromEntityKind != null) {
							if (fromEntityKind.equals(EntityKind.trial)) {
								records = WebServiceInstance.getDatabaseConnection().listTrialMeasurementsWithAttributes(recordId);
							} else if (fromEntityKind.equals(EntityKind.measurement_conf)) {
								records = WebServiceInstance.getDatabaseConnection().listMeasurementConfMeasurementsWithAttributes(recordId);
							}
						}
					}
					
					for (GenericDescription<?> r : records) {
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
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				return null;
			}
			
			@Override
			protected void done() {
				AttributeTableModel.this.fireTableStructureChanged();
				//table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor());
				//table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  
			}
		};
		worker.execute();
	}
	
	public void refresh() {
		super.refresh();
		getTableContents();
		fireTableStructureChanged();
	}
	
	public String getColumnName(int column) {
		String attribute = this.attributeNames.get(column);
		// Show/hide columnNames
		//if (BottomSplitPanel.isCheckedAttribute(tableName, attribute)) {
			return attribute;
		//} else {
			//return "";
		//}
	}
}
