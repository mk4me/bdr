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
import motion.applet.tables.BasicTable;
import motion.applet.trees.ResultTree;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTable table;
	private TableModel tableModel;
	private JTree tree;
	
	public RightSplitPanel() {
		super();
		this.setLayout(new BorderLayout());
		table = new JTable();
		showTable(TableNamesInstance.PERFORMER.getTableName());
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
					Point point = e.getPoint();
					int row = table.rowAtPoint(point);
					//int column = table.columnAtPoint(point);
					ListSelectionModel model = table.getSelectionModel();
					model.setSelectionInterval(row, row);
					JPopupMenu popupMenu = new JPopupMenu();
					popupMenu.add(new JMenuItem("Context menu"));
					popupMenu.show(table, point.x, point.y);
				}
			}
		});
	}
	
	private void showTable(String tableName) {
		Connector connector = new Connector();
		try {
			tableModel = new BasicTable(connector, tableName);
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
		showTable(((TableName) comboBox.getSelectedItem()).getTableName());
	}
}
