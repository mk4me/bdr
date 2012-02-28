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


        public void CreateUserAccount(string login, string email, string pass, string firstName, string lastName)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            Boolean fault = false;
            string faultMessage = "";
            string code = "";
            int result = 0;

            if (login.Length < 4 || login.Length > 20)
            {
                faultMessage = "Login length invalid. Required <4,20> characters";
                fault = true;
            }
            if (pass.Length < 6 || pass.Length > 20)
            {
                faultMessage = faultMessage + " Password length invalid. Required at least 6 characters";
                fault = true;
            }

            if (!(Regex.IsMatch(pass, @".*?\d.*?")))
            {
                faultMessage = faultMessage + " Password not valid. At least one digit required.";
                fault = true;
            }

            if (!(Regex.IsMatch(email, @"\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b")))
            {
                faultMessage = faultMessage + " Email syntax invalid.";
                fault = true;
            }
            if (email.Length > 50)
            {
                faultMessage = faultMessage + " Email too long. Maximum 50 characters supported.";
                fault = true;
            }

            if (fault)
            {
                AccountFactoryException exc = new AccountFactoryException("parameter", faultMessage);
                throw new FaultException<AccountFactoryException>(exc, "Login, password or email invalid", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
            }

            code = ProduceRandomCode(10);

            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "create_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_password", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@user_email", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@user_first_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_last_name", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);

                cmd.Parameters["@user_login"].Value = login;
                cmd.Parameters["@user_password"].Value = pass;
                cmd.Parameters["@user_email"].Value = email;
                cmd.Parameters["@user_first_name"].Value = firstName;
                cmd.Parameters["@user_last_name"].Value = lastName;
                cmd.Parameters["@activation_code"].Value = code;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);
                cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result == 1)
                {
                    AccountFactoryException exc = new AccountFactoryException("parameters", "Login " + login + " already in use");
                    throw new FaultException<AccountFactoryException>(exc, "Illegal parameter value", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
                }
                else if (result == 2)
                {
                    AccountFactoryException exc = new AccountFactoryException("parameters", "Email " + email + " already in use");
                    throw new FaultException<AccountFactoryException>(exc, "Illegal parameter value", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
                }
                else if (result == 3)
                {
                    AccountFactoryException exc = new AccountFactoryException("parameters", "One of obligatory parameters (login, password, email) has zero length");
                    throw new FaultException<AccountFactoryException>(exc, "Illegal parameter value", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
                }

            }
            catch (SqlException ex)
            {
                AccountFactoryException exc = new AccountFactoryException("database", "User creation failed at database level " + ex.Message);
                throw new FaultException<AccountFactoryException>(exc, "Failed to create user", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
            }
            finally
            {
                CloseConnection();
            }
        }

        public bool ActivateUserAccount(string login, string password, string activationCode)
        {

            int result = 0;

            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "activate_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_password", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);

                cmd.Parameters["@user_login"].Value = login;
                cmd.Parameters["@user_password"].Value = password;
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
