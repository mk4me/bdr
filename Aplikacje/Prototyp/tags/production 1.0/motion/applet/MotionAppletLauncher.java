package motion.applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import motion.applet.dialogs.LoginDialog;

public class MotionAppletLauncher extends JApplet {
	
	@Override
	public void init() {
		this.setSize(150, 80);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.white);
		JLabel titleLabel = new JLabel("<html><p align=center>Motion client<br>(click button to start)</p></html>");
		titlePanel.add(titleLabel);
		this.getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		JButton launcherButton = new JButton("Start applet");
		buttonPanel.add(launcherButton);
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		
		launcherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MotionAppletFrame.main(new String[0]);
			}
		});
	}
}
