package motion.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import motion.database.Connector;
import motion.tables.BasicTable;

public class RightSplitPanel extends JPanel implements ActionListener {
	private JTable table;
	private TableModel tableModel;
	
	public RightSplitPanel() {
		super();
		table = new JTable();
		showTable("Performer");
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
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
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JComboBox comboBox = (JComboBox) actionEvent.getSource();
		showTable((String) comboBox.getSelectedItem());
	}
}
