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
    public static string SERVER = "TPPServer";

	public DatabaseProcedures()
	{
		//
		// TODO: Add constructor logic here
		//
	}

    public static Dictionary<string, string> getEnumerationString(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
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
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
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

    public static Dictionary<byte, string> getEnumerationByteWithNoData(string table, string attribute, byte noData)
    {
        Dictionary<byte, string> dictionary = getEnumerationByte(table, attribute);
        dictionary.Add(noData, "");
        

        return dictionary;
    }

    public static Dictionary<decimal, string> getEnumerationDecimal(string table, string attribute)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
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

    public static Dictionary<decimal, string> getEnumerationDecimalWithNoData(string table, string attribute, decimal noData)
    {
        Dictionary<decimal, string> dictionary = getEnumerationDecimal(table, attribute);
        dictionary.Add(noData, "");


        return dictionary;
    }

    public static object getByteOrNull(string value)
    {
        return getByteOrNullWithNoData(value, "");
    }

    public static object getByteOrNullWithNoData(string value, string noData)
    {
        byte b;
        if (value == noData || !byte.TryParse(value, out b))
        {
            return DBNull.Value;
        }
        else
        {
            return b;
        }
    }

    public static object getShortOrNull(string value)
    {
        short s;
        if (value == "" || !short.TryParse(value, out s))
        {
            return DBNull.Value;
        }
        else
        {
            return s;
        }
    }

    public static object getBitOrNull(string value)
    {
        return (value == "2") ? DBNull.Value : (object)Convert.ToBoolean(int.Parse(value));
    }

    public static object getStringOrNull(string value)
    {
        return (value == "") ? DBNull.Value : (object)value;
    }

    public static object getDecimalOrNull(string value)
    {
        return getDecimalOrNullWithNoData(value, "");
    }

    public static object getDecimalOrNullWithNoData(string value, string noData)
    {
        decimal d;
        if (value == noData || !decimal.TryParse(value, out d))
        {
            return DBNull.Value;
        }
        else
        {
            return d;
        }
    }

    public static string getDropYesNoValue(object value)
    {
        return value == DBNull.Value ? "2" : ((byte)value).ToString();
    }

    public static string getDropBitValue(object value)
    {
        if (value == DBNull.Value)
        {
            return "2";
        }
        else if ((bool)value == false)
        {
            return "0";
        }
        else if ((bool)value == true)
        {
            return "1";
        }
        else
        {
            return "2";
        }
    }

    public static string getDropMultiValue(object value)
    {
        return value == DBNull.Value ? "1" : ((byte)value).ToString();
    }

    public static string getDropMultiValueWithNoData(object value, string noData)
    {
        return value == DBNull.Value ? noData : ((byte)value).ToString();
    }

    public static string getDropDecimalValue(object value)
    {
        return value == DBNull.Value ? "2.0" : ((decimal)value).ToString();
    }

    public static string getDropDecimalValueWithNoData(object value, string noData)
    {
        return value == DBNull.Value ? noData : ((decimal)value).ToString();
    }

    public static string getTextDecimalValue(object value)
    {
        return value == DBNull.Value ? "" : ((decimal)value).ToString();
    }

    public static string getTextStringValue(object value)
    {
        return value == DBNull.Value ? "" : value.ToString();
    }

    public static string getTextByteValue(object value)
    {
        return value == DBNull.Value ? "" : ((byte)value).ToString();
    }

    public static string getTextShortValue(object value)
    {
        return value == DBNull.Value ? "" : ((short)value).ToString();
    }
}