using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PartHForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropUSGWynik.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "USGWynik");
            dropUSGWynik.DataTextField = "Value";
            dropUSGWynik.DataValueField = "Key";
            dropUSGWynik.DataBind();
        }
    }
}