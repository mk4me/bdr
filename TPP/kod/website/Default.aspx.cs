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
    protected void buttonTestCSV_Click(object sender, EventArgs e)
    {
        DataTable dataTable = CSVExporter.getTestDataTable();
        CSVExporter.writeDelimitedData(dataTable, "testdata.csv", ",");
    }
}
