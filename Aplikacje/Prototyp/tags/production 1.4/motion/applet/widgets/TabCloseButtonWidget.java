package motion.applet.widgets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabCloseButtonWidget extends JPanel {
	private JTabbedPane tabbedPane;
	public static String RESULTS_TAB_LABEL = "Results";
	
	public TabCloseButtonWidget(String tabLabel, JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		
		setOpaque(false);
		add(new JLabel(tabLabel));
		JButton closeButton = new JButton();
		closeButton.setContentAreaFilled(false);
		closeButton.setIcon(new Icon() {
			@Override
			public int getIconHeight() {
				
				return 8;
			}
			
			@Override
			public int getIconWidth() {
				
				return 8;
			}
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.black);
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(x, y, x+getIconWidth(), y+getIconHeight());
				g2.drawLine(x, y+getIconHeight(), x+getIconWidth(), y);
			}
		});
		closeButton.setPreferredSize(new Dimension(16, 16));
		closeButton.setBorderPainted(true);
		
		add(closeButton);
		
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = TabCloseButtonWidget.this.tabbedPane.indexOfTabComponent(TabCloseButtonWidget.this);
				TabCloseButtonWidget.this.tabbedPane.removeTabAt(i);
			}
		});
	}
	
	public void addCloseButton() {
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, this);
	}
}