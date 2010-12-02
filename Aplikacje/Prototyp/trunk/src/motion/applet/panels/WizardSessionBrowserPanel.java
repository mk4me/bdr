package motion.applet.panels;

import java.awt.BorderLayout;

import motion.database.model.Session;

public class WizardSessionBrowserPanel extends WizardPanel {
	private SessionBrowserPanel sessionBrowserPanel;
	
	public WizardSessionBrowserPanel(String stepMessage,
			boolean enableCancel, boolean enableBack, boolean enableNext, boolean enableFinish) {
		super(stepMessage, enableCancel, enableBack, enableNext, enableFinish);
		
	}
	
	@Override
	protected void createWizardContents() {
		this.setLayout(new BorderLayout());
	}
	
	@Override
	public void afterNextPressed(WizardPanel wizardPanel) {
		if (wizardPanel instanceof WizardSessionDirectoryPanel) {
			Session session = ((WizardSessionDirectoryPanel) wizardPanel).getSession();
			try {
				sessionBrowserPanel = new SessionBrowserPanel();
				sessionBrowserPanel.setSession(new Session[] {session});
				this.add(sessionBrowserPanel, BorderLayout.CENTER);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}