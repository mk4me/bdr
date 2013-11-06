using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Web.Configuration;

public partial class Main : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            listPatientsBMT.DataSource = getPatients("BMT");
            listPatientsBMT.DataBind();
            listPatientsDBS.DataSource = getPatients("DBS");
            listPatientsDBS.DataBind();
            listPatientsPOP.DataSource = getPatients("POP");
            listPatientsPOP.DataBind();
            toggleButtons(false);
        }
    }

    private List<string> getPatients(string group)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select NumerPacjenta from Pacjent where NazwaGrupy = '" + group + "'";
        cmd.Connection = con;

        List<string> list = new List<string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                string numerPacjenta = (string) rdr["NumerPacjenta"];

                list.Add(numerPacjenta);
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

    protected void buttonNewPatient_Click(object sender, EventArgs e)
    {
        Session.Remove("PatientNumber");
        Response.Redirect("~/PatientForm.aspx");
    }
    // todo: remove else if's
    protected void buttonDeletePatient_Click(object sender, EventArgs e)
    {
        if (listPatientsBMT.SelectedIndex >= 0)
        {
            deletePatient(listPatientsBMT.SelectedValue);
            Response.Redirect(Request.RawUrl);
        }
        else if (listPatientsDBS.SelectedIndex >= 0)
        {
            deletePatient(listPatientsDBS.SelectedValue);
            Response.Redirect(Request.RawUrl);
        }
        else if (listPatientsPOP.SelectedIndex >= 0)
        {
            deletePatient(listPatientsPOP.SelectedValue);
            Response.Redirect(Request.RawUrl);
        }
    }

    private void deletePatient(string patientNumber)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "delete from Pacjent where NumerPacjenta = '" + patientNumber + "'";
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
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
    }

    protected void buttonEditPatient_Click(object sender, EventArgs e)
    {
        if (listPatientsBMT.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsBMT.SelectedValue;
            Response.Redirect("~/PatientForm.aspx");
        }
        else if (listPatientsDBS.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsDBS.SelectedValue;
            Response.Redirect("~/PatientForm.aspx");
        }
        else if (listPatientsPOP.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsPOP.SelectedValue;
            Response.Redirect("~/PatientForm.aspx");
        }
    }

    protected void buttonShowAppointments_Click(object sender, EventArgs e)
    {
        if (listPatientsBMT.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsBMT.SelectedValue;
            Response.Redirect("~/AppointmentList.aspx");
        }
        else if (listPatientsDBS.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsDBS.SelectedValue;
            Response.Redirect("~/AppointmentList.aspx");
        }
        else if (listPatientsPOP.SelectedIndex >= 0)
        {
            Session["PatientNumber"] = listPatientsPOP.SelectedValue;
            Response.Redirect("~/AppointmentList.aspx");
        }
    }

    protected void listPatients_SelectedIndexChanged(object sender, System.EventArgs e)
    {
        if (sender.Equals(listPatientsBMT))
        {
            listPatientsDBS.ClearSelection();
            listPatientsPOP.ClearSelection();
        }
        else if (sender.Equals(listPatientsDBS))
        {
            listPatientsBMT.ClearSelection();
            listPatientsPOP.ClearSelection();
        }
        else if (sender.Equals(listPatientsPOP))
        {
            listPatientsBMT.ClearSelection();
            listPatientsDBS.ClearSelection();
        }
        if (listPatientsBMT.SelectedIndex >= 0 || listPatientsDBS.SelectedIndex >= 0 || listPatientsPOP.SelectedIndex >= 0)
        {
            toggleButtons(true);
        }
    }

    private void toggleButtons(bool enable)
    {
        buttonEditPatient.Enabled = enable;
        buttonDeletePatient.Enabled = enable;
        buttonShowAppointments.Enabled = enable;
    }
}