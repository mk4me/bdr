package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
	
	public BottomSplitPanel() {
		super();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, BORDER_TITLE);
		this.setBorder(titledBorder);
		this.setLayout(new BorderLayout());
		
		// Configuration area
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BoxLayout(configurationPanel, BoxLayout.X_AXIS));
		
		configurationPanel.add(createTreePanel(TableNamesInstance.PERFORMER));
		configurationPanel.add(createTreePanel(TableNamesInstance.SESSION));
		configurationPanel.add(createTreePanel(TableNamesInstance.TRIAL));
		
		this.add(configurationPanel, BorderLayout.CENTER);
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		JButton applySelectionButton = new JButton(APPLY_SELECTION);
		buttonPanel.add(applySelectionButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private JPanel createTreePanel(TableName tableName) {
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		ConfigurationTree performerTree = new ConfigurationTree(tableName);
		JScrollPane performerScrollPane = new JScrollPane(performerTree.tree);
		
		JLabel treeLabel = new JLabel(tableName.toString());
		
		treePanel.add(treeLabel, BorderLayout.NORTH);
		treePanel.add(performerScrollPane, BorderLayout.CENTER);
		
		return treePanel;
	}
}
