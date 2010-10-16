package motion.applet.trees;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import motion.applet.dialogs.OkCancelDialog;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

public class AttributeTree {

	public JTree tree;
	public JPopupMenu popupA;
	private JPopupMenu popupG;
	private JPopupMenu popupN;
	
	public final EntityKind entityKind;
	
	public Vector<EntityAttributeGroup> newGroups = new Vector();
	public Vector<EntityAttributeGroup> removeGroups = new Vector();
	public Vector<EntityAttributeGroup> modifyGroups = new Vector();
	
	public Vector<EntityAttribute> newAttributes = new Vector();
	public Vector<EntityAttribute> removeAttributes = new Vector();
	public Vector<EntityAttribute> modifyAttributes = new Vector();
	
	DefaultMutableTreeNode root; 
	
	public AttributeTree(EntityKind entityKind) {
	
		this.entityKind = entityKind; 
		
		root = new DefaultMutableTreeNode("Root");
		
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
	

	    JMenuItem editAttributeItem = new JMenuItem("Edit"); 
	    JMenuItem removeAttributeItem = new JMenuItem("Remove"); 
	    JMenuItem renameGroupItem = new JMenuItem("Rename"); 
	    JMenuItem removeGroupItem = new JMenuItem("Remove"); 
	    JMenuItem newAttributeItem = new JMenuItem("New Attribute"); 
	    JMenuItem newGroupItem1 = new JMenuItem("New Group");
	    JMenuItem newGroupItem2 = new JMenuItem("New Group");
	    JMenuItem newGroupItem3 = new JMenuItem("New Group");
	    
		popupA = new JPopupMenu();
		popupA.add( editAttributeItem );
		popupA.add( removeAttributeItem );
		popupA.addSeparator();
		popupA.add( newGroupItem1 );
		
		popupG = new JPopupMenu();
		popupG.add( renameGroupItem );
		popupG.add( removeGroupItem );
		popupG.add( newAttributeItem );
		popupG.addSeparator();
		popupG.add( newGroupItem2 );
	
		popupN = new JPopupMenu();
		popupN.add( newGroupItem3 );
		
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		tree.setSelectionModel(selectionModel );
	    tree.addMouseListener(  new MouseAdapter() {
	        public void mouseReleased( MouseEvent e ) {
	        	mousePressed(e);
	        }
	        public void mousePressed( MouseEvent e ) {
	            tree.setSelectionRow( tree.getRowForLocation(e.getX(), e.getY()));
	            if ( e.isPopupTrigger() ){
		            if (tree.getSelectionModel().getSelectionPath() != null) {
		            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
		                if (node.isGroup)
		                	popupG.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		                else	
		                	popupA.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		            }
		            else
		            	popupN.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		        }
	        }
	    });
	    
	    renameGroupItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
            	EntityAttributeGroup group = (EntityAttributeGroup) node.data;

            	TextFieldPanel panel = new TextFieldPanel( "Group name", group.name );
				OkCancelDialog okCancel = new OkCancelDialog( panel );
				okCancel.setVisible( true );
				
				if (okCancel.getResult() == okCancel.OK_PRESSED)
				{	
					group.name = panel.getValue();
					if ( !newGroups.contains( group ) )
						if ( !modifyGroups.contains( group ))
							modifyGroups.add(group);
					node.setName( group.name );
					((DefaultTreeModel)tree.getModel()).reload();
				}
			}
		});

	    removeGroupItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
            	EntityAttributeGroup group = (EntityAttributeGroup) node.data;

				OkCancelDialog okCancel = new OkCancelDialog( "Confirm attribute group removal!" );
				okCancel.setVisible( true );
				
				if (okCancel.getResult() == okCancel.OK_PRESSED)
				{	
					if ( !removeGroups.contains( group ))
					{
						if (newGroups.contains( group ))
							newGroups.remove( group );
						else
							removeGroups.add(group);
					}
					DefaultTreeModel parent = (DefaultTreeModel) tree.getModel();
					parent.removeNodeFromParent(node);
				}
			}
		});

	    final EntityKind kind = entityKind;
	    ActionListener newGroupItemListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
            	TextFieldPanel panel = new TextFieldPanel( "Group name", "new_group" );
				OkCancelDialog okCancel = new OkCancelDialog( panel );
				okCancel.setVisible( true );

				if (okCancel.getResult() == okCancel.OK_PRESSED)
				{	
					EntityAttributeGroup newGroup = new EntityAttributeGroup( panel.getValue(), kind );
					newGroups.add(newGroup);
					root.add( new ConfigTreeNode( newGroup.name, true, newGroup ));
					DefaultTreeModel parent = (DefaultTreeModel) tree.getModel();
					parent.reload();
				}
			}
		};
		newGroupItem1.addActionListener( newGroupItemListener );
		newGroupItem2.addActionListener( newGroupItemListener );
		newGroupItem3.addActionListener( newGroupItemListener );
	}

	class TextFieldPanel extends JPanel
	{
		JTextField editField; 
	
		public TextFieldPanel(String message, String value) {
			super( new GridBagLayout() );

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
			gridBagConstraints.ipadx = 10;
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);
			
			JLabel label = new JLabel(message);
			editField = new JTextField( value ); 
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			this.add( label, gridBagConstraints );
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			this.add( editField, gridBagConstraints );
		}
	
		public String getValue()
		{
	    	return editField.getText();
		}
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
	
	public void setName(String name)
	{
		this.setUserObject( name );
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