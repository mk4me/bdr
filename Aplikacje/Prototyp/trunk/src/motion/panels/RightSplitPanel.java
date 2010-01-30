package motion.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableModel;

import motion.database.Connector;
import motion.tables.BasicTable;
import motion.trees.ResultTree;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTable table;
	private TableModel tableModel;
	private JTree tree;
	
	public RightSplitPanel() {
		super();
		this.setLayout(new BorderLayout());
		table = new JTable();
		//showTable("Performer");
		//JScrollPane scrollPane = new JScrollPane(table);
		showTree("Performer");
		JScrollPane scrollPane = new JScrollPane(tree);
		this.add(scrollPane, BorderLayout.CENTER);
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
		showTable((String) comboBox.getSelectedItem());
	}
}
