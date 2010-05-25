package motion.applet.toolbars;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

import motion.applet.MotionAppletFrame;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;

public class AppletToolBar extends JToolBar {
	private TableName[] tableNames = {TableNamesInstance.PERFORMER,
			TableNamesInstance.SESSION};
			//TableNamesInstance.TRIAL,
			//TableNamesInstance.PATIENT};
	private JComboBox tableComboBox = new JComboBox(tableNames);
	private String[] labs = {"Lab 0", "Lab 1"};
	private JComboBox labComboBox = new JComboBox(labs);
	private static int labId = 1;
	
	public AppletToolBar() {
		super(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		
		FlowLayout appletToolBarLayout = new FlowLayout();
		appletToolBarLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(appletToolBarLayout);
		//JButton showTableButton = new JButton("Show");
		//this.add(showTableButton);
		this.add(tableComboBox);
		
		labComboBox.setSelectedIndex(labId);
		this.add(labComboBox);
		
		labComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labId = AppletToolBar.this.labComboBox.getSelectedIndex();
				AppletToolBar.this.tableComboBox.setSelectedIndex(AppletToolBar.this.tableComboBox.getSelectedIndex());
			}
		});
	}
	
	public void addTableComboBoxListener(ActionListener actionListener) {
		this.tableComboBox.addActionListener(actionListener);
	}
	
	public static int getLabId() {
		
		return labId;
	}
}
