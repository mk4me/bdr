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
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dropAppointmentType.DataSource = DatabaseProcedures.getEnumerationDecimal("Wizyta", "RodzajWizyty");
            dropAppointmentType.DataTextField = "Value";
            dropAppointmentType.DataValueField = "Key";
            dropAppointmentType.DataBind();

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

        string patientNumber = Request.QueryString["PatientNumber"];
        if (patientNumber != null)
        {
            labelPatientNumber.Text = patientNumber;
        }
    }

    private void saveExamination(string number, decimal examinationType, byte education, byte family, short symptomYear, byte symptomMonth, byte firstSymptom, byte timeSymptom,
        byte dyskinesia, decimal timeDiskinesia, byte fluctuations, decimal yearsFluctuations, byte cigarettes, byte coffee, byte greenTea, byte alcohol, byte treatmentNumber,
        byte place, byte toxic, DateTime dateIn, DateTime dateOut, string login, bool update)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
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
        dyskinesiaDecimal.Value = timeDiskinesia;
        cmd.Parameters.Add(dyskinesiaDecimal);
        cmd.Parameters.Add("@FluktuacjeObecnie", SqlDbType.TinyInt).Value = fluctuations;
        SqlParameter fluctuationsDecimal = new SqlParameter("@FluktuacjeOdLat", SqlDbType.Decimal);
        fluctuationsDecimal.Precision = 3;
        fluctuationsDecimal.Scale = 1;
        fluctuationsDecimal.Value = yearsFluctuations;
        cmd.Parameters.Add(fluctuationsDecimal);
        cmd.Parameters.Add("@Papierosy", SqlDbType.TinyInt).Value = cigarettes;
        cmd.Parameters.Add("@Kawa", SqlDbType.TinyInt).Value = coffee;
        cmd.Parameters.Add("@ZielonaHerbata", SqlDbType.TinyInt).Value = greenTea;
        cmd.Parameters.Add("@Alkohol", SqlDbType.TinyInt).Value = alcohol;
        cmd.Parameters.Add("@ZabiegowWZnieczOgPrzedRozpoznaniemPD", SqlDbType.TinyInt).Value = treatmentNumber;
        cmd.Parameters.Add("@Zamieszkanie", SqlDbType.TinyInt).Value = place;
        cmd.Parameters.Add("@NarazenieNaToks", SqlDbType.TinyInt).Value = toxic;
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
            if (success == 0 || success == 1)
            {
                Response.Redirect("~/AppointmentList.aspx?PatientNumber=" + Request.QueryString["PatientNumber"]);
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

    protected void dropDiskinesia_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropDiskinesia.SelectedValue) == 1)
        {
            textTimeDiskinesia.Enabled = true;
        }
        else
        {
            textTimeDiskinesia.Enabled = false;
            textTimeDiskinesia.Text = "";
        }
    }

    protected void dropFluctuations_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropFluctuations.SelectedValue) == 1)
        {
            textYearsFluctuations.Enabled = true;
        }
        else
        {
            textYearsFluctuations.Enabled = false;
            textYearsFluctuations.Text = "";
        }
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        saveExamination(Request.QueryString["PatientNumber"], decimal.Parse(dropAppointmentType.SelectedValue), byte.Parse(dropEducation.SelectedValue), byte.Parse(dropFamily.SelectedValue),
            short.Parse(dropYear.SelectedValue), byte.Parse(dropMonth.SelectedValue), byte.Parse(dropSymptom.SelectedValue), byte.Parse(textTimeSymptom.Text),
            byte.Parse(dropDiskinesia.SelectedValue), decimal.Parse(textTimeDiskinesia.Text), byte.Parse(dropFluctuations.SelectedValue), decimal.Parse(textYearsFluctuations.Text),
            byte.Parse(dropCigarettes.SelectedValue), byte.Parse(dropCoffee.SelectedValue), byte.Parse(dropGreenTea.SelectedValue), byte.Parse(dropAlcohol.SelectedValue),
            byte.Parse(textTreatmentNumber.Text), byte.Parse(dropPlace.SelectedValue), byte.Parse(dropToxic.SelectedValue), DateTime.Parse(textDateIn.Text), DateTime.Parse(textDateOut.Text),
            User.Identity.Name, false);
    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentList.aspx?" + Request.QueryString["PatientNumber"]);
    }
}