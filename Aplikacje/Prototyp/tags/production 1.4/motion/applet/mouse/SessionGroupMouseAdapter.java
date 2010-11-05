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

public class SessionGroupMouseAdapter extends MouseAdapter {
	private static String MENU_VIEW_SESSIONS = "View sessions";
	
	private RightSplitPanel rightPanel;
	
	public SessionGroupMouseAdapter(RightSplitPanel rightPanel) {
		super();
		
		this.rightPanel = rightPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		final int recordId = rightPanel.getSelectedRecord((JTable) e.getSource(), e);
		if (SwingUtilities.isRightMouseButton(e)) {	// Right click.
			JPopupMenu popupMenu = new JPopupMenu();
			
			// View Sessions context menu
			JMenuItem viewSessionsMenuItem = new JMenuItem(MENU_VIEW_SESSIONS);
			popupMenu.add(viewSessionsMenuItem);
			
			viewSessionsMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewSessions(recordId);
				}
			});
			
			popupMenu.show((JTable) e.getSource(), e.getPoint().x, e.getPoint().y);
		} else if (e.getClickCount() == 2) {	// Double click.
			viewSessions(recordId);
		}
	}
	
	private void viewSessions(int recordId) {
		rightPanel.showTable(EntityKind.session, recordId, EntityKind.sessionGroup);
		MotionApplet.setBrowsePanelVisible();
	}
}
