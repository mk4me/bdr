package motion.applet.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.panels.RightSplitPanel;

public class SessionMouseAdapter extends MouseAdapter {
	private static String MENU_CREATE_TRIAL = "Create new trial";
	private static String MENU_VIEW_TRIALS = "View trials";
	private static String MENU_VIEW_FILES = "View files";
	private static String MENU_UPLOAD = "Upload file";
	
	private RightSplitPanel rightPanel;
	
	public void mouseClicked(MouseEvent e) {
		// TODO: get JTable from e
		final int recordId = getSelectedRecord( tables[1], e );
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			JMenuItem createTrialMenuItem = new JMenuItem(MENU_CREATE_TRIAL);
			popupMenu.add(createTrialMenuItem);
			
			createTrialMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showTrialDialog(recordId);
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
			
			popupMenu.add(new JSeparator());
			// Upload context menu.
			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rightPanel.showUploadDialog(TableNamesInstance.SESSION, recordId);
				}
			});
			
			// View files context menu
			JMenuItem viewFilesMenuItem = new JMenuItem(MENU_VIEW_FILES);
			popupMenu.add(viewFilesMenuItem);
			
			viewFilesMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewFiles(recordId, TableNamesInstance.SESSION);
				}
			});
			
			popupMenu.show( tables[1], e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewTrials(recordId);
		}
	}
	
	private void viewTrials(int recordId) {
		rightPanel.showTable(TableNamesInstance.TRIAL, recordId);
	}
	
	private void viewFiles(int recordId, TableName tableName) {
		rightPanel.showTable(TableNamesInstance.FILE, recordId, tableName);
	}
}
