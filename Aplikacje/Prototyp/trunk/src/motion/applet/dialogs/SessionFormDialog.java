package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingWorker;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.panels.PerformerAssignmentPanel;
import motion.applet.panels.PrivilegesPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.ws.SessionPrivilegesSetter;

public class SessionFormDialog extends FormDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create a new session.";
	private static String MISSING_SESSION_DATE = "Missing or incorrect session date.";
	private static String CREATING_MESSAGE = "Creating a new session...";
	
	private PrivilegesPanel privilegesPanel;
	private PerformerAssignmentPanel performerAssignmentPanel;
	
	private ArrayList<String> motionKinds;
	
	public SessionFormDialog() {
		super(TITLE, WELCOME_MESSAGE, true);
		
		boolean motionKindsSet = false;
		for (EntityAttributeGroup g : EntityKind.session.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			if (motionKindsSet == false) {
				motionKindsSet = setMotionKinds(g);
			}
			addFormFields(g, g.name);
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
								
								//performers
								int[] selectedPerformers = performerAssignmentPanel.getSelectedPerformers();
								for (int i = 0; i < selectedPerformers.length; i++) {
									WebServiceInstance.getDatabaseConnection().assignPerformerToSession(sessionID, selectedPerformers[i]);
								}
								
								
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
		performerAssignmentPanel = new PerformerAssignmentPanel();
		this.getFormTabbedPane().addTab("Performers", performerAssignmentPanel);
	}
	
	public SessionFormDialog(Session session) {
		this();
		fillFormFields(session);
		fillSessionPerformers(session);
	}
	
	private void fillSessionPerformers(final Session session) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					DbElementsList<Performer> performers = WebServiceInstance.getDatabaseConnection().listSessionPerformersWithAttributes(session.getId());
					int[] recordIds = new int[performers.size()];
					int i = 0;
					for (Performer p : performers) {
						recordIds[i] = p.getId();
					}
					performerAssignmentPanel.setSelectedPerformers(recordIds);
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
			}
		};
		worker.execute();
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
	
	private ArrayList<String> getMotionKinds() {
		if (motionKinds != null) {
			
			return motionKinds;
		} else {
			Vector<MotionKind> motionKindsDefined = new Vector<MotionKind>();
			try {
				motionKindsDefined = WebServiceInstance.getDatabaseConnection().listMotionKindsDefined();
			} catch (Exception e) {
				
			}
			motionKinds = new ArrayList<String>();
			
			int i = 0;
			for (MotionKind m : motionKindsDefined) {
				motionKinds.add(m.name);
				i++;
			}
			
			return motionKinds;
		}
	}
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(SessionStaticAttributes.SessionID.toString());
		attributes.add(SessionStaticAttributes.LabID.toString());
		attributes.add(SessionStaticAttributes.UserID.toString());
		
		return attributes;
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
	
	protected boolean validateResult() {
		if (getSessionDate() == null) {
			this.messageLabel.setText(MISSING_SESSION_DATE);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return super.validateResult();
	}
}