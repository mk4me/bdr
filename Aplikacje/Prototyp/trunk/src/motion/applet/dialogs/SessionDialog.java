package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;

import motion.applet.models.EntityEditorModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.EntityAttributeGroup;
import motion.database.GenericDescription;

public class SessionDialog extends BasicDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create new session.";
	private static String CANCEL = "Cancel";
	
	private JButton cancelButton;
	
	public SessionDialog() {
		super(TITLE, WELCOME_MESSAGE);
	}
	
	@Override
	protected void constructUserInterface() {
		try {
			GenericDescription<?> entity = WebServiceInstance.getDatabaseConnection().getSessionById(1);
			HashMap<String, EntityAttributeGroup> g = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("session");
			entity.addEmptyGenericAttributes( g );
			JTreeTable treeTable = new JTreeTable(new EntityEditorModel( entity ));
			this.add(new JScrollPane(new JScrollPane(treeTable)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Button area
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void addListeners() {
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				SessionDialog.this.setVisible(false);
				SessionDialog.this.dispose();
			}
		});
	}
}
