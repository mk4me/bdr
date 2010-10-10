package motion.applet.trees;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

public class ConfigurationTree {
	public JTree tree;

	public ConfigurationTree(EntityKind entityKind) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		
		for (EntityAttributeGroup g : entityKind.getGroupedAttributeCopies()) {
			if (g.name.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
				for (EntityAttribute a : g) {
					root.add(new DefaultMutableTreeNode(new CheckBoxNode(a.name, true)));
				}
			} else {
				DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(new CheckBoxNode(g.name, true));
				for (EntityAttribute a : g) {
					groupNode.add(new DefaultMutableTreeNode(new CheckBoxNode(a.name, true)));
				}
				root.add(groupNode);
			}
		}
		
		tree = new JTree(root);
		tree.setToggleClickCount(1);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.putClientProperty("JTree.lineStyle", "None");
		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new CheckBoxNodeEditor(tree));
		tree.setEditable(true);
		
		tree.setModel(new DefaultTreeModel(root) {
			public void valueForPathChanged(TreePath path, Object newValue) {
				Object currNode = path.getLastPathComponent();
				super.valueForPathChanged(path, newValue);
				if ((currNode != null) && (currNode instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode editedNode = (DefaultMutableTreeNode) currNode;
					CheckBoxNode newCBN = (CheckBoxNode) newValue;
					
					if (!editedNode.isLeaf()) {
						for (int i = 0; i < editedNode.getChildCount(); i++) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getChildAt(i);
							CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
							cbn.setSelected(newCBN.isSelected());
						}
					} else {
						boolean isAllChiledSelected = true;
						for (int i = 0; i < editedNode.getParent().getChildCount(); i++) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getParent().getChildAt(i);
							CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
							if (!cbn.isSelected()) {
								isAllChiledSelected = false;
							}
						}
						
						if (isAllChiledSelected) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode)editedNode.getParent();
							// No parent group exception.
							if (!(node.getUserObject() instanceof String)) {
								CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
								cbn.setSelected(isAllChiledSelected);
							}
						}
					}
					
					if (!newCBN.isSelected()) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getParent();
						if (node.getUserObject() instanceof CheckBoxNode)
							((CheckBoxNode) node.getUserObject()).setSelected(false);
					}
				}
			}
		});
	}
}