﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PatientForm : System.Web.UI.Page
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
            dropYear.SelectedIndex = currentYear - minYear - 50;

            for (int i = 1; i <= 12; i++)
            {
                dropMonth.Items.Add(new ListItem("" + i, "" + i));
            }

            for (int i = 0; i <= 10; i++)
            {
                dropElectrodes.Items.Add(new ListItem("" + i, "" + i));
            }
            dropElectrodes.SelectedIndex = 2;
        }
    }

    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Main.aspx");
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        int sex = 0;
        if (radioMan.Checked)
        {
            sex = 1;
        }
        savePatient(textPatientNumber.Text, int.Parse(dropYear.SelectedValue), int.Parse(dropMonth.SelectedValue), sex,
            textLocation.Text, int.Parse(dropElectrodes.SelectedValue), false);
    }

    private void savePatient(string number, int birthYear, int birthMonth, int sex,
        string location, int electrodes, bool update)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_patient]";
        cmd.Parameters.Add("@NumerPacjenta", SqlDbType.VarChar, 20).Value = number;
        cmd.Parameters.Add("@RokUrodzenia", SqlDbType.SmallInt).Value = (short)birthYear;
        cmd.Parameters.Add("@MiesiacUrodzenia", SqlDbType.TinyInt).Value = (byte)birthMonth;
        cmd.Parameters.Add("@Plec", SqlDbType.TinyInt).Value = sex;
        cmd.Parameters.Add("@Lokalizacja", SqlDbType.VarChar, 10).Value = location;
        cmd.Parameters.Add("@LiczbaElektrod", SqlDbType.TinyInt).Value = (byte)electrodes;
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