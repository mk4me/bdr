package motion.applet;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.LoginDialog;
import motion.applet.panels.BasketPanel;
import motion.applet.panels.LeftSplitPanel;
import motion.applet.panels.RightSplitPanel;
import motion.applet.panels.StatusBar;
import motion.applet.tables.BasicTableModel;
import motion.applet.toolbars.AppletToolBar;
import motion.database.DatabaseConnection;
import motion.widgets.TabCloseButtonWidget;

public class MotionAppletFrame extends JFrame {
	public static String APPLET_NAME = Messages.getString("MotionApplet.AppletName"); //$NON-NLS-1$
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	private static String MENU_VIEW = Messages.getString("MotionApplet.MenuView"); //$NON-NLS-1$
	private static String MENU_ALL_PERFORMERS = Messages.getString("MotionApplet.MenuAllPerformers"); //$NON-NLS-1$
	private static String MENU_ALL_SESSIONS = Messages.getString("MotionApplet.MenuAllSessions"); //$NON-NLS-1$
	private static String MENU_REFRESH = Messages.getString("MotionApplet.MenuRefresh"); //$NON-NLS-1$
	private static String MENU_NEW = Messages.getString("MotionApplet.MenuNew"); //$NON-NLS-1$
	private static String MENU_BASKET = Messages.getString("Basket"); //$NON-NLS-1$
	private static String MENU_UPLOAD = Messages.getString("Upload"); //$NON-NLS-1$
	private static String TAB_BROWSE = Messages.getString("MotionApplet.Browse"); //$NON-NLS-1$
	private static String TAB_QUERY = Messages.getString("MotionApplet.Query"); //$NON-NLS-1$
	private static String TAB_BASKETS = Messages.getString("Baskets"); //$NON-NLS-1$
	private static String MESSAGE_PLEASE_WAIT = Messages.getString("MotionApplet.PleaseWait"); //$NON-NLS-1$
	
	private static RightSplitPanel rightPanel;
	private static BasketPanel basketPanel;
	private static JTabbedPane queryResultsPane;
	
	private static AppletToolBar appletToolBar;
	
	public MotionAppletFrame() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		this.setTitle(APPLET_NAME);
		
