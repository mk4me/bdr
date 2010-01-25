using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Data;
using System.Data.SqlClient;
using System.IO;

namespace MotionDBWebServices
{
    /// <summary>
    /// Summary description for FileStoremanService
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class FileStoremanService : System.Web.Services.WebService
    {
        int maxFileSize = 10000000;
        byte[] fileData = null;
        SqlConnection conn = null;
        SqlCommand cmd = null;

        private void OpenConnection()
        {
            // server = DBPAWELL
            conn = new SqlConnection(@"server = DBPAWELL; integrated security = true; database = Motion");
            conn.Open();
            cmd = conn.CreateCommand();
        }

        private void CloseConnection()
        {
            conn.Close();
        }

        [WebMethod]
        public void StorePerformerFiles(int performerID, string path)
        {
            return;
        }

        [WebMethod]
        public void StoreSessionFiles(int sessionID, string path)
        {


            return;
        }

        [WebMethod]
        public int StoreSessionFile(int sessionId, string path, string description, string filename)
        {
            string fileLocation = @"C:\FTPShare\"+path+@"\"+filename;


            int newFileId = 0;

            
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdSesja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@sess_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id",SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                // can be used for recoring of several files
                cmd.Parameters["@sess_id"].Value = sessionId;
                FileStream fs = new FileStream(fileLocation, FileMode.Open, FileAccess.Read);
                BinaryReader br = new BinaryReader(fs);
                fileData = br.ReadBytes(maxFileSize);
                if (description.Equals("")) description = "sample description";
                cmd.Parameters["@file_desc"].Value = description;
                cmd.Parameters["@file_data"].Value = fileData;
                cmd.Parameters["@file_name"].Value = filename;
                cmd.ExecuteNonQuery();
                newFileId = (int)fileIdParameter.Value;
                br.Close();
                fs.Close();
                File.Delete(fileLocation);

            }
            catch (SqlException ex)
            {
                // log the exception
                return 0;

            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }

        [WebMethod]
        public void StoreTrialFiles(int trialId, string path)
        {
            return;
        }

        [WebMethod]
        public void DownloadComplete(string path)
        {
            return;
        }

        [WebMethod]
        public string RetrieveFile(int fileId)
        {
            SqlDataReader fileReader = null;
            string relativePath = "";
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdSesja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@sess_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);


            }
            catch (SqlException ex)
            {
                // log the exception
                return "error occurred";

            }
            finally
            {
                CloseConnection();
            }
            return relativePath;
        }


    }
}
