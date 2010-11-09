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
import motion.database.model.Session;

public class SessionMouseAdapter extends MouseAdapter {
	private static String MENU_CREATE_TRIAL = "Create new trial";
	private static String MENU_VIEW_TRIALS = "View trials";
	private static String MENU_VIEW_PERFORMERS = "View performers";
	private static String MENU_VIEW_SESSION_GROUPS = "View groups";
	private static String MENU_VIEW_FILES = "View files";
	
	private static String MENU_VIEW_TREE = "View as tree";

	private static String MENU_UPLOAD = "Upload file";
	private static String MENU_EDIT = "Edit";
	
	private RightSplitPanel rightPanel;
	
	public SessionMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			JMenuItem createTrialMenuItem = new JMenuItem(MENU_CREATE_TRIAL);
			popupMenu.add(createTrialMenuItem);
			
			createTrialMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showTrialDialog(recordId, null);
				}
			});
			// View Session trials context menu
			JMenuItem viewTrialsMenuItem = new JMenuItem(MENU_VIEW_TRIALS);
			popupMenu.add(viewTrialsMenuItem);
			
			viewTrialsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewTrials(recordId);
				}
			});
			// View Session performers context menu
			JMenuItem viewPerformersMenuItem = new JMenuItem(MENU_VIEW_PERFORMERS);
			popupMenu.add(viewPerformersMenuItem);
			
			viewPerformersMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewPerformers(recordId);
				}
			});
			// View Session groups context menu
			JMenuItem viewSessionGroupsMenuItem = new JMenuItem(MENU_VIEW_SESSION_GROUPS);
			popupMenu.add(viewSessionGroupsMenuItem);
			
			viewSessionGroupsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewSessionGroups(recordId);
				}
			});
			popupMenu.add(new JSeparator());
			JMenuItem viewSessionTreeMenuItem = new JMenuItem(MENU_VIEW_TREE);
			popupMenu.add(viewSessionTreeMenuItem);
			
			viewSessionTreeMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						rightPanel.getApplet().switchToSessionBrowser();
						rightPanel.getApplet().getSessionBrowserPanel().setSession( recordId );
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			
			popupMenu.add(new JSeparator());
			// Upload context menu.
			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showUploadDialog(EntityKind.session, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, EntityKind.session);
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
			viewTrials(recordId);
		}
	}
	
	private void viewTrials(int recordId) {
		rightPanel.showTable(EntityKind.trial, recordId);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void viewPerformers(int recordId) {
		rightPanel.showTable(EntityKind.performer, recordId);
		MotionApplet.setBrowsePanelVisible();
	}
	
	private void viewSessionGroups(int recordId) {
		rightPanel.showTable(EntityKind.sessionGroup, recordId);
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
					Session session = (Session) WebServiceInstance.getDatabaseConnection().getById(recordId, EntityKind.session);
					rightPanel.showSessionDialog(session);
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
