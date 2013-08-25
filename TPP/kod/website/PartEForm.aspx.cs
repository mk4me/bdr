using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartEForm : System.Web.UI.Page
{
    private static byte NO_DATA = 100;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartE();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }

        if (!IsPostBack)
        {
            dropAddItems(Page, DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "_ObjawAutonomiczny", NO_DATA));

        }
    }

    private void dropAddItems(Control parent, Dictionary<byte, string> items)
    {
        foreach (Control control in parent.Controls)
        {
            DropDownList drop = control as DropDownList;
            if (drop != null)
            {
                drop.DataSource = items;
                drop.DataTextField = "Value";
                drop.DataValueField = "Key";
                drop.DataBind();
            }

            if (control.HasControls())
            {
                dropAddItems(control, items);
            }
        }
    }

    private void savePartE()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partE]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@WydzielanieSliny", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropWydzielanieSliny.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Dysfagia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropDysfagia.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@DysfagiaCzestotliwosc", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropDysfagiaCzestotliwosc.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Nudnosci", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNudnosci.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Zaparcia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropZaparcia.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@TrudnosciWOddawaniuMoczu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropTrudnosciWOddawaniuMoczu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@PotrzebaNaglegoOddaniaMoczu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropPotrzebaNaglegoOddaniaMoczu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@NiekompletneOproznieniePecherza", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNiekompletneOproznieniePecherza.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@SlabyStrumienMoczu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSlabyStrumienMoczu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@CzestotliwowscOddawaniaMoczu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropCzestotliwowscOddawaniaMoczu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Nykturia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNykturia.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@NiekontrolowaneOddawanieMoczu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNiekontrolowaneOddawanieMoczu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Omdlenia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropOmdlenia.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ZaburzeniaRytmuSerca", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropZaburzeniaRytmuSerca.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@ProbaPionizacyjna", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropProbaPionizacyjna.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@WzrostPodtliwosciTwarzKark", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropWzrostPodtliwosciTwarzKark.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@WzrostPotliwosciRamionaDlonie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropWzrostPotliwosciRamionaDlonie.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@WzrostPotliwosciKonczynyDolneStopy", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropWzrostPotliwosciKonczynyDolneStopy.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@SpadekPodtliwosciTwarzKark", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSpadekPodtliwosciTwarzKark.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@SpadekPotliwosciRamionaDlonie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSpadekPotliwosciRamionaDlonie.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@SpadekPotliwosciKonczynyDolneStopy", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSpadekPotliwosciKonczynyDolneStopy.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@NietolerancjaWysokichTemp", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNietolerancjaWysokichTemp.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@NietolerancjaNiskichTemp", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropNietolerancjaNiskichTemp.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Lojotok", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropLojotok.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@SpadekLibido", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSpadekLibido.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@KlopotyOsiagnieciaErekcji", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropKlopotyOsiagnieciaErekcji.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@KlopotyUtrzymaniaErekcji", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropKlopotyUtrzymaniaErekcji.SelectedValue, NO_DATA.ToString());
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

    private void loadPartE()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select WydzielanieSliny, Dysfagia, DysfagiaCzestotliwosc, Nudnosci, Zaparcia, TrudnosciWOddawaniuMoczu, " +
            "PotrzebaNaglegoOddaniaMoczu, NiekompletneOproznieniePecherza, SlabyStrumienMoczu, CzestotliwowscOddawaniaMoczu, Nykturia, NiekontrolowaneOddawanieMoczu, Omdlenia, " +
            "ZaburzeniaRytmuSerca, ProbaPionizacyjna, WzrostPodtliwosciTwarzKark, WzrostPotliwosciRamionaDlonie, WzrostPotliwosciKonczynyDolneStopy, SpadekPodtliwosciTwarzKark, SpadekPotliwosciRamionaDlonie, " +
            "SpadekPotliwosciKonczynyDolneStopy, NietolerancjaWysokichTemp, NietolerancjaNiskichTemp, Lojotok, SpadekLibido, KlopotyOsiagnieciaErekcji, KlopotyUtrzymaniaErekcji " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropWydzielanieSliny.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["WydzielanieSliny"], NO_DATA.ToString());
                dropDysfagia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Dysfagia"], NO_DATA.ToString());
                dropDysfagiaCzestotliwosc.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DysfagiaCzestotliwosc"], NO_DATA.ToString());
                dropNudnosci.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Nudnosci"], NO_DATA.ToString());
                dropZaparcia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Zaparcia"], NO_DATA.ToString());
                dropTrudnosciWOddawaniuMoczu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["TrudnosciWOddawaniuMoczu"], NO_DATA.ToString());
                dropPotrzebaNaglegoOddaniaMoczu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["PotrzebaNaglegoOddaniaMoczu"], NO_DATA.ToString());
                dropNiekompletneOproznieniePecherza.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["NiekompletneOproznieniePecherza"], NO_DATA.ToString());
                dropSlabyStrumienMoczu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["SlabyStrumienMoczu"], NO_DATA.ToString());
                dropCzestotliwowscOddawaniaMoczu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["CzestotliwowscOddawaniaMoczu"], NO_DATA.ToString());
                dropNiekontrolowaneOddawanieMoczu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["NiekontrolowaneOddawanieMoczu"], NO_DATA.ToString());
                dropNykturia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Nykturia"], NO_DATA.ToString());
                dropOmdlenia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Omdlenia"], NO_DATA.ToString());
                dropZaburzeniaRytmuSerca.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["ZaburzeniaRytmuSerca"], NO_DATA.ToString());
                dropProbaPionizacyjna.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["ProbaPionizacyjna"], NO_DATA.ToString());
                dropWzrostPodtliwosciTwarzKark.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["WzrostPodtliwosciTwarzKark"], NO_DATA.ToString());
                dropWzrostPotliwosciRamionaDlonie.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["WzrostPotliwosciRamionaDlonie"], NO_DATA.ToString());
                dropWzrostPotliwosciKonczynyDolneStopy.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["WzrostPotliwosciKonczynyDolneStopy"], NO_DATA.ToString());
                dropSpadekPodtliwosciTwarzKark.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["SpadekPodtliwosciTwarzKark"], NO_DATA.ToString());
                dropSpadekPotliwosciRamionaDlonie.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["SpadekPotliwosciRamionaDlonie"], NO_DATA.ToString());
                dropSpadekPotliwosciKonczynyDolneStopy.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["SpadekPotliwosciKonczynyDolneStopy"], NO_DATA.ToString());
                dropNietolerancjaWysokichTemp.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["NietolerancjaWysokichTemp"], NO_DATA.ToString());
                dropNietolerancjaNiskichTemp.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["NietolerancjaNiskichTemp"], NO_DATA.ToString());
                dropLojotok.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Lojotok"], NO_DATA.ToString());
                dropSpadekLibido.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["SpadekLibido"], NO_DATA.ToString());
                dropKlopotyOsiagnieciaErekcji.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["KlopotyOsiagnieciaErekcji"], NO_DATA.ToString());
                dropKlopotyUtrzymaniaErekcji.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["KlopotyUtrzymaniaErekcji"], NO_DATA.ToString());
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
        savePartE();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}