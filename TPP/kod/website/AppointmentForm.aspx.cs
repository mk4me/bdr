﻿using System;
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
    private static short NO_DATA_SHORT = 100;

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
                initControls();
                if (update)
                {
                    loadAppointment((int)Session["AppointmentId"]);
                }
                else
                {
                    initAppointment();
                }
                setDisorderDuration();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void initControls()
    {
        textDateIn.Text = DateTime.Now.ToString(DATE_FORMAT);
        textDateSurgery.Text = DateTime.Now.ToString(DATE_FORMAT);
        textDateOut.Text = DateTime.Now.ToString(DATE_FORMAT);
        // date not setting when disabled textbox?
        //textDateIn.Enabled = false;
        //textDateSurgery.Enabled = false;
        //textDateOut.Enabled = false;

        dropAppointmentType.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "RodzajWizyty");
        dropAppointmentType.DataTextField = "Value";
        dropAppointmentType.DataValueField = "Key";
        dropAppointmentType.DataBind();
        dropAppointmentType.Enabled = false;
        toggleButtons(update);

        int minYear = 1920;
        int currentYear = DateTime.Now.Year;
        for (int i = minYear; i <= currentYear; i++)
        {
            dropRokZachorowania.Items.Add(new ListItem("" + i, "" + i));
        }
        dropRokZachorowania.Items.Add(new ListItem("", NO_DATA_SHORT.ToString()));
        dropRokZachorowania.SelectedValue = NO_DATA_SHORT.ToString();

        dropEducation.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "Wyksztalcenie", NO_DATA);
        dropEducation.DataTextField = "Value";
        dropEducation.DataValueField = "Key";
        dropEducation.DataBind();

        dropSymptom.DataSource = DatabaseProcedures.getEnumerationByteWithNoData("Wizyta", "PierwszyObjaw", NO_DATA);
        dropSymptom.DataTextField = "Value";
        dropSymptom.DataValueField = "Key";
        dropSymptom.DataBind();

        // Wizyta inna niz pierwsza - wygasic ponizsze atrybuty. Dla POP pierwsza wizyta jest po pol roku.
        if ((Session["PatientNumber"].ToString().Contains(Consts.PATIENT_POP) == false && Session["AppointmentType"].ToString() != Consts.APPOINTMENT_0.ToString()) ||
            (Session["PatientNumber"].ToString().Contains(Consts.PATIENT_POP) == true && Session["AppointmentType"].ToString() != Consts.APPOINTMENT_0.ToString() && Session["AppointmentType"].ToString() != Consts.APPOINTMENT_6.ToString()))
        {
            textDateSurgery.Visible = false;
            labelDateSurgery.Visible = false;
            dropEducation.Visible = false;
            labelEducation.Visible = false;
            dropFamily.Visible = false;
            labelFamily.Visible = false;
            dropSymptom.Visible = false;
            labelSymptom.Visible = false;
            textTimeSymptom.Visible = false;
            labelTimeSymptom.Visible = false;
            dropRokZachorowania.Visible = false;
            labelRokZachorowania.Visible = false;
            textTimeDiskinesia.Visible = false;
            labelTimeDiskinesia.Visible = false;
            textYearsFluctuations.Visible = false;
            labelYearsFluctuations.Visible = false;
        }

        // W grupie BMT - wygasic date zabiegu.
        if (Session["PatientNumber"].ToString().Contains(Consts.PATIENT_BMT) == true)
        {
            textDateSurgery.Visible = false;
            labelDateSurgery.Visible = false;
        }
    }

    private void saveAppointment()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partA]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = patientNumber;
        cmd.Parameters.Add("@RodzajWizyty", SqlDbType.TinyInt).Value = byte.Parse(dropAppointmentType.SelectedValue);
        cmd.Parameters.Add("@DataPrzyjecia", SqlDbType.Date).Value = DateTime.Parse(textDateIn.Text);
        cmd.Parameters.Add("@DataOperacji", SqlDbType.Date).Value = DateTime.Parse(textDateSurgery.Text);
        cmd.Parameters.Add("@DataWypisu", SqlDbType.Date).Value = DateTime.Parse(textDateOut.Text);
        SqlParameter masaCialaDecimal = new SqlParameter("@MasaCiala", SqlDbType.Decimal);
        masaCialaDecimal.Precision = 4;
        masaCialaDecimal.Scale = 1;
        masaCialaDecimal.Value = DatabaseProcedures.getDecimalOrNull(textMasaCiala.Text);
        cmd.Parameters.Add(masaCialaDecimal);
        cmd.Parameters.Add("@Wyksztalcenie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropEducation.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Rodzinnosc", SqlDbType.TinyInt).Value = byte.Parse(dropFamily.SelectedValue);
        if (dropRokZachorowania.Visible == true)
        {
            cmd.Parameters.Add("@RokZachorowania", SqlDbType.SmallInt).Value = DatabaseProcedures.getShortOrNullWithNoData(dropRokZachorowania.SelectedValue, NO_DATA_SHORT.ToString());//short.Parse(dropRokZachorowania.SelectedValue);
        }
        else
        {
            cmd.Parameters.Add("@RokZachorowania", SqlDbType.SmallInt).Value = DBNull.Value;
        }
        cmd.Parameters.Add("@PierwszyObjaw", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(dropSymptom.SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@Drzenie", SqlDbType.TinyInt).Value = byte.Parse(dropDrzenie.SelectedValue);
        cmd.Parameters.Add("@Sztywnosc", SqlDbType.TinyInt).Value = byte.Parse(dropSztywnosc.SelectedValue);
        cmd.Parameters.Add("@Spowolnienie", SqlDbType.TinyInt).Value = byte.Parse(dropSpowolnienie.SelectedValue);
        cmd.Parameters.Add("@ObjawyInne", SqlDbType.TinyInt).Value = byte.Parse(dropObjawy.SelectedValue);
        cmd.Parameters.Add("@ObjawyInneJakie", SqlDbType.VarChar, 80).Value = textObjawy.Text.ToString();
        cmd.Parameters.Add("@CzasOdPoczObjDoWlLDopy", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTimeSymptom.Text);
        cmd.Parameters.Add("@DyskinezyObecnie", SqlDbType.TinyInt).Value = byte.Parse(dropDiskinesia.SelectedValue);
        SqlParameter dyskinesiaDecimal = new SqlParameter("@DyskinezyOdLat", SqlDbType.Decimal);
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
        SqlParameter czasDyskinezDecimal = new SqlParameter("@CzasDyskinez", SqlDbType.Decimal);
        czasDyskinezDecimal.Precision = 3;
        czasDyskinezDecimal.Scale = 1;
        czasDyskinezDecimal.Value = DatabaseProcedures.getDecimalOrNull(textCzasOFF.Text);
        cmd.Parameters.Add(czasDyskinezDecimal);
        SqlParameter czasOFFDecimal = new SqlParameter("@CzasOFF", SqlDbType.Decimal);
        czasOFFDecimal.Precision = 3;
        czasOFFDecimal.Scale = 1;
        czasOFFDecimal.Value = DatabaseProcedures.getDecimalOrNull(textCzasOFF.Text);
        cmd.Parameters.Add(czasOFFDecimal);
        cmd.Parameters.Add("@PoprawaPoLDopie", SqlDbType.TinyInt).Value = byte.Parse(dropPoprawa.SelectedValue);
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
                labelSaved.Text = "Dane zapisane";
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

    private void setDisorderDuration()
    {
        if (Session["AppointmentId"] != null)
        {
            labelCzasTrwaniaChorobyText.Text = "" + DatabaseProcedures.getDisorderDurationForExamination((int)Session["AppointmentId"]);
        }
        else
        {
            labelCzasTrwaniaChorobyText.Text = "nieznany";
        }
    }

    private void loadAppointment(int appointmentId)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select DataPrzyjecia, DataOperacji, DataWypisu, Wyksztalcenie, MasaCiala, Rodzinnosc, RokZachorowania, " +
            "PierwszyObjaw, Drzenie, Sztywnosc, Spowolnienie, ObjawyInne, ObjawyInneJakie, CzasOdPoczObjDoWlLDopy, DyskinezyObecnie, DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat, CzasDyskinez, CzasOFF, PoprawaPoLDopie " +
            " from Wizyta where IdWizyta = @IdWizyta";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = appointmentId;
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
                textMasaCiala.Text = DatabaseProcedures.getTextDecimalValue(rdr["MasaCiala"]);
                dropEducation.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["Wyksztalcenie"], NO_DATA.ToString());
                dropFamily.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Rodzinnosc"]);
                if (dropRokZachorowania.Visible == true)
                {
                    dropRokZachorowania.SelectedValue = DatabaseProcedures.getDropShortValueWithNoData(rdr["RokZachorowania"], NO_DATA_SHORT.ToString());
                }
                dropSymptom.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["PierwszyObjaw"], NO_DATA.ToString());
                dropDrzenie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Drzenie"]);
                dropSztywnosc.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Sztywnosc"]);
                dropSpowolnienie.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["Spowolnienie"]);
                dropObjawy.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["ObjawyInne"]);
                textObjawy.Text = DatabaseProcedures.getTextStringValue(rdr["ObjawyInneJakie"]);
                textTimeSymptom.Text = DatabaseProcedures.getTextByteValue(rdr["CzasOdPoczObjDoWlLDopy"]);
                dropDiskinesia.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["DyskinezyObecnie"], NO_DATA.ToString());
                if (dropDiskinesia.SelectedValue == "0")
                {
                    textTimeDiskinesia.Enabled = false;
                    //RequiredFieldValidator2.Enabled = false;
                }
                else
                {
                    textTimeDiskinesia.Text = DatabaseProcedures.getTextDecimalValue(rdr["DyskinezyOdLat"]);
                    //RequiredFieldValidator2.Enabled = true;
                }
                dropFluctuations.SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr["FluktuacjeObecnie"], NO_DATA.ToString());
                if (dropFluctuations.SelectedValue == "0")
                {
                    textYearsFluctuations.Enabled = false;
                    //RequiredFieldValidator3.Enabled = false;
                }
                else
                {
                    textYearsFluctuations.Text = DatabaseProcedures.getTextDecimalValue(rdr["FluktuacjeOdLat"]);
                    //RequiredFieldValidator3.Enabled = true;
                }
                textCzasDyskinez.Text = DatabaseProcedures.getTextDecimalValue(rdr["CzasDyskinez"]);
                textCzasOFF.Text = DatabaseProcedures.getTextDecimalValue(rdr["CzasOFF"]);
                dropPoprawa.SelectedValue = DatabaseProcedures.getDropYesNoValue(rdr["PoprawaPoLDopie"]);
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
        textTimeDiskinesia.Enabled = false;
        //RequiredFieldValidator2.Enabled = false;
        textYearsFluctuations.Enabled = false;
        //RequiredFieldValidator3.Enabled = false;
    }

    protected void dropDiskinesia_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropDiskinesia.SelectedValue) == 1)
        {
            textTimeDiskinesia.Enabled = true;
            //RequiredFieldValidator2.Enabled = true;
        }
        else
        {
            textTimeDiskinesia.Enabled = false;
            textTimeDiskinesia.Text = "";
            //RequiredFieldValidator2.Enabled = false;
        }
    }

    protected void dropFluctuations_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (byte.Parse(dropFluctuations.SelectedValue) == 1)
        {
            textYearsFluctuations.Enabled = true;
            //RequiredFieldValidator3.Enabled = true;
        }
        else
        {
            textYearsFluctuations.Enabled = false;
            textYearsFluctuations.Text = "";
            //RequiredFieldValidator3.Enabled = false;
        }
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        saveAppointment();
        setDisorderDuration();
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