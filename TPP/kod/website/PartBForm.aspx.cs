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
    private static decimal NO_DATA_DECIMAL = 100;
    private List<Tuple<CheckBox, byte>> toxicChoiceList = new List<Tuple<CheckBox, byte>>();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                initControls();
                loadPartB();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void initControls()
    {
        dropPrzebyteLeczenieOperacyjne.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "PrzebyteLeczenieOperacyjnePD", NO_DATA);
        dropPrzebyteLeczenieOperacyjne.DataTextField = "Value";
        dropPrzebyteLeczenieOperacyjne.DataValueField = "Key";
        dropPrzebyteLeczenieOperacyjne.DataBind();

        dropCigarettes.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Papierosy", NO_DATA);
        dropCigarettes.DataTextField = "Value";
        dropCigarettes.DataValueField = "Key";
        dropCigarettes.DataBind();

        dropCoffee.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Kawa", NO_DATA);
        dropCoffee.DataTextField = "Value";
        dropCoffee.DataValueField = "Key";
        dropCoffee.DataBind();

        dropAlcohol.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Alkohol", NO_DATA);
        dropAlcohol.DataTextField = "Value";
        dropAlcohol.DataValueField = "Key";
        dropAlcohol.DataBind();

        dropZamieszkanie.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Zamieszkanie", NO_DATA);
        dropZamieszkanie.DataTextField = "Value";
        dropZamieszkanie.DataValueField = "Key";
        dropZamieszkanie.DataBind();

        checkListToxic.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "NarazenieNaToks");
        checkListToxic.DataTextField = "Value";
        checkListToxic.DataValueField = "Key";
        checkListToxic.DataBind();

        dropDominujacyObjawObecnie.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "DominujacyObjawObecnie", NO_DATA);
        dropDominujacyObjawObecnie.DataTextField = "Value";
        dropDominujacyObjawObecnie.DataValueField = "Key";
        dropDominujacyObjawObecnie.DataBind();

        checkListObjawyAutonomiczne.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "ObjawyAutonomiczne");
        checkListObjawyAutonomiczne.DataTextField = "Value";
        checkListObjawyAutonomiczne.DataValueField = "Key";
        checkListObjawyAutonomiczne.DataBind();

        dropOtepienie.DataSource = DatabaseProcedures.getEnumerationDecimalWithNoData("Wizyta", "Otepienie", NO_DATA_DECIMAL);
        dropOtepienie.DataTextField = "Value";
        dropOtepienie.DataValueField = "Key";
        dropOtepienie.DataBind();

        Utils.setSelectedCheckListItems(DatabaseProcedures.getMultiChoice("NarazenieNaToks", int.Parse(Session["AppointmentId"].ToString())), checkListToxic);
        Utils.setSelectedCheckListItems(DatabaseProcedures.getMultiChoice("ObjawyAutonomiczne", int.Parse(Session["AppointmentId"].ToString())), checkListObjawyAutonomiczne);
    }

    private void savePartB()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partB]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@PrzebyteLeczenieOperacyjnePD", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropPrzebyteLeczenieOperacyjne.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Papierosy", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropCigarettes.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Kawa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropCoffee.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ZielonaHerbata", SqlDbType.TinyInt).Value = byte.Parse(dropGreenTea.SelectedValue);
        cmd.Parameters.Add("@Alkohol", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropAlcohol.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ZabiegowWZnieczOgPrzedRozpoznaniemPD", SqlDbType.TinyInt).Value = byte.Parse(textTreatmentNumber.Text);
        cmd.Parameters.Add("@Zamieszkanie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropZamieszkanie.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Uwagi", SqlDbType.VarChar, 50).Value = textNotes.Text;
        cmd.Parameters.Add("@Nadcisnienie", SqlDbType.TinyInt).Value = byte.Parse(dropNadcisnienie.SelectedValue);
        cmd.Parameters.Add("@BlokeryKanWapn", SqlDbType.TinyInt).Value = byte.Parse(dropBlokery.SelectedValue);
        cmd.Parameters.Add("@DominujacyObjawObecnie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropDominujacyObjawObecnie.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@DominujacyObjawUwagi", SqlDbType.VarChar, 50).Value = textDominujacyObjawUwagi.Text.ToString();
        cmd.Parameters.Add("@RLS", SqlDbType.TinyInt).Value = byte.Parse(dropRLS.SelectedValue);
        cmd.Parameters.Add("@ObjawyPsychotyczne", SqlDbType.TinyInt).Value = byte.Parse(dropPsychotyczne.SelectedValue);
        cmd.Parameters.Add("@Depresja", SqlDbType.TinyInt).Value = byte.Parse(dropDepresja.SelectedValue);
        SqlParameter otepienieDecimal = new SqlParameter("@Otepienie", SqlDbType.Decimal);
        otepienieDecimal.Precision = 2;
        otepienieDecimal.Scale = 1;
        otepienieDecimal.Value = DatabaseProcedures.getDecimalOrNullWithNoData(dropOtepienie.SelectedValue, NO_DATA_DECIMAL.ToString());
        cmd.Parameters.Add(otepienieDecimal);
        cmd.Parameters.Add("@Dyzartria", SqlDbType.TinyInt).Value = byte.Parse(dropDyzartia.SelectedValue);
        cmd.Parameters.Add("@DysfagiaObjaw", SqlDbType.TinyInt).Value = byte.Parse(dropDysfagiaObjaw.SelectedValue);
        cmd.Parameters.Add("@RBD", SqlDbType.TinyInt).Value = byte.Parse(dropRBD.SelectedValue);
        cmd.Parameters.Add("@ZaburzenieRuchomosciGalekOcznych", SqlDbType.TinyInt).Value = byte.Parse(dropZaburzeniaGalek.SelectedValue);
        cmd.Parameters.Add("@Apraksja", SqlDbType.TinyInt).Value = byte.Parse(dropApraksja.SelectedValue);
        cmd.Parameters.Add("@TestKlaskania", SqlDbType.TinyInt).Value = byte.Parse(dropKlaskanie.SelectedValue);
        cmd.Parameters.Add("@ZaburzeniaWechowe", SqlDbType.TinyInt).Value = byte.Parse(dropZaburzeniaWechowe.SelectedValue);
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

    private void loadPartB()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select PrzebyteLeczenieOperacyjnePD, Papierosy, Kawa, ZielonaHerbata, Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD, " +
            "Zamieszkanie, Uwagi, Nadcisnienie, BlokeryKanWapn, DominujacyObjawObecnie, DominujacyObjawUwagi, RLS, ObjawyPsychotyczne, Depresja, Otepienie, Dyzartria, DysfagiaObjaw, RBD, ZaburzenieRuchomosciGalekOcznych, " +
            "Apraksja, TestKlaskania, ZaburzeniaWechowe, MasaCiala, Drzenie, Sztywnosc, Spowolnienie, " +
            "ObjawyInne, ObjawyInneJakie, CzasOFF, PoprawaPoLDopie from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropPrzebyteLeczenieOperacyjne.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["PrzebyteLeczenieOperacyjnePD"], NO_DATA.ToString());
                dropCigarettes.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Papierosy"], NO_DATA.ToString());
                dropCoffee.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Kawa"], NO_DATA.ToString());
                dropGreenTea.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["ZielonaHerbata"], NO_DATA.ToString());
                dropAlcohol.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Alkohol"], NO_DATA.ToString());
                textTreatmentNumber.Text = DatabaseProcedures.getTextByteValue(rdr["ZabiegowWZnieczOgPrzedRozpoznaniemPD"]);
                dropZamieszkanie.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Zamieszkanie"], NO_DATA.ToString());
                textNotes.Text = DatabaseProcedures.getTextStringValue(rdr["Uwagi"]);
                dropNadcisnienie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Nadcisnienie"]);
                dropBlokery.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["BlokeryKanWapn"]);
                dropDominujacyObjawObecnie.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DominujacyObjawObecnie"], NO_DATA.ToString());
                textDominujacyObjawUwagi.Text = DatabaseProcedures.getTextStringValue(rdr["DominujacyObjawUwagi"]);
                dropRLS.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["RLS"]);
                dropPsychotyczne.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ObjawyPsychotyczne"]);
                dropDepresja.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Depresja"]);
                dropOtepienie.SelectedValue = DatabaseProcedures.getDropDecimalValueWithNoData(rdr["Otepienie"], NO_DATA_DECIMAL.ToString());
                dropDyzartia.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Dyzartria"]);
                dropDysfagiaObjaw.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DysfagiaObjaw"], NO_DATA.ToString());
                dropRBD.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["RBD"]);
                dropZaburzeniaGalek.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ZaburzenieRuchomosciGalekOcznych"]);
                dropApraksja.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Apraksja"]);
                dropKlaskanie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["TestKlaskania"]);
                dropZaburzeniaWechowe.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ZaburzeniaWechowe"]);
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
        DatabaseProcedures.saveMultiChoice(Utils.getSelectedCheckListItems(checkListToxic), "NarazenieNaToks", int.Parse(Session["AppointmentId"].ToString()), User.Identity.Name);
        DatabaseProcedures.saveMultiChoice(Utils.getSelectedCheckListItems(checkListObjawyAutonomiczne), "ObjawyAutonomiczne", int.Parse(Session["AppointmentId"].ToString()), User.Identity.Name);
        savePartB();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}