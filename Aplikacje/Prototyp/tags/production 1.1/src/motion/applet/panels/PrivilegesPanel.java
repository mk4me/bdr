package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import motion.database.model.Privileges;

public class PrivilegesPanel extends JPanel {

	String[] buttonLabels = { "private", 
							  "public for reading", 
							  "public for writing",
							  "custom"};
	String[] buttonExplanations = { "(Only you can read or write.)",
									"(Only you can write. All can read.)",
									"(All can read and write.)",
									"(Customized for selected users)"};
	
	Privileges[] values = { Privileges.PRIVATE, 
							Privileges.PUBLIC_READ,
							Privileges.PUBLIC_WRITE,
							Privileges.CUSTOM };
	
	
	JRadioButton[] buttons;
	ButtonGroup privilegesGroup; 
	
	public PrivilegesPanel()
	{
		super();
		JPanel centerPanel = new JPanel();
		setLayout( new BorderLayout() );
		add( centerPanel, BorderLayout.CENTER );
		centerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		JLabel loginLabel = new JLabel("Choose privileges:");
		gridBagConstraints.ipady = 20;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(loginLabel, gridBagConstraints);

		gridBagConstraints.ipady = 0;

		privilegesGroup = new ButtonGroup();
		buttons = new JRadioButton[ buttonLabels.length ];

		for (int i=0; i<buttons.length; i++)
		{
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = i+1;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			buttons[i] = new JRadioButton( buttonLabels[i] );
			buttons[i].setHorizontalTextPosition( SwingConstants.RIGHT );
			privilegesGroup.add( buttons[i] );
			centerPanel.add(buttons[i], gridBagConstraints);
			gridBagConstraints.gridx = 1;
			centerPanel.add(new JLabel( buttonExplanations[i] ), gridBagConstraints );
		}
		buttons[0].setSelected( true );
		buttons[3].setEnabled( false );
		
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.setBorder(border);
		
    	Dimension marginDimension = new Dimension();
		marginDimension.width = 20;
		JPanel marginLeft = new JPanel();
		marginLeft.setPreferredSize( marginDimension );
		add( marginLeft, BorderLayout.WEST );
		JPanel marginRight = new JPanel();
		marginRight.setPreferredSize( marginDimension );
		add( marginRight, BorderLayout.EAST );

	}
	
	
	public Privileges getResult()
	{
		for (int i=0; i<buttons.length; i++)
			if (buttons[i].isSelected())
				return values[i];
		return values[0];
	}
	
	
	public static void main(String [] args)
	{
		JFrame b = new JFrame();
		b.setLayout( new BorderLayout() );
		b.add( new PrivilegesPanel(), BorderLayout.SOUTH );
		
		b.setSize( 500, 500 );
		b.setVisible( true );
	}
}
