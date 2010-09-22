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

import motion.applet.dialogs.FormDialog;
import motion.applet.dialogs.MeasurementConfigurationFormDialog;
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
import motion.database.model.EntityKind;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTabbedPane tabbedPane;
	private BottomSplitPanel bottomPanel;
	private Hashtable<EntityKind, Integer> tabNameHash = new Hashtable<EntityKind, Integer>();
	
	private static int TABLE_SIZE = 5;
	private static int TABLE_PERFORMER = 0;
	private static int TABLE_SESSION = 1;
	private static int TABLE_TRIAL = 2;
	private static int TABLE_FILE = 3;
	private static int TABLE_MEASUREMENT_CONFIGURATION = 4;
	private JTable tables[] = new JTable[TABLE_SIZE];
	
	public RightSplitPanel() {
		super();
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		
		tabNameHash.put(EntityKind.performer, TABLE_PERFORMER);
		tables[TABLE_PERFORMER] = new JTable();
		tabbedPane.addTab(EntityKind.performer.getGUIName(), new JScrollPane(tables[TABLE_PERFORMER]));
		
		tabNameHash.put(EntityKind.session, TABLE_SESSION);
		tables[TABLE_SESSION] = new JTable();
		tabbedPane.addTab(EntityKind.session.getGUIName(), new JScrollPane(tables[TABLE_SESSION]));
		
		tabNameHash.put(EntityKind.trial, TABLE_TRIAL);
		tables[TABLE_TRIAL] = new JTable();
		tabbedPane.addTab(EntityKind.trial.getGUIName(), new JScrollPane(tables[TABLE_TRIAL]));
		
		tabNameHash.put(EntityKind.file, TABLE_FILE);
		tables[TABLE_FILE] = new JTable();
		tabbedPane.addTab(EntityKind.file.getGUIName(), new JScrollPane(tables[TABLE_FILE]));
		
		tabNameHash.put(EntityKind.measurement_conf, TABLE_MEASUREMENT_CONFIGURATION);
		tables[TABLE_MEASUREMENT_CONFIGURATION] = new JTable();
		tabbedPane.addTab(EntityKind.measurement_conf.getGUIName(), new JScrollPane(tables[TABLE_MEASUREMENT_CONFIGURATION]));
		
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
		//TODO: Add measurement conf mouse listener.
		
		//tables[3].setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		//FIXME: double refresh
		//showTable(EntityKind.session);
		showTable(EntityKind.performer);	// Performers not grouped by labs.
		
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
		int[] checkedRecords = ((BasicTableModel) table.getModel()).getCheckedRecordIds();
		if (checkedRecords.length == 0) {
			
			return new int[]{getSelectedRecord(table, e)};
		} else {
		
			return checkedRecords;
		}
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
	
	public void showTable(EntityKind entityKind) {

		int i = tabNameHash .get( entityKind );
		tables[i].setModel( new AttributeTableModel(entityKind) );
		tabbedPane.setSelectedIndex( i );
	}
	
	public void showTable(EntityKind entityKind, int recordId) {

		int i = tabNameHash .get( entityKind );
		tables[i].setModel( new AttributeTableModel(entityKind, recordId) );
		tabbedPane.setSelectedIndex( i );
	}
	
	public void showTable(EntityKind entityKind, int recordId, EntityKind fromEntityKind) {

		int i = tabNameHash .get( entityKind );
		tables[i].setModel( new AttributeTableModel(entityKind, recordId, fromEntityKind) );
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
		TableModel tableModel = tables[TABLE_PERFORMER].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshSessionTable() {
		TableModel tableModel = tables[TABLE_SESSION].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshTrialTable() {
		TableModel tableModel = tables[TABLE_TRIAL].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshMeasurementConfigurationTable() {
		TableModel tableModel = tables[TABLE_MEASUREMENT_CONFIGURATION].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	private void refreshFileTable() {
		TableModel tableModel = tables[TABLE_FILE].getModel();
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
		//refreshFileTable();	// No view configuration for File.
		tabbedPane.setSelectedIndex(i);
	}
	
	private void refreshTablesForLab() {
		int i = tabbedPane.getSelectedIndex();
		//showTable(EntityKind.performer);	// Performers not grouped by labs.
		showTable(EntityKind.session);
		clearTrialTable();
		clearFileTable();
		tabbedPane.setSelectedIndex(i);
	}
	
	public void showPerformerDialog() {
		PerformerFormDialog performerFormDialog = new PerformerFormDialog();
		performerFormDialog.setVisible(true);
		if (performerFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_PERFORMER);
			RightSplitPanel.this.refreshPerformerTable();
		}
	}
	
	public void showSessionDialog() {
		SessionFormDialog sessionFormDialog = new SessionFormDialog();
		sessionFormDialog.pack();	// TODO: is this needed?
		sessionFormDialog.setVisible(true);
		if (sessionFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_SESSION);
			RightSplitPanel.this.refreshSessionTable();
		}
	}
	
	public void showTrialDialog(int recordId) {
		TrialFormDialog trialFormDialog = new TrialFormDialog(recordId);
		trialFormDialog.setVisible(true);
		if (trialFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_TRIAL);
			RightSplitPanel.this.refreshTrialTable();
		}
	}
	
	public void showMeasurementConfigurationDialog() {
		MeasurementConfigurationFormDialog measurementConfigurationDialog = new MeasurementConfigurationFormDialog();
		measurementConfigurationDialog.setVisible(true);
		if (measurementConfigurationDialog.getResult() == FormDialog.CREATE_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_MEASUREMENT_CONFIGURATION);
			RightSplitPanel.this.refreshMeasurementConfigurationTable();
		}
	}
	
	public void showUploadDialog(EntityKind entityKind, int recordId) {
		UploadDialog uploadDialog = new UploadDialog(entityKind, recordId);
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	public void showUploadDialog(EntityKind entityKind) {
		UploadDialog uploadDialog = new UploadDialog(entityKind);
		uploadDialog.setVisible(true);
		RightSplitPanel.this.refreshFileTable();
	}
	
	private void clearSessionTable() {
		int i = tabNameHash.get(EntityKind.session);
		tables[i].setModel(new BasicTableModel());
	}
	
	public void clearTrialTable() {
		int i = tabNameHash.get(EntityKind.trial);
		tables[i].setModel(new BasicTableModel());
	}
	
	public void clearFileTable() {
		int i = tabNameHash.get(EntityKind.file);
		tables[i].setModel(new BasicTableModel());
	}
	
	public JTabbedPane getTabbedPane() {
		
		return this.tabbedPane;
	}
}
