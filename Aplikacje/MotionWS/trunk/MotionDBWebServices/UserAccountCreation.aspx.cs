using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MotionDBCommons;
using System.Text.RegularExpressions;
using System.Data;
using System.Data.SqlClient;

namespace MotionDBWebServices
{
    public partial class UserAccountCreation : System.Web.UI.Page
    {

        string faultMessage = "";

        public bool CreateUserAccount(string login, string email, string pass, string firstName, string lastName)
        {
            Boolean fault = false;
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
                lblStatus.Text = faultMessage;
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

                das.cmd.Parameters["@user_login"].Value = login;
                das.cmd.Parameters["@user_password"].Value = pass;
                das.cmd.Parameters["@user_email"].Value = email;
                das.cmd.Parameters["@user_first_name"].Value = firstName;
                das.cmd.Parameters["@user_last_name"].Value = lastName;
                das.cmd.Parameters["@activation_code"].Value = code;
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

        public bool ActivateUserAccount(string login, string password, string activationCode)
        {

            int result = 0;

            try
            {

                das.OpenConnection();
                das.cmd.CommandType = CommandType.StoredProcedure;
                das.cmd.CommandText = "activate_user_account";
                das.cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                das.cmd.Parameters.Add("@user_password", SqlDbType.VarChar, 20);
                das.cmd.Parameters.Add("@activation_code", SqlDbType.VarChar, 10);

                das.cmd.Parameters["@user_login"].Value = login;
                das.cmd.Parameters["@user_password"].Value = password;
                das.cmd.Parameters["@activation_code"].Value = activationCode;
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                das.cmd.Parameters.Add(resultParameter);
                das.cmd.ExecuteNonQuery();

                result = (int)resultParameter.Value;
                if (result == 1)
                {
                    faultMessage = "User login, password and activation code combination is invalid";
                    return false;
                }

            }
            catch (SqlException ex)
            {
                faultMessage = "Account activation failed at database level ";
                return false;
            }
            finally
            {
                das.CloseConnection();
            }
            return true;
        }

        
        DatabaseAccessService das;
        protected void Page_Load(object sender, EventArgs e)
        {
            das = new DatabaseAccessService();
         

        }

        protected void btnCreateUserAccount_Click(object sender, EventArgs e)
        {
            if (!this.CreateUserAccount(tbLogin.Text, tbEmail.Text, tbPassword.Text, tbFirstName.Text, tbLastName.Text))
                lblStatus.Text = faultMessage;
            else
                lblStatus.Text = "User account created";

        }

        protected void cbHasCode_CheckedChanged(object sender, EventArgs e)
        {
            if (cbHasCode.Checked) tbActivationCode.Enabled = true;

        }

        protected void btnActivateUserAccount_Click(object sender, EventArgs e)
        {
            if (!this.ActivateUserAccount(tbLogin.Text, tbPassword.Text, tbActivationCode.Text))
                lblStatus.Text = faultMessage;
            else
                lblStatus.Text = "User account activated";
        }
    }
}