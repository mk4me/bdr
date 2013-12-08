using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;
using System.Diagnostics;

public partial class PartFForm : System.Web.UI.Page
{
    private static int VARIANTS = 4;
    private List<Tuple<DropDownList[], string>> UPDRSList1 = new List<Tuple<DropDownList[], string>>();
    private List<Tuple<DropDownList[], string>> UPDRSList2 = new List<Tuple<DropDownList[], string>>();
    private Tuple<TextBox[], string> UPDRSListCalculated1;
    private Tuple<TextBox[], string> UPDRSListCalculated2;
    private Tuple<DropDownList[], string> variantsHYscale;
    private Tuple<DropDownList[], string> variantsSchwabEnglandScale;
    private Tuple<DropDownList[], string> variantsJazzNovo;
    private Tuple<DropDownList[], string> variantsWideookulograf;
    private Tuple<DropDownList[], string> variantsLatencymeter;
    private List<Tuple<TextBox[], string>> variantsPartAList = new List<Tuple<TextBox[], string>>();
    private List<Tuple<DropDownList[], string>> variantsPartBList = new List<Tuple<DropDownList[], string>>();
    private List<Tuple<TextBox[], string>> variantsPartBList2 = new List<Tuple<TextBox[], string>>();
    private Tuple<DropDownList[], string> variantsTremorometria;
    private List<Tuple<TextBox[], string>> variantsPartBList3 = new List<Tuple<TextBox[], string>>();
    private Tuple<DropDownList[], string> variantsTestSchodkowy;
    private Tuple<TextBox[], string> variantsTestSchodkowyCzas1;
    private Tuple<TextBox[], string> variantsTestSchodkowyCzas2;
    private Tuple<DropDownList[], string> variantsTestMarszu;
    private Tuple<TextBox[], string> variantsTestMarszuCzas1;
    private Tuple<TextBox[], string> variantsTestMarszuCzas2;
    private Tuple<DropDownList[], string> variantsPosturografia;
    private Tuple<DropDownList[], string> variantsMotionAnalysis;
    private List<Tuple<TextBox[], string>> variantsPartCList = new List<Tuple<TextBox[], string>>();
    private Tuple<DropDownList[], string> variantsTandemPivot;
    private static byte NO_DATA = 101;  //SchwabEnglandScale 100%
    private static decimal NO_DATA_DECIMAL = 100;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            initControls();
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadVariantIds();

