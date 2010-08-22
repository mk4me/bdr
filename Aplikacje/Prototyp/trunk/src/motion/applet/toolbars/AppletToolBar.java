package motion.applet.toolbars;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import motion.applet.MotionAppletFrame;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.RightSplitPanel;
import motion.applet.tables.BasicTable;
import motion.applet.webservice.client.WebServiceInstance;

public class AppletToolBar extends JToolBar {
	private String[] labs = {"Lab 0", "Lab 1"};
	private JComboBox labComboBox = new JComboBox(labs);
	private static int labId = 1;
	private JComboBox basketComboBox = new JComboBox(new String[]{"--Select basket--", "basket1", "basket2"});
	private JButton newBasketButton;
	private JButton addToBasketButton;
	private JButton removeFromBasketButton;
	private RightSplitPanel rightPanel;
	
	public AppletToolBar(RightSplitPanel rightPanel) {
		super(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		
		this.rightPanel = rightPanel;
		this.addLabComboBoxListener(rightPanel);
		
		FlowLayout appletToolBarLayout = new FlowLayout();
		appletToolBarLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(appletToolBarLayout);
		
		labComboBox.setSelectedIndex(labId);
		this.add(labComboBox);
		this.addSeparator();
		
		this.newBasketButton = new JButton("New basket");
		this.add(newBasketButton);
		
		this.add(basketComboBox);
		
		this.addToBasketButton = new JButton("Add to basket");
		this.addToBasketButton.setEnabled(false);
		this.add(addToBasketButton);
		
		this.removeFromBasketButton = new JButton("Remove from basket");
		this.add(removeFromBasketButton);
		
		addListeners();
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
								BasicTable currentTable = MotionAppletFrame.getCurrentTable();
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
								BasicTable currentTable = MotionAppletFrame.getCurrentTable();
								if (currentTable != null) {
									int[] selectedRecords = currentTable.getCheckedRecordIds();
									String entity = currentTable.getTableName().getEntity();
									String basketName = currentTable.fromBasket;
									for (int i = 0; i < selectedRecords.length; i++) {
										WebServiceInstance.getDatabaseConnection().removeEntityFromBasket(
												basketName, selectedRecords[i], entity);
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
}
