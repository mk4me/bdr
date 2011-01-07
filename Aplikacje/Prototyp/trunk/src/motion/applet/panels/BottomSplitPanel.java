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
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.trees.CheckBoxNode;
import motion.applet.trees.ConfigurationTree;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityKind;

public class BottomSplitPanel extends JPanel {
	private static final String BORDER_TITLE = Messages.getString("BottomSplitPanel.ViewConfiguration"); //$NON-NLS-1$
	private static final String APPLY_SELECTION = Messages.getString("BottomSplitPanel.ApplySelection"); //$NON-NLS-1$
	private static ConfigurationTree performerTree;
	private static ConfigurationTree sessionTree;
	private static ConfigurationTree sessionGroupTree;
	private static ConfigurationTree trialTree;
	private static ConfigurationTree fileTree;
	private static ConfigurationTree measurementConfigurationTree;
	private static ConfigurationTree measurementTree;
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
		
/*		try {
			WebServiceInstance.getDatabaseConnection().readAttributeViewConfiguration();
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
		}
*/		
		performerTree = createConfigurationTree(EntityKind.performer, configurationPanel);
		sessionTree = createConfigurationTree(EntityKind.session, configurationPanel);
		sessionGroupTree = createConfigurationTree(EntityKind.sessionGroup, configurationPanel);
		trialTree = createConfigurationTree(EntityKind.trial, configurationPanel);
		fileTree = createConfigurationTree(EntityKind.file, configurationPanel);
		measurementConfigurationTree = createConfigurationTree(EntityKind.measurement_conf, configurationPanel);
		measurementTree = createConfigurationTree(EntityKind.measurement, configurationPanel);
		
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
	
	private ConfigurationTree createConfigurationTree(EntityKind entityKind, JPanel panel) {
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		ConfigurationTree configurationTree = new ConfigurationTree(entityKind);
		JScrollPane performerScrollPane = new JScrollPane(configurationTree.tree);
		JLabel treeLabel = new JLabel(entityKind.getGUIName());
		treePanel.add(treeLabel, BorderLayout.NORTH);
		treePanel.add(performerScrollPane, BorderLayout.CENTER);
		panel.add(treePanel);
		
		return configurationTree;
	}
	
	public void refreshConfigurationTrees() {
		performerTree.getTreeContents();
		sessionTree.getTreeContents();
		sessionGroupTree.getTreeContents();
		trialTree.getTreeContents();
		fileTree.getTreeContents();
		measurementConfigurationTree.getTreeContents();
		measurementTree.getTreeContents();
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
	
	public static ArrayList<String> getCheckedSessionGroupAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) sessionGroupTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedTrialAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) trialTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedFileAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedMeasurementConfigurationAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) measurementConfigurationTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedMeasurementAttributes() {
		ArrayList<String> checkedItems = new ArrayList<String>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) measurementTree.tree.getModel().getRoot();
		getCheckedItems(node, checkedItems);
		
		return checkedItems;
	}
	
	public static ArrayList<String> getCheckedAttributes(EntityKind entityKind) {
		if (entityKind.equals(EntityKind.performer)) {
			return getCheckedPerformerAttributes();
		} else if (entityKind.equals(EntityKind.session)) {
			return getCheckedSessionAttributes();
		} else if (entityKind.equals(EntityKind.sessionGroup)) {
			return getCheckedSessionGroupAttributes();
		} else if (entityKind.equals(EntityKind.trial)) {
			return getCheckedTrialAttributes();
		} else if (entityKind.equals(EntityKind.file)) {
			return getCheckedFileAttributes();
		} else if (entityKind.equals(EntityKind.measurement_conf)) {
			return getCheckedMeasurementConfigurationAttributes();
		} else if (entityKind.equals(EntityKind.measurement)) {
			return getCheckedMeasurementAttributes();
		}
		
		return null;
	}
	
	public static boolean isCheckedAttribute(EntityKind entityKind, String attribute) {
		if (entityKind.equals(EntityKind.performer)) {
			return getCheckedPerformerAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.session)) {
			return getCheckedSessionAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.sessionGroup)) {
			return getCheckedSessionGroupAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.trial)) {
			return getCheckedTrialAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.file)) {
			return getCheckedFileAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.measurement_conf)) {
			return getCheckedMeasurementConfigurationAttributes().contains(attribute);
		} else if (entityKind.equals(EntityKind.measurement)) {
			return getCheckedMeasurementAttributes().contains(attribute);
		}
		
		return false;
	}
	
	public void addApplyButtonListener(ActionListener actionListener) {
		this.applySelectionButton.addActionListener(actionListener);
	}
}
