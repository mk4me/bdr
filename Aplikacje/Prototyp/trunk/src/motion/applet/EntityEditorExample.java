package motion.applet;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;

import motion.applet.models.EntityEditorModel;
import motion.database.DatabaseConnection;
import motion.database.EntityAttributeGroup;
import motion.database.GenericDescription;
import motion.database.Session;
import motion.database.SessionStaticAttributes;


public class EntityEditorExample
{
    public static void main(String[] args) throws Exception {
	new EntityEditorExample();
    }

    public EntityEditorExample() throws Exception {
	JFrame frame = new JFrame("Entity Editor");

	DatabaseConnection database = DatabaseConnection.getInstance();
	database.setWSCredentials("applet", "motion#motion2X", "pjwstk");
	database.setFTPSCredentials("dbpawell", "testUser", "testUser");

	GenericDescription<?> entity = database.getPerformerById(1);
	HashMap<String,EntityAttributeGroup> g = database.listGrouppedAttributesDefined( "performer");
	
	entity.addEmptyGenericAttributes( g );

	JTreeTable treeTable = new JTreeTable(new EntityEditorModel( entity ));

	frame.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent we) {
		System.exit(0);
	    }
	});

	frame.getContentPane().add( new JScrollPane( new JScrollPane(treeTable) ));
	frame.pack();
	frame.setVisible( true );
    }
}

