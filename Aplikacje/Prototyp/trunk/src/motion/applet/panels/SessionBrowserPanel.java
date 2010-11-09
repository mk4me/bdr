package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.treetable.JTreeTable;
import javax.swing.treetable.TreeTableModel;
import javax.swing.treetable.TreeTableModelAdapter;

import motion.applet.models.SessionBrowserModel;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.model.EntityKind;
import motion.database.model.Session;

public class SessionBrowserPanel extends JPanel {

	JTreeTable table;
	Session s;
	
	public SessionBrowserPanel() {
	}

	public void setSession( Session s ) throws Exception
	{
		this.s = s;
		SwingWorker<?, ?> worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				
//				SessionBrowserPanel.this.table.setEnabled(false);

				SessionBrowserPanel.this.removeAll();
				SessionBrowserPanel.this.validate();
				SessionBrowserPanel.this.repaint();
				
				table = new JTreeTable( new SessionBrowserModel(SessionBrowserPanel.this.s) );
				table.setAutoResizeMode(JTreeTable.AUTO_RESIZE_ALL_COLUMNS);
				table.setGridColor(Color.lightGray);
				table.setIntercellSpacing( new Dimension(4,1) );
				//table.setRowMargin(4);
				table.setRowHeight( 22 );
				table.setShowGrid(true);
				table.setAutoscrolls( true );
				
				
				SessionBrowserPanel.this.setLayout( new BorderLayout() );
				SessionBrowserPanel.this.add( new JScrollPane(table), BorderLayout.CENTER );

				SessionBrowserPanel.this.validate();
				SessionBrowserPanel.this.repaint();
				
				return null;
			}
		};
		
		worker.execute();
	}
	
	public void setSession(int sessionId) throws Exception {
		Session s = (Session) DatabaseConnection.getInstanceWCF().getById( sessionId, EntityKind.session );
		setSession( s );
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
		
		Session s = (Session) DatabaseConnection.getInstanceWCF().getById( 1, EntityKind.session );
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
