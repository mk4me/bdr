/**
 * 
 */
package motion.database;


class TransferNotifier extends Thread
{
	private final NotifyingInputStream nis;
	public boolean killed = false;

	public TransferNotifier(NotifyingInputStream notifyingInputStream)
	{
		this.nis = notifyingInputStream;
	}
	
	public void run()
	{
		if (this.nis.totalSize == 0 || this.nis.listeners == null || this.nis.listeners.size() == 0)
			return;
		int lastReported = 0;
		
		while (!killed)
		{
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}

			int percent = (int)((float)this.nis.currentSize / (float)this.nis.totalSize * 100);
			if (percent >= (lastReported + this.nis.notificationStepPercent))
			{
				lastReported = percent;
				for( FileTransferListener listener : nis.listeners )
					listener.transferStepPercent( percent );
			}
			if (percent>=this.nis.totalSize)
				killed = true;
		}
	}
}