package motion.database.ws;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.TextMessageListener;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericResult;
import motion.database.model.Measurement;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;
import motion.database.model.User;
import motion.database.model.UserBasket;
import motion.database.model.UserPrivileges;
import motion.database.ws.administrationWCF.IAdministrationWS;
import motion.database.ws.administrationWCF.IAdministrationWSDefineAttributeAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.administrationWCF.IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.authorizationWCF.IAuthorizationWS;
import motion.database.ws.authorizationWCF.IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage;
import motion.database.ws.authorizationWCF.IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfString;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.GenericUniformAttributesQueryResult;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGenericQueryUniformXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionLabelQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListAttributeGroupsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListAttributesDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListEnumValuesQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListLabPerformersWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListLabSessionsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListMotionKindsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListPerformerSessionsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListPerformersWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListSessionGroupsDefinedQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListSessionTrialsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSListTrialMeasurementsWithAttributesXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.MeasurementConfDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.MeasurementDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerSessionWithAttributesList;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesServiceWCF.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesServiceWCF.GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListEnumValuesResponse.ListEnumValuesResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesServiceWCF.SessionGroupDefinitionList.SessionGroupDefinition;
import motion.database.ws.basicUpdatesServiceWCF.ArrayOfInt;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateMeasurementConfigurationUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateMeasurementUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreatePerformerUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.PerformerData;
import motion.database.ws.fileStoremanServiceWCF.FileData;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSCreateBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSRemoveEntityFromBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.BasketDefinitionList.BasketDefinition;
import motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult;
import motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult;
import motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult;
import motion.database.ws.userPersonalSpaceWCF.ListUserBasketsResponse.ListUserBasketsResult;

import com.zehon.BatchTransferProgress;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;


public class DatabaseConnection2 implements DatabaseProxy {

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

	enum ConnectionState{ INITIALIZED, CONNECTED, ABORTED, CLOSED, UNINITIALIZED }

	private static final String entity = null;;
	
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
	
	public String getConnectionInfo()
	{
		return this.wsCredentials.userName + "@" + this.ftpsCredentials.address;
	}
	
	
	public void setFTPSCredentials(String address, String userName, String password)
	{
		log.info("Setting FTP credentials for:" + userName + "@" + address);
		this.ftpsCredentials.setAddress(address);
		this.ftpsCredentials.setCredentials(userName, password, null);
	}

	
	public DatabaseConnection2(Logger log)
	{
		this.log = log;
		this.state = ConnectionState.UNINITIALIZED;
	};

	
	public void registerStateMessageListener(TextMessageListener listener) {
	
		ConnectionTools2.registerActionListener(listener);
	}

/*==========================================================================
 * 	BasicQueriesServiceWCF
 *==========================================================================
 */	
	
	
////////////////////////////////////////////////////////////////////////////
//	Generic Result 
	
	public  List<GenericResult> execGenericQuery(ArrayList<FilterPredicate> filterPredicates, String[] p_entitiesToInclude) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "execGenericQuery", this );
			
			ArrayOfString entitiesToInclude = new ArrayOfString();
			for( String s : p_entitiesToInclude )
				entitiesToInclude.getString().add( s );
			
			ArrayOfFilterPredicate arrayOfFilterPredicate = new ArrayOfFilterPredicate();
			arrayOfFilterPredicate.getFilterPredicate().addAll(filterPredicates);
			
			GenericQueryUniformXMLResult result = port.genericQueryUniformXML( 
					arrayOfFilterPredicate,	entitiesToInclude );
	
			
			DbElementsList<GenericResult> output = new DbElementsList<GenericResult>();
			GenericUniformAttributesQueryResult ss = result.getGenericUniformAttributesQueryResult();
			for (Attributes aa : ss.getAttributes() )
				output.add( ConnectionTools2.transformGenericAttributes( aa, new GenericResult() ) );
			
			return output;
		}
		catch(IBasicQueriesWSGenericQueryUniformXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}
	

