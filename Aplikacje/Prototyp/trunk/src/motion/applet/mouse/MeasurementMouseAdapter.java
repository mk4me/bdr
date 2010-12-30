package motion.applet.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import motion.applet.MotionApplet;
import motion.applet.panels.RightSplitPanel;
import motion.database.model.EntityKind;

public class MeasurementMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_MEASUREMENT_CONFIGURATIONS = "View measurement configurations";
	
	private RightSplitPanel rightPanel;
	
	public MeasurementMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			// View Sessions context menu
			JMenuItem viewMeasurementConfigurationsMenuItem = new JMenuItem(MENU_VIEW_MEASUREMENT_CONFIGURATIONS);
			popupMenu.add(viewMeasurementConfigurationsMenuItem);
			
			viewMeasurementConfigurationsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewMeasurementConfigurations(recordId);
				}
			});
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewMeasurementConfigurations(recordId);
		}
	}
	
	private void viewMeasurementConfigurations(int recordId) {
		rightPanel.showTable(EntityKind.measurement_conf, recordId);
		MotionApplet.setBrowsePanelVisible();
	}
}
