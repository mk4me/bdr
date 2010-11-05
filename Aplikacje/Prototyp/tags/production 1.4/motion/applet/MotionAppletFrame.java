package motion.applet;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;

import motion.Messages;

@SuppressWarnings("serial")
public class MotionAppletFrame extends JFrame {

	public static String APPLET_NAME = Messages.getString("MotionApplet.AppletName"); //$NON-NLS-1$
	public static int APPLET_HEIGHT = 600;
	public static int APPLET_WIDTH = 800;
	
	public MotionAppletFrame() {
		this.setSize(APPLET_WIDTH, APPLET_HEIGHT);
		this.setTitle(APPLET_NAME);
		
		// window closing
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MotionAppletFrame.this.dispose();
			}
		});
	}

	public static void main(String args[]) {

		if (args.length == 0)
			args = new String[]{MotionApplet.LANGUAGE_ENGLISH};
		JFrame frame = new JFrame();
		frame.setTitle(APPLET_NAME);
		JApplet motionApplet = new MotionApplet(args[0]);
		frame.add( motionApplet );
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				((Window) e.getSource()).dispose();
				System.exit(0);
			}
		});
	
		frame.setVisible(true);
		frame.setSize( APPLET_WIDTH, APPLET_HEIGHT );
		motionApplet.init();
	}
}
