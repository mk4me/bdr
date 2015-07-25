using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Export : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            checkListRodzajWizyty.DataSource = DatabaseProcedures.getEnumerationByte("Wizyta", "RodzajWizyty");
            checkListRodzajWizyty.DataTextField = "Value";
            checkListRodzajWizyty.DataValueField = "Key";
            checkListRodzajWizyty.DataBind();

            listBoxWszystkie.DataSource = DatabaseProcedures.getExportColumns();
            listBoxWszystkie.DataTextField = "Value";
            listBoxWszystkie.DataValueField = "Key";
            listBoxWszystkie.DataBind();
        }
    }

    private void moveItem(ListBox listBoxFrom, ListBox listBoxTo)
    {
        List<ListItem> listRemoveItems = new List<ListItem>();
        int itemCount = listBoxFrom.Items.Count;
        int lastSelection = 0;
        for (int i = 0; i < itemCount; i++)
        {
            if (listBoxFrom.Items[i].Selected)
            {
                listRemoveItems.Add(listBoxFrom.Items[i]);
                lastSelection = i;
            }
        }
        if (lastSelection < itemCount - 1)
        {
            listBoxFrom.SelectedIndex = lastSelection + 1;
        }
        else
        {
            listBoxFrom.SelectedIndex = lastSelection - 1;
        }
        foreach (ListItem listItem in listRemoveItems)
        {
            listBoxFrom.Items.Remove(listItem);
            listBoxTo.Items.Add(listItem);
        }
    }

    private void sortItems(ListBox listBox)
    {
        SortedDictionary<int, string> sorted = new SortedDictionary<int, string>();
        foreach (ListItem listItem in listBox.Items)
        {
            sorted.Add(Convert.ToInt32(listItem.Value), listItem.Text);
        }
        listBox.Items.Clear();
        foreach (int key in sorted.Keys)
        {
            listBox.Items.Add(new ListItem(sorted[key], key.ToString()));
        }
    }

    public void switchItem(int direction)
    {
        if (listBoxWybrane.SelectedItem == null ||
            listBoxWybrane.SelectedIndex < 0 ||
            listBoxWybrane.GetSelectedIndices().Count() != 1)

            return;

        int newIndex = listBoxWybrane.SelectedIndex + direction;
        if (newIndex < 0 || newIndex >= listBoxWybrane.Items.Count)

            return;

        ListItem selected = listBoxWybrane.SelectedItem;
        listBoxWybrane.Items.Remove(selected);
        listBoxWybrane.Items.Insert(newIndex, selected);
        listBoxWybrane.SelectedIndex = newIndex;
    }

    protected void buttonToRight_Click(object sender, EventArgs e)
    {
        if (listBoxWszystkie.SelectedIndex >= 0)
        {
            moveItem(listBoxWszystkie, listBoxWybrane);
        }
    }

    protected void buttonToLeft_Click(object sender, EventArgs e)
    {
        if (listBoxWybrane.SelectedIndex >= 0)
        {
            moveItem(listBoxWybrane, listBoxWszystkie);
            sortItems(listBoxWszystkie);
        }
    }

    protected void buttonUp_Click(object sender, EventArgs e)
    {
        switchItem(-1);
    }

    protected void buttonDown_Click(object sender, EventArgs e)
    {
        switchItem(1);
    }

    protected void buttonExportCSV_Click(object sender, EventArgs e)
    {
        List<Tuple<string, string>> selectedColumns = new List<Tuple<string, string>>();
        foreach (ListItem listItem in listBoxWybrane.Items)
        {
            string[] tableColumn = listItem.Text.Split(':');
            if (tableColumn.Count() == 2)
            {
                selectedColumns.Add(new Tuple<string, string>(tableColumn[0], tableColumn[1]));
            }
        }

        int timelineFilter = 0;
        for (int i = 0; i < checkListRodzajWizyty.Items.Count; i++)
        {
            if (checkListRodzajWizyty.Items[i].Selected)
            {
                timelineFilter += (int)Math.Pow(2, i);
            }
        }

        DataTable dataTable = CSVExporter.getTransformedCopy(selectedColumns, timelineFilter,
            checkListBMTDBS.Items[0].Selected,
            checkListBMTDBS.Items[1].Selected,
            checkListBMTDBS.Items[2].Selected,
            checkListBMTDBS.Items[3].Selected);
        CSVExporter.writeDelimitedData(dataTable, "BD_" + DateTime.Now.ToString("yyyy-MM-dd") + ".csv", ";");
    }

    protected void buttonReset_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Export.aspx");
    }

    protected void buttonBack_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Default.aspx");
    }
}