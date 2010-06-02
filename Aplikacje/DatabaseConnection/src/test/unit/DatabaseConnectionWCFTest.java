/**
 * 
 */
package test.unit;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.GenericName;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.Trial;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * @author Administrator
 *
 */
public class DatabaseConnectionWCFTest {

	static final boolean testFileUploading = true;
	
	
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

	private DatabaseProxy database; 
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		database = DatabaseConnection.getInstanceWCF();
//		database.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		database.setWSCredentials("applet_user", "aplet4Motion", "db-bdr");
//		database.setWSCredentials("bzdura", "bzdura", "DBPAWELL");
		database.setFTPSCredentials("db-bdr.pjwstk.edu.pl", "testUser", "testUser");
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#testConnection()}.
	 * @throws Exception 
	 */
//	//@Test
//	public void testTestConnection() throws Exception {
//		
//		beforeTest();
//
//		database.testConnection();
//	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#uploadSessionFile()}.
	 * @throws Exception 
	 */
	@Test
	public void testUploadSessionFile() throws Exception {
		
		beforeTest();

		if (!testFileUploading)
			return;
		
		database.uploadSessionFile( 1, "Pr�ba wgrania pliku ze �ledzeniem", "data/Combo_1.c3d", new ConsoleTransferListener() );
		//database.uploadSessionFile( 1, "Pr�ba wgrania pliku ze �ledzeniem", "data/test.xml", new ConsoleTransferListener() );
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

		if (!testFileUploading)
			return;
		int id = 2;
		
		database.uploadSessionFiles(id, "data/uploaded", "", new ConsoleTransferListener() );
		//database.uploadSessionFile( 1, "Pr�ba wgrania pliku ze �ledzeniem", "data/test.xml", new ConsoleTransferListener() );
	
		System.out.println( database.listSessionFiles( id ) );
	}

	
	@Test
	public void testListMotionKindsSessionGroupsDefined() throws Exception {
		
		beforeTest();
		Vector<? extends GenericName> results = database.listMotionKindsDefined();
		
		System.out.println("Motion kinds: ");
		if (results != null)
			for ( GenericName m : results)
				System.out.println( m );

		results = database.listSessionGroupsDefined();
		
		System.out.println("Session groups: ");
		if (results != null)
			for ( GenericName m : results)
				System.out.println( m );
	}


	@Test
	public void testListLabSessionsWithAttributes() throws Exception {
		
		beforeTest();

		int labID = 1;
		List<Session> results = database.listPerformerSessionsWithAttributes(labID);
		
		System.out.println("Sessions for lab: " + labID);
		if (results != null)
			System.out.println( results );			
	}

	@Test
	public void testListLabPerformersWithAttributes() throws Exception {
		
		beforeTest();

		int labID = 1;
		DbElementsList<Performer> results = database.listLabPerformersWithAttributes(labID);
		
		System.out.println("Performers for lab: " + labID);
		if (results != null)
			System.out.println( results );			
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

	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#listGrouppedAtributesDefined()}.
	 * @throws Exception 
	 */
	
	@Test
	public void testListGrouppedAttributesDefined() throws Exception {
		
		beforeTest();

		HashMap<String, EntityAttributeGroup> results = database.listGrouppedAttributesDefined( "performer" );

		System.out.println("Groupped attributes defined:");
		
		for (String s : results.keySet() ) 
			System.out.println("Attribute group: " + s + "\n\r content: " + results.get( s ));			
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listAtributeGroupDefined()}.
	 * @throws Exception 
	 */
	
	@Test
	public void testListAttributeGroupsDefined() throws Exception {
		
		beforeTest();

		Vector<String> results = database.listAttributeGroupsDefined("_ALL_ENTITIES");

		System.out.println("Attributes defined:");
		
		for (String s : results ) 
			System.out.println("Attributes group: "+ s );			
	}
	
	
	@Test
	public void testCreatePerformer() throws Exception {
		
		beforeTest();

		int id = database.createPerformer("Chuck", "Noris");
		System.out.println("Created performer: " + id );

		Performer performer = database.getPerformerById(id);
		Assert.assertNotNull( performer );
	}
	
	@Test
	public void testCreateSession() throws Exception {
		
		beforeTest();

		XMLGregorianCalendar date = new XMLGregorianCalendarImpl();
		date.setYear(2010);
		date.setMonth(3);
		date.setDay(23);
		
		int id = database.createSession(1, new int[]{}, "Pierwsza sesja Chucka", 1, 1, date, "kopniak tyłem na siedząco?");
		System.out.println("Created session: " + id );

		Session session = database.getSessionById(id);
		Assert.assertNotNull( session );
	}

	@Test
	public void testCreateTrial() throws Exception {
		
		beforeTest();

		int id = database.createTrial( 1, "Kopniak lew� nog�", 1 );
		System.out.println("Created trial: " + id );
		
		Trial trials = database.getTrialById(id);
		Assert.assertNotNull( trials );
	}

	@Test
	public void testDefineTrialSegment() throws Exception {
		
		beforeTest();

		int id = 1;
		database.defineTrialSegment( id, "zamach", 1, 2);
		
		System.out.println("Created segment: " + id );
		
		Segment segments = database.getSegmentById(id);
		Assert.assertNotNull( segments );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#listSessionFiles()}.
	 * @throws Exception 
	 */
	@Test
	public void testListSessionFiles() throws Exception {
		
		beforeTest();

		int sessionID = 3;
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

			int id = 3;
			String attributeName = "date_of_birth";
			String value = "12.12.2012";
			database.setPerformerAttribute( id, attributeName, value, true);

			DbElementsList<Performer> list = database.listPerformersWithAttributes();
			Performer x = list.findById(3);
			Assert.assertTrue( x.get( attributeName ).value.equals( value ) );
	}
	
	/**
	 * Test method for {@link motion.database.DatabaseConnection#setPerformerAttribute}.
	 * @throws Exception 
	 */
	//@Test
	public void testSetSesssionAttribute() throws Exception {
		
		beforeTest();

		int id = 3;
		String attributeName = "date_of_birth";
		String value = "12.12.2012";
		database.setSessionAttribute( id, attributeName, value, false);

//		DbElementsList<Performer> list = database.listPerformerSessionsWithAttributes(1);
//		Performer x = list.findById(3);
//		Assert.assertTrue( x.get( attributeName ).equals( value ) );
	}

	/**
	 * Test method for {@link motion.database.DatabaseConnection#setPerformerAttribute}.
	 * @throws Exception 
	 */
	//@Test
	public void testSetTrialAttribute() throws Exception {
		
		beforeTest();

		int id = 3;
		String attributeName = "date_of_birth";
		String value = "12.12.2012";
		database.setPerformerAttribute( id, attributeName, value, false);

		DbElementsList<Performer> list = database.listPerformersWithAttributes();
		Performer x = list.findById(3);
		Assert.assertTrue( x.get( attributeName ).equals( value ) );
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
			return 5; // b�dzie informowany co 5%
		}

		@Override
		public void transferStep() {
			System.out.println( "--------> tick" );	
			System.out.flush();
		}
	}
}
