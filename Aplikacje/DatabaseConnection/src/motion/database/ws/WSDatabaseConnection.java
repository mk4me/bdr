package motion.database.ws;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.PerformerConfiguration;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.SessionPrivileges;
import motion.database.model.SessionValidationInfo;
import motion.database.model.Trial;
import motion.database.model.User;
import motion.database.model.UserBasket;
import motion.database.ws.administrationWCF.IAdministrationWS;
import motion.database.ws.administrationWCF.IAdministrationWSDefineAttributeAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.administrationWCF.IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.administrationWCF.IAdministrationWSRemoveAttributeAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.administrationWCF.IAdministrationWSRemoveAttributeGroupAdministrationOperationExceptionFaultFaultMessage;
import motion.database.ws.authorizationWCF.IAuthorizationWS;
import motion.database.ws.authorizationWCF.IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage;
import motion.database.ws.authorizationWCF.IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfFileNameEntry;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfString;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.FileNameEntry;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.GenericUniformAttributesQueryResult;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGenericQueryUniformXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerConfigurationByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionContentQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionLabelQueryExceptionFaultFaultMessage;
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
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSValidateSessionFileSetQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.MeasurementConfDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerConfDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerSessionWithAttributesList;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionGroupDefinition;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesServiceWCF.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesServiceWCF.GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionContentResponse.GetSessionContentResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListEnumValuesResponse.ListEnumValuesResult;
import motion.database.ws.basicQueriesServiceWCF.ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesServiceWCF.ValidateSessionFileSetResponse.ValidateSessionFileSetResult;
import motion.database.ws.basicUpdatesServiceWCF.ArrayOfInt;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSAssignPerformerToSessionUpdateExceptionFaultFaultMessage;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage;
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
import motion.database.ws.userPersonalSpaceWCF.ArrayOfAttributeGroupViewSetting;
import motion.database.ws.userPersonalSpaceWCF.ArrayOfAttributeViewSetting;
import motion.database.ws.userPersonalSpaceWCF.AttributeGroupViewSetting;
import motion.database.ws.userPersonalSpaceWCF.AttributeViewSetting;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSCreateBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSRemoveEntityFromBasketUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSUpdateViewConfigurationUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration;
import motion.database.ws.userPersonalSpaceWCF.AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList.AttributeView;
import motion.database.ws.userPersonalSpaceWCF.BasketDefinitionList.BasketDefinition;
import motion.database.ws.userPersonalSpaceWCF.ListUserBasketsResponse.ListUserBasketsResult;
import motion.database.ws.userPersonalSpaceWCF.ListViewConfigurationResponse.ListViewConfigurationResult;

import com.zehon.BatchTransferProgress;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;


public class WSDatabaseConnection implements DatabaseProxy {

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

	//private static final String entity = null;;
	
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
				
				log.fine("[Serwer pyta o hasło. (Odpowiedziałem)]" );
				
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

	
	public WSDatabaseConnection(Logger log)
	{
		this.log = log;
		this.state = ConnectionState.UNINITIALIZED;
	};

	
	@Override
	public void registerStateMessageListener(TextMessageListener listener) {
	
		ConnectionTools2.registerActionListener(listener);
	}

/*==========================================================================
 * 	BasicQueriesServiceWCF
 *==========================================================================
 */	

	@Override
	public GenericDescription<?> getById(int id, EntityKind kind) throws Exception
	{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getById:" + kind, this );
	
			return kind.getByID(port, id);	
	}

	
////////////////////////////////////////////////////////////////////////////
//	Generic Result 
	
	@Override
	public  List<GenericDescription> execGenericQuery(ArrayList<FilterPredicate> filterPredicates, String[] p_entitiesToInclude) throws Exception
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
	
			
			DbElementsList<GenericDescription> output = new DbElementsList<GenericDescription>();
			
