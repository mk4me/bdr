using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IBasicUpdatesWS" here, you must also update the reference to "IBasicUpdatesWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IBasicUpdatesWS
    {
        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreatePerformer(int PerformerID);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreateSession(int labID, string motionKindName, DateTime sessionDate, string sessionName, string tags, string sessionDescription, int[] sessionGroupIDs);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreateMeasurementConfiguration(string mcName, string mcKind, string mcDescription);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreateTrial(int sessionID, string trialName, string trialDescription);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        bool AssignSessionToGroup(int sessionID, int groupID);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int AssignPerformerToSession(int sessionID, int performerID);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetPerformerAttribute(int performerID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetSessionAttribute(int sessionID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetTrialAttribute(int trialID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetMeasurementConfAttribute(int measurementConfID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetPerformerConfAttribute(int performerConfID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetFileAttribute(int fileID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void ClearAttributeValue(int resourceID, string attributeName, string entity);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetFileTypedAttributeValue(int resourceID, string entity, string attributeName, int fileID, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetMyAnnotationStatus(int trialID, int status, string comment);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetAnnotationReview(int trialID, int userID, int status, string note);



    }
}
