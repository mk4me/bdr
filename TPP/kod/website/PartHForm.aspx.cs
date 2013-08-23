using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartHForm : System.Web.UI.Page
{
    private static byte NO_DATA = 100;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartH();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }

        if (!IsPostBack)
        {
            dropWynikWechu.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "WynikWechu", NO_DATA);
            dropWynikWechu.DataTextField = "Value";
            dropWynikWechu.DataValueField = "Key";
            dropWynikWechu.DataBind();

            dropLimitDysfagii.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "LimitDysfagii", NO_DATA);
            dropLimitDysfagii.DataTextField = "Value";
            dropLimitDysfagii.DataValueField = "Key";
            dropLimitDysfagii.DataBind();

            dropUSGWynik.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "USGWynik", NO_DATA);
            dropUSGWynik.DataTextField = "Value";
            dropUSGWynik.DataValueField = "Key";
            dropUSGWynik.DataBind();
        }
    }

    private void savePartH()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partH]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@Holter", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropHolter.SelectedValue);
        cmd.Parameters.Add("@BadanieWechu", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropBadanieWechu.SelectedValue);
        cmd.Parameters.Add("@WynikWechu", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropWynikWechu.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@LimitDysfagii", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropLimitDysfagii.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@pH_metriaPrzełyku", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(droppHmetriaPrzełyku.SelectedValue);
        cmd.Parameters.Add("@SPECT", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropSPECT.SelectedValue);
        cmd.Parameters.Add("@MRI", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropMRI.SelectedValue);
        cmd.Parameters.Add("@MRIwynik", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textMRIwynik.Text);
        cmd.Parameters.Add("@USGsrodmozgowia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(dropUSGsrodmozgowia.SelectedValue);
        cmd.Parameters.Add("@USGWynik", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropUSGsrodmozgowia.SelectedValue, NO_DATA.ToString());
        SqlParameter kwasMoczowyDecimal = new SqlParameter("@KwasMoczowy", SqlDbType.Decimal);
        kwasMoczowyDecimal.Precision = 6;
        kwasMoczowyDecimal.Scale = 2;
        kwasMoczowyDecimal.Value = DatabaseProcedures.getDecimalOrNull(textKwasMoczowy.Text);
        cmd.Parameters.Add(kwasMoczowyDecimal);
        cmd.Parameters.Add("@Genetyka", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropGenetyka.SelectedValue);
        cmd.Parameters.Add("@GenetykaWynik", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textGenetykaWynik.Text);
        cmd.Parameters.Add("@Surowica", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropSurowica.SelectedValue);
        cmd.Parameters.Add("@SurowicaPozostało", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textSurowicaPozostało.Text);
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

    private void loadPartH()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select Holter, BadanieWechu, WynikWechu, LimitDysfagii, pH_metriaPrzełyku, SPECT, MRI, " +
            "MRIwynik, USGsrodmozgowia, USGWynik, KwasMoczowy, Genetyka, GenetykaWynik, Surowica, SurowicaPozostało " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        List<string> list = new List<string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropHolter.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Holter"]);
                dropBadanieWechu.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["BadanieWechu"]);
                dropWynikWechu.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["WynikWechu"], NO_DATA.ToString());
                dropLimitDysfagii.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["LimitDysfagii"], NO_DATA.ToString());
                droppHmetriaPrzełyku.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["pH_metriaPrzełyku"]);
                dropSPECT.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["SPECT"]);
                dropMRI.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["MRI"]);
                textMRIwynik.Text = DatabaseProcedures.getTextStringValue(rdr["MRIwynik"]);
                dropUSGsrodmozgowia.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["USGsrodmozgowia"]);
                dropUSGWynik.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["USGWynik"], NO_DATA.ToString());
                textKwasMoczowy.Text = DatabaseProcedures.getTextDecimalValue(rdr["KwasMoczowy"]);
                dropGenetyka.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Genetyka"]);
                textGenetykaWynik.Text = DatabaseProcedures.getTextStringValue(rdr["GenetykaWynik"]);
                dropSurowica.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Surowica"]);
                textSurowicaPozostało.Text = DatabaseProcedures.getTextStringValue(rdr["SurowicaPozostało"]);
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
        savePartH();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}