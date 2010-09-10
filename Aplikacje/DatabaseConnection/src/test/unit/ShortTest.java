/**
 * 
 */
package test.unit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Session;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class ShortTest {

	public void beforeTest()
	{
		System.out.println("============================================================");
	}
	
	private DatabaseProxy database; 
	
	
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
	public void test() throws Exception {
		
		beforeTest();
	
		int id = 2;
		
		//database.uploadSessionFiles(id, "data/uploaded", new ConsoleTransferListener() );

//		DbElementsList<Session> r = database.listPerformerSessionsWithAttributes(1);
		//DbElementsList<Session> r = database.listLabSessionsWithAttributes(1);
		
		//database.uploadSessionFile( 1, "test nowego wgrania", "data/uploaded/Combo_1.c3d", null);
		
		EnumSet<EntityKind> set = EnumSet.of( EntityKind.performer, EntityKind.session, EntityKind.trial, EntityKind.measurement_conf );

		System.out.println("========Attribute Groups=========");
		
		for( EntityKind e : set )
		{
			System.out.println(e.getGUIName() + "::" );
			HashMap<String, EntityAttributeGroup> results = e.getAllAttributeGroups();
			for ( String ss : results.keySet() )
			{
				EntityAttributeGroup g = results.get(ss);
				System.out.println( "Group: " + g.name );
				for(EntityAttribute a : g)
				{
					System.out.println( "   attribute:" + a.toStringAllContent() );
				}
				
			}
		}
		
		System.out.println("========Static Attributes=========");
		for( EntityKind e : set )
		{
			ArrayList<EntityAttribute> results = e.getStaticAttributes();
			for(EntityAttribute a : results)
			{
				System.out.println( "   attribute:" + a.toStringAllContent() );
			}
		}

		System.out.println("========Generic Attributes=========");
		for( EntityKind e : set )
		{
			ArrayList<EntityAttribute> results = e.getGenericAttributes();
			for(EntityAttribute a : results)
			{
				System.out.println( "   attribute:" + a.toStringAllContent() );
			}
		}

	}
}
