package motion.database;

public interface FileTransferListener {

		/**
		 * Ta metoda b�dzie wo�ana co X bajt�w transferu. Przy downloadzie.
		 * Aktualnie X wynosi 4096
		 */
		public void transferStep();
		
		/**
		 * Ta metoda b�dzie wo�ana co getDesiredStepPercent()
		 * 
		 * @param percent aktualny stan uploadu w procentach
		 */
		public void transferStepPercent(int percent);
		
		/**
		 * Ta metoda ustala co ile procent post�pu ma by� wo�ana metoda transferStepPercent.
		 * 
		 * @return warto�� w procentach (np 5)
		 */
		public int getDesiredStepPercent();
}
