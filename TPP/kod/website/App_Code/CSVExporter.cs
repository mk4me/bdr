using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Web.Configuration;

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
        HttpContext.Current.Response.Charset = "utf-8";
        HttpContext.Current.Response.ContentEncoding = System.Text.Encoding.GetEncoding("windows-1250");
        HttpContext.Current.Response.AppendHeader("Content-Disposition", string.Format("attachment; filename={0}", fileName));

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

    public static DataTable getDatabaseCopy()
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_database_copy]";
        cmd.Connection = con;

        DataTable dataTable = new DataTable();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            dataTable.Load(rdr);
        }
        catch (SqlException ex)
        {
        }
        finally
        {
            cmd.Dispose();
            if (con != null)
            {
                con.Close();
            }
        }

        return dataTable;
    }
}