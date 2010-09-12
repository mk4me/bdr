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
import motion.applet.panels.RightSplitPanel;
import motion.database.model.EntityKind;

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
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		}
	}
	
	private void viewFiles(int recordId, EntityKind entityKind) {
		rightPanel.showTable(EntityKind.file, recordId, entityKind);
		MotionAppletFrame.setBrowsePanelVisible();
	}
}