			GenericUniformAttributesQueryResult ss = result.getGenericUniformAttributesQueryResult();

			EntityKind resultKind = null;
			for (Attributes aa : ss.getAttributes() )
			{
				resultKind = ConnectionTools2.inferEntityType( aa ); 
				if ( resultKind != null )
					break;
			}
			if (resultKind == null)
				resultKind = EntityKind.result;
			
			
			for (Attributes aa : ss.getAttributes() )
				output.add( ConnectionTools2.transformGenericAttributes( aa, resultKind.newEntity() ) );
			
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

	
	@Override
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

	
	@Override
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

	
	@Override
	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listGrouppedAttributesDefined", this );

			HashMap<String, EntityAttributeGroup> output = new HashMap<String, EntityAttributeGroup>();
			ListAttributeGroupsDefinedResult resultg = port.listAttributeGroupsDefined(entityKind);
			if ( resultg!=null && resultg.getAttributeGroupDefinitionList() != null)
				for (AttributeGroupDefinition a : resultg.getAttributeGroupDefinitionList().getAttributeGroupDefinition() )
				{
					EntityAttributeGroup group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
					if (a.getShow()!=null)
						group.isVisible = a.getShow()==1;
				output.put( group.name, group);
				}
			
			ListAttributesDefinedResult result = port.listAttributesDefined( "_ALL", entityKind);
	
