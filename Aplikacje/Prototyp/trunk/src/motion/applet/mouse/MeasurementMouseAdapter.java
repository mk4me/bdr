package motion.applet.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import motion.applet.MotionApplet;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.RightSplitPanel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityKind;
import motion.database.model.Measurement;

public class MeasurementMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_MEASUREMENT_CONFIGURATIONS = "View measurement configurations";
	private static String MENU_EDIT = "Edit";
	
	private RightSplitPanel rightPanel;
	
	public MeasurementMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			// No operation to view MeasurementConfigurations from Measurement
			/*
			// View Measurement Configurations context menu
			JMenuItem viewMeasurementConfigurationsMenuItem = new JMenuItem(MENU_VIEW_MEASUREMENT_CONFIGURATIONS);
			popupMenu.add(viewMeasurementConfigurationsMenuItem);
			
			viewMeasurementConfigurationsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewMeasurementConfigurations(recordId);
				}
			});
			
			popupMenu.add(new JSeparator());
			*/
			// Edit
			JMenuItem editMenuItem = new JMenuItem(MENU_EDIT);
			popupMenu.add(editMenuItem);
			
			editMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					edit(recordId);
				}
			});
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewMeasurementConfigurations(recordId);
		}
	}
	
	private void viewMeasurementConfigurations(int recordId) {
		rightPanel.showTable(EntityKind.measurement_conf, recordId);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void edit(final int recordId) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					Measurement measurement = (Measurement) WebServiceInstance.getDatabaseConnection().getById(recordId, EntityKind.measurement);
					rightPanel.showMeasurementDialog(recordId, measurement);
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
			}
		};
		worker.execute();
	}
}
