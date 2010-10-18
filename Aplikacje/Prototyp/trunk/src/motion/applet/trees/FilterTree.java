package motion.applet.trees;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import motion.database.model.EntityKind;
import motion.database.model.Filter;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;

public class FilterTree {
	public JTree tree;
	public DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	
	public FilterTree(EntityKind entityKind) {
		root = new DefaultMutableTreeNode(entityKind.getGUIName() + " (filters)");
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
					} else {
						boolean isAllChildSelected = true;
						for (int i = 0; i < editedNode.getParent().getChildCount(); i++) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getParent().getChildAt(i);
							CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
							if(!cbn.isSelected()) {
								isAllChildSelected = false;
							}
						}
						if (isAllChildSelected) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode)editedNode.getParent();
							// No parent group exception.
							if (!(node.getUserObject() instanceof String)) {
								CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
								cbn.setSelected(isAllChildSelected);
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
	
	public motion.database.ws.basicQueriesServiceWCF.FilterPredicate composeChildPredicates3(DefaultMutableTreeNode node,
			ArrayList<FilterPredicate> filterPredicates) {
		boolean emptyBranch = true;
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate branch = null;
		if (node != root) {
			branch = Filter.createBranchGroup(((FilterNode) node.getUserObject()).getFilter().getFilterPredicatesWCF().get(0));
		}
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate previousBranch = null;
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			if (((FilterNode) child.getUserObject()).isSelected()) {
				emptyBranch = false;
				motion.database.ws.basicQueriesServiceWCF.FilterPredicate childBranch = composeChildPredicates3(child, filterPredicates);
				filterPredicates.addAll(((FilterNode) child.getUserObject()).getFilter().getFilterPredicatesWCF());
				if (previousBranch == null) {
					if (node != root) {
						((FilterNode) node.getUserObject()).getFilter().linkChildGroupFilterPredicates(
								branch,
								childBranch,
								filterPredicates);
					}
				} else {
					Filter.linkSiblingBranches(previousBranch, childBranch);
				}
				previousBranch = childBranch;
			}
		}
		
		if (emptyBranch) {	// TODO: simplify if/else
			if (node == root) {
				
				return null;
			} else {
				
				return ((FilterNode) node.getUserObject()).getFilter().getFilterPredicatesWCF().get(0);
			}
		} else {
			if (branch != null) {
				filterPredicates.add(branch);
			}
			
			return branch;
		}
	}
	
	public void decomposeChildPredicates3(DefaultMutableTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			((FilterNode) child.getUserObject()).getFilter().removeFilterPredicatesWCF();
			if (child.getChildCount() > 0) {
				decomposeChildPredicates3(child);
			}
		}
	}
}