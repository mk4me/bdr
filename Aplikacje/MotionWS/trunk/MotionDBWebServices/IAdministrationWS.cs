using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IAdministrationWS" here, you must also update the reference to "IAdministrationWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AdministrationService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IAdministrationWS
    {
        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void DefineAttriubeGroup(string groupName, string entity);
        
        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void RemoveAttributeGroup(string groupName, string entity);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void DefineAttribute(string attributeName, string groupName, string entity, bool isEnum, string pluginDescriptor, 
            string type, string unit);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void RemoveAttribute(string attributeName, string groupName, string entity);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void AddAttributeEnumValue(string attributeName, string groupName, string entity, string value, bool clearExisting);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void AlterUserToUserGroupAssignment(int userID, int userGroupID, bool assign);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void AlterUserGroupToSessionGroupAssignment(int userID, int userGroupID, bool assign);

        [OperationContract]
        [FaultContract(typeof(AdministrationOperationException))]
        void DownloadAreaCleanup(int olderThanMinutes);

    }
}
