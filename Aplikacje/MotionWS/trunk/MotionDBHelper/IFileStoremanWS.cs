﻿using System;
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
        [FaultContract(typeof(FileAccessServiceException))]
        int StorePerformerFile(int performerID, string path, string description, string filename);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        int StoreSessionFile(int sessionId, string path, string description, string filename);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        int StoreTrialFile(int trialID, string path, string description, string filename);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void StorePerformerFiles(int performerID, string path);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void StoreSessionFiles(int sessionID, string path, string description);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void StoreTrialFiles(int trialId, string path);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void DownloadComplete(int fileID, string path);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        string RetrieveFile(int fileID);


    }
}
