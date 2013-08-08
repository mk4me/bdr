using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class AppointmentList : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            buttonEditAppointment.Enabled = false;
            buttonDeleteAppointment.Enabled = false;

        }

        string patientNumber = Request.QueryString["PatientNumber"];
        if (patientNumber != null)
        {
            List<AppointmentSelection> list = getAppointments(patientNumber);
            if (list.Count() > 0)
            {
                TableHeaderRow header = new TableHeaderRow();
                TableHeaderCell headerCell1 = new TableHeaderCell();
                TableHeaderCell headerCell2 = new TableHeaderCell();
                TableHeaderCell headerCell3 = new TableHeaderCell();
                header.Cells.Add(headerCell1);
                header.Cells.Add(headerCell2);
                header.Cells.Add(headerCell3);
                tableAppointments.Rows.Add(header);
                headerCell1.Text = "";
                headerCell2.Text = "Data";
                headerCell3.Text = "Typ wizyty";
                foreach (AppointmentSelection appointment in getAppointments(patientNumber))
                {


                    TableRow row = new TableRow();
                    TableCell cell1 = new TableCell();
                    TableCell cell2 = new TableCell();
                    TableCell cell3 = new TableCell();
                    row.Cells.Add(cell1);
                    row.Cells.Add(cell2);
                    row.Cells.Add(cell3);
                    tableAppointments.Rows.Add(row);
                    cell1.Controls.Add(appointment.radioSelected);
                    cell2.Controls.Add(appointment.date);
                    cell3.Controls.Add(appointment.type);
                }
            }
            else
            {
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                row.Cells.Add(cell1);
                tableAppointments.Rows.Add(row);
                cell1.Text = "Brak wizyt.";
            }
        }
    }

    private List<AppointmentSelection> getAppointments(string patientNumber)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select DataPrzyjecia, RodzajWizyty from Wizyta where exists (select IdPacjent from Pacjent where Wizyta.IdPacjent = Pacjent.IdPacjent and Pacjent.NumerPacjenta = '" + patientNumber + "')";
        cmd.Connection = con;

        List<AppointmentSelection> list = new List<AppointmentSelection>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                AppointmentSelection appointmentSelection = new AppointmentSelection("SelectionGroup", (DateTime)rdr["DataPrzyjecia"], (decimal)rdr["RodzajWizyty"], DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty"));
                list.Add(appointmentSelection);
            }
        }
        catch (SqlException ex)
        {
            labelMessage.Text = ex.Message;
        }
        finally
        {
            cmd.Dispose();
            if (con != null)
            {
                con.Close();
            }
        }

        return list;
    }

    private class AppointmentSelection
    {
        public RadioButton radioSelected;
        public Label date;
        public Label type;

        public AppointmentSelection(string radioGroup, DateTime dateValue, decimal typeKey, Dictionary<decimal, string> typeList)
        {
            radioSelected = new RadioButton();
            radioSelected.GroupName = radioGroup;
            date = new Label();
            date.Text = dateValue.ToString("yyyy-MM-dd");
            type = new Label();
            string typeValue;
            typeList.TryGetValue(typeKey, out typeValue);
            type.Text = typeValue;
        }
    }

    protected void buttonBack_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }

    protected void buttonNewAppointment_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx?PatientNumber=" + Request.QueryString["PatientNumber"]);
    }
}