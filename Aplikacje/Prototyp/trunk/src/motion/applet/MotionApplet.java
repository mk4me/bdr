package motion.applet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.LoginDialog;
import motion.applet.dialogs.PerformerDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.panels.LeftSplitPanel;
import motion.applet.panels.RightSplitPanel;
import motion.applet.toolbars.AppletToolBar;

public class MotionApplet extends JApplet {
	public static String APPLET_NAME = Messages.getString("MotionApplet.AppletName"); //$NON-NLS-1$
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	
	public void init() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
		initUserInterface();
		
		//Connector connectorTest = new Connector();
		//connectorTest.displayDatabaseProperties();
		
		//MotionWebServiceClient wsClient = new MotionWebServiceClient();
		//wsClient.callWebService();
		
		//ConnectorInstance connectorInstance = new ConnectorInstance();
	}
	
	private void initUserInterface() {
		// Set language
		//Messages.setLanguagePolish();
		Messages.setLanguageEnglish();
		
		// Create the menu bar
		JMenuBar appletMenuBar = new JMenuBar();
		JMenu sessionMenu = new JMenu(Messages.getString("Session")); //$NON-NLS-1$
		appletMenuBar.add(sessionMenu);
		JMenuItem uploadItem = new JMenuItem(Messages.getString("Upload")); //$NON-NLS-1$
		sessionMenu.add(uploadItem);
		uploadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				UploadDialog uploadDialog = new UploadDialog();
				uploadDialog.setVisible(true);
			}
		});
		
		JMenu performerMenu = new JMenu(Messages.getString("Performer")); //$NON-NLS-1$
		appletMenuBar.add(performerMenu);
		JMenuItem createPerformerItem = new JMenuItem(Messages.getString("MotionApplet.New_performer")); //$NON-NLS-1$
		performerMenu.add(createPerformerItem);
		createPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the new performer dialog
				PerformerDialog performerDialog = new PerformerDialog();
				performerDialog.setVisible(true);
				
			}
		});
		
		//JMenu menuMenu = new JMenu("Menu");
		//appletMenuBar.add(menuMenu);
		this.setJMenuBar(appletMenuBar);
		
		//Create the tool bar
		AppletToolBar appletToolBar = new AppletToolBar();
		this.getContentPane().add(appletToolBar, BorderLayout.PAGE_START);
		
		
		// Create the horizontal split panels
		// Left panel with tool bars
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		LeftSplitPanel performerPanel = new LeftSplitPanel(TableNamesInstance.PERFORMER);
		LeftSplitPanel sessionPanel = new LeftSplitPanel(TableNamesInstance.SESSION);
		LeftSplitPanel observationPanel = new LeftSplitPanel(TableNamesInstance.TRIAL);
		leftPanel.add(performerPanel);
		leftPanel.add(sessionPanel);
		leftPanel.add(observationPanel);
		
		// Right panel with a tree
		RightSplitPanel rightPanel = new RightSplitPanel();
		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		this.getContentPane().add(leftRightSplitPane);
		
		appletToolBar.addTableComboBoxListener(rightPanel);
		
		// Create the login dialog
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setVisible(true);
		
	}
}
