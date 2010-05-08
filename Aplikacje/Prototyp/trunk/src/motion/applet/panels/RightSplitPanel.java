package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.table.TableModel;

import motion.applet.database.Connector;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.SessionDialog;
import motion.applet.dialogs.TrialDialog;
import motion.applet.tables.BasicTable;
import motion.applet.trees.ResultTree;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTable table;
	private TableModel tableModel;
	private JTree tree;
	private TableName tableName;
	
	private static String MENU_CREATE_SESSION = "Create new session";
	private static String MENU_CREATE_TRIAL = "Create new trial";
	
	public RightSplitPanel() {
		super();
		this.tableName = TableNamesInstance.PERFORMER;
		this.setLayout(new BorderLayout());
		table = new JTable();
		showTable(this.tableName);
		JScrollPane scrollPane = new JScrollPane(table);
		//showTree("Performer");
		//JScrollPane scrollPane = new JScrollPane(tree);
		
		////this.add(scrollPane, BorderLayout.CENTER);
		
		BottomSplitPanel bottomPanel = new BottomSplitPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, bottomPanel);
		splitPane.setResizeWeight(0.8);
		this.add(splitPane, BorderLayout.CENTER);
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					if (RightSplitPanel.this.tableName.equals(TableNamesInstance.PERFORMER)) {
						Point point = e.getPoint();
						int row = table.rowAtPoint(point);
						int column = table.columnAtPoint(point);
						ListSelectionModel model = table.getSelectionModel();
						model.setSelectionInterval(row, row);
						JPopupMenu popupMenu = new JPopupMenu();
						JMenuItem createSessionMenuItem = new JMenuItem(MENU_CREATE_SESSION);
						popupMenu.add(createSessionMenuItem);
						final Object value = table.getModel().getValueAt(row, 0); // ID column.
						
						createSessionMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								SessionDialog sessionDialog = new SessionDialog(Integer.parseInt(value.toString()));
								sessionDialog.setVisible(true);
							}
						});
						popupMenu.show(table, point.x, point.y);
					} else if (RightSplitPanel.this.tableName.equals(TableNamesInstance.SESSION)) {
						Point point = e.getPoint();
						int row = table.rowAtPoint(point);
						int column = table.columnAtPoint(point);
						ListSelectionModel model = table.getSelectionModel();
						model.setSelectionInterval(row, row);
						JPopupMenu popupMenu = new JPopupMenu();
						JMenuItem createTrialMenuItem = new JMenuItem(MENU_CREATE_TRIAL);
						popupMenu.add(createTrialMenuItem);
						final Object value = table.getModel().getValueAt(row, 0); // ID column.
						
						createTrialMenuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								TrialDialog trialDialog = new TrialDialog(Integer.parseInt(value.toString()));
								trialDialog.setVisible(true);
							}
						});
						popupMenu.show(table, point.x, point.y);
					}
				}
			}
		});
	}
	
	private void showTable(TableName tableName) {
		this.tableName = tableName;
		try {
			tableModel = new BasicTable(tableName);
			table.setModel(tableModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showTree(String tableName) {
		Connector connector = new Connector();
		try {
			ResultTree resultTree = new ResultTree(connector, tableName);
			tree = resultTree.tree;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JComboBox comboBox = (JComboBox) actionEvent.getSource();
		showTable(((TableName) comboBox.getSelectedItem()));
	}
}
