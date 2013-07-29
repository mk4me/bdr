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
            listPatients.DataSource = getPatients();
            listPatients.DataBind();
        }
    }

    private List<string> getPatients()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select NumerPacjenta from Pacjent";
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
        Response.Redirect("~/PatientForm.aspx");
    }
    protected void buttonDeletePatient_Click(object sender, EventArgs e)
    {
        deletePatient(listPatients.SelectedValue);
        Response.Redirect(Request.RawUrl);
    }

    private void deletePatient(string patientNumber)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "delete from Pacjent where NumerPacjenta = '" + patientNumber + "'";
        cmd.Connection = con;

        List<string> list = new List<string>();

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
        Response.Redirect("~/PatientForm.aspx?PatientNumber=" + listPatients.SelectedValue);
    }
}