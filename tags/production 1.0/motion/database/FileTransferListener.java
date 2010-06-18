package motion.database;

public interface FileTransferListener {

		/**
		 * Ta metoda bêdzie wo³ana co X bajtów transferu. Przy downloadzie.
		 * Aktualnie X wynosi 4096
		 */
		public void transferStep();
		
		/**
		 * Ta metoda bêdzie wo³ana co getDesiredStepPercent()
		 * 
		 * @param percent aktualny stan uploadu w procentach
		 */
		public void transferStepPercent(int percent);
		
		/**
		 * Ta metoda ustala co ile procent postêpu ma byæ wo³ana metoda transferStepPercent.
		 * 
		 * @return wartoœæ w procentach (np 5)
		 */
		public int getDesiredStepPercent();
}
