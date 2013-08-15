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
        if (Session["PatientNumber"] != null)
        {
            labelPatientNumber.Text = "Numer pacjenta: " + Session["PatientNumber"].ToString();
            Dictionary<decimal, string> appointmentTypes = DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty");
            Dictionary<decimal, DateTime> existingAppointments = getAppointments(Session["PatientNumber"].ToString(), appointmentTypes);

            TableHeaderRow header = new TableHeaderRow();
            TableHeaderCell headerCell1 = new TableHeaderCell();
            TableHeaderCell headerCell2 = new TableHeaderCell();
            header.Cells.Add(headerCell1);
            header.Cells.Add(headerCell2);
            tableAppointments.Rows.Add(header);
            headerCell1.Text = "Data";
            headerCell2.Text = "Typ wizyty";

            foreach (KeyValuePair<decimal, string> appointmentType in DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty"))
            {
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                TableCell cell2 = new TableCell();
                TableCell cell3 = new TableCell();
                row.Cells.Add(cell1);
                row.Cells.Add(cell2);
                row.Cells.Add(cell3);
                tableAppointments.Rows.Add(row);

                AppointmentSelection appointment = null;
                foreach(KeyValuePair<decimal, DateTime> existingAppointment in existingAppointments)
                {
                    if (existingAppointment.Key == appointmentType.Key)
                    {
                        appointment = new AppointmentSelection(existingAppointment.Value, appointmentType.Value, appointmentType.Key, this);
                        cell1.Controls.Add(appointment.labelDate);
                        cell2.Controls.Add(appointment.labelType);
                        cell3.Controls.Add(appointment.buttonEdit);
                        cell3.Controls.Add(appointment.buttonDelete);
                    }
                }

                if (appointment == null)
                {
                    appointment = new AppointmentSelection(appointmentType.Value, appointmentType.Key, this);
                    cell2.Controls.Add(appointment.labelType);
                    cell3.Controls.Add(appointment.buttonNew);
                }
            }
        }
    }

    private Dictionary<decimal, DateTime> getAppointments(string patientNumber, Dictionary<decimal, string> appointmentTypes)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select DataPrzyjecia, RodzajWizyty from Wizyta where exists (select IdPacjent from Pacjent where Wizyta.IdPacjent = Pacjent.IdPacjent and Pacjent.NumerPacjenta = '" + patientNumber + "')";
        cmd.Connection = con;

        Dictionary<decimal, DateTime> dictrionary = new Dictionary<decimal, DateTime>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                dictrionary.Add((decimal)rdr["RodzajWizyty"], (DateTime)rdr["DataPrzyjecia"]);
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

        return dictrionary;
    }

    private class AppointmentSelection
    {
        public decimal typeKey;
        public Label labelDate;
        public Label labelType;
        public Button buttonNew;
        public Button buttonEdit;
        public Button buttonDelete;
        private Page page;

        public AppointmentSelection(string typeValue, decimal typeKey, Page page)
        {
            this.page = page;
            labelType = new Label();
            labelType.Text = typeValue;
            this.typeKey = typeKey;
            buttonNew = new Button();
            buttonNew.Text = "Utwórz";
            buttonNew.Click += new System.EventHandler(buttonNew_Click);
        }

        public AppointmentSelection(DateTime date, string typeValue, decimal typeKey, Page page)
        {
            this.page = page;
            labelDate = new Label();
            labelDate.Text = date.ToString("yyyy-MM-dd");
            labelType = new Label();
            labelType.Text = typeValue;
            this.typeKey = typeKey;
            buttonEdit = new Button();
            buttonEdit.Text = "Edytuj";
            buttonEdit.Click += new System.EventHandler(buttonEdit_Click);
            buttonDelete = new Button();
            buttonDelete.Text = "Usuń";
        }

        protected void buttonNew_Click(object sender, EventArgs e)
        {
            page.Session["PatientNumber"] = page.Session["PatientNumber"];
            page.Session["AppointmentType"] = typeKey;
            page.Session["Update"] = false;
            page.Response.Redirect("~/AppointmentForm.aspx");
        }

        protected void buttonEdit_Click(object sender, EventArgs e)
        {
            page.Session["PatientNumber"] = page.Session["PatientNumber"];
            page.Session["AppointmentType"] = typeKey;
            page.Session["Update"] = true;
            page.Response.Redirect("~/AppointmentForm.aspx");
        }
    }

    protected void buttonBack_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }
}