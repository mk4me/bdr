package motion.applet.trees;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.PopupMenu;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import motion.applet.dialogs.FormValidator;
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
	EntityAttributeGroup selectedGroup;

//	private Window frame;
	
	public AttributeTree(EntityKind entityKind) {
	
//		this.frame = frame;
		this.entityKind = entityKind; 
		
		root = new DefaultMutableTreeNode("Root");
		
		for (EntityAttributeGroup g : entityKind.getGroupedAttributeCopies()) {
			if (g.name.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
/*				for (EntityAttribute a : g) {
					root.add(new ConfigTreeNode( a.name, false, a ));
				}
*/			} else {
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
		tree.setExpandsSelectedPaths(true);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon( null );
		renderer.setOpenIcon( null );
		renderer.setClosedIcon( null );
		tree.setCellRenderer( renderer  );
		tree.putClientProperty("JTree.lineStyle", "None");
		tree.setEditable(false);
	

	    //JMenuItem editAttributeItem = new JMenuItem("Edit"); 
	    JMenuItem removeAttributeItem = new JMenuItem("Remove"); 
	    //JMenuItem renameGroupItem = new JMenuItem("Rename"); 
	    JMenuItem removeGroupItem = new JMenuItem("Remove"); 
	    JMenuItem newAttributeItem = new JMenuItem("New Attribute"); 
	    JMenuItem newGroupItem1 = new JMenuItem("New Group");
	    JMenuItem newGroupItem2 = new JMenuItem("New Group");
	    JMenuItem newGroupItem3 = new JMenuItem("New Group");
	    
		popupA = new JPopupMenu();
		//popupA.add( editAttributeItem );
		popupA.add( removeAttributeItem );
		popupA.addSeparator();
		popupA.add( newGroupItem1 );
		
		popupG = new JPopupMenu();
		//popupG.add( renameGroupItem );
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
		                {
		                	popupG.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		                	selectedGroup = (EntityAttributeGroup) node.data;
		                }
		                else	
		                	popupA.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		            }
		            else
		            	popupN.show( (JComponent)e.getSource(), e.getX(), e.getY() );
		        }
	        }
	    });
	    
