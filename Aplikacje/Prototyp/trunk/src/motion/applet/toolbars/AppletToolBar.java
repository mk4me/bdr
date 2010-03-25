package motion.applet.toolbars;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

import motion.applet.MotionApplet;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;

public class AppletToolBar extends JToolBar {
	private TableName[] tableNames = {TableNamesInstance.PERFORMER,
			TableNamesInstance.SESSION,
			TableNamesInstance.TRIAL,
			TableNamesInstance.PATIENT};
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
