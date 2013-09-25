using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartCForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartC();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void savePartC()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partC]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@Ldopa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(dropLdopa.SelectedValue);
        cmd.Parameters.Add("@LDopaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textLDopa.Text);
        cmd.Parameters.Add("@Agonista", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropAgonista.SelectedValue);
        cmd.Parameters.Add("@AgonistaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textAgonista.Text);
        cmd.Parameters.Add("@Amantadyna", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropAmantadyna.SelectedValue);
        cmd.Parameters.Add("@AmantadynaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textAmantadyna.Text);
        cmd.Parameters.Add("@MAOBinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropMAOBinh.SelectedValue);
        cmd.Parameters.Add("@MAOBinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textMAOBinh.Text);
        cmd.Parameters.Add("@COMTinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropCOMTinh.SelectedValue);
        cmd.Parameters.Add("@COMTinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textCOMTinh.Text);
        cmd.Parameters.Add("@Cholinolityk", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropCholinolityk.SelectedValue);
        cmd.Parameters.Add("@CholinolitykObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textCholinolityk.Text);
        cmd.Parameters.Add("@LekiInne", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropLekiInne.SelectedValue);
        cmd.Parameters.Add("@LekiInneJakie", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textLekiInne.Text);
        cmd.Parameters.Add("@L_STIMOpis", SqlDbType.VarChar, 30).Value = DatabaseProcedures.getStringOrNull(textL_STIMOpis.Text);
        cmd.Parameters.Add(saveDecimal("@L_STIMAmplitude", textL_STIMAmplitude));
        cmd.Parameters.Add(saveDecimal("@L_STIMDuration", textL_STIMDuration));
        cmd.Parameters.Add(saveDecimal("@L_STIMFrequency", textL_STIMFrequency));
        cmd.Parameters.Add("@R_STIMOpis", SqlDbType.VarChar, 30).Value = DatabaseProcedures.getStringOrNull(textR_STIMOpis.Text);
        cmd.Parameters.Add(saveDecimal("@R_STIMAmplitude", textR_STIMAmplitude));
        cmd.Parameters.Add(saveDecimal("@R_STIMDuration", textR_STIMDuration));
        cmd.Parameters.Add(saveDecimal("@R_STIMFrequency", textR_STIMFrequency));
        cmd.Parameters.Add("@Wypis_Ldopa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(dropWypis_Ldopa.SelectedValue);
        cmd.Parameters.Add("@Wypis_LDopaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_LDopa.Text);
        cmd.Parameters.Add("@Wypis_Agonista", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_Agonista.SelectedValue);
        cmd.Parameters.Add("@Wypis_AgonistaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_Agonista.Text);
        cmd.Parameters.Add("@Wypis_Amantadyna", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_Amantadyna.SelectedValue);
        cmd.Parameters.Add("@Wypis_AmantadynaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_Amantadyna.Text);
        cmd.Parameters.Add("@Wypis_MAOBinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_MAOBinh.SelectedValue);
        cmd.Parameters.Add("@Wypis_MAOBinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_MAOBinh.Text);
        cmd.Parameters.Add("@Wypis_COMTinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_COMTinh.SelectedValue);
        cmd.Parameters.Add("@Wypis_COMTinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_COMTinh.Text);
        cmd.Parameters.Add("@Wypis_Cholinolityk", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_Cholinolityk.SelectedValue);
        cmd.Parameters.Add("@Wypis_CholinolitykObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textWypis_Cholinolityk.Text);
        cmd.Parameters.Add("@Wypis_LekiInne", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropWypis_LekiInne.SelectedValue);
        cmd.Parameters.Add("@Wypis_LekiInneJakie", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textWypis_LekiInne.Text);
        cmd.Parameters.Add("@Wypis_L_STIMOpis", SqlDbType.VarChar, 30).Value = DatabaseProcedures.getStringOrNull(textWypis_L_STIMOpis.Text);
        cmd.Parameters.Add(saveDecimal("@Wypis_L_STIMAmplitude", textWypis_L_STIMAmplitude));
        cmd.Parameters.Add(saveDecimal("@Wypis_L_STIMDuration", textWypis_L_STIMDuration));
        cmd.Parameters.Add(saveDecimal("@Wypis_L_STIMFrequency", textWypis_L_STIMFrequency));
        cmd.Parameters.Add("@Wypis_R_STIMOpis", SqlDbType.VarChar, 30).Value = DatabaseProcedures.getStringOrNull(textWypis_R_STIMOpis.Text);
        cmd.Parameters.Add(saveDecimal("@Wypis_R_STIMAmplitude", textWypis_R_STIMAmplitude));
        cmd.Parameters.Add(saveDecimal("@Wypis_R_STIMDuration", textWypis_R_STIMDuration));
        cmd.Parameters.Add(saveDecimal("@Wypis_R_STIMFrequency", textWypis_R_STIMFrequency));
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

    private SqlParameter saveDecimal(string parameter, TextBox textBox)
    {
        SqlParameter decimalParameter = new SqlParameter(parameter, SqlDbType.Decimal);
        decimalParameter.Precision = 5;
        decimalParameter.Scale = 1;
        decimalParameter.Value = DatabaseProcedures.getDecimalOrNull(textBox.Text);

        return decimalParameter;
    }

    private void loadPartC()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select Ldopa, LDopaObecnie, Agonista, AgonistaObecnie, Amantadyna, AmantadynaObecnie, MAOBinh, " +
            "MAOBinhObecnie, COMTinh, COMTinhObecnie, Cholinolityk, CholinolitykObecnie, LekiInne, LekiInneJakie, " +
            "L_STIMOpis, L_STIMAmplitude, L_STIMDuration, L_STIMFrequency, R_STIMOpis, R_STIMAmplitude, R_STIMDuration, R_STIMFrequency, " +
            "Wypis_Ldopa, Wypis_LDopaObecnie, Wypis_Agonista, Wypis_AgonistaObecnie, Wypis_Amantadyna, Wypis_AmantadynaObecnie, Wypis_MAOBinh, " +
            "Wypis_MAOBinhObecnie, Wypis_COMTinh, Wypis_COMTinhObecnie, Wypis_Cholinolityk, Wypis_CholinolitykObecnie, Wypis_LekiInne, Wypis_LekiInneJakie, " +
            "Wypis_L_STIMOpis, Wypis_L_STIMAmplitude, Wypis_L_STIMDuration, Wypis_L_STIMFrequency, Wypis_R_STIMOpis, Wypis_R_STIMAmplitude, Wypis_R_STIMDuration, Wypis_R_STIMFrequency " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropLdopa.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Ldopa"]);
                textLDopa.Text = DatabaseProcedures.getTextShortValue(rdr["LDopaObecnie"]);
                dropAgonista.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Agonista"]);
                textAgonista.Text = DatabaseProcedures.getTextShortValue(rdr["AgonistaObecnie"]);
                dropAmantadyna.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Amantadyna"]);
                textAmantadyna.Text = DatabaseProcedures.getTextShortValue(rdr["AmantadynaObecnie"]);
                dropMAOBinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["MAOBinh"]);
                textMAOBinh.Text = DatabaseProcedures.getTextShortValue(rdr["MAOBinhObecnie"]);
                dropCOMTinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["COMTinh"]);
                textCOMTinh.Text = DatabaseProcedures.getTextShortValue(rdr["COMTinhObecnie"]);
                dropCholinolityk.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Cholinolityk"]);
                textCholinolityk.Text = DatabaseProcedures.getTextShortValue(rdr["CholinolitykObecnie"]);
                dropLekiInne.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["LekiInne"]);
                textLekiInne.Text = DatabaseProcedures.getTextStringValue(rdr["LekiInneJakie"]);
                textL_STIMOpis.Text = DatabaseProcedures.getTextStringValue(rdr["L_STIMOpis"]);
                textL_STIMAmplitude.Text = DatabaseProcedures.getTextDecimalValue(rdr["L_STIMAmplitude"]);
                textL_STIMDuration.Text = DatabaseProcedures.getTextDecimalValue(rdr["L_STIMDuration"]);
                textL_STIMFrequency.Text = DatabaseProcedures.getTextDecimalValue(rdr["L_STIMFrequency"]);
                textR_STIMOpis.Text = DatabaseProcedures.getTextStringValue(rdr["R_STIMOpis"]);
                textR_STIMAmplitude.Text = DatabaseProcedures.getTextDecimalValue(rdr["R_STIMAmplitude"]);
                textR_STIMDuration.Text = DatabaseProcedures.getTextDecimalValue(rdr["R_STIMDuration"]);
                textR_STIMFrequency.Text = DatabaseProcedures.getTextDecimalValue(rdr["R_STIMFrequency"]);
                dropWypis_Ldopa.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Wypis_Ldopa"]);
                textWypis_LDopa.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_LDopaObecnie"]);
                dropWypis_Agonista.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_Agonista"]);
                textWypis_Agonista.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_AgonistaObecnie"]);
                dropWypis_Amantadyna.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_Amantadyna"]);
                textWypis_Amantadyna.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_AmantadynaObecnie"]);
                dropWypis_MAOBinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_MAOBinh"]);
                textWypis_MAOBinh.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_MAOBinhObecnie"]);
                dropWypis_COMTinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_COMTinh"]);
                textWypis_COMTinh.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_COMTinhObecnie"]);
                dropWypis_Cholinolityk.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_Cholinolityk"]);
                textWypis_Cholinolityk.Text = DatabaseProcedures.getTextShortValue(rdr["Wypis_CholinolitykObecnie"]);
                dropWypis_LekiInne.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Wypis_LekiInne"]);
                textWypis_LekiInne.Text = DatabaseProcedures.getTextStringValue(rdr["Wypis_LekiInneJakie"]);
                textWypis_L_STIMOpis.Text = DatabaseProcedures.getTextStringValue(rdr["Wypis_L_STIMOpis"]);
                textWypis_L_STIMAmplitude.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_L_STIMAmplitude"]);
                textWypis_L_STIMDuration.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_L_STIMDuration"]);
                textWypis_L_STIMFrequency.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_L_STIMFrequency"]);
                textWypis_R_STIMOpis.Text = DatabaseProcedures.getTextStringValue(rdr["Wypis_R_STIMOpis"]);
                textWypis_R_STIMAmplitude.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_R_STIMAmplitude"]);
                textWypis_R_STIMDuration.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_R_STIMDuration"]);
                textWypis_R_STIMFrequency.Text = DatabaseProcedures.getTextDecimalValue(rdr["Wypis_R_STIMFrequency"]);
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
        savePartC();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}