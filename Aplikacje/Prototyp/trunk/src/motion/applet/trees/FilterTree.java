package motion.applet.trees;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import motion.applet.database.TableName;
import motion.database.model.Filter;
import motion.database.model.Predicate;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;

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
	
	public motion.database.ws.basicQueriesServiceWCF.FilterPredicate composeChildPredicates3(DefaultMutableTreeNode node,
			ArrayList<FilterPredicate> filterPredicates) {
		boolean emptyBranch = true;
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate branch = Filter.createBranchGroup();
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate previousBranch = null;
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			if (((FilterNode) child.getUserObject()).isSelected()) {
				emptyBranch = false;
				motion.database.ws.basicQueriesServiceWCF.FilterPredicate childBranch = composeChildPredicates3(child, filterPredicates);
				filterPredicates.addAll(((FilterNode) child.getUserObject()).getFilter().getFilterPredicatesWCF());
				if (node != root) {
					((FilterNode) node.getUserObject()).getFilter().linkChildGroupFilterPredicates(
							branch,
							childBranch,
							filterPredicates);
				}
				
				if (previousBranch != null) {
					Filter.linkSiblingBranches(previousBranch, childBranch);
				}
				previousBranch = childBranch;
			}
		}
		
		if (emptyBranch) {
			
			//return null;
			return ((FilterNode) node.getUserObject()).getFilter().getFilterPredicatesWCF().get(0);
		} else {
			filterPredicates.add(branch);
			
			return branch;
		}
	}
	/*
	public DefaultMutableTreeNode composeChildPredicates2(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode returnNode = null;	// First checked node.
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate[] parentGroup = null;
		if (node != root) {
			parentGroup = ((FilterNode) node.getUserObject()).getFilter().getFilterPredicatesWCF();
		}
		motion.database.ws.basicQueriesServiceWCF.FilterPredicate[] previousGroup = null;
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			if (((FilterNode) child.getUserObject()).isSelected()) {
				if (previousGroup != null){
					// Sibling predicate GROUP, link with OR.
					previousGroup = ((FilterNode) child.getUserObject()).getFilter().linkSiblingGroupFilterPredicates(previousGroup);
				}
				
				DefaultMutableTreeNode checkedChild = null;
				if (child.getChildCount() > 0) {
					checkedChild = composeChildPredicates2(child);
				}
				
				if (checkedChild != null) {
					// Child predicate GROUP, link with AND.
					previousPredicateGroup = ((FilterNode) checkedChild.getUserObject()).getFilter().createPredicateGroup(
							PredicateComposition.andOperator, null, ((FilterNode) child.getUserObject()).getFilter().getPredicateGroup());
				} else {
					previousPredicateGroup = ((FilterNode) child.getUserObject()).getFilter().createPredicateGroup(
							PredicateComposition.emptyOperator, null, parentPredicateGroup);
				}
				
				if (returnNode == null) {
					returnNode = child;
				}
				
			}
		}
		
		return returnNode;
	}
	*/
	
	public DefaultMutableTreeNode composeChildPredicates(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode previousNode = null;
		if (node != root) {
			previousNode = node;
		}
		
		DefaultMutableTreeNode returnNode = null;	// First checked node.
		boolean first = true;
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			if (((FilterNode) child.getUserObject()).isSelected()) {
				if (previousNode != null){
					Predicate previousPredicate = ((FilterNode) previousNode.getUserObject()).getFilter().getPredicate();
					if (first == true) {
						previousPredicate = ((FilterNode) child.getUserObject()).getFilter().getPredicate().setPreviousPredicateGroup("AND", previousPredicate);
					} else {
						previousPredicate = ((FilterNode) child.getUserObject()).getFilter().getPredicate().setPreviousPredicateGroup("OR", previousPredicate);
					}
					
				}
				
				if (child.getChildCount() > 0) {
					DefaultMutableTreeNode checkedChild = composeChildPredicates(child);
					if (checkedChild != null) {
						previousNode = checkedChild;
					} else {
						previousNode = child;
					}
				} else {
					previousNode = child;
				}
				
				if (returnNode == null) {
					returnNode = child;
				}
				
				first = false;
			}
		}
		
		return returnNode;
	}
	
	public void decomposeChildPredicates(DefaultMutableTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			((FilterNode) child.getUserObject()).getFilter().getPredicate().decomposeGroup();
			if (child.getChildCount() > 0) {
				decomposeChildPredicates(child);
			}
		}
	}
}