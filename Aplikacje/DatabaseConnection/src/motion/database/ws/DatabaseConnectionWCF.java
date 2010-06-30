package motion.database.ws;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.TextMessageListener;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Filter;
import motion.database.model.GenericResult;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;
import motion.database.ws.authorizationWCF.IAuthorizationWS;
import motion.database.ws.authorizationWCF.IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfString;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.GenericUniformAttributesQueryResult;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGenericQueryUniformXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSegmentByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionLabelQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListAttributeGroupsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListAttributesDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListLabPerformersWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListLabSessionsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListMotionKindsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListPerformerSessionsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListPerformersWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListSessionGroupsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListSessionTrialsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListTrialSegmentsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerSessionWithAttributesList;
import motion.database.ws.basicQueriesServiceWCF.SegmentDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesServiceWCF.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesServiceWCF.GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesServiceWCF.SessionGroupDefinitionList.SessionGroupDefinition;
import motion.database.ws.basicUpdatesServiceWCF.ArrayOfInt;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreatePerformerUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSDefineTrialSegmentUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetSegmentAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.PerformerData;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;

import com.zehon.BatchTransferProgress;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;


public class DatabaseConnectionWCF implements DatabaseProxy {

	public static class Credentials {
		public String userName;
		public String password;
		public String domainName;
		public String address;

		public void setCredentials(String userName, String password, String domainName)
		{
			this.userName = userName;
			this.password = password;
			this.domainName = domainName;
		}
		
		public void setAddress(String address)
		{
			this.address = address;
		}
	}

	enum ConnectionState{ INITIALIZED, CONNECTED, ABORTED, CLOSED, UNINITIALIZED };
	
	protected ConnectionState state;
	protected Credentials wsCredentials = new Credentials();
	protected Credentials ftpsCredentials = new Credentials();
	protected Authenticator authenticator;
	protected FileTransferSupport fileTransferSupport = new FileTransferSupport();
	protected boolean fileTransferCancelled;

	protected Logger log;

	
	public void setWSCredentials(String userName, String password, String domainName)
	{
		log.info("Setting WS credentials for:" + userName + " domain:" + domainName);

		this.wsCredentials.userName = userName;
		this.wsCredentials.password = password;
		this.wsCredentials.domainName = domainName;
		this.state = ConnectionState.INITIALIZED;
	
		this.authenticator = new Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				
				System.err.println("Serwer pyta o hasło. (Odpowiedziałem)" );
				
				log.entering("Authenticator", "getPasswordAuthentication");
				log.fine( "Host: " + this.getRequestingHost() );
				log.fine( "Prompt: " + this.getRequestingPrompt() );
				log.fine( "Protocol: " + this.getRequestingProtocol() );
				log.fine( "Sheme: " + this.getRequestingScheme() );
				log.fine( "Site: " + this.getRequestingSite() );
				log.fine( "Requestor Type: " + this.getRequestorType() );
				
				// W niekt�rych przypadkach przed username trzeba poda� domain oddzielone backslashem
				// my te� tak robimy na wszelki wypadek 
				return new PasswordAuthentication(wsCredentials.domainName+"\\"+wsCredentials.userName, wsCredentials.password.toCharArray() );
			  }
		};
		
        Authenticator.setDefault( this.authenticator );

        System.setProperty( "http.auth.preference", "ntlm");
    	System.setProperty( "java.security.krb5.conf", "krb5.conf");
    	System.setProperty( "java.security.auth.login.config", "login.conf");
    	System.setProperty( "javax.security.auth.useSubjectCredsOnly", "false");
    	System.setProperty( "http.auth.ntlm.domain", domainName);
   	}
	

	public void setFTPSCredentials(String address, String userName, String password)
	{
		log.info("Setting FTP credentials for:" + userName + "@" + address);
		this.ftpsCredentials.setAddress(address);
		this.ftpsCredentials.setCredentials(userName, password, null);
	}

	
	public DatabaseConnectionWCF(Logger log)
	{
		this.log = log;
		this.state = ConnectionState.UNINITIALIZED;
	};

	
	public void registerStateMessageListener(TextMessageListener listener) {
	
		ToolsWCF.registerActionListener(listener);
	}

