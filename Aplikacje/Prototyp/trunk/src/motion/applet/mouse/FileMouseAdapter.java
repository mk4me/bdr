package motion.applet.mouse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import motion.applet.dialogs.DownloadDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.ProgressBarDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.panels.RightSplitPanel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityKind;

public class FileMouseAdapter extends MouseAdapter {
	private static String MENU_UPLOAD = "Upload new file";
	private static String MENU_DOWNLOAD = "Download selected";
	private static String MENU_EDIT = "Edit";
	private File file;	// Save last directory for downloaded files.
	
	private RightSplitPanel rightPanel;
	
	public FileMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		if (SwingUtilities.isRightMouseButton(e)) {
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
			
			JMenuItem editMenuItem = new JMenuItem(MENU_EDIT);
			popupMenu.addSeparator();
			popupMenu.add(editMenuItem);
			
			editMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					edit(recordIds[0]);
				}
			});

			JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
			popupMenu.add(uploadMenuItem);
			
			uploadMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					upload(recordIds[0]);
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
	
	private void edit(final int recordId) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					DatabaseFile file = (DatabaseFile) WebServiceInstance.getDatabaseConnection().getById(recordId, EntityKind.file);
					rightPanel.showFileDialog(file);
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

	private void upload(final int recordId) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					JFileChooser fileChooser = UploadDialog.createFileChooser();
					fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
					fileChooser.setMultiSelectionEnabled( false );

					int result = fileChooser.showOpenDialog( rightPanel );
					if ( result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						final ProgressBarDialog progressBarDialog = new ProgressBarDialog("Upload progress", "Replacing file with: " + file.getName());
						SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws InterruptedException {
								progressBarDialog.setVisible(true);
								
								return null;
							}
							
							@Override
							protected void done() {
							}
						};
						worker.execute();
						
						DatabaseConnection.getInstance().replaceFile(recordId, file.getAbsolutePath(), progressBarDialog.uploadTransferListener );
						
						progressBarDialog.setVisible(false);
						progressBarDialog.dispose();
						
						rightPanel.refreshFileTable();
					}
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
