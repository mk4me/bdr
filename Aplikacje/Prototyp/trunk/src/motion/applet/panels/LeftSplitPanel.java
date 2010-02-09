package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import motion.applet.MotionApplet;
import motion.applet.database.Filter;
import motion.applet.dialogs.FilterDialog;
import motion.applet.trees.CheckBoxNodeEditor;
import motion.applet.trees.CheckBoxNodeRenderer;

public class LeftSplitPanel extends JPanel {
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel treeModel;
	private JTree tree;
	private static String ADD_FILTER = "Add";
	private static String REMOVE_FILTER = "Remove";
	private static String EDIT_FILTER = "Edit";
	private static String START_FILTER = "Start";
	
	public LeftSplitPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionApplet.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton(ADD_FILTER);
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterDialog filterDialog = new FilterDialog();
				filterDialog.setVisible(true);
				if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
					Filter filter = new Filter(filterDialog.getName());
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
					FilterDialog filterDialog = new FilterDialog(selectedNode.toString());
					filterDialog.setVisible(true);
					if (filterDialog.getResult() == FilterDialog.ADD_PRESSED) {
						Filter filter = new Filter(filterDialog.getName());
						selectedNode.setUserObject(filter);
						treeModel.reload();
					}
				}
			}
		});
		
		JButton startButton = new JButton(START_FILTER);
		toolBar.add(startButton);
		
		add(toolBar);
		
		// Create the tree
		rootNode = new DefaultMutableTreeNode("Filter tree");
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
