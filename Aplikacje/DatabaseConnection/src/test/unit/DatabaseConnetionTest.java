/**
 * 
 */
package test.unit;

import static org.junit.Assert.*;

import java.util.List;

import motion.database.DatabaseConnection;
import motion.database.Session;
import motion.database.SessionStaticAttributes;
import motion.database.ws.basicQueriesService.FileDetails;
import motion.database.ws.basicQueriesService.SessionDetails;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class DatabaseConnetionTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	
		
	}

	private DatabaseConnection database; 
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		database = DatabaseConnection.getInstance();
		database.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		database.setFTPSCredentials("dbpawell", "testUser", "testUser");
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#testConnection()}.
	 * @throws Exception 
	 */
	//@Test
	public void testTestConnection() throws Exception {
		
		database.testConnection();
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadFile()}.
	 * @throws Exception 
	 */
	//@Test
	public void testUploadFile() throws Exception {
		
		database.uploadFile( 1, "Druga próba wgrania pliku", "data/Combo_1.c3d" );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listPerformerSessions()}.
	 * @throws Exception 
	 */
	@Test
	public void testListPerformerSessions() throws Exception {
		
		int performerID = 1;
		List<Session> results = database.listPerformerSessions( performerID );
		
		for (Session s : results)
			for ( String key : s.keySet() )
				System.out.println( key + " = " + s.get(key) ); 
	}

	@Test
	public void testListPerformerSessionsWithAttributes() throws Exception {
		
		int performerID = 1;
		List<Session> results = database.listPerformerSessionsWithAttributes(performerID);
		
		for (Session s : results)
			for ( String key : s.keySet() )
				System.out.println( key + " = " + s.get(key) ); 
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testListSessionFiles() throws Exception {
		
		int sessionID = 1;
		List<FileDetails> results = database.listSessionFiles(sessionID);
		
		for (FileDetails s : results)
		{
			System.out.println( "File Name:" + s.getFileName() + " File ID:" + s.getFileID() ); 
		}
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionFiles()}.
	 * @throws Exception 
	 */
	//@Test
	public void testDownloadFile() throws Exception {
		
		int fileID = 3;
		String result = database.downloadFile(fileID, "");
		
		System.out.println( "Downloaded local file name:" + result ); 
	}
}
