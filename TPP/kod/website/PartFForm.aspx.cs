using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class PartFForm : System.Web.UI.Page
{
    private static int VARIANTS = 8;
    private List<Tuple<DropDownList[], string>> UPDRSList1 = new List<Tuple<DropDownList[], string>>();
    private List<Tuple<DropDownList[], string>> UPDRSList2 = new List<Tuple<DropDownList[], string>>();
    private Tuple<TextBox[], string> UPDRSListCalculated1;
    private Tuple<TextBox[], string> UPDRSListCalculated2;
    private Tuple<DropDownList[], string> variantsHYscale;
    private Tuple<DropDownList[], string> variantsSchwabEnglandScale;
    private Tuple<DropDownList[], string> variantsOkulografiaUrzadzenie;
    private Tuple<DropDownList[], string> variantsWideo;
    private List<Tuple<DropDownList[], string>> variantsPartBList = new List<Tuple<DropDownList[], string>>();
    private List<Tuple<TextBox[], string>> variantsPartBList2 = new List<Tuple<TextBox[], string>>();
    private Tuple<DropDownList[], string> variantsTremorometria;
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

    protected void Page_Load(object sender, EventArgs e)
    {
        UPDRSList2.Add(addVariantDropDowns("UPDRS_I", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_I")));
        UPDRSList2.Add(addVariantDropDowns("UPDRS_II", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_II")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_18", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_18")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_19", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_19")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_FaceLipsChin", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_20_FaceLipsChin")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_20_RHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_20_LHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_RFoot", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_20_RFoot")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_20_LFoot", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_20_LFoot")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_21_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_21_RHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_21_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_21_LHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_Neck", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_22_Neck")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_22_RHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_LHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_22_LHand")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_RFoot", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_22_RFoot")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_22_LFoot", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_22_LFoot")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_23_R", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_23_R")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_23_L", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_23_L")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_24_R", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_24_R")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_24_L", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_24_L")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_25_R", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_25_R")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_25_L", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_25_L")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_26_R", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_26_R")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_26_L", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_26_L")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_27", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_27")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_28", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_28")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_29", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_29")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_30", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_30")));
        UPDRSList1.Add(addVariantDropDowns("UPDRS_31", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_31")));
        UPDRSListCalculated1 = addVariantTextBoxes("UPDRS_III", tableUPDRS, false);
        UPDRSList2.Add(addVariantDropDowns("UPDRS_IV", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_IV")));
        UPDRSListCalculated2 = addVariantTextBoxes("UPDRS_TOTAL", tableUPDRS, false);

        variantsHYscale = addVariantDropDowns("HYscale", tableUPDRSExtra, DatabaseProcedures.getEnumerationDecimal("Badanie", "HYscale"));
        variantsSchwabEnglandScale = addVariantDropDowns("SchwabEnglandScale", tableUPDRSExtra, DatabaseProcedures.getEnumerationByte("Badanie", "SchwabEnglandScale"));
        variantsOkulografiaUrzadzenie = addVariantDropDowns("OkulografiaUrzadzenie", tableUPDRSExtra, DatabaseProcedures.getEnumerationByte("Badanie", "OkulografiaUrzadzenie"));
        Dictionary<byte, string> dictionaryYesNo = new Dictionary<byte, string>();
        dictionaryYesNo.Add(0, "nie");
        dictionaryYesNo.Add(1, "tak");
        variantsWideo = addVariantDropDowns("Wideo", tableUPDRSExtra, dictionaryYesNo);

        variantsTremorometria = addVariantDropDowns("Tremorometria", tablePart2, dictionaryYesNo);
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
        variantsTandemPivot = addVariantDropDowns("TandemPivot", tablePart3, DatabaseProcedures.getEnumerationByte("Badanie", "TandemPivot"));
        variantsPartCList.Add(addVariantTextBoxes("WTT", tablePart3, true));

        if (Session["PatientNumber"] != null && Session["AppointmentId"] != null && Session["AppointmentName"] != null)
        {
            labelAppointment.Text = "Pacjent: " + Session["PatientNumber"] + "<br />Wizyta: " + Session["AppointmentName"];
            if (!IsPostBack)
            {
                loadVariantIds();
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
                    buttonSavePartB.Enabled = false;
                    buttonSavePartC.Enabled = false;
                }
                loadPartF();
            }
        }
        else
        {
            Response.Redirect("~/Main.aspx");
        }
    }

    private TableRow addVariantRow(String label, Table table)
    {
        TableRow row = new TableRow();
        table.Rows.Add(row);
        Label labelVariant = new Label();
        labelVariant.Text = label;
        labelVariant.Width = new Unit(150);
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

    protected void dropUPDRS_SelectedIndexChanged(object sender, EventArgs e)
    {
        for (int i = 0; i < VARIANTS; i++)
        {
            int sum = 0;
            foreach (Tuple<DropDownList[], string> dropVariants in UPDRSList1)
            {
                sum += int.Parse(dropVariants.Item1[i].SelectedValue);
            }
            UPDRSListCalculated1.Item1[i].Text = sum.ToString();
        }
    }

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
            int sum = 0;
            foreach (Tuple<DropDownList[], string> dropVariants in UPDRSList1)
            {
                sum += int.Parse(dropVariants.Item1[i].SelectedValue);
            }
            UPDRSListCalculated1.Item1[i].Text = sum.ToString();

            foreach (Tuple<DropDownList[], string> dropVariants in UPDRSList2)
            {
                sum += int.Parse(dropVariants.Item1[i].SelectedValue);
            }
            UPDRSListCalculated2.Item1[i].Text = sum.ToString();
        }
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
            cmd.Parameters.Add("@" + UPDRSList2[i].Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSList2[i].Item1[variant].SelectedValue);
        }
        for (int i = 0; i < UPDRSList1.Count; i++)
        {
            cmd.Parameters.Add("@" + UPDRSList1[i].Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSList1[i].Item1[variant].SelectedValue);
        }
        cmd.Parameters.Add("@" + UPDRSListCalculated1.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSListCalculated1.Item1[variant].Text);
        cmd.Parameters.Add("@" + UPDRSListCalculated2.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(UPDRSListCalculated2.Item1[variant].Text);
        SqlParameter HYscaleDecimal = new SqlParameter("@" + variantsHYscale.Item2, SqlDbType.Decimal);
        HYscaleDecimal.Precision = 2;
        HYscaleDecimal.Scale = 1;
        HYscaleDecimal.Value = DatabaseProcedures.getDecimalOrNull(variantsHYscale.Item1[variant].SelectedValue);
        cmd.Parameters.Add(HYscaleDecimal);
        cmd.Parameters.Add("@" + variantsSchwabEnglandScale.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(variantsSchwabEnglandScale.Item1[variant].SelectedValue);
        cmd.Parameters.Add("@" + variantsOkulografiaUrzadzenie.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(variantsOkulografiaUrzadzenie.Item1[variant].SelectedValue);
        cmd.Parameters.Add("@" + variantsWideo.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getBitOrNull(variantsWideo.Item1[variant].SelectedValue);
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
        cmd.Parameters.Add("@" + variantsTandemPivot.Item2, SqlDbType.TinyInt).Value = DatabaseProcedures.getByteOrNull(variantsTandemPivot.Item1[variant].Text);
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
                variantIds[getVariantId(DBS, BMT)] = (int)rdr["IdBadanie"];
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

    private int getVariantId(byte DBS, bool BMT)
    {/*
        if (DBS == 0)
        {
            if (BMT)
                return 3;
            else
                return 7;
        }
        else if (DBS == 1)
        {
            if (BMT)
                return 0;
            else
                return 4;
        }
        else if (DBS == 2)
        {
            if (BMT)
                return 1;
            else
                return 5;
        }
        else if (DBS == 3)
        {
            if (BMT)
                return 2;
            else
                return 6;
        }
        */

        if (BMT)
        {
            if (DBS == 3)
            {
                return 0;
            }
            else if (DBS == 1)
            {
                return 1;
            }
            else if (DBS == 2)
            {
                return 2;
            }
            else if (DBS == 0)
            {
                return 3;
            }
        }
        else
        {
            if (DBS == 3)
            {
                return 4;
            }
            else if (DBS == 1)
            {
                return 5;
            }
            else if (DBS == 2)
            {
                return 6;
            }
            else if (DBS == 0)
            {
                return 7;
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
            "OkulografiaUrzadzenie, " +
            "Wideo " +
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
                    UPDRSList2[i].Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValue(rdr[UPDRSList2[i].Item2]);
                }
                for (int i = 0; i < UPDRSList1.Count; i++)
                {
                    UPDRSList1[i].Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValue(rdr[UPDRSList1[i].Item2]);
                }
                UPDRSListCalculated1.Item1[variant].Text = DatabaseProcedures.getTextByteValue(rdr[UPDRSListCalculated1.Item2]);
                UPDRSListCalculated2.Item1[variant].Text = DatabaseProcedures.getTextByteValue(rdr[UPDRSListCalculated2.Item2]);
                variantsHYscale.Item1[variant].SelectedValue = DatabaseProcedures.getDropDecimalValue(rdr[variantsHYscale.Item2]);
                variantsSchwabEnglandScale.Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValue(rdr[variantsSchwabEnglandScale.Item2]);
                variantsOkulografiaUrzadzenie.Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValue(rdr[variantsOkulografiaUrzadzenie.Item2]);
                variantsWideo.Item1[variant].SelectedValue = DatabaseProcedures.getDropBitValue(rdr[variantsWideo.Item2]);
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
                variantsTandemPivot.Item1[variant].SelectedValue = DatabaseProcedures.getDropMultiValue(rdr[variantsTandemPivot.Item2]);
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
            variantIds[i] = saveVariantPartA(DBS, BMT, getVariantId(DBS, BMT));
            BMT = false;
            i++;
            variantIds[i] = saveVariantPartA(DBS, BMT, getVariantId(DBS, BMT));
        }
        if (variantIds.Length > 0)
        {
            ViewState["VariantIds"] = variantIds;
            buttonSavePartB.Enabled = true;
            buttonSavePartC.Enabled = true;
        }
    }
    protected void buttonSavePartB_Click(object sender, EventArgs e)
    {
        int[] variantIds = (int[])ViewState["VariantIds"];
        if (variantIds != null)
        {
            for (byte DBS = 0, i = 0; i < variantIds.Length; DBS++, i++)
            {
                bool BMT = true;
                saveVariantPartB(variantIds[i], getVariantId(DBS, BMT));
                BMT = false;
                i++;
                saveVariantPartB(variantIds[i], getVariantId(DBS, BMT));
            }
        }
    }
    protected void buttonSavePartC_Click(object sender, EventArgs e)
    {
        int[] variantIds = (int[])ViewState["VariantIds"];
        if (variantIds != null)
        {
            for (byte DBS = 0, i = 0; i < variantIds.Length; DBS++, i++)
            {
                bool BMT = true;
                saveVariantPartC(variantIds[i], getVariantId(DBS, BMT));
                BMT = false;
                i++;
                saveVariantPartC(variantIds[i], getVariantId(DBS, BMT));
            }
        }
    }
}