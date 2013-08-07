using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Data.SqlClient;
using System.Data;

/// <summary>
/// Summary description for DatabaseProcedures
/// </summary>
public class DatabaseProcedures
{
	public DatabaseProcedures()
	{
		//
		// TODO: Add constructor logic here
		//
	}

    public static Dictionary<string, string> getEnumerationString(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_enumeration_varchar]";
        cmd.Parameters.Add("@table_name", SqlDbType.VarChar, 30).Value = table;
        cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 50).Value = attribute;
        cmd.Connection = con;

        Dictionary<string, string> enumeration = new Dictionary<string, string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                string value = (string) rdr["Value"];
                string label = (string) rdr["Label"];

                enumeration.Add(value, label);
            }
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

        return enumeration;
    }

    public static Dictionary<byte, string> getEnumerationByte(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_enumeration_int]";
        cmd.Parameters.Add("@table_name", SqlDbType.VarChar, 30).Value = table;
        cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 50).Value = attribute;
        cmd.Connection = con;

        Dictionary<byte, string> enumeration = new Dictionary<byte, string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                byte value = (byte)rdr["Value"];
                string label = (string)rdr["Label"];

                enumeration.Add(value, label);
            }
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

        return enumeration;
    }

    public static Dictionary<decimal, string> getEnumerationDecimal(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["TPPServer"].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_enumeration_decimal]";
        cmd.Parameters.Add("@table_name", SqlDbType.VarChar, 30).Value = table;
        cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 50).Value = attribute;
        cmd.Connection = con;

        Dictionary<decimal, string> enumeration = new Dictionary<decimal, string>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();

            while (rdr.Read())
            {
                decimal value = (decimal)rdr["Value"];
                string label = (string)rdr["Label"];

                enumeration.Add(value, label);
            }
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

        return enumeration;
    }
}