		// window closing
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MotionAppletFrame.this.dispose();
			}
		});
		
		// Create the status bar
		StatusBar statusBar =  new StatusBar(this);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		DatabaseConnection.getInstanceWCF().registerStateMessageListener( statusBar );
		statusBar.setMessage(MESSAGE_PLEASE_WAIT);
		this.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initUserInterface();
			}
		});
	}

	private void initUserInterface() {// Create the horizontal split panels
		// Left panel with tool bars
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		createLeftSplitPanel(leftPanel, TableNamesInstance.PERFORMER);
		createLeftSplitPanel(leftPanel, TableNamesInstance.SESSION);
		createLeftSplitPanel(leftPanel, TableNamesInstance.TRIAL);
		
		// Query results
		queryResultsPane = new JTabbedPane();
		
		// Right panel with a tree
		rightPanel = new RightSplitPanel();
		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, queryResultsPane );
		
		// Basket panel
		basketPanel = new BasketPanel();
		JSplitPane basketSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, basketPanel, basketPanel.tablePane);
		basketSplitPane.setResizeWeight(0.1);
		
		// Main tabs
		JTabbedPane mainTabs = new JTabbedPane(JTabbedPane.TOP);
		mainTabs.addTab(TAB_BROWSE, rightPanel);
		mainTabs.addTab(TAB_QUERY, leftRightSplitPane);
		mainTabs.addTab(TAB_BASKETS, basketSplitPane);
		getContentPane().add(mainTabs, BorderLayout.CENTER);
		
		// Create the menu bar
		createMenuBar(rightPanel);
		
		//Create the tool bar
		appletToolBar = new AppletToolBar(rightPanel);
		this.getContentPane().add(appletToolBar, BorderLayout.NORTH);
	}
	
	private void createMenuBar(final RightSplitPanel rightPanel) {
		JMenuBar appletMenuBar = new JMenuBar();
		
		// New menu
		JMenu newMenu = new JMenu(MENU_NEW);
		appletMenuBar.add(newMenu);
		JMenuItem createPerformerItem = new JMenuItem(TableNamesInstance.PERFORMER.getLabel());
		newMenu.add(createPerformerItem);
		createPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showPerformerDialog();
			}
		});
		
		JMenuItem createBasketItem = new JMenuItem(MENU_BASKET);
		newMenu.add(createBasketItem);
		createBasketItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BasketDialog basketDialog = new BasketDialog();
				basketDialog.setVisible(true);
			}
		});
		
		// View menu
		JMenu viewMenu = new JMenu(MENU_VIEW);
		appletMenuBar.add(viewMenu);
		
		JMenuItem viewAllPerformersItem = new JMenuItem(MENU_ALL_PERFORMERS);
		viewMenu.add(viewAllPerformersItem);
		viewAllPerformersItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(TableNamesInstance.PERFORMER);
			}
		});
		
		JMenuItem viewAllSessionsItem = new JMenuItem(MENU_ALL_SESSIONS);
		viewMenu.add(viewAllSessionsItem);
		viewAllSessionsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(TableNamesInstance.SESSION);
			}
		});
		
		JMenuItem viewRefreshItem = new JMenuItem(MENU_REFRESH);
		viewMenu.add(viewRefreshItem);
		viewRefreshItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//rightPanel.refreshAllTables();
				MotionAppletFrame.getCurrentTable().refresh();
			}
		});
		
		// Upload menu
		JMenu uploadMenu = new JMenu(MENU_UPLOAD);
		appletMenuBar.add(uploadMenu);
		JMenuItem uploadPerformerItem = new JMenuItem(TableNamesInstance.PERFORMER.getLabel());
		uploadMenu.add(uploadPerformerItem);
		uploadPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.PERFORMER);
			}
		});
		JMenuItem uploadSessionItem = new JMenuItem(TableNamesInstance.SESSION.getLabel());
		uploadMenu.add(uploadSessionItem);
		uploadSessionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.SESSION);
			}
		});
		JMenuItem uploadTrialItem = new JMenuItem(TableNamesInstance.TRIAL.getLabel());
		uploadMenu.add(uploadTrialItem);
		uploadTrialItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.TRIAL);
			}
		});
		
		this.setJMenuBar(appletMenuBar);
	}
	
	private void createLeftSplitPanel(JPanel leftPanel, TableName tableName) {
		LeftSplitPanel leftSplitPanel = new LeftSplitPanel(tableName);
		leftPanel.add(leftSplitPanel);
	}
	
	public static void addResult(JTable resultTable) {
		queryResultsPane.addTab("query results", new JScrollPane(resultTable));
		queryResultsPane.setSelectedIndex(queryResultsPane.getTabCount()-1);
		TabCloseButtonWidget tabCloseButtonWidget = new TabCloseButtonWidget(TabCloseButtonWidget.RESULTS_TAB_LABEL, queryResultsPane);
		tabCloseButtonWidget.addCloseButton();
	}
	
	public static BasicTableModel getCurrentTable() {
		JTabbedPane tabbedPane = null;
		if (isBrowsePanelVisible()) {
			tabbedPane = rightPanel.getTabbedPane();
		} else if (isBasketPanelVisible()) {
			tabbedPane = basketPanel.getTabbedPane();
		} else if (isQueryPanelVisible()) {
			tabbedPane = queryResultsPane;
		}
		
		if (tabbedPane != null) {
			Component component = tabbedPane.getSelectedComponent();
			if (component instanceof JScrollPane) {
				component = ((JScrollPane) component).getViewport().getView();
				if (component instanceof JTable) {
					return (BasicTableModel) ((JTable) component).getModel();
				}
			}
		}
		
		return null;
	}
	
	public static boolean isBrowsePanelVisible() {
		
		return rightPanel.getTabbedPane().isShowing();
	}
	
	public static boolean isBasketPanelVisible() {
		
		return basketPanel.isShowing();
	}
	
	public static boolean isQueryPanelVisible() {
		
		return queryResultsPane.isShowing();
	}
	
	public static void refreshBaskets() {
		appletToolBar.getBasketListContents();
		basketPanel.getBasketTreeContents();
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Login dialog
				LoginDialog loginDialog = new LoginDialog();
				loginDialog.setVisible(true);
				
				// Check if login was successful
				if (loginDialog.getResult() == LoginDialog.LOGIN_SUCCESSFUL) {	
					MotionAppletFrame motionAppletFrame = new MotionAppletFrame();
					motionAppletFrame.setVisible(true);
				}
		    }
		});
	}
}
