using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class ExaminationList : System.Web.UI.Page
{
    private int appointmentId;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["AppointmentId"] != null)
        {
            appointmentId = (int)Session["AppointmentId"];
            labelAppointmentId.Text = "Wizyta numer: " + appointmentId;

            List<ExaminationSelection> existingExaminations = getExaminations(appointmentId);

            TableHeaderRow header = new TableHeaderRow();
            TableHeaderCell headerCell1 = new TableHeaderCell();
            TableHeaderCell headerCell2 = new TableHeaderCell();
            header.Cells.Add(headerCell1);
            header.Cells.Add(headerCell2);
            tableExaminations.Rows.Add(header);
            headerCell1.Text = "Badania";

            bool exists = false;
            foreach (ExaminationSelection existingExamination in existingExaminations)
            {
                exists = true;
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                TableCell cell2 = new TableCell();
                row.Cells.Add(cell1);
                row.Cells.Add(cell2);
                tableExaminations.Rows.Add(row);
                cell1.Controls.Add(existingExamination.labelExamination);
                cell2.Controls.Add(existingExamination.buttonEdit);
                cell2.Controls.Add(existingExamination.buttonDelete);
            }

            if (!exists)
            {
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                row.Cells.Add(cell1);
                tableExaminations.Rows.Add(row);
                Label labelNoExaminations = new Label();
                labelNoExaminations.Text = "Brak badań dla tej wizyty";
                cell1.Controls.Add(labelNoExaminations);
            }
        }
    }

    private List<ExaminationSelection> getExaminations(int appointmentId)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select IdBadanie from Badanie where exists (select IdWizyta from Wizyta where Badanie.IdWizyta = Wizyta.IdWizyta and Wizyta.IdWizyta = " + appointmentId + ")";
        cmd.Connection = con;

        List<ExaminationSelection> list = new List<ExaminationSelection>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                ExaminationSelection appointment = new ExaminationSelection((int)rdr["IdBadanie"], this);
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

    protected void buttonNewUPDRS_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/UPDRSForm.aspx");
    }

    private class ExaminationSelection
    {
        private int idBadanie;
        private ExaminationList page;
        public Label labelExamination;
        public Button buttonEdit;
        public Button buttonDelete;

        public ExaminationSelection(int id, ExaminationList page)
        {
            idBadanie = id;
            this.page = page;
            labelExamination = new Label();
            labelExamination.Text = "Badanie nr " + id;
            buttonEdit = new Button();
            buttonEdit.Text = "Edytuj";
            buttonEdit.Click += new System.EventHandler(buttonEdit_Click);
            buttonDelete = new Button();
            buttonDelete.Text = "Usuń";
            buttonDelete.Click += new System.EventHandler(buttonDelete_Click);
        }

        protected void buttonEdit_Click(object sender, EventArgs e)
        {
            //page.Session["PatientNumber"] = page.Session["PatientNumber"];
            //page.Session["AppointmentType"] = typeKey;
            //page.Session["Update"] = true;
            //page.Session["AppointmentId"] = idAppointment;
            page.Response.Redirect("~/UPDRSForm.aspx");
        }

        protected void buttonDelete_Click(object sender, EventArgs e)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.Text;
            cmd.CommandText = "delete from Badanie where IdBadanie = " + idBadanie;
            cmd.Connection = con;

            List<string> list = new List<string>();

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
        Response.Redirect("~/AppointmentList.aspx");
    }
}