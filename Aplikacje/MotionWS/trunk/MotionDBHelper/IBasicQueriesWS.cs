using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IBasicQueriesWS" here, you must also update the reference to "IBasicQueriesWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IBasicQueriesWS
    {
        [OperationContract ] 
        [FaultContract(typeof(QueryException))]
        XmlElement GetPerformerByIdXML(int id);

    }

}
