using System;
using System.ServiceModel;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IdentityModel.Selectors;
using System.IdentityModel.Tokens;
using System.Data;
using System.Data.SqlClient;
using MotionDBCommons;


namespace MotionMedDBWebServices
{


    public class CustomAuthValidator : System.IdentityModel.Selectors.UserNamePasswordValidator
    {
        protected SqlConnection conn = null;
        protected SqlCommand cmd = null;

        protected string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion_Med";
        }

        protected void OpenConnection()
        {
            conn = new SqlConnection(GetConnectionString());
            conn.Open();
            cmd = conn.CreateCommand();
        }

        protected void CloseConnection()
        {
            conn.Close();
        }

        public override void Validate(string userName, string password)
        {
            /*
            if (userName == null || password == null)
            {
                throw new ArgumentNullException();
            }
            if (!(userName == "applet_user" && password == "aplet4Motion"))
            {
                throw new SecurityTokenException("Unknown Username or Password");
            } 
            */

            int result = 0;
            if (userName == null || password == null)
            {
                throw new ArgumentNullException();
            }
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "validate_password";
                cmd.Parameters.Add("@login", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@pass", SqlDbType.VarChar, 25);
                SqlParameter resultParameter =
                    new SqlParameter("@res", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);

                cmd.Parameters["@login"].Value = userName;
                cmd.Parameters["@pass"].Value = password;

                cmd.ExecuteNonQuery();
                result = (int)resultParameter.Value;
            }
            catch (SqlException ex)
            {
                throw new SecurityTokenException("Unable to perform the validation");
            }
            finally
            {
                CloseConnection();
            }
            if (result != 1)
            {
                throw new SecurityTokenException("Unknown Username or Password");
            }
        }

    }
}
