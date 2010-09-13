package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import motion.Messages;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.trees.CheckBoxNode;
import motion.applet.trees.ConfigurationTree;

public class BottomSplitPanel extends JPanel {
	private static final String BORDER_TITLE = Messages.getString("BottomSplitPanel.ViewConfiguration"); //$NON-NLS-1$
	private static final String APPLY_SELECTION = Messages.getString("BottomSplitPanel.ApplySelection"); //$NON-NLS-1$
	private static ConfigurationTree performerTree;
	private static ConfigurationTree sessionTree;
	private static ConfigurationTree trialTree;
	private JButton applySelectionButton;
	
	public BottomSplitPanel() {
		super();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, BORDER_TITLE);
		this.setBorder(titledBorder);
		this.setLayout(new BorderLayout());
		
		// Configuration area
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BoxLayout(configurationPanel, BoxLayout.X_AXIS));
		
		performerTree = createConfigurationTree(TableNamesInstance.PERFORMER, configurationPanel);
		sessionTree = createConfigurationTree(TableNamesInstance.SESSION, configurationPanel);
		trialTree = createConfigurationTree(TableNamesInstance.TRIAL, configurationPanel);
		
		this.add(configurationPanel, BorderLayout.CENTER);
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		applySelectionButton = new JButton(APPLY_SELECTION);
		buttonPanel.add(applySelectionButton);
		
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private ConfigurationTree createConfigurationTree(TableName tableName, JPanel panel) {
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		ConfigurationTree configurationTree = new ConfigurationTree(tableName.toEntityKind());
		JScrollPane performerScrollPane = new JScrollPane(configurationTree.tree);
		JLabel treeLabel = new JLabel(tableName.getLabel());
		treePanel.add(treeLabel, BorderLayout.NORTH);
		treePanel.add(performerScrollPane, BorderLayout.CENTER);
		panel.add(treePanel);
		
		return configurationTree;
	}
	
	private static void getCheckedItems(DefaultMutableTreeNode node, ArrayList<String> checkedItems) {
		Enumeration children = node.children();
		while(children.hasMoreElements()) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();
			if (currentNode.isLeaf()) {
				Object userObject = currentNode.getUserObject();
				if (userObject instanceof CheckBoxNode) {
					CheckBoxNode checkBoxNode = (CheckBoxNode) userObject;
					if (checkBoxNode.isSelected()) {
						checkedItems.add(checkBoxNode.getText());
					}
				}
			} else {
				getCheckedItems(currentNode, checkedItems);
			}
		}
	}
	
	public static ArrayList<String> getCheckedPerformerAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) performerTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedSessionAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) sessionTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedTrialAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) trialTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedAttributes(TableName tableName) {
		if (tableName.equals(TableNamesInstance.PERFORMER)) {
			return getCheckedPerformerAttributes();
		} else if (tableName.equals(TableNamesInstance.SESSION)) {
			return getCheckedSessionAttributes();
		} else if (tableName.equals(TableNamesInstance.TRIAL)) {
			return getCheckedTrialAttributes();
		}
		
		return null;
	}
	
	public static boolean isCheckedAttribute(TableName tableName, String attribute) {
		if (tableName.equals(TableNamesInstance.PERFORMER)) {
			return getCheckedPerformerAttributes().contains(attribute);
		} else if (tableName.equals(TableNamesInstance.SESSION)) {
			return getCheckedSessionAttributes().contains(attribute);
		} else if (tableName.equals(TableNamesInstance.TRIAL)) {
			return getCheckedTrialAttributes().contains(attribute);
		}
		
		return false;
	}
	
	public void addApplyButtonListener(ActionListener actionListener) {
		this.applySelectionButton.addActionListener(actionListener);
	}
}
