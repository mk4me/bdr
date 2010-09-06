package motion.applet.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import motion.applet.dialogs.DownloadDialog;
import motion.applet.panels.RightSplitPanel;

public class FileMouseAdapter extends MouseAdapter {
	private static String MENU_DOWNLOAD = "Download selected";
	private File file;	// Save last directory for downloaded files.
	
	private RightSplitPanel rightPanel;
	
	public void mouseClicked(MouseEvent e) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		if (SwingUtilities.isRightMouseButton(e)) {
			// TODO: get JTable from e
			// Get checked rows.
			final int[] recordIds = rightPanel.getSelectedRecords((JTable) e.getSource(), e);
			// Upload context menu.
			JMenuItem downloadMenuItem = new JMenuItem(MENU_DOWNLOAD);
			popupMenu.add(downloadMenuItem);
			
			downloadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setCurrentDirectory(file);
					int result = fileChooser.showSaveDialog(rightPanel);
					if (result == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						DownloadDialog downloadDialog = new DownloadDialog(recordIds, file.toString());
						downloadDialog.setVisible(true);
					}
				}
			});
			/*
			JMenuItem batchDownloadMenuItem = new JMenuItem(MENU_INVERT_SELECTION);
			popupMenu.add(batchDownloadMenuItem);

			batchDownloadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					invertSelection(tables[3]);
				}
			});
			 */
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		}
	}
}
