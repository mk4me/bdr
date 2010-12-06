package motion.applet;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import motion.Messages;
import motion.applet.dialogs.AttributeEditDialog;
import motion.applet.dialogs.BasketDialog;
import motion.applet.dialogs.LoginDialog;
import motion.applet.dialogs.OkCancelDialog;
import motion.applet.dialogs.WizardDialog;
import motion.applet.mouse.PerformerMouseAdapter;
import motion.applet.mouse.SessionMouseAdapter;
import motion.applet.mouse.TrialMouseAdapter;
import motion.applet.panels.BasketPanel;
import motion.applet.panels.LeftSplitPanel;
import motion.applet.panels.RightSplitPanel;
import motion.applet.panels.SessionBrowserPanel;
import motion.applet.panels.StatusBar;
import motion.applet.panels.WizardPanel;
import motion.applet.panels.WizardSessionBrowserPanel;
import motion.applet.panels.WizardSessionDirectoryPanel;
import motion.applet.tables.BasicTableModel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.widgets.TabCloseButtonWidget;
import motion.database.DatabaseConnection;
import motion.database.model.EntityKind;

public class MotionApplet extends JApplet {
	private static final String MENU_ADMINISTER = "Admin";
	public static String APPLET_NAME = Messages.getString("MotionApplet.AppletName"); //$NON-NLS-1$
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	private static String MENU_VIEW = Messages.getString("MotionApplet.MenuView"); //$NON-NLS-1$
	private static String MENU_ALL_PERFORMERS = Messages.getString("MotionApplet.MenuAllPerformers"); //$NON-NLS-1$
	private static String MENU_ALL_SESSIONS = Messages.getString("MotionApplet.MenuAllSessions"); //$NON-NLS-1$
	private static String MENU_ALL_SESSION_GROUPS = "All session groups";
	private static String MENU_VIEW_CONFIGURATION = "View configuration";
	private static String MENU_REFRESH = Messages.getString("MotionApplet.MenuRefresh"); //$NON-NLS-1$
	private static String MENU_NEW = Messages.getString("MotionApplet.MenuNew"); //$NON-NLS-1$
	private static String MENU_BASKET = Messages.getString("Basket"); //$NON-NLS-1$
	private static String MENU_UPLOAD = Messages.getString("Upload"); //$NON-NLS-1$
	private static String TAB_BROWSE = Messages.getString("MotionApplet.Browse"); //$NON-NLS-1$
	private static String TAB_QUERY = Messages.getString("MotionApplet.Query"); //$NON-NLS-1$
	private static String TAB_BASKETS = Messages.getString("Baskets"); //$NON-NLS-1$
	private static final String TAB_SESSION_BROWSER = "Session Content Browser";
	private static String MESSAGE_PLEASE_WAIT = Messages.getString("MotionApplet.PleaseWait"); //$NON-NLS-1$

	public static String LANGUAGE_ENGLISH = "English";
	public static String LANGUAGE_POLISH = "Polish";

	private static JTabbedPane mainTabs;
	
	private static RightSplitPanel rightPanel;
	private static BasketPanel basketPanel;
	private static JTabbedPane queryResultsPane;
	
	private static AppletToolBar appletToolBar;

	String language = null;
	private boolean noApplet;
	private SessionBrowserPanel sessionBrowserPanel;
	
	public static Container contentPane;
	
	public MotionApplet() {
		this(null);
		noApplet = false;
	}
	
	public MotionApplet(String language) {
		this.language = language;
		noApplet  = true;
		contentPane = this.getContentPane();
	}
	
	public SessionBrowserPanel getSessionBrowserPanel()
	{
		return sessionBrowserPanel;
	}
	
	private void initUserInterface() {// Create the horizontal split panels
		// Left panel with tool bars
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		createLeftSplitPanel(leftPanel, EntityKind.performer);
		createLeftSplitPanel(leftPanel, EntityKind.session);
		createLeftSplitPanel(leftPanel, EntityKind.trial);
		
		// Query results
		queryResultsPane = new JTabbedPane();
		
		// Right panel with attribute tree
		rightPanel = new RightSplitPanel(this);
		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, queryResultsPane );
		
