package motion.applet.trees;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

public class ConfigurationTree {
	public JTree tree;
	
	public ConfigurationTree(EntityKind entityKind) {
		//TODO: Clean up code.
		try {
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
			
			
			//nf:Vector noGroup = new Vector();
			//nf:noGroup.add("No Group");
			//nf:noGroup.addAll(entityKind.getStaticAttributes());
			//noGroup.add("Static attribute 1");
			//noGroup.add("Static attribute 2");
			//noGroup.add("Static attribute 3");
			
			//nf:groupVector.add(noGroup);
			//if (group1.size() > 1) {
				//groupVector.add(group1);
			//}
			//groupVector.add(group2);
			
			Iterator it = entityKind.getAllAttributeGroups().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				//System.out.println(pairs.getKey() + " = " + pairs.getValue());
				Vector groupA = new Vector();
				groupA.add(((EntityAttributeGroup) pairs.getValue()).name);
				groupA.addAll(((EntityAttributeGroup) pairs.getValue()));
				groupVector.add(groupA);
			}
			
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
			
			for(int i =0;i<groupVector.size();i++){
	            String group = ((Vector)groupVector.elementAt(i)).elementAt(0).toString();
	            
	            if (!group.equals("_static")) { //nf:if (!group.equals("No Group")) {
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
		} catch (Exception e) {
			
		}
	}
}