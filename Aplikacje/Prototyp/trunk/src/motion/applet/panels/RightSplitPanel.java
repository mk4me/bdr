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
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

import motion.applet.MotionApplet;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.FileFormDialog;
import motion.applet.dialogs.FormDialog;
import motion.applet.dialogs.MeasurementConfigurationFormDialog;
import motion.applet.dialogs.PerformerFormDialog;
import motion.applet.dialogs.SessionFormDialog;
import motion.applet.dialogs.TrialFormDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.mouse.FileMouseAdapter;
import motion.applet.mouse.PerformerMouseAdapter;
import motion.applet.mouse.SessionGroupMouseAdapter;
import motion.applet.mouse.SessionMouseAdapter;
import motion.applet.mouse.TrialMouseAdapter;
import motion.applet.tables.AttributeTableModel;
import motion.applet.tables.BasicTableModel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityKind;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.Trial;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTabbedPane tabbedPane;
	private BottomSplitPanel bottomPanel;
	private Hashtable<EntityKind, Integer> tabNameHash = new Hashtable<EntityKind, Integer>();
	
	private static int TABLE_SIZE = 6;
	private static int TABLE_PERFORMER = 0;
	private static int TABLE_SESSION = 1;
	private static int TABLE_TRIAL = 2;
	private static int TABLE_FILE = 3;
	private static int TABLE_SESSION_GROUP = 4;
	private static int TABLE_MEASUREMENT_CONFIGURATION = 5;
	private JTable tables[] = new JTable[TABLE_SIZE];
	private MotionApplet applet;
	private JSplitPane splitPane;
	private int dividerSize;
	
	public RightSplitPanel(MotionApplet applet) {
		super();
		this.applet = applet;
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
		
		tabNameHash.put(EntityKind.sessionGroup, TABLE_SESSION_GROUP);
		tables[TABLE_SESSION_GROUP] = new JTable();
		tabbedPane.addTab(EntityKind.sessionGroup.getGUIName(), new JScrollPane(tables[TABLE_SESSION_GROUP]));
		
		tabNameHash.put(EntityKind.measurement_conf, TABLE_MEASUREMENT_CONFIGURATION);
		tables[TABLE_MEASUREMENT_CONFIGURATION] = new JTable();
		tabbedPane.addTab(EntityKind.measurement_conf.getGUIName(), new JScrollPane(tables[TABLE_MEASUREMENT_CONFIGURATION]));
		
		this.setLayout(new BorderLayout());
		
		bottomPanel = new BottomSplitPanel();
		bottomPanel.setVisible(false);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomPanel);
		splitPane.setResizeWeight(0.8);
		dividerSize = splitPane.getDividerSize();
		splitPane.setDividerSize(0);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane, BorderLayout.CENTER);
		
		bottomPanel.addApplyButtonListener(this);
		
		tables[TABLE_PERFORMER].addMouseListener(new PerformerMouseAdapter(this)); 
		tables[TABLE_SESSION].addMouseListener(new SessionMouseAdapter(this));
		tables[TABLE_TRIAL].addMouseListener(new TrialMouseAdapter(this));
		tables[TABLE_FILE].addMouseListener(new FileMouseAdapter(this));
		//TODO: Add measurement conf mouse listener.
		tables[TABLE_SESSION_GROUP].addMouseListener(new SessionGroupMouseAdapter(this));
		
		//tables[3].setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		//FIXME: double refresh
		//showTable(EntityKind.session);
		showTable(EntityKind.sessionGroup);
		showTable(EntityKind.performer);	// Performers not grouped by labs.
	}
	
	private void createSelectionAtMouse( JTable table, MouseEvent e ) {
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionInterval(row, row);
	}
	
	public MotionApplet getApplet()
	{
		return applet;
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
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws InterruptedException {
					try {
						WebServiceInstance.getDatabaseConnection().saveAttributeViewConfiguration();
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
	
	private void refreshSessionGroupTable() {
		TableModel tableModel = tables[TABLE_SESSION_GROUP].getModel();
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
	
	private void refreshMeasurementFileTable() {
		TableModel tableModel = tables[TABLE_MEASUREMENT_CONFIGURATION].getModel();
		if (tableModel instanceof AttributeTableModel) {
			((AttributeTableModel) tableModel).refresh();
		}
	}
	
	//FIXME: Change to column hiding
	public void refreshAllTables() {
		int i = tabbedPane.getSelectedIndex();
		refreshPerformerTable();
		refreshSessionTable();
		refreshSessionGroupTable();
		refreshTrialTable();
		refreshFileTable();
		refreshMeasurementFileTable();
		tabbedPane.setSelectedIndex(i);
	}
	
	public void refreshAttributes() {
		bottomPanel.refreshConfigurationTrees();
		refreshAllTables();
	}
	
	private void refreshTablesForLab() {
		int i = tabbedPane.getSelectedIndex();
		//showTable(EntityKind.performer);	// Performers not grouped by labs.
		showTable(EntityKind.session);
		clearTrialTable();
		clearFileTable();
		tabbedPane.setSelectedIndex(i);
	}
	
	public void showPerformerDialog(Performer performer) {
		PerformerFormDialog performerFormDialog;
		if (performer == null) {
			performerFormDialog = new PerformerFormDialog(PerformerFormDialog.TITLE, PerformerFormDialog.WELCOME_MESSAGE);
		} else {
			performerFormDialog = new PerformerFormDialog(performer);
		}
		performerFormDialog.setVisible(true);
		if (performerFormDialog.getResult() == FormDialog.CREATE_PRESSED ||
				performerFormDialog.getResult() == FormDialog.EDIT_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_PERFORMER);
			RightSplitPanel.this.refreshPerformerTable();
		}
	}
	
	public void showSessionDialog(Session session) {
		SessionFormDialog sessionFormDialog;
		if (session == null) {
			sessionFormDialog = new SessionFormDialog(SessionFormDialog.TITLE, SessionFormDialog.WELCOME_MESSAGE);
		} else {
			sessionFormDialog = new SessionFormDialog(session);
		}
		sessionFormDialog.pack();	// TODO: is this needed?
		sessionFormDialog.setVisible(true);
		if (sessionFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			showTable(EntityKind.session);	// Show newly created sessions by viewing all sessions.
		} else if (sessionFormDialog.getResult() == FormDialog.EDIT_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_SESSION);
			RightSplitPanel.this.refreshSessionTable();
		}
	}

	public void showFileDialog(DatabaseFile file) {
		FileFormDialog fileFormDialog;
		if (file == null) {
			// this should never happen since we cannot create a file in this way. 
			return;
		} else {
			fileFormDialog = new FileFormDialog(file);
		}
		fileFormDialog.pack();	// TODO: is this needed?
		fileFormDialog.setVisible(true);
		if (fileFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			showTable(EntityKind.file);	// Show newly created sessions by viewing all sessions.
		} else if (fileFormDialog.getResult() == FormDialog.EDIT_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_FILE);
			RightSplitPanel.this.refreshFileTable();
		}
	}

	public void showTrialDialog(int recordId, Trial trial) {
		TrialFormDialog trialFormDialog;
		if (trial == null) {
			trialFormDialog = new TrialFormDialog(TrialFormDialog.TITLE, TrialFormDialog.WELCOME_MESSAGE, recordId);
		} else {
			trialFormDialog = new TrialFormDialog(recordId, trial);
		}
		trialFormDialog.setVisible(true);
		if (trialFormDialog.getResult() == FormDialog.CREATE_PRESSED) {
			showTable(EntityKind.trial, recordId);	// Show newly created trial for the session.
		} else if (trialFormDialog.getResult() == FormDialog.EDIT_PRESSED) {
			tabbedPane.setSelectedIndex(TABLE_TRIAL);
			RightSplitPanel.this.refreshTrialTable();
		}
	}
	
	public void showMeasurementConfigurationDialog(MeasurementConfiguration measurementConfiguration) {
		MeasurementConfigurationFormDialog measurementConfigurationDialog;
		if (measurementConfiguration == null) {
			measurementConfigurationDialog = new MeasurementConfigurationFormDialog(MeasurementConfigurationFormDialog.TITLE, MeasurementConfigurationFormDialog.WELCOME_MESSAGE);
		} else {
			measurementConfigurationDialog = new MeasurementConfigurationFormDialog(measurementConfiguration);
		}
		measurementConfigurationDialog.setVisible(true);
		if (measurementConfigurationDialog.getResult() == FormDialog.CREATE_PRESSED ||
				measurementConfigurationDialog.getResult() == FormDialog.EDIT_PRESSED) {
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
	
	public void toggleViewConfiguration() {
		boolean isVisible = !bottomPanel.isVisible();
		bottomPanel.setVisible(isVisible);
		splitPane.resetToPreferredSizes();
		if (isVisible) {
			splitPane.setDividerSize(dividerSize);
		} else {
			splitPane.setDividerSize(0);
		}
	}
}
