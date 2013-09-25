using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartGForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartG();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void savePartG()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partG]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@TestZegara", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropTestZegara.SelectedValue);
        cmd.Parameters.Add("@MMSE", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textMMSE.Text);
        cmd.Parameters.Add("@WAIS_R_Wiadomosci", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textWAISR_Wiadomosci.Text);
        cmd.Parameters.Add("@WAIS_R_PowtarzanieCyfr", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textWAISR_PowtarzanieCyfr.Text);
        cmd.Parameters.Add("@SkalaDepresjiBecka", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textSkalaDepresjiBecka.Text);
        cmd.Parameters.Add("@TestFluencjiZwierzeta", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiZwierzeta.Text);
        cmd.Parameters.Add("@TestFluencjiOstre", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiOstre.Text);
        cmd.Parameters.Add("@TestFluencjiK", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiK.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowA", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestLaczeniaPunktowA.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowB", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestLaczeniaPunktowB.Text);
        cmd.Parameters.Add("@TestUczeniaSlownoSluchowego", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestUczeniaSlownoSluchowego.Text);
        cmd.Parameters.Add("@TestStroopa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestStroopa.Text);
        cmd.Parameters.Add("@TestMinnesota", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestMinnesota.Text);
        cmd.Parameters.Add("@InnePsychologiczne", SqlDbType.VarChar, 150).Value = DatabaseProcedures.getStringOrNull(textInnePsychologiczne.Text);
        cmd.Parameters.Add("@OpisBadania", SqlDbType.VarChar, 2000).Value = DatabaseProcedures.getStringOrNull(textOpisBadania.Text);
        cmd.Parameters.Add("@Wnioski", SqlDbType.VarChar, 2000).Value = DatabaseProcedures.getStringOrNull(textWnioski.Text);
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
            success = (int)cmd.Parameters["@result"].Value;

            if (success == 0)
            {
                Response.Redirect("~/AppointmentForm.aspx");
            }
            else
            {
                labelMessage.Text = (string)cmd.Parameters["@message"].Value;
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

    private void loadPartG()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select TestZegara, MMSE, WAIS_R_Wiadomosci, WAIS_R_PowtarzanieCyfr, SkalaDepresjiBecka, TestFluencjiZwierzeta, TestFluencjiOstre, " +
            "TestFluencjiK, TestLaczeniaPunktowA, TestLaczeniaPunktowB, TestUczeniaSlownoSluchowego, TestStroopa, TestMinnesota, InnePsychologiczne, " +
            "OpisBadania, Wnioski " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropTestZegara.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["TestZegara"]);
                textMMSE.Text = DatabaseProcedures.getTextByteValue(rdr["MMSE"]);
                textWAISR_Wiadomosci.Text = DatabaseProcedures.getTextByteValue(rdr["WAIS_R_Wiadomosci"]);
                textWAISR_PowtarzanieCyfr.Text = DatabaseProcedures.getTextByteValue(rdr["WAIS_R_PowtarzanieCyfr"]);
                textSkalaDepresjiBecka.Text = DatabaseProcedures.getTextByteValue(rdr["SkalaDepresjiBecka"]);
                textTestFluencjiZwierzeta.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiZwierzeta"]);
                textTestFluencjiOstre.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiOstre"]);
                textTestFluencjiK.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiK"]);
                textTestLaczeniaPunktowA.Text = DatabaseProcedures.getTextByteValue(rdr["TestLaczeniaPunktowA"]);
                textTestLaczeniaPunktowB.Text = DatabaseProcedures.getTextByteValue(rdr["TestLaczeniaPunktowB"]);
                textTestUczeniaSlownoSluchowego.Text = DatabaseProcedures.getTextByteValue(rdr["TestUczeniaSlownoSluchowego"]);
                textTestStroopa.Text = DatabaseProcedures.getTextByteValue(rdr["TestStroopa"]);
                textTestMinnesota.Text = DatabaseProcedures.getTextByteValue(rdr["TestMinnesota"]);
                textInnePsychologiczne.Text = DatabaseProcedures.getTextStringValue(rdr["InnePsychologiczne"]);
                textOpisBadania.Text = DatabaseProcedures.getTextStringValue(rdr["OpisBadania"]);
                textWnioski.Text = DatabaseProcedures.getTextStringValue(rdr["Wnioski"]);
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

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        savePartG();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}