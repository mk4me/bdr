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

namespace MotionMedDBWebServices
{
    public partial class UserAccountUpdate : System.Web.UI.Page
    {
        LoginManagerHelper lmh;
        protected void Page_Load(object sender, EventArgs e)
        {
            lmh = new LoginManagerHelper();


        }

        string faultMessage = "";

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
            if (!lmh.UpdateUserAccount(tbLogin.Text, tbEmail.Text, tbOldPassword.Text, pass, firstname, tbLastName.Text, out faultMessage))
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