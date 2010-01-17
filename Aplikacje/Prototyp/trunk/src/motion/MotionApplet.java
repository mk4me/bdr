package motion;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import motion.dialogs.LoginDialog;
import motion.panels.LeftSplitPanel;
import motion.panels.RightSplitPanel;
import motion.toolbars.AppletToolBar;
import motion.webservice.client.MotionWebServiceClient;

public class MotionApplet extends JApplet {
	public static String APPLET_NAME = "Motion";
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	
	public void init() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
		initUserInterface();
		
		//Connector connectorTest = new Connector();
		//connectorTest.displayDatabaseProperties();
		
		MotionWebServiceClient wsClient = new MotionWebServiceClient();
		wsClient.callWebService();
	}
	
	private void initUserInterface() {
		
		
		// Create the menu bar
		JMenuBar appletMenuBar = new JMenuBar();
		JMenu motionMenu = new JMenu(MotionApplet.APPLET_NAME);
		appletMenuBar.add(motionMenu);
		JMenuItem optionItem = new JMenuItem("Option");
		motionMenu.add(optionItem);
		
		JMenu menuMenu = new JMenu("Menu");
		appletMenuBar.add(menuMenu);
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
