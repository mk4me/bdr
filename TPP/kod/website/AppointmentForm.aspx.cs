using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class AppointmentForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropAppointmentType.DataSource = DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty");
            dropAppointmentType.DataTextField = "Value";
            dropAppointmentType.DataValueField = "Key";
            dropAppointmentType.DataBind();
            buttonOK.Enabled = false;
        }

        string patientNumber = Request.QueryString["PatientNumber"];
        if (patientNumber != null)
        {
            labelPatientNumber.Text = patientNumber;
        }
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {

    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentList.aspx?" + Request.QueryString["PatientNumber"]);
    }
}