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

import motion.applet.MotionAppletFrame;
import motion.applet.panels.RightSplitPanel;
import motion.database.model.EntityKind;

public class PerformerMouseAdapter extends MouseAdapter {
	private static String MENU_CREATE_SESSION = "Create new session";
	private static String MENU_VIEW_SESSIONS = "View sessions";
	private static String MENU_VIEW_FILES = "View files";
	private static String MENU_UPLOAD = "Upload file";
	
	private RightSplitPanel rightPanel;
	
	public PerformerMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO: get JTable from e
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			JMenuItem createSessionMenuItem = new JMenuItem(MENU_CREATE_SESSION);
			popupMenu.add(createSessionMenuItem);
				
			createSessionMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showSessionDialog(recordId);
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
			
			popupMenu.add(new JSeparator());
			// Upload context menu.
			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showUploadDialog(EntityKind.performer, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, EntityKind.performer);
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
		MotionAppletFrame.setBrowsePanelVisible();
	}
	
	private void viewFiles(int recordId, EntityKind entityKind) {
		rightPanel.showTable(EntityKind.session, recordId, entityKind);
		MotionAppletFrame.setBrowsePanelVisible();
	}
}
