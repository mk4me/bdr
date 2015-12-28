using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartGForm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadPartG();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void savePartG()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partG]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@TestZegara", SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(dropTestZegara.SelectedValue);
        cmd.Parameters.Add("@MMSE", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textMMSE.Text);
        cmd.Parameters.Add("@TestZegaraACE_III", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestZegaraACE_III.Text);
        cmd.Parameters.Add("@CLOX1_Rysunek", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCLOX1_Rysunek.Text);
        cmd.Parameters.Add("@CLOX2_Kopia", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCLOX2_Kopia.Text);
        cmd.Parameters.Add("@AVLT_proba_1", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_proba_1.Text);
        cmd.Parameters.Add("@AVLT_proba_2", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_proba_2.Text);
        cmd.Parameters.Add("@AVLT_proba_3", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_proba_3.Text);
        cmd.Parameters.Add("@AVLT_proba_4", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_proba_4.Text);
        cmd.Parameters.Add("@AVLT_proba_5", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_proba_5.Text);
        cmd.Parameters.Add("@AVLT_Suma", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_Suma.Text);
        SqlParameter AVLT_SredniaDecimal = new SqlParameter("@AVLT_Srednia", SqlDbType.Decimal);
        AVLT_SredniaDecimal.Precision = 4;
        AVLT_SredniaDecimal.Scale = 2;
        AVLT_SredniaDecimal.Value = DatabaseProcedures.getDecimalOrNull(textAVLT_Srednia.Text);
        cmd.Parameters.Add(AVLT_SredniaDecimal);
        cmd.Parameters.Add("@AVLT_KrotkieOdroczenie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_KrotkieOdroczenie.Text);
        SqlParameter AVLT_Odroczony20minDecimal = new SqlParameter("@AVLT_Odroczony20min", SqlDbType.Decimal);
        AVLT_Odroczony20minDecimal.Precision = 4;
        AVLT_Odroczony20minDecimal.Scale = 2;
        AVLT_Odroczony20minDecimal.Value = DatabaseProcedures.getDecimalOrNull(textAVLT_Odroczony20min.Text);
        cmd.Parameters.Add(AVLT_Odroczony20minDecimal);
        cmd.Parameters.Add("@AVLT_Rozpoznawanie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_Rozpoznawanie.Text);
        cmd.Parameters.Add("@AVLT_BledyRozpoznania", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAVLT_BledyRozpoznania.Text);
        cmd.Parameters.Add("@TestAVLTSrednia", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestAVLTSrednia.Text);
        cmd.Parameters.Add("@TestAVLTOdroczony", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestAVLTOdroczony.Text);
        cmd.Parameters.Add("@TestAVLTPo20min", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestAVLTPo20min.Text);
        cmd.Parameters.Add("@TestAVLTRozpoznawanie", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestAVLTRozpoznawanie.Text);
        cmd.Parameters.Add("@CVLT_proba_1", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_proba_1.Text);
        cmd.Parameters.Add("@CVLT_proba_2", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_proba_2.Text);
        cmd.Parameters.Add("@CVLT_proba_3", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_proba_3.Text);
        cmd.Parameters.Add("@CVLT_proba_4", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_proba_4.Text);
        cmd.Parameters.Add("@CVLT_proba_5", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_proba_5.Text);
        cmd.Parameters.Add("@CVLT_Suma", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_Suma.Text);
        SqlParameter CVLT_OSKO_krotkie_odroczenieDecimal = new SqlParameter("@CVLT_OSKO_krotkie_odroczenie", SqlDbType.Decimal);
        CVLT_OSKO_krotkie_odroczenieDecimal.Precision = 4;
        CVLT_OSKO_krotkie_odroczenieDecimal.Scale = 2;
        CVLT_OSKO_krotkie_odroczenieDecimal.Value = DatabaseProcedures.getDecimalOrNull(textCVLT_OSKO_krotkie_odroczenie.Text);
        cmd.Parameters.Add(CVLT_OSKO_krotkie_odroczenieDecimal);
        cmd.Parameters.Add("@CVLT_OPKO_krotkie_odroczenie_i_pomoc", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_OPKO_krotkie_odroczenie_i_pomoc.Text);
        SqlParameter CVLT_OSDO_po20minDecimal = new SqlParameter("@CVLT_OSDO_po20min", SqlDbType.Decimal);
        CVLT_OSDO_po20minDecimal.Precision = 4;
        CVLT_OSDO_po20minDecimal.Scale = 2;
        CVLT_OSDO_po20minDecimal.Value = DatabaseProcedures.getDecimalOrNull(textCVLT_OSDO_po20min.Text);
        cmd.Parameters.Add(CVLT_OSDO_po20minDecimal);
        cmd.Parameters.Add("@CVLT_OPDO_po20min_i_pomoc", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_OPDO_po20min_i_pomoc.Text);
        cmd.Parameters.Add("@CVLT_perseweracje", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_perseweracje.Text);
        cmd.Parameters.Add("@CVLT_WtraceniaOdtwarzanieSwobodne", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_WtraceniaOdtwarzanieSwobodne.Text);
        cmd.Parameters.Add("@CVLT_wtraceniaOdtwarzanieZPomoca", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_wtraceniaOdtwarzanieZPomoca.Text);
        cmd.Parameters.Add("@CVLT_Rozpoznawanie", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_Rozpoznawanie.Text);
        cmd.Parameters.Add("@CVLT_BledyRozpoznania", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCVLT_BledyRozpoznania.Text);
        cmd.Parameters.Add("@Benton_JOL", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textBenton_JOL.Text);
        cmd.Parameters.Add("@TFZ_ReyaLubInny", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTFZ_ReyaLubInny.Text);
        cmd.Parameters.Add("@WAIS_R_Wiadomosci", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textWAIS_R_Wiadomosci.Text);
        cmd.Parameters.Add("@WAIS_R_PowtarzanieCyfr", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textWAIS_R_PowtarzanieCyfr.Text);
        cmd.Parameters.Add("@WAIS_R_Podobienstwa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textWAIS_R_Podobienstwa.Text);
        cmd.Parameters.Add("@BostonskiTestNazywaniaBNT", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textBostonskiTestNazywaniaBNT.Text);
        cmd.Parameters.Add("@BNT_SredniCzasReakcji_sek", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textBNT_SredniCzasReakcji_sek.Text);
        SqlParameter SkalaDepresjiBeckaDecimal = new SqlParameter("@SkalaDepresjiBecka", SqlDbType.Decimal);
        SkalaDepresjiBeckaDecimal.Precision = 4;
        SkalaDepresjiBeckaDecimal.Scale = 1;
        SkalaDepresjiBeckaDecimal.Value = DatabaseProcedures.getDecimalOrNull(textSkalaDepresjiBecka.Text);
        cmd.Parameters.Add(SkalaDepresjiBeckaDecimal);
        SqlParameter SkalaDepresjiBeckaDecimalII = new SqlParameter("@SkalaDepresjiBeckaII", SqlDbType.Decimal);
        SkalaDepresjiBeckaDecimalII.Precision = 4;
        SkalaDepresjiBeckaDecimalII.Scale = 1;
        SkalaDepresjiBeckaDecimalII.Value = DatabaseProcedures.getDecimalOrNull(textSkalaDepresjiBeckaII.Text);
        cmd.Parameters.Add(SkalaDepresjiBeckaDecimalII);
        cmd.Parameters.Add("@TestFluencjiK", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiK.Text);
        cmd.Parameters.Add("@TestFluencjiP", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiP.Text);
        cmd.Parameters.Add("@TestFluencjiZwierzeta", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiZwierzeta.Text);
        cmd.Parameters.Add("@TestFluencjiOwoceWarzywa", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiOwoceWarzywa.Text);
        cmd.Parameters.Add("@TestFluencjiOstre", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textTestFluencjiOstre.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowA", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestLaczeniaPunktowA.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowB", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestLaczeniaPunktowB.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowA_maly", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestLaczeniaPunktowA_maly.Text);
        cmd.Parameters.Add("@TestLaczeniaPunktowB_maly", SqlDbType.VarChar, 40).Value = DatabaseProcedures.getStringOrNull(textTestLaczeniaPunktowB_maly.Text);
        cmd.Parameters.Add("@ToL_SumaRuchow", SqlDbType.Int).Value = DatabaseProcedures.getIntOrNull(textToL_SumaRuchow.Text);
        cmd.Parameters.Add("@ToL_LiczbaPrawidlowych", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textToL_LiczbaPrawidlowych.Text);
        cmd.Parameters.Add("@ToL_CzasInicjowania_sek", SqlDbType.Int).Value = DatabaseProcedures.getIntOrNull(textToL_CzasInicjowania_sek.Text);
        cmd.Parameters.Add("@ToL_CzasWykonania_sek", SqlDbType.Int).Value = DatabaseProcedures.getIntOrNull(textToL_CzasWykonania_sek.Text);
        cmd.Parameters.Add("@ToL_CzasCalkowity_sek", SqlDbType.Int).Value = DatabaseProcedures.getIntOrNull(textToL_CzasCalkowity_sek.Text);
        cmd.Parameters.Add("@ToL_CzasPrzekroczony", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textToL_CzasPrzekroczony.Text);
        cmd.Parameters.Add("@ToL_LiczbaPrzekroczenZasad", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textToL_LiczbaPrzekroczenZasad.Text);
        cmd.Parameters.Add("@ToL_ReakcjeUkierunkowane", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textToL_ReakcjeUkierunkowane.Text);
        cmd.Parameters.Add("@InnePsychologiczne", SqlDbType.VarChar, 150).Value = DatabaseProcedures.getStringOrNull(textInnePsychologiczne.Text);
        cmd.Parameters.Add("@OpisBadania", SqlDbType.VarChar, 2000).Value = DatabaseProcedures.getStringOrNull(textOpisBadania.Text);
        cmd.Parameters.Add("@Wnioski", SqlDbType.VarChar, 2000).Value = DatabaseProcedures.getStringOrNull(textWnioski.Text);
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

    private void loadPartG()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select TestZegara, MMSE, TestZegaraACE_III, CLOX1_Rysunek, CLOX2_Kopia, " +
            "AVLT_proba_1, AVLT_proba_2, AVLT_proba_3, AVLT_proba_4, AVLT_proba_5, AVLT_Suma, AVLT_Srednia, AVLT_KrotkieOdroczenie, AVLT_Odroczony20min, AVLT_Rozpoznawanie, AVLT_BledyRozpoznania, " +
            "TestAVLTSrednia, TestAVLTOdroczony, TestAVLTPo20min, TestAVLTRozpoznawanie, " +
            "CVLT_proba_1, CVLT_proba_2, CVLT_proba_3, CVLT_proba_4, CVLT_proba_5, CVLT_Suma, CVLT_OSKO_krotkie_odroczenie, CVLT_OPKO_krotkie_odroczenie_i_pomoc, " +
            "CVLT_OSDO_po20min, CVLT_OPDO_po20min_i_pomoc, CVLT_perseweracje, CVLT_WtraceniaOdtwarzanieSwobodne, CVLT_wtraceniaOdtwarzanieZPomoca, CVLT_Rozpoznawanie, CVLT_BledyRozpoznania, " +
            "Benton_JOL, TFZ_ReyaLubInny, WAIS_R_Wiadomosci, WAIS_R_PowtarzanieCyfr, WAIS_R_Podobienstwa, BostonskiTestNazywaniaBNT, BNT_SredniCzasReakcji_sek, SkalaDepresjiBecka, SkalaDepresjiBeckaII, " +
            "TestFluencjiK, TestFluencjiP, TestFluencjiZwierzeta, TestFluencjiOwoceWarzywa, TestFluencjiOstre, TestLaczeniaPunktowA, TestLaczeniaPunktowB, TestLaczeniaPunktowA_maly, TestLaczeniaPunktowB_maly, " +
            "ToL_SumaRuchow, ToL_LiczbaPrawidlowych, ToL_CzasInicjowania_sek, ToL_CzasWykonania_sek, ToL_CzasCalkowity_sek, ToL_CzasPrzekroczony, ToL_LiczbaPrzekroczenZasad, ToL_ReakcjeUkierunkowane, " +
            "InnePsychologiczne, OpisBadania, Wnioski " +
            "from Wizyta where IdWizyta = @IdWizyta";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                dropTestZegara.SelectedValue = DatabaseProcedures.getDropBitValue(rdr["TestZegara"]);
                textMMSE.Text = DatabaseProcedures.getTextByteValue(rdr["MMSE"]);
                textTestZegaraACE_III.Text = DatabaseProcedures.getTextByteValue(rdr["TestZegaraACE_III"]);
                textCLOX1_Rysunek.Text = DatabaseProcedures.getTextByteValue(rdr["CLOX1_Rysunek"]);
                textCLOX2_Kopia.Text = DatabaseProcedures.getTextByteValue(rdr["CLOX2_Kopia"]);
                textAVLT_proba_1.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_proba_1"]);
                textAVLT_proba_2.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_proba_2"]);
                textAVLT_proba_3.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_proba_3"]);
                textAVLT_proba_4.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_proba_4"]);
                textAVLT_proba_5.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_proba_5"]);
                textAVLT_Suma.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_Suma"]);
                textAVLT_Srednia.Text = DatabaseProcedures.getTextDecimalValue(rdr["AVLT_Srednia"]);
                textAVLT_KrotkieOdroczenie.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_KrotkieOdroczenie"]);
                textAVLT_Odroczony20min.Text = DatabaseProcedures.getTextDecimalValue(rdr["AVLT_Odroczony20min"]);
                textAVLT_Rozpoznawanie.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_Rozpoznawanie"]);
                textAVLT_BledyRozpoznania.Text = DatabaseProcedures.getTextByteValue(rdr["AVLT_BledyRozpoznania"]);
                textTestAVLTSrednia.Text = DatabaseProcedures.getTextStringValue(rdr["TestAVLTSrednia"]);
                textTestAVLTOdroczony.Text = DatabaseProcedures.getTextStringValue(rdr["TestAVLTOdroczony"]);
                textTestAVLTPo20min.Text = DatabaseProcedures.getTextStringValue(rdr["TestAVLTPo20min"]);
                textTestAVLTRozpoznawanie.Text = DatabaseProcedures.getTextStringValue(rdr["TestAVLTRozpoznawanie"]);
                textCVLT_proba_1.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_proba_1"]);
                textCVLT_proba_2.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_proba_2"]);
                textCVLT_proba_3.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_proba_3"]);
                textCVLT_proba_4.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_proba_4"]);
                textCVLT_proba_5.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_proba_5"]);
                textCVLT_Suma.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_Suma"]);
                textCVLT_OSKO_krotkie_odroczenie.Text = DatabaseProcedures.getTextDecimalValue(rdr["CVLT_OSKO_krotkie_odroczenie"]);
                textCVLT_OPKO_krotkie_odroczenie_i_pomoc.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_OPKO_krotkie_odroczenie_i_pomoc"]);
                textCVLT_OSDO_po20min.Text = DatabaseProcedures.getTextDecimalValue(rdr["CVLT_OSDO_po20min"]);
                textCVLT_OPDO_po20min_i_pomoc.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_OPDO_po20min_i_pomoc"]);
                textCVLT_perseweracje.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_perseweracje"]);
                textCVLT_WtraceniaOdtwarzanieSwobodne.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_WtraceniaOdtwarzanieSwobodne"]);
                textCVLT_wtraceniaOdtwarzanieZPomoca.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_wtraceniaOdtwarzanieZPomoca"]);
                textCVLT_Rozpoznawanie.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_Rozpoznawanie"]);
                textCVLT_BledyRozpoznania.Text = DatabaseProcedures.getTextByteValue(rdr["CVLT_BledyRozpoznania"]);
                textBenton_JOL.Text = DatabaseProcedures.getTextByteValue(rdr["Benton_JOL"]);
                textTFZ_ReyaLubInny.Text = DatabaseProcedures.getTextByteValue(rdr["TFZ_ReyaLubInny"]);
                textWAIS_R_Wiadomosci.Text = DatabaseProcedures.getTextByteValue(rdr["WAIS_R_Wiadomosci"]);
                textWAIS_R_PowtarzanieCyfr.Text = DatabaseProcedures.getTextByteValue(rdr["WAIS_R_PowtarzanieCyfr"]);
                textWAIS_R_Podobienstwa.Text = DatabaseProcedures.getTextByteValue(rdr["WAIS_R_Podobienstwa"]);
                textBostonskiTestNazywaniaBNT.Text = DatabaseProcedures.getTextByteValue(rdr["BostonskiTestNazywaniaBNT"]);
                textBNT_SredniCzasReakcji_sek.Text = DatabaseProcedures.getTextIntValue(rdr["BNT_SredniCzasReakcji_sek"]);
                textSkalaDepresjiBecka.Text = DatabaseProcedures.getTextDecimalValue(rdr["SkalaDepresjiBecka"]);
                textSkalaDepresjiBeckaII.Text = DatabaseProcedures.getTextDecimalValue(rdr["SkalaDepresjiBeckaII"]);
                textTestFluencjiK.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiK"]);
                textTestFluencjiP.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiP"]);
                textTestFluencjiZwierzeta.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiZwierzeta"]);
                textTestFluencjiOwoceWarzywa.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiOwoceWarzywa"]);
                textTestFluencjiOstre.Text = DatabaseProcedures.getTextByteValue(rdr["TestFluencjiOstre"]);
                textTestLaczeniaPunktowA.Text = DatabaseProcedures.getTextStringValue(rdr["TestLaczeniaPunktowA"]);
                textTestLaczeniaPunktowB.Text = DatabaseProcedures.getTextStringValue(rdr["TestLaczeniaPunktowB"]);
                textTestLaczeniaPunktowA_maly.Text = DatabaseProcedures.getTextStringValue(rdr["TestLaczeniaPunktowA_maly"]);
                textTestLaczeniaPunktowB_maly.Text = DatabaseProcedures.getTextStringValue(rdr["TestLaczeniaPunktowB_maly"]);
                textToL_SumaRuchow.Text = DatabaseProcedures.getTextIntValue(rdr["ToL_SumaRuchow"]);
                textToL_LiczbaPrawidlowych.Text = DatabaseProcedures.getTextByteValue(rdr["ToL_LiczbaPrawidlowych"]);
                textToL_CzasInicjowania_sek.Text = DatabaseProcedures.getTextIntValue(rdr["ToL_CzasInicjowania_sek"]);
                textToL_CzasWykonania_sek.Text = DatabaseProcedures.getTextIntValue(rdr["ToL_CzasWykonania_sek"]);
                textToL_CzasCalkowity_sek.Text = DatabaseProcedures.getTextIntValue(rdr["ToL_CzasCalkowity_sek"]);
                textToL_CzasPrzekroczony.Text = DatabaseProcedures.getTextByteValue(rdr["ToL_CzasPrzekroczony"]);
                textToL_LiczbaPrzekroczenZasad.Text = DatabaseProcedures.getTextByteValue(rdr["ToL_LiczbaPrzekroczenZasad"]);
                textToL_ReakcjeUkierunkowane.Text = DatabaseProcedures.getTextByteValue(rdr["ToL_ReakcjeUkierunkowane"]);
                textInnePsychologiczne.Text = DatabaseProcedures.getTextStringValue(rdr["InnePsychologiczne"]);
                textOpisBadania.Text = DatabaseProcedures.getTextStringValue(rdr["OpisBadania"]);
                textWnioski.Text = DatabaseProcedures.getTextStringValue(rdr["Wnioski"]);
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
        savePartG();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
}