		// Basket panel
		basketPanel = new BasketPanel();
		JSplitPane basketSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, basketPanel, basketPanel.tablePane);
		basketSplitPane.setResizeWeight(0.1);
		
		// Session browser panel
		sessionBrowserPanel = new SessionBrowserPanel();
		
		// Main tabs
		mainTabs = new JTabbedPane(JTabbedPane.TOP);
		mainTabs.addTab(TAB_BROWSE, rightPanel);
		mainTabs.addTab(TAB_QUERY, leftRightSplitPane);
		mainTabs.addTab(TAB_BASKETS, basketSplitPane);
		mainTabs.addTab(TAB_SESSION_BROWSER, sessionBrowserPanel);
		getContentPane().add(mainTabs, BorderLayout.CENTER);
		
		// Create the menu bar
		createMenuBar(rightPanel);
		
		//Create the tool bar
		appletToolBar = new AppletToolBar(rightPanel);
		this.getContentPane().add(appletToolBar, BorderLayout.NORTH);
	}
	
	public void switchToSessionBrowser()
	{
		mainTabs.setSelectedIndex(3);
	}
	
	private void createMenuBar(final RightSplitPanel rightPanel) {
		JMenuBar appletMenuBar = new JMenuBar();
		
		// New menu
		JMenu newMenu = new JMenu(MENU_NEW);
		appletMenuBar.add(newMenu);
		JMenuItem createPerformerItem = new JMenuItem(EntityKind.performer.getGUIName());
		newMenu.add(createPerformerItem);
		createPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showPerformerDialog(null);
			}
		});
		
		JMenuItem createSessionItem = new JMenuItem(EntityKind.session.getGUIName());
		newMenu.add(createSessionItem);
		createSessionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showSessionDialog(null);
			}
		});
		
		JMenuItem createMeasurementConfigurationItem = new JMenuItem(EntityKind.measurement_conf.getGUIName());
		newMenu.add(createMeasurementConfigurationItem);
		createMeasurementConfigurationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showMeasurementConfigurationDialog(null);
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
				rightPanel.showTable(EntityKind.performer);
			}
		});
		
		JMenuItem viewAllSessionsItem = new JMenuItem(MENU_ALL_SESSIONS);
		viewMenu.add(viewAllSessionsItem);
		viewAllSessionsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(EntityKind.session);
			}
		});
		
		JMenuItem viewAllSessionGroupsItem = new JMenuItem(MENU_ALL_SESSION_GROUPS);
		viewMenu.add(viewAllSessionGroupsItem);
		viewAllSessionGroupsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(EntityKind.sessionGroup);
			}
		});
		
		JRadioButtonMenuItem viewConfigurationItem = new JRadioButtonMenuItem(MENU_VIEW_CONFIGURATION, false);
		viewMenu.add(viewConfigurationItem);
		viewConfigurationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.toggleViewConfiguration();
			}
		});
		
		JMenuItem viewRefreshItem = new JMenuItem(MENU_REFRESH);
		viewMenu.add(viewRefreshItem);
		viewRefreshItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//rightPanel.refreshAllTables();
				BasicTableModel currentTable = MotionApplet.getCurrentTable();
				if (currentTable != null) {
					currentTable.refresh();
				}
			}
		});
		
		// Upload menu
		JMenu uploadMenu = new JMenu(MENU_UPLOAD);
		appletMenuBar.add(uploadMenu);
