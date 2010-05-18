package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;

import motion.database.TextMessageListener;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements TextMessageListener{
    
    private Container container;
    private JLabel label;

	public StatusBar(Container container) {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        label = new JLabel();
        this.setLayout( new BorderLayout() );
        this.add(label, BorderLayout.WEST );
        setMessage("Ready");
        this.container = container;
    }
    
    public void setMessage(String message) {
        invalidate();
        System.err.println("Status bar --" + message);
        label.setText(" "+message);
        label.repaint();
        if (container!=null)
        	container.invalidate();
        repaint();
        this.updateUI();
    }        
}