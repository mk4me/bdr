package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import motion.Messages;
import motion.applet.MotionAppletFrame;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.UserBasket;

public class BasketDialog extends BasicDialog {
	private static String TITLE = Messages.getString("BasketDialog.Title"); //$NON-NLS-1$
	private static String WELCOME_MESSAGE = Messages.getString("BasketDialog.CreateNewBasket"); //$NON-NLS-1$
	private static String CREATE = Messages.getString("Create"); //$NON-NLS-1$
	private static String CLOSE = Messages.getString("Close"); //$NON-NLS-1$
	private static String REMOVE = Messages.getString("Remove"); //$NON-NLS-1$
	private static String BASKET_LABEL = Messages.getString("BasketDialog.NewBasketName") + Messages.COLON; //$NON-NLS-1$
	private static String BASKETS_LABEL = Messages.getString("Baskets") + Messages.COLON; //$NON-NLS-1$
	private static String CREATING_MESSAGE = Messages.getString("BasketDialog.CreatingNewBasket"); //$NON-NLS-1$
	private static String REMOVING_MESSAGE = Messages.getString("BasketDialog.RemovingBasket"); //$NON-NLS-1$
	private static String MISSING_BASKET_NAME = Messages.getString("BasketDialog.MissingBasketNameMessage"); //$NON-NLS-1$
	private static String MISSING_BASKET_SELECTION = Messages.getString("BasketDialog.MissingBasketSelectionMessage"); //$NON-NLS-1$
	protected static String PRESS_CREATE_MESSAGE = Messages.getString("BasketDialog.PressCreateMessage"); //$NON-NLS-1$
	
	private JButton createButton;
	private JButton closeButton;
	private JButton removeButton;
	
	private JTextField basketNameText;
	
	private JList basketList;
	private DefaultListModel basketModel;
	
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
		//gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		basketModel = new DefaultListModel();
		basketList = new JList(basketModel);
		JScrollPane basketScroll = new JScrollPane(basketList);
		//basketScroll.setMaximumSize(new Dimension(50, 50));
		basketScroll.setSize(new Dimension(165, 180));
		basketScroll.setPreferredSize(new Dimension(165, 180));
		formPanel.add(basketScroll, gridBagConstraints);
		
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
		//gridBagConstraints.fill = GridBagConstraints.NONE;
		removeButton = new JButton(REMOVE);
		formPanel.add(removeButton, gridBagConstraints);
		
		// Button area
		closeButton = new JButton(CLOSE);
		this.addToButtonPanel(closeButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(450, 350);
		getBasketListContents();
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
								getBasketListContents();
								MotionAppletFrame.refreshBaskets();
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
								getBasketListContents();
								MotionAppletFrame.refreshBaskets();
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
	
	private void getBasketListContents() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					BasketDialog.this.basketModel.clear();
					DbElementsList<UserBasket> baskets = WebServiceInstance.getDatabaseConnection().listUserBaskets();
					for (UserBasket b : baskets) {
						BasketDialog.this.basketModel.addElement(b);
						//((EntityAttribute) b.get(UserBasketStaticAttributes.basketName)).value);
					}
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
}