////////////////////////////////////////////////////////////////////////////
//	Attributes 

	
	public HashMap<String, String> listAttributesDefined(String group, String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listAttributesDefined", this );
	
			ListAttributesDefinedResult result = port.listAttributesDefined( group, entityKind );
			
			HashMap<String, String> output = new HashMap<String, String>();
			if (result != null && result.getAttributeDefinitionList() != null)
				for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
				{
					output.put( a.getAttributeName(), a.getAttributeType() );
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
			ConnectionTools2.finalizeCall();
		}
	}

	
	public List<String> listEnumValues(String attributeName, String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listEnumValues", this );
	
			ListEnumValuesResult result = port.listEnumValues(attributeName, entityKind);
			
			if (result != null && result.getEnumValueList() != null && result.getEnumValueList().getEnumValue() != null)
				return result.getEnumValueList().getEnumValue();
			return
				new ArrayList<String>();
		}
		catch(IBasicQueriesWSListEnumValuesQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listGrouppedAttributesDefined", this );
	
			ListAttributesDefinedResult result = port.listAttributesDefined( "_ALL", entityKind);
			
			HashMap<String, EntityAttributeGroup> output = new HashMap<String, EntityAttributeGroup>();
	
			if ( result!=null && result.getAttributeDefinitionList() != null)
				for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
				{	
					EntityAttributeGroup group = output.get( a.getAttributeGroupName() );
					if (group == null)
					{
						group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
						output.put( a.getAttributeGroupName(), group );
					}
					EntityAttribute attr = new EntityAttribute( a.getAttributeName(), null, a.getAttributeGroupName(), a.getAttributeType() );
					attr.unit = a.getUnit();
					attr.subtype = a.getSubtype();
					if (a.getEnumValues() != null) {
						attr.enumValues = a.getEnumValues().getValue();
					}
					group.add( attr );
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
			ConnectionTools2.finalizeCall();
		}
	}

	
	
	
