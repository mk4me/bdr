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
import motion.database.model.Session;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class BasicQueriesTest {

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
	
	
	private DatabaseProxy database;
	private Logger log; 
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		database = DatabaseConnection.getInstanceWCF();
//		database.setWSCredentials("applet", "motion#motion2X", "pjwstk");
//		database.setWSCredentials("bzdura", "bzdura", "pjwstk");
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");
		
		this.log = DatabaseConnection.log;
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
	
	@Test
	public void testEntityKindGetAttributes() throws Exception {
		
		beforeTest();
	
		//database.uploadSessionFiles(id, "data/uploaded", new ConsoleTransferListener() );

//		DbElementsList<Session> r = database.listPerformerSessionsWithAttributes(1);
		//DbElementsList<Session> r = database.listLabSessionsWithAttributes(1);
		
		//database.uploadSessionFile( 1, "test nowego wgrania", "data/uploaded/Combo_1.c3d", null);
		
		message("\n======== Attribute Groups =========");
		
		for( EntityKind e : EntityKind.kindsWithGenericAttributes )
		{
			message( "\n" + e.getGUIName() + "::" );
			HashMap<String, EntityAttributeGroup> results = e.getAllAttributeGroups();
			for ( String ss : results.keySet() )
			{
				EntityAttributeGroup g = results.get(ss);
				message( "Group: " + g.name );
				for(EntityAttribute a : g)
				{
					message( "   attribute:" + a.toStringAllContent() );
				}
				
			}
		}
		
		message("\n======== Static Attributes =========");
		for( EntityKind e : EntityKind.kindsWithGenericAttributes )
		{
			message( e.getGUIName() + "::\n" );
			ArrayList<EntityAttribute> results = e.getStaticAttributes();
			for(EntityAttribute a : results)
			{
				message( "   attribute:" + a.toStringAllContent() );
			}
		}

		message("\n======== Generic Attributes =========");
		for( EntityKind e : EntityKind.kindsWithGenericAttributes )
		{
			message( e.getGUIName() + "::" );
			ArrayList<EntityAttribute> results = e.getGenericAttributes();
			for(EntityAttribute a : results)
			{
				message( "   attribute:" + a.toStringAllContent() );
			}
		}
	}
	
	@Test
	public void testEntityKindBetById() throws Exception {
		
		beforeTest();
	
		message("\n======== Geting for ID(1) =========");
		
		for( EntityKind e : EntityKind.kindsWithGetByID )
		{
			message( "\n" + e.getGUIName() + "::" );
			GenericDescription<?> result = database.getById(1, e);
			message( result.toString() + " " + result.values() );
		}
	}

}
