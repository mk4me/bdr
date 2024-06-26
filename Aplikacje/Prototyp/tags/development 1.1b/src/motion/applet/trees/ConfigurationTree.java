package motion.applet.trees;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import motion.applet.database.AttributeGroup;
import motion.applet.database.TableName;

public class ConfigurationTree {
	public JTree tree;
	
	public ConfigurationTree(TableName tableName) {
		Vector groupVector = new Vector();
		Vector group1 = new Vector();
		//group1.add("Attribute group _ALL");
		//group1.addAll(tableName.getDefinedAttributes());
		//group1.add("Defined attribute 1");
		//group1.add("Defined attribute 2");
		//group1.add("Defined attribute 3");
		
		
		Vector group2 = new Vector();
		//group2.add("Attribute group 2");
		//group2.add("Defined attribute 4");
		//group2.add("Defined attribute 5");
		//group2.add("Defined attribute 6");
		
		
		Vector noGroup = new Vector();
		noGroup.add("No Group");
		noGroup.addAll(tableName.getStaticAttributes());
		//noGroup.add("Static attribute 1");
		//noGroup.add("Static attribute 2");
		//noGroup.add("Static attribute 3");
		
		groupVector.add(noGroup);
		//if (group1.size() > 1) {
			//groupVector.add(group1);
		//}
		//groupVector.add(group2);
		
		for (AttributeGroup a : tableName.getGroupedAttributes()) {
			Vector groupA = new Vector();
			groupA.add(a.getGroupName());
			groupA.addAll(a.getAttributes());
			groupVector.add(groupA);
		}
		
        
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		
		for(int i =0;i<groupVector.size();i++){
            String group = ((Vector)groupVector.elementAt(i)).elementAt(0).toString();
            
            if (!group.equals("No Group")) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(new CheckBoxNode(((Vector)groupVector.elementAt(i)).elementAt(0).toString(), true));
                for(int j=1;j<((Vector)groupVector.elementAt(i)).size();j++){
                        node.add(new DefaultMutableTreeNode(new CheckBoxNode(((Vector)groupVector.elementAt(i)).elementAt(j).toString(), true)));		
                }
                root.add(node);
            } else {
                for(int j=1;j<((Vector)groupVector.elementAt(i)).size();j++){
                    root.add(new DefaultMutableTreeNode(new CheckBoxNode(((Vector)groupVector.elementAt(i)).elementAt(j).toString(), true)));		
                }
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
		            }
		            else{
		                boolean isAllChiledSelected = true;
		               for (int i = 0; i < editedNode.getParent().getChildCount(); i++) {
		                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) editedNode.getParent().getChildAt(i);
		                    CheckBoxNode cbn = (CheckBoxNode) node.getUserObject();
		                    if(!cbn.isSelected()){
		                        isAllChiledSelected = false;
		                    }
		                }
		                
		                if(isAllChiledSelected){
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