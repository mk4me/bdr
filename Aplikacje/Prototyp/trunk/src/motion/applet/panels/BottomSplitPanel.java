package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import motion.applet.Messages;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.trees.ConfigurationTree;

public class BottomSplitPanel extends JPanel {
	private static final String BORDER_TITLE = Messages.getString("BottomSplitPanel.ViewConfiguration"); //$NON-NLS-1$
	private static final String APPLY_SELECTION = Messages.getString("BottomSplitPanel.ApplySelection"); //$NON-NLS-1$
	private ConfigurationTree performerTree;
	private ConfigurationTree sessionTree;
	private ConfigurationTree trialTree;
	
	public BottomSplitPanel() {
		super();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, BORDER_TITLE);
		this.setBorder(titledBorder);
		this.setLayout(new BorderLayout());
		
		// Configuration area
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BoxLayout(configurationPanel, BoxLayout.X_AXIS));
		
		configurationPanel.add(createTreePanel(TableNamesInstance.PERFORMER, performerTree));
		configurationPanel.add(createTreePanel(TableNamesInstance.SESSION, sessionTree));
		configurationPanel.add(createTreePanel(TableNamesInstance.TRIAL, trialTree));
		
		this.add(configurationPanel, BorderLayout.CENTER);
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		JButton applySelectionButton = new JButton(APPLY_SELECTION);
		buttonPanel.add(applySelectionButton);
		
		applySelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private JPanel createTreePanel(TableName tableName, ConfigurationTree configurationTree) {
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		configurationTree = new ConfigurationTree(tableName);
		JScrollPane performerScrollPane = new JScrollPane(configurationTree.tree);
		
		JLabel treeLabel = new JLabel(tableName.toString());
		
		treePanel.add(treeLabel, BorderLayout.NORTH);
		treePanel.add(performerScrollPane, BorderLayout.CENTER);
		
		return treePanel;
	}
}
