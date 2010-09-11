package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.FormDialog;
import motion.applet.dialogs.PerformerFormDialog;
import motion.applet.dialogs.SessionFormDialog;
import motion.applet.dialogs.TrialFormDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.mouse.FileMouseAdapter;
import motion.applet.mouse.PerformerMouseAdapter;
import motion.applet.mouse.SessionMouseAdapter;
import motion.applet.mouse.TrialMouseAdapter;
import motion.applet.tables.AttributeTableModel;
import motion.applet.tables.BasicTableModel;
import motion.applet.toolbars.AppletToolBar;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTabbedPane tabbedPane;
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
		
		tables[0].addMouseListener(new PerformerMouseAdapter(this)); 
		tables[1].addMouseListener(new SessionMouseAdapter(this));
		tables[2].addMouseListener(new TrialMouseAdapter(this));
		tables[3].addMouseListener(new FileMouseAdapter(this));
		
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
	
	public int getSelectedRecord( JTable table, MouseEvent e ) {
		createSelectionAtMouse(table, e);
		
		return ((BasicTableModel) table.getModel()).getRecordId( table.getSelectedRows()[0] );
	}
	
	public int[] getSelectedRecords( JTable table, MouseEvent e ) {
		createSelectionAtMouse(table, e);
		
		return ((BasicTableModel) table.getModel()).getCheckedRecordIds();
	}
	
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
	
	public void showTable(TableName tableName) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new AttributeTableModel(tableName) );
		tabbedPane.setSelectedIndex( i );
	}
	
	public void showTable(TableName tableName, int recordId) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new AttributeTableModel(tableName, recordId) );
		tabbedPane.setSelectedIndex( i );
	}
	
	public void showTable(TableName tableName, int recordId, TableName fromTableName) {

		int i = tabNameHash .get( tableName );
		tables[i].setModel( new AttributeTableModel(tableName, recordId, fromTableName) );
		tabbedPane.setSelectedIndex( i );
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
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshSessionTable() {
		TableModel tableModel = tables[1].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshTrialTable() {
		TableModel tableModel = tables[2].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshFileTable() {
		TableModel tableModel = tables[3].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	//FIXME: Change to column hiding
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
		UploadDialog uploadDialog = new UploadDialog(tableName.toEntityKind(), recordId);
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	public void showUploadDialog(TableName tableName) {
		UploadDialog uploadDialog = new UploadDialog(tableName.toEntityKind());
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	private void clearSessionTable() {
		int i = tabNameHash .get(TableNamesInstance.SESSION);
		tables[i].setModel(new BasicTableModel());
	}
	
	public void clearTrialTable() {
		int i = tabNameHash .get(TableNamesInstance.TRIAL);
		tables[i].setModel(new BasicTableModel());
	}
	
	public void clearFileTable() {
		int i = tabNameHash .get(TableNamesInstance.FILE);
		tables[i].setModel(new BasicTableModel());
	}
	
	public JTabbedPane getTabbedPane() {
		
		return this.tabbedPane;
	}
}
