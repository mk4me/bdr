package motion.database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.apache.commons.net.ftp.FTP;

import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;

class FileTransferSupport {

	Vector<FileTransferListener> uploadListeners = new Vector<FileTransferListener>(); 
	Vector<FileTransferListener> downloadListeners = new Vector<FileTransferListener>(); 

	
	public void putFile(String localFilePath, String destRemoteFolder, String address, String userName, String password ) throws Exception
	{
		FileInputStream fileInputStream = new FileInputStream(localFilePath);
		File file = new File(localFilePath);
		long size = file.length();
		NotifyingInputStream input = new NotifyingInputStream( fileInputStream, size, uploadListeners );
		
		int status = FTPs.sendFile( input, file.getName(), destRemoteFolder, address, userName, password);
		if(FileTransferStatus.FAILURE == status){
			throw new Exception( "Fail to ftps  to  folder "+destRemoteFolder );
		}
		
		input.close();
	}

	public void getFile(String remoteFileName, String remoteFilePath, String localFileName, String destLocalFolder, String address, String userName, String password ) throws Exception
	{
		BufferedInputStream input;
		BufferedOutputStream output = new BufferedOutputStream( new FileOutputStream( localFileName ) );
		input = new BufferedInputStream( FTPs.getFileAsStream(remoteFileName, remoteFilePath, address, userName, password) );
		
		byte buffer[] = new byte[4096];
		int counter = 0;
		
		while((counter =input.read( buffer )) != -1)
		{
			output.write( buffer, 0, counter );
			for (FileTransferListener l : downloadListeners)
				l.transferStep();
		}
		input.close();
		output.close();
	}

	public void registerUploadListener(FileTransferListener listener) {
		
		this.uploadListeners.add( listener );
	}

	public void registerDownloadListener(FileTransferListener listener)
	{
		this.downloadListeners.add( listener );
	}


	public void resetUploadListeners() {
		uploadListeners.removeAllElements();
	}

	public void resetDownloadListeners() {
		downloadListeners.removeAllElements();
	}

	
}

class NotifyingInputStream extends FilterInputStream
{

	long totalSize;
	long currentSize;
	Vector<FileTransferListener> listeners;
	private TransferNotifier notifier;
	public int notificationStepPercent; 

	protected NotifyingInputStream(InputStream arg0, long totalTransferSize, Vector<FileTransferListener> listeners) {
		super(arg0);
		this.totalSize = totalTransferSize;
		this.listeners = null;
		this.currentSize = 0;
		this.listeners = listeners;
		if (listeners != null && listeners.get(0) != null)
			this.notificationStepPercent = listeners.get(0).getDesiredStepPercent();
		
		notifier = new TransferNotifier(this);
		notifier.setDaemon( true );
		notifier.start();
	}
	
	@Override
	public int read() throws IOException
	{
		currentSize++;
		return super.read();
	}

	@Override
	public int read(byte[] buffer) throws IOException
	{
		int result = super.read(buffer);
		currentSize += result/2;
		return result;
	}
	
	@Override
	public int read(byte[] buffer, int x, int y) throws IOException
	{
		int result = super.read(buffer, x, y);
		currentSize += result/2;
		return result;
	}
	
	@Override
	public void close() throws IOException
	{
		super.close();
		if (this.notifier != null )
			this.notifier.killed = true;
	}
}
