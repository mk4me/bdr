package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import motion.applet.MotionAppletFrame;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.tables.BasicTable;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.Trial;
import motion.widgets.TabCloseButtonWidget;

public class BasketPanel extends JPanel {
	private JTree tree;
	public JTabbedPane tablePane = new JTabbedPane();
	
	public BasketPanel() {
		super();
		this.setLayout(new BorderLayout());
		tree = new JTree(createTree());
		this.add(new JScrollPane(tree), BorderLayout.CENTER);
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton("Add");
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasketDialog basketDialog = new BasketDialog();
				basketDialog.setVisible(true);
			}
		});
		
		JButton removeButton = new JButton("Remove");
		toolBar.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton viewButton = new JButton("View");
		toolBar.add(viewButton);
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							String basketName = BasketPanel.this.getSelectedBasket();
							String entity = BasketPanel.this.getSelectedEntity();
							TableName tableName = TableNamesInstance.toTableName(entity);
							JTable table = new JTable();
							if (tableName.equals(TableNamesInstance.PERFORMER)) {
								DbElementsList<Performer> records = WebServiceInstance.getDatabaseConnection().listBasketPerformersWithAttributes(basketName);
								table.setModel(new BasicTable(records));
							} else if (tableName.equals(TableNamesInstance.SESSION)) {
								DbElementsList<Session> records = WebServiceInstance.getDatabaseConnection().listBasketSessionsWithAttributes(basketName);
								//TODO:
								//table.setModel(new BasicTable(records));
							} else if (tableName.equals(TableNamesInstance.TRIAL)) {
								DbElementsList<Trial> records = WebServiceInstance.getDatabaseConnection().listBasketTrialsWithAttributes(basketName);
								//TODO:
								//table.setModel(new BasicTable(records));
							}
							
							BasketPanel.this.addTab(table, basketName + " (" + entity + ")");
							
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
			}
		});
		
		this.add(toolBar, BorderLayout.NORTH);
	}
	
	private DefaultMutableTreeNode createTree() {
		ArrayList<String> basketList = new ArrayList<String>();
		basketList.add("basket1");
		basketList.add("basket2");
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Baskets");
		for (String s : basketList) {
			DefaultMutableTreeNode basket = new DefaultMutableTreeNode(s);
			basket.add(new DefaultMutableTreeNode("Performer"));
			basket.add(new DefaultMutableTreeNode("Session"));
			basket.add(new DefaultMutableTreeNode("Trial"));
			root.add(basket);
		}
		
		return root;
	}
	
	private String getSelectedEntity() {
		DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) this.tree.getSelectionPath().getLastPathComponent());
		if (selectedNode.getLevel() == 2) {
			
			return selectedNode.toString();
		}
		
		return "";
	}
	
	private String getSelectedBasket() {
		DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) this.tree.getSelectionPath().getLastPathComponent());
		if (selectedNode.getLevel() == 2) {
			
			return selectedNode.getParent().toString();
		}
		
		return "";
	}
	
	public void addTab(JTable table, String tabLabel) {
		tablePane.addTab("basket records", new JScrollPane(table));
		tablePane.setSelectedIndex(tablePane.getTabCount()-1);
		TabCloseButtonWidget tabCloseButtonWidget = new TabCloseButtonWidget(tabLabel, tablePane);
		tabCloseButtonWidget.addCloseButton();
	}
}
