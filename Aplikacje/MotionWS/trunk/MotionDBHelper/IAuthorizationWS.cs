using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IAuthorizationWS" here, you must also update the reference to "IAuthorizationWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IAuthorizationWS
    {
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool CheckUserAccount();
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void CreateUserAccount(string firstName, string lastName);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void GrantSessionPrivileges(string grantedUserLogin, string grantedUserDomain, int sessionID, bool write);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RemoveSessionPrivileges(string grantedUserLogin, string grantedUserDomain, int sessionID);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void AlterSessionVisibility(int sessionID, bool idPublic, bool isWritable);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListUsers();
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListSessionPrivileges(int sessionID);

    }
}
