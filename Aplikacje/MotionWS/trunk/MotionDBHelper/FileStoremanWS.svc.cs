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

        int maxFileSize = 10000000;
        byte[] fileData = null;

        SqlDataReader fileReader = null;

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int StoreMeasurementConfFile(int mcID, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;

            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdKonfiguracja_pomiarowa, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@mc_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@mc_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                // can be used for recoring of several files
                cmd.Parameters["@mc_id"].Value = mcID;
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
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFile")));

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
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(fileIdParameter);
                cmd.Prepare();
                // can be used for recoring of several files
                cmd.Parameters["@sess_id"].Value = sessionId;
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

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Update failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed: " + ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFile")));

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
        public void StoreMeasurementConfFiles(int mcID, string path, string description)
        {
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";


            if (path.StartsWith("\\") || path.StartsWith("/")) path = path.Substring(1);
            if (path.EndsWith("\\") || path.EndsWith("/")) path = path.Substring(0, path.Length - 1);
            dirLocation = baseLocalFilePath + path;
            dirLocLength = dirLocation.Length + 1; // plus additional "/" character

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdKonfiguracja_pomiarowa, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@mc_id, @file_desc, @file_data, @file_name)";
                cmd.Parameters.Add("@mc_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_path", SqlDbType.VarChar, 100);
                // can be used for recoring of several files
                cmd.Parameters["@mc_id"].Value = mcID;

                FileInfo[] sFiles = di.GetFiles("*.*", SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.FullName.Length - dirLocLength - fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File subdirectory path too long", "Relative file path: " + fi.FullName.Substring(dirLocLength) + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file path", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File name too long", "File name: " + fi.Name + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file name", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {
                    fileName = fi.Name;
                    subdirPath = fi.FullName.Substring(dirLocLength);
                    subdirPath = subdirPath.Substring(0, subdirPath.Length - fileName.Length - 1);
                    subdirPath = subdirPath.Replace("\\", "/");
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "-";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fi.Name;
                    cmd.Parameters["@file_path"].Value = subdirPath;
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName, true);

            }
            catch (SqlException ex)
            {
                // log the exception
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "File operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFiles")));


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
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";


            if (path.StartsWith("\\") || path.StartsWith("/")) path = path.Substring(1);
            if (path.EndsWith("\\") || path.EndsWith("/")) path = path.Substring(0, path.Length - 1);
            dirLocation = baseLocalFilePath + path;
            dirLocLength = dirLocation.Length + 1; // plus additional "/" character
            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdSesja, Opis_pliku, Plik, Nazwa_pliku, Sciezka)
                                        values (@sess_id, @file_desc, @file_data, @file_name, @file_path)";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_path", SqlDbType.VarChar, 100);
                // can be used for recoring of several files
                cmd.Parameters["@sess_id"].Value = sessionID;

                FileInfo[] sFiles = di.GetFiles("*.*", SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.FullName.Length - dirLocLength - fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File subdirectory path too long", "Relative file path: " + fi.FullName.Substring(dirLocLength) + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file path", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File name too long", "File name: " + fi.Name + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file name", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {
                    fileName = fi.Name;
                    subdirPath = fi.FullName.Substring(dirLocLength);
                    subdirPath = subdirPath.Substring(0, subdirPath.Length - fileName.Length - 1);
                    subdirPath = subdirPath.Replace("\\", "/");
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "sample description";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fileName;
                    cmd.Parameters["@file_path"].Value = subdirPath;
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName, true);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("Database access failure", "Database could not be updated");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed: " + ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));


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
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";

            if (path.StartsWith("\\") || path.StartsWith("/")) path = path.Substring(1);
            if (path.EndsWith("\\") || path.EndsWith("/")) path = path.Substring(0, path.Length - 1);
            dirLocation = baseLocalFilePath + path;
            dirLocLength = dirLocation.Length + 1; // plus additional "/" character

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdObserwacja, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@trial_id, @file_desc, @file_data, @file_name)";
                cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                cmd.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@file_path", SqlDbType.VarChar, 100);
                // can be used for recoring of several files
                cmd.Parameters["@trial_id"].Value = trialId;

                FileInfo[] sFiles = di.GetFiles("*.*", SearchOption.AllDirectories);
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.FullName.Length - dirLocLength - fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File subdirectory path too long", "Relative file path: " + fi.FullName.Substring(dirLocLength) + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file path", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {

                    if ((fi.Name.Length) > 100)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("File name too long", "File name: " + fi.Name + " exceeds the maximum length of 100 characters");
                        throw new FaultException<FileAccessServiceException>(exc, "Invalid file name", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFiles")));
                    }
                }
                foreach (FileInfo fi in sFiles)
                {
                    fileName = fi.Name;
                    subdirPath = fi.FullName.Substring(dirLocLength);
                    subdirPath = subdirPath.Substring(0, subdirPath.Length - fileName.Length - 1);
                    subdirPath = subdirPath.Replace("\\", "/");
                    if ((fi.Attributes & FileAttributes.Directory) == FileAttributes.Directory) continue;
                    FileStream fs = new FileStream(fi.FullName, FileMode.Open, FileAccess.Read);
                    BinaryReader br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);
                    if (description.Equals("")) description = "sample description";
                    cmd.Parameters["@file_desc"].Value = description;
                    cmd.Parameters["@file_data"].Value = fileData;
                    cmd.Parameters["@file_name"].Value = fi.Name;
                    cmd.Parameters["@file_path"].Value = subdirPath;
                    cmd.ExecuteNonQuery();
                    br.Close();
                    fs.Close();
                    File.Delete(fi.FullName);
                }


                Directory.Delete(di.FullName, true);

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

        /*
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
