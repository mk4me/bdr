using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

namespace MotionDBWebServices
{
    public class DatabaseAccessService : System.Web.Services.WebService
    {
        protected SqlConnection conn = null;
        protected SqlCommand cmd = null;

        protected void OpenConnection()
        {
            // server = DBPAWELL
            conn = new SqlConnection(@"server = .; integrated security = true; database = Motion");
            conn.Open();
            cmd = conn.CreateCommand();
        }

        protected void CloseConnection()
        {
            conn.Close();
        }
    }
}