/*==========================================================================
 * 	BasicQueriesServiceWCF
 *==========================================================================
 */	
	
	
////////////////////////////////////////////////////////////////////////////
//	Generic Result 
	
	
	public  List<GenericResult> execGenericQuery(Filter filter, String[] p_entitiesToInclude) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "execGenericQuery", this );
			
			ArrayOfString entitiesToInclude = new ArrayOfString();
			for( String s : p_entitiesToInclude )
				entitiesToInclude.getString().add( s );
			
			ArrayOfFilterPredicate arrayOfFilterPredicate = new ArrayOfFilterPredicate();
			for ( FilterPredicate f : filter.toFilterPredicateWCF() )
				arrayOfFilterPredicate.getFilterPredicate().add( f );
			
			GenericQueryUniformXMLResult result = port.genericQueryUniformXML( 
					arrayOfFilterPredicate,	entitiesToInclude );
	
			
			DbElementsList<GenericResult> output = new DbElementsList<GenericResult>();
			GenericUniformAttributesQueryResult ss = result.getGenericUniformAttributesQueryResult();
			for (Attributes aa : ss.getAttributes() )
				output.add( ToolsWCF.transformGenericAttributes( aa, new GenericResult() ) );
			
			return output;
		}
		catch(IBasicQueriesWSGenericQueryUniformXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}
	