			if ( result!=null && result.getAttributeDefinitionList() != null)
				for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
				{	
					EntityAttributeGroup group = output.get( a.getAttributeGroupName() );
					if (group == null)
					{
						group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
						if (a.getShow()!=null)
							group.isVisible = a.getShow()==1;
						output.put( a.getAttributeGroupName(), group );
					}
					EntityAttribute attr = new EntityAttribute( a.getAttributeName(), group.kind, null, a.getAttributeGroupName(), a.getAttributeType() );
					attr.unit = a.getUnit();
					attr.isEnum = a.getAttributeEnum()==1;
					if (a.getShow() != null)
						attr.isVisible = a.getShow()==1;
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

	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(EntityKind kind) throws Exception
	{
		return listGrouppedAttributesDefined(kind.name());
	}
	
	
	
////////////////////////////////////////////////////////////////////////////
//	Attribute Groups
	
	
	@Override
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

	
	@Override
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

	
	@Override
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

	
	@Override
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


	@Override
	public  DbElementsList<Performer> listSessionPerformersWithAttributes(int sessionID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listPerformersWithAttributes", this );
	
			ListSessionPerformersWithAttributesXMLResult result = port.listSessionPerformersWithAttributesXML(sessionID);
	
			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for ( PerformerDetailsWithAttributes s : result.getSessionPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
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
// Performer Configuration 

	@Override
	public  DbElementsList<PerformerConfiguration> listSessionPerformerConfigurations(int sessionID) throws Exception
	{
		try{	
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getSessionPerformerConfiguration", this );
		
			DbElementsList<PerformerConfiguration> list = new DbElementsList<PerformerConfiguration>();

			for (PerformerConfDetailsWithAttributes c : port.listSessionPerformerConfsWithAttributesXML(sessionID).getSessionPerformerConfWithAttributesList().getPerformerConfDetailsWithAttributes() )
				list.add( ConnectionTools2.transformPerformerConfigurationDetails( c ) );
			
			return list;
			
		}
		catch(IBasicQueriesWSGetPerformerConfigurationByIdXMLQueryExceptionFaultFaultMessage e)
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
	

	@Override
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

	
	@Override
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

	
	@Override
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


	@Override
	public  Session getSessionContent(int sessionID) throws Exception
	{
		try {
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "getSessionContent", this );
			Session output = null;
			
			GetSessionContentResult result = port.getSessionContent(sessionID);
			if (result != null && result.getSessionContent()!=null )
				output = ConnectionTools2.transformSessionContent( result.getSessionContent() );
			
			return output;
		} 
		catch (IBasicQueriesWSGetSessionContentQueryExceptionFaultFaultMessage e) 
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
	
	@Override
	public DbElementsList<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionGroupsDefined", this );
			
			ListSessionGroupsDefinedResult result = port.listSessionGroupsDefined();
			
			DbElementsList<SessionGroup> output = new DbElementsList<SessionGroup>();
	
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


	@Override
	public DbElementsList<Session> listGroupSessions(int sessionGroupID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listGroupSessions", this );
			
			ListGroupSessionsWithAttributesXMLResult result = port.listGroupSessionsWithAttributesXML(sessionGroupID);
			
			DbElementsList<Session> output = new DbElementsList<Session>();
	
			for (SessionDetailsWithAttributes a : result.getGroupSessionWithAttributesList().getSessionDetailsWithAttributes() )
				output.add( ConnectionTools2.transformSessionDetails(a) );
			
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
	
	@Override
	public DbElementsList<SessionGroup> listSessionSessionGroups(int sessionID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listSessionSessionGroups", this );
			
			ListSessionSessionGroupsResult result = port.listSessionSessionGroups(sessionID);
			
			DbElementsList<SessionGroup> output = new DbElementsList<SessionGroup>();
	
			for (SessionGroupDefinition a : result.getSessionSessionGroupList().getSessionGroupDefinition() )
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

	
	@Override
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

	
////////////////////////////////////////////////////////////////////////////
//	Measurements
	/* // 16.07.2011
	@Override
	public  DbElementsList<Measurement> listTrialMeasurementsWithAttributes(int trialID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listTrialsMeasurementsWithAttributes", this );
		
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
	*/
	/* // 16.07.2011
	@Override
	public  DbElementsList<Measurement> listMeasurementConfMeasurementsWithAttributes(int measurementConfID) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listMeasurementConfMeasurementsWithAttributes", this );
		
			ListMeasurementConfMeasurementsWithAttributesXMLResult result = port.listMeasurementConfMeasurementsWithAttributesXML(measurementConfID);
			DbElementsList<Measurement> output = new DbElementsList<Measurement>();
			
			for ( MeasurementDetailsWithAttributes s : result.getMeasurementConfMeasurementWithAttributesList().getMeasurementDetailsWithAttributes() )
				output.add( ConnectionTools2.transformMeasurementDetails(s) );
				
			ConnectionTools2.finalizeCall();
			return output;
		} 
		catch (IBasicQueriesWSListMeasurementConfMeasurementsWithAttributesXMLQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}
	*/

////////////////////////////////////////////////////////////////////////////
//	MesurementsConfiguration


	@Override
	public String [] listMeasurementConfKinds(){
		return new String[]{"mocap", "GRF", "video", "sEMG"};
	}

	
	@Override
	public  DbElementsList<MeasurementConfiguration> listMeasurementConfigurationsWithAttributes() throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listMeasurementConfigurationsWithAttributes", this );
		
			ListMeasurementConfigurationsWithAttributesXMLResult result = port.listMeasurementConfigurationsWithAttributesXML();
			DbElementsList<MeasurementConfiguration> output = new DbElementsList<MeasurementConfiguration>();
			if (result.getMeasurementConfWithAttributesList() != null) {
				for ( MeasurementConfDetailsWithAttributes s : result.getMeasurementConfWithAttributesList().getMeasurementConfDetailsWithAttributes() )
					output.add( ConnectionTools2.transformMeasurementConfigurationDetails(s) );
			}
				
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
	
	@Override
	public  DbElementsList<DatabaseFile> listFiles( int resourceID, EntityKind kind ) throws Exception
	{
		try{
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "listFiles:" + kind, this );
			DbElementsList<DatabaseFile> result = kind.listFiles(port, resourceID);
			return result;
		} 
		catch (Exception e) 
		{
			throw ConnectionTools2.transformWSFaultMessage(e); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
////////////////////////////////////////////////////////////////////////////
//	Wizard (validation) 

	
	@Override
	public  SessionValidationInfo validateSessionFileSet(File [] paths) throws Exception
	{
		try {
			IBasicQueriesWS port = ConnectionTools2.getBasicQueriesPort( "validateSessionFileSet", this );
			Session output = null;
			LinkedList<String> errors = null; 
			
			ArrayOfFileNameEntry input = new ArrayOfFileNameEntry();
			for (File file : paths){
				if (file != null) {
					FileNameEntry fne = new FileNameEntry();
					fne.setName( file.getName() );
					input.getFileNameEntry().add( fne );
				}
			}
			ValidateSessionFileSetResult result = port.validateSessionFileSet( input );
			if (result != null && result.getFileSetValidationResult().getSessionContent()!=null )
				output = ConnectionTools2.transformSessionContent( result.getFileSetValidationResult().getSessionContent() );
			if (result != null && result.getFileSetValidationResult().getErrorList()!=null )
			{
				errors = new LinkedList<String>();
				for (String error : result.getFileSetValidationResult().getErrorList().getError() )
				{
					DatabaseConnection.log.info( "Session file set validation error: " + error  );
					errors.add( error );
				}
			}
			
			//uploadSessionFileSet(paths, null);
			
			return new SessionValidationInfo( output, errors );
		} 
		catch (IBasicQueriesWSValidateSessionFileSetQueryExceptionFaultFaultMessage e) 
		{
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
	public int uploadSessionFileSet(File[] paths, FileTransferListener listener) throws Exception
	{
		try {
			IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "uploadSessionFileSet", this );
			
			String destRemoteFolder = getUniqueFolderName();
			createRemoteFolder( destRemoteFolder, "" );
			for (File path : paths)
			{
				if ( path.isDirectory() )
				{
					int filesNo = path.list().length;
					createRemoteFolder( path.getName(), destRemoteFolder );
					FTPs.sendFolder( path.getAbsolutePath(), destRemoteFolder+path.getName(), 
							new BatchTransferProgressObserver(listener, filesNo), 
							ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
				}
				else
					putFileIntoExistingFolder(path.getAbsolutePath(), destRemoteFolder, listener);			
			
			}
			return port.createSessionFromFiles( destRemoteFolder );
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.getMessage(), e );
			throw e; 
		}
		finally
		{
			ConnectionTools2.finalizeCall();
		}
	}

	
	
	/*==========================================================================
	 * 	FileStoremanServiceWCF
	 *==========================================================================
	 */	

	@Override
	public void cancelCurrentFileTransfer()
	{
		fileTransferSupport.cancel();
		fileTransferCancelled = true;
	}


	@Override
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
	
	
	@Override
	public  String downloadFile(int fileID, String destLocalFolder, FileTransferListener transferListener, boolean recreateFolder) throws Exception
	{
		fileTransferCancelled = false;
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
			FileTransferListener transferListener) throws Exception {

		fileTransferSupport.resetDownloadListeners();
		fileTransferSupport.registerDownloadListener(transferListener);
		
		try {
			fileTransferSupport.getFile(remoteFileName, remoteFilePath, remoteFileName, destLocalFolder, address, userName, password );
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
			throw new Exception( e );
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

	protected void putFileIntoExistingFolder(String localFilePath, String destRemoteFolder, FileTransferListener listener) throws FileTransferException
	{
		fileTransferSupport.resetUploadListeners();
		if (listener != null)
			fileTransferSupport.registerUploadListener(listener);
		
		try {
			fileTransferSupport.putFile(localFilePath, destRemoteFolder, ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
		} finally
		{
			ConnectionTools2.finalizeCall();
		}
	}


	@Override
	public void setFileTypedAttribute(int resourceId, EntityAttribute attribute, int fileID, boolean update) throws Exception
	{
		if (attribute.kind == null)
			throw new Exception("Attribute without entity kind defined: " + attribute);

		IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setFileTypedAttribute", this );

		port.setFileTypedAttributeValue(resourceId, attribute.kind.name(), attribute.name, fileID, update);
		
		ConnectionTools2.finalizeCall();
	}

	
	@Override
	public void uploadFile(int resourceId, EntityKind kind, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		fileTransferCancelled = false;
		IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "uploadFile", this );

		String destRemoteFolder = getUniqueFolderName();
		createRemoteFolder( destRemoteFolder, "" );
		putFile(localFilePath, destRemoteFolder, listener);			
		if (!fileTransferCancelled)
				kind.storeFile( port, resourceId, destRemoteFolder, description, new File(localFilePath).getName() );
		
		//System.out.println( destRemoteFolder + "   " + localFilePath );
		
		ConnectionTools2.finalizeCall();
	}
	
	@Override
	public void uploadDirectory(int resourceId, EntityKind kind, String description, String filesPath, FileTransferListener listener) throws Exception
	{
		fileTransferCancelled = false;
		IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "uploadDirectory", this );

		String destRemoteFolder = getUniqueFolderName();

		File dir = new File(filesPath);
		if ( dir.isDirectory() )
		{
			int filesNo = dir.list().length;
			createRemoteFolder( dir.getName(), destRemoteFolder );
			FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(listener, filesNo), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
		
			kind.storeFiles(port, resourceId, destRemoteFolder, description);
		}
		else
			throw new Exception( filesPath + " is not a directory. Cannot perform batch upload.");
		
		ConnectionTools2.finalizeCall();
	}

	@Override
	public void uploadFilesDirectories(int resourceId, EntityKind kind, String description, File[] filesPath, FileTransferListener listener) throws Exception
	{
		fileTransferCancelled = false;
		IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "uploadFilesDirectories", this );
		

		for (File path : filesPath)
		{
			String destRemoteFolder = getUniqueFolderName();
			if ( path.isDirectory() )
			{
				int filesNo = path.list().length;
				createRemoteFolder( path.getName(), destRemoteFolder );
				FTPs.sendFolder( path.getAbsolutePath(), destRemoteFolder+path.getName(), new BatchTransferProgressObserver(listener, filesNo), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
			
				if (!fileTransferCancelled)
					kind.storeFiles(port, resourceId, destRemoteFolder, description);
			}
			else
			{
				putFile(path.getAbsolutePath(), destRemoteFolder, listener);			
			    
				if (!fileTransferCancelled)
						kind.storeFile( port, resourceId, destRemoteFolder, description, path.getName() );
			}
		}
		ConnectionTools2.finalizeCall();
	}

	@Override
	public void replaceFile(int resourceId, String localFilePath, FileTransferListener listener) throws Exception
	{
		fileTransferCancelled = false;
		IFileStoremanWS port = ConnectionTools2.getFileStoremanServicePort( "replaceFile", this );

		String destRemoteFolder = getUniqueFolderName();
		putFile(localFilePath, destRemoteFolder, listener);			
		if (!fileTransferCancelled)
				port.replaceFile( resourceId, destRemoteFolder, new File(localFilePath).getName() );
		
		ConnectionTools2.finalizeCall();
	}


	/*==========================================================================
	 * 	BasicUpdateServiceWCF
	 *==========================================================================
	 */	
	
	@Override
	public int createPerformer() throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createPerformer", this );

			PerformerData performerData = new PerformerData();
	
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


	@Override
	public int assignPerformerToSession(int sessionID, int performerID) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "assignPerformerToSession", this );

			return port.assignPerformerToSession(sessionID, performerID);
		} 
		catch ( IBasicUpdatesWSAssignPerformerToSessionUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
	public boolean assignSessionToGroup(int sessionID, int groupID) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "assignSessionToGroup", this );

			return port.assignSessionToGroup(sessionID, groupID);
		} 
		catch ( IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	/* // 16.07.2011
	@Override
	public boolean addPerformerToMeasurement(int performerID, int measurementID) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "addPerformerToMeasurement", this );

			return port.addPerformerToMeasurement(performerID, measurementID);
		} 
		catch ( IBasicUpdatesWSAddPerformerToMeasurementUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	*/
	
	@Override
	public int createSession(int [] sessionGroupID, String sessionName, String sessionTags, String sessionDescription, int labID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
	{
		try {
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createSession", this );

			ArrayOfInt sessionGroupIDs = new ArrayOfInt();
			for (int s: sessionGroupID)
				sessionGroupIDs.getInt().add(s);
				
			return port.createSession(labID, motionKindName, sessionDate, sessionName, sessionTags, sessionDescription, sessionGroupIDs);
		} 
		catch ( IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
	public int createTrial(int sessionID, String trialName, String trialDescription ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createTrial", this );
			
			return port.createTrial(sessionID, trialName, trialDescription);
		} 
		catch ( IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}


	@Override
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

	
	@Override
	public int createMeasurementConfiguration(String name, String kind, String description ) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "createMeasurementConfiguration", this );
			
			return port.createMeasurementConfiguration(name, kind, description);
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
	@Override
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
	@Override
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


	@Override
	public void clearEntityAttribute(int ID, EntityAttribute a) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "clearEntityAttribute:" + a , this );

			if (a != null)
			{
				port.clearAttributeValue( ID, a.name, a.kind.name() );			
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
	
	
	@Override
	public void setEntityAttribute(int ID, EntityAttribute a, boolean update) throws Exception
	{
		try{
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setEntityAttribute:" + a , this );

			if (a != null && a.kind != null )
			{
				if (a.value == null)
					a.emptyValue();
				a.kind.setEntityAttribute( port, ID, a, update);			
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
	@Override
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
	@Override
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
	@Override
	public void setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		try{	
			IBasicUpdatesWS port = ConnectionTools2.getBasicUpdateServicePort( "setFileAttribute", this );

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

	@Override
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

	@Override
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


	@Override
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

	
	@Override
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

	@Override
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
	
	
	@Override
	public void setSessionPrivileges(int sessionID, boolean readPrivilege, boolean writePrivilege) throws Exception
	{
		try {
			IAuthorizationWS port = ConnectionTools2.getAuthorizationServicePort( "setSessionPrivileges", this );
			port.alterSessionVisibility(sessionID, readPrivilege, writePrivilege);
			
		
		} catch (IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	@Override
	public SessionPrivileges listSessionPrivileges(int sessionID) throws Exception
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

	@Override
	public void addEntityToBasket(String basketName, int resourceID, String entityName) throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "addEntityToBasket", this );
			port.addEntityToBasket(basketName, resourceID, entityName);
		
		} catch (IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	

	@Override
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

	
	@Override
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


	@Override
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


	@Override
	public void readAttributeViewConfiguration() throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "readAttributeViewConfiguration", this );
			ListViewConfigurationResult r = port.listViewConfiguration();
		
			for ( AttributeGroupViewConfiguration v : r.getAttributeGroupViewConfigurationList().getAttributeGroupViewConfiguration() )
			{
				EntityKind kind = Enum.valueOf( EntityKind.class, v.getDescribedEntity() );
				for (EntityAttributeGroup g : kind.getGroupedAttributes()) {
					if (g.name.equals(v.getAttributeGroupName())) {
						if (v.getShow() != null) {
							if (v.getShow()==1)
								g.isVisible = true;
							else
								g.isVisible = false;
						}
						for ( AttributeView s : v.getAttributeViewList().getAttributeView())
						{
							for (EntityAttribute a : g) {
								if (a.name.equals(s.getAttributeName())) {
									if (s.getShow() != null) {
										if (s.getShow()==1)
											a.isVisible = true;
										else
											a.isVisible = false;
									}
								}
							}
						}
					}
				}
			}
			
		} catch (IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
	public void saveAttributeViewConfiguration() throws Exception
	{
		try {
			IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "saveAttributeViewConfiguration", this );
		
			ArrayOfAttributeGroupViewSetting groupView = new ArrayOfAttributeGroupViewSetting();
			ArrayOfAttributeViewSetting attributeView = new ArrayOfAttributeViewSetting();
		
			for(EntityKind kind : EntityKind.kindsWithGenericAttributes)
				for (EntityAttributeGroup group: kind.getAllAttributeGroups().values())
					if (group.name != EntityKind.STATIC_ATTRIBUTE_GROUP)
					{
						AttributeGroupViewSetting groupSetting = new AttributeGroupViewSetting();
						groupSetting.setDescribedEntity( kind.getName() );
						groupSetting.setAttributeGroupName( group.name );
						groupSetting.setShow( group.isVisible );
						groupView.getAttributeGroupViewSetting().add( groupSetting );
	
						for (EntityAttribute attr : group)
						{
							AttributeViewSetting attrSetting = new AttributeViewSetting();
							attrSetting.setAttributeName( attr.name );
							attrSetting.setDescribedEntity( kind.getName() );
							attrSetting.setShow( attr.isVisible );
							attributeView.getAttributeViewSetting().add(attrSetting);
						}
					}
			
			port.updateViewConfiguration( groupView, attributeView );
			
		} catch (IUserPersonalSpaceWSUpdateViewConfigurationUPSExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}
	
	
	@Override
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
	
	
	@Override
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

	
	@Override
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


	@Override
	public DbElementsList<? extends GenericDescription<?>>  listBasketEntitiesWithAttributes(String basketName, EntityKind kind) throws Exception
	{
		IUserPersonalSpaceWS port = ConnectionTools2.getUserPersonalSpaceServicePort( "listBasketEntityWithAttributes", this );
		return kind.listBasketEntitiesWithAttributes(port, basketName);
	}
	


	/*==========================================================================
	 * 	AdministrationWS
	 *==========================================================================
	 */	
	
	
	@Override
	public void  defineAttribute( EntityAttribute a, String pluginDescriptor ) throws Exception 
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "defineAttribute", this );
			port.defineAttribute(a.name, a.groupName, a.kind.name(), a.isEnum, pluginDescriptor, a.type, a.unit);
			if (a.isEnum)
			{
				for (String enumValue : a.enumValues)
					port.addAttributeEnumValue(a.name, a.groupName, a.kind.name(), enumValue, false);
			}

		} catch (IAdministrationWSDefineAttributeAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	@Override
	public void  removeAttribute( EntityAttribute a, String pluginDescriptor ) throws Exception 
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "removeAttribute", this );
			port.removeAttribute(a.name, a.groupName, a.kind.name());

		} catch (IAdministrationWSRemoveAttributeAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
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
	

	@Override
	public void  removeAttributeGroup(String groupName, String unit) throws Exception
	{
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "removeAttributeGroup", this );
			port.removeAttributeGroup(groupName, unit);

		} catch (IAdministrationWSRemoveAttributeGroupAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

	
	@Override
	public void  addAttributeEnumValue(EntityAttribute a, String value, boolean clearExisting ) throws Exception
	{
		if (a.kind == null)
			throw new Exception ("Attribute must have entity kind defined!" + a);
		if (a.groupName == null)
			throw new Exception ("Attribute must have group MeasurementConfName defined!" + a);
		try {
			IAdministrationWS port = ConnectionTools2.getAdministrationServicePort( "defineAttributeEnumValue", this );
			port.addAttributeEnumValue(a.name, a.groupName, a.kind.name(), value, clearExisting);

		} catch (IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage e) {
			log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
			throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
		}	
		finally{
			ConnectionTools2.finalizeCall();
		}
	}

}
