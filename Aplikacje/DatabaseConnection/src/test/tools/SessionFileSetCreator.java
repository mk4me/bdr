package test.tools;

import java.io.FileWriter;
import java.io.IOException;

public class SessionFileSetCreator {

	/**
	 * @param args 
	 * [0] - session name
	 * [1] - session date
	 * [2] - number of trials
	 */
	public static void main(String[] args) {
		
		String date = args[1];
		String name = args[0];
		int trialsNo = Integer.parseInt( args[2] );
		
		try {
			createSessionFiles( date+name, trialsNo );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private static void createSessionFiles(String string, int trialsNo) throws IOException 
	{
		createFile( string+".zip", "Hello session Kitty!" );

		for(int i=1; i<=trialsNo; i++)
			createTrialFiles(string, i);
	}
	
	private static void createTrialFiles(String string, int trialNo) throws IOException {
		for( int i=1; i<=4; i++)
			createFile( string+"-trial" + trialNo + ".cam" + i + ".avi", "Hello trial Kitty!" );
	}

	private static void createFile(String name, String content) throws IOException
	{
		FileWriter f = new FileWriter(name);
		f.write( content );
		f.close();
		System.out.println("Created file: " + name);
	}
}