////////////////////////////////////////////////////////////////////////////
//	Attributes 

	
	public HashMap<String, String> listAttributesDefined(String group, String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listAttributesDefined", this );
	
			ListAttributesDefinedResult result = port.listAttributesDefined( group, entityKind );
			
			HashMap<String, String> output = new HashMap<String, String>();
			for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
					output.put( a.getAttributeName(), a.getAttributeType() );
			
			return output;
		}
		catch(IBasicQueriesWSListAttributesDefinedQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listGrouppedAttributesDefined", this );
	
			ListAttributesDefinedResult result = port.listAttributesDefined( "_ALL", entityKind);
			
			HashMap<String, EntityAttributeGroup> output = new HashMap<String, EntityAttributeGroup>();
	
			for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
			{	
				EntityAttributeGroup group = output.get( a.getAttributeGroupName() );
				if (group == null)
				{
					group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
					output.put( a.getAttributeGroupName(), group );
				}
				group.add( new EntityAttribute( a.getAttributeName(), null, a.getAttributeGroupName(), a.getAttributeType() ) );
			}
		
			return output;
		}
		catch(IBasicQueriesWSListAttributesDefinedQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	
////////////////////////////////////////////////////////////////////////////
//	Attribute Groups
	
	
	public Vector<String> listAttributeGroupsDefined(String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listAttributeGroupsDefined", this );
			
			ListAttributeGroupsDefinedResult result = port.listAttributeGroupsDefined(entityKind);
			
			Vector<String> output = new Vector<String>();
			for (AttributeGroupDefinition a : result.getAttributeGroupDefinitionList().getAttributeGroupDefinition() )
				output.add( a.getAttributeGroupName() );
	
			return output;
		}
		catch(IBasicQueriesWSListAttributeGroupsDefinedQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Motion Kinds

	
	public Vector<MotionKind> listMotionKindsDefined() throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listMotionKindsDefined", this );
			
			ListMotionKindsDefinedResult result = port.listMotionKindsDefined();
			
			Vector<MotionKind> output = new Vector<MotionKind>();
			for (MotionKindDefinition a : result.getMotionKindDefinitionList().getMotionKindDefinition() )
				output.add( new MotionKind( a.getMotionKindID(), a.getMotionKindName() ) );
	
			return output;
		}
		catch(IBasicQueriesWSListMotionKindsDefinedQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
}


	
////////////////////////////////////////////////////////////////////////////
//	Performer

	
	public Performer getPerformerById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getPerformerById", this );
		
			GetPerformerByIdXMLResult result = port.getPerformerByIdXML(id);
			PerformerDetailsWithAttributes s = result.getPerformerDetailsWithAttributes();
	
			return ToolsWCF.transformPerformerDetails(s);
		}
		catch(IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public  DbElementsList<Performer> listLabPerformersWithAttributes(int labID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabPerformersWithAttributes", this );
	
			ListLabPerformersWithAttributesXMLResult result = port.listLabPerformersWithAttributesXML(labID);
	
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			if (result.getLabPerformerWithAttributesList() != null) 
				for ( PerformerDetailsWithAttributes s : result.getLabPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
						output.add( ToolsWCF.transformPerformerDetails(s) );
			
			return output;
		}
		catch(IBasicQueriesWSListLabPerformersWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public  DbElementsList<Performer> listPerformersWithAttributes() throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformersWithAttributes", this );
	
			ListPerformersWithAttributesXMLResult result = port.listPerformersWithAttributesXML();
	
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for ( PerformerDetailsWithAttributes s : result.getPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
				output.add( ToolsWCF.transformPerformerDetails(s) );
			
			return output;
		}
		catch(IBasicQueriesWSListPerformersWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session
	
	
	public Session getSessionById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSessionById", this );
		
			GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
			SessionDetailsWithAttributes s = result.getSessionDetailsWithAttributes();
	
			return ToolsWCF.transformSessionDetails(s);
		}
		catch(IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}


	public  String getSessionLabel(int sessionID) throws Exception
	{
		try{	
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSessionLabel", this );
		
			return port.getSessionLabel( sessionID );
		}
		catch(IBasicQueriesWSGetSessionLabelQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public  DbElementsList<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformerSessionsWithAttributes", this );
		
			ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			PerformerSessionWithAttributesList ss = result.getPerformerSessionWithAttributesList();
			for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
					output.add( ToolsWCF.transformSessionDetails(s) );
	
			return output;
		}
		catch(IBasicQueriesWSListPerformerSessionsWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
	{
		try {
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabSessionsWithAttributes", this );

			ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			for ( motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes s : result.getLabSessionWithAttributesList().getSessionDetailsWithAttributes() )
				output.add( ToolsWCF.transformSessionDetails(s) );
			
			return output;
		} 
		catch (IBasicQueriesWSListLabSessionsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session Groups
	
	public Vector<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionGroupsDefined", this );
			
			ListSessionGroupsDefinedResult result = port.listSessionGroupsDefined();
			
			Vector<SessionGroup> output = new Vector<SessionGroup>();
	
			for (SessionGroupDefinition a : result.getSessionGroupDefinitionList().getSessionGroupDefinition() )
				output.add( new SessionGroup( a.getSessionGroupID(), a.getSessionGroupName() ) );
			
			return output;
		} 
		catch (IBasicQueriesWSListSessionGroupsDefinedQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}


	

////////////////////////////////////////////////////////////////////////////
//	Trial

	
	public Trial getTrialById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getTrialById", this );
	
			GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
			TrialDetailsWithAttributes s = result.getTrialDetailsWithAttributes();
	
			ToolsWCF.finalizeCall();
			return ToolsWCF.transformTrialDetails(s);
		} 
		catch (IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}


	public  DbElementsList<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionTrialsWithAttributes", this );
		
			ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
			DbElementsList<Trial> output = new DbElementsList<Trial>();
			
			for ( TrialDetailsWithAttributes s : result.getSessionTrialWithAttributesList().getTrialDetailsWithAttributes() )
				output.add( ToolsWCF.transformTrialDetails(s) );
				
			ToolsWCF.finalizeCall();
			return output;
		} 
		catch (IBasicQueriesWSListSessionTrialsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}


	
////////////////////////////////////////////////////////////////////////////
//	Segment

	
	public Segment getSegmentById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSegmentById", this );
		
			GetSegmentByIdXMLResult result = port.getSegmentByIdXML(id);
			SegmentDetailsWithAttributes s = result.getSegmentDetailsWithAttributes();
			
			return ToolsWCF.transformSegmentDetails(s);
		} 
		catch (IBasicQueriesWSGetSegmentByIdXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public  DbElementsList<Segment> listTrialSegmentsWithAttributes(int trialID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listTrialSegmentsWithAttributes", this );
			
			ListTrialSegmentsWithAttributesXMLResult result = port.listTrialSegmentsWithAttributesXML(trialID);
			DbElementsList<Segment> output = new DbElementsList<Segment>();
			
			for ( SegmentDetailsWithAttributes s : result.getTrailSegmentWithAttributesList().getSegmentDetailsWithAttributes() )
				output.add( ToolsWCF.transformSegmentDetails(s) );
	
			return output;
		} 
		catch (IBasicQueriesWSListTrialSegmentsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	File
	
	
	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionFiles", this );
		
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, EntityKind.session.name());
	
			return ToolsWCF.transformListOfFiles(result);
		} 
		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	

	public  DbElementsList<DatabaseFile> listTrialFiles(int trialID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listTrialFiles", this );
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, EntityKind.trial.name());
			
			return ToolsWCF.transformListOfFiles(result);
		} 
		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	

	public  DbElementsList<DatabaseFile> listPerformerFiles(int performerID) throws Exception
	{
		try{
			IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformerFiles", this );
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, EntityKind.performer.name());
			
			return ToolsWCF.transformListOfFiles(result);
		} 
		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ToolsWCF.finalizeCall();
		}
	}
	
	
	/*==========================================================================
	 * 	FileStoremanServiceWCF
	 *==========================================================================
	 */	

	public void cancelCurrentFileTransfer()
	{
		fileTransferSupport.cancel();
		fileTransferCancelled = true;
	}


	public void registerFileUploadListener( FileTransferListener listener )
	{
		this.fileTransferSupport.registerUploadListener(listener);
	}


	protected void createRemoteFolder( String newFolder, String destRemoteFolder ) throws FileTransferException
	{
		int status = FTPs.createFolder( newFolder, destRemoteFolder, 
				this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password);
		if(FileTransferStatus.SUCCESS == status){
			log.info( newFolder + " created in folder "+destRemoteFolder);
		}
		else if(FileTransferStatus.FAILURE == status){
			log.severe("Fail to ftps  to  folder "+destRemoteFolder);
		}
	}

	protected String getUniqueFolderName() {
		return wsCredentials.userName + System.currentTimeMillis()+"/";
	}

	
	class BatchTransferProgressObserver implements BatchTransferProgress
	{

		@Override
		public void transferComplete(String arg0) {
			System.out.println( "Transfer finished: " + arg0);
		}

		@Override
		public void transferError(String arg0, Throwable arg1) {
			System.out.println( "Transfer error: " + arg0);
		}

		@Override
		public void transferStart(String arg0) {
			System.out.println( "Transfer started: " + arg0);
		}
	}
	
