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

public class MeasurementConfigurationMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_MEASUREMENTS = "View measurements";
	
	private RightSplitPanel rightPanel;
	
	public MeasurementConfigurationMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			// View Sessions context menu
			JMenuItem viewMeasurementsMenuItem = new JMenuItem(MENU_VIEW_MEASUREMENTS);
			popupMenu.add(viewMeasurementsMenuItem);
			
			viewMeasurementsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewMeasurements(recordId);
				}
			});
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewMeasurements(recordId);
		}
	}
	
	private void viewMeasurements(int recordId) {
		rightPanel.showTable(EntityKind.measurement, recordId, EntityKind.measurement_conf);
		MotionApplet.setBrowsePanelVisible();
	}
}