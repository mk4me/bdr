using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;


namespace MotionDBCommons
{
    public class DatabaseAccessService : MotionDBUtils
    {
        protected SqlConnection conn = null;
        public SqlCommand cmd = null;
        protected const bool debug = false;
        protected static string baseLocalFilePath = @"F:\FTPShare\"; // !!! change to F: in production!
        protected virtual string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion";
        }


        public virtual void OpenConnection()
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
