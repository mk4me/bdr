package motion.applet.trees;

import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

public class AttributeTree {

	public JTree tree;
	public JPopupMenu popupA;
	private JPopupMenu popupG;
	
	public AttributeTree(EntityKind entityKind) {
	
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		
		for (EntityAttributeGroup g : entityKind.getGroupedAttributeCopies()) {
			if (g.name.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
				for (EntityAttribute a : g) {
					root.add(new ConfigTreeNode( a.name, false, a ));
				}
			} else {
				DefaultMutableTreeNode groupNode = new ConfigTreeNode( g.name, true, g );
				for (EntityAttribute a : g) {
					groupNode.add(new ConfigTreeNode( a.name, false, a ));
				}
				root.add(groupNode);
			}
		}

		tree = new JTree(root);
		tree.setToggleClickCount(1);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon( null );
		renderer.setOpenIcon( null );
		renderer.setClosedIcon( null );
		tree.setCellRenderer( renderer  );
		tree.putClientProperty("JTree.lineStyle", "None");
		tree.setEditable(false);
	
	
		popupA = new JPopupMenu();
		popupA.add( new JMenuItem("Edit") );
		popupA.add( new JMenuItem("Remove") );
		popupG = new JPopupMenu();
		popupG.add( new JMenuItem("Edit") );
		popupG.add( new JMenuItem("Remove") );
		popupG.add( new JMenuItem("New Attribute") );
		popupA.setLightWeightPopupEnabled(true);
		popupA.setOpaque(true);
		
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		tree.setSelectionModel(selectionModel );
	    tree.addMouseListener(  new MouseAdapter() {
	        public void mouseReleased( MouseEvent e ) {
	        	mousePressed(e);
	        }
	        public void mousePressed( MouseEvent e ) {
	            tree.setSelectionRow( tree.getRowForLocation(e.getX(), e.getY()));
	            if ( e.isPopupTrigger()) {
	            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
	                if (node.isGroup)
	                	popupG.show( (JComponent)e.getSource(), e.getX(), e.getY() );
	                else	
	                	popupA.show( (JComponent)e.getSource(), e.getX(), e.getY() );
	            }
	        }
	    });
	    
	    
	}
}


class ConfigTreeNode extends DefaultMutableTreeNode
{
	public Object data;
	public boolean isGroup;
	
	public ConfigTreeNode(String name, boolean b, Object a) {
		super(name, b);
		this.isGroup = b;
		this.data = a;
	}
}

/*
class AttributeTreeCellRenderer implements DefaultTreeCellRenderer
{

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		new JLabel()
	}
	
}
*/