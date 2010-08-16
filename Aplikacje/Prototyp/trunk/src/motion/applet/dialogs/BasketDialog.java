package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;

public class BasketDialog extends BasicDialog {
	private static String TITLE = "New basket";
	private static String WELCOME_MESSAGE = "Create a new basket.";
	private static String CREATE = Messages.CREATE;
	private static String CLOSE = Messages.CLOSE;
	private static String REMOVE = Messages.REMOVE;
	private static String BASKET_LABEL = "New basket name:";
	private static String BASKETS_LABEL = "Baskets:";
	private static String CREATING_MESSAGE = "Creating a new basket...";
	private static String REMOVING_MESSAGE = "Removing basket...";
	private static String MISSING_BASKET_NAME = "Please type the name of the basket.";
	private static String MISSING_BASKET_SELECTION = "Select a basket to remove.";
	protected static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	
	private JButton createButton;
	private JButton closeButton;
	private JButton removeButton;
	
	private JTextField basketNameText;
	
	private JList basketList;
	
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
		gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		// Basket name
		JLabel basketLabel = new JLabel(BASKET_LABEL);
		formPanel.add(basketLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		this.basketNameText = new JTextField(15);
		basketLabel.setLabelFor(basketNameText);
		formPanel.add(basketNameText, gridBagConstraints);
		
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		createButton = new JButton(CREATE);
		formPanel.add(createButton);
		
		// Baskets
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel basketsLabel = new JLabel(BASKETS_LABEL);
		formPanel.add(basketsLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		basketList = new JList(new String[]{"basket1", "basket2"});
		formPanel.add(new JScrollPane(basketList), gridBagConstraints);
		
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
		removeButton = new JButton(REMOVE);
		formPanel.add(removeButton, gridBagConstraints);
		
		// Button area
		closeButton = new JButton(CLOSE);
		this.addToButtonPanel(closeButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(420, 350);
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
							BasketDialog.this.messageLabel.setText(WELCOME_MESSAGE);
							BasketDialog.this.createButton.setEnabled(true);
						}
					};
					worker.execute();
				}
			}
		});
		
		this.removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BasketDialog.this.validateResult2() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							BasketDialog.this.messageLabel.setText(REMOVING_MESSAGE);
							BasketDialog.this.removeButton.setEnabled(false);
							try {
								WebServiceInstance.getDatabaseConnection().removeBasket(BasketDialog.this.getBasketNameToRemove());
								
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							BasketDialog.this.messageLabel.setText(WELCOME_MESSAGE);
							BasketDialog.this.removeButton.setEnabled(true);
						}
					};
					worker.execute();
				}
			}
		});
		
		this.closeButton.addActionListener(new ActionListener() {
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
	
	private boolean validateResult2() {
		if (this.getBasketNameToRemove().equals("")) { //$NON-NLS-1$
			this.messageLabel.setText(MISSING_BASKET_SELECTION);
			
			return false;
		}
		
		return true;
	}
	
	private String getBasketName() {
		
		return this.basketNameText.getText();
	}
	
	private String getBasketNameToRemove() {
		if (this.basketList.isSelectionEmpty()) {
			
			return "";
		} else {
			return this.basketList.getSelectedValue().toString();
		}
	}
}