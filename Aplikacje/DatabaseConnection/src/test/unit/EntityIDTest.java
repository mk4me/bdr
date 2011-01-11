/**
 * 
 */
package test.unit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.ws.ConnectionTools2;
import motion.database.ws.WSDatabaseConnection;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class EntityIDTest {

	public void beforeTest()
	{
		log.info( "\n============================================================");
		log.info( "\t" + Thread.currentThread().getStackTrace()[2].getMethodName() );
		log.info( "============================================================");
	}
	
	public void message(String s)
	{
		log.info( s );
	}
	
	
	private WSDatabaseConnection database;
	private Logger log; 
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		database = (WSDatabaseConnection) DatabaseConnection.getInstanceWCF();
//		database.setWSCredentials("applet", "motion#motion2X", "pjwstk");
//		database.setWSCredentials("bzdura", "bzdura", "pjwstk");
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");
		
		this.log = DatabaseConnection.log;
	}
	
	@Test
	public void testSetAttribute() throws Exception
	{
		beforeTest();
		IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "execGenericQuery", database);
	
		int id = 2;
		
		EntityKind kind = EntityKind.performer;

		Performer p = new Performer();
		
		p.put( PerformerStaticAttributes.FirstName, "Janek" );
		p.put( PerformerStaticAttributes.LastName, "Kowalski" );
		
		p = (Performer)kind.getByID(port, id);
		
		System.out.println( p );
		System.out.println( p.getId() );
	}
	
	@Test
	public void testListSessionGroupsDefined() throws Exception
	{
		beforeTest();
		IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "execGenericQuery", database);
	
		DbElementsList<SessionGroup> res = database.listSessionGroupsDefined();
	
		for (SessionGroup sg : res)
			System.out.println( sg );
		
	}
}