/*		JMenuItem uploadPerformerItem = new JMenuItem(EntityKind.performer.getGUIName());
		uploadMenu.add(uploadPerformerItem);
		uploadPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(EntityKind.performer);
			}
		});
*/		JMenuItem uploadSessionItem = new JMenuItem(EntityKind.session.getGUIName());
		uploadMenu.add(uploadSessionItem);
		uploadSessionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(EntityKind.session);
			}
		});
		JMenuItem uploadTrialItem = new JMenuItem(EntityKind.trial.getGUIName());
		uploadMenu.add(uploadTrialItem);
		uploadTrialItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(EntityKind.trial);
			}
		});
	
		// Administer menu
		
		JMenu administerMenu = new JMenu(MENU_ADMINISTER);
		appletMenuBar.add(administerMenu);
		
		JMenu ega = new JMenu( "Edit Generic Attributes" );
		for (final EntityKind entityKind : EntityKind.kindsWithGenericAttributes )
		{
			JMenuItem item = new JMenuItem(entityKind.getGUIName());
			item.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					AttributeEditDialog dialog = new AttributeEditDialog(entityKind);
					dialog.setVisible( true );
				}
			});
			ega.add( item );
		}
		administerMenu.add( ega );
		this.setJMenuBar(appletMenuBar);
		
		// Wizard
		JMenuItem wizardItem = new JMenuItem("Session wizard");
		newMenu.add(wizardItem);
		wizardItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WizardDialog wizardDialog = new WizardDialog("Session wizard",
						new ArrayList<WizardPanel>(Arrays.asList(
								new WizardSessionDirectoryPanel("Choose session directory to upload.", true, false, true, false),
								new WizardSessionBrowserPanel("Session validation.", true, true, false, true)
								
								)
						)
				);
				wizardDialog.setVisible(true);
			}
		});
	}
	
	private void createLeftSplitPanel(JPanel leftPanel, EntityKind entityKind) {
		LeftSplitPanel leftSplitPanel = new LeftSplitPanel(entityKind);
		leftPanel.add(leftSplitPanel);
	}
	
	public static void addResultTab(JTable table) {
		addTableMouseListeners(table);
		queryResultsPane.addTab("query results", new JScrollPane(table));
		queryResultsPane.setSelectedIndex(queryResultsPane.getTabCount()-1);
		TabCloseButtonWidget tabCloseButtonWidget = new TabCloseButtonWidget(TabCloseButtonWidget.RESULTS_TAB_LABEL, queryResultsPane);
		tabCloseButtonWidget.addCloseButton();
	}
	
	public static void addBasketTab(JTable table, String tabLabel) {
		addTableMouseListeners(table);
		basketPanel.getTabbedPane().addTab("basket records", new JScrollPane(table));
		basketPanel.getTabbedPane().setSelectedIndex(basketPanel.getTabbedPane().getTabCount()-1);
		TabCloseButtonWidget tabCloseButtonWidget = new TabCloseButtonWidget(tabLabel, basketPanel.getTabbedPane());
		tabCloseButtonWidget.addCloseButton();
	}
	
	private static void addTableMouseListeners(JTable table) {
		EntityKind entityKind = ((BasicTableModel) table.getModel()).getEntityKind();
		if (entityKind != null) {
			if (entityKind.equals(EntityKind.performer)) {
				table.addMouseListener(new PerformerMouseAdapter(rightPanel));
			} else if (entityKind.equals(EntityKind.session)) {
				table.addMouseListener(new SessionMouseAdapter(rightPanel));
			} else if (entityKind.equals(EntityKind.trial)) {
				table.addMouseListener(new TrialMouseAdapter(rightPanel));
			}
		}
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
					TableModel tableModel = ((JTable) component).getModel();
					if (tableModel instanceof BasicTableModel) {
						
						return (BasicTableModel) tableModel;
					}
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
	
	public static void setBrowsePanelVisible() {
		mainTabs.setSelectedIndex(0);
	}
	
	public static void refreshBaskets() {
		appletToolBar.getBasketListContents();
		basketPanel.getBasketTreeContents();
	}
	
	public static void refreshTables() {
		rightPanel.refreshAttributes();
	}
	
	@Override
	public void init () {
		this.setSize(MotionApplet.APPLET_WIDTH, MotionApplet.APPLET_HEIGHT);
		if (language == null)
			language = this.getParameter("language");

		if (language!= null && language.equals(LANGUAGE_ENGLISH)) {
			Messages.setLanguageEnglish();
		} else if (language!= null && language.equals(LANGUAGE_POLISH)) {
			Messages.setLanguagePolish();
		} else
			Messages.setLanguageEnglish();
		
		
		// Login dialog
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setVisible(true);
		
		while(!loginDialog.finished);
		
		// Check if login was successful
		if (loginDialog.getResult() != LoginDialog.LoginResult.LOGIN_SUCCESSFUL) {	
			System.err.println("Login unsuccesfull!");
			
			OkCancelDialog d = new OkCancelDialog("<html>Login filed. Cannot continue!<br>  Login result:" + loginDialog.getResult().toString() + "</html>" );
			d.setMessage("Login failed!");
			d.hideCancelButton();
			d.setVisible(true);
			while (!d.finished);
			if (noApplet)
				System.exit(1);
			else
				return;
		}

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
}
