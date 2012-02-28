using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;
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
        void RemoveUserAccount();

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void ChangePassword(string login, string oldPass, string newPass);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void EvokeGroupMembership(string login, string groupName);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RevokeGroupMembership(string login, string groupName);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListUsers();

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]     
        void GrantSessionPrivileges(string grantedUserLogin, int sessionID, bool write);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RemoveSessionPrivileges(string grantedUserLogin, int sessionID);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void AlterSessionVisibility(int sessionID, bool isPublic, bool isWritable);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListSessionPrivileges(int sessionID);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool IfCanUpdate(int resourceID, string entity);

    }
}

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
<<<<<<< .mine
        void RemoveUserAccount();
=======
        string CreateUserAccount(string username, string email);
>>>>>>> .r945

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void ChangePassword(string login, string oldPass, string newPass);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void EvokeGroupMembership(string login, string groupName);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RevokeGroupMembership(string login, string groupName);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListUsers();

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]     
        void GrantSessionPrivileges(string grantedUserLogin, int sessionID, bool write);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RemoveSessionPrivileges(string grantedUserLogin, int sessionID);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void AlterSessionVisibility(int sessionID, bool isPublic, bool isWritable);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListSessionPrivileges(int sessionID);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool IfCanUpdate(int resourceID, string entity);

    }
}
