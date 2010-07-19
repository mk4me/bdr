package motion.applet.toolbars;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

import motion.applet.MotionAppletFrame;

public class AppletToolBar extends JToolBar {
	private String[] labs = {"Lab 0", "Lab 1"};
	private JComboBox labComboBox = new JComboBox(labs);
	private static int labId = 1;
	
	public AppletToolBar() {
		super(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		
		FlowLayout appletToolBarLayout = new FlowLayout();
		appletToolBarLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(appletToolBarLayout);
		
		labComboBox.setSelectedIndex(labId);
		this.add(labComboBox);
	}
	
	public void addLabComboBoxListener(ActionListener actionListener) {
		this.labComboBox.addActionListener(actionListener);
	}
	
	public static void setLabId(int id) {
		labId = id;
	}
	
	public static int getLabId() {
		
		return labId;
	}
}
