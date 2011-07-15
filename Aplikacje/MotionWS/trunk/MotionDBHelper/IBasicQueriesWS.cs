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
        XmlElement GetMeasurementConfigurationByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetPerformerConfigurationByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetFileDataByIdXML(int id);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformersXML();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformersWithAttributesXML();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionPerformersWithAttributesXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListLabPerformersWithAttributesXML(int labID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformerSessionsXML(int performerID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListGroupSessionsWithAttributesXML(int sessionGroupID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListPerformerSessionsWithAttributesXML(int performerID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListLabSessionsWithAttributesXML(int labID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListMeasurementConfSessionsWithAttributesXML(int measurementConfID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionSessionGroups(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionTrialsXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionTrialsWithAttributesXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionPerformerConfsWithAttributesXML(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListMeasurementConfigurationsWithAttributesXML();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFilesXML(int subjectID, string subjectType);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFilesWithAttributesXML(int subjectID, string subjectType);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFileAttributeDataXML(int subjectID, string subjectType);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListFileAttributeDataWithAttributesXML(int subjectID, string subjectEntity);
/* ODLOZONE
        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetAttributeFileDataXML(int resourceID, string entity, string attributeName);
        */
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

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListSessionContents(int pageSize, int pageNo);


        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement GetSessionContent(int sessionID);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ValidateSessionFileSet(FileNameEntryCollection fileNames);


    }

}
