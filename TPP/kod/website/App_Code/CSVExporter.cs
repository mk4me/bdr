using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;

/// <summary>
/// Summary description for CSVExporter
/// </summary>
public class CSVExporter
{
	public CSVExporter()
	{
		//
		// TODO: Add constructor logic here
		//
	}

    public static void writeDelimitedData(DataTable dataTable, string fileName, string delimiter)
    {
        HttpContext.Current.Response.Clear();
        HttpContext.Current.Response.ContentType = "text/csv";
        HttpContext.Current.Response.AppendHeader("Content-Disposition",
            string.Format("attachment; filename={0}", fileName));

        for (int i = 0; i < dataTable.Columns.Count; i++)
        {
            HttpContext.Current.Response.Write(dataTable.Columns[i].ColumnName);
            HttpContext.Current.Response.Write((i < dataTable.Columns.Count - 1) ? delimiter : Environment.NewLine);
        }

        foreach (DataRow row in dataTable.Rows)
        {
            for (int i = 0; i < dataTable.Columns.Count; i++)
            {
                HttpContext.Current.Response.Write(row[i].ToString());
                HttpContext.Current.Response.Write((i < dataTable.Columns.Count - 1) ? delimiter : Environment.NewLine);
            }
        }

        HttpContext.Current.Response.End();
    }

    public static DataTable getTestDataTable()
    {
        String table = "Wizyta";
        String attribute = "PierwszyObjaw";
        Dictionary<byte, string> dictionary = DatabaseProcedures.getEnumerationByte(table, attribute);
        DataTable dataTable = new DataTable();
        dataTable.Columns.Add("Klucz", typeof(byte));
        dataTable.Columns.Add("Definicja", typeof(string));
        DataRow row = dataTable.NewRow();

        foreach (KeyValuePair<byte, string> entry in dictionary)
        {
            dataTable.Rows.Add(entry.Key, entry.Value);
        }

        return dataTable;
    }
}