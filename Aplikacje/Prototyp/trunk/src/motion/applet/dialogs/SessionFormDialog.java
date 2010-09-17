package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingWorker;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.panels.PrivilegesPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.MotionKind;
import motion.database.model.SessionStaticAttributes;
import motion.database.ws.SessionPrivilegesSetter;

public class SessionFormDialog extends FormDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create a new session.";
	private static String MISSING_SESSION_DATE = "Missing or incorrect session date.";
	private static String MISSING_MOTION_KIND = "Missing motion kind.";
	private static String CREATING_MESSAGE = "Creating a new session...";
	
	private PrivilegesPanel privilegesPanel;
	
	public SessionFormDialog() {
		super(TITLE, WELCOME_MESSAGE, true);
		
		boolean motionKindsSet = false;
		for (EntityAttributeGroup g : EntityKind.session.getGroupedAttributeCopies()) {
			if (motionKindsSet == false) {
				motionKindsSet = setMotionKinds(g);
			}
			addDefinedFormFields(g, g.name);
		}
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SessionFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							SessionFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							SessionFormDialog.this.createButton.setEnabled(false);
							try {
								int sessionID = WebServiceInstance.getDatabaseConnection().createSession(
										new int[]{},
										SessionFormDialog.this.getSessionDescription(),
										AppletToolBar.getLabId(),
										SessionFormDialog.this.getSessionDate(),
										SessionFormDialog.this.getMotionKind());
								
								setDefinedAttributes(sessionID);
								
								//privileges
								SessionPrivilegesSetter sessionPrivileges = new SessionPrivilegesSetter(sessionID, privilegesPanel.getResult() );
								sessionPrivileges.setUserPrivileges( privilegesPanel.getPrivileges() );
								sessionPrivileges.performDatabaseUpdate();
								
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							SessionFormDialog.this.setVisible(false);
							SessionFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
		
		privilegesPanel = new PrivilegesPanel(this); 
		this.getFormTabbedPane().addTab("Privileges", privilegesPanel);
	}
	
	private boolean setMotionKinds(EntityAttributeGroup group) {
		for (EntityAttribute a : group) {
			if (a.name.equals(SessionStaticAttributes.MotionKindID.toString())) {
				a.enumValues = getMotionKinds();
				
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<String> getMotionKinds() {	//TODO: Add thread.
		Vector<MotionKind> motionKinds = new Vector<MotionKind>();
		try {
			motionKinds = WebServiceInstance.getDatabaseConnection().listMotionKindsDefined();
		} catch (Exception e) {
			
		}
		ArrayList<String> motionKindStrings = new ArrayList<String>();
		
		int i = 0;
		for (MotionKind m : motionKinds) {
			motionKindStrings.add(m.name);
			i++;
		}
		
		return motionKindStrings;
	}
	
	private XMLGregorianCalendar getSessionDate() {
		
		return (XMLGregorianCalendar) getAttributeValue(EntityKind.session, SessionStaticAttributes.SessionDate.toString());
	}
	
	private String getSessionDescription() {
		
		return (String) getAttributeValue(EntityKind.session, SessionStaticAttributes.SessionDescription.toString());
	}
	
	private String getMotionKind() {
		
		return getMotionKinds().get((Integer) getAttributeValue(EntityKind.session, SessionStaticAttributes.MotionKindID.toString()));
	}
	
	private boolean validateResult() {
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}