////////////////////////////////////////////////////////////////////////////
//	Attribute Groups
	
	
	public Vector<String> listAttributeGroupsDefined(String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listAttributeGroupsDefined", this );
			
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
			ConnectionTools2.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Motion Kinds

	
	public Vector<MotionKind> listMotionKindsDefined() throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listMotionKindsDefined", this );
			
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
			ConnectionTools2.finalizeCall();
		}
}


	
////////////////////////////////////////////////////////////////////////////
//	Performer

	
	public Performer getPerformerById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getPerformerById", this );
		
			GetPerformerByIdXMLResult result = port.getPerformerByIdXML(id);
			PerformerDetailsWithAttributes s = result.getPerformerDetailsWithAttributes();
	
			return ConnectionTools2.transformPerformerDetails(s);
		}
		catch(IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage e)
		{
			DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public  DbElementsList<Performer> listLabPerformersWithAttributes(int labID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listLabPerformersWithAttributes", this );
	
			ListLabPerformersWithAttributesXMLResult result = port.listLabPerformersWithAttributesXML(labID);
	
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			if (result.getLabPerformerWithAttributesList() != null) 
				for ( PerformerDetailsWithAttributes s : result.getLabPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
						output.add( ConnectionTools2.transformPerformerDetails(s) );
			
			return output;
		}
		catch(IBasicQueriesWSListLabPerformersWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public  DbElementsList<Performer> listPerformersWithAttributes() throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listPerformersWithAttributes", this );
	
			ListPerformersWithAttributesXMLResult result = port.listPerformersWithAttributesXML();
	
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for ( PerformerDetailsWithAttributes s : result.getPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
				output.add( ConnectionTools2.transformPerformerDetails(s) );
			
			return output;
		}
		catch(IBasicQueriesWSListPerformersWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session
	
	
	public Session getSessionById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getSessionById", this );
		
			GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
			SessionDetailsWithAttributes s = result.getSessionDetailsWithAttributes();
	
			return ConnectionTools2.transformSessionDetails(s);
		}
		catch(IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}


	public  String getSessionLabel(int sessionID) throws Exception
	{
		try{	
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getSessionLabel", this );
		
			return port.getSessionLabel( sessionID );
		}
		catch(IBasicQueriesWSGetSessionLabelQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public  DbElementsList<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listPerformerSessionsWithAttributes", this );
		
			ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			if (result != null)
			{
				PerformerSessionWithAttributesList ss = result.getPerformerSessionWithAttributesList();
				for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
					output.add( ConnectionTools2.transformSessionDetails(s) );
			}
			return output;
		}
		catch(IBasicQueriesWSListPerformerSessionsWithAttributesXMLQueryExceptionFaultFaultMessage e)
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
	{
		try {
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listLabSessionsWithAttributes", this );

			ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			if (result != null && result.getLabSessionWithAttributesList() != null )
				for ( motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes s : result.getLabSessionWithAttributesList().getSessionDetailsWithAttributes() )
					output.add( ConnectionTools2.transformSessionDetails(s) );
			
			return output;
		} 
		catch (IBasicQueriesWSListLabSessionsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session Groups
	
	public Vector<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionGroupsDefined", this );
			
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
			ConnectionTools2.finalizeCall();
		}
	}


	

////////////////////////////////////////////////////////////////////////////
//	Trial

	
	public Trial getTrialById(int id) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getTrialById", this );
	
			GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
			TrialDetailsWithAttributes s = result.getTrialDetailsWithAttributes();
	
			ConnectionTools2.finalizeCall();
			return ConnectionTools2.transformTrialDetails(s);
		} 
		catch (IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}


	public  DbElementsList<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionTrialsWithAttributes", this );
		
			ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
			DbElementsList<Trial> output = new DbElementsList<Trial>();
			
			for ( TrialDetailsWithAttributes s : result.getSessionTrialWithAttributesList().getTrialDetailsWithAttributes() )
				output.add( ConnectionTools2.transformTrialDetails(s) );
				
			ConnectionTools2.finalizeCall();
			return output;
		} 
		catch (IBasicQueriesWSListSessionTrialsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}


	public  DbElementsList<Measurement> listTrialMeasurementsWithAttributes(int trialID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionTrialsWithAttributes", this );
		
			ListTrialMeasurementsWithAttributesXMLResult result = port.listTrialMeasurementsWithAttributesXML(trialID);
			DbElementsList<Measurement> output = new DbElementsList<Measurement>();
			
			for ( MeasurementDetailsWithAttributes s : result.getTrialMeasurementWithAttributesList().getMeasurementDetailsWithAttributes() )
				output.add( ConnectionTools2.transformMeasurementDetails(s) );
				
			ConnectionTools2.finalizeCall();
			return output;
		} 
		catch (IBasicQueriesWSListTrialMeasurementsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}
	
	
	public  DbElementsList<MeasurementConfiguration> listTrialMeasurementConfigurationsWithAttributes(int trialID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionTrialsWithAttributes", this );
		
			ListMeasurementConfigurationsWithAttributesXMLResult result = port.listMeasurementConfigurationsWithAttributesXML();
			DbElementsList<MeasurementConfiguration> output = new DbElementsList<MeasurementConfiguration>();
			
			for ( MeasurementConfDetailsWithAttributes s : result.getMeasurementConfListWithAttributesList().getMeasurementConfDetailsWithAttributes() )
				output.add( ConnectionTools2.transformMeasurementConfigurationDetails(s) );
				
			ConnectionTools2.finalizeCall();
			return output;
		} 
		catch (IBasicQueriesWSListTrialMeasurementsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}
	
////////////////////////////////////////////////////////////////////////////
//	File
	
//	
//	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
//	{
//		try{
//			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionFiles", this );
//		
//			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, EntityKind.session.name());
//	
//			return ConnectionTools2.transformListOfFiles(result);
//		} 
//		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
//		{
//			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
//			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
//		}
//		finally
//		{
//			ConnectionTools2.finalizeCall();
//		}
//	}
//
//	
//
//	public  DbElementsList<DatabaseFile> listTrialFiles(int trialID) throws Exception
//	{
//		try{
//			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listTrialFiles", this );
//	
//			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, EntityKind.trial.name());
//			
//			return ConnectionTools2.transformListOfFiles(result);
//		} 
//		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
//		{
//			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
//			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
//		}
//		finally
//		{
//			ConnectionTools2.finalizeCall();
//		}
//	}
//
//	
//
//	public  DbElementsList<DatabaseFile> listPerformerFiles(int performerID) throws Exception
//	{
//		try{
//			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listPerformerFiles", this );
//	
//			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, EntityKind.performer.name());
//			
//			return ConnectionTools2.transformListOfFiles(result);
//		} 
//		catch (IBasicQueriesWSListFilesWithAttributesXMLQueryExceptionFaultFaultMessage e) 
//		{
//			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
//			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
//		}
//		finally
//		{
//			ConnectionTools2.finalizeCall();
//		}
//	}
	
	
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

		FileTransferListener listener;
		int progress;
		float step;
		
		BatchTransferProgressObserver( FileTransferListener listener, int steps )
		{
			this.listener = listener;
			progress = 0;
			step = 100 / steps / 2;
		}
		
		@Override
		public void transferComplete(String arg0) {
			progress += step;
			listener.transferStepPercent( progress );
			System.out.println( "Transfer finished: " + arg0);
		}

		@Override
		public void transferError(String arg0, Throwable arg1) {
			System.out.println( "Transfer error: " + arg0);
		}

		@Override
		public void transferStart(String arg0) {
			progress += step;
			listener.transferStepPercent( progress );
			System.out.println( "Transfer started: " + arg0);
		}
	}
	
////////////////////////////////////////////////////////////////////////////
//	Download
	
	
	public  String downloadFile(int fileID, String destLocalFolder, FileTransferListener transferListener, boolean recreateFolder) throws Exception
	{
			IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "downloadFile", this );
	
			FileData file = port.retrieveFile(fileID);
			
			File remoteFile = new File ( file.getFileLocation() );

			if (recreateFolder)
			{
				destLocalFolder += "/"+ file.getSubdirPath();
				File localDest = new File(destLocalFolder);
				localDest.mkdirs();
			}
			getFile( remoteFile.getName(), remoteFile.getParent(), 
					this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password,
					destLocalFolder, transferListener );

			port.downloadComplete(fileID, file.getFileLocation());
			
			ConnectionTools2.finalizeCall();
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
			ConnectionTools2.finalizeCall();
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
			ConnectionTools2.finalizeCall();
		}
	}

	
	public void uploadAttributeFile(int resourceId, EntityKind kind, String attributeName, String description, String localFilePath, FileTransferListener listener) throws Exception
	{

		IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "uploadAttributeFile", this );

		String destRemoteFolder = getUniqueFolderName();
		putFile(localFilePath, destRemoteFolder, listener);			
		    
		if (!fileTransferCancelled)
				port.storeAttributeFile(resourceId, kind.name(), attributeName, destRemoteFolder, description, new File(localFilePath).getName() );
		
		System.out.println( destRemoteFolder + "   " + localFilePath );
		
		ConnectionTools2.finalizeCall();
	}

	
	

	/*==========================================================================
	 * 	BasicUpdateServiceWCF
	 *==========================================================================
	 */	
	
	public int createPerformer(String name, String surname) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createPerformer", this );

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
			ConnectionTools2.finalizeCall();
		}
	}

	
	public int createSession(int [] sessionGroupID, String sessionDescription, int labID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
	{
		try {
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createSession", this );

			ArrayOfInt sessionGroupIDs = new ArrayOfInt();
			for (int s: sessionGroupID)
				sessionGroupIDs.getInt().add(s);
				
			return port.createSession(labID, motionKindName, sessionDate, sessionDescription, sessionGroupIDs);
		} 
		catch ( IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public int createTrial(int sessionID, String trialDescription, int trialDuration ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createTrial", this );
			
			return port.createTrial(sessionID, trialDescription, trialDuration);
		} 
		catch ( IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	public int createMeasurement(int trialID, int measurementConfigurationID ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createMeasurement", this );
			
			return port.createMeasurement(trialID, measurementConfigurationID);
		} 
		catch ( IBasicUpdatesWSCreateMeasurementUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public int createMeasurementConfiguration(String name, String description ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createMeasurementConfiguration", this );
			
			return port.createMeasurementConfiguration(name, description);
		} 
		catch ( IBasicUpdatesWSCreateMeasurementConfigurationUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Deprecated
	public void setSessionAttribute(int sessionID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setSessionAttribute", this );

			port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
		} 
		catch ( IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Deprecated
	public void setSessionAttribute(int sessionID, EntityAttribute a, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setSessionAttribute", this );

			port.setSessionAttribute(sessionID, a.name, a.value.toString(), update);			
		} 
		catch ( IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	public void setEntityAttribute(int ID, EntityKind kind, EntityAttribute a, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setEntityAttribute", this );

			if (a == null)
			{
				// TODO: add clearing an attribute 
			}
			else
			{
				if (a.value == null)
					a.emptyValue();
				kind.setEntityAttribute( port, ID, a, update);			
			}
		} 
		catch ( IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	

	@Deprecated
	public void setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setTrialAttribute", this );

			port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
		} 
		catch ( IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	@Deprecated
	public void setPerformerAttribute(int performerID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try {
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setPerformerAttribute", this );
			port.setPerformerAttribute(performerID, attributeName, attributeValue, update);
		
		} catch (IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	@Deprecated
	public void setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{	
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setPerformerAttribute", this );

			port.setFileAttribute(fileID, attributeName, attributeValue, update);			
		} catch (IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	/*==========================================================================
	 * 	AuthorizationWS
	 *==========================================================================
	 */	

	public boolean checkUserAccount() throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "checkUserAccount", this );
			return port.checkUserAccount();
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	public void createUserAccount(String firstName, String lastName) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "createUserAccount", this );
			port.createUserAccount(firstName, lastName);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	public DbElementsList<User> listUsers() throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "listUsers", this );
			return ConnectionTools2.transformListOfUsers( port.listUsers() );
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public void grantSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "grantSessionPrivileges", this );
			port.grantSessionPrivileges(grantedUserLogin, sessionID, writePrivilege);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	public void removeSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "removeSessionPrivileges", this );
			port.removeSessionPrivileges(grantedUserLogin, sessionID);
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	
	
	public void setSessionPrivileges(int sessionID, boolean readPrivilege, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "removeSessionPrivileges", this );
			port.alterSessionVisibility(sessionID, readPrivilege, writePrivilege);
			
		
		} catch (IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	public DbElementsList<UserPrivileges> listSessionPrivileges(int sessionID) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "listSessionPrivileges", this );
			return ConnectionTools2.transformListOfPrivileges( sessionID, port.listSessionPrivileges(sessionID) );
		
		} catch (IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	/*==========================================================================
	 * 	UserPersonalSpaceWS
	 *==========================================================================
	 */	

	public void addEntityToBasket(String basketName, int resourceID, String entity) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "addEntityToBasket", this );
			port.addEntityToBasket(basketName, resourceID, entity);
		
		} catch (IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	

	public void removeEntityFromBasket(String basketName, int resourceID, String entityName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "removeEntityFromoBasket", this );
			port.removeEntityFromBasket(basketName, resourceID, entityName);
		
		} catch (IUserPersonalSpaceWSRemoveEntityFromBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public void createBasket(String basketName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "createBasket", this );
			port.createBasket(basketName);
		
		} catch (IUserPersonalSpaceWSCreateBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	public void removeBasket(String basketName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "removeBasket", this );
			port.removeBasket(basketName);
		
		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	public void updateStoredFilters(motion.database.ws.userPersonalSpaceWCF.ArrayOfFilterPredicate filter) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "updateStoredFilters", this );
			port.updateStoredFilters(filter);
		
		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	
	
	public List<motion.database.ws.userPersonalSpaceWCF.FilterList.FilterPredicate> listStoredFilters() throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listStoredFilters", this );
			return port.listStoredFilters().getFilterList().getFilterPredicate();
		
		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public DbElementsList<UserBasket>  listUserBaskets() throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listUserBaskets", this );
			ListUserBasketsResult result = port.listUserBaskets();
		
			DbElementsList<UserBasket> output = new DbElementsList<UserBasket>();
			
			if (result.getBasketDefinitionList() != null) 
				for ( BasketDefinition s : result.getBasketDefinitionList().getBasketDefinition() )
						output.add( ConnectionTools2.transformBasketDefinitionUPS(s) );
			
			return output;

		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public DbElementsList<Performer>  listBasketPerformersWithAttributes(String basketName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listBasketPerformersWithAttributes", this );
			ListBasketPerformersWithAttributesXMLResult result = port.listBasketPerformersWithAttributesXML(basketName);
		
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			if (result.getBasketPerformerWithAttributesList() != null) 
				for ( motion.database.ws.userPersonalSpaceWCF.PerformerDetailsWithAttributes s : result.getBasketPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
						output.add( ConnectionTools2.transformPerformerDetailsUPS(s) );
			
			return output;

		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public DbElementsList<Session>  listBasketSessionsWithAttributes(String basketName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listBasketSessionsWithAttributes", this );
			ListBasketSessionsWithAttributesXMLResult result = port.listBasketSessionsWithAttributesXML(basketName);
		
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			if (result.getBasketSessionWithAttributesList() != null) 
				for ( motion.database.ws.userPersonalSpaceWCF.SessionDetailsWithAttributes s : result.getBasketSessionWithAttributesList().getSessionDetailsWithAttributes() )
						output.add( ConnectionTools2.transformSessionDetailsUPS(s) );
			
			return output;

		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	public DbElementsList<Trial>  listBasketTrialsWithAttributes(String basketName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listBasketTrialsWithAttributes", this );
			ListBasketTrialsWithAttributesXMLResult result = port.listBasketTrialsWithAttributesXML(basketName);
		
			DbElementsList<Trial> output = new DbElementsList<Trial>();
			
			if (result.getBasketTrialWithAttributesList() != null) 
				for ( motion.database.ws.userPersonalSpaceWCF.TrialDetailsWithAttributes s : result.getBasketTrialWithAttributesList().getTrialDetailsWithAttributes() )
						output.add( ConnectionTools2.transformTrialDetailsUPS(s) );
			
			return output;

		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	/*==========================================================================
	 * 	AdministrationWS
	 *==========================================================================
	 */	
	
	
	public void  defineAttribute(String attributeName, String groupName, 
			String storageType, boolean isEnum, String pluginDescriptor, 
			String dataSubtype, String unit) throws Exception
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "defineAttribute", this );
			port.defineAttribute(attributeName, groupName, entity, storageType, isEnum, pluginDescriptor, dataSubtype, unit);

		} catch (IAdministrationWSDefineAttributeAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	public void  defineAttributeGroup(String groupName, String unit) throws Exception
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "defineAttributeGroup", this );
			port.defineAttriubeGroup(groupName, unit);

		} catch (IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	

	/**
	 * To be changed.
	 * 
	 * @param a
	 * @param attributeName
	 * @param groupName
	 * @param entity
	 * @param value
	 * @param clearExisting
	 * @throws Exception
	 */
	public void  addAttributeEnumValue(EntityAttribute a, String attributeName, String groupName, String entity, String value, boolean clearExisting ) throws Exception
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "defineAttribute", this );
			port.addAttributeEnumValue(attributeName, groupName, entity, value, clearExisting);

		} catch (IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

}
