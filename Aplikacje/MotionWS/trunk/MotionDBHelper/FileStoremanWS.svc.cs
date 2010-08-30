using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Security.Permissions;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "FileStoremanWS" here, you must also update the reference to "FileStoremanWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")]
    public class FileStoremanWS : DatabaseAccessService, IFileStoremanWS
    {
        string baseLocalFilePath = @"C:\FTPShare\";
        int maxFileSize = 10000000;
        byte[] fileData = null;

        SqlDataReader fileReader = null;

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StoreMeasurementResultFile(int measurementID, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;

            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '"+filename+"'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementResultFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( Opis_pliku, Plik, Nazwa_pliku)
                                        values (@file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";

                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                FileStream fs = new FileStream(fileLocation, FileMode.Open, FileAccess.Read);
                BinaryReader br = new BinaryReader(fs);
                fileData = br.ReadBytes(maxFileSize);
                if (description.Equals("")) description = "-";
                cmd.Parameters["@file_desc"].Value = description;
                cmd.Parameters["@file_data"].Value = fileData;
                cmd.Parameters["@file_name"].Value = filename;
                cmd.ExecuteNonQuery();
                newFileId = (int)fileIdParameter.Value;
                br.Close();
                fs.Close();
                File.Delete(fileLocation);
                Directory.Delete(di.FullName);
                cmd.Parameters.Clear();
                cmd.Parameters.Add("@meas_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@meas_id"].Value = measurementID;
                cmd.Parameters["@file_id"].Value = newFileId;
                cmd.CommandText = @"insert into Wynik_pomiaru ( IdPomiar, IdPlik ) values (@meas_id, @file_id)";
                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Update failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementResultFile")));
            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }
/*
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StorePreviewFile(int sourceFileID, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;

            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StorePerformerFile")));

            }
            
            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
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
                Directory.Delete(di.FullName);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Update failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFile")));

            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }
 */
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StoreAttributeFile(int resourceID, string entity, string attributeName, string path, string description, string filename)
        {
            // UWAGA: nie dopuszczono mozliwosci wprowadzania atrybutow plikowych dla encji PLIK !
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;

            string operationName = "";
            string paramName = "";
            int resultCode = 0;

            int newFileId = 0;

            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( Opis_pliku, Plik, Nazwa_pliku)
                                        values (@file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";

                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                FileStream fs = new FileStream(fileLocation, FileMode.Open, FileAccess.Read);
                BinaryReader br = new BinaryReader(fs);
                fileData = br.ReadBytes(maxFileSize);
                if (description.Equals("")) description = "-";
                cmd.Parameters["@file_desc"].Value = description;
                cmd.Parameters["@file_data"].Value = fileData;
                cmd.Parameters["@file_name"].Value = filename;
                cmd.ExecuteNonQuery();
                newFileId = (int)fileIdParameter.Value;
                br.Close();
                fs.Close();
                File.Delete(fileLocation);
                Directory.Delete(di.FullName);
                cmd.Parameters.Clear();
                switch (entity)
                {
                    case "performer":
                        operationName = "set_performer_attribute";
                        paramName = "@perf_id";
                        break;
                    case "session":
                        operationName = "set_session_attribute";
                        paramName = "@sess_id";
                        break;
                    case "trial":
                        operationName = "set_trial_attribute";
                        paramName = "@trial_id";
                        break;
                    case "measurement":
                        operationName = "set_measurement_attribute";
                        paramName = "@meas_id";
                        break;
                    case "measurement_conf":
                        operationName = "set_measurement_conf_attribute";
                        paramName = "@mc_id";
                        break;
                }
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                cmd.Parameters.Add(paramName, SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters[paramName].Value = resourceID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = newFileId.ToString();
                cmd.Parameters["@update"].Value = 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Update failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFile")));


            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }
 
        public void DownloadComplete(int fileID, string path)
        {

            string fileLocation = "NOT_FOUND";
            path = path.Substring(0, path.LastIndexOf('/'));
            try
            {
                OpenConnection();
                cmd.CommandText = @"select Lokalizacja from Plik_udostepniony where IdPlik_udostepniony = @file_id and Lokalizacja = @file_path";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = fileID;
                cmd.Parameters.Add("@file_path", SqlDbType.VarChar, 80);
                cmd.Parameters["@file_path"].Value = path;

                fileReader = cmd.ExecuteReader();

                while (fileReader.Read())
                {
                    fileLocation = (string)fileReader.GetValue(0);
                }
                fileReader.Close();

                cmd.CommandText = @"delete from Plik_udostepniony 
                                        where IdPlik_udostepniony = @file_id and Lokalizacja = @file_path";
                cmd.ExecuteNonQuery();
                if (Directory.Exists(baseLocalFilePath + fileLocation))
                    Directory.Delete(baseLocalFilePath + fileLocation, true);
            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "File operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("DownloadComplete")));

            }
            finally
            {
                CloseConnection();
            }
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public FileData RetrieveFile(int fileID)
        {
            string relativePath = "";
            string fileName = "NOT_FOUND";
            string filePath = "";
            string fileLocation = "";
            FileData fData = new FileData();
            bool found = false;
            object pathObject;

            fileData = null;
            fileName = "";
            relativePath = DateTime.Now.Ticks.ToString(); 
            try
            {
                // TO DO: generowanie losowej nazwy katalogu
                // TO DO: jeśli plik jest juz wystawiony - zamiast pobierac z bazy - odzyskac lokalizacje i odswiezyc date

                OpenConnection();
                cmd.CommandText = @"select Plik, Nazwa_pliku, Sciezka from Plik where IdPlik = @file_id";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = fileID;
                fileReader = cmd.ExecuteReader();


                while (fileReader.Read())
                {
                    fileData = (byte[])fileReader.GetValue(0);
                    fileName = (string)fileReader.GetValue(1);
                    pathObject = fileReader.GetValue(2);
                    if (pathObject != DBNull.Value) filePath = (string)fileReader.GetValue(2);
                    found = true;
                }

                if (!found)
                {
                    FileAccessServiceException exc = new FileAccessServiceException("not found", "File does not exist or not permitted to retrieve");
                    throw new FaultException<FileAccessServiceException>(exc, "Cannot retrieve this file", FaultCode.CreateReceiverFaultCode(new FaultCode("RetrieveFile")));
                }

                if(!Directory.Exists(baseLocalFilePath + relativePath))
                    Directory.CreateDirectory(baseLocalFilePath + relativePath);

                fileName = fileName.Substring(fileName.LastIndexOf('\\') + 1);
                fileName = fileName.Substring(fileName.LastIndexOf('/') + 1);

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
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "File operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("RetrieveFile")));


            }
            finally
            {
                CloseConnection();
            }
            fData.FileLocation = relativePath+"/"+fileName;
            fData.SubdirPath = filePath;
            return fData;
        }


    }

}
