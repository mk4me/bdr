using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MotionDBCommons;

namespace MotionDBWebServices
{
    public partial class UserAccountCreation : System.Web.UI.Page
    {

        string errMsg = "";

        
        LoginManagerHelper lmh;
        protected void Page_Load(object sender, EventArgs e)
        {
            lmh = new LoginManagerHelper();
         

        }

        protected void btnCreateUserAccount_Click(object sender, EventArgs e)
        {

            if (!lmh.CreateUserAccount(tbLogin.Text, tbEmail.Text, tbPassword.Text, tbFirstName.Text, tbLastName.Text, out errMsg))
                lblStatus.Text = errMsg;
            else
                lblStatus.Text = "User account created";

        }

        protected void cbHasCode_CheckedChanged(object sender, EventArgs e)
        {
            if (cbHasCode.Checked) tbActivationCode.Enabled = true;

        }

        protected void btnActivateUserAccount_Click(object sender, EventArgs e)
        {
            if (!lmh.ActivateUserAccount(tbLogin.Text, tbActivationCode.Text, out errMsg))
                lblStatus.Text = errMsg;
            else
                lblStatus.Text = "User account activated";
        }
    }
}