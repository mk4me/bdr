using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI.WebControls;

/// <summary>
/// Summary description for Utils
/// </summary>
public class Utils
{
	public Utils()
	{
		//
		// TODO: Add constructor logic here
		//
	}

    public static void setSelectedCheckListItems(List<string> selectedValues, CheckBoxList checkList)
    {
        foreach (string value in selectedValues)
        {
            ListItem listItem = checkList.Items.FindByValue(value);
            if (listItem != null)
            {
                listItem.Selected = true;
            }
        }
    }

    public static List<string> getSelectedCheckListItems(CheckBoxList checkList)
    {
        List<string> selectedValues = new List<string>();
        foreach (ListItem listItem in checkList.Items)
        {
            if (listItem.Selected)
            {
                selectedValues.Add(listItem.Value);
            }
        }

        return selectedValues;
    }

    public static void colorRow(Table table, TableRow row)
    {
        table.CellPadding = 3;
        table.CellSpacing = 0;
        if (table.Rows.Count % 2 == 0)
        {
            row.BackColor = System.Drawing.Color.FromArgb(255, 255, 240, 178);
        }
    }
}