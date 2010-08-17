package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import motion.applet.database.Connector;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.DownloadDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.FormDialog;
import motion.applet.dialogs.PerformerFormDialog;
import motion.applet.dialogs.SessionFormDialog;
import motion.applet.dialogs.TrialFormDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.tables.BasicTable;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.trees.ResultTree;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTree tree;
	private JTabbedPane tabbedPane;
	
	private static String MENU_CREATE_SESSION = "Create new session";
	private static String MENU_CREATE_TRIAL = "Create new trial";
	private static String MENU_UPLOAD = "Upload file";
	private static String MENU_DOWNLOAD = "Download selected";
	private static String MENU_INVERT_SELECTION = "Invert selection";
	private static String MENU_VIEW_SESSIONS = "View sessions";
	private static String MENU_VIEW_TRIALS = "View trials";
	private static String MENU_VIEW_FILES = "View files";
	
	private BottomSplitPanel bottomPanel;
	private Hashtable<TableName, Integer> tabNameHash = new Hashtable();
	private JTable tables[] = new JTable[4];
	
	public RightSplitPanel() {
		super();
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

		int i=0;
		for( TableName tn : TableNamesInstance.allTableNames )
		{
			tabNameHash.put( tn, i );
			tables[i] = new JTable();
			tabbedPane.addTab( tn.getLabel(), new JScrollPane ( tables[i] ) );
			i++;
		}
		this.setLayout(new BorderLayout());
		
		bottomPanel = new BottomSplitPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomPanel);
		splitPane.setResizeWeight(0.8);
		this.add(splitPane, BorderLayout.CENTER);
		
		bottomPanel.addApplyButtonListener(this);
		
		tables[0].addMouseListener(new PerformerMouseAdapter() ); 
		tables[1].addMouseListener(new SessionMouseAdapter() );
		tables[2].addMouseListener(new TrialMouseAdapter());
		tables[3].addMouseListener(new FileMouseAdapter());
		
		tables[3].setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		showTable(TableNamesInstance.SESSION);
		showTable( TableNamesInstance.PERFORMER );
		
	}

	private void createSelectionAtMouse( JTable table, MouseEvent e ) {
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionInterval(row, row);
	}
	
	private int getSelectedRecord( JTable table, MouseEvent e ) {
		createSelectionAtMouse(table, e);
		
		return ((BasicTable) table.getModel()).getRecordId( table.getSelectedRows()[0] );
	}
	
	private int[] getSelectedRecords( JTable table, MouseEvent e ) {
		createSelectionAtMouse(table, e);
		
		return ((BasicTable) table.getModel()).getCheckedRecordIds();
	}
	
	//TODO: get visible tab, why is this called twice?
	public BasicTable getCurrentTable() {
		int currentTab = this.tabbedPane.getSelectedIndex();
		
		return (BasicTable) tables[currentTab].getModel();
	}
	
	/*//replaced by getSelectedRecords(JTable table)
	private int[] getSelectedRecords( JTable table, MouseEvent e ) 
	{
		if ( table.getSelectedRowCount() == 0 )	// FIXME: always false?
			createSelectionAtMouse(table, e);

		int recordIds[] = new int[ table.getSelectedRowCount() ];
		int i = 0;
		for (int row : table.getSelectedRows() )
			recordIds[i++] = ((BasicTable) table.getModel()).getRecordId(row);
		
		return recordIds;
	}
	*/
	/*//TODO: move functionality to BasicTable
	private void invertSelection(JTable table)
	{
		int selIndex = 0, row = 0;
		int [] selected = table.getSelectedRows(); 
		if (selected.length == 0)
		{
			table.getSelectionModel().addSelectionInterval(0, table.getRowCount()-1);
			return;
		}
		while (row<table.getRowCount())
		{
			if ( selIndex < selected.length && selected[selIndex] == row )
			{
				table.getSelectionModel().removeSelectionInterval(row, row);
				selIndex++;
			}
			else
				table.getSelectionModel().addSelectionInterval(row, row);
			row++;
		}
	}
	*/

	private class PerformerMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			final int recordId = getSelectedRecord( tables[0], e );
			if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
				JPopupMenu popupMenu = new JPopupMenu();
				
				JMenuItem createSessionMenuItem = new JMenuItem(MENU_CREATE_SESSION);
				popupMenu.add(createSessionMenuItem);
					
				createSessionMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RightSplitPanel.this.showSessionDialog(recordId);
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
						RightSplitPanel.this.showUploadDialog(TableNamesInstance.PERFORMER, recordId);
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

				popupMenu.show( tables[0], e.getPoint().x, e.getPoint().y);
			} else if (e.getClickCount() == 2) {	// Double click.
				viewSessions(recordId);
			}
		}
		
		private void viewSessions(int recordId) {
			RightSplitPanel.this.showTable(TableNamesInstance.SESSION, recordId);
			RightSplitPanel.this.clearTrialTable();
			RightSplitPanel.this.clearFileTable();
		}
		
		private void viewFiles(int recordId, TableName tableName) {
			RightSplitPanel.this.showTable(TableNamesInstance.FILE, recordId, tableName);
		}
	}
	
	private class SessionMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			final int recordId = getSelectedRecord( tables[1], e );
			if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
				JPopupMenu popupMenu = new JPopupMenu();
				
				JMenuItem createTrialMenuItem = new JMenuItem(MENU_CREATE_TRIAL);
				popupMenu.add(createTrialMenuItem);
				
				createTrialMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RightSplitPanel.this.showTrialDialog(recordId);
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
						RightSplitPanel.this.showUploadDialog(TableNamesInstance.SESSION, recordId);
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
			RightSplitPanel.this.showTable(TableNamesInstance.TRIAL, recordId);
		}
		
		private void viewFiles(int recordId, TableName tableName) {
			RightSplitPanel.this.showTable(TableNamesInstance.FILE, recordId, tableName);
		}
	}
	
	private class TrialMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {	// Right click.
			final int recordId = getSelectedRecord( tables[2], e );
			if (SwingUtilities.isRightMouseButton(e)) {
				JPopupMenu popupMenu = new JPopupMenu();
				
				// Upload context menu.
				JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
				popupMenu.add(uploadMenuItem);
				
				uploadMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RightSplitPanel.this.showUploadDialog(TableNamesInstance.TRIAL, recordId);
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
				
				popupMenu.show( tables[2], e.getPoint().x, e.getPoint().y);
			}
		}
		
		private void viewFiles(int recordId, TableName tableName) {
			RightSplitPanel.this.showTable(TableNamesInstance.FILE, recordId, tableName);
		}
	}
	
	class FileMouseAdapter extends MouseAdapter {
		private File file;	// Save last directory for downloaded files.
		
		public void mouseClicked(MouseEvent e) {
			JPopupMenu popupMenu = new JPopupMenu();
			
			if (SwingUtilities.isRightMouseButton(e)) {
				// Get checked rows.
				final int[] recordIds = RightSplitPanel.this.getSelectedRecords(tables[3], e);
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
						int result = fileChooser.showSaveDialog(RightSplitPanel.this);
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
				
				popupMenu.show( tables[3], e.getPoint().x, e.getPoint().y);
			}
		}
	}
	
	public void showTable(TableName tableName) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new BasicTable(tableName) );
		tabbedPane.setSelectedIndex( i );
	}
	
	
	private void showTable(TableName tableName, int recordId) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new BasicTable(tableName, recordId) );
		tabbedPane.setSelectedIndex( i );
	}
	
	
	private void showTable(TableName tableName, int recordId, TableName fromTableName) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new BasicTable(tableName, recordId, fromTableName) );
		tabbedPane.setSelectedIndex( i );
	}
	
	
	private void showTree(String tableName) {
		Connector connector = new Connector();
		try {
			ResultTree resultTree = new ResultTree(connector, tableName);
			tree = resultTree.tree;
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() instanceof JComboBox) {	// The source is the toolbar lab combo box.
			JComboBox comboBox = (JComboBox) actionEvent.getSource();
			//showTable(((TableName) comboBox.getSelectedItem()));
			AppletToolBar.setLabId(comboBox.getSelectedIndex());
			refreshTablesForLab();
		} else if (actionEvent.getSource() instanceof JButton) {	// The source is the Apply selection button.
			 refreshAllTables();
		}
	}
	
	private void refreshPerformerTable() {
		TableModel tableModel = tables[0].getModel();
		if (tableModel instanceof BasicTable) {
			showTable(TableNamesInstance.PERFORMER, ((BasicTable) tableModel).recordId, ((BasicTable) tableModel).fromTableName);
		}
	}
	
	private void refreshSessionTable() {
		TableModel tableModel = tables[1].getModel();
		if (tableModel instanceof BasicTable) {
			showTable(TableNamesInstance.SESSION, ((BasicTable) tableModel).recordId, ((BasicTable) tableModel).fromTableName);
		}
	}
	
	private void refreshTrialTable() {
		TableModel tableModel = tables[2].getModel();
		if (tableModel instanceof BasicTable) {
			showTable(TableNamesInstance.TRIAL, ((BasicTable) tableModel).recordId, ((BasicTable) tableModel).fromTableName);
		}
	}
	
	private void refreshFileTable() {
		TableModel tableModel = tables[3].getModel();
		if (tableModel instanceof BasicTable) {
			showTable(TableNamesInstance.FILE, ((BasicTable) tableModel).recordId, ((BasicTable) tableModel).fromTableName);
		}
	}
	
	public void refreshAllTables() {
		int i = tabbedPane.getSelectedIndex();
		refreshPerformerTable();
		refreshSessionTable();
		refreshTrialTable();
		refreshFileTable();
		tabbedPane.setSelectedIndex(i);
	}
	
	private void refreshTablesForLab() {
		int i = tabbedPane.getSelectedIndex();
		showTable(TableNamesInstance.PERFORMER);
		showTable(TableNamesInstance.SESSION);
		clearTrialTable();
		clearFileTable();
		tabbedPane.setSelectedIndex(i);
	}
	
	public void showPerformerDialog() {
		PerformerFormDialog performerFormDialog = new PerformerFormDialog();
		performerFormDialog.setVisible(true);
		if (performerFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			RightSplitPanel.this.refreshPerformerTable();
		}
	}
	
	public void showSessionDialog(int recordId) {
		SessionFormDialog sessionFormDialog = new SessionFormDialog(recordId);
		sessionFormDialog.pack();	// TODO: is this needed?
		sessionFormDialog.setVisible(true);
		if (sessionFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			RightSplitPanel.this.refreshSessionTable();
		}
	}
	
	public void showTrialDialog(int recordId) {
		TrialFormDialog trialFormDialog = new TrialFormDialog(recordId);
		trialFormDialog.setVisible(true);
		if (trialFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			RightSplitPanel.this.refreshTrialTable();
		}
	}
	
	public void showUploadDialog(TableName tableName, int recordId) {
		UploadDialog uploadDialog = new UploadDialog(tableName, recordId);
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	public void showUploadDialog(TableName tableName) {
		UploadDialog uploadDialog = new UploadDialog(tableName);
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	private void clearSessionTable() {
		int i = tabNameHash .get(TableNamesInstance.SESSION);
		tables[i].setModel(new BasicTable());
	}
	
	private void clearTrialTable() {
		int i = tabNameHash .get(TableNamesInstance.TRIAL);
		tables[i].setModel(new BasicTable());
	}
	
	private void clearFileTable() {
		int i = tabNameHash .get(TableNamesInstance.FILE);
		tables[i].setModel(new BasicTable());
	}
}
