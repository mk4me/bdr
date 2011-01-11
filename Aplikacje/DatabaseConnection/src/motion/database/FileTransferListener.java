package motion.database;

public interface FileTransferListener {

		/**
		 * This method will be called every X bytes of transfer during download. 
		 * Initially X is set to 4096
		 */
		public void transferStep();
		
		/**
		 * This method will be called every getDesiredStepPercent()
		 * 
		 * @param percent actual upload state in percents 
		 */
		public void transferStepPercent(int percent);
		
		/**
		 * 
		 * This method sets every how many percents of progress the method transferStepPercent must be called. 
		 * 
		 * @return a value in percents (for ex. 5)
		 */
		public int getDesiredStepPercent();
}
