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
    public partial class UserAccountUpdate : System.Web.UI.Page
    {
        DatabaseAccessService das;
        protected void Page_Load(object sender, EventArgs e)
        {
            das = new DatabaseAccessService();


        }

        string faultMessage = "";

        public bool UpdateUserAccount(string login, string email, string pass, string newPass, string firstName, string lastName)
        {
            Boolean fault = false;
            int result = 0;

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
            if (pass != "-nochange-")
            {
                if (!(Regex.IsMatch(pass, @"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}")))
                {
                    faultMessage = faultMessage + " Password not valid. Must include uppercase, lowercase, digit and be 6-20 characters long";
                    fault = true;
                }

            }
            if (fault)
            {
                lblStatus.Text = faultMessage;
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

        protected void btnCreateUserAccount_Click(object sender, EventArgs e)
        {
            if (!(cbChangeDetails.Checked || cbChangePassword.Checked))
            {
                lblStatus.Text = "Engage at least one section of the form!";
            }
            string pass = "-nochange-";
            string firstname = "-nochange-";
            if (cbChangePassword.Checked) pass = tbPassword.Text;
            if (cbChangeDetails.Checked) firstname = tbFirstName.Text;
            if (!this.UpdateUserAccount(tbLogin.Text, tbEmail.Text, tbOldPassword.Text, pass, firstname, tbLastName.Text))
                lblStatus.Text = faultMessage;
            else
                lblStatus.Text = "User account updated";
        }

        protected void CheckBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (cbChangePassword.Checked)
            {
                tbPassword.Enabled = true;
                tbRetypePassword.Enabled = true;
            }
            else
            {
                tbPassword.Text = "";
                tbRetypePassword.Text = "";
                tbPassword.Enabled = false;
                tbRetypePassword.Enabled = false;
            }
        }

        protected void cbChangeDetails_CheckedChanged(object sender, EventArgs e)
        {
            if (cbChangeDetails.Checked)
            {
                tbEmail.Enabled = true;
                tbFirstName.Enabled = true;
                tbLastName.Enabled = true;
            }
            else
            {
                tbEmail.Text = "";
                tbFirstName.Text = "";
                tbLastName.Text = "";
                tbEmail.Enabled = false;
                tbFirstName.Enabled = false;
                tbLastName.Enabled = false;
            }
        }


    }
}