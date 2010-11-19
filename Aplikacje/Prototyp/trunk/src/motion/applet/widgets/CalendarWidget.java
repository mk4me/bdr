package motion.applet.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import motion.applet.MotionApplet;

import org.freixas.jcalendar.JCalendar;

public class CalendarWidget extends JDialog {
	private JPanel formPanel;
	private JButton setButton;
	private JCalendar calendar;
	private Date date;
	
	public CalendarWidget() {
		super((JFrame) null, "Calendar", true);
		this.setSize(350, 350);
		this.setLocationRelativeTo(MotionApplet.contentPane);
		
		formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout());
		this.getContentPane().add(formPanel);
		
		calendar = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, true);
		formPanel.add(calendar, BorderLayout.CENTER);
		
		/*
		CalendarListener calendarListener = new CalendarListener();
		calendar.addDateListener(calendarListener);
		*/
		
		JPanel buttonPanel = new JPanel();
		setButton = new JButton("Set");
		buttonPanel.add(setButton);
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = calendar.getDate();
				CalendarWidget.this.setVisible(false);
				CalendarWidget.this.dispose();
			}
		});
	}
	
	public void setDate(Date date) {
		this.date = date;
		calendar.setDate(date);
	}
	
	public Date getDate() {
		
		return date;
	}
	/*
	private class CalendarListener implements DateListener {

		@Override
		public void dateChanged(DateEvent e) {
			Calendar calendar = e.getSelectedDate();
			if (calendar != null) {
				date = calendar.getTime();
			}
		}
	}*/
}