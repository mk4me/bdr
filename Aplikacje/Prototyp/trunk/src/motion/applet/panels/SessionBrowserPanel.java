package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.treetable.JTreeTable;
import javax.swing.treetable.TreeTableModel;
import javax.swing.treetable.TreeTableModelAdapter;

import motion.applet.models.SessionBrowserModel;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.model.EntityKind;
import motion.database.model.Session;

public class SessionBrowserPanel extends JPanel {

	int sessionID;
	
	public SessionBrowserPanel(int i) {
		
		this.sessionID = i;
		createUserInterface();
	}

	public void createUserInterface()
	{
		try {
			Session s = (Session) DatabaseConnection.getInstanceWCF().getById( sessionID, EntityKind.session );
			
			SessionBrowserModel model = new SessionBrowserModel(s);
			JTreeTable table = new JTreeTable(model);
			table.setAutoResizeMode(JTreeTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setGridColor(Color.lightGray);
			table.setIntercellSpacing( new Dimension(4,1) );
			//table.setRowMargin(4);
			table.setRowHeight( 22 );
			table.setShowGrid(true);
			
			this.setLayout( new BorderLayout() );
			this.add( table, BorderLayout.CENTER );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DatabaseProxy database = DatabaseConnection.getInstanceWCF();
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

		JFrame b = new JFrame();
		b.setLayout( new BorderLayout() );
		b.add( new SessionBrowserPanel(2), BorderLayout.CENTER );
		
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
