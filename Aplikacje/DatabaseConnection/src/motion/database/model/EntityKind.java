package motion.database.model;

import java.io.File;
import java.util.logging.Level;

import motion.database.DatabaseConnection;
import motion.database.ws.ConnectionTools2;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetMeasurementByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetPerformerByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetSessionByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWSGetTrialByIdXMLQueryExceptionFaultFaultMessage;
import motion.database.ws.basicQueriesServiceWCF.MeasurementDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;

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
	}, 
	measurementConfiguration(MeasurementConfigurationStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("MeasurementConfiguration entity does not support generic attributes!");
		}
		
		@Override
		public MeasurementConfiguration getByID(IBasicQueriesWS port, int id) throws Exception {
			throw new Exception("MeasurementConfiguration entity does not support getting by measurementConfigurationID!");
		}

	}, 
	file(DatabaseFileStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setFileAttribute(ID, a.name, a.value.toString(), update);			
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

	}, 
	user(UserStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("User entity does not support generic attributes!");
		}

		@Override
		public User getByID(IBasicQueriesWS port, int id) throws Exception {
			throw new Exception("User entity does not support getting by measurementConfigurationID!");
		}
	}, 
	userPrivileges(UserPrivilegesStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("UserPrivileges entity does not support generic attributes!");
		}

		@Override
		public UserPrivileges getByID(IBasicQueriesWS port, int id) throws Exception {
			throw new Exception("UserPrivileges entity does not support getting by measurementConfigurationID!");
		}
	},
	result(null) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("Result entity does not support generic attributes!");
		}
		@Override
		public User getByID(IBasicQueriesWS port, int id) throws Exception {
			throw new Exception("Result entity does not support getting by measurementConfigurationID!");
		}

	}, 
	userBasket (UserBasketStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("UserBasket entity does not support generic attributes!");
		}

		@Override
		public UserBasket getByID(IBasicQueriesWS port, int id) throws Exception {
			throw new Exception("UserBasket entity does not support getting by measurementConfigurationID!");
		} 

	},;
	
	private Class<?> staticAttributes;

	EntityKind(Class<?> staticAttributes)
	{
		this.staticAttributes = staticAttributes;
	}
	
	public String[] getKeys()
	{
		Object[] k = (staticAttributes).getEnumConstants();
		String [] result = new String[k.length];
		int i = 0;
		for( Object o : k )
			result[i++] = o.toString();
		return result;
	}
	
	abstract public void setEntityAttribute(IBasicUpdatesWS port, int ID, EntityAttribute a, boolean update) throws Exception;
	
	abstract public GenericDescription<?> getByID(IBasicQueriesWS port, int ID) throws Exception;
	
}
