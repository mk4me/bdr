using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionDBWebServices
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IAccountFactoryWS" in both code and config file together.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AccountFactoryService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IAccountFactoryWS
    {
        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        void CreateUserAccount(string login, string email, string pass, string firstName, string lastName);

        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        bool ActivateUserAccount(string login, string activationCode);
        
        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        bool ResetPassword(string email);
    }
}
