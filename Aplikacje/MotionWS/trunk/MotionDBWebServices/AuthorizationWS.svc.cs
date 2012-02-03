using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;
using System.Xml;

using System.DirectoryServices.AccountManagement;

using MotionDBCommons;


namespace MotionDBWebServices
{
    // NOTE: If you change the class name "AuthorizationWS" here, you must also update the reference to "AuthorizationWS" in Web.config.

    
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")]
    [ErrorLoggerBehaviorAttribute]
    public class AuthorizationWS : DatabaseAccessService, IAuthorizationWS   
    {

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public bool CheckUserAccount()
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int result = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "check_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar,30);
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);

                cmd.Parameters["@user_login"].Value = userName;

                cmd.ExecuteNonQuery();
                result = (int)resultParameter.Value;
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("unknown", "Validation failed for user " + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name);
                throw new FaultException<AuthorizationException>(exc, "Login validation failed for user " + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name, FaultCode.CreateReceiverFaultCode(new FaultCode("CheckUserAccount")));
            }
            finally
            {
                CloseConnection();
            }
            return result == 1;
        }
        // SPrawdzić !!!!

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionAdmins")]
        public string CreateUserAccount(string username, string email)
        {
            string password = "aplet4Motion";

            try
            {
                PrincipalContext context = new PrincipalContext(ContextType.Machine);
                UserPrincipal user = new UserPrincipal(context);
                user.SetPassword(password);
                user.DisplayName = username;
                user.Name = username;
                user.Description = "";
                user.UserCannotChangePassword = false;
                user.PasswordNeverExpires = true;
                user.Save();

                //now add user to "Users" group so it displays in Control Panel
                GroupPrincipal group = GroupPrincipal.FindByIdentity(context, "Users");
                group.Members.Add(user);
                group.Save();
            }
            catch (Exception ex1)
            {
                AuthorizationException exc = new AuthorizationException("account_management", "Account creation failed");
                throw new FaultException<AuthorizationException>(exc, "Failed to create user account "+ ex1.Message , FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));

            }
            
            /*
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "create_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_first_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_last_name", SqlDbType.VarChar, 50);

                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@user_first_name"].Value = firstName;
                cmd.Parameters["@user_last_name"].Value = lastName;


                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("unknown", "User creation failed");
                throw new FaultException<AuthorizationException>(exc, "Failed to create user", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateUserAccount")));
            }
            finally
            {
                CloseConnection();
            } */

            return "random_pass";
        }

        // SECURE ME !!!
        public void GrantSessionPrivileges(string grantedUserLogin, int sessionID, bool write)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_session_privileges";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@granted_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@write", SqlDbType.Bit);

                cmd.Parameters["@granting_user_login"].Value = userName;
                cmd.Parameters["@granted_user_login"].Value = grantedUserLogin;
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@write"].Value = write ? 1 : 0;


                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("SQL error", "Privilege grant by "+userName+" to "+grantedUserLogin+" failed");
                throw new FaultException<AuthorizationException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("GrantSessionPrivileges")));
            }
            finally
            {
                CloseConnection();
            }
        }


        // SECURE ME !!!
        public void RemoveSessionPrivileges(string grantedUserLogin, int sessionID)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "unset_session_privileges";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@granted_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters["@granting_user_login"].Value = userName;
                cmd.Parameters["@granted_user_login"].Value = grantedUserLogin;
                cmd.Parameters["@sess_id"].Value = sessionID;


                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("SQL error", "Privilege grant failed");
                throw new FaultException<AuthorizationException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveSessionPrivileges")));
            }
            finally
            {
                CloseConnection();
            }
        }

        // SECURE ME !!!
        public void AlterSessionVisibility(int sessionID, bool isPublic, bool isWritable)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "alter_session_visibility";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@public", SqlDbType.Bit);
                cmd.Parameters.Add("@writeable", SqlDbType.Bit);
                cmd.Parameters["@granting_user_login"].Value = userName;
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@public"].Value = isPublic?1:0;
                cmd.Parameters["@writeable"].Value = isWritable?1:0;

                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("SQL error", "Privilege grant failed");
                throw new FaultException<AuthorizationException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterSessionVisibility")));
            }
            finally
            {
                CloseConnection();
            }
        }
        // SECURE ME !!!
        public XmlElement ListUsers()
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_users_xml";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("UserList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("unknown", "Database access failed");
                throw new FaultException<AuthorizationException>(exc, "Could not retrieve user list", FaultCode.CreateReceiverFaultCode(new FaultCode("ListUsers")));

            }
            finally
            {
                CloseConnection();
            }
            return xd.DocumentElement;
        }




        // SECURE ME !!!
        public XmlElement ListSessionPrivileges(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_privileges_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter sessId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = sessionID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("SessionPrivilegeList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("unknown", "Database access failed");
                throw new FaultException<AuthorizationException>(exc, "Could not retrieve user list", FaultCode.CreateReceiverFaultCode(new FaultCode("ListSessionPrivileges")));

            }
            finally
            {
                CloseConnection();
            }
            return xd.DocumentElement;
        }

        // SECURE ME !!!
        public bool IfCanUpdate(int resourceID, string entity)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int result = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "if_can_update";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@res_id", SqlDbType.Int);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);

                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@res_id"].Value = resourceID;
                cmd.Parameters["@entity"].Value = entity;

                cmd.ExecuteNonQuery();
                result = (int)resultParameter.Value;
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("database", "Check failed for user " + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name);
                throw new FaultException<AuthorizationException>(exc, "Update authorization check failed" + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name, FaultCode.CreateReceiverFaultCode(new FaultCode("IfCanUpdate")));
            }
            finally
            {
                CloseConnection();
            }
            return result == 1;
        }

    }
}

