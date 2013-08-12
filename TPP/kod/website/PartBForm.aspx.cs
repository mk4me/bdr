using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PartBForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropOtepienie.DataSource = DatabaseProcedures.getEnumerationDecimal("Wizyta", "Otepienie");
            dropOtepienie.DataTextField = "Value";
            dropOtepienie.DataValueField = "Key";
            dropOtepienie.DataBind();
        }
    }
}