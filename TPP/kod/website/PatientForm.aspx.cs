using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PatientForm : System.Web.UI.Page
{
    private bool update = false;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropGroup.DataSource = DatabaseProcedures.getEnumerationString("Pacjent", "NazwaGrupy");
            dropGroup.DataTextField = "Value";
            dropGroup.DataValueField = "Key";
            dropGroup.DataBind();

            int minYear = 1920;
            int currentYear = DateTime.Now.Year;
            for (int i = minYear; i <= currentYear; i++)
            {
                dropYear.Items.Add(new ListItem("" + i, "" + i));
            }
            dropYear.SelectedIndex = currentYear - minYear - 50;

            for (int i = 1; i <= 12; i++)
            {
                dropMonth.Items.Add(new ListItem("" + i, "" + i));
            }
            
            dropLocation.DataSource = DatabaseProcedures.getEnumerationString("Pacjent", "Lokalizacja");
            dropLocation.DataTextField = "Value";
            dropLocation.DataValueField = "Key";
            dropLocation.DataBind();
            
            for (int i = 0; i <= 10; i++)
            {
                dropElectrodes.Items.Add(new ListItem("" + i, "" + i));
            }
            dropElectrodes.SelectedIndex = 2;
        }

        if (Session["PatientNumber"] != null)
        {
            String patientNumber = Session["PatientNumber"].ToString();
            update = true;
            if (!IsPostBack)
            {
                textPatientNumber.Text = patientNumber;
                textPatientNumber.Enabled = false;
                dropGroup.Enabled = false;
                loadPatient(patientNumber);
            }
        }
        else
        {
            if (!IsPostBack)
            {
                textPatientNumber.Text = suggestNewPatientNumber(dropGroup.SelectedValue);
            }
        }
        toggleLocation();
    }

    // "Można podawać Lokalizację = NULL gdy grupa = BMT" (zawsze null, jak BMT)
    private void toggleLocation()
    {
        if (dropGroup.SelectedValue == Consts.PATIENT_BMT)
        {
            dropLocation.Visible = false;
            labelLocation.Visible = false;
            
        }
        else
        {
            dropLocation.Visible = true;
            labelLocation.Visible = true;
        }
    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        savePatient();
    }

    private void savePatient()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_patient_l]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = textPatientNumber.Text;
        cmd.Parameters.Add("@NazwaGrupy", SqlDbType.VarChar, 3).Value = dropGroup.SelectedValue;
        cmd.Parameters.Add("@RokUrodzenia", SqlDbType.SmallInt).Value = (short)int.Parse(dropYear.SelectedValue);
        cmd.Parameters.Add("@MiesiacUrodzenia", SqlDbType.TinyInt).Value = (byte)int.Parse(dropMonth.SelectedValue);
        int sex = 0;
        if (radioMan.Checked)
        {
            sex = 1;
        }
        cmd.Parameters.Add("@Plec", SqlDbType.TinyInt).Value = sex;
        if (dropLocation.Visible)
        {
            cmd.Parameters.Add("@Lokalizacja", SqlDbType.VarChar, 10).Value = dropLocation.SelectedValue;
        }
        else
        {
            cmd.Parameters.Add("@Lokalizacja", SqlDbType.VarChar, 10).Value = DBNull.Value;
        }
        cmd.Parameters.Add("@LiczbaElektrod", SqlDbType.TinyInt).Value = (byte)int.Parse(dropElectrodes.SelectedValue);
        cmd.Parameters.Add("@ZakonczenieUdzialu", SqlDbType.VarChar, 255).Value = textZakonczenieUdzialu.Text;
        cmd.Parameters.Add("@allow_update_existing", SqlDbType.Bit).Value = update;
        cmd.Parameters.Add("@actor_login", SqlDbType.VarChar, 50).Value = User.Identity.Name;
        cmd.Parameters.Add("@result", SqlDbType.Int);
        cmd.Parameters["@result"].Direction = ParameterDirection.Output;
        cmd.Parameters.Add("@message", SqlDbType.VarChar, 200);
        cmd.Parameters["@message"].Direction = ParameterDirection.Output;
        cmd.Connection = con;

        int success = 0;
        try
        {
            con.Open();
            cmd.ExecuteNonQuery();
            success = (int) cmd.Parameters["@result"].Value;
            if (success == 2)
            {
                labelMessage.Text = (string) cmd.Parameters["@message"].Value;
            }
            else
            {
                Response.Redirect("~/Main.aspx");
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
    }

    private void loadPatient(string patientNumber)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select NazwaGrupy, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, ZakonczenieUdzialu from Pacjent where NumerPacjenta = @NumerPacjenta";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = patientNumber;
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropGroup.SelectedValue = rdr["NazwaGrupy"].ToString();
                dropYear.SelectedValue = ((short)rdr["RokUrodzenia"]).ToString();
                dropMonth.SelectedValue = ((byte)rdr["MiesiacUrodzenia"]).ToString();
                int sex = ((byte)rdr["Plec"]); ;
                if (sex == 0)
                {
                    radioWoman.Checked = true;
                }
                else
                {
                    radioMan.Checked = true;
                }
                String location = DatabaseProcedures.getTextStringValue(rdr["Lokalizacja"]);
                if (location != "")
                {
                    dropLocation.SelectedValue = DatabaseProcedures.getTextStringValue(rdr["Lokalizacja"]);
                }
                dropElectrodes.SelectedValue = ((byte)rdr["LiczbaElektrod"]).ToString();
                textZakonczenieUdzialu.Text = rdr["ZakonczenieUdzialu"].ToString();
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
    }

    private string suggestNewPatientNumber(string group)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[suggest_new_patient_number_with_group]";
        cmd.Parameters.Add("@admission_date", SqlDbType.Date).Value = DateTime.Now.ToString("yyyy-MM-dd");
        cmd.Parameters.Add("@patient_group", SqlDbType.VarChar, 3).Value = group;
        cmd.Parameters.Add("@patient_number", SqlDbType.VarChar, 20);
        cmd.Parameters["@patient_number"].Direction = ParameterDirection.Output;
        cmd.Connection = con;

        string patientNumber = "";
        try
        {
            con.Open();
            cmd.ExecuteNonQuery();
            patientNumber = DatabaseProcedures.getTextStringValue(cmd.Parameters["@patient_number"].Value);
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

        return patientNumber;
    }

    protected void dropGroup_SelectedIndexChanged(object sender, EventArgs e)
    {
        textPatientNumber.Text = suggestNewPatientNumber(dropGroup.SelectedValue);
        toggleLocation();
    }
}