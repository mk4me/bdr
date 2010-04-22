/**
 * 
 */
package test.unit;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;
import motion.database.DatabaseArrayOfFilterPredicate;
import motion.database.DatabaseConnection;
import motion.database.DatabaseFile;
import motion.database.DbElementsList;
import motion.database.EntityAttributeGroup;
import motion.database.FileTransferListener;
import motion.database.GenericName;
import motion.database.MotionKind;
import motion.database.Performer;
import motion.database.Segment;
import motion.database.Session;
import motion.database.Trial;
import motion.database.ws.basicQueriesService.ArrayOfFilterPredicate;
import motion.database.ws.FilterPredicate;

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

	
	@Test
	public void testDownloadFile() throws Exception {
		
		beforeTest();
		
		FilterPredicate filter = new FilterPredicate();
		filter.setPredicateID(1);
		filter.setParentPredicate(0);
		filter.setContextEntity("session");
		filter.setPreviousPredicate(0);
		filter.setNextOperator("");
		filter.setFeatureName("sessionID");
		filter.setOperator("=");
		filter.setValue("1");
		filter.setAggregateEntity("");
		filter.setAggregateFunction("");
		
		List<? extends Object> result = database.execGenericQuery(new FilterPredicate[]{filter}, new String[]{"session"});
	
		System.out.println(result);
	}
}
