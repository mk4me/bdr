using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PartEForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropAddItems(Page, DatabaseProcedures.getEnumerationByte("Wizyta", "_ObjawAutonomiczny"));

        }
    }

    private void dropAddItems(Control parent, Dictionary<byte, string> items)
    {
        foreach (Control control in parent.Controls)
        {
            DropDownList drop = control as DropDownList;
            if (drop != null)
            {
                drop.DataSource = items;
                drop.DataTextField = "Value";
                drop.DataValueField = "Key";
                drop.DataBind();
            }

            if (control.HasControls())
            {
                dropAddItems(control, items);
            }
        }
    }
}