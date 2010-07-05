﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;


namespace MotionDBWebServices
{
    // NOTE: If you change the class name "AuthorizationWS" here, you must also update the reference to "AuthorizationWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")]
    public class AuthorizationWS : DatabaseAccessService, IAuthorizationWS   
    {

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public bool CheckUserAccount()
        {
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

                cmd.Parameters["@user_login"].Value = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;

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


        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void CreateUserAccount(string firstName, string lastName)
        {
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "create_user_account";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_first_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@user_last_name", SqlDbType.VarChar, 50);

                cmd.Parameters["@user_login"].Value = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
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
            }
        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void GrantSessionPrivileges(string grantedUserLogin, string grantedUserDomain, int sessionID, bool write)
        {
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_session_privileges";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@granted_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@write", SqlDbType.Bit);

                cmd.Parameters["@granting_user_login"].Value = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
                cmd.Parameters["@granted_user_login"].Value = grantedUserDomain.Equals("")?grantedUserLogin:grantedUserDomain+"\\"+grantedUserLogin;
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@write"].Value = write ? 1 : 0;


                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                AuthorizationException exc = new AuthorizationException("SQL error", "Privilege grant failed");
                throw new FaultException<AuthorizationException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("GrantSessionPrivileges")));
            }
            finally
            {
                CloseConnection();
            }
        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void RemoveSessionPrivileges(string grantedUserLogin, string grantedUserDomain, int sessionID)
        {
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "unset_session_privileges";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@granted_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters["@granting_user_login"].Value = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
                cmd.Parameters["@granted_user_login"].Value = grantedUserDomain.Equals("") ? grantedUserLogin : grantedUserDomain + "\\" + grantedUserLogin;
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void AlterSessionVisibility(string grantedUserLogin, string grantedUserDomain, int sessionID, bool isPublic, bool isWritable)
        {
            try
            {

                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "alter_session_visibility";
                cmd.Parameters.Add("@granting_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@granted_user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@public", SqlDbType.Bit);
                cmd.Parameters.Add("@writeable", SqlDbType.Bit);
                cmd.Parameters["@granting_user_login"].Value = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
                cmd.Parameters["@granted_user_login"].Value = grantedUserDomain.Equals("") ? grantedUserLogin : grantedUserDomain + "\\" + grantedUserLogin;
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@public"].Value = isPublic;
                cmd.Parameters["@writeable"].Value = isWritable;


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

    }
}

