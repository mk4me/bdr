package motion.applet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import motion.applet.database.ConnectorInstance;
import motion.applet.dialogs.LoginDialog;
import motion.applet.dialogs.UploadDialog;
import motion.applet.panels.LeftSplitPanel;
import motion.applet.panels.RightSplitPanel;
import motion.applet.toolbars.AppletToolBar;

public class MotionApplet extends JApplet {
	public static String APPLET_NAME = "Motion";
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	
	public void init() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
		initUserInterface();
		
		//Connector connectorTest = new Connector();
		//connectorTest.displayDatabaseProperties();
		
		//MotionWebServiceClient wsClient = new MotionWebServiceClient();
		//wsClient.callWebService();
		
		ConnectorInstance connectorInstance = new ConnectorInstance();
	}
	
	private void initUserInterface() {
		
		
		// Create the menu bar
		JMenuBar appletMenuBar = new JMenuBar();
		JMenu motionMenu = new JMenu("Session");
		appletMenuBar.add(motionMenu);
		JMenuItem uploadItem = new JMenuItem("Upload");
		motionMenu.add(uploadItem);
		uploadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Crete the upload dialog
				UploadDialog uploadDialog = new UploadDialog();
				uploadDialog.setVisible(true);
			}
		});
		
		
		//JMenu menuMenu = new JMenu("Menu");
		//appletMenuBar.add(menuMenu);
		this.setJMenuBar(appletMenuBar);
		
		//Create the tool bar
		AppletToolBar appletToolBar = new AppletToolBar();
		this.getContentPane().add(appletToolBar, BorderLayout.PAGE_START);
		
		
		// Create the horizontal split panels
		LeftSplitPanel leftPanel = new LeftSplitPanel();
		RightSplitPanel rightPanel = new RightSplitPanel();
		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		this.getContentPane().add(leftRightSplitPane);
		
		appletToolBar.addTableComboBoxListener(rightPanel);
		
		// Create the login dialog
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setVisible(true);
		
	}
}
