using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Web.Configuration;
using System.Web.Security;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void AppLogin_Authenticate(object sender, AuthenticateEventArgs e)
    {
        string user = AppLogin.UserName;
        string password = AppLogin.Password;

        if (user != string.Empty && password != string.Empty)
        {
            if (Validate_Login(user, password))
            {
                FormsAuthentication.RedirectFromLoginPage(user, false);
                //Response.Redirect(AppLogin.DestinationPageUrl);
            }
        }
    }

    private bool Validate_Login(String Username, String Password)
    {//test/pass
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();

        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[validate_password]";
        cmd.Parameters.Add("@login", SqlDbType.VarChar, 30).Value = Username;
        cmd.Parameters.Add("@pass", SqlDbType.VarChar, 25).Value = Password;
        cmd.Parameters.Add("@res", SqlDbType.Bit);
        cmd.Parameters["@res"].Direction = ParameterDirection.Output;
        cmd.Connection = con;

        bool success = false;
        try
        {
            con.Open();
            cmd.ExecuteNonQuery();
            success = (bool) cmd.Parameters["@res"].Value;
        }
        catch (SqlException ex)
        {
            lblMessage.Text = ex.Message;
        }
        finally
        {
            cmd.Dispose();
            if (con != null)
            {
                con.Close();
            }
        }

        return success;
    }
}