using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IFileStoremanWS" here, you must also update the reference to "IFileStoremanWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]

    public interface IFileStoremanWS
    {
        [OperationContract]
        int StorePerformerFile(int performerID, string path, string description, string filename);

        [OperationContract]
        int StoreSessionFile(int sessionId, string path, string description, string filename);

        [OperationContract]
        int StoreTrialFile(int trialID, string path, string description, string filename);

        [OperationContract]
        void StorePerformerFiles(int performerID, string path);

        [OperationContract]
        void StoreSessionFiles(int sessionID, string path, string description);

        [OperationContract]
        void StoreTrialFiles(int trialId, string path);

        [OperationContract]
        void DownloadComplete(int fileID, string path);

        [OperationContract]
        string RetrieveFile(int fileID);


    }
}
