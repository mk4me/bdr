package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import motion.Messages;
import motion.database.DatabaseConnection;

public class ExceptionDialog extends BasicDialog {
	private static String TITLE = "Motion client";
	private static String REPORT_MESSAGE = "Please report this occurrence.";
	private static String CLOSE = Messages.getString("Close"); //$NON-NLS-1$
	private static String COPY = "Copy to clipboard";
	private Exception exception;
	private String exceptionText;
	private JTextArea exceptionTextArea;
	private JButton closeButton;
	private JButton copyButton;
	
	private Logger logger = DatabaseConnection.getLogger();
	
	public ExceptionDialog(Exception exception) {
		super(TITLE, REPORT_MESSAGE);
		this.exception = exception;
		this.exceptionText = getExceptionText();
		
		logger.severe(exceptionText);
		
		this.finishUserInterface();
	}
	
	public ExceptionDialog(String text, String message) {
		super(TITLE, message);
		this.exceptionText = text;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Exception area
		exceptionTextArea = new JTextArea();
		exceptionTextArea.setBackground(Color.lightGray);
		exceptionTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(exceptionTextArea);
		this.add(scrollPane, BorderLayout.CENTER);
		
		// Button area
		copyButton = new JButton(COPY);
		this.addToButtonPanel(copyButton);
		
		closeButton = new JButton(CLOSE);
		this.addToButtonPanel(closeButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(400, 250);
		this.setModal(false);
		this.exceptionTextArea.setText(exceptionText);
	}
	
	@Override
	protected void addListeners() {
		this.closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExceptionDialog.this.setVisible(false);
				ExceptionDialog.this.dispose();
			}
		});
		
		this.copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(ExceptionDialog.this.exceptionText), null);
			}
		});
	}
	
	private String getExceptionText() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		this.exception.printStackTrace(printWriter);
		printWriter.flush();
		stringWriter.flush();
		
		return stringWriter.toString();
	}
}
