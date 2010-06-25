using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IAuthorizationWS" here, you must also update the reference to "IAuthorizationWS" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IAuthorizationWS
    {
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        bool CheckUserAccount(string login, string domain);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void CreateUserAccount(string firstName, string lastName);
        [OperationContract]
        [FaultContract(typeof(AuthorizationException))]
        void GrantSessionPrivileges(string grantingUserLogin, string grantedUserDomain, int sessionID, bool write);
    }
}
