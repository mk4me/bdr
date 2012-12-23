using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Data;
using System.Data.SqlClient;

namespace MotionMedDBWebServices
{

    public class LoginManagerHelper
    {

        MedDatabaseAccessService das = new MedDatabaseAccessService();

        public string ProduceRandomCode(int l)
        {
            return das.ProduceRandomCode(l);
        }


        public bool CreateUserAccount(string login, string email, string pass, string firstName, string lastName, out string faultMessage, bool propagateToHMDB)
        {
            Boolean fault = false;
            string code = "";
            int result = 0;
            int propagate = propagateToHMDB?1:0;

            faultMessage = "";

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
                return false;
            }

            code = das.ProduceRandomCode(10);

            try
            {

                das.OpenConnection();
                das.cmd.CommandType = CommandType.StoredProcedure;
                das.cmd.CommandText = "create_user_account";
                das.cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@user_password", SqlDbType.VarChar, 20);
                das.cmd.Parameters.Add("@user_email", SqlDbType.VarChar, 50);
                das.cmd.Parameters.Add("@user_first_name", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@user_last_name", SqlDbType.VarChar, 50);
                das.cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);
                das.cmd.Parameters.Add("@hmdb_propagate", SqlDbType.Bit);
                das.cmd.Parameters["@user_login"].Value = login;
                das.cmd.Parameters["@user_password"].Value = pass;
                das.cmd.Parameters["@user_email"].Value = email;
                das.cmd.Parameters["@user_first_name"].Value = firstName;
                das.cmd.Parameters["@user_last_name"].Value = lastName;
                das.cmd.Parameters["@activation_code"].Value = code;
                das.cmd.Parameters["@hmdb_propagate"].Value = propagate;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                das.cmd.Parameters.Add(resultParameter);
                das.cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result != 0)
                {
                    if (result == 1)
                    {
                        faultMessage = "Login " + login + " already in use";

                    }
                    else if (result == 2)
                    {
                        faultMessage = "Email " + email + " already in use";

                    }
                    else if (result == 3)
                    {
                        faultMessage = "One of obligatory parameters (login, password, email) has zero length";

                    }
                    else if (result == 4)
                    {
                        faultMessage = "Propagation to HMDB requested, but the login provided is already in use in HMDB";

                    }
                    else if (result == 5)
                    {
                        faultMessage = "Propagation to HMDB requested, but the email provided is already in use in HMDB";

                    }
                    else faultMessage = "Unknown processing error";
                    fault = true;
                }


            }
            catch (SqlException ex)
            {
                faultMessage = "User creation failed at database level " + ex.Message;
                fault = true;

            }
            finally
            {
                das.CloseConnection();
            }
            return !fault;
        }

        public bool ActivateUserAccount(string login, string activationCode, bool propagateToHMDB, out string faultMessage)
        {

            int result = 0;
            int propagate = propagateToHMDB ? 1 : 0;
            faultMessage = "";
            try
            {

                das.OpenConnection();
                das.cmd.CommandType = CommandType.StoredProcedure;
                das.cmd.CommandText = "activate_user_account";
                das.cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);
                das.cmd.Parameters.Add("@hmdb_propagate", SqlDbType.Bit);

                das.cmd.Parameters["@user_login"].Value = login;
                das.cmd.Parameters["@activation_code"].Value = activationCode;
                das.cmd.Parameters["@hmdb_propagate"].Value = propagate;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                das.cmd.Parameters.Add(resultParameter);
                das.cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result > 0)
                {
                    if ( result == 1) faultMessage = "Authentication negative";
                    if ( result == 2) faultMessage = "HMDB account activation requested but account missing";
                    return false;
                }
               
            }
            catch (SqlException ex)
            {
                faultMessage = "Database system access error";
                return false;
            }
            finally
            {
                das.CloseConnection();
            }
            return true;
        }


        public bool UpdateUserAccount(string login, string email, string pass, string newPass, string firstName, string lastName, out string faultMessage)
        {
            Boolean fault = false;
            int result = 0;

            faultMessage = "";

            if (firstName != "-nochange-")
            {
                if (pass.Length < 6 || pass.Length > 20)
                {
                    faultMessage = faultMessage + " Password length invalid. Required at least 6 characters";
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
            }
            if (newPass != "-nochange-")
            {
                if (!(Regex.IsMatch(newPass, @"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}")))
                {
                    faultMessage = faultMessage + " Password not valid. Must include uppercase, lowercase, digit and be 6-20 characters long";
                    fault = true;
                }

            }
            if (fault)
            {
                return false;
            }


            try
            {

                das.OpenConnection();
                das.cmd.CommandType = CommandType.StoredProcedure;
                das.cmd.CommandText = "update_user_account";
                das.cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@user_password", SqlDbType.VarChar, 20);
                das.cmd.Parameters.Add("@user_new_password", SqlDbType.VarChar, 20);
                das.cmd.Parameters.Add("@user_email", SqlDbType.VarChar, 50);
                das.cmd.Parameters.Add("@user_first_name", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@user_last_name", SqlDbType.VarChar, 50);
                das.cmd.Parameters["@user_login"].Value = login;
                das.cmd.Parameters["@user_password"].Value = pass;
                das.cmd.Parameters["@user_new_password"].Value = newPass;
                das.cmd.Parameters["@user_email"].Value = email;
                das.cmd.Parameters["@user_first_name"].Value = firstName;
                das.cmd.Parameters["@user_last_name"].Value = lastName;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                das.cmd.Parameters.Add(resultParameter);
                das.cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result != 0)
                {
                    if (result == 1)
                    {
                        faultMessage = "Authentication failed - revise the login and current password";

                    }
                    else if (result == 2)
                    {
                        faultMessage = "Email " + email + " already in use";

                    }
                    else if (result == 3)
                    {
                        faultMessage = "One of obligatory parameters (login, password, email) has zero length";

                    }
                    else faultMessage = "Unknown processing error";
                    fault = true;
                }


            }
            catch (SqlException ex)
            {
                faultMessage = "User creation failed at database level " + ex.Message;
                fault = true;

            }
            finally
            {
                das.CloseConnection();
            }
            return !fault;
        }
    }
}