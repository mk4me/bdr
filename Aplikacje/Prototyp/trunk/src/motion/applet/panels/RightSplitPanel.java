package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import motion.applet.database.Connector;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.SessionDialog;
import motion.applet.dialogs.TrialDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.tables.BasicTable;
import motion.applet.trees.ResultTree;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTable table;
	private BasicTable tableModel;
	private JTree tree;
	private TableName tableName;
	
	private static String MENU_CREATE_SESSION = "Create new session";
	private static String MENU_CREATE_TRIAL = "Create new trial";
	private static String MENU_UPLOAD = "Upload file";
	private static String MENU_VIEW_SESSIONS = "View sessions";
	private static String MENU_VIEW_TRIALS = "View trials";
	
	private BottomSplitPanel bottomPanel;
	
	public RightSplitPanel() {
		super();
		this.tableName = TableNamesInstance.PERFORMER;
		this.setLayout(new BorderLayout());
		table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		//showTree("Performer");
		//JScrollPane scrollPane = new JScrollPane(tree);
		
		////this.add(scrollPane, BorderLayout.CENTER);
		
		bottomPanel = new BottomSplitPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, bottomPanel);
		splitPane.setResizeWeight(0.8);
		this.add(splitPane, BorderLayout.CENTER);
		
		bottomPanel.addApplyButtonListener(this);
		
		showTable(this.tableName);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Point point = e.getPoint();
					int row = table.rowAtPoint(point);
					int column = table.columnAtPoint(point);
					ListSelectionModel model = table.getSelectionModel();
					model.setSelectionInterval(row, row);
					JPopupMenu popupMenu = new JPopupMenu();
					final int recordId = ((BasicTable) table.getModel()).getRecordId(row); // ID column.
					
					if (RightSplitPanel.this.tableName.equals(TableNamesInstance.PERFORMER)) {
						// New Session context menu.
						JMenuItem createSessionMenuItem = new JMenuItem(MENU_CREATE_SESSION);
						popupMenu.add(createSessionMenuItem);
						
						createSessionMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								SessionDialog sessionDialog = new SessionDialog(recordId);
								sessionDialog.setVisible(true);
							}
						});
						// View Performer sessions context menu
						JMenuItem viewSessionsMenuItem = new JMenuItem(MENU_VIEW_SESSIONS);
						popupMenu.add(viewSessionsMenuItem);
						
						viewSessionsMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								RightSplitPanel.this.showTable(TableNamesInstance.SESSION, recordId);
							}
						});
					} else if (RightSplitPanel.this.tableName.equals(TableNamesInstance.SESSION)) {
						// New Trial context menu.
						JMenuItem createTrialMenuItem = new JMenuItem(MENU_CREATE_TRIAL);
						popupMenu.add(createTrialMenuItem);
						
						createTrialMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								TrialDialog trialDialog = new TrialDialog(recordId);
								trialDialog.setVisible(true);
							}
						});
						// View Session trials context menu
						JMenuItem viewTrialsMenuItem = new JMenuItem(MENU_VIEW_TRIALS);
						popupMenu.add(viewTrialsMenuItem);
						
						viewTrialsMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								RightSplitPanel.this.showTable(TableNamesInstance.TRIAL, recordId);
							}
						});
					}
					// Upload context menu.
					JMenuItem uploadMenuItem = new JMenuItem(MENU_UPLOAD);
					popupMenu.add(uploadMenuItem);
					
					uploadMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							UploadDialog uploadDialog = new UploadDialog(RightSplitPanel.this.tableName, recordId);
							uploadDialog.setVisible(true);
						}
					});
					
					popupMenu.show(table, point.x, point.y);
				}
			}
		});
	}
	
	private void showTable(TableName tableName) {
		this.tableName = tableName;
		
		tableModel = new BasicTable(tableName);
		table.setModel(tableModel);
	}
	
	private void showTable(TableName tableName, int recordId) {
		this.tableName = tableName;
		
		tableModel = new BasicTable(tableName, recordId);
		table.setModel(tableModel);
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
		if (actionEvent.getSource() instanceof JComboBox) {
			JComboBox comboBox = (JComboBox) actionEvent.getSource();
			showTable(((TableName) comboBox.getSelectedItem()));
		} else if (actionEvent.getSource() instanceof JButton) {
			showTable(tableModel.tableName, tableModel.recordId);
		}
	}
}
