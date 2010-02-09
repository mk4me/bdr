package motion.applet.toolbars;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import motion.applet.MotionApplet;

public class AppletToolBar extends JToolBar {
	private String[] tableNames = {"Performer", "Sesja", "Obserwacja", "Pacjent"};
	private JComboBox tableComboBox = new JComboBox(tableNames);
	
	public AppletToolBar() {
		super(MotionApplet.APPLET_NAME, JToolBar.HORIZONTAL);
		
		FlowLayout appletToolBarLayout = new FlowLayout();
		appletToolBarLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(appletToolBarLayout);
		//JButton showTableButton = new JButton("Show");
		//this.add(showTableButton);
		this.add(tableComboBox);
	}
	
	public void addTableComboBoxListener(ActionListener actionListener) {
		this.tableComboBox.addActionListener(actionListener);
	}
}
