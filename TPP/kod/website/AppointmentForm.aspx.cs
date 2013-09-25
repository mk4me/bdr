using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class AppointmentForm : System.Web.UI.Page
{
    private bool update = false;
    private string patientNumber;
    private static string DATE_FORMAT = "yyyy-MM-dd";
    private static byte NO_DATA = 100;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["AppointmentType"] != null && Session["PatientNumber"] != null && Session["Update"] != null)
        {
            patientNumber = Session["PatientNumber"].ToString();
            labelPatientNumber.Text = "Numer pacjenta: " + patientNumber;

            dropAppointmentType.SelectedValue = Session["AppointmentType"].ToString();

            update = (bool)Session["Update"];
            if (!IsPostBack)
            {
                if (update)
                {
                    loadAppointment((int)Session["AppointmentId"]);
                }
                else
                {
                    initAppointment();
                }
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }

        if (!IsPostBack)
        {
            textDateIn.Text = DateTime.Now.ToString(DATE_FORMAT);
            textDateSurgery.Text = DateTime.Now.ToString(DATE_FORMAT);
            textDateOut.Text = DateTime.Now.ToString(DATE_FORMAT);
            // date not setting when disabled textbox?
            //textDateIn.Enabled = false;
            //textDateSurgery.Enabled = false;
            //textDateOut.Enabled = false;

            dropAppointmentType.DataSource = DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty");
            dropAppointmentType.DataTextField = "Value";
            dropAppointmentType.DataValueField = "Key";
            dropAppointmentType.DataBind();
            dropAppointmentType.Enabled = false;
            toggleButtons(update);

            int minYear = 1920;
            int currentYear = DateTime.Now.Year;
            for (int i = minYear; i <= currentYear; i++)
            {
                dropYear.Items.Add(new ListItem("" + i, "" + i));
            }

            dropYear.SelectedIndex = currentYear - minYear - 10;

            for (int i = 1; i <= 12; i++)
            {
                dropMonth.Items.Add(new ListItem("" + i, "" + i));
            }

            dropEducation.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Wyksztalcenie", NO_DATA);
            dropEducation.DataTextField = "Value";
            dropEducation.DataValueField = "Key";
            dropEducation.DataBind();

            dropSymptom.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "PierwszyObjaw", NO_DATA);
            dropSymptom.DataTextField = "Value";
            dropSymptom.DataValueField = "Key";
            dropSymptom.DataBind();

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

            dropPlace.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Zamieszkanie", NO_DATA);
            dropPlace.DataTextField = "Value";
            dropPlace.DataValueField = "Key";
            dropPlace.DataBind();

            dropToxic.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "NarazenieNaToks", NO_DATA);
            dropToxic.DataTextField = "Value";
            dropToxic.DataValueField = "Key";
            dropToxic.DataBind();
        }
    }

    private void saveAppointment()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partA]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = patientNumber;
        SqlParameter examinationTypeDecimal = new SqlParameter("@RodzajWizyty", SqlDbType.Decimal);
        examinationTypeDecimal.Precision = 2;
        examinationTypeDecimal.Scale = 1;
        examinationTypeDecimal.Value = decimal.Parse(dropAppointmentType.SelectedValue);
        cmd.Parameters.Add(examinationTypeDecimal);
        cmd.Parameters.Add("@DataPrzyjecia", SqlDbType.Date).Value = DateTime.Parse(textDateIn.Text);
        cmd.Parameters.Add("@DataOperacji", SqlDbType.Date).Value = DateTime.Parse(textDateSurgery.Text);
        cmd.Parameters.Add("@DataWypisu", SqlDbType.Date).Value = DateTime.Parse(textDateOut.Text);
        cmd.Parameters.Add("@Wyksztalcenie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropEducation.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Rodzinnosc", SqlDbType.TinyInt).Value = byte.Parse(dropFamily.SelectedValue);
        cmd.Parameters.Add("@RokZachorowania", SqlDbType.SmallInt).Value = short.Parse(dropYear.SelectedValue);
        cmd.Parameters.Add("@MiesiacZachorowania", SqlDbType.TinyInt).Value = byte.Parse(dropMonth.SelectedValue);
        cmd.Parameters.Add("@PierwszyObjaw", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSymptom.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@CzasOdPoczObjDoWlLDopy", SqlDbType.TinyInt).Value = byte.Parse(textTimeSymptom.Text);
        cmd.Parameters.Add("@DyskinezyObecnie", SqlDbType.TinyInt).Value = byte.Parse(dropDiskinesia.SelectedValue);
        SqlParameter dyskinesiaDecimal = new SqlParameter("@CzasDyskinezOdLat", SqlDbType.Decimal);
        dyskinesiaDecimal.Precision = 3;
        dyskinesiaDecimal.Scale = 1;
        dyskinesiaDecimal.Value = DatabaseProcedures.getDecimalOrNull(textTimeDiskinesia.Text);
        cmd.Parameters.Add(dyskinesiaDecimal);
        cmd.Parameters.Add("@FluktuacjeObecnie", SqlDbType.TinyInt).Value = byte.Parse(dropFluctuations.SelectedValue);
        SqlParameter fluctuationsDecimal = new SqlParameter("@FluktuacjeOdLat", SqlDbType.Decimal);
        fluctuationsDecimal.Precision = 3;
        fluctuationsDecimal.Scale = 1;
        fluctuationsDecimal.Value = DatabaseProcedures.getDecimalOrNull(textYearsFluctuations.Text);
        cmd.Parameters.Add(fluctuationsDecimal);
        cmd.Parameters.Add("@Papierosy", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropCigarettes.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Kawa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropCoffee.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ZielonaHerbata", SqlDbType.TinyInt).Value = byte.Parse(dropGreenTea.SelectedValue);
        cmd.Parameters.Add("@Alkohol", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropAlcohol.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ZabiegowWZnieczOgPrzedRozpoznaniemPD", SqlDbType.TinyInt).Value = byte.Parse(textTreatmentNumber.Text);
        cmd.Parameters.Add("@Zamieszkanie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropPlace.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@NarazenieNaToks", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropToxic.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Uwagi", SqlDbType.VarChar, 50).Value = textNotes.Text;
        cmd.Parameters.Add("@allow_update_existing", SqlDbType.Bit).Value = update;
        cmd.Parameters.Add("@actor_login", SqlDbType.VarChar, 50).Value = User.Identity.Name;
        cmd.Parameters.Add("@result", SqlDbType.Int);
        cmd.Parameters["@result"].Direction = ParameterDirection.Output;
        cmd.Parameters.Add("@visit_id", SqlDbType.Int);
        cmd.Parameters["@visit_id"].Direction = ParameterDirection.Output;
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
                if (!update)
                {
                    toggleButtons(true);
                    Session["AppointmentId"] = (int)cmd.Parameters["@visit_id"].Value;
                    Session["Update"] = true;
                }
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

    private void loadAppointment(int appointmentId)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select DataPrzyjecia, DataWypisu, Wyksztalcenie, Rodzinnosc, RokZachorowania, MiesiacZachorowania, " +
            "PierwszyObjaw, CzasOdPoczObjDoWlLDopy, DyskinezyObecnie, CzasDyskinezOdLat, FluktuacjeObecnie, FluktuacjeOdLat, " +
            "Papierosy, Kawa, ZielonaHerbata, Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD, Zamieszkanie, NarazenieNaToks, DataOperacji, Uwagi from Wizyta where IdWizyta = " + appointmentId;
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                textDateIn.Text = ((DateTime)rdr["DataPrzyjecia"]).ToString(DATE_FORMAT);
                textDateSurgery.Text = ((DateTime)rdr["DataOperacji"]).ToString(DATE_FORMAT);
                textDateOut.Text = ((DateTime)rdr["DataWypisu"]).ToString(DATE_FORMAT);
                dropEducation.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Wyksztalcenie"], NO_DATA.ToString());
                dropFamily.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Rodzinnosc"]);
                dropYear.SelectedValue = ((short)rdr["RokZachorowania"]).ToString();
                dropMonth.SelectedValue = ((byte)rdr["MiesiacZachorowania"]).ToString();
                dropSymptom.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["PierwszyObjaw"], NO_DATA.ToString());
                textTimeSymptom.Text = ((byte)rdr["CzasOdPoczObjDoWlLDopy"]).ToString();
                dropDiskinesia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DyskinezyObecnie"], NO_DATA.ToString());
                if (dropDiskinesia.SelectedValue == "0")
                {
                    textTimeDiskinesia.Enabled = false;
                    RequiredFieldValidator2.Enabled = false;
                }
                else
                {
                    textTimeDiskinesia.Text = DatabaseProcedures.getTextDecimalValue(rdr["CzasDyskinezOdLat"]);
                    RequiredFieldValidator2.Enabled = true;
                }
                dropFluctuations.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["FluktuacjeObecnie"], NO_DATA.ToString());
                if (dropFluctuations.SelectedValue == "0")
                {
                    textYearsFluctuations.Enabled = false;
                    RequiredFieldValidator3.Enabled = false;
                }
                else
                {
                    textYearsFluctuations.Text = DatabaseProcedures.getTextDecimalValue(rdr["FluktuacjeOdLat"]);
                    RequiredFieldValidator3.Enabled = true;
                }
                dropCigarettes.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Papierosy"], NO_DATA.ToString());
                dropCoffee.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Kawa"], NO_DATA.ToString());
                dropGreenTea.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["ZielonaHerbata"], NO_DATA.ToString());
                dropAlcohol.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Alkohol"], NO_DATA.ToString());
                textTreatmentNumber.Text = ((byte)rdr["ZabiegowWZnieczOgPrzedRozpoznaniemPD"]).ToString();
                dropPlace.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Zamieszkanie"], NO_DATA.ToString());
                dropToxic.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["NarazenieNaToks"], NO_DATA.ToString());
                textNotes.Text = (string)rdr["Uwagi"];
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

    private void initAppointment()
    {
        dropEducation.SelectedValue = NO_DATA.ToString();
        dropSymptom.SelectedValue = NO_DATA.ToString();
        dropCigarettes.SelectedValue = NO_DATA.ToString();
        dropCoffee.SelectedValue = NO_DATA.ToString();
        dropAlcohol.SelectedValue = NO_DATA.ToString();
        dropPlace.SelectedValue = NO_DATA.ToString();
        dropToxic.SelectedValue = NO_DATA.ToString();
        textTimeDiskinesia.Enabled = false;
        RequiredFieldValidator2.Enabled = false;
        textYearsFluctuations.Enabled = false;
        RequiredFieldValidator3.Enabled = false;
    }

    protected void dropDiskinesia_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropDiskinesia.SelectedValue) == 1)
        {
            textTimeDiskinesia.Enabled = true;
            RequiredFieldValidator2.Enabled = true;
        }
        else
        {
            textTimeDiskinesia.Enabled = false;
            textTimeDiskinesia.Text = "";
            RequiredFieldValidator2.Enabled = false;
        }
    }

    protected void dropFluctuations_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropFluctuations.SelectedValue) == 1)
        {
            textYearsFluctuations.Enabled = true;
            RequiredFieldValidator3.Enabled = true;
        }
        else
        {
            textYearsFluctuations.Enabled = false;
            textYearsFluctuations.Text = "";
            RequiredFieldValidator3.Enabled = false;
        }
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        saveAppointment();
    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentList.aspx");
    }
    protected void buttonPartB_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartBForm.aspx");
    }
    protected void buttonPartC_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartCForm.aspx");
    }
    protected void buttonPartE_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartEForm.aspx");
    }
    protected void buttonPartF_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartFForm.aspx");
    }
    protected void buttonPartG_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartGForm.aspx");
    }
    protected void buttonPartH_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/PartHForm.aspx");
    }

    private void toggleButtons(bool enable)
    {
        if (enable)
        {
            Session["AppointmentName"] = dropAppointmentType.SelectedItem.ToString();
            labelAppointment.Text = "Badania dla wizyty: " + Session["AppointmentName"];
        }
        else
        {
            labelAppointment.Text = "";
        }
        buttonPartB.Enabled = enable;
        buttonPartC.Enabled = enable;
        buttonPartE.Enabled = enable;
        buttonPartF.Enabled = enable;
        buttonPartG.Enabled = enable;
        buttonPartH.Enabled = enable;
    }
}