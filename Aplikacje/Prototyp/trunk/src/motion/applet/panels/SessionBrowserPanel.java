package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.treetable.JTreeTable;
import javax.swing.treetable.TreeTableModel;
import javax.swing.treetable.TreeTableModelAdapter;

import motion.applet.models.SessionBrowserModel;
import motion.applet.models.SessionBrowserModel.AttributedVectorView;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.Session;

@SuppressWarnings("serial")
public class SessionBrowserPanel extends JPanel {

	JTreeTable table;
	Session[] s;

	DatabaseFile file = null;
	EntityAttribute attribute = null;
	GenericDescription<?> entity = null;  

	public SessionBrowserPanel() {
	}


	public void setSession( Session[] s ) throws Exception
	{
		this.s = s;
		SwingWorker<?, ?> worker = new SwingWorker<Object, Object>(){

			@Override
			protected Object doInBackground() throws Exception {
				
//				SessionBrowserPanel.this.table.setEnabled(false);

				SessionBrowserPanel.this.removeAll();
				SessionBrowserPanel.this.validate();
				SessionBrowserPanel.this.repaint();
				
				table = new JTreeTable( new SessionBrowserModel(SessionBrowserPanel.this.s ) );
				table.setAutoResizeMode(JTreeTable.AUTO_RESIZE_ALL_COLUMNS);
				table.setGridColor(Color.lightGray);
				table.setIntercellSpacing( new Dimension(4,1) );
				//table.setRowMargin(4);
				table.setRowHeight( 19 );
				table.setShowGrid(true);
				table.setAutoscrolls( true );
				table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
				table.getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				
				
				SessionBrowserPanel.this.setLayout( new BorderLayout() );
				SessionBrowserPanel.this.add( new JScrollPane(table), BorderLayout.CENTER );

				SessionBrowserPanel.this.validate();
				SessionBrowserPanel.this.repaint();
	
				table.addMouseListener( new MouseAdapter(){
					
					public void mouseClicked(final MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
							DefaultListSelectionModel selectionModel = (DefaultListSelectionModel) table.getSelectionModel();

							JPopupMenu popupMenu = new JPopupMenu();

							if (table.getSelectedRows().length == 2)
							{
								Object o1 = table.getTree().getPathForRow( table.getSelectedRows()[0] ).getLastPathComponent();
								Object o2 = table.getTree().getPathForRow( table.getSelectedRows()[1] ).getLastPathComponent();
								
								if (o1 instanceof SessionBrowserModel.FileView)
									SessionBrowserPanel.this.file = (DatabaseFile) ((SessionBrowserModel.FileView)o1).entity;
								else if (o2 instanceof SessionBrowserModel.FileView)
									SessionBrowserPanel.this.file = (DatabaseFile) ((SessionBrowserModel.FileView)o2).entity;

								if (o1 instanceof SessionBrowserModel.AttributeView)
								{
									SessionBrowserPanel.this.attribute = (EntityAttribute) ((SessionBrowserModel.AttributeView)o1).attribute;
									SessionBrowserPanel.this.entity =  findParentEntity( table.getTree().getPathForRow( table.getSelectedRows()[0] ) );
								}
								else if (o2 instanceof SessionBrowserModel.AttributeView)
								{
									SessionBrowserPanel.this.attribute = (EntityAttribute) ((SessionBrowserModel.AttributeView)o2).attribute;
									SessionBrowserPanel.this.entity =  findParentEntity( table.getTree().getPathForRow( table.getSelectedRows()[1] ) );
								}

								if (file != null && attribute != null)
								{
									if (attribute.type.equals(EntityAttribute.TYPE_FILE))
									{
										JMenuItem assignFileMenuItem = new JMenuItem("Assign file to attribute");
										popupMenu.add(assignFileMenuItem);
										
										assignFileMenuItem.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												System.out.println("Assigning file to attribute now!");
												try {
													boolean isUpdate = ( getInteger(attribute.value) != 0);
													DatabaseConnection.getInstance().setFileTypedAttribute(
															SessionBrowserPanel.this.entity.getId(), 
															SessionBrowserPanel.this.attribute, 
															SessionBrowserPanel.this.file.getId(), isUpdate);
												} catch (Exception e1) {
													e1.printStackTrace();
													DatabaseConnection.log.severe( e1.getMessage() );
												}
											}
										});
										
										
									}
								}
							}
							else if (table.getSelectedRows().length == 1)
							{
								Object o1 = table.getTree().getPathForRow( table.getSelectedRows()[0] ).getLastPathComponent();
								if (o1 instanceof SessionBrowserModel.AttributeView)
								{
									SessionBrowserPanel.this.attribute = (EntityAttribute) ((SessionBrowserModel.AttributeView)o1).attribute;
									if (getInteger(attribute.value) > 0)
									{
										JMenuItem removeFileAssignmentMenuItem = new JMenuItem("Remove file assignment"); 
										popupMenu.add(removeFileAssignmentMenuItem);
											removeFileAssignmentMenuItem.addActionListener( new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												try {
													DatabaseConnection.getInstance().clearEntityAttribute( SessionBrowserPanel.this.entity.getId(), attribute);
												} catch (Exception e1) {
													e1.printStackTrace();
													DatabaseConnection.log.severe( e1.getMessage() );
												}
											}
										});
									}
								}
							}

							popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
						}
					}

					private int getInteger(Object value) {
						if (value instanceof Integer)
							return (Integer)value;
						else if (value instanceof String)
							return Integer.parseInt( (String) value );
						else
							return 0;
					}
					
					private GenericDescription<?> findParentEntity(
							TreePath path) {

						int jump = path.getPathCount()-3; 
						if ( ! (path.getPathComponent(jump) instanceof SessionBrowserModel.AttributedVectorView) )
							jump--;
						
						return ((AttributedVectorView)path.getPathComponent( jump )).entity;
					};
				});
				return null;
			}
		};
		
		worker.execute();
	}
	
	public void setSession(int[] recordIds) throws Exception {
		Session [] session = new Session[recordIds.length];
		for (int i=0; i<recordIds.length; i++)
		{
			session[i] = (Session) DatabaseConnection.getInstance().getById( recordIds[i], EntityKind.session);
			for (EntityAttribute generic: EntityKind.session.getGenericAttributes())
			{
				if ( session[i].get( generic.name ) == null ) //&& generic.type == EntityAttribute.TYPE_FILE)
				{	
					generic.emptyValue();
					session[i].put( generic.name, generic );
				}
			}
		}
		setSession(session);
	}

	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		DatabaseProxy database = DatabaseConnection.getInstanceWCF();
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

		JFrame b = new JFrame();
		b.setLayout( new BorderLayout() );
		
		Session[] s = {(Session) DatabaseConnection.getInstanceWCF().getById( 1, EntityKind.session )};
		SessionBrowserPanel p = new SessionBrowserPanel(); 
		b.add( p, BorderLayout.CENTER );
		
		p.setSession( s );
		
		b.pack();
		b.setSize(400, 400);
		b.setVisible( true );
		b.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);		
			}
		});	}

}
