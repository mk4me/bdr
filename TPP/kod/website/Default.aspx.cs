using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class _Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void buttonExportCSV_Click(object sender, EventArgs e)
    {
        DataTable dataTable = CSVExporter.getDatabaseCopy();
        CSVExporter.writeDelimitedData(dataTable, "BD_" + DateTime.Now.ToString("yyyy-MM-dd") + ".csv", ";");
    }
}
