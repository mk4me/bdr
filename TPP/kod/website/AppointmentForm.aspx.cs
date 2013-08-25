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

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["AppointmentType"] != null && Session["PatientNumber"] != null && Session["Update"] != null)
        {
            patientNumber = Session["PatientNumber"].ToString();
            labelPatientNumber.Text = "Numer pacjenta: " + patientNumber;

            dropAppointmentType.SelectedValue = Session["AppointmentType"].ToString();

            update = (bool)Session["Update"];
            if (update && !IsPostBack)
            {
                loadAppointment((int)Session["AppointmentId"]);
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

            dropEducation.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "Wyksztalcenie");
            dropEducation.DataTextField = "Value";
            dropEducation.DataValueField = "Key";
            dropEducation.DataBind();

            dropSymptom.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "PierwszyObjaw");
            dropSymptom.DataTextField = "Value";
            dropSymptom.DataValueField = "Key";
            dropSymptom.DataBind();

            dropCigarettes.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "Papierosy");
            dropCigarettes.DataTextField = "Value";
            dropCigarettes.DataValueField = "Key";
            dropCigarettes.DataBind();

            dropCoffee.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "Kawa");
            dropCoffee.DataTextField = "Value";
            dropCoffee.DataValueField = "Key";
            dropCoffee.DataBind();

            dropAlcohol.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "Alkohol");
            dropAlcohol.DataTextField = "Value";
            dropAlcohol.DataValueField = "Key";
            dropAlcohol.DataBind();

            dropPlace.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "Zamieszkanie");
            dropPlace.DataTextField = "Value";
            dropPlace.DataValueField = "Key";
            dropPlace.DataBind();

            dropToxic.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "NarazenieNaToks");
            dropToxic.DataTextField = "Value";
            dropToxic.DataValueField = "Key";
            dropToxic.DataBind();
        }
    }

    private void saveAppointment(string number, decimal examinationType, byte education, byte family, short symptomYear, byte symptomMonth, byte firstSymptom, byte timeSymptom,
        byte dyskinesia, string timeDiskinesia, byte fluctuations, string yearsFluctuations, byte cigarettes, byte coffee, byte greenTea, byte alcohol, byte treatmentNumber,
        byte place, byte toxic, DateTime dateIn, DateTime dateSurgery, DateTime dateOut, string login, string notes, bool update)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partA]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = number;
        SqlParameter examinationTypeDecimal = new SqlParameter("@RodzajWizyty", SqlDbType.Decimal);
        examinationTypeDecimal.Precision = 2;
        examinationTypeDecimal.Scale = 1;
        examinationTypeDecimal.Value = examinationType;
        cmd.Parameters.Add(examinationTypeDecimal);
        cmd.Parameters.Add("@DataPrzyjecia", SqlDbType.Date).Value = dateIn;
        cmd.Parameters.Add("@DataOperacji", SqlDbType.Date).Value = dateSurgery;
        cmd.Parameters.Add("@DataWypisu", SqlDbType.Date).Value = dateOut;
        cmd.Parameters.Add("@Wyksztalcenie", SqlDbType.TinyInt).Value = education;
        cmd.Parameters.Add("@Rodzinnosc", SqlDbType.TinyInt).Value = family;
        cmd.Parameters.Add("@RokZachorowania", SqlDbType.SmallInt).Value = symptomYear;
        cmd.Parameters.Add("@MiesiacZachorowania", SqlDbType.TinyInt).Value = symptomMonth;
        cmd.Parameters.Add("@PierwszyObjaw", SqlDbType.TinyInt).Value = firstSymptom;
        cmd.Parameters.Add("@CzasOdPoczObjDoWlLDopy", SqlDbType.TinyInt).Value = timeSymptom;
        cmd.Parameters.Add("@DyskinezyObecnie", SqlDbType.TinyInt).Value = dyskinesia;
        SqlParameter dyskinesiaDecimal = new SqlParameter("@CzasDyskinez", SqlDbType.Decimal);
        dyskinesiaDecimal.Precision = 3;
        dyskinesiaDecimal.Scale = 1;
        dyskinesiaDecimal.Value = DatabaseProcedures.getDecimalOrNull(timeDiskinesia);
        cmd.Parameters.Add(dyskinesiaDecimal);
        cmd.Parameters.Add("@FluktuacjeObecnie", SqlDbType.TinyInt).Value = fluctuations;
        SqlParameter fluctuationsDecimal = new SqlParameter("@FluktuacjeOdLat", SqlDbType.Decimal);
        fluctuationsDecimal.Precision = 3;
        fluctuationsDecimal.Scale = 1;
        fluctuationsDecimal.Value = DatabaseProcedures.getDecimalOrNull(yearsFluctuations);
        cmd.Parameters.Add(fluctuationsDecimal);
        cmd.Parameters.Add("@Papierosy", SqlDbType.TinyInt).Value = cigarettes;
        cmd.Parameters.Add("@Kawa", SqlDbType.TinyInt).Value = coffee;
        cmd.Parameters.Add("@ZielonaHerbata", SqlDbType.TinyInt).Value = greenTea;
        cmd.Parameters.Add("@Alkohol", SqlDbType.TinyInt).Value = alcohol;
        cmd.Parameters.Add("@ZabiegowWZnieczOgPrzedRozpoznaniemPD", SqlDbType.TinyInt).Value = treatmentNumber;
        cmd.Parameters.Add("@Zamieszkanie", SqlDbType.TinyInt).Value = place;
        cmd.Parameters.Add("@NarazenieNaToks", SqlDbType.TinyInt).Value = toxic;
        cmd.Parameters.Add("@Uwagi", SqlDbType.VarChar, 50).Value = notes;
        cmd.Parameters.Add("@allow_update_existing", SqlDbType.Bit).Value = update;
        cmd.Parameters.Add("@actor_login", SqlDbType.VarChar, 50).Value = login;
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
                }
                else
                {
                    Session.Remove("Update");
                    Response.Redirect("~/AppointmentList.aspx");
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
            "PierwszyObjaw, CzasOdPoczObjDoWlLDopy, DyskinezyObecnie, CzasDyskinez, FluktuacjeObecnie, FluktuacjeOdLat, " +
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
                dropEducation.SelectedValue = ((byte)rdr["Wyksztalcenie"]).ToString();
                dropFamily.SelectedValue = ((byte)rdr["Rodzinnosc"]).ToString();
                dropYear.SelectedValue = ((short)rdr["RokZachorowania"]).ToString();
                dropMonth.SelectedValue = ((byte)rdr["MiesiacZachorowania"]).ToString();
                dropSymptom.SelectedValue = ((byte)rdr["PierwszyObjaw"]).ToString();
                textTimeSymptom.Text = ((byte)rdr["CzasOdPoczObjDoWlLDopy"]).ToString();
                dropDiskinesia.SelectedValue = ((byte)rdr["DyskinezyObecnie"]).ToString();
                if (dropDiskinesia.SelectedValue == "0")
                {
                    textTimeDiskinesia.Enabled = false;
                    RequiredFieldValidator2.Enabled = false;
                }
                else
                {
                    textTimeDiskinesia.Text = ((decimal)rdr["CzasDyskinez"]).ToString();
                    RequiredFieldValidator2.Enabled = true;
                }
                dropFluctuations.SelectedValue = ((byte)rdr["FluktuacjeObecnie"]).ToString();
                if (dropFluctuations.SelectedValue == "0")
                {
                    textYearsFluctuations.Enabled = false;
                    RequiredFieldValidator3.Enabled = false;
                }
                else
                {
                    textYearsFluctuations.Text = ((decimal)rdr["FluktuacjeOdLat"]).ToString();
                    RequiredFieldValidator3.Enabled = true;
                }
                dropCigarettes.SelectedValue = ((byte)rdr["Papierosy"]).ToString();
                dropCoffee.SelectedValue = ((byte)rdr["Kawa"]).ToString();
                dropGreenTea.SelectedValue = ((byte)rdr["ZielonaHerbata"]).ToString();
                dropAlcohol.SelectedValue = ((byte)rdr["Alkohol"]).ToString();
                textTreatmentNumber.Text = ((byte)rdr["ZabiegowWZnieczOgPrzedRozpoznaniemPD"]).ToString();
                dropPlace.SelectedValue = ((byte)rdr["Zamieszkanie"]).ToString();
                dropToxic.SelectedValue = ((byte)rdr["NarazenieNaToks"]).ToString();
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
        saveAppointment(patientNumber, decimal.Parse(dropAppointmentType.SelectedValue), byte.Parse(dropEducation.SelectedValue), byte.Parse(dropFamily.SelectedValue),
            short.Parse(dropYear.SelectedValue), byte.Parse(dropMonth.SelectedValue), byte.Parse(dropSymptom.SelectedValue), byte.Parse(textTimeSymptom.Text),
            byte.Parse(dropDiskinesia.SelectedValue), textTimeDiskinesia.Text, byte.Parse(dropFluctuations.SelectedValue), textYearsFluctuations.Text,
            byte.Parse(dropCigarettes.SelectedValue), byte.Parse(dropCoffee.SelectedValue), byte.Parse(dropGreenTea.SelectedValue), byte.Parse(dropAlcohol.SelectedValue),
            byte.Parse(textTreatmentNumber.Text), byte.Parse(dropPlace.SelectedValue), byte.Parse(dropToxic.SelectedValue), DateTime.Parse(textDateIn.Text), DateTime.Parse(textDateSurgery.Text),
            DateTime.Parse(textDateOut.Text), User.Identity.Name, textNotes.Text, update);
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