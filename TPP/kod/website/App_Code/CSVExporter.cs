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

    public static DataTable getTransformedCopy(List<Tuple<string, string>> selectedColumns, int timelineFilter, bool BMT_ON, bool BMT_OFF, bool DBS_ON, bool DBS_OFF)
    {
        DataTable dataTableColumns = new DataTable();
        DataColumn dataColumn1 = new DataColumn("Pozycja", typeof(System.Int32));
        dataTableColumns.Columns.Add(dataColumn1);
        DataColumn dataColumn2 = new DataColumn("KolumnaID", typeof(System.Int32));
        dataTableColumns.Columns.Add(dataColumn2);
        for (int i = 0; i < selectedColumns.Count(); i++)
        {
            int columnId = DatabaseProcedures.getColumnId(selectedColumns[i].Item1, selectedColumns[i].Item2);
            DataRow dataRow = dataTableColumns.NewRow();
            dataRow[0] = i + 1;
            dataRow[1] = columnId;
            dataTableColumns.Rows.Add(dataRow);
        }

        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_transformed_copy]";
        SqlParameter param = cmd.Parameters.AddWithValue("@column_filter", dataTableColumns);
        cmd.Parameters.Add("@timeline_filter", SqlDbType.Int).Value = timelineFilter;
        cmd.Parameters.Add("@b_on", SqlDbType.Bit).Value = BMT_ON;
        cmd.Parameters.Add("@b_off", SqlDbType.Bit).Value = BMT_OFF;
        cmd.Parameters.Add("@d_on", SqlDbType.Bit).Value = DBS_ON;
        cmd.Parameters.Add("@d_off", SqlDbType.Bit).Value = DBS_OFF;
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