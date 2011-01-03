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
import motion.database.model.Trial;

public class TrialMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_MEASUREMENT = "View measurements";
	private static String MENU_VIEW_FILES = "View files";
	private static String MENU_UPLOAD = "Upload file";
	private static String MENU_EDIT = "Edit";
	
	private RightSplitPanel rightPanel;
	
	public TrialMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {	// Right click.
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {
			JPopupMenu popupMenu = new JPopupMenu();
			
			// View Trial measurement context menu
			JMenuItem viewMeasurementsMenuItem = new JMenuItem(MENU_VIEW_MEASUREMENT);
			popupMenu.add(viewMeasurementsMenuItem);
			
			viewMeasurementsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewMeasurements(recordId);
				}
			});
			
			popupMenu.add(new JSeparator());
			// Upload context menu.
			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showUploadDialog(EntityKind.trial, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, EntityKind.trial);
				}
			});
			
			popupMenu.add(new JSeparator());
			
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
			viewMeasurements(recordId);
		}
	}
	
	private void viewFiles(int recordId, EntityKind entityKind) {
		rightPanel.showTable(EntityKind.file, recordId, entityKind);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void viewMeasurements(int recordId) {
		rightPanel.showTable(EntityKind.measurement, recordId, EntityKind.trial);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void edit(final int recordId) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					Trial trial = (Trial) WebServiceInstance.getDatabaseConnection().getById(recordId, EntityKind.trial);
					rightPanel.showTrialDialog(recordId, trial);
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
