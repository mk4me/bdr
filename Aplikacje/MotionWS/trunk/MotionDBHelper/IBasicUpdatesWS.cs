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
        int CreatePerformer(PerformerData performerData);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreateSession(int labID, string motionKindName, int performerID, DateTime sessionDate, string sessionDescription, int[] sessionGroupIDs);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int CreateTrial(int sessionID, string trialDescription, int trialDuration);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        int DefineTrialSegment(int trialID, string segmentName, int startTime, int endTime);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        bool AssignSessionToGroup(int sessionID, int groupID);

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
        void SetSegmentAttribute(int segmentID, string attributeName, string attributeValue, bool update);

        [OperationContract]
        [FaultContract(typeof(UpdateException))]
        void SetFileAttribute(int fileID, string attributeName, string attributeValue, bool update);

    }
}
