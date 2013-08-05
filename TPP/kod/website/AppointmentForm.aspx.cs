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
            dropAppointmentType.DataSource = DatabaseProcedures.getEnumeration("Wizyta", "RodzajWizyty");
            dropAppointmentType.DataTextField = "Value";
            dropAppointmentType.DataValueField = "Key";
            dropAppointmentType.DataBind();
        }
    }
    protected void buttonOK_Click(object sender, EventArgs e)
    {

    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }
}