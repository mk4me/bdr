package motion.database.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import motion.Messages;
import motion.database.DatabaseConnection;
import motion.database.DbElementsList;
import motion.database.ws.ConnectionTools2;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetMeasurementByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerConfigurationByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.MeasurementDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerConfDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSListBasketPerformersWithAttributesXMLUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSListBasketSessionsWithAttributesXMLUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWSListBasketTrialsWithAttributesXMLUPSExceptionFaultFaultMessage;
import motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult;
import motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult;
import motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult;

public enum EntityKind {

	performer(PerformerStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setPerformerAttribute(ID, a.name, a.value.toString(), update);			
		}

		@Override
		public Performer getByID(IBasicQueriesWS port, int id) throws Exception {
			try{
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
		
		@Override
		public DbElementsList<? extends GenericDescription<?>>  listBasketEntitiesWithAttributes(IUserPersonalSpaceWS port, String basketName) throws Exception
		{
			try {
				ListBasketPerformersWithAttributesXMLResult result = port.listBasketPerformersWithAttributesXML(basketName);
			
				DbElementsList<Performer> output = new DbElementsList<Performer>();
				
				if (result.getBasketPerformerWithAttributesList() != null) 
					for ( motion.database.ws.userPersonalSpaceWCF.PerformerDetailsWithAttributes s : result.getBasketPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
							output.add( ConnectionTools2.transformPerformerDetailsUPS(s) );
				
				return output;

			} catch (IUserPersonalSpaceWSListBasketPerformersWithAttributesXMLUPSExceptionFaultFaultMessage e) {
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}	
			finally{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public GenericDescription<?> newEntity()
		{
			return new Performer();
		}
	}, 

	session(SessionStaticAttributes.class) {

		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setSessionAttribute(ID, a.name, a.value.toString(), update);			
		}

		@Override
		public Session getByID(IBasicQueriesWS port, int id) throws Exception {
			try{
				GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
				SessionDetailsWithAttributes s = result.getSessionDetailsWithAttributes();
		
				return ConnectionTools2.transformSessionDetails(s);
			}
			catch(IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage e)
			{
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}
			finally
			{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public void storeFile(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description, String filename) throws Exception{
			port.storeSessionFile(resourceId, destRemoteFolder, description, filename);
		}

		@Override
		public void storeFiles(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description) throws Exception{
			port.storeSessionFiles(resourceId, destRemoteFolder, description);
		}

		@Override
		public DbElementsList<DatabaseFile> listFiles(IBasicQueriesWS port, int resourceID) throws Exception
		{
			return super.listFilesMethod(port, resourceID);
		}
		
		@Override
		public DbElementsList<? extends GenericDescription<?>>  listBasketEntitiesWithAttributes(IUserPersonalSpaceWS port, String basketName) throws Exception
		{
			try {
				ListBasketSessionsWithAttributesXMLResult result = port.listBasketSessionsWithAttributesXML(basketName);
			
				DbElementsList<Session> output = new DbElementsList<Session>();
				
				if (result.getBasketSessionWithAttributesList() != null) 
					for ( motion.database.ws.userPersonalSpaceWCF.SessionDetailsWithAttributes s : result.getBasketSessionWithAttributesList().getSessionDetailsWithAttributes() )
							output.add( ConnectionTools2.transformSessionDetailsUPS(s) );
				
				return output;

			} catch (IUserPersonalSpaceWSListBasketSessionsWithAttributesXMLUPSExceptionFaultFaultMessage e) {
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}	
			finally{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public GenericDescription<?> newEntity()
		{
			return new Session();
		}
	}, 

	trial(TrialStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setTrialAttribute(ID, a.name, a.value.toString(), update);			
		}
		
		@Override
		public Trial getByID(IBasicQueriesWS port, int id) throws Exception {
			try{
				GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
				TrialDetailsWithAttributes s = result.getTrialDetailsWithAttributes();
		
				return ConnectionTools2.transformTrialDetails(s);
			}
			catch(IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage e)
			{
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}
			finally
			{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public void storeFile(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description, String filename) throws Exception{
			port.storeTrialFile(resourceId, destRemoteFolder, description, filename);
		}

		@Override
		public void storeFiles(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description) throws Exception{
			port.storeTrialFiles(resourceId, destRemoteFolder, description);
		}
		
		@Override
		public DbElementsList<DatabaseFile> listFiles(IBasicQueriesWS port, int resourceID) throws Exception
		{
			return super.listFilesMethod(port, resourceID);
		}
		
		@Override
		public DbElementsList<? extends GenericDescription<?>>  listBasketEntitiesWithAttributes(IUserPersonalSpaceWS port, String basketName) throws Exception
		{
			try {
				ListBasketTrialsWithAttributesXMLResult result = port.listBasketTrialsWithAttributesXML(basketName);
			
				DbElementsList<Trial> output = new DbElementsList<Trial>();
				
				if (result.getBasketTrialWithAttributesList() != null) 
					for ( motion.database.ws.userPersonalSpaceWCF.TrialDetailsWithAttributes s : result.getBasketTrialWithAttributesList().getTrialDetailsWithAttributes() )
							output.add( ConnectionTools2.transformTrialDetailsUPS(s) );
				
				return output;

			} catch (IUserPersonalSpaceWSListBasketTrialsWithAttributesXMLUPSExceptionFaultFaultMessage e) {
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}	
			finally{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public GenericDescription<?> newEntity()
		{
			return new Trial();
		}
	}, 
	
	measurement(MeasurementStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setMeasurementAttribute(ID, a.name, a.value.toString(), update);
		}
		
		@Override
		public Measurement getByID(IBasicQueriesWS port, int id) throws Exception {
			try{
				GetMeasurementByIdXMLResult result = port.getMeasurementByIdXML(id);
				MeasurementDetailsWithAttributes s = result.getMeasurementDetailsWithAttributes();
		
				return ConnectionTools2.transformMeasurementDetails(s);
			}
			catch(IBasicQueriesWSGetMeasurementByIdXMLQueryExceptionFaultFaultMessage e)
			{
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}
			finally
			{
				ConnectionTools2.finalizeCall();
			}
		}

		@Override
		public GenericDescription<?> newEntity()
		{
			return new Measurement();
		}
	}, 
	
	measurement_conf(MeasurementConfigurationStaticAttributes.class) {

		@Override
		public void storeFile(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description, String filename) throws Exception{
			port.storeMeasurementConfFile(resourceId, destRemoteFolder, description, filename);
		}
	
		@Override
		public void storeFiles(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description) throws Exception{
			port.storeMeasurementConfFiles(resourceId, destRemoteFolder, description);
		}

		@Override
		public DbElementsList<DatabaseFile> listFiles(IBasicQueriesWS port, int resourceID) throws Exception
		{
			return super.listFilesMethod(port, resourceID);
		}

		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setMeasurementConfAttribute(ID, a.name, a.value.toString(), update);
		}
		
		@Override
		public GenericDescription<?> newEntity()
		{
			return new MeasurementConfiguration();
		}
	}, 
	
	file(DatabaseFileStaticAttributes.class)
	{
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setFileAttribute(ID, a.name, a.value.toString(), update);
		}

		@Override
		public GenericDescription<?> newEntity()
		{
			return new DatabaseFile();
		}
	},

	user(UserStaticAttributes.class)
	{
		@Override
		public GenericDescription<?> newEntity()
		{
			return new User();
		}
	}, 
	
	userPrivileges(UserPrivilegesStaticAttributes.class)
	{
		@Override
		public GenericDescription<?> newEntity()
		{
			return new UserPrivileges();
		}
	},
	
	result(null)
	{
		@Override
		public GenericDescription<?> newEntity()
		{
			return new GenericResult();
		}
	},
	
	userBasket (UserBasketStaticAttributes.class)
	{
		@Override
		public GenericDescription<?> newEntity()
		{
			return new UserBasket();
		}
	}, 
	
	sessionGroup (SessionGroupStaticAttributes.class)
	{
		@Override
		public GenericDescription<?> newEntity()
		{
			return new SessionGroup();
		}

		@Override
		public HashMap<String, EntityAttributeGroup> getAllAttributeGroups() throws Exception
		{
			if (this.genericAttributeGroups==null)
			{	
				EntityAttributeGroup group = new EntityAttributeGroup(STATIC_ATTRIBUTE_GROUP, this);
				group.add( new EntityAttribute(SessionGroupStaticAttributes.SessionGroupID.name(), this, null, group.name, EntityAttribute.DB_TYPE_INTEGER) );
				group.add( new EntityAttribute(SessionGroupStaticAttributes.Name.name(), this, null, group.name, EntityAttribute.DB_TYPE_STRING) );
				this.genericAttributeGroups = new HashMap<String, EntityAttributeGroup>();
				this.genericAttributeGroups.put(group.name, group);
			}
			return genericAttributeGroups;	
		}
	},
	
	performer_conf(PerformerConfigurationStaticAttributes.class) 
	{

		/*		@Override
				public void storeFile(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description, String filename) throws Exception{
					port.storeMeasurementConfFile(resourceId, destRemoteFolder, description, filename);
				}
			
				@Override
				public void storeFiles(IFileStoremanWS port, int resourceId, String destRemoteFolder, String description) throws Exception{
					port.storeMeasurementConfFiles(resourceId, destRemoteFolder, description);
				}

				@Override
				public DbElementsList<DatabaseFile> listFiles(IBasicQueriesWS port, int resourceID) throws Exception
				{
					return super.listFilesMethod(port, resourceID);
				}
		*/
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setPerformerConfAttribute(ID, a.name, a.value.toString(), update);
		}
		
		@Override
		public GenericDescription<?> newEntity()
		{
			return new PerformerConfiguration();
		}

		@Override
		public PerformerConfiguration getByID(IBasicQueriesWS port, int id) throws Exception {
			try{
				GetPerformerConfigurationByIdXMLResult result = port.getPerformerConfigurationByIdXML(id);
				PerformerConfDetailsWithAttributes s = result.getPerformerConfDetailsWithAttributes();
		
				return ConnectionTools2.transformPerformerConfigurationDetails(s);
			}
			catch(IBasicQueriesWSGetPerformerConfigurationByIdXMLQueryExceptionFaultFaultMessage e)
			{
				DatabaseConnection.log.log( Level.SEVERE, e.getFaultInfo().getDetails().getValue(), e );
				throw new Exception( e.getFaultInfo().getDetails().getValue(), e ); 
			}
			finally
			{
				ConnectionTools2.finalizeCall();
			}
		}
	};
	
	
	//////////////////////////////////////////////////////////////////////////
	
	protected Class<?> staticAttributesNames;
	public static String STATIC_ATTRIBUTE_GROUP = "_static";

	protected static String[] guiNames={Messages.getString("EntityKind.0"), Messages.getString("EntityKind.1"), 
		Messages.getString("EntityKind.2"), Messages.getString("EntityKind.3"), 
		Messages.getString("EntityKind.4"), Messages.getString("EntityKind.5"), Messages.getString("EntityKind.6"), 
		Messages.getString("EntityKind.7"), Messages.getString("EntityKind.8"), 
		Messages.getString("EntityKind.9"), Messages.getString("EntityKind.10"), Messages.getString("EntityKind.11")}; 

	protected ArrayList<EntityAttribute> genericAttributes;
	protected ArrayList<EntityAttribute> staticAttributes;
	protected HashMap<String, EntityAttributeGroup> genericAttributeGroups;

	//////////////////////////////////////////////////////////////////////////

	static public EnumSet<EntityKind> kindsWithGenericAttributes = EnumSet.of( EntityKind.performer, EntityKind.session, EntityKind.trial, EntityKind.measurement_conf, EntityKind.file );
	static public EnumSet<EntityKind> kindsWithGetByID = EnumSet.of( EntityKind.performer, EntityKind.session, EntityKind.trial, EntityKind.measurement );
	static public EnumSet<EntityKind> kindsBasketStorable = EnumSet.of( EntityKind.performer, EntityKind.session, EntityKind.trial );

	
	//////////////////////////////////////////////////////////////////////////

	EntityKind(Class<?> staticAttributes)
	{
		this.staticAttributesNames = staticAttributes;
	}
	
	public HashMap<String, EntityAttributeGroup> getAllAttributeGroups() throws Exception
	{
		if (genericAttributeGroups==null)
			genericAttributeGroups = DatabaseConnection.getInstanceWCF().listGrouppedAttributesDefined( this ); 
		return genericAttributeGroups;	
	}
	
	public ArrayList<EntityAttribute> getGenericAttributes() throws Exception
	{
		if (genericAttributes == null)
		{
			genericAttributes = new ArrayList<EntityAttribute>();
			staticAttributes = new ArrayList<EntityAttribute>();
			if (getAllAttributeGroups()!=null && getAllAttributeGroups().values() != null)
				for(Iterator<EntityAttributeGroup> i = getAllAttributeGroups().values().iterator(); i.hasNext(); )
				{
					EntityAttributeGroup g =  i.next();
					if (g.name.equals("_static"))
						staticAttributes.addAll(g);
					else
						genericAttributes.addAll(g);
				}
		}
		
		return genericAttributes;		
	}
	
	public ArrayList<EntityAttribute> getStaticAttributes() throws Exception
	{
		if (staticAttributes == null)
				getGenericAttributes();
		return staticAttributes;
	}
	
	public ArrayList<EntityAttribute> getGenericAttributeCopies() {
		ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
		try {
			attributes.addAll(getGenericAttributes());
		} catch (Exception e) {
		}
		
		return new ArrayList<EntityAttribute>(attributes);
	}
	
	public ArrayList<EntityAttribute> getStaticAttributeCopies() {
		ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
		try {
			attributes.addAll(getStaticAttributes());
		} catch (Exception e) {
		}
		
		return new ArrayList<EntityAttribute>(attributes);
	}
	
	public ArrayList<EntityAttribute> getAllAttributeCopies() {
		ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
		attributes.addAll(getStaticAttributeCopies());
		attributes.addAll(getGenericAttributeCopies());
		
		return attributes;
	}
	
	public ArrayList<EntityAttributeGroup> getGroupedAttributes() {
		ArrayList<EntityAttributeGroup> groups = new ArrayList<EntityAttributeGroup>();
		try {
			Iterator i = getAllAttributeGroups().entrySet().iterator();
			while (i.hasNext()) {
				EntityAttributeGroup attributeGroup = (EntityAttributeGroup) (((Map.Entry) i.next()).getValue());
				groups.add(attributeGroup);
			}
		} catch (Exception e) {
		}
		
		return groups;
	}
	
	public ArrayList<EntityAttributeGroup> getGroupedAttributeCopies() {
		ArrayList<EntityAttributeGroup> groups = getGroupedAttributes();
		
		return new ArrayList<EntityAttributeGroup>(groups);
	}
	
	public ArrayList<EntityAttribute> getSelectedAttributeCopies(ArrayList<String> selectedAttributes) {
		if (selectedAttributes.isEmpty()) {
			
			return new ArrayList<EntityAttribute>();
		} else {
			ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
			for (EntityAttribute a : getStaticAttributeCopies()) {
				if (selectedAttributes.contains(a.toString())) {
					attributes.add(a);
				}
			}
			
			for (EntityAttribute a : getGenericAttributeCopies()) {
				if (selectedAttributes.contains(a.toString())) {
					attributes.add(a);
				}
			}
			
			return new ArrayList<EntityAttribute>(attributes);
		}
	}
	
	public ArrayList<EntityAttributeGroup> getDeselectedAttributeGroupCopies(ArrayList<String> deselectedAttributes) {
		if (deselectedAttributes.isEmpty()) {
			
			return getGroupedAttributeCopies();
		} else {
			ArrayList<EntityAttributeGroup> groups = getGroupedAttributeCopies();
			for (EntityAttributeGroup attributeGroup : groups) {
				Iterator i = attributeGroup.iterator();
				while (i.hasNext()) {
					if (deselectedAttributes.contains(((EntityAttribute) i.next()).name)) {
						i.remove();
					}
				}
			}
			
			return groups;
		}
	}
	
	public EntityAttribute getEntityAttributeCopy(String attribute) {
		for (EntityAttribute a : getAllAttributeCopies()) {
			if (a.name.equals(attribute)) {
				
				return a;
			}
		}
		
		return null;
	}
	
	public void rescanGenericAttributeGroups() throws Exception
	{
		genericAttributeGroups = null;
		getAllAttributeGroups();
	}

	public void rescanGenericAttributes() throws Exception
	{
		genericAttributes = null;
		getGenericAttributes();
	}
	
	
	public String[] getKeys()
	{
		Object[] k = (staticAttributesNames).getEnumConstants();
		String [] result = new String[k.length];
		int i = 0;
		for( Object o : k )
			result[i++] = o.toString();
		return result;
	}

	public void setEntityAttribute(IBasicUpdatesWS port, int ID, EntityAttribute a, boolean update) throws Exception
	{
		throw new Exception( this.name() + Messages.getString("EntityKind.Error1")); //$NON-NLS-1$
	}

	public GenericDescription<?> getByID(IBasicQueriesWS port, int id) throws Exception {
		throw new Exception( this.name() + Messages.getString("EntityKind.Error2")); //$NON-NLS-1$
	}

	public void storeFile(IFileStoremanWS port, int resourceId, String destRemoteFolder, 
			String description, String filename) throws Exception{
		throw new Exception( this.name() + Messages.getString("EntityKind.Error3")); //$NON-NLS-1$
	}

	public void storeFiles(IFileStoremanWS port, int resourceId,
			String destRemoteFolder, String description) throws Exception {
		throw new Exception( this.name() + Messages.getString("EntityKind.Error3")); //$NON-NLS-1$
	}


	public void storeAttributeFile(IFileStoremanWS port, int resourceId, EntityAttribute attribute, String destRemoteFolder, 
			String description, String filename) throws Exception{
		throw new Exception( this.name() + Messages.getString("EntityKind.Error4")); //$NON-NLS-1$
	}

	public  DbElementsList<DatabaseFile> listFiles(IBasicQueriesWS port, int resourceID) throws Exception
	{
		throw new Exception( this.name() + Messages.getString("EntityKind.Error5")); //$NON-NLS-1$
	}

	public DbElementsList<? extends GenericDescription<?>> listBasketEntitiesWithAttributes(IUserPersonalSpaceWS port, String basketName) throws Exception {
		throw new Exception( this.name() + Messages.getString("EntityKind.Error6")); //$NON-NLS-1$
	}

	public String getGUIName()
	{
		return guiNames[ this.ordinal() ];
	}
	
	public String getName()
	{
		return this.name();
	}

	public abstract GenericDescription newEntity();

	
	public EntityKind fromString(String s)
	{
		return EntityKind.valueOf( s.toLowerCase() );
	}
	
	//////////////////////////////////////////////////////////////////////////

	private  DbElementsList<DatabaseFile> listFilesMethod(IBasicQueriesWS port, int resourceID) throws Exception
	{
		ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(resourceID, this.name());
		return ConnectionTools2.transformListOfFiles(result);
	}

}