////////////////////////////////////////////////////////////////////////////
//	Download
	
	
	public  String downloadFile(int fileID, String destLocalFolder, FileTransferListener transferListener) throws Exception
	{
			IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "downloadFile", this );
	
			String file = port.retrieveFile(fileID);
			
			File remoteFile = new File ( file );

			getFile( remoteFile.getName(), remoteFile.getParent(), 
					this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password,
					destLocalFolder, transferListener );

			port.downloadComplete(fileID, file);
			
			ToolsWCF.finalizeCall();
			return destLocalFolder + remoteFile.getName();
	}

	
	private void getFile(String remoteFileName, String remoteFilePath, String address,
			String userName, String password, String destLocalFolder,
			FileTransferListener transferListener) {

		fileTransferSupport.resetDownloadListeners();
		fileTransferSupport.registerDownloadListener(transferListener);
		
		try {
			fileTransferSupport.getFile(remoteFileName, remoteFilePath, remoteFileName, destLocalFolder, address, userName, password );
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
		}
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Upload


	protected void putFile(String localFilePath, String destRemoteFolder, FileTransferListener listener) throws FileTransferException
	{
		fileTransferSupport.resetUploadListeners();
		if (listener != null)
			fileTransferSupport.registerUploadListener(listener);
		
		try {
			createRemoteFolder( destRemoteFolder, "" );
			fileTransferSupport.putFile(localFilePath, destRemoteFolder, ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
		} finally
		{
			ToolsWCF.finalizeCall();
		}
	}

	
	public void uploadPerformerFile(int performerId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{

		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadPerformerFile", this );

		String destRemoteFolder = getUniqueFolderName();
		putFile(localFilePath, destRemoteFolder, listener);			
		    
		if (!fileTransferCancelled)
				port.storePerformerFile( performerId, destRemoteFolder, description, new File(localFilePath).getName() );
		
		System.out.println( destRemoteFolder + "   " + localFilePath );
		
		ToolsWCF.finalizeCall();
	}

	
	public void uploadSessionFile(int sessionId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadSessionFile", this );

		String destRemoteFolder = getUniqueFolderName();
		putFile(localFilePath, destRemoteFolder, listener);			

		if (!fileTransferCancelled)
				port.storeSessionFile(sessionId, destRemoteFolder, description, new File(localFilePath).getName() );
		ToolsWCF.finalizeCall();
	}

	
	public void uploadTrialFile(int trialId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadTrialFile", this );

		String destRemoteFolder = getUniqueFolderName();
		putFile(localFilePath, destRemoteFolder, listener);			

		if (!fileTransferCancelled)
			port.storeTrialFile(trialId, destRemoteFolder, description, new File(localFilePath).getName() );
		ToolsWCF.finalizeCall();
	}


	public void uploadPerformerFiles(int performerId, String filesPath, String description, FileTransferListener listener) throws Exception
	{
		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadPerformerFiles", this );

		String destRemoteFolder = getUniqueFolderName();

		File dir = new File(filesPath);
		if ( dir.isDirectory() )
		{
			createRemoteFolder( dir.getName(), destRemoteFolder );
			FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
			port.storePerformerFiles( performerId, destRemoteFolder+dir.getName(), description );
		}
		else
			throw new Exception( filesPath + " is not a directory. Cannot perform batch upload.");
		ToolsWCF.finalizeCall();
	}
	

	public void uploadSessionFiles(int sessionId, String filesPath, String description, FileTransferListener listener) throws Exception
	{
		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadSessionFiles", this );

		String destRemoteFolder = getUniqueFolderName();

		File dir = new File(filesPath);
		if ( dir.isDirectory() )
		{
			createRemoteFolder( dir.getName(), destRemoteFolder );
			FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
			port.storeSessionFiles( sessionId, destRemoteFolder+dir.getName(), description );
		}
		else
			throw new Exception( filesPath + " is not a directory. Cannot perform batch upload.");
		ToolsWCF.finalizeCall();
	}

	
	public void uploadTrialFiles(int trialId, String filesPath, String description, FileTransferListener listener) throws Exception
	{
		IFileStoremanWS port = ToolsWCF.getFileStoremanServicePort( "uploadTrialFiles", this );

		String destRemoteFolder = getUniqueFolderName();

		File dir = new File(filesPath);
		if ( dir.isDirectory() )
		{
			createRemoteFolder( dir.getName(), destRemoteFolder );
			FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
			port.storeTrialFiles( trialId, destRemoteFolder+dir.getName(), description );
		}
		else
			throw new Exception( filesPath + " is not a directory. Cannot perform batch upload.");
		ToolsWCF.finalizeCall();
	}
	
	

	/*==========================================================================
	 * 	BasicUpdateServiceWCF
	 *==========================================================================
	 */	
	
	public int createPerformer(String name, String surname) throws Exception
	{
		try{
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "createPerformer", this );

			PerformerData performerData = new PerformerData();
			performerData.setName( name );
			performerData.setSurname( surname );
	
			return port.createPerformer(performerData);
		} 
		catch ( IBasicUpdatesWSCreatePerformerUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
	public int createSession(int performerID, int [] sessionGroupID, String sessionDescription, int labID, int userID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
	{
		try {
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "createSession", this );

			ArrayOfInt sessionGroupIDs = new ArrayOfInt();
			for (int s: sessionGroupID)
				sessionGroupIDs.getInt().add(s);
				
			return port.createSession(userID, labID, motionKindName, performerID, sessionDate, sessionDescription, sessionGroupIDs);
		} 
		catch ( IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
	public int createTrial(int sessionID, String trialDescription, int trialDuration ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "createTrial", this );
			
			return port.createTrial(sessionID, trialDescription, trialDuration);
		} 
		catch ( IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}


	public int defineTrialSegment(int trialID, String segmentName, int startTime, int endTime ) throws Exception
	{
		try{	
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "defineTrialSegment", this );

			return port.defineTrialSegment(trialID, segmentName, startTime, endTime);
		} 
		catch ( IBasicUpdatesWSDefineTrialSegmentUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
	public void setSessionAttribute(int sessionID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "setSessionAttribute", this );

			port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
			ToolsWCF.finalizeCall();
		} 
		catch ( IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}


	public void setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "setTrialAttribute", this );

			port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
		} 
		catch ( IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}


	public void setPerformerAttribute(int performerID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try {
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "setPerformerAttribute", this );
			port.setPerformerAttribute(performerID, attributeName, attributeValue, update);
		
		} catch (IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
	public void setSegmentAttribute(int segmentID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{	
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "setPerformerAttribute", this );

			port.setSegmentAttribute(segmentID, attributeName, attributeValue, update);			
			ToolsWCF.finalizeCall();
		} catch (IBasicUpdatesWSSetSegmentAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}


	public void setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{	
			IBasicUpdatesWS port = ToolsWCF.getBasicUpdateServicePort( "setPerformerAttribute", this );

			port.setFileAttribute(fileID, attributeName, attributeValue, update);			
			ToolsWCF.finalizeCall();
		} catch (IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	
	/*==========================================================================
	 * 	AuthorizationWS
	 *==========================================================================
	 */	

	public boolean checkUserAccount() throws Exception
	{
		try {
			IAuthorizationWS port = ToolsWCF.getAuthorizationServicePort( "checkUserAccount", this );
			return port.checkUserAccount();
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	public void createUserAccount(String firstName, String lastName) throws Exception
	{
		try {
			IAuthorizationWS port = ToolsWCF.getAuthorizationServicePort( "createUserAccount", this );
			port.createUserAccount(firstName, lastName);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	public void grantSessionPrivileges(String grantedUserLogin, String grantedUserDomain, int sessionID, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ToolsWCF.getAuthorizationServicePort( "grantSessionPrivileges", this );
			port.grantSessionPrivileges(grantedUserLogin, grantedUserDomain, sessionID, writePrivilege);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}

	public void removeSessionPrivileges(String grantedUserLogin, String grantedUserDomain, int sessionID, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ToolsWCF.getAuthorizationServicePort( "removeSessionPrivileges", this );
			port.removeSessionPrivileges(grantedUserLogin, grantedUserDomain, sessionID);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ToolsWCF.finalizeCall();
		}
	}
}
