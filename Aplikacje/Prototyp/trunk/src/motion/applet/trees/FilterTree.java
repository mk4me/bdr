package motion.applet.trees;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import motion.applet.database.TableName;

public class FilterTree {
	public JTree tree;
	public DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	
	public FilterTree(TableName tableName) {
		root = new DefaultMutableTreeNode(tableName.getLabel() + " (filters)");
		tree = new JTree(root);
		tree.setToggleClickCount(1);
		//tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.putClientProperty("JTree.lineStyle", "None");
		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new CheckBoxNodeEditor(tree));
		tree.setEditable(true);
		
		treeModel = new DefaultTreeModel(root) {
			public void valueForPathChanged(TreePath path, Object newValue) {
				Object currNode = path.getLastPathComponent();
				super.valueForPathChanged(path, newValue);
				if ((currNode != null) && (currNode instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode editedNode = (DefaultMutableTreeNode) currNode;
					CheckBoxNode newCBN = (CheckBoxNode) newValue;
					if (!editedNode.isLeaf() && newCBN.isSelected() == false) {	// TODO: select parents up to root
						unCheckAllChildren(editedNode);
					}
					else {
						boolean isAllChiledSelected = true;
						for (int i = 0; i < editedNode.getParent().getChildCount(); i++) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getParent().getChildAt(i);
							CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
							if(!cbn.isSelected()) {
								isAllChiledSelected = false;
							}
						}
						if(isAllChiledSelected) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode)editedNode.getParent();
							// No parent group exception.
							if (!(node.getUserObject() instanceof String)) {
								CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
								cbn.setSelected(isAllChiledSelected);
							}
						}
					}
					if (newCBN.isSelected()) {
						DefaultMutableTreeNode node = editedNode;
						while (node.getParent() != root) {
							node = (DefaultMutableTreeNode) node.getParent();
							if (node.getUserObject() instanceof CheckBoxNode)
								((CheckBoxNode) node.getUserObject()).setSelected(true);
						}
					}
				}
			}
			private void unCheckAllChildren(DefaultMutableTreeNode node) {
				for (int i = 0; i < node.getChildCount(); i++) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
					CheckBoxNode checkBoxNode = (CheckBoxNode) child.getUserObject();
					checkBoxNode.setSelected(false);
					if (child.getChildCount() > 0) {
						unCheckAllChildren(child);
					}
				}
			}
		};
		
		tree.setModel(treeModel);
	}
	
	public void clear() {
		root.removeAllChildren();
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
	
	public DefaultMutableTreeNode addFilterNode(FilterNode filterNode) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();
		
		if (parentPath == null) {
			parentNode = root;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}
		
		return addFilterNode(parentNode, filterNode, true);
	}
	
	private DefaultMutableTreeNode addFilterNode(DefaultMutableTreeNode parent, FilterNode filterNode) {
		return addFilterNode(parent, filterNode, false);
	}
	
	private DefaultMutableTreeNode addFilterNode(DefaultMutableTreeNode parent, FilterNode filterNode, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(filterNode);
		
		if (parent == null) {
			parent = root;
		}
		
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		
		return childNode;
	}
}