                // Persist changes accross postbacks.
                // Doesn't work on mobile devices?
                int[] variantIds = (int[])ViewState["VariantIds"];
                if (variantIds != null)
                {
                    for (int i = 0; i < variantIds.Length; i++)
                    {
                        loadPartA(variantIds[i], i);
                    }
                    for (int i = 0; i < variantIds.Length; i++)
                    {
                        loadPartB(variantIds[i], i);
                    }
                    for (int i = 0; i < variantIds.Length; i++)
                    {
                        loadPartC(variantIds[i], i);
                    }
                }
                else
                {
                    initParts();
                }
                loadPartF();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private void initControls()
    {
        UPDRSList2.Add(addVariantDropDowns("UPDRS_I", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_I", NO_DATA)));
        UPDRSList2.Add(addVariantDropDowns("UPDRS_II", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_II", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_18", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_18", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_19", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_19", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_FaceLipsChin", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_20_FaceLipsChin", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_20_RHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_20_LHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_RFoot", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_20_RFoot", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_LFoot", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_20_LFoot", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_21_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_21_RHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_21_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_21_LHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_Neck", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_22_Neck", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_22_RHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_22_LHand", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_RFoot", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_22_RFoot", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_LFoot", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_22_LFoot", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_23_R", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_23_R", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_23_L", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_23_L", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_24_R", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_24_R", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_24_L", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_24_L", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_25_R", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_25_R", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_25_L", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_25_L", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_26_R", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_26_R", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_26_L", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_26_L", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_27", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_27", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_28", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_28", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_29", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_29", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_30", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_30", NO_DATA)));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_31", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_31", NO_DATA)));
        UPDRSListCalculated1 = addVariantTextBoxes("UPDRS_III", tableUPDRS, false);
        UPDRSList2.Add(addVariantDropDowns("UPDRS_IV", tableUPDRS, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "UPDRS_IV", NO_DATA)));
        UPDRSListCalculated2 = addVariantTextBoxes("UPDRS_TOTAL", tableUPDRS, false);

        variantsHYscale = addVariantDropDowns("HYscale", tableUPDRSExtra, DatabaseProcedures.getEnumerationDecimalWithNoData("Badanie", "HYscale", NO_DATA_DECIMAL));
        variantsSchwabEnglandScale = addVariantDropDowns("SchwabEnglandScale", tableUPDRSExtra, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "SchwabEnglandScale", NO_DATA));
        Dictionary<byte, string> dictionaryYesNo = new Dictionary<byte, string>();
        dictionaryYesNo.Add(2, "");
        dictionaryYesNo.Add(0, "nie");
        dictionaryYesNo.Add(1, "tak");
        variantsJazzNovo = addVariantDropDowns("JazzNovo", tableUPDRSExtra, dictionaryYesNo);
        variantsWideookulograf = addVariantDropDowns("Wideookulograf", tableUPDRSExtra, dictionaryYesNo);
        variantsLatencymeter = addVariantDropDowns("Latencymeter", tableUPDRSExtra, dictionaryYesNo);
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterDurationLEFT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterLatencyLEFT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterAmplitudeLEFT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterPeakVelocityLEFT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterDurationRIGHT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterLatencyRIGHT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterAmplitudeRIGHT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterPeakVelocityRIGHT", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterDurationALL", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterLatencyALL", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterAmplitudeALL", tableUPDRSExtra, true));
        variantsPartAList.Add(addVariantTextBoxes("LatencymeterPeakVelocityALL", tableUPDRSExtra, true));

        variantsTremorometria = addVariantDropDowns("Tremorometria", tablePart2, dictionaryYesNo);
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_0_1", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_1_2", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_2_3", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_3_4", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_4_5", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_5_6", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_6_7", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_7_8", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_8_9", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_9_10", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaLEFT_23_24", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_0_1", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_1_2", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_2_3", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_3_4", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_4_5", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_5_6", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_6_7", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_7_8", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_8_9", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_9_10", tablePart2, true));
        variantsPartBList3.Add(addVariantTextBoxes("TremorometriaRIGHT_23_24", tablePart2, true));

        variantsTestSchodkowy = addVariantDropDowns("TestSchodkowy", tablePart2, dictionaryYesNo);
        variantsTestSchodkowyCzas1 = addVariantTextBoxes("TestSchodkowyCzas1", tablePart2, true);
        variantsTestSchodkowyCzas2 = addVariantTextBoxes("TestSchodkowyCzas2", tablePart2, true);
        variantsTestMarszu = addVariantDropDowns("TestMarszu", tablePart2, dictionaryYesNo);
        variantsTestMarszuCzas1 = addVariantTextBoxes("TestMarszuCzas1", tablePart2, true);
        variantsTestMarszuCzas2 = addVariantTextBoxes("TestMarszuCzas2", tablePart2, true);
        variantsPosturografia = addVariantDropDowns("Posturografia", tablePart2, dictionaryYesNo);
        variantsMotionAnalysis = addVariantDropDowns("MotionAnalysis", tablePart2, dictionaryYesNo);
        variantsPartBList.Add(variantsTremorometria);
        variantsPartBList.Add(variantsTestSchodkowy);
        variantsPartBList.Add(variantsTestMarszu);
        variantsPartBList.Add(variantsPosturografia);
        variantsPartBList.Add(variantsMotionAnalysis);
        variantsPartBList2.Add(variantsTestSchodkowyCzas1);
        variantsPartBList2.Add(variantsTestSchodkowyCzas2);
        variantsPartBList2.Add(variantsTestMarszuCzas1);
        variantsPartBList2.Add(variantsTestMarszuCzas2);

        variantsPartCList.Add(addVariantTextBoxes("UpAndGo", tablePart3, true));
        variantsPartCList.Add(addVariantTextBoxes("UpAndGoLiczby", tablePart3, true));
        variantsPartCList.Add(addVariantTextBoxes("UpAndGoKubekPrawa", tablePart3, true));
        variantsPartCList.Add(addVariantTextBoxes("UpAndGoKubekLewa", tablePart3, true));
        variantsPartCList.Add(addVariantTextBoxes("TST", tablePart3, true));
        variantsTandemPivot = addVariantDropDowns("TandemPivot", tablePart3, DatabaseProcedures.getEnumerationByteWithNoData("Badanie", "TandemPivot", NO_DATA));
        variantsPartCList.Add(addVariantTextBoxes("WTT", tablePart3, true));

        disablePhases();

        
    }

    private TableRow addVariantRow(String label, Table table)
    {
        TableRow row = new TableRow();
        table.CellPadding = 3;
        table.CellSpacing = 0;
        if (table.Rows.Count % 2 == 0)
        {
            row.BackColor = System.Drawing.Color.FromArgb(255, 255, 240, 178);
        }
        table.Rows.Add(row);
        Label labelVariant = new Label();
        labelVariant.Text = label;
        labelVariant.Width = new Unit(200);
        TableCell cell = new TableCell();
        row.Controls.Add(cell);
        cell.Controls.Add(labelVariant);

        return row;
    }

    private Tuple<DropDownList[], string> addVariantDropDowns(String label, Table table, object dictionary)
    {
        TableRow row = addVariantRow(label, table);
        DropDownList[] columns = new DropDownList[VARIANTS];
        for (int i = 0; i < VARIANTS; i++)
        {
            TableCell cell = new TableCell();
            row.Controls.Add(cell);
            DropDownList drop = new DropDownList();
            drop.Width = new Unit(104);
            drop.DataSource = dictionary;
            drop.DataTextField = "Value";
            drop.DataValueField = "Key";
            drop.DataBind();
            //drop.AutoPostBack = true;
            //drop.SelectedIndexChanged += new System.EventHandler(dropUPDRS_SelectedIndexChanged);
            cell.Controls.Add(drop);
            columns[i] = drop;
        }

        Tuple<DropDownList[], string> tuple = new Tuple<DropDownList[], string>(columns, label);

        return tuple;
    }

    //protected void dropUPDRS_SelectedIndexChanged(object sender, EventArgs e)
    //{
    //    calculateUPDRS();
    //}

    private Tuple<TextBox[], string> addVariantTextBoxes(String label, Table table, bool enabled)
    {
        TableRow row = addVariantRow(label, table);
        TextBox[] columns = new TextBox[VARIANTS];
        for (int i = 0; i < VARIANTS; i++)
        {
            TableCell cell = new TableCell();
            row.Controls.Add(cell);
            TextBox text = new TextBox();
            text.Enabled = enabled;
            text.Width = new Unit(100);
            cell.Controls.Add(text);
            columns[i] = text;
        }

        Tuple<TextBox[], string> tuple = new Tuple<TextBox[], string>(columns, label);

        return tuple;
    }

    protected void buttonCalculate_Click(object sender, EventArgs e)
    {
        calculateUPDRS();
    }

    private void calculateUPDRS()
    {
        for (int i = 0; i < VARIANTS; i++)
        {
            UPDRSListCalculated1.Item1[i].Text = calculateSum(UPDRSList1, i).ToString();
            UPDRSListCalculated2.Item1[i].Text = calculateSum(UPDRSList2, i).ToString();
        }
    }

    private int calculateSum(List<Tuple<DropDownList[], string>> UPDRSList, int variant)
    {
        int sum = 0;
        foreach (Tuple<DropDownList[], string> dropVariants in UPDRSList)
        {
            int value = int.Parse(dropVariants.Item1[variant].SelectedValue);
            if (value != NO_DATA)
            {
                sum += value;
            }
        }

        return sum;
    }

    private int saveVariantPartA(byte DBS, bool BMT, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_variant_examination_data_partA]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@DBS", SqlDbType.TinyInt).Value = DBS;
        cmd.Parameters.Add("@BMT", SqlDbType.Bit).Value = BMT;
        for (int i = 0; i < UPDRSList2.Count; i++)
        {
            cmd.Parameters.Add("@" + UPDRSList2[i].Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(UPDRSList2[i].Item1[variant].SelectedValue, NO_DATA.ToString());
        }
        for (int i = 0; i < UPDRSList1.Count; i++)
        {
            cmd.Parameters.Add("@" + UPDRSList1[i].Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(UPDRSList1[i].Item1[variant].SelectedValue, NO_DATA.ToString());
        }
        cmd.Parameters.Add("@" + UPDRSListCalculated1.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSListCalculated1.Item1[variant].Text);
        cmd.Parameters.Add("@" + UPDRSListCalculated2.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSListCalculated2.Item1[variant].Text);
        SqlParameter HYscaleDecimal = new SqlParameter("@" + variantsHYscale.Item2, SqlDbType.Decimal);
        HYscaleDecimal.Precision = 2;
        HYscaleDecimal.Scale = 1;
        HYscaleDecimal.Value = DatabaseProcedures.getDecimalOrNullWithNoData(variantsHYscale.Item1[variant].SelectedValue, NO_DATA_DECIMAL.ToString());
        cmd.Parameters.Add(HYscaleDecimal);
        cmd.Parameters.Add("@" + variantsSchwabEnglandScale.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(variantsSchwabEnglandScale.Item1[variant].SelectedValue, NO_DATA.ToString());
        cmd.Parameters.Add("@" + variantsJazzNovo.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getBitOrNull(variantsJazzNovo.Item1[variant].SelectedValue);
        cmd.Parameters.Add("@" + variantsWideookulograf.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getBitOrNull(variantsWideookulograf.Item1[variant].SelectedValue);
        cmd.Parameters.Add("@" + variantsLatencymeter.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getBitOrNull(variantsLatencymeter.Item1[variant].SelectedValue);
        for (int i = 0; i < variantsPartAList.Count; i++)
        {
            SqlParameter latencymeterDecimal = new SqlParameter("@" + variantsPartAList[i].Item2, SqlDbType.Decimal);
            latencymeterDecimal.Precision = 6;
            latencymeterDecimal.Scale = 2;
            latencymeterDecimal.Value = DatabaseProcedures.getDecimalOrNull(variantsPartAList[i].Item1[variant].Text);
            cmd.Parameters.Add(latencymeterDecimal);
        }
        bool update = false;
        if (ViewState["VariantIds"] != null)
        {
            update = true;
        }
        cmd.Parameters.Add("@allow_update_existing", SqlDbType.Bit).Value = update;
        cmd.Parameters.Add("@actor_login", SqlDbType.VarChar, 50).Value = User.Identity.Name;
        cmd.Parameters.Add("@result", SqlDbType.Int);
        cmd.Parameters["@result"].Direction = ParameterDirection.Output;
        cmd.Parameters.Add("@variant_id", SqlDbType.Int);
        cmd.Parameters["@variant_id"].Direction = ParameterDirection.Output;
        cmd.Parameters.Add("@message", SqlDbType.VarChar, 200);
        cmd.Parameters["@message"].Direction = ParameterDirection.Output;
        cmd.Connection = con;

        int success = 0;
        int variantId = 0;
        try
        {
            con.Open();
            cmd.ExecuteNonQuery();
            success = (int)cmd.Parameters["@result"].Value;
            variantId = (int)cmd.Parameters["@variant_id"].Value;

            if (success == 0)
            {
                
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

        return variantId;
    }

    private void saveVariantPartB(int examinationId, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_variant_examination_data_partB]";
        cmd.Parameters.Add("@IdBadanie", SqlDbType.Int).Value = examinationId;
        for (int i = 0; i < variantsPartBList.Count; i++)
        {
            cmd.Parameters.Add("@" + variantsPartBList[i].Item2, SqlDbType.Bit).Value = DatabaseProcedures.getBitOrNull(variantsPartBList[i].Item1[variant].SelectedValue);
        }

        for (int i = 0; i < variantsPartBList2.Count; i++)
        {
            SqlParameter testDecimal = new SqlParameter("@" + variantsPartBList2[i].Item2, SqlDbType.Decimal);
            testDecimal.Precision = 4;
            testDecimal.Scale = 2;
            testDecimal.Value = DatabaseProcedures.getDecimalOrNull(variantsPartBList2[i].Item1[variant].Text);
            cmd.Parameters.Add(testDecimal);
        }

        for (int i = 0; i < variantsPartBList3.Count; i++)
        {
            SqlParameter tremorometriaDecimal = new SqlParameter("@" + variantsPartBList3[i].Item2, SqlDbType.Decimal);
            tremorometriaDecimal.Precision = 7;
            tremorometriaDecimal.Scale = 2;
            tremorometriaDecimal.Value = DatabaseProcedures.getDecimalOrNull(variantsPartBList3[i].Item1[variant].Text);
            cmd.Parameters.Add(tremorometriaDecimal);
        }

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

    private void saveVariantPartC(int examinationId, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_variant_examination_data_partC]";
        cmd.Parameters.Add("@IdBadanie", SqlDbType.Int).Value = examinationId;
        for (int i = 0; i < variantsPartCList.Count; i++)
        {
            SqlParameter UpAndGoDecimal = new SqlParameter("@" + variantsPartCList[i].Item2, SqlDbType.Decimal);
            UpAndGoDecimal.Precision = 3;
            UpAndGoDecimal.Scale = 1;
            UpAndGoDecimal.Value = DatabaseProcedures.getDecimalOrNull(variantsPartCList[i].Item1[variant].Text);
            cmd.Parameters.Add(UpAndGoDecimal);
        }
        cmd.Parameters.Add("@" + variantsTandemPivot.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNullWithNoData(variantsTandemPivot.Item1[variant].Text, NO_DATA.ToString());
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

    private void savePartF()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_examination_questionnaire_partF]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = int.Parse(Session["AppointmentId"].ToString());
        cmd.Parameters.Add("@PDQ39", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textPDQ39.Text);
        cmd.Parameters.Add("@AIMS", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textAIMS.Text);
        cmd.Parameters.Add("@Epworth", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textEpworth.Text);
        cmd.Parameters.Add("@CGI", SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(textCGI.Text);
        SqlParameter FSSDecimal = new SqlParameter("@FSS", SqlDbType.Decimal);
        FSSDecimal.Precision = 3;
        FSSDecimal.Scale = 1;
        FSSDecimal.Value = DatabaseProcedures.getDecimalOrNull(textFSS.Text);
        cmd.Parameters.Add(FSSDecimal);
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

    private void saveVariantFile(int examinationId, string fileName, byte[] fileBytes, TextBox fileDescription)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "update Plik set IdBadanie = @IdBadanie, OpisPliku = @OpisPliku, Plik = @Plik, NazwaPliku = @NazwaPliku where IdBadanie = @IdBadanie;";
        cmd.Parameters.Add("@IdBadanie", SqlDbType.Int).Value = examinationId;
        cmd.Parameters.Add("@OpisPliku", SqlDbType.VarChar, 100).Value = DatabaseProcedures.getStringOrNull(fileDescription.Text);
        cmd.Parameters.Add("@Plik", SqlDbType.VarBinary).Value = fileBytes;
        cmd.Parameters.Add("@NazwaPliku", SqlDbType.VarChar, 255).Value = fileName;
        
        cmd.Connection = con;
        
        try
        {
            con.Open();
            int rows = cmd.ExecuteNonQuery();
            if (rows == 0)
            {
                cmd.CommandText = "insert into Plik (IdBadanie, OpisPliku, Plik, NazwaPliku) values (@IdBadanie, @OpisPliku, @Plik, @NazwaPliku);";
                cmd.ExecuteNonQuery();
            }
        }
        catch (SqlException ex)
        {
            labelMessage.Text += ex.Message;
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

    private void loadVariantIds()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select IdBadanie, DBS, BMT " +
            "from Badanie where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            int[] variantIds = new int[VARIANTS];
            int i = 0;
            while (rdr.Read() && i < VARIANTS)
            {
                byte DBS = (byte)rdr["DBS"];
                bool BMT = (bool)rdr["BMT"];
                int variantId = (int)rdr["IdBadanie"];
                variantIds[getVariantColumn(DBS, BMT)] = variantId;
                i++;
            }
            if (i > 0)
            {
                ViewState["VariantIds"] = variantIds;
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

    private int getVariantColumn(byte DBS, bool BMT)
    {
        if (BMT)
        {
            if (DBS == 1)
            {
                return 0;
            }
            else if (DBS == 0)
            {
                return 1;
            }
        }
        else
        {
            if (DBS == 1)
            {
                return 2;
            }
            else if (DBS == 0)
            {
                return 3;
            }
        }

        return 0;
    }

    private void loadPartA(int variantId, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select DBS, " +
            "BMT, " +
            "UPDRS_I, " +
            "UPDRS_II, " +
            "UPDRS_18, " +
            "UPDRS_19, " +
            "UPDRS_20_FaceLipsChin, " +
            "UPDRS_20_RHand, " +
            "UPDRS_20_LHand, " +
            "UPDRS_20_RFoot, " +
            "UPDRS_20_LFoot, " +
            "UPDRS_21_RHand, " +
            "UPDRS_21_LHand, " +
            "UPDRS_22_Neck, " +
            "UPDRS_22_RHand, " +
            "UPDRS_22_LHand, " +
            "UPDRS_22_RFoot, " +
            "UPDRS_22_LFoot, " +
            "UPDRS_23_R, " +
            "UPDRS_23_L, " +
            "UPDRS_24_R, " +
            "UPDRS_24_L, " +
            "UPDRS_25_R, " +
            "UPDRS_25_L, " +
            "UPDRS_26_R, " +
            "UPDRS_26_L, " +
            "UPDRS_27, " +
            "UPDRS_28, " +
            "UPDRS_29, " +
            "UPDRS_30, " +
            "UPDRS_31, " +
            "UPDRS_III, " +
            "UPDRS_IV, " +
            "UPDRS_TOTAL, " +
            "HYscale, " +
            "SchwabEnglandScale, " +
            "JazzNovo, " +
            "Wideookulograf, " +
            "Latencymeter, " +
            "LatencymeterDurationLEFT, " +
            "LatencymeterLatencyLEFT, " +
            "LatencymeterAmplitudeLEFT, " +
            "LatencymeterPeakVelocityLEFT, " +
            "LatencymeterDurationRIGHT, " +
            "LatencymeterLatencyRIGHT, " +
            "LatencymeterAmplitudeRIGHT, " +
            "LatencymeterPeakVelocityRIGHT, " +
            "LatencymeterDurationALL, " +
            "LatencymeterLatencyALL, " +
            "LatencymeterAmplitudeALL, " +
            "LatencymeterPeakVelocityALL " +
            "from Badanie where IdBadanie = " + variantId;
        cmd.Connection = con;
        
        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                for (int i = 0; i < UPDRSList2.Count; i++)
                {
                    UPDRSList2[i].Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr[UPDRSList2[i].Item2], NO_DATA.ToString());
                }
                for (int i = 0; i < UPDRSList1.Count; i++)
                {
                    UPDRSList1[i].Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr[UPDRSList1[i].Item2], NO_DATA.ToString());
                }
                UPDRSListCalculated1.Item1[variant].Text = DatabaseProcedures.getTextByteValue(rdr[UPDRSListCalculated1.Item2]);
                UPDRSListCalculated2.Item1[variant].Text = DatabaseProcedures.getTextByteValue(rdr[UPDRSListCalculated2.Item2]);
                variantsHYscale.Item1[variant].SelectedValue = DatabaseProcedures.getDropDecimalValueWithNoData(rdr[variantsHYscale.Item2], NO_DATA_DECIMAL.ToString());
                variantsSchwabEnglandScale.Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr[variantsSchwabEnglandScale.Item2], NO_DATA.ToString());
                variantsJazzNovo.Item1[variant].SelectedValue = DatabaseProcedures.getDropBitValue(rdr[variantsJazzNovo.Item2]);
                variantsWideookulograf.Item1[variant].SelectedValue = DatabaseProcedures.getDropBitValue(rdr[variantsWideookulograf.Item2]);
                variantsLatencymeter.Item1[variant].SelectedValue = DatabaseProcedures.getDropBitValue(rdr[variantsLatencymeter.Item2]);
                for (int i = 0; i < variantsPartAList.Count; i++)
                {
                    variantsPartAList[i].Item1[variant].Text = DatabaseProcedures.getTextDecimalValue(rdr[variantsPartAList[i].Item2]);
                }
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

    private void loadPartB(int variantId, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select Tremorometria, " +
            "TremorometriaLEFT_0_1, " +
            "TremorometriaLEFT_1_2, " +
            "TremorometriaLEFT_2_3, " +
            "TremorometriaLEFT_3_4, " +
            "TremorometriaLEFT_4_5, " +
            "TremorometriaLEFT_5_6, " +
            "TremorometriaLEFT_6_7, " +
            "TremorometriaLEFT_7_8, " +
            "TremorometriaLEFT_8_9, " +
            "TremorometriaLEFT_9_10, " +
            "TremorometriaLEFT_23_24, " +
            "TremorometriaRIGHT_0_1, " +
            "TremorometriaRIGHT_1_2, " +
            "TremorometriaRIGHT_2_3, " +
            "TremorometriaRIGHT_3_4, " +
            "TremorometriaRIGHT_4_5, " +
            "TremorometriaRIGHT_5_6, " +
            "TremorometriaRIGHT_6_7, " +
            "TremorometriaRIGHT_7_8, " +
            "TremorometriaRIGHT_8_9, " +
            "TremorometriaRIGHT_9_10, " +
            "TremorometriaRIGHT_23_24, " +
            "TestSchodkowy, " +
            "TestSchodkowyCzas1, " +
            "TestSchodkowyCzas2, " +
            "TestMarszu, " +
            "TestMarszuCzas1, " +
            "TestMarszuCzas2, " +
            "Posturografia, " +
            "MotionAnalysis " +
            "from Badanie where IdBadanie = " + variantId;
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                for (int i = 0; i < variantsPartBList.Count; i++)
                {
                    variantsPartBList[i].Item1[variant].SelectedValue = DatabaseProcedures.getDropBitValue(rdr[variantsPartBList[i].Item2]);
                }
                for (int i = 0; i < variantsPartBList2.Count; i++)
                {
                    variantsPartBList2[i].Item1[variant].Text = DatabaseProcedures.getTextDecimalValue(rdr[variantsPartBList2[i].Item2]);
                }
                for (int i = 0; i < variantsPartBList3.Count; i++)
                {
                    variantsPartBList3[i].Item1[variant].Text = DatabaseProcedures.getTextDecimalValue(rdr[variantsPartBList3[i].Item2]);
                }
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

    private void loadPartC(int variantId, int variant)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select UpAndGo, " +
            "UpAndGoLiczby, " +
            "UpAndGoKubekPrawa, " +
            "UpAndGoKubekLewa, " +
            "TST, " +
            "TandemPivot, " +
            "WTT " +
            "from Badanie where IdBadanie = " + variantId;
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                for (int i = 0; i < variantsPartCList.Count; i++)
                {
                    variantsPartCList[i].Item1[variant].Text = DatabaseProcedures.getTextDecimalValue(rdr[variantsPartCList[i].Item2]);
                }
                variantsTandemPivot.Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValueWithNoData(rdr[variantsTandemPivot.Item2], NO_DATA.ToString());
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

    private void loadPartF()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select PDQ39, AIMS, Epworth, CGI, FSS " +
            "from Wizyta where IdWizyta = " + Session["AppointmentId"];
        cmd.Connection = con;

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                textPDQ39.Text = DatabaseProcedures.getTextByteValue(rdr["PDQ39"]);
                textAIMS.Text = DatabaseProcedures.getTextByteValue(rdr["AIMS"]);
                textEpworth.Text = DatabaseProcedures.getTextByteValue(rdr["Epworth"]);
                textCGI.Text = DatabaseProcedures.getTextByteValue(rdr["CGI"]);
                textFSS.Text = DatabaseProcedures.getTextDecimalValue(rdr["FSS"]);
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

    private void initParts()
    {
        toggleButtons(false);

        for (int variant = 0; variant < VARIANTS; variant++)
        {
            for (int i = 0; i < UPDRSList2.Count; i++)
            {
                UPDRSList2[i].Item1[variant].SelectedValue = NO_DATA.ToString();
            }
            for (int i = 0; i < UPDRSList1.Count; i++)
            {
                UPDRSList1[i].Item1[variant].SelectedValue = NO_DATA.ToString();
            }
            variantsHYscale.Item1[variant].SelectedValue = NO_DATA_DECIMAL.ToString();
            variantsSchwabEnglandScale.Item1[variant].SelectedValue = NO_DATA.ToString();
            variantsJazzNovo.Item1[variant].SelectedValue = NO_DATA.ToString();
            variantsWideookulograf.Item1[variant].SelectedValue = NO_DATA.ToString();
            variantsLatencymeter.Item1[variant].SelectedValue = NO_DATA.ToString();
            variantsTandemPivot.Item1[variant].SelectedValue = NO_DATA.ToString();
        }
    }

    private void disablePhases()
    {
        List<int> variantList = new List<int>();

        if (Session["AppointmentName"].ToString() == "przedoperacyjna")
        {
            variantList.Add(getVariantColumn(1, true));
            variantList.Add(getVariantColumn(1, false));
            foreach (Tuple<DropDownList[], string> tuple in UPDRSList1)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            foreach (Tuple<DropDownList[], string> tuple in UPDRSList2)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            foreach (Tuple<TextBox[], string> tuple in variantsPartAList)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            foreach (Tuple<DropDownList[], string> tuple in variantsPartBList)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            foreach (Tuple<TextBox[], string> tuple in variantsPartBList2)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            foreach (Tuple<TextBox[], string> tuple in variantsPartBList3)
            {
                disableWebControlVariants(tuple.Item1, variantList);
            }
            disableWebControlVariants(variantsHYscale.Item1, variantList);
            disableWebControlVariants(variantsSchwabEnglandScale.Item1, variantList);
            disableWebControlVariants(variantsJazzNovo.Item1, variantList);
            disableWebControlVariants(variantsWideookulograf.Item1, variantList);
            disableWebControlVariants(variantsLatencymeter.Item1, variantList);

            disableWebControlVariants(variantsTestSchodkowy.Item1, variantList);
            disableWebControlVariants(variantsTestSchodkowyCzas1.Item1, variantList);
            disableWebControlVariants(variantsTestSchodkowyCzas2.Item1, variantList);
            disableWebControlVariants(variantsTestMarszu.Item1, variantList);
            disableWebControlVariants(variantsTestMarszuCzas1.Item1, variantList);
            disableWebControlVariants(variantsTestMarszuCzas2.Item1, variantList);
            disableWebControlVariants(variantsPosturografia.Item1, variantList);
            disableWebControlVariants(variantsMotionAnalysis.Item1, variantList);
            foreach (Tuple<TextBox[], string> variantsPartC in variantsPartCList)
            {
                disableWebControlVariants(variantsPartC.Item1, variantList);
            }
            disableWebControlVariants(variantsTandemPivot.Item1, variantList);
        }
    }

    private void disableWebControlVariants(WebControl[] webControlVariants, List<int> variantList)
    {
        foreach (int i in variantList)
        {
            webControlVariants[i].Enabled = false;
        }
    }

    protected void buttonOK_Click(object sender, EventArgs e)
    {
        savePartF();
    }
    protected void buttonCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AppointmentForm.aspx");
    }
    protected void buttonSavePartA_Click(object sender, EventArgs e)
    {
        calculateUPDRS();
        int[] variantIds = new int[VARIANTS];
        for (byte DBS = 0, i = 0; i < VARIANTS; DBS++, i++)
        {
            bool BMT = true;
            int variant = getVariantColumn(DBS, BMT);
            variantIds[variant] = saveVariantPartA(DBS, BMT, variant);
            i++;
            BMT = false;
            variant = getVariantColumn(DBS, BMT);
            variantIds[variant] = saveVariantPartA(DBS, BMT, variant);
        }
        if (variantIds.Length > 0)
        {
            ViewState["VariantIds"] = variantIds;
            toggleButtons(true);
        }
    }
    protected void buttonSavePartB_Click(object sender, EventArgs e)
    {
        int[] variantIds = (int[])ViewState["VariantIds"];
        if (variantIds != null)
        {
            for (int i = 0; i < variantIds.Length; i++)
            {
                saveVariantPartB(variantIds[i], i);
            }
        }
    }
    protected void buttonSavePartC_Click(object sender, EventArgs e)
    {
        int[] variantIds = (int[])ViewState["VariantIds"];
        if (variantIds != null)
        {
            for (int i = 0; i < variantIds.Length; i++)
            {
                saveVariantPartC(variantIds[i], i);
            }
        }
    }

    protected void buttonSaveFiles_Click(object sender, EventArgs e)
    {
        int[] variantIds = (int[])ViewState["VariantIds"];
        if (variantIds != null && variantIds.Length >= 4)
        {
            if (fileUploadVariant1.HasFile)
            {
                saveVariantFile(variantIds[0], fileUploadVariant1.FileName, fileUploadVariant1.FileBytes, textOpis1);
            }
            if (fileUploadVariant2.HasFile)
            {
                saveVariantFile(variantIds[1], fileUploadVariant2.FileName, fileUploadVariant2.FileBytes, textOpis2);
            }
            if (fileUploadVariant3.HasFile)
            {
                saveVariantFile(variantIds[2], fileUploadVariant3.FileName, fileUploadVariant3.FileBytes, textOpis3);
            }
            if (fileUploadVariant4.HasFile)
            {
                saveVariantFile(variantIds[3], fileUploadVariant4.FileName, fileUploadVariant4.FileBytes, textOpis4);
            }
        }
    }

    private void toggleButtons(bool enable)
    {
        buttonSavePartB.Enabled = enable;
        buttonSavePartC.Enabled = enable;
        buttonSaveFiles.Enabled = enable;
    }
}