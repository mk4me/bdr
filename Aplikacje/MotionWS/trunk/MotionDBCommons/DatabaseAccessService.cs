using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace MotionDBCommons
{
    public class DatabaseAccessService
    {
        protected SqlConnection conn = null;
        public SqlCommand cmd = null;
        protected const bool debug = false;
        protected static string baseLocalFilePath = @"F:\FTPShare\"; // !!! change to F: in production!
        protected string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion";
        }

        public string ProduceRandomCode(int len)
        {
            Random r = new Random();
            StringBuilder b = new StringBuilder();

            for (int i = 0; i < len; i++) b.Append(Convert.ToChar(Convert.ToInt32(Math.Floor(26 * r.NextDouble() + 65))));
            return b.ToString();
        }
        public void OpenConnection()
        {
            conn = new SqlConnection(GetConnectionString());
            conn.Open();
            cmd = conn.CreateCommand();
        }

        public void CloseConnection()
        {
            conn.Close();
        }
        protected string WrapTryCatch(string query)
        {
            
            return debug?@"BEGIN TRY " + query + @" END TRY
                        BEGIN CATCH
                            insert into Blad ( NrBledu, Dotkliwosc, Stan, Procedura, Linia, Komunikat )
                            values ( ERROR_NUMBER() , ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE() )
                        END CATCH;":query;
        }

    }
}
