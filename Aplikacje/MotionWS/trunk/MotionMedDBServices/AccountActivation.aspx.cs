using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MotionDBCommons;

namespace MotionMedDBWebServices
{
    public partial class AccountActivation : System.Web.UI.Page
    {
        LoginManagerHelper lmh = new LoginManagerHelper();

        protected void Page_Load(object sender, EventArgs e)
        {

            string errMsg = "";
            bool propagate = false;
            
            if( Request.QueryString["code"] == null  || Request.QueryString["login"] == null )
            {
                lbActivationStatus.Text = "ERROR: missing login or activation code";
                return;
            }

            string ac = Request.QueryString["code"];
            string ul = Request.QueryString["login"];

            if (Request.QueryString["hmdb"] != null)
                propagate = (Request.QueryString["hmdb"] == "yes") ? true : false;

            if (!lmh.ActivateUserAccount(ul, ac, propagate, out errMsg))
                lbActivationStatus.Text = "ERROR: "+errMsg;
            else
                lbActivationStatus.Text = "User account activated";
        }
    }
}