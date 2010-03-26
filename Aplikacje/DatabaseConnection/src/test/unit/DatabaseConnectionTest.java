/**
 * 
 */
package test.unit;

import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;
import motion.database.DatabaseConnection;
import motion.database.DatabaseFile;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.Performer;
import motion.database.Segment;
import motion.database.Session;
import motion.database.Trial;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * @author Administrator
 *
 */
public class DatabaseConnectionTest {

	static final boolean testFileUploading = false;
	
	
	public void beforeTest()
	{
		System.out.println("============================================================");
	}
	
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
		
		beforeTest();

		database.testConnection();
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadSessionFile()}.
	 * @throws Exception 
	 */
	@Test
	public void testUploadSessionFile() throws Exception {
		
		beforeTest();

		if (!testFileUploading)
			return;
		
		database.uploadSessionFile( 1, "Próba wgrania pliku ze œledzeniem", "data/Combo_1.c3d", new ConsoleTransferListener() );
		//database.uploadSessionFile( 1, "Próba wgrania pliku ze œledzeniem", "data/test.xml", new ConsoleTransferListener() );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadTrialFile()}.
	 * @throws Exception 
	 */
	@Test
	public void testUploadTrialFile() throws Exception {
		
		beforeTest();

		if (!testFileUploading)
			return;

		database.uploadTrialFile( 1, "A new trial file", "data/Combo_1.c3d", new ConsoleTransferListener() );
	}

	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadPerformerFile()}.
	 * @throws Exception 
	 */
	//@Test
	public void testUploadPerformerFile() throws Exception {
		
		beforeTest();

		if (!testFileUploading)
			return;

		database.uploadPerformerFile( 1, "A new performer file", "data/Combo_1.c3d", new ConsoleTransferListener() );
	}


	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadSessionFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testUploadSessionFiles() throws Exception {
		
		beforeTest();

		//if (!testFileUploading)
		//	return;
		int id = 2;
		
		database.uploadSessionFiles(id, "data/uploaded", new ConsoleTransferListener() );
		//database.uploadSessionFile( 1, "Próba wgrania pliku ze œledzeniem", "data/test.xml", new ConsoleTransferListener() );
	
		System.out.println( database.listSessionFiles( id ) );
	}
	
	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#listPerformerSessionsWithAttributes()}.
	 * @throws Exception 
	 */
	@Test
	public void testListPerformerSessionsWithAttributes() throws Exception {
		
		beforeTest();

		int performerID = 1;
		List<Session> results = database.listPerformerSessionsWithAttributes(performerID);
		
		System.out.println("Sessions for performer: " + performerID);
		if (results != null)
			System.out.println( results );			
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionsTrialsWithAttributes()}.
	 * @throws Exception 
	 */
	@Test
	public void testListSessionsTrialsWithAttributes() throws Exception {
		
		beforeTest();

		int sessionID = 3;
		List<Trial> results = database.listSessionTrialsWithAttributes(sessionID);

		System.out.println("Trials for session: " + sessionID);
		if (results!=null)
			System.out.println( results );			
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listTrialSegmentsWithAttributes()}.
	 * @throws Exception 
	 */
	@Test
	public void testListTrialSegmentsWithAttributes() throws Exception {
		
		beforeTest();

		int trialID = 1;
		List<Segment> results = database.listTrialSegmentsWithAttributes(trialID);

		System.out.println("Segments for trial: " + trialID);
		if (results!=null)
			System.out.println( results );			
	}
/**
	 * Test method for {@link motion.database.DatabaseConnection#listAtributesDefined()}.
	 * @throws Exception 
	 */
	
	@Test
	public void testListAttributesDefined() throws Exception {
		
		beforeTest();

		HashMap<String,String> results = database.listAttributesDefined( "_ALL", "performer" );

		System.out.println("Attributes defined:");
		
		for (String s : results.keySet() ) 
			System.out.println("Attribute: "+ s + " type: " + results.get( s ));			
	}

	@Test
	public void testCreatePerformer() throws Exception {
		
		beforeTest();

		int id = database.createPerformer("Chuck", "Noris");
		System.out.println("Created performer: " + id );

		DbElementsList<Performer> performer = database.listPerformersWithAttributes();
		Assert.assertNotNull( performer.findById( id ) );
	}
	
	@Test
	public void testCreateSession() throws Exception {
		
		beforeTest();

		XMLGregorianCalendar date = new XMLGregorianCalendarImpl();
		date.setYear(2010);
		date.setMonth(3);
		date.setDay(23);
		
		int id = database.createSession(1, null, "Pierwsza sesja Chucka", 1, 1, date, "kopniak z pó³obrotu");
		System.out.println("Created session: " + id );

		DbElementsList<Session> sessions = database.listPerformerSessionsWithAttributes(1);
		Assert.assertNotNull( sessions.findById(id) );
	}

	@Test
	public void testCreateTrial() throws Exception {
		
		beforeTest();

		int id = database.createTrial( 1, "Kopniak lew¹ nog¹", 1 );
		System.out.println("Created trial: " + id );
		
		DbElementsList<Trial> trials = database.listSessionTrialsWithAttributes(1);
		Assert.assertNotNull( trials.findById( id ) );
	}

	@Test
	public void testDefineTrialSegment() throws Exception {
		
		beforeTest();

		int id = 1;
		database.defineTrialSegment( id, "zamach", 1, 2);
		
		System.out.println("Created segment: " + id );
		
		DbElementsList<Segment> segments = database.listTrialSegmentsWithAttributes(id);
		Assert.assertNotNull( segments.findById(id) );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testListSessionFiles() throws Exception {
		
		beforeTest();

		int sessionID = 1;
		DbElementsList<DatabaseFile> results = database.listSessionFiles(sessionID);

		System.out.println("Files for session: " + sessionID);
		if (results != null)
			System.out.println( results );
	}

	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#listTrialFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testListTrialFiles() throws Exception {
		
		beforeTest();
		
		int trialID = 1;
		List<DatabaseFile> results = database.listTrialFiles(trialID);
		
		System.out.println("Files for trial: " + trialID);
		if (results != null)
			System.out.println( results );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listPerformerFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testListPerformerFiles() throws Exception {
		
		beforeTest();

		int performerID = 1;
		DbElementsList<DatabaseFile> results = database.listPerformerFiles(performerID);
		System.out.println("Files for performer: " + performerID);
		if (results != null)
			System.out.println( results );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#setPerformerAttribute}.
	 * @throws Exception 
	 */
	@Test
	public void testSetPerformerAttribute() throws Exception {
		
		beforeTest();

		int id = 1;
		int result = database.setPerformerAttribute( id, "kick_length", "10", true);

		DbElementsList<Performer> list = database.listPerformersWithAttributes();
		Performer x = list.findById(id);
		System.out.println( x );
		this.testListAttributesDefined();
	}
	
	
	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testDownloadFile() throws Exception {
		
		beforeTest();

		if (!testFileUploading)
			return;
		
		int fileID = 3;
		String result = database.downloadFile(fileID, "./data/", new ConsoleTransferListener());
		
		System.out.println( "Downloaded local file name:" + result ); 
	}
	

	
	
	class ConsoleTransferListener implements FileTransferListener
	{

		@Override
		public void transferStepPercent(int percent) {

			System.out.println( "-------->" + percent + "%" );	
			System.out.flush();
		}
		
		@Override
		public int getDesiredStepPercent()
		{
			return 5; // bêdzie informowany co 5%
		}

		@Override
		public void transferStep() {
			System.out.println( "--------> tick" );	
			System.out.flush();
		}
	}
}
