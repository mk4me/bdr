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
import motion.applet.MotionAppletFrame;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.SessionAssignmentDialog;
import motion.applet.panels.RightSplitPanel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityKind;
import motion.database.model.Performer;

public class PerformerMouseAdapter extends MouseAdapter {
	private static String MENU_CREATE_SESSION = "Create new session";
	private static String MENU_VIEW_SESSIONS = "View sessions";
	//private static String MENU_VIEW_FILES = "View files";
	private static String MENU_ASSIGN_TO_SESSION = "Assign to session";
	// No performer file uploading since v.1.3
	//private static String MENU_UPLOAD = "Upload file";
	private static String MENU_EDIT = "Edit";
	
	private RightSplitPanel rightPanel;
	
	public PerformerMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		final int[] recordIds = rightPanel.getSelectedRecords((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			JMenuItem createSessionMenuItem = new JMenuItem(MENU_CREATE_SESSION);
			popupMenu.add(createSessionMenuItem);
				
			createSessionMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showSessionDialog(null);
				}
			});
			// View Performer sessions context menu
			JMenuItem viewSessionsMenuItem = new JMenuItem(MENU_VIEW_SESSIONS);
			popupMenu.add(viewSessionsMenuItem);
			
			viewSessionsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewSessions(recordId);
				}
			});
			// Assign Performer to session context menu
			JMenuItem assignToSessionMenuItem = new JMenuItem(MENU_ASSIGN_TO_SESSION);
			popupMenu.add(assignToSessionMenuItem);
			
			assignToSessionMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SessionAssignmentDialog sessionAssignmentDialog = new SessionAssignmentDialog(recordIds);
					sessionAssignmentDialog.setVisible(true);
				}
			});
			
//			popupMenu.add(new JSeparator());
			// Upload context menu.
//			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
//			popupMenu.add(uploadMenuItem);
//			
//			uploadMenuItem.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					rightPanel.showUploadDialog(EntityKind.performer, recordId);
//				}
//			});
			
			// View files context menu
//			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
//			popupMenu.add(viewFilesMenuItem);
//			
//			viewFilesMenuItem.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					viewFiles(recordId, EntityKind.performer);
//				}
//			});
			
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
			viewSessions(recordId);
		}
	}
	
	private void viewSessions(int recordId) {
		rightPanel.showTable(EntityKind.session, recordId);
		rightPanel.clearTrialTable();
		rightPanel.clearFileTable();
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void viewFiles(int recordId, EntityKind entityKind) {
		rightPanel.showTable(EntityKind.file, recordId, entityKind);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void edit(final int recordId) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					Performer performer = (Performer) WebServiceInstance.getDatabaseConnection().getById(recordId, EntityKind.performer);
					rightPanel.showPerformerDialog(performer);
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