/*	    renameGroupItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
            	EntityAttributeGroup group = (EntityAttributeGroup) node.data;

            	TextFieldsPanel panel = new TextFieldsPanel( "Group name", group.name );
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
*/
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

	    removeAttributeItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
            	ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
            	EntityAttribute attribute = (EntityAttribute) node.data;

				OkCancelDialog okCancel = new OkCancelDialog( "Confirm attribute removal!" );
				okCancel.setVisible( true );
				
				if (okCancel.getResult() == okCancel.OK_PRESSED)
				{	
					if ( !removeAttributes.contains( attribute ))
					{
						if (newAttributes.contains( attribute ))
							newAttributes.remove( attribute );
						else
							removeAttributes.add(attribute);
					}
					DefaultTreeModel parent = (DefaultTreeModel) tree.getModel();
					parent.removeNodeFromParent(node);
				}
			}
		});
	    
	    newAttributeItem.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				ConfigTreeNode node = (ConfigTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
            	EntityAttributeGroup group = (EntityAttributeGroup) node.data;

            	String labels[] = { "Name:", "Group:", "Unit:" };
            	String values[] = { "", AttributeTree.this.selectedGroup.name, "" };
            	boolean enabled[] = { true, false, true };
            	String comboLabels[] = { "Type:", "Subtype:" };
            	String comboValues[][] = { {"integer", "string", "float", "date"}, 
            			{"int", "decimal", "nonNegativeInteger", "nonNegativeDecimal", "dateTime", "date", "TIMECODE"} };
            	boolean comboEnabled[] = {true, true };  
            	                     
            	final TextFieldsPanel panel = new TextFieldsPanel(labels, values, enabled);
            	final ComboBoxesPanel comboPanel = new ComboBoxesPanel(comboLabels, comboValues, comboEnabled);
            	final EnumValuesPanel enumValuesPanel = new EnumValuesPanel();
            	
            	JPanel container = new JPanel( new BorderLayout());
            	container.add( panel, BorderLayout.NORTH );
            	container.add( comboPanel, BorderLayout.CENTER );
            	container.add( enumValuesPanel, BorderLayout.SOUTH );
            	
				OkCancelDialog okCancel = new OkCancelDialog( container );
				enumValuesPanel.frame = okCancel;
				okCancel.setValidator( new FormValidator(){
					String errorMessage = null;
					
					@Override
					public boolean validate() {
						
						if (panel.getValue(0).equals("")) {
							errorMessage = "Name property cannot be empty!";
							return false;
						}
						if (enumValuesPanel.isEnum.isSelected() 
								&& enumValuesPanel.getEnumValues().size() == 0)	{
							errorMessage = "Enum must allow at least one value!";
							return false;
						}
						return true;
					}

					@Override
					public String getErrorMessage() {
						return errorMessage;
					}
				});
				okCancel.setVisible( true );

				
				if (okCancel.getResult() == okCancel.OK_PRESSED)
				{
					EntityAttribute attribute = null;//new EntityAttribute( panel.getValue(0), comboPanel.getValue(0), comboPanel.getValue(1), panel.getValue(2), null, group.name);
					attribute.kind = AttributeTree.this.entityKind;
					if (enumValuesPanel.isEnum.isSelected())
					{
						attribute.isEnum = true;
						attribute.enumValues = enumValuesPanel.getEnumValues();
					}
					newAttributes.add( attribute );
					node.add( new ConfigTreeNode( attribute.name, false, attribute ));
					DefaultTreeModel parent = (DefaultTreeModel) tree.getModel();
					parent.reload();
					group.add( attribute );
				}	
			}
		});
	    
	    
	    final EntityKind kind = entityKind;
	    ActionListener newGroupItemListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
            	TextFieldsPanel panel = new TextFieldsPanel( "Group name", "new_group" );
				OkCancelDialog okCancel = new OkCancelDialog( panel );
				okCancel.pack();
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

	
	class TextFieldsPanel extends JPanel
	{
		JTextField editField[]; 

		public TextFieldsPanel(String message[], String value[], boolean enabled[]) {
			super( new GridBagLayout() );
		
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.ipadx = 10;
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);

			editField = new JTextField[message.length];
			for( int i=0; i<message.length; i++)
			{
				JLabel label = new JLabel(message[i]);
				editField[i] = new JTextField( value[i] ); 
				editField[i].setColumns(20);
				editField[i].setEnabled( enabled[i] );
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = i;
				this.add( label, gridBagConstraints );
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = i;
				this.add( editField[i], gridBagConstraints );
			}
		}
		
		public TextFieldsPanel(String message, String value) {
			this( new String[]{message}, new String[]{value}, new boolean[]{true});
		}
	
		public String getValue()
		{
	    	return editField[0].getText();
		}

		public String getValue(int i)
		{
	    	return editField[i].getText();
		}
	}

	
	class EnumValuesPanel extends JPanel
	{
		JCheckBox isEnum;
		JTextArea valuesBox;
		private JPanel bottomPanel;
		public Window frame;
		
		public EnumValuesPanel()
		{
			super( new GridBagLayout() );
			
			isEnum = new JCheckBox();
			isEnum.setText( "only enumeration" );
			valuesBox = new JTextArea(10, 15);
			
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			this.add( isEnum, gridBagConstraints );
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weighty = 10;
			bottomPanel = new JPanel( new BorderLayout() );
			bottomPanel.setVisible( false );
			bottomPanel.add( new JLabel("<html>Write enumeration values<br>" +
					 "in subsequent lines below.<br>" + 
					 "One value per line only.</html>"), BorderLayout.NORTH );
			bottomPanel.add( new JScrollPane(valuesBox), BorderLayout.SOUTH );
			
			this.add(  bottomPanel, gridBagConstraints );
			
			ActionListener changeListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if ( isEnum.isSelected() )
					{
						bottomPanel.setVisible( true );
						frame.pack();
					}
					else
					{
						bottomPanel.setVisible( false );
						frame.pack();
					}
				}
			};
			isEnum.addActionListener( changeListener );
		}

		public List<String> getEnumValues()
		{
			ArrayList<String> result = new ArrayList<String>();
			
			for( int i=0; i<valuesBox.getLineCount(); i++ )
			{
				try {
					String line = valuesBox.getText( valuesBox.getLineStartOffset(i), 
							valuesBox.getLineEndOffset(i)-valuesBox.getLineStartOffset(i) ).trim();
					if (!line.isEmpty())
						result.add( line );
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return result;
		}
	}
	
	class ComboBoxesPanel extends JPanel
	{
		JComboBox comboBoxField[]; 

		public ComboBoxesPanel(String message[], String value[][], boolean enabled[]) {
			super( new GridBagLayout() );
		
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			//gridBagConstraints.
			gridBagConstraints.ipadx = 10;
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);

			comboBoxField = new JComboBox[message.length];
			for( int i=0; i<message.length; i++)
			{
				JLabel label = new JLabel(message[i]);
				comboBoxField[i] = new JComboBox( value[i] ); 
				comboBoxField[i].setEnabled( enabled[i] );
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = i;
				this.add( label, gridBagConstraints );
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = i;
				this.add( comboBoxField[i], gridBagConstraints );
			}
		}
		
		public String getValue(int i)
		{
	    	return (String) comboBoxField[i].getSelectedItem();
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