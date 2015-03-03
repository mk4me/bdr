using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Data.SqlClient;
using System.Data;
using System.Globalization;

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
        return getShortOrNullWithNoData(value, "");
    }

    public static object getShortOrNullWithNoData(string value, string noData)
    {
        short s;
        if (value == noData || !short.TryParse(value, out s))
        {
            return DBNull.Value;
        }
        else
        {
            return s;
        }
    }

    public static object getIntOrNull(string value)
    {
        int i;
        if (value == "" || !int.TryParse(value, out i))
        {
            return DBNull.Value;
        }
        else
        {
            return i;
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

    /// <summary>
    /// Accepts a point or a comma for decimals.
    /// </summary>
    public static object getDecimalOrNullWithNoData(string value, string noData)
    {
        value = value.Replace(',', '.');
        decimal d;
        if (value == noData || !decimal.TryParse(value, NumberStyles.Any, CultureInfo.InvariantCulture, out d))
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

    public static string getDropShortValueWithNoData(object value, string noData)
    {
        return value == DBNull.Value ? noData : ((short)value).ToString();
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

    public static string getTextIntValue(object value)
    {
        return value == DBNull.Value ? "" : ((int)value).ToString();
    }

    public static void saveMultiChoice(List<string> selectedValues, string attributeName, int appointmentId, string actor)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[update_exam_int_attribute]";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = appointmentId;
        cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 50).Value = attributeName;

        DataTable dataTable = new DataTable();
        DataColumn dataColumn = new DataColumn();
        dataColumn = new DataColumn("Value", typeof(System.Int32));
        dataTable.Columns.Add(dataColumn);
        foreach (string value in selectedValues)
        {
            DataRow dataRow = dataTable.NewRow();
            dataRow[0] = value;
            dataTable.Rows.Add(dataRow);
        }
        SqlParameter param = cmd.Parameters.AddWithValue("@values", dataTable);
        cmd.Parameters.Add("@actor_login", SqlDbType.VarChar, 50).Value = actor;
        cmd.Parameters.Add("@result", SqlDbType.Int);
        cmd.Parameters["@result"].Direction = ParameterDirection.Output;
        cmd.Connection = con;

        int success = 0;
        try
        {
            con.Open();
            cmd.ExecuteNonQuery();
            success = (int)cmd.Parameters["@result"].Value;
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
    }

    public static List<string> getMultiChoice(string attributeName, int appointmentId)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select * from dbo.get_exam_multi_values('" + attributeName + "', " + appointmentId + ")";
        cmd.Connection = con;

        List<string> selectedValues = new List<string>();
        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                string value = rdr["Wartosc"].ToString();
                selectedValues.Add(value);

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

        return selectedValues;
    }

    public static decimal getDisorderDurationForExamination(int appointmentId)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select dbo.disorder_duration_for_examination(@IdWizyta)";
        cmd.Parameters.Add("@IdWizyta", SqlDbType.Int).Value = appointmentId;
        cmd.Connection = con;

        decimal disorderDuration = 0;
        try
        {
            con.Open();
            object result = cmd.ExecuteScalar();
            if (result != null)
            {
                if (result != DBNull.Value)
                {
                    disorderDuration = (decimal)result;
                }
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

        return disorderDuration;
    }
}