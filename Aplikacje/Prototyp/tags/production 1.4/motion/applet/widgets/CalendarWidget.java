package motion.applet.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendar;

public class CalendarWidget extends JDialog {
	private JPanel formPanel;
	private JButton setButton;
	private Date date;
	
	public CalendarWidget() {
		super((JFrame) null, "Calendar", true);
		this.setSize(350, 350);
		this.setLocation(200, 200);
		
		formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout());
		this.getContentPane().add(formPanel);
		
		JCalendar calendar = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, true);
		formPanel.add(calendar, BorderLayout.CENTER);
		
		CalendarListener calendarListener = new CalendarListener();
		calendar.addDateListener(calendarListener);
		
		JPanel buttonPanel = new JPanel();
		setButton = new JButton("Set");
		buttonPanel.add(setButton);
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarWidget.this.setVisible(false);
				CalendarWidget.this.dispose();
			}
		});
	}
	
	public Date getDate() {
		
		return date;
	}
	
	private class CalendarListener implements DateListener {

		@Override
		public void dateChanged(DateEvent e) {
			Calendar calendar = e.getSelectedDate();
			if (calendar != null) {
				date = calendar.getTime();
			}
		}
	}
}