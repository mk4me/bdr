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

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.panels.RightSplitPanel;

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
					rightPanel.showUploadDialog(TableNamesInstance.PERFORMER, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, TableNamesInstance.PERFORMER);
				}
			});

			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewSessions(recordId);
		}
	}
	
	private void viewSessions(int recordId) {
		rightPanel.showTable(TableNamesInstance.SESSION, recordId);
		rightPanel.clearTrialTable();
		rightPanel.clearFileTable();
	}
	
	private void viewFiles(int recordId, TableName tableName) {
		rightPanel.showTable(TableNamesInstance.FILE, recordId, tableName);
	}
}
