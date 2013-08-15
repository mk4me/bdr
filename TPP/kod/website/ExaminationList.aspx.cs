using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class ExaminationList : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null)
        {
            labelPatientNumber.Text = "Numer pacjenta: " + Session["PatientNumber"].ToString();
        }
    }

    protected void buttonNewUPDRS_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/UPDRSForm.aspx");
    }

    protected void buttonNewOculography_Click(object sender, EventArgs e)
    {

    }
}