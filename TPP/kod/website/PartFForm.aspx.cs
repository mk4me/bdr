using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PartFForm : System.Web.UI.Page
{
    static int VARIANTS = 8;
    private List<DropDownList[]> UPDRSList1 = new List<DropDownList[]>();
    private List<DropDownList[]> UPDRSList2 = new List<DropDownList[]>();
    private TextBox[] UPDRSListCalculated1 = new TextBox[VARIANTS];
    private TextBox[] UPDRSListCalculated2 = new TextBox[VARIANTS];
    private DropDownList[] variantsHYscale = new DropDownList[VARIANTS];
    private DropDownList[] variantsSchwabEnglandScale = new DropDownList[VARIANTS];
    private DropDownList[] variantsOkulografiaUrzadzenie = new DropDownList[VARIANTS];
    private DropDownList[] variantsWideo = new DropDownList[VARIANTS];
    private DropDownList[] variantsTremorometria = new DropDownList[VARIANTS];
    private DropDownList[] variantsTestSchodkowy = new DropDownList[VARIANTS];
    private TextBox[] variantsTestSchodkowyCzas1 = new TextBox[VARIANTS];
    private TextBox[] variantsTestSchodkowyCzas2 = new TextBox[VARIANTS];
    private DropDownList[] variantsTestMarszu = new DropDownList[VARIANTS];
    private TextBox[] variantsTestMarszuCzas1 = new TextBox[VARIANTS];
    private TextBox[] variantsTestMarszuCzas2 = new TextBox[VARIANTS];
    private DropDownList[] variantsPosturografia = new DropDownList[VARIANTS];
    private DropDownList[] variantsMotionAnalysis = new DropDownList[VARIANTS];
    private TextBox[] variantsUpAndGo = new TextBox[VARIANTS];
    private TextBox[] variantsUpAndGoLiczby = new TextBox[VARIANTS];
    private TextBox[] variantsUpAndGoKubekPrawa = new TextBox[VARIANTS];
    private TextBox[] variantsUpAndGoKubekLewa = new TextBox[VARIANTS];
    private TextBox[] variantsTST = new TextBox[VARIANTS];
    private DropDownList[] variantsTandemPivot = new DropDownList[VARIANTS];
    private TextBox[] variantsWTT = new TextBox[VARIANTS];

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
        UPDRSList1.Add(addVariantDropDowns("UPDRS_21_RHand", tableUPDRS, DatabaseProcedures.getEnumerationByte("Badanie", "UPDRS_21_RHand")));
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
        Dictionary<bool, string> dictionaryYesNo = new Dictionary<bool, string>();
        dictionaryYesNo.Add(false, "nie");
        dictionaryYesNo.Add(true, "tak");
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

        variantsUpAndGo = addVariantTextBoxes("UpAndGo", tablePart3, true);
        variantsUpAndGoLiczby = addVariantTextBoxes("UpAndGoLiczby", tablePart3, true);
        variantsUpAndGoKubekPrawa = addVariantTextBoxes("UpAndGoKubekPrawa", tablePart3, true);
        variantsUpAndGoKubekLewa = addVariantTextBoxes("UpAndGoKubekLewa", tablePart3, true);
        variantsTST = addVariantTextBoxes("TST", tablePart3, true);
        variantsTandemPivot = addVariantDropDowns("TandemPivot", tablePart3, DatabaseProcedures.getEnumerationByte("Badanie", "TandemPivot"));
        variantsWTT = addVariantTextBoxes("WTT", tablePart3, true);
    }

    private TableRow addVariantRow(String label, Table table)
    {
        TableRow row = new TableRow();
        table.Rows.Add(row);
        Label labelVariant = new Label();
        labelVariant.Text = label;
        TableCell cell = new TableCell();
        row.Controls.Add(cell);
        cell.Controls.Add(labelVariant);

        return row;
    }

    private DropDownList[] addVariantDropDowns(String label, Table table, object dictionary)
    {
        TableRow row = addVariantRow(label, table);
        DropDownList[] columns = new DropDownList[VARIANTS];
        for (int i = 0; i < VARIANTS; i++)
        {
            TableCell cell = new TableCell();
            row.Controls.Add(cell);
            DropDownList drop = new DropDownList();
            drop.Width = new Unit(103);
            drop.DataSource = dictionary;
            drop.DataTextField = "Value";
            drop.DataValueField = "Key";
            drop.DataBind();
            //drop.AutoPostBack = true;
            //drop.SelectedIndexChanged += new System.EventHandler(dropUPDRS_SelectedIndexChanged);
            cell.Controls.Add(drop);
            columns[i] = drop;
        }

        return columns;
    }

    protected void dropUPDRS_SelectedIndexChanged(object sender, EventArgs e)
    {
        for (int i = 0; i < VARIANTS; i++)
        {
            int sum = 0;
            foreach (DropDownList[] dropVariants in UPDRSList1)
            {
                sum += int.Parse(dropVariants[i].SelectedValue);
            }
            UPDRSListCalculated1[i].Text = sum.ToString();
        }
    }

    private TextBox[] addVariantTextBoxes(String label, Table table, bool enabled)
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

        return columns;
    }

    protected void buttonCalculate_Click(object sender, EventArgs e)
    {
        for (int i = 0; i < VARIANTS; i++)
        {
            int sum = 0;
            foreach (DropDownList[] dropVariants in UPDRSList1)
            {
                sum += int.Parse(dropVariants[i].SelectedValue);
            }
            UPDRSListCalculated1[i].Text = sum.ToString();

            foreach (DropDownList[] dropVariants in UPDRSList2)
            {
                sum += int.Parse(dropVariants[i].SelectedValue);
            }
            UPDRSListCalculated2[i].Text = sum.ToString();
        }

    }
}