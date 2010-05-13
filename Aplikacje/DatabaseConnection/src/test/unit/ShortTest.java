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
import motion.database.model.AttributeName;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.Filter;
import motion.database.model.GenericName;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.SimplePredicate;
import motion.database.model.Trial;
import motion.database.ws.basicQueriesService.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesService.FilterPredicate;
import motion.database.ws.DatabaseArrayOfFilterPredicate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

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
		database.setWSCredentials("applet_user", "aplet4Motion", "DBPAWELL");
		database.setFTPSCredentials("dbpawell", "testUser", "testUser");
	}

	
	@Test
	public void test() throws Exception {
		
		beforeTest();
	
		DbElementsList<Session> r = database.listPerformerSessionsWithAttributes(1);
		
		System.out.println(r);
	}
}
