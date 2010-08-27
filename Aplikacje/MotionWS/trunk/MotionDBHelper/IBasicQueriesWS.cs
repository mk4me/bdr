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
        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GenericQueryXML(FilterPredicateCollection filter, string[] entitiesToInclude);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GenericQueryUniformXML(FilterPredicateCollection filter, string[] entitiesToInclude);

        [OperationContract] 
        [FaultContract(typeof(QueryException))]
        XmlElement GetPerformerByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetSessionByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        string GetSessionLabel(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetTrialByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformersXML();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformersWithAttributesXML();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListLabPerformersWithAttributesXML(int labID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformerSessionsXML(int performerID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformerSessionsWithAttributesXML(int performerID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListLabSessionsWithAttributesXML(int labID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionTrialsXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionTrialsWithAttributesXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFileAttributeDataXML(int subjectID, string subjectType);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFileAttributeDataWithAttributesXML(int subjectID, string subjectType);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListAttributesDefined(string attributeGroupName, string entityKind);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListAttributeGroupsDefined(string entityKind);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionGroupsDefined();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListMotionKindsDefined();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListEnumValues(string attributeName, string entityKind);
    }

}
