package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import motion.Messages;
import motion.applet.MotionApplet;
import motion.applet.MotionAppletFrame;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.FilterDialog;
import motion.applet.tables.QueryTableModel;
import motion.applet.trees.FilterNode;
import motion.applet.trees.FilterTree;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityKind;
import motion.database.model.Filter;
import motion.database.model.GenericDescription;
import motion.database.model.GenericResult;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;

public class LeftSplitPanel extends JPanel {
	//private DefaultMutableTreeNode rootNode;
	//private DefaultTreeModel treeModel;
	private FilterTree filterTree;
	private static String ADD_FILTER = Messages.getString("Add"); //$NON-NLS-1$
	private static String REMOVE_FILTER = Messages.getString("Remove"); //$NON-NLS-1$
	private static String EDIT_FILTER = Messages.getString("Edit"); //$NON-NLS-1$
	private static String START_FILTER = Messages.getString("LeftSplitPanel.Start"); //$NON-NLS-1$
	private static String TREE_TITLE = Messages.getString("LeftSplitPanel.Filters"); //$NON-NLS-1$
	private EntityKind entityKind;
	private JLabel messageLabel;
	
	public LeftSplitPanel(EntityKind entityKind) {
		super();
		this.entityKind = entityKind;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton(ADD_FILTER);
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterDialog filterDialog = new FilterDialog(LeftSplitPanel.this.entityKind);
				filterDialog.setVisible(true);
				if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
					FilterNode filterNode = new FilterNode(new Filter(filterDialog.getName(), filterDialog.getPredicate()));
					filterTree.addFilterNode(filterNode).setUserObject(filterNode);
				}
			}
		});
		
		JButton removeButton = new JButton(REMOVE_FILTER);
		toolBar.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterTree.removeCurrentNode();
			}
		});
		
		JButton editButton = new JButton(EDIT_FILTER);
		toolBar.add(editButton);
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (filterTree.tree.getSelectionPath() != null) {
					DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) filterTree.tree.getSelectionPath().getLastPathComponent());
					if (!selectedNode.isRoot()) {
						FilterDialog filterDialog = new FilterDialog(((FilterNode) selectedNode.getUserObject()).getFilter());
						filterDialog.setVisible(true);
						if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
							FilterNode filterNode = new FilterNode(new Filter(filterDialog.getName(), filterDialog.getPredicate()));
							selectedNode.setUserObject(filterNode);
							filterTree.treeModel.reload();
						}
					}
				}
			}
		});
		
		JButton startButton = new JButton(START_FILTER);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							DefaultMutableTreeNode root = (DefaultMutableTreeNode) filterTree.tree.getModel().getRoot();
							ArrayList<FilterPredicate> filterPredicates = new ArrayList<FilterPredicate>();
							filterTree.decomposeChildPredicates3(root);
							filterTree.composeChildPredicates3(root, filterPredicates);
							
							for (FilterPredicate f : filterPredicates) {
								System.out.println(
										"pId=" + f.getPredicateID() +
										", cE=" + f.getContextEntity() +
										", fN=" + f.getFeatureName() +
										", parP=" + f.getParentPredicate() +
										", prevP=" + f.getPreviousPredicate() +
										", nO=" + f.getNextOperator() +
										", o=" + f.getOperator() +
										", v=" + f.getValue()
										);
							}
							
							if (filterPredicates.isEmpty() == false) {
								List<GenericDescription> result = WebServiceInstance.getDatabaseConnection().execGenericQuery(
										filterPredicates,
										new String[] {LeftSplitPanel.this.entityKind.getName()});
								
								JTable resultTable = new JTable();
								resultTable.setModel(new QueryTableModel(result));
								MotionApplet.addResultTab(resultTable);
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
				
				//TreeNode root = (TreeNode) tree.getModel().getRoot();
			}
			/*
			private ArrayList<SimplePredicate> getChildPredicates(TreeNode node) {
				//ArrayList<SimplePredicate> predicates
				Enumeration children = node.children();
				while(children.hasMoreElements()) {
					TreeNode currentNode = (TreeNode) children.nextElement();
					
				}
			}*/
		});
		toolBar.add(startButton);
		
		add(toolBar);
		
		// Create the tree
		//rootNode = new DefaultMutableTreeNode(this.tableName.getLabel() + TREE_TITLE);
		//treeModel = new DefaultTreeModel(rootNode);
		
		filterTree = new FilterTree(this.entityKind);
		JScrollPane scrollPane = new JScrollPane(filterTree.tree);
		add(scrollPane, BorderLayout.CENTER);
		/*
		filterTree.treeModel.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				DefaultMutableTreeNode node;
				node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
				int index = e.getChildIndices()[0];
				node = (DefaultMutableTreeNode)(node.getChildAt(index));
				((FilterNode) node.getUserObject()).getFilter().getPredicate().printPredicate();
			}
			
			@Override
			public void treeNodesInserted(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void treeNodesRemoved(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void treeStructureChanged(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
}
