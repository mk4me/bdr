package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import motion.Messages;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;

public class SessionAssignmentDialog extends BasicDialog {
	private static String TITLE = "Assign session";
	private static String WELCOME_MESSAGE = "Assign selected performers to a session.";
	
	private static String ASSIGN = "Assign";
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String SESSION = "Session" + Messages.COLON;
	
	private JButton assignButton;
	private JButton cancelButton;
	
	private JComboBox sessionComboBox;
	
	private int[] performerIds;
	
	public SessionAssignmentDialog(int[] recordIds) {
		super(TITLE, WELCOME_MESSAGE);
		this.performerIds = recordIds;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		// Session combo box
		JLabel sessionLabel = new JLabel(SESSION);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(sessionLabel, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		sessionComboBox = new JComboBox();
		sessionComboBox.setSize(new Dimension(200, sessionComboBox.getPreferredSize().height));
		sessionComboBox.setPreferredSize(new Dimension(200, sessionComboBox.getPreferredSize().height));
		formPanel.add(sessionComboBox, gridBagConstraints);
		
		this.add(formPanel, BorderLayout.CENTER);
		
		// Button area
		assignButton = new JButton(ASSIGN);
		this.addToButtonPanel(assignButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(400, 200);
		this.setLocation(200, 200);
		
		assignButton.setEnabled(false);
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					SessionAssignmentDialog.this.getSessionComboBoxContents();
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				if (sessionComboBox.getItemCount() > 0) {
					assignButton.setEnabled(true);
				}
			}
		};
		worker.execute();
	}
	
	@Override
	protected void addListeners() {
		this.assignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							int sessionId = Integer.parseInt(((Session) sessionComboBox.getSelectedItem()).
									get(SessionStaticAttributes.SessionID.toString()).
									value.toString());
							//TODO: WebServiceInstance.getDatabaseConnection().
						} catch (Exception e1) {
							ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
							exceptionDialog.setVisible(true);
						}
						return null;
					}
					
					@Override
					protected void done() {
						SessionAssignmentDialog.this.setVisible(false);
						SessionAssignmentDialog.this.dispose();
					}
				};
				worker.execute();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				WebServiceInstance.getDatabaseConnection().cancelCurrentFileTransfer();
				SessionAssignmentDialog.this.setVisible(false);
				SessionAssignmentDialog.this.dispose();
			}
		});
	}
	
	private void getSessionComboBoxContents() throws Exception {
		DbElementsList<Session> list = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
		for (Session s : list) {
			sessionComboBox.addItem(s);
		}
	}
}