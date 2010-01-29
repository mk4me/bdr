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
    public class FileStoremanService : DatabaseAccessService
    {
        string baseLocalFilePath = @"C:\FTPShare\";
        int maxFileSize = 10000000;
        byte[] fileData = null;

        SqlDataReader fileReader = null;

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
            string fileLocation = baseLocalFilePath+path+@"\"+filename;


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
        public void DownloadComplete(int fileID)
        {
            string relativePath = "sample_path";
            string fileLocation = "NOT_FOUND";

            try
            {
                OpenConnection();
                cmd.CommandText = @"select Lokalizacja from Plik_udostepniony where IdPlik_udostepniony = @file_id";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = fileID;
                fileReader = cmd.ExecuteReader();

                while (fileReader.Read())
                {
                    fileLocation = (string)fileReader.GetValue(0);
                }
                fileReader.Close();

                cmd.CommandText = @"delete from Plik_udostepniony 
                                        where IdPlik_udostepniony = @file_id";
                cmd.ExecuteNonQuery();
                if(Directory.Exists(baseLocalFilePath + relativePath))
                    Directory.Delete(baseLocalFilePath + relativePath,true);
            }
            catch (SqlException ex)
            {
                // log the exception
            }
            finally
            {
                CloseConnection();
            }
        }

        [WebMethod]
        public string RetrieveFile(int fileID)
        {
            string relativePath = "sample_path";
            string fileName = "NOT_FOUND";
            string fileLocation = "";

            fileData = null;
            fileName = "";
            try
            {
                // TO DO: generowanie losowej nazwy katalogu
                // TO DO: jeśli plik jest juz wystawiony - zamiast pobierac z bazy - odzyskac lokalizacje i odswiezyc date

                OpenConnection();
                cmd.CommandText = @"select Plik, Nazwa_pliku from Plik where IdPlik = @file_id";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = fileID;
                fileReader = cmd.ExecuteReader();

                while (fileReader.Read())
                {
                    fileData = (byte[])fileReader.GetValue(0);
                    fileName = (string)fileReader.GetValue(1);
                }
                if(!Directory.Exists(baseLocalFilePath + relativePath))
                    Directory.CreateDirectory(baseLocalFilePath + relativePath);
                FileStream fs = File.Create(baseLocalFilePath + relativePath + @"\" + fileName);
                BinaryWriter sw = new BinaryWriter(fs);
                sw.Write(fileData);
                fileLocation = baseLocalFilePath + relativePath + @"\" + fileName;
                fileReader.Close();
                cmd.Parameters.Clear();
                cmd.CommandText = @"insert into Plik_udostepniony ( IdPlik_udostepniony, Data_udostepnienia, Lokalizacja )
                                        values ( @file_id, getdate(), @relative_path)";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters.Add("@relative_path", SqlDbType.VarChar, 80);

                // can be used for recoring of several files
                cmd.Parameters["@file_id"].Value = fileID;
                cmd.Parameters["@relative_path"].Value = relativePath;
                cmd.ExecuteNonQuery();
                sw.Close();
                fs.Close();

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
            return relativePath+"/"+fileName;
        }


    }
}
