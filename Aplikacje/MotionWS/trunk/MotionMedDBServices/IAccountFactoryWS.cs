using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace MotionMedDBWebServices
{
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/AccountFactoryService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IAccountFactoryWS
    {
        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        void CreateUserAccount(string login, string email, string pass, string firstName, string lastName, bool propagateToHMDB);

        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        bool ActivateUserAccount(string login, string activationCode, bool propagateToHMDB);

        [OperationContract]
        [FaultContract(typeof(AccountFactoryException))]
        bool ResetPassword(string email, bool propagateToHMDB);
    }
}
