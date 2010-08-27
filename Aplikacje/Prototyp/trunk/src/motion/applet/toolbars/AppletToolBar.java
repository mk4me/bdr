package motion.applet.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.MotionAppletFrame;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.RightSplitPanel;
import motion.applet.tables.BasicTableModel;
import motion.applet.tables.BasketTableModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.UserBasket;

public class AppletToolBar extends JToolBar {
	private String[] labs = {"Lab 0", "Lab 1"};
	private JComboBox labComboBox = new JComboBox(labs);
	private static int labId = 1;
	private JComboBox basketComboBox = new JComboBox();
	private JButton newBasketButton;
	private JButton addToBasketButton;
	private JButton removeFromBasketButton;
	private RightSplitPanel rightPanel;
	
	private static String NEW_BASKET = Messages.getString("AppletToolBar.NewBasket"); //$NON-NLS-1$
	private static String ADD_TO_BASKET = Messages.getString("AppletToolBar.AddToBasket"); //$NON-NLS-1$
	private static String REMOVE_FROM_BASKET = Messages.getString("AppletToolBar.RemoveFromBasket"); //$NON-NLS-1$
	private static String SELECT_BASKET = Messages.getString("AppletToolBar.SelectBasket"); //$NON-NLS-1$
	
	public AppletToolBar(RightSplitPanel rightPanel) {
		super(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		
		this.rightPanel = rightPanel;
		this.addLabComboBoxListener(this.rightPanel);
		
		FlowLayout appletToolBarLayout = new FlowLayout();
		appletToolBarLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(appletToolBarLayout);
		
		labComboBox.setSelectedIndex(labId);
		this.add(labComboBox);
		this.addSeparator();
		
		this.newBasketButton = new JButton(NEW_BASKET);
		this.add(newBasketButton);
		
		basketComboBox.setSize(new Dimension(140, basketComboBox.getPreferredSize().height));
		basketComboBox.setPreferredSize(new Dimension(140, basketComboBox.getPreferredSize().height));
		this.add(basketComboBox);
		
		this.addToBasketButton = new JButton(ADD_TO_BASKET);
		this.addToBasketButton.setEnabled(false);
		this.add(addToBasketButton);
		
		this.removeFromBasketButton = new JButton(REMOVE_FROM_BASKET);
		this.add(removeFromBasketButton);
		
		addListeners();
		
		getBasketListContents();
	}
	
	private void addLabComboBoxListener(ActionListener actionListener) {
		this.labComboBox.addActionListener(actionListener);
	}
	
	private void addListeners() {
		this.newBasketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketDialog basketDialog = new BasketDialog();
				basketDialog.setVisible(true);
			}
		});
		
		this.addToBasketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							if (AppletToolBar.this.validateSelectedBasket()) {
								BasicTableModel currentTable = MotionAppletFrame.getCurrentTable();
								if (currentTable != null) {
									int[] selectedRecords = currentTable.getCheckedRecordIds();
									String entity = currentTable.getTableName().getEntity();
									for (int i = 0; i < selectedRecords.length; i++) {
										WebServiceInstance.getDatabaseConnection().addEntityToBasket(
												AppletToolBar.this.getSelectedBasketName(), selectedRecords[i], entity);
									}
								}
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
		});
		
		this.removeFromBasketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							if (MotionAppletFrame.isBasketPanelVisible()) {
								BasketTableModel currentTable = (BasketTableModel) MotionAppletFrame.getCurrentTable();
								if (currentTable != null) {
									int[] selectedRecords = currentTable.getCheckedRecordIds();
									String entity = currentTable.getTableName().getEntity();
									String basketName = currentTable.fromBasket;
									for (int i = 0; i < selectedRecords.length; i++) {
										WebServiceInstance.getDatabaseConnection().removeEntityFromBasket(
												basketName, selectedRecords[i], entity);
										MotionAppletFrame.getCurrentTable().removeCheckedRecords();
									}
								}
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
		});
		
		this.basketComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateSelectedBasket()) {
					AppletToolBar.this.addToBasketButton.setEnabled(true);
				} else {
					AppletToolBar.this.addToBasketButton.setEnabled(false);
				}
			}
		});
	}
	
	public static void setLabId(int id) {
		labId = id;
	}
	
	public static int getLabId() {
		
		return labId;
	}
	
	private String getSelectedBasketName() {
		if (validateSelectedBasket()) {
			
			return this.basketComboBox.getSelectedItem().toString();
		} else {
			
			return "";
		}
	}
	
	private boolean validateSelectedBasket() {
		if (AppletToolBar.this.basketComboBox.getSelectedIndex() > 0) {	// First item is "select basket" label
			
			return true;
		} else {
			
			return false;
		}
	}
	
	public void getBasketListContents() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					AppletToolBar.this.basketComboBox.removeAllItems();
					AppletToolBar.this.basketComboBox.addItem(SELECT_BASKET);
					DbElementsList<UserBasket> baskets = WebServiceInstance.getDatabaseConnection().listUserBaskets();
					for (UserBasket b : baskets) {
						AppletToolBar.this.basketComboBox.addItem(b);
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
