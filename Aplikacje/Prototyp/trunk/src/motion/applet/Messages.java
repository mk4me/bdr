package motion.applet;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	public static String ENGLISH = "ENG";
	public static String POLISH = "PL";
	private static String currentLanguage = ENGLISH;
	
	private static final String BUNDLE_NAME = "motion.applet.messages"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	// Symbols
	public static final String COLON = ":"; //$NON-NLS-1$
	public static final String ADD_SIGN = "+"; //$NON-NLS-1$
	public static final String REMOVE_SIGN = "X"; //$NON-NLS-1$
	
	// Button labels
	public static final String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	public static final String ADD = Messages.getString("Add"); //$NON-NLS-1$
	public static final String EDIT = Messages.getString("Edit"); //$NON-NLS-1$
	public static final String CREATE = Messages.getString("Create"); //$NON-NLS-1$
	
	private Messages() {
	}
	
	public static String getString(String key) {
		try {
			if (currentLanguage.equals(ENGLISH)) {
				
				return RESOURCE_BUNDLE.getString(key);
			} else if (currentLanguage.equals(POLISH)) {
				
				return RESOURCE_BUNDLE.getString(key + "_" + POLISH);
			} else {	// Unknown language. Default to English.
				
				return RESOURCE_BUNDLE.getString(key);
			}
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void setLanguageEnglish() {
		currentLanguage = ENGLISH;
	}
	
	public static void setLanguagePolish() {
		currentLanguage = POLISH;
	}
	
	public static String getLanguage() {
		
		return currentLanguage;
	}
}
