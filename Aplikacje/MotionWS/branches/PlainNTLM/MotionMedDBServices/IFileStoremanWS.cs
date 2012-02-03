using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionMedDBWebServices
{
    // NOTE: If you change the interface name "IService1" here, you must also update the reference to "IService1" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/FileStoremanService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IFileStoremanWS
    {
        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void DownloadComplete(int resourceID, string resourceType, string path);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        string GetMetadata();

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        string GetShallowCopy();

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        MotionMedDBWebServices.FileData RetrievePhoto(int photoID);

    }
}
