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
            List<AppointmentSelection> existingAppointments = getAppointments(Session["PatientNumber"].ToString(), appointmentTypes);

            TableHeaderRow header = new TableHeaderRow();
            TableHeaderCell headerCell1 = new TableHeaderCell();
            TableHeaderCell headerCell2 = new TableHeaderCell();
            header.Cells.Add(headerCell1);
            header.Cells.Add(headerCell2);
            tableAppointments.Rows.Add(header);
            headerCell1.Text = "Data";
            headerCell2.Text = "Typ wizyty";

            foreach (KeyValuePair<decimal, string> appointmentType in appointmentTypes)
            {
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                TableCell cell2 = new TableCell();
                TableCell cell3 = new TableCell();
                row.Cells.Add(cell1);
                row.Cells.Add(cell2);
                row.Cells.Add(cell3);
                Utils.colorRow(tableAppointments, row);
                tableAppointments.Rows.Add(row);

                bool exists = false;
                foreach(AppointmentSelection existingAppointment in existingAppointments)
                {
                    if (existingAppointment.typeKey == appointmentType.Key)
                    {
                        exists = true;
                        cell1.Controls.Add(existingAppointment.labelDate);
                        cell2.Controls.Add(existingAppointment.labelType);
                        cell3.Controls.Add(existingAppointment.buttonEdit);
                        cell3.Controls.Add(existingAppointment.buttonDelete);
                    }
                }

                if (!exists)
                {
                    AppointmentSelection appointment = new AppointmentSelection(appointmentType.Value, appointmentType.Key, this);
                    cell2.Controls.Add(appointment.labelType);
                    cell3.Controls.Add(appointment.buttonNew);
                }
            }
        }
    }

    private List<AppointmentSelection> getAppointments(string patientNumber, Dictionary<decimal, string> appointmentTypes)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select IdWizyta, DataPrzyjecia, RodzajWizyty from Wizyta where exists (select IdPacjent from Pacjent where Wizyta.IdPacjent = Pacjent.IdPacjent and Pacjent.NumerPacjenta = '" + patientNumber + "')";
        cmd.Connection = con;

        List<AppointmentSelection> list = new List<AppointmentSelection>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                decimal typeKey = (decimal)rdr["RodzajWizyty"];
                string typeValue = "";
                appointmentTypes.TryGetValue(typeKey, out typeValue);
                AppointmentSelection appointment = new AppointmentSelection((int)rdr["IdWizyta"], (DateTime)rdr["DataPrzyjecia"], typeValue, typeKey, this);
                list.Add(appointment);
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
        private int idAppointment;
        public decimal typeKey;
        public Label labelDate;
        public Label labelType;
        public Button buttonNew;
        public Button buttonEdit;
        public Button buttonDelete;
        private AppointmentList page;
        private static Unit UNIT_WIDTH = new Unit(100);
        private static Unit UNIT_WIDTH_DELETE = new Unit(50);

        public AppointmentSelection(string typeValue, decimal typeKey, AppointmentList page)
        {
            this.page = page;
            labelType = new Label();
            labelType.Text = typeValue;
            this.typeKey = typeKey;
            buttonNew = new Button();
            buttonNew.Width = UNIT_WIDTH;
            buttonNew.Text = "Utwórz";
            buttonNew.Click += new System.EventHandler(buttonNew_Click);
        }

        public AppointmentSelection(int id, DateTime date, string typeValue, decimal typeKey, AppointmentList page)
        {
            this.page = page;
            idAppointment = id;
            labelDate = new Label();
            labelDate.Text = date.ToString("yyyy-MM-dd");
            labelType = new Label();
            labelType.Text = typeValue;
            this.typeKey = typeKey;
            buttonEdit = new Button();
            buttonEdit.Width = UNIT_WIDTH;
            buttonEdit.Text = "Edytuj";
            buttonEdit.Click += new System.EventHandler(buttonEdit_Click);
            buttonDelete = new Button();
            buttonDelete.Width = UNIT_WIDTH_DELETE;
            buttonDelete.Text = "Usuń";
            buttonDelete.Style.Add("margin-left", "50px");
            buttonDelete.Click += new System.EventHandler(buttonDelete_Click);
            buttonDelete.OnClientClick = "return confirm('Czy na pewno usunąć daną wizytę?');";
        }

        protected void buttonNew_Click(object sender, EventArgs e)
        {
            page.Session["PatientNumber"] = page.Session["PatientNumber"];
            page.Session["AppointmentType"] = typeKey;
            page.Session["Update"] = false;
            page.Session["AppointmentId"] = null;
            page.Response.Redirect("~/AppointmentForm.aspx");
        }

        protected void buttonEdit_Click(object sender, EventArgs e)
        {
            page.Session["PatientNumber"] = page.Session["PatientNumber"];
            page.Session["AppointmentType"] = typeKey;
            page.Session["Update"] = true;  // todo: use Session["AppointmentType"] != null instead
            page.Session["AppointmentId"] = idAppointment;
            page.Response.Redirect("~/AppointmentForm.aspx");
        }

        protected void buttonDelete_Click(object sender, EventArgs e)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.Text;
            cmd.CommandText = "delete from Wizyta where IdWizyta = " + idAppointment + ";delete from Badanie where IdWizyta = " + idAppointment;
            cmd.Connection = con;

            try
            {
                con.Open();
                SqlDataReader rdr = cmd.ExecuteReader();
            }
            catch (SqlException ex)
            {
                page.labelMessage.Text = ex.Message;
            }
            finally
            {
                cmd.Dispose();
                if (con != null)
                {
                    con.Close();
                }
                page.Response.Redirect(page.Request.RawUrl);
            }
        }
    }

    protected void buttonBack_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }
}