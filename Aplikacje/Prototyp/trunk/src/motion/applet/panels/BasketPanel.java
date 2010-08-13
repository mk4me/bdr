package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import motion.applet.MotionAppletFrame;
import motion.applet.dialogs.BasketDialog;

public class BasketPanel extends JPanel {
	private JTree tree;
	
	public BasketPanel() {
		super();
		this.setLayout(new BorderLayout());
		tree = new JTree(createTree());
		this.add(new JScrollPane(tree), BorderLayout.CENTER);
		
		// Create the tool bar
		JToolBar toolBar = new JToolBar(MotionAppletFrame.APPLET_NAME, JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		JButton addButton = new JButton("Add");
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasketDialog basketDialog = new BasketDialog();
				basketDialog.setVisible(true);
			}
		});
		
		JButton removeButton = new JButton("Remove");
		toolBar.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.add(toolBar, BorderLayout.NORTH);
	}
	
	private DefaultMutableTreeNode createTree() {
		ArrayList<String> basketList = new ArrayList<String>();
		basketList.add("Basket_1");
		basketList.add("Basket_2");
		basketList.add("Basket_3");
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Baskets");
		for (String s : basketList) {
			DefaultMutableTreeNode basket = new DefaultMutableTreeNode(s);
			basket.add(new DefaultMutableTreeNode("Performer"));
			basket.add(new DefaultMutableTreeNode("Session"));
			basket.add(new DefaultMutableTreeNode("Trial"));
			root.add(basket);
		}
		
		return root;
	}
}