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
        /*
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RemoveUserAccount();
        

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void ChangePassword(string login, string oldPass, string newPass);
        
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void EvokeGroupMembership(string grantedUserLogin, string groupName);

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void RevokeGroupMembership(string grantedUserLogin, string groupName);
        */
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        XmlElement ListUsers();

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        UserData GetMyUserData();

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool UpdateUserAccount(string login, string email, string pass, string newPass, string firstName, string lastName);

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

        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool CheckMyLogin();

    }
}
