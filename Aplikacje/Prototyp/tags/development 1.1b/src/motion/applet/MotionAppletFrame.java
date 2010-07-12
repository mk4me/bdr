package motion.applet;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.LoginDialog;
import motion.applet.panels.LeftSplitPanel;
import motion.applet.panels.RightSplitPanel;
import motion.applet.panels.StatusBar;
import motion.applet.toolbars.AppletToolBar;
import motion.database.DatabaseConnection;

public class MotionAppletFrame extends JFrame {
	public static String APPLET_NAME = Messages.getString("MotionApplet.AppletName"); //$NON-NLS-1$
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	
	private static JTabbedPane queryResultsPane;
	
	public MotionAppletFrame() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		this.setTitle(APPLET_NAME);
		
		initUserInterface();
	}

	private void initUserInterface() {
		// Create the status bar
		StatusBar statusBar =  new StatusBar(this);
		this.getContentPane().add( statusBar, BorderLayout.SOUTH );
		DatabaseConnection.getInstanceWCF().registerStateMessageListener( statusBar );
		
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

		// Query results
		queryResultsPane = new JTabbedPane();
		
		// Right panel with a tree
		final RightSplitPanel rightPanel = new RightSplitPanel();
		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, queryResultsPane );
		//this.getContentPane().add(leftRightSplitPane);
		appletToolBar.addTableComboBoxListener(rightPanel);


		// Main tabs
		JTabbedPane mainTabs = new JTabbedPane(JTabbedPane.TOP);
		mainTabs.addTab("   Browse   ", rightPanel);
		mainTabs.addTab("   Query   ", leftRightSplitPane);
		this.getContentPane().add( mainTabs );
		
		// Create the menu bar
		JMenuBar appletMenuBar = new JMenuBar();
		JMenu uploadMenu = new JMenu(Messages.getString("Upload")); //$NON-NLS-1$
		appletMenuBar.add(uploadMenu);
		JMenuItem uploadPerformerItem = new JMenuItem(TableNamesInstance.PERFORMER.getLabel()); //$NON-NLS-1$
		uploadMenu.add(uploadPerformerItem);
		uploadPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.PERFORMER);
			}
		});
		JMenuItem uploadSessionItem = new JMenuItem(TableNamesInstance.SESSION.getLabel()); //$NON-NLS-1$
		uploadMenu.add(uploadSessionItem);
		uploadSessionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.SESSION);
			}
		});
		JMenuItem uploadTrialItem = new JMenuItem(TableNamesInstance.TRIAL.getLabel()); //$NON-NLS-1$
		uploadMenu.add(uploadTrialItem);
		uploadTrialItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create the upload dialog
				rightPanel.showUploadDialog(TableNamesInstance.TRIAL);
			}
		});
		
		JMenu performerMenu = new JMenu(TableNamesInstance.PERFORMER.getLabel()); //$NON-NLS-1$
		appletMenuBar.add(performerMenu);
		JMenuItem createPerformerItem = new JMenuItem(Messages.getString("MotionApplet.New_performer")); //$NON-NLS-1$
		performerMenu.add(createPerformerItem);
		createPerformerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showPerformerDialog();
			}
		});
		
		JMenu viewMenu = new JMenu("View");
		appletMenuBar.add(viewMenu);
		
		JMenuItem viewAllPerformersItem = new JMenuItem("All performers");
		viewMenu.add(viewAllPerformersItem);
		viewAllPerformersItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(TableNamesInstance.PERFORMER);
			}
		});
		
		JMenuItem viewAllSessionsItem = new JMenuItem("All sessions");
		viewMenu.add(viewAllSessionsItem);
		viewAllSessionsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.showTable(TableNamesInstance.SESSION);
			}
		});
		
		this.setJMenuBar(appletMenuBar);

		// window closing
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MotionAppletFrame.this.dispose();
			}
			
		});
	}
	
	public static void addResult(JTable resultTable) {
		queryResultsPane.addTab("query results", new JScrollPane(resultTable));
		queryResultsPane.setSelectedIndex(queryResultsPane.getTabCount()-1);
		final JPanel tabPanel = new JPanel();
		tabPanel.setOpaque(false);
		tabPanel.add(new JLabel("Results"));
		JButton closeButton = new JButton();
		closeButton.setContentAreaFilled(false);
		/*
		java.net.URL imageURL = ClassLoader.getSystemClassLoader().getResource("images/close.gif");
		if (imageURL != null)
			closeButton.setIcon(new ImageIcon( imageURL ));
		else
			DatabaseConnection.log.severe( "Cannot load resource images/close.gif" );
		*/
		closeButton.setIcon(new Icon() {
			@Override
			public int getIconHeight() {
				
				return 8;
			}
			
			@Override
			public int getIconWidth() {
				
				return 8;
			}
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.black);
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(x, y, x+getIconWidth(), y+getIconHeight());
				g2.drawLine(x, y+getIconHeight(), x+getIconWidth(), y);
			}
		});
		closeButton.setPreferredSize(new Dimension(16, 16));
		closeButton.setBorderPainted(true);
		
		tabPanel.add(closeButton);
		
		queryResultsPane.setTabComponentAt(queryResultsPane.getTabCount()-1, tabPanel);
		
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = queryResultsPane.indexOfTabComponent(tabPanel);
				queryResultsPane.removeTabAt(i);
			}
		});
	}
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
	
		    	// Set language
				//Messages.setLanguagePolish();
				Messages.setLanguageEnglish();

				// Login dialog
				LoginDialog loginDialog = new LoginDialog();
				loginDialog.setVisible(true);

				// Check if login was successful
				if (loginDialog.getResult() == LoginDialog.LOGIN_SUCCESSFUL) 
				{	
					MotionAppletFrame motionAppletFrame = new MotionAppletFrame();
					motionAppletFrame.setVisible(true);
				}
		    }
		});
	}
}
