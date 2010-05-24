package motion.applet.trees;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
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
	
	private class CheckBoxNodeRenderer implements TreeCellRenderer {
	    private JCheckBox leafRenderer = new JCheckBox();
	    
	    private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
	    
	    Color selectionBorderColor, selectionForeground, selectionBackground,
	            textForeground, textBackground;
	    
	    protected JCheckBox getLeafRenderer() {
	        return leafRenderer;
	    }
	    
	    public CheckBoxNodeRenderer() {
	        Font fontValue;
	        fontValue = UIManager.getFont("Tree.font");
	        if (fontValue != null) {
	            leafRenderer.setFont(fontValue);
	        }
	        Boolean booleanValue = (Boolean) UIManager
	                .get("Tree.drawsFocusBorderAroundIcon");
	        leafRenderer.setFocusPainted((booleanValue != null)
	        && (booleanValue.booleanValue()));
	        
	        selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
	        selectionForeground = UIManager.getColor("Tree.selectionForeground");
	        selectionBackground = UIManager.getColor("Tree.selectionBackground");
	        textForeground = UIManager.getColor("Tree.textForeground");
	        textBackground = UIManager.getColor("Tree.textBackground");
	    }
	    
	    public Component getTreeCellRendererComponent(JTree tree, Object value,
	            boolean selected, boolean expanded, boolean leaf, int row,
	            boolean hasFocus) {
	        
	        Component returnValue;
	        
	        String stringValue = tree.convertValueToText(value, selected,
	                expanded, leaf, row, false);
	        leafRenderer.setText(stringValue);
	        leafRenderer.setSelected(false);
	        
	        leafRenderer.setEnabled(tree.isEnabled());
	        
	        if (selected) {
	            leafRenderer.setForeground(selectionForeground);
	            leafRenderer.setBackground(selectionBackground);
	        } else {
	            leafRenderer.setForeground(textForeground);
	            leafRenderer.setBackground(textBackground);
	        }
	        
	        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
	            Object userObject = ((DefaultMutableTreeNode) value)
	            .getUserObject();
	            if (userObject instanceof CheckBoxNode) {
	                CheckBoxNode node = (CheckBoxNode) userObject;
	                leafRenderer.setText(node.getText());
	                leafRenderer.setSelected(node.isSelected());
	            }
	        }
	        returnValue = leafRenderer;
	        return returnValue;
	    }
	}
	
	private class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	    
	    CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	    
	    ChangeEvent changeEvent = null;
	    
	    JTree tree1;
	    
	    DefaultMutableTreeNode editedNode;
	    
	    public CheckBoxNodeEditor(JTree tree) {
	        this.tree1 = tree;
	    }
	    
	    public Object getCellEditorValue() {
	        JCheckBox checkbox = renderer.getLeafRenderer();
	        CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(),
	                checkbox.isSelected());
	        return checkBoxNode;
	    }
	    
	    public boolean isCellEditable(EventObject event) {
	        boolean returnValue = false;
	        if (event instanceof MouseEvent) {
	            MouseEvent mouseEvent = (MouseEvent) event;
	            TreePath path = tree1.getPathForLocation(mouseEvent.getX(),
	                    mouseEvent.getY());
	            if (path != null) {
	                Object node = path.getLastPathComponent();
	                if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
	                    editedNode = (DefaultMutableTreeNode) node;
	                    Object userObject = editedNode.getUserObject();
	                    Rectangle r = tree1.getPathBounds(path);
	                    int x = mouseEvent.getX() - r.x;
	                    int y = mouseEvent.getY() - r.y;
	                    JCheckBox checkbox = renderer.getLeafRenderer();
	                    checkbox.setText("");
	                    returnValue = userObject instanceof CheckBoxNode && x > 0 && x < checkbox.getPreferredSize().width;
	                }
	            }
	        }
	        return returnValue;
	    }
	    
	    public Component getTreeCellEditorComponent(final JTree tree, Object value,
	            boolean selected, boolean expanded, boolean leaf, int row) {
	        
	        Component editor = renderer.getTreeCellRendererComponent(tree, value,
	                true, expanded, leaf, row, true);
	        
	        ItemListener itemListener = new ItemListener() {
	            public void itemStateChanged(ItemEvent itemEvent) {
	                    tree.repaint();
	                    fireEditingStopped();
	            }
	        };
	        
	        if (editor instanceof JCheckBox) {
	            ((JCheckBox) editor).addItemListener(itemListener);
	        }
	        
	        return editor;
	    }
	}
	
	public class CheckBoxNode {
	    String text;
	    
	    boolean selected;
	    
	    public CheckBoxNode(String text, boolean selected) {
	        this.text = text;
	        this.selected = selected;
	    }
	    
	    public boolean isSelected() {
	        return selected;
	    }
	    
	    public void setSelected(boolean newValue) {
	        selected = newValue;
	    }
	    
	    public String getText() {
	        return text;
	    }
	    
	    public void setText(String newValue) {
	        text = newValue;
	    }
	    
	    public String toString() {
	        return getClass().getName() + "[" + text + "/" + selected + "]";
	    }
	}
}
