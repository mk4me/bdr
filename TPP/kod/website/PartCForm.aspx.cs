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
        cmd.Parameters.Add("@Ldopa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(dropLDopa.SelectedValue);
        cmd.Parameters.Add("@LDopaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textLDopa.Text);
        cmd.Parameters.Add("@Agonista", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropAgonista.SelectedValue);
        cmd.Parameters.Add("@AgonistaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textAgonista.Text);
        cmd.Parameters.Add("@Amantadyna", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropAmantadyna.SelectedValue);
        cmd.Parameters.Add("@AmantadynaObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textAmantadyna.Text);
        cmd.Parameters.Add("@MAOBinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropMAOBinh.SelectedValue);
        cmd.Parameters.Add("@MAOBinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textMAOBinh.Text);
        cmd.Parameters.Add("@COMTinh", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropCOMTinh.SelectedValue);
        cmd.Parameters.Add("@COMTinhObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textCOMTinh.Text);
        cmd.Parameters.Add("@Cholinolityk", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropCholinotyk.SelectedValue);
        cmd.Parameters.Add("@CholinolitykObecnie", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNull(textCholinotyk.Text);
        cmd.Parameters.Add("@LekiInne", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropLekiInne.SelectedValue);
        cmd.Parameters.Add("@LekiInneJakie", SqlDbType.VarChar, 50).Value = DatabaseProcedures.getStringOrNull(textLekiInne.Text);
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

    private void loadPartC()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select Ldopa, LDopaObecnie, Agonista, AgonistaObecnie, Amantadyna, AmantadynaObecnie, MAOBinh, " +
            "MAOBinhObecnie, COMTinh, COMTinhObecnie, Cholinolityk, CholinolitykObecnie, LekiInne, LekiInneJakie " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropLDopa.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Ldopa"]);
                textLDopa.Text = DatabaseProcedures.getTextShortValue(rdr["LdopaObecnie"]);
                dropAgonista.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Agonista"]);
                textAgonista.Text = DatabaseProcedures.getTextShortValue(rdr["AgonistaObecnie"]);
                dropAmantadyna.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Amantadyna"]);
                textAmantadyna.Text = DatabaseProcedures.getTextShortValue(rdr["AmantadynaObecnie"]);
                dropMAOBinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["MAOBinh"]);
                textMAOBinh.Text = DatabaseProcedures.getTextShortValue(rdr["MAOBinhObecnie"]);
                dropCOMTinh.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["COMTinh"]);
                textCOMTinh.Text = DatabaseProcedures.getTextShortValue(rdr["COMTinhObecnie"]);
                dropCholinotyk.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["Cholinolityk"]);
                textCholinotyk.Text = DatabaseProcedures.getTextShortValue(rdr["CholinolitykObecnie"]);
                dropLekiInne.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["LekiInne"]);
                textLekiInne.Text = DatabaseProcedures.getTextStringValue(rdr["LekiInneJakie"]);
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