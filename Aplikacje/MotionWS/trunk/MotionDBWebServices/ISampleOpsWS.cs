using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "ISampleOpsWS" here, you must also update the reference to "ISampleOpsWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SampleOpsService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface ISampleOpsWS
    {
        //[OperationContract]
        //[FaultContract(typeof(QueryException))]
        //XmlElement ListLabSessionsWithAttributesXML(int labID);


        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformersXML();
    }
}
