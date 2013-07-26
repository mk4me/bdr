using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Web.Configuration;

public partial class ExaminationForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
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

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "RodzajWizyty"))
            {
                dropExaminationType.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "Wyksztalcenie"))
            {
                dropEducation.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "PierwszyObjaw"))
            {
                dropSymptom.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "Papierosy"))
            {
                dropCigarettes.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "Kawa"))
            {
                dropCoffee.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "Alkohol"))
            {
                dropAlcohol.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "Zamieszkanie"))
            {
                dropPlace.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }

            foreach (KeyValuePair<byte, string> entry in getEnumeration("Wizyta", "NarazenieNaToks"))
            {
                dropToxic.Items.Add(new ListItem(entry.Value, entry.Key.ToString()));
            }
        }
    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        saveExamination(textPatientNumber.Text, byte.Parse(dropExaminationType.SelectedValue), byte.Parse(dropEducation.SelectedValue), byte.Parse(dropFamily.SelectedValue),
            short.Parse(dropYear.SelectedValue), byte.Parse(dropMonth.SelectedValue), byte.Parse(dropSymptom.SelectedValue), byte.Parse(textTimeSymptom.Text),
            byte.Parse(dropDiskinesia.SelectedValue), decimal.Parse(textTimeDiskinesia.Text), byte.Parse(dropFluctuations.SelectedValue), decimal.Parse(textYearsFluctuations.Text),
            byte.Parse(dropCigarettes.SelectedValue), byte.Parse(dropCoffee.SelectedValue), byte.Parse(dropGreenTea.SelectedValue), byte.Parse(dropAlcohol.SelectedValue),
            byte.Parse(textTreatmentNumber.Text), byte.Parse(dropPlace.SelectedValue), byte.Parse(dropToxic.SelectedValue), false);
    }

    private Dictionary<byte, string> getEnumeration(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_enumeration]";
        cmd.Parameters.Add("@table_name", SqlDbType.VarChar, 30).Value = table;
        cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 50).Value = attribute;
        cmd.Connection = con;

        Dictionary<byte, string> enumeration = new Dictionary<byte, string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                byte value = (byte) rdr["Value"];
                string label = (string) rdr["Label"];

                enumeration.Add(value, label);
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

        return enumeration;
    }

    private void saveExamination(string number, byte examinationType, byte education, byte family, short symptomYear, byte symptomMonth, byte firstSymptom, byte timeSymptom,
        byte dyskinesia, decimal timeDiskinesia, byte fluctuations, decimal yearsFluctuations, byte cigarettes, byte coffee, byte greenTea, byte alcohol, byte treatmentNumber,
        byte place, byte toxic, bool update)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = number;
        cmd.Parameters.Add("@RodzajWizyty", SqlDbType.TinyInt).Value = examinationType;
        cmd.Parameters.Add("@Wyksztalcenie", SqlDbType.TinyInt).Value = education;
        cmd.Parameters.Add("@Rodzinnosc", SqlDbType.TinyInt).Value = family;
        cmd.Parameters.Add("@RokZachorowania", SqlDbType.SmallInt).Value = symptomYear;
        cmd.Parameters.Add("@MiesiacZachorowania", SqlDbType.TinyInt).Value = symptomMonth;
        cmd.Parameters.Add("@PierwszyObjaw", SqlDbType.TinyInt).Value = firstSymptom;
        cmd.Parameters.Add("@CzasOdPoczObjDoWlLDopy", SqlDbType.TinyInt).Value = timeSymptom;
        cmd.Parameters.Add("@DyskinezyObecnie", SqlDbType.TinyInt).Value = dyskinesia;
        SqlParameter param = new SqlParameter("@CzasDyskinez", SqlDbType.Decimal);
        param.Precision = 3;
        param.Scale = 1;
        param.Value = timeDiskinesia;
        cmd.Parameters.Add(param);
        cmd.Parameters.Add("@FluktuacjeObecnie", SqlDbType.TinyInt).Value = fluctuations;
        param = new SqlParameter("@FluktuacjeOdLat", SqlDbType.Decimal);
        param.Precision = 3;
        param.Scale = 1;
        param.Value = yearsFluctuations;
        cmd.Parameters.Add(param);
        cmd.Parameters.Add("@Papierosy", SqlDbType.TinyInt).Value = cigarettes;
        cmd.Parameters.Add("@Kawa", SqlDbType.TinyInt).Value = coffee;
        cmd.Parameters.Add("@ZielonaHerbata", SqlDbType.TinyInt).Value = greenTea;
        cmd.Parameters.Add("@Alkohol", SqlDbType.TinyInt).Value = alcohol;
        cmd.Parameters.Add("@ZabiegowWZnieczOgPrzedRozpoznaniemPD", SqlDbType.TinyInt).Value = treatmentNumber;
        cmd.Parameters.Add("@Zamieszkanie", SqlDbType.TinyInt).Value = place;
        cmd.Parameters.Add("@NarazenieNaToks", SqlDbType.TinyInt).Value = toxic;
        cmd.Parameters.Add("@allow_update_existing", SqlDbType.Bit).Value = update;
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
}