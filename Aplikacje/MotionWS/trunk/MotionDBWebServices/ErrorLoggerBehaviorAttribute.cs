using System;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;
using System.ServiceModel.Channels;
using System.Collections.ObjectModel;



// Implementacja oparta na: http://taswar.zeytinsoft.com/2009/02/26/wcf-error-handling-with-log4net/

namespace MotionDBWebServices
{
    public class ErrorLoggerBehaviorAttribute : Attribute, IServiceBehavior, IErrorHandler
    {
		protected Type ServiceType { get; set; }
		public void Validate(ServiceDescription serviceDescription, ServiceHostBase serviceHostBase)
		{
			//Dont do anything
		}
 
		public void AddBindingParameters(ServiceDescription serviceDescription, ServiceHostBase serviceHostBase, Collection<ServiceEndpoint> endpoints, BindingParameterCollection bindingParameters)
		{
			//dont do anything
		}
 
		public void ApplyDispatchBehavior(ServiceDescription serviceDescription, ServiceHostBase serviceHostBase)
		{
			ServiceType = serviceDescription.ServiceType;
			foreach (ChannelDispatcher dispatcher in serviceHostBase.ChannelDispatchers)
			{
				dispatcher.ErrorHandlers.Add(this);
			}
		}
 
		public void ProvideFault(Exception error, MessageVersion version, ref Message fault)
		{
            if (!(error is FaultException)) fault = null; //Suppress any faults in contract
		}
 
		public bool HandleError(Exception error)
		{
            if (!EventLog.SourceExists("BDR_WS" ))
                EventLog.CreateEventSource("BDR_WS", "MotionWS");

            EventLog.WriteEntry("BDR_WS", error.Message + error.StackTrace, EventLogEntryType.Error);

			return false;
		}

    }
}
