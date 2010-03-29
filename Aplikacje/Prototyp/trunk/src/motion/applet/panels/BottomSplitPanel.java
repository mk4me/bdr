package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import motion.applet.trees.ConfigurationTree;

public class BottomSplitPanel extends JPanel {
	private static final String BORDER_TITLE = "View configuration";
	private static final String APPLY_SELECTION = "Apply selection";
	
	public BottomSplitPanel() {
		super();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, BORDER_TITLE);
		this.setBorder(titledBorder);
		this.setLayout(new BorderLayout());
		
		// Configuration area
		//JPanel configurationPanel = new JPanel();
		ConfigurationTree configurationTree = new ConfigurationTree();
		JScrollPane scrollPane = new JScrollPane(configurationTree.tree);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		JButton applySelectionButton = new JButton(APPLY_SELECTION);
		buttonPanel.add(applySelectionButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}

}
