using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartBForm : System.Web.UI.Page
{
    private static byte NO_DATA = 100;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartB();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }

        if (!IsPostBack)
        {
            dropOtepienie.DataSource = DatabaseProcedures.getEnumerationDecimal("Wizyta", "Otepienie");
            dropOtepienie.DataTextField = "Value";
            dropOtepienie.DataValueField = "Key";
            dropOtepienie.DataBind();

            dropPrzebyteLeczenieOperacyjne.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "PrzebyteLeczenieOperacyjnePD", NO_DATA);
            dropPrzebyteLeczenieOperacyjne.DataTextField = "Value";
            dropPrzebyteLeczenieOperacyjne.DataValueField = "Key";
            dropPrzebyteLeczenieOperacyjne.DataBind();

            dropDominujacyObjawObecnie.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "DominujacyObjawObecnie", NO_DATA);
            dropDominujacyObjawObecnie.DataTextField = "Value";
            dropDominujacyObjawObecnie.DataValueField = "Key";
            dropDominujacyObjawObecnie.DataBind();
        }
    }

    private void savePartB()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partB]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@RLS", SqlDbType.TinyInt).Value = byte.Parse(dropRLS.SelectedValue);
        cmd.Parameters.Add("@ObjawyPsychotyczne", SqlDbType.TinyInt).Value = byte.Parse(dropPsychotyczne.SelectedValue);
        cmd.Parameters.Add("@Depresja", SqlDbType.TinyInt).Value = byte.Parse(dropDepresja.SelectedValue);
        SqlParameter otepienieDecimal = new SqlParameter("@Otepienie", SqlDbType.Decimal);
        otepienieDecimal.Precision = 2;
        otepienieDecimal.Scale = 1;
        otepienieDecimal.Value = DatabaseProcedures.getDecimalOrNull(dropOtepienie.SelectedValue);
        cmd.Parameters.Add(otepienieDecimal);
        cmd.Parameters.Add("@Dyzartria", SqlDbType.TinyInt).Value = byte.Parse(dropDyzartia.SelectedValue);
        cmd.Parameters.Add("@RBD", SqlDbType.TinyInt).Value = byte.Parse(dropRBD.SelectedValue);
        cmd.Parameters.Add("@ZaburzenieRuchomosciGalekOcznych", SqlDbType.TinyInt).Value = byte.Parse(dropZaburzeniaGalek.SelectedValue);
        cmd.Parameters.Add("@Apraksja", SqlDbType.TinyInt).Value = byte.Parse(dropApraksja.SelectedValue);
        cmd.Parameters.Add("@TestKlaskania", SqlDbType.TinyInt).Value = byte.Parse(dropKlaskanie.SelectedValue);
        cmd.Parameters.Add("@ZaburzeniaWechowe", SqlDbType.TinyInt).Value = byte.Parse(dropZaburzeniaWechowe.SelectedValue);
        SqlParameter masaCialaDecimal = new SqlParameter("@MasaCiala", SqlDbType.Decimal);
        masaCialaDecimal.Precision = 4;
        masaCialaDecimal.Scale = 1;
        masaCialaDecimal.Value = DatabaseProcedures.getDecimalOrNull(textMasa.Text);
        cmd.Parameters.Add(masaCialaDecimal);
        cmd.Parameters.Add("@Drzenie", SqlDbType.TinyInt).Value = byte.Parse(dropDrzenie.SelectedValue);
        cmd.Parameters.Add("@Sztywnosc", SqlDbType.TinyInt).Value = byte.Parse(dropSztywnosc.SelectedValue);
        cmd.Parameters.Add("@Spowolnienie", SqlDbType.TinyInt).Value = byte.Parse(dropSpowolnienie.SelectedValue);
        cmd.Parameters.Add("@ObjawyInne", SqlDbType.TinyInt).Value = byte.Parse(dropObjawy.SelectedValue);
        cmd.Parameters.Add("@ObjawyInneJakie", SqlDbType.VarChar, 80).Value = textObjawy.Text.ToString();
        cmd.Parameters.Add("@CzasOFF", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCzasOFF.Text);
        cmd.Parameters.Add("@PoprawaPoLDopie", SqlDbType.TinyInt).Value = byte.Parse(dropPoprawa.SelectedValue);
        cmd.Parameters.Add("@PrzebyteLeczenieOperacyjnePD", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropPrzebyteLeczenieOperacyjne.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Nadcisnienie", SqlDbType.TinyInt).Value = byte.Parse(dropNadcisnienie.SelectedValue);
        cmd.Parameters.Add("@BlokeryKanWapn", SqlDbType.TinyInt).Value = byte.Parse(dropBlokery.SelectedValue);
        cmd.Parameters.Add("@DominujacyObjawObecnie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropDominujacyObjawObecnie.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@DominujacyObjawUwagi", SqlDbType.VarChar, 50).Value = textDominujacyObjawUwagi.Text.ToString();
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
                Session["Update"] = true;
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

    private void loadPartB()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select RLS, ObjawyPsychotyczne, Depresja, Otepienie, Dyzartria, RBD, ZaburzenieRuchomosciGalekOcznych, " +
            "Apraksja, TestKlaskania, ZaburzeniaWechowe, MasaCiala, Drzenie, Sztywnosc, Spowolnienie, " +
            "ObjawyInne, ObjawyInneJakie, CzasOFF, PoprawaPoLDopie, PrzebyteLeczenieOperacyjnePD, Nadcisnienie, BlokeryKanWapn, DominujacyObjawObecnie, DominujacyObjawUwagi from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropRLS.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["RLS"]);
                dropPsychotyczne.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ObjawyPsychotyczne"]);
                dropDepresja.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Depresja"]);
                dropOtepienie.SelectedValue = DatabaseProcedures.getDropDecimalValue(rdr["Otepienie"]);
                dropDyzartia.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Dyzartria"]);
                dropRBD.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["RBD"]);
                dropZaburzeniaGalek.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ZaburzenieRuchomosciGalekOcznych"]);
                dropApraksja.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Apraksja"]);
                dropKlaskanie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["TestKlaskania"]);
                dropZaburzeniaWechowe.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ZaburzeniaWechowe"]);
                textMasa.Text = DatabaseProcedures.getTextDecimalValue(rdr["MasaCiala"]);
                dropDrzenie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Drzenie"]);
                dropSztywnosc.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Sztywnosc"]);
                dropSpowolnienie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Spowolnienie"]);
                dropObjawy.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ObjawyInne"]);
                textObjawy.Text = DatabaseProcedures.getTextStringValue(rdr["ObjawyInneJakie"]);
                textCzasOFF.Text = DatabaseProcedures.getTextByteValue(rdr["CzasOFF"]);
                dropPoprawa.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["PoprawaPoLDopie"]);
                dropPrzebyteLeczenieOperacyjne.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["PrzebyteLeczenieOperacyjnePD"], NO_DATA.ToString());
                dropNadcisnienie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Nadcisnienie"]);
                dropBlokery.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["BlokeryKanWapn"]);
                dropDominujacyObjawObecnie.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DominujacyObjawObecnie"], NO_DATA.ToString());
                textDominujacyObjawUwagi.Text = DatabaseProcedures.getTextStringValue(rdr["DominujacyObjawUwagi"]);
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
        savePartB();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}