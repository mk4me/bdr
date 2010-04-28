package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import motion.applet.Messages;
import motion.applet.MotionApplet;
import motion.applet.database.TableName;
import motion.applet.dialogs.FilterDialog;
import motion.applet.trees.CheckBoxNodeEditor;
import motion.applet.trees.CheckBoxNodeRenderer;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.Filter;
import motion.database.ws.FilterPredicate;

public class LeftSplitPanel extends JPanel {
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel treeModel;
	private JTree tree;
	private static String ADD_FILTER = Messages.getString("Add"); //$NON-NLS-1$
	private static String REMOVE_FILTER = Messages.getString("Remove"); //$NON-NLS-1$
	private static String EDIT_FILTER = Messages.getString("Edit"); //$NON-NLS-1$
	private static String START_FILTER = Messages.getString("LeftSplitPanel.Start"); //$NON-NLS-1$
	private static String TREE_TITLE = Messages.getString("LeftSplitPanel.Filters"); //$NON-NLS-1$
	private TableName tableName;
	
	public LeftSplitPanel(TableName tableName) {
		super();
		this.tableName = tableName;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionApplet.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton(ADD_FILTER);
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterDialog filterDialog = new FilterDialog(LeftSplitPanel.this.tableName);
				filterDialog.setVisible(true);
				if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
					Filter filter = new Filter(filterDialog.getName(), filterDialog.getPredicate());
					addObject(filter).setUserObject(filter);
				}
			}
		});
		
		JButton removeButton = new JButton(REMOVE_FILTER);
		toolBar.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCurrentNode();
			}
		});
		
		JButton editButton = new JButton(EDIT_FILTER);
		toolBar.add(editButton);
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tree.getSelectionPath() != null) {
					DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
					if (!selectedNode.isRoot()) {
						FilterDialog filterDialog = new FilterDialog((Filter) selectedNode.getUserObject());
						filterDialog.setVisible(true);
						if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
							Filter filter = new Filter(filterDialog.getName(), filterDialog.getPredicate());
							selectedNode.setUserObject(filter);
							treeModel.reload();
						}
					}
				}
			}
		});
		
		JButton startButton = new JButton(START_FILTER);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(tree.getModel().getRoot());
				if (tree.getSelectionPath() != null) {
					DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
					if (!selectedNode.isRoot()) {
						FilterPredicate[] query = ((Filter) selectedNode.getUserObject()).toFilterPredicate();
						try {
							List<? extends Object> result = WebServiceInstance.getDatabaseConnection().execGenericQuery(query, new String[]{LeftSplitPanel.this.tableName.toString().toLowerCase()});
							System.out.println(result);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
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
		rootNode = new DefaultMutableTreeNode(this.tableName + TREE_TITLE);
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				DefaultMutableTreeNode node;
				node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
				int index = e.getChildIndices()[0];
				node = (DefaultMutableTreeNode)(node.getChildAt(index));
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
		});
		tree = new JTree(treeModel);
		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	    tree.setCellRenderer(renderer);
	    tree.setCellEditor(new CheckBoxNodeEditor(tree));
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}
	
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
	        }
	    }
	}
	
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();
		
		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}
		
		return addObject(parentNode, child, true);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		
		if (parent == null) {
			parent = rootNode;
		}
		
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		
		return childNode;
	}
}
