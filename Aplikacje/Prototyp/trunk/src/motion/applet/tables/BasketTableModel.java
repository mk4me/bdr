package motion.applet.tables;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.BottomSplitPanel;
import motion.database.DbElementsList;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;

public class BasketTableModel extends BasicTableModel {
	private DbElementsList<? extends GenericDescription<?>> records;
	public String fromBasket;	// Used for baskets.
	
	public BasketTableModel(EntityKind entityKind, DbElementsList<? extends GenericDescription<?>> records, String fromBasket) {
		this.records = records;
		this.entityKind =  entityKind;
		this.fromBasket = fromBasket;
		getTableContentsFromRecords();
	}
	
	private void getTableContentsFromRecords() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
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
					
					if (attributes != null) {
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
		getTableContentsFromRecords();
		fireTableStructureChanged();
	}
}
