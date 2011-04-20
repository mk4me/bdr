package motion.applet.widgets;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import motion.applet.MotionApplet;

public class TagWidget extends JDialog {
	private JPanel formPanel;
	private JButton okButton;
	private ArrayList<CheckBoxGroup> checkBoxGroups;
	private String tags = "";
	
	public TagWidget() {
		super((JFrame) null, "Edit tags", true);
		//this.setSize(350, 350);
		this.setLocationRelativeTo(MotionApplet.contentPane);
		
		formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout());
		this.getContentPane().add(formPanel);
		
		
		JPanel tagPanel = new JPanel();
		tagPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		checkBoxGroups = new ArrayList<CheckBoxGroup>();
		checkBoxGroups.add(new CheckBoxGroup("emg", new String[]{"E1", "E2", "E3"}, gridBagConstraints, tagPanel));
		checkBoxGroups.add(new CheckBoxGroup("grf", new String[]{"G1", "G2", "G3", "G4"}, gridBagConstraints, tagPanel));
		checkBoxGroups.add(new CheckBoxGroup("mocap", new String[]{"M1", "M2"}, gridBagConstraints, tagPanel));
		checkBoxGroups.add(new CheckBoxGroup("video", new String[]{"V1", "V2"}, gridBagConstraints, tagPanel));
		
		formPanel.add(tagPanel, BorderLayout.CENTER);
		
		
		JPanel buttonPanel = new JPanel();
		okButton = new JButton("OK");
		buttonPanel.add(okButton);
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTags();
				TagWidget.this.setVisible(false);
				TagWidget.this.dispose();
			}
		});
		
		this.pack();
	}
	
	private void buildTags() {
		boolean first = true;
		for (CheckBoxGroup c : checkBoxGroups) {
			String someTags = c.getTags();
			if (!someTags.equals("")) {
				if (first) {
					tags += c.getTags();
					first = false;
				} else {
					tags += ", " + c.getTags();
				}
			}
		}
	}
	
	public String getTags() {
		
		return tags;
	}
	
	private class CheckBoxGroup {
		private JCheckBox checkBox;
		private ArrayList<JRadioButton> radioButtons;
		private String checkBoxName;
		
		public CheckBoxGroup(String checkBoxName, String[] radioButtonNames, GridBagConstraints gridBagConstraints, JPanel tagPanel) {
			this.checkBoxName = checkBoxName;
			
			ButtonGroup radioButtonGroup = new ButtonGroup();
			
			checkBox = new JCheckBox(checkBoxName);
			tagPanel.add(checkBox, gridBagConstraints);
			gridBagConstraints.gridx++;
			
			radioButtons = new ArrayList<JRadioButton>();
			for (int i = 0; i < radioButtonNames.length; i++) {
				JRadioButton radioButton = new JRadioButton(radioButtonNames[i]);
				radioButtons.add(radioButton);
				radioButtonGroup.add(radioButton);
				tagPanel.add(radioButton, gridBagConstraints);
				gridBagConstraints.gridx++;
			}
			gridBagConstraints.gridy++;
			gridBagConstraints.gridx = 0;
		}
		
		public String getTags() {
			String tags = "";
			if (checkBox.isSelected()) {
				for (JRadioButton r : radioButtons) {
					if (r.isSelected()) {
						tags += checkBoxName;
						tags += "(" + r.getText() + ")";
						break;
					}
				}
			}
			
			return tags;
		}
	}
}
