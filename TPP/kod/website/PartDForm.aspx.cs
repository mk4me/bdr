using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PartDForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropPrzebyteLeczenieOperacyjne.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "PrzebyteLeczenieOperacyjnePD");
            dropPrzebyteLeczenieOperacyjne.DataTextField = "Value";
            dropPrzebyteLeczenieOperacyjne.DataValueField = "Key";
            dropPrzebyteLeczenieOperacyjne.DataBind();

            dropDominujacyObjawObecnie.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "DominujacyObjawObecnie");
            dropDominujacyObjawObecnie.DataTextField = "Value";
            dropDominujacyObjawObecnie.DataValueField = "Key";
            dropDominujacyObjawObecnie.DataBind();

            dropWynikWechu.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "WynikWechu");
            dropWynikWechu.DataTextField = "Value";
            dropWynikWechu.DataValueField = "Key";
            dropWynikWechu.DataBind();

            dropLimitDysfagii.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "LimitDysfagii");
            dropLimitDysfagii.DataTextField = "Value";
            dropLimitDysfagii.DataValueField = "Key";
            dropLimitDysfagii.DataBind();
        }
    }
}