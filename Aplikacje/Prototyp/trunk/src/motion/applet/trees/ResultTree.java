package motion.applet.trees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import motion.applet.database.Connector;

public class ResultTree {
	private java.sql.Connection connection;
	private String tableName;
	private String databaseName;
	public JTree tree;
	
	public ResultTree(Connector connector, String tableName) throws SQLException {
		super();
		this.tableName = tableName;
		this.databaseName = connector.getDatabaseName();
		this.connection = connector.openConnection();
		getTreeContents();
		connector.closeConnection();
	}
	
	private void getTreeContents() throws SQLException {
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
		ArrayList list = new ArrayList();
		list.add("Results");
		
		while (resultSet.next()) {
			Object value[] = { resultSet.getString(1), resultSet.getString(2), resultSet.getString(3) };
			list.add(value);
		}
		
		Object hierarchy[] = list.toArray();
		
		DefaultMutableTreeNode root = processHierarchy(hierarchy);
		tree = new JTree(root);
		
		// 1. Close the ResultSet
		// 2. Close the Statement
		// 3. Close the Connection
		resultSet.close();
		statement.close();
	}
	
	private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		DefaultMutableTreeNode child;
		for (int i = 1; i < hierarchy.length; i++) {
			Object nodeSpecifier = hierarchy[i];
			if (nodeSpecifier instanceof Object[]) {
				child = processHierarchy((Object[]) nodeSpecifier);
			} else {
				child = new DefaultMutableTreeNode(nodeSpecifier);
			}
			node.add(child);
		}
		return (node);
	}
}
