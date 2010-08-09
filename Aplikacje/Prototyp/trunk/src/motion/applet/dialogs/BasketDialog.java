package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;

public class BasketDialog extends BasicDialog {
	private static String TITLE = "New basket";
	private static String WELCOME_MESSAGE = "Create a new basket.";
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	private static String BASKET_LABEL = "Basket name:";
	private static String CREATING_MESSAGE = "Creating a new basket...";
	private static String MISSING_BASKET_NAME = "Please type the name of the basket.";
	protected static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JTextField basketNameText;
	
	public BasketDialog() {
		super(TITLE, WELCOME_MESSAGE);
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		this.add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		// Basket name
		JLabel basketLabel = new JLabel(BASKET_LABEL);
		formPanel.add(basketLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		this.basketNameText = new JTextField(20);
		basketLabel.setLabelFor(basketNameText);
		formPanel.add(basketNameText, gridBagConstraints);
		
		// Button area
		createButton = new JButton(CREATE);
		this.addToButtonPanel(createButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(400, 200);
	}
	
	@Override
	protected void addListeners() {
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BasketDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							BasketDialog.this.messageLabel.setText(CREATING_MESSAGE);
							BasketDialog.this.createButton.setEnabled(false);
							try {
								WebServiceInstance.getDatabaseConnection().createBasket(BasketDialog.this.getBasketName());
								
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							BasketDialog.this.setVisible(false);
							BasketDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketDialog.this.setVisible(false);
				BasketDialog.this.dispose();
			}
		});
	}
	
	private boolean validateResult() {
		if (this.getBasketName().equals("")) { //$NON-NLS-1$
			this.messageLabel.setText(MISSING_BASKET_NAME);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
	
	private String getBasketName() {
		
		return this.basketNameText.getText();
	}
}