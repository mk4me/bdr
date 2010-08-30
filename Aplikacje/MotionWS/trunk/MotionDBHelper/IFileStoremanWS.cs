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
        [FaultContract(typeof(FileAccessServiceException))]
        int StoreMeasurementResultFile(int measurementID, string path, string description, string filename);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        int StoreAttributeFile(int resourceID, string entity, string attributeName, string path, string description, string filename);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        void DownloadComplete(int fileID, string path);

        [OperationContract]
        [FaultContract(typeof(FileAccessServiceException))]
        FileData RetrieveFile(int fileID);
    }
}
