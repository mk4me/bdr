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
        public int StorePerformerFile(int performerID, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;

            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '"+filename+"'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StorePerformerFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdPerformer, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@perf_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                // can be used for recoring of several files
                cmd.Parameters["@perf_id"].Value = performerID;
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
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StorePerformerFile")));

            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StoreSessionFile(int sessionId, string path, string description, string filename)
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
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StoreTrialFile(int trialID, string path, string description, string filename)
        {

            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


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
                cmd.CommandText = @"insert into Plik ( IdObserwacja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@trial_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                // can be used for recoring of several files
                cmd.Parameters["@trial_id"].Value = trialID;
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
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFile")));


            }
            finally
            {
                CloseConnection();
            }
            return newFileId;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void StorePerformerFiles(int performerID, string path, string description)

        {
            string dirLocation = baseLocalFilePath + path;
            int dirLocLength = dirLocation.Length + 1;            

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdPerformer, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@perf_id, @file_desc, @file_data, @file_name)";
                cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                // can be used for recoring of several files
                cmd.Parameters["@perf_id"].Value = performerID;

                FileInfo[] sFiles = di.GetFiles("*.*", SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {
                    if ((fi.FullName.Length - dirLocLength) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File path too long", "Relative file path: " + fi.FullName.Substring(dirLocLength) + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "File storage request failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StorePerformerFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "sample description";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fi.FullName.Substring(dirLocLength);
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName);

            }
            catch (SqlException ex)
            {
                // log the exception
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "File operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StorePerformerFiles")));


            }
            finally
            {
                CloseConnection();
            }
            return;
        }
       [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void StoreSessionFiles(int sessionID, string path, string description)
        {
            string dirLocation = baseLocalFilePath + path;
            int dirLocLength = dirLocation.Length + 1;


            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdSesja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@sess_id, @file_desc, @file_data, @file_name)";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                // can be used for recoring of several files
                cmd.Parameters["@sess_id"].Value = sessionID;

                FileInfo[] sFiles = di.GetFiles("*.*",SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {
                    if ((fi.FullName.Length - dirLocLength) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File path too long", "Relative file path: "+fi.FullName.Substring(dirLocLength)+" exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "File storage request failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)

                {
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "sample description";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fi.FullName.Substring(dirLocLength);
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName,true);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("Database access failure", "Database could not be updated");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));


            }
            finally
            {
                CloseConnection();
            }
            return;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public void StoreTrialFiles(int trialId, string path, string description)
        {
            string dirLocation = baseLocalFilePath + path;
            int dirLocLength = dirLocation.Length + 1;


            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdObserwacja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@trial_id, @file_desc, @file_data, @file_name)";
                cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                // can be used for recoring of several files
                cmd.Parameters["@trial_id"].Value = trialId;

                FileInfo[] sFiles = di.GetFiles("*.*", SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {
                    if ((fi.FullName.Length - dirLocLength) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File path too long", "Relative file path: " + fi.FullName.Substring(dirLocLength) + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "File storage request failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "sample description";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fi.FullName.Substring(dirLocLength);
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName);

            }
            catch (SqlException ex)
            {
                // log the exception
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "File operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFiles")));


            }
            finally
            {
                CloseConnection();
            }
            return;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
        public string RetrieveFile(int fileID)
        {
            string relativePath = "";
            string fileName = "NOT_FOUND";
            string fileLocation = "";

            fileData = null;
            fileName = "";
            relativePath = DateTime.Now.Ticks.ToString(); 
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
            return relativePath+"/"+fileName;
        }


    }

}
