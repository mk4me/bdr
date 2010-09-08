package motion.applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import motion.Messages;

public class MotionAppletLauncher extends JApplet {
	private JComboBox languageComboBox = new JComboBox();
	private static String ENGLISH = "English";
	private static String POLISH = "Polish";
	
	@Override
	public void init() {
		this.setSize(230, 80);
		
		// Set language
		//Messages.setLanguagePolish();
		Messages.setLanguageEnglish();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.white);
		JLabel titleLabel = new JLabel("<html><p align=center>Motion client<br>(click button to start, select language)</p></html>");
		titlePanel.add(titleLabel);
		this.getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		JButton launcherButton = new JButton("Start applet");
		buttonPanel.add(launcherButton);
		
		languageComboBox.addItem(ENGLISH);
		languageComboBox.addItem(POLISH);
		buttonPanel.add(languageComboBox);
		
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		
		launcherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MotionAppletFrame.main(new String[0]);
			}
		});
		
		languageComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (languageComboBox.getSelectedItem().equals(ENGLISH)) {
					Messages.setLanguageEnglish();
				} else if (languageComboBox.getSelectedItem().equals(POLISH)) {
					Messages.setLanguagePolish();
				}
			}
		});
	}
}
