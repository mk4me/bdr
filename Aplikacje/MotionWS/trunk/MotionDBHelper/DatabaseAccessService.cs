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
        protected const bool debug = false;
        protected string baseLocalFilePath = @"C:\FTPShare\"; // !!! change to F: in production!

        protected void OpenConnection()
        {
            // server = DBPAWELL albo DB-BDR
            // zmieniać w recznie generowanych wsdl-ach
            conn = new SqlConnection(@"server = .; integrated security = true; database = Motion");
            conn.Open();
            cmd = conn.CreateCommand();
        }

        protected void CloseConnection()
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
