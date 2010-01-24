/**
 * 
 */
package test.unit;

import static org.junit.Assert.*;

import motion.database.DatabaseConnection;

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
	@Test
	public void testTestConnection() throws Exception {
		
		database.testConnection();
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadFile()}.
	 * @throws Exception 
	 */
	@Test
	public void testUploadFile() throws Exception {
		
		database.uploadFile( "login.conf" );
	}

}
