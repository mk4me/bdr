package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import motion.applet.Messages;
import motion.applet.MotionAppletFrame;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.tables.BasketTableModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.Trial;
import motion.database.model.UserBasket;
import motion.widgets.TabCloseButtonWidget;

public class BasketPanel extends JPanel {
	private JTree tree;
	private DefaultTreeModel treeModel;
	public JTabbedPane tablePane = new JTabbedPane();
	
	private static String ADD_BASKET = Messages.getString("Add"); //$NON-NLS-1$
	private static String REMOVE_BASKET = Messages.getString("Remove"); //$NON-NLS-1$
	private static String VIEW_BASKET = Messages.getString("View"); //$NON-NLS-1$
	private static String TREE_TITLE = Messages.getString("Baskets"); //$NON-NLS-1$
	
	public BasketPanel() {
		super();
		this.setLayout(new BorderLayout());
		tree = new JTree();
		treeModel = (DefaultTreeModel) tree.getModel();
		this.add(new JScrollPane(tree), BorderLayout.CENTER);
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton(ADD_BASKET);
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasketDialog basketDialog = new BasketDialog();
				basketDialog.setVisible(true);
			}
		});
		
		JButton removeButton = new JButton(REMOVE_BASKET);
		toolBar.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeBasket();
			}
		});
		
		JButton viewButton = new JButton(VIEW_BASKET);
		toolBar.add(viewButton);
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewBasket();
			}
		});
		
		this.add(toolBar, BorderLayout.NORTH);
		
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//int selRow = tree.getRowForLocation(e.getX(), e.getY());
				//TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (e.getClickCount() == 2) {	// Double click.
					viewBasket();
				}
			}
		});
		
		getBasketTreeContents();
	}
	
	public void getBasketTreeContents() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					ArrayList<UserBasket> basketList = new ArrayList<UserBasket>();
					DbElementsList<UserBasket> baskets = WebServiceInstance.getDatabaseConnection().listUserBaskets();
					for (UserBasket b : baskets) {
						basketList.add(b);
						//((EntityAttribute) b.get(UserBasketStaticAttributes.basketName)).value);
					}
					DefaultMutableTreeNode root = new DefaultMutableTreeNode(TREE_TITLE);
					for (UserBasket b : basketList) {
						DefaultMutableTreeNode basket = new DefaultMutableTreeNode(b);
						basket.add(new DefaultMutableTreeNode(TableNamesInstance.PERFORMER.getLabel()));
						basket.add(new DefaultMutableTreeNode(TableNamesInstance.SESSION.getLabel()));
						basket.add(new DefaultMutableTreeNode(TableNamesInstance.TRIAL.getLabel()));
						root.add(basket);
					}
					BasketPanel.this.treeModel.setRoot(root);
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
	
	private void viewBasket() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					String basketName = BasketPanel.this.getSelectedBasket();
					String entity = BasketPanel.this.getSelectedEntity();
					if (!entity.equals("")) {
						TableName tableName = TableNamesInstance.toTableName(entity);
						JTable table = new JTable();
						if (tableName.equals(TableNamesInstance.PERFORMER)) {
							DbElementsList<Performer> records = WebServiceInstance.getDatabaseConnection().listBasketPerformersWithAttributes(basketName);
							table.setModel(new BasketTableModel(tableName, records, basketName));
						} else if (tableName.equals(TableNamesInstance.SESSION)) {
							DbElementsList<Session> records = WebServiceInstance.getDatabaseConnection().listBasketSessionsWithAttributes(basketName);
							table.setModel(new BasketTableModel(tableName, records, basketName));
						} else if (tableName.equals(TableNamesInstance.TRIAL)) {
							DbElementsList<Trial> records = WebServiceInstance.getDatabaseConnection().listBasketTrialsWithAttributes(basketName);
							table.setModel(new BasketTableModel(tableName, records, basketName));
						}
						
						BasketPanel.this.addTab(table, basketName + " (" + entity + ")");
					}
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
	
	private void removeBasket() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					String basketName = BasketPanel.this.getSelectedBasket();
					WebServiceInstance.getDatabaseConnection().removeBasket(basketName);
					MotionAppletFrame.refreshBaskets();
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
	
	private String getSelectedEntity() {
		TreePath selectionPath = this.tree.getSelectionPath();
		if (selectionPath != null) {	// Node clicked and not tree handles.
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
			if (selectedNode.getLevel() == 2) {
				
				return selectedNode.toString();
			}
		}
		
		return "";
	}
	
	private String getSelectedBasket() {
		TreePath selectionPath = this.tree.getSelectionPath();
		if (selectionPath != null) {	// Node clicked and not tree handles.
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
			if (selectedNode.getLevel() == 2) {	// Performer/Session/Trial node selected.
				
				return selectedNode.getParent().toString();
			} else if (selectedNode.getLevel() == 1) {	// Basket name node selected.
				
				return selectedNode.toString();
			}
		}
		
		return "";
	}
	
	private void addTab(JTable table, String tabLabel) {
		tablePane.addTab("basket records", new JScrollPane(table));
		tablePane.setSelectedIndex(tablePane.getTabCount()-1);
		TabCloseButtonWidget tabCloseButtonWidget = new TabCloseButtonWidget(tabLabel, tablePane);
		tabCloseButtonWidget.addCloseButton();
	}
	
	public JTabbedPane getTabbedPane() {
		
		return this.tablePane;
	}
}
