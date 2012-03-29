using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using MotionDBCommons;

namespace MotionDBWebServices
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "AccountFactoryWS" in code, svc and config file together.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AccountFactoryService")]
    [ErrorLoggerBehaviorAttribute]
    public class AccountFactoryWS : DatabaseAccessService, IAccountFactoryWS
    {

        LoginManagerHelper lmh = new LoginManagerHelper();

        public void CreateUserAccount(string login, string email, string pass, string firstName, string lastName)
        {

            string faultMessage = "";


            if(!lmh.CreateUserAccount( login, email, pass, firstName, lastName, out faultMessage ))
            {
                AccountFactoryException exc = new AccountFactoryException("parameter", faultMessage);
                throw new FaultException<AccountFactoryException>(exc, "Login, password or email invalid", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
            }

        }

        public bool ActivateUserAccount(string login, string activationCode)
        {

            int result = 0;

            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "activate_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);

                cmd.Parameters["@user_login"].Value = login;
                cmd.Parameters["@activation_code"].Value = activationCode;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);
                cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result == 1)
                {
                    return false;
                }

            }
            catch (SqlException ex)
            {
                AccountFactoryException exc = new AccountFactoryException("database", "Account activation failed at database level " + ex.Message);
                throw new FaultException<AccountFactoryException>(exc, "Failed to activate account", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
            }
            finally
            {
                CloseConnection();
            }
            return true;
        }


    }
}
