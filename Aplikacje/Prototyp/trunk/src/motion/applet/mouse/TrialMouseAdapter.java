package motion.applet.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import motion.applet.MotionAppletFrame;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.panels.RightSplitPanel;

public class TrialMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_FILES = "View files";
	private static String MENU_UPLOAD = "Upload file";
	
	private RightSplitPanel rightPanel;
	
	public TrialMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {	// Right click.
		// TODO: get JTable from e
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {
			JPopupMenu popupMenu = new JPopupMenu();
			
			// Upload context menu.
			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showUploadDialog(TableNamesInstance.TRIAL, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, TableNamesInstance.TRIAL);
				}
			});
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		}
	}
	
	private void viewFiles(int recordId, TableName tableName) {
		rightPanel.showTable(TableNamesInstance.FILE, recordId, tableName);
		MotionAppletFrame.setBrowsePanelVisible();
	}
}