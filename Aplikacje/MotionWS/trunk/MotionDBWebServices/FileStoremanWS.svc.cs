using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Text.RegularExpressions;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Xml;
using System.Security.Permissions;
using MotionDBCommons;



namespace MotionDBWebServices
{
    // NOTE: If you change the class name "FileStoremanWS" here, you must also update the reference to "FileStoremanWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")]
    [ErrorLoggerBehaviorAttribute]
    public class FileStoremanWS : DatabaseAccessService, IFileStoremanWS
    {

        int maxFileSize = 40000000;
        byte[] fileData = null;

        static string localReadDirSuffix = "BDR/";
        static string localReadDir = baseLocalFilePath + localReadDirSuffix;
        static string localWriteDirSuffix = localReadDirSuffix + "w/";
        static string localWriteDir = localReadDir + localWriteDirSuffix;

        SqlDataReader fileReader = null;

        // !! TODO - docelowo zmienic na bazujace implicite na sciezce localWriteDir - ale po aktualizacji klienta BDR dopiero

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public int StoreMeasurementConfFile(int mcID, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;
            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFile")));

            }
            */

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
                //Directory.Delete(di.FullName);

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

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public int StoreSessionFile(int sessionId, string path, string description, string filename)
        {
            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;
            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFile")));

            }
            */
            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFile")));

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
                //Directory.Delete(di.FullName);

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

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public int StoreTrialFile(int trialID, string path, string description, string filename)
        {

            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;


            int newFileId = 0;
            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFile")));

            }
            */
            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdProba, Opis_pliku, Plik, Nazwa_pliku)
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
                //Directory.Delete(di.FullName);

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

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public void ReplaceFile(int fileID, string path, string filename)
        {

            string dirLocation = baseLocalFilePath + path;
            string fileLocation = dirLocation + @"\" + filename;
            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));

            }
            */
            if (filename.Normalize().Contains('\\') || filename.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + filename + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));

            }

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"update Plik 
                                        set Plik = @file_data, Nazwa_pliku = @file_name
                                        where IdPlik = @file_id";
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                FileStream fs = new FileStream(fileLocation, FileMode.Open, FileAccess.Read);
                BinaryReader br = new BinaryReader(fs);
                fileData = br.ReadBytes(maxFileSize);
                cmd.Parameters["@file_data"].Value = fileData;
                cmd.Parameters["@file_name"].Value = filename;
                cmd.Parameters["@file_id"].Value = fileID;
                cmd.ExecuteNonQuery();
                br.Close();
                fs.Close();
                File.Delete(fileLocation);
                //Directory.Delete(di.FullName);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("database", "Database operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed at the DBMS side", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));
            }
            catch (SystemException ex1)
            {
                FileAccessServiceException exc = new FileAccessServiceException("system", "File access failure: " + ex1.Message);
                throw new FaultException<FileAccessServiceException>(exc, "File access failure", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));
            }
            finally
            {
                CloseConnection();
            }
        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public void StoreMeasurementConfFiles(int mcID, string path, string description)
        {
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";


            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreMeasurementConfFiles")));

            }
            */
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public void StoreSessionFiles(int sessionID, string path, string description)
        {
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";

            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreSessionFiles")));

            }
            */
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public void StoreTrialFiles(int trialId, string path, string description)
        {
            string dirLocation = baseLocalFilePath;

            int dirLocLength;
            string subdirPath = "";
            string fileName = "";


            /* To be activated after the BDR client update
            if (! path.Normalize().Contains(localWriteDir))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file location", "The path is required to lead to the: '" + localWriteDir + "' subdirectory.");
                throw new FaultException<FileAccessServiceException>(exc, "Wrong file location", FaultCode.CreateReceiverFaultCode(new FaultCode("StoreTrialFiles")));

            }
            */
            if (path.StartsWith("\\") || path.StartsWith("/")) path = path.Substring(1);
            if (path.EndsWith("\\") || path.EndsWith("/")) path = path.Substring(0, path.Length - 1);
            dirLocation = baseLocalFilePath + path;
            dirLocLength = dirLocation.Length + 1; // plus additional "/" character

            try
            {
                DirectoryInfo di = new DirectoryInfo(dirLocation);
                OpenConnection();
                cmd.CommandText = @"insert into Plik ( IdProba, Opis_pliku, Plik, Nazwa_pliku, Sciezka)
                                        values (@trial_id, @file_desc, @file_data, @file_name, @file_path)";
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
                // SECURE ME !!!
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

                // SECURE ME !!!
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

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            if ((fileID == 0) && path.Contains(userName))
            {
                if (Directory.Exists(baseLocalFilePath + path))
                    Directory.Delete(baseLocalFilePath + path, true);
                return;
            }

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
            relativePath = localReadDirSuffix + DateTime.Now.Ticks.ToString();
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

                if (!Directory.Exists(baseLocalFilePath + relativePath))
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
            fData.FileLocation = relativePath + "/" + fileName;
            fData.SubdirPath = filePath;
            return fData;
        }


        public string GetShallowCopy()
        {
            string filePath = "";
            string fileName = "shallowCopy.xml";
            string fileLocation = "";


            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = localReadDirSuffix + filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_shallow_copy";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(baseLocalFilePath + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetShallowCopy")));
            }
            finally
            {
                CloseConnection();
            }

            return fileLocation;
        }


        public string GetShallowCopyIncrement(DateTime since)
        {
            string filePath = "";
            string fileName = "shallowCopyInc.xml";
            string fileLocation = "";


            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = localReadDirSuffix + filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_shallow_copy_increment";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                SqlParameter sincePar = cmd.Parameters.Add("@since", SqlDbType.DateTime);
                usernamePar.Direction = ParameterDirection.Input;
                sincePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                sincePar.Value = since;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(baseLocalFilePath + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetShallowCopyIncrement")));
            }
            finally
            {
                CloseConnection();
            }

            return fileLocation;
        }

        public string GetShallowCopyBranchesIncrement(DateTime since)
        {
            string filePath = "";
            string fileName = "shallowCopyBranchesInc.xml";
            string fileLocation = "";


            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = localReadDirSuffix + filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_shallow_copy_branches_increment";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                SqlParameter sincePar = cmd.Parameters.Add("@since", SqlDbType.DateTime);
                usernamePar.Direction = ParameterDirection.Input;
                sincePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                sincePar.Value = since;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(baseLocalFilePath + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetShallowCopyIncrement")));
            }
            finally
            {
                CloseConnection();
            }

            return fileLocation;
        }


        public string GetMetadata()
        {
            string filePath = "";
            string fileName = "metadata.xml";
            string fileLocation = "";


            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = localReadDirSuffix + filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_metadata";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(baseLocalFilePath + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetMetadata")));
            }
            finally
            {
                CloseConnection();
            }

            return fileLocation;
        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public string GetUserAssignments()
        {
            string filePath = "";
            string fileName = "userAssignments.xml";
            string fileLocation = "";


            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = localReadDirSuffix + filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_user_assignments";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(baseLocalFilePath + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetUserAssignments")));
            }
            finally
            {
                CloseConnection();
            }

            return fileLocation;
        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public int CreateSessionFromFiles(string path)
        {

            // int labID, string motionKindName, DateTime sessionDate, string sessionName, string tags, string sessionDescription, int[] sessionGroupIDs

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            int result = 0;
            int sessionId = 0;

            string fileName;
            string fullPath;
            string entity;
            int resId;
            int asfFileId = 0;

            string dirLocation = baseLocalFilePath;

            if (path.StartsWith("\\") || path.StartsWith("/")) path = path.Substring(1);
            if (path.EndsWith("\\") || path.EndsWith("/")) path = path.Substring(0, path.Length - 1);
            dirLocation = dirLocation + path + "\\";

            SqlConnection connF;
            SqlCommand cmdF;

            SqlConnection connA;
            SqlCommand cmdA;

            FileNameEntryCollection fileNames = new FileNameEntryCollection();
            // Wczytanie nazw plikow ze wskazanego katalogu. Utworzenie sesji i triali wedlug nazw tych plikow. Poprzedzone walidacja.
            connF = new SqlConnection(@"server = .; integrated security = true; database = Motion");
            connA = new SqlConnection(@"server = .; integrated security = true; database = Motion");
            try
            {
                OpenConnection();
                connF.Open();
                connA.Open();


                DirectoryInfo di = new DirectoryInfo(dirLocation);
                foreach (FileInfo fi in di.GetFiles("????-??-??*-S??*.??*", SearchOption.TopDirectoryOnly))
                {

                    if (Regex.IsMatch(fi.Name, @"(\d{4}-\d{2}-\d{2}-[AB]\d{4}-S\d{2}(-T\d{2})?(\.\d+)?\.(asf|amc|c3d|avi|zip|vsk|mp))|(\d{4}-\d{2}-\d{2}-S\d{2}(-T\d{2})?\.(png|xml))"))
                    {
                        FileNameEntry fne = new FileNameEntry();
                        fne.Name = fi.Name;
                        fileNames.Add(fne);
                    }
                }

                cmd = conn.CreateCommand();
                cmd.CommandText = "create_session_from_file_list";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@files", SqlDbType.Structured);
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);

                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@files"].Value = fileNames;
                SqlDataReader sdr = cmd.ExecuteReader();

                if (resultParameter.Value != null)
                {
                    result = (int)resultParameter.Value;
                    if (result != 0)
                    {
                        FileAccessServiceException exc = new FileAccessServiceException("validation", "File set validation error");
                        throw new FaultException<FileAccessServiceException>(exc, "Files validation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSessionFromFiles")));
                    }
                }
                cmdF = connF.CreateCommand();
                cmdA = connA.CreateCommand();

                cmdF.CommandText = @"insert into Plik ( IdSesja, IdProba, Opis_pliku, Plik, Nazwa_pliku)
                                        values (@sess_id, @trial_id, @file_desc, @file_data, @file_name)
                                        set @file_id = SCOPE_IDENTITY()";
                cmdF.CommandType = CommandType.Text;
                cmdF.Parameters.Add("@sess_id", SqlDbType.Int);
                cmdF.Parameters.Add("@trial_id", SqlDbType.Int);
                cmdF.Parameters.Add("@file_desc", SqlDbType.VarChar, 100);
                cmdF.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmdF.Parameters.Add("@file_name", SqlDbType.VarChar, 255);

                cmdF.Parameters["@file_desc"].Value = "";
                SqlParameter fileIdParameter =
                    new SqlParameter("@file_id", SqlDbType.Int);
                fileIdParameter.Direction = ParameterDirection.Output;
                cmdF.Parameters.Add(fileIdParameter);


                cmdA.CommandText = "set_trial_attribute";
                cmdA.CommandType = CommandType.StoredProcedure;
                cmdA.Parameters.Add("@trial_id", SqlDbType.Int);
                cmdA.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmdA.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmdA.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter = new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmdA.Parameters.Add(resultCodeParameter);

                cmdA.Parameters["@attr_name"].Value = "SkeletonFile";
                cmdA.Parameters["@update"].Value = 1;

                FileStream fs;
                BinaryReader br;

                while (sdr.Read())
                {
                    fileName = sdr[0].ToString();
                    resId = int.Parse(sdr[1].ToString());
                    entity = sdr[2].ToString();

                    if (entity.Equals("session"))
                    {
                        sessionId = resId;
                        cmdF.Parameters["@sess_id"].Value = sessionId;
                        cmdF.Parameters["@trial_id"].Value = DBNull.Value;
                    }
                    else
                    {
                        cmdF.Parameters["@sess_id"].Value = DBNull.Value;
                        cmdF.Parameters["@trial_id"].Value = resId;
                        if (fileName.EndsWith(".amc"))
                        {
                            cmdA.Parameters["@trial_id"].Value = resId;
                            cmdA.Parameters["@attr_value"].Value = asfFileId;
                            cmdA.ExecuteNonQuery();
                        }
                    }

                    fullPath = dirLocation + fileName;

                    fs = new FileStream(fullPath, FileMode.Open, FileAccess.Read);
                    br = new BinaryReader(fs);
                    fileData = br.ReadBytes(maxFileSize);

                    cmdF.Parameters["@file_data"].Value = fileData;
                    cmdF.Parameters["@file_name"].Value = fileName;
                    cmdF.ExecuteNonQuery();
                    if (fileName.EndsWith(".asf")) asfFileId = (int)fileIdParameter.Value;

                    br.Close();
                    fs.Close();
                    /* File.Delete(fullPath); */
                }

                Directory.Delete(di.FullName, true);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("Database access failure", "Database could not be updated: " + ex.Message);
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed: " + ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSessionFromFiles")));
            }
            catch (Exception ex1)
            {
                if (ex1 is FaultException) throw ex1;
                else
                {
                    FileAccessServiceException exc = new FileAccessServiceException("other", "Other exception: " + ex1.Message + ex1.StackTrace);
                    throw new FaultException<FileAccessServiceException>(exc, "Other exception", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSessionFromFiles")));
                }

            }

            finally
            {
                connA.Close();
                connF.Close();
                CloseConnection();
            }
            return sessionId;
        }


        // No FTP
        public FileByteData RetrieveFileByteData(int fileID)
        {
            string relativePath = "";
            string fileName = "NOT_FOUND";
            FileByteData fileByteData = new FileByteData();

            fileData = null;
            fileName = "";
            relativePath = localReadDirSuffix + DateTime.Now.Ticks.ToString();
            try
            {
                OpenConnection();
                cmd.CommandText = @"select Plik, Nazwa_pliku, Sciezka from Plik where IdPlik = @file_id";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = fileID;
                fileReader = cmd.ExecuteReader();


                while (fileReader.Read())
                {
                    fileData = (byte[])fileReader.GetValue(0);
                    fileName = (string)fileReader.GetValue(1);
                }

                fileName = fileName.Substring(fileName.LastIndexOf('\\') + 1);
                fileName = fileName.Substring(fileName.LastIndexOf('/') + 1);
                fileReader.Close();

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
            fileByteData.FileName = fileName;
            fileByteData.FileData = fileData;

            return fileByteData;
        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_operators")]
        public void StoreFileByteData(int fileID, FileByteData fileByteData, bool update)
        {
            if (fileByteData.FileName.Normalize().Contains('\\') || fileByteData.FileName.Normalize().Contains('/'))
            {
                FileAccessServiceException exc = new FileAccessServiceException("Wrong file name", "Subdirectory symbol detected in: '" + fileByteData.FileName + "'. Must be a simple file name.");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));
            }

            try
            {
                OpenConnection();
                if (update)
                {
                    cmd.CommandText = @"update Plik 
                                            set Plik = @file_data, Nazwa_pliku = @file_name
                                            where IdPlik = @file_id";
                }
                else
                {
                    cmd.CommandText = @"insert into Plik (IdPlik, Plik, Nazwa_pliku)
                                            values (@file_id, @file_data, @file_name)";
                }
                cmd.Parameters.Add("@file_data", SqlDbType.VarBinary, maxFileSize);
                cmd.Parameters.Add("@file_name", SqlDbType.VarChar, 255);
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_data"].Value = fileByteData.FileData;
                cmd.Parameters["@file_name"].Value = fileByteData.FileName;
                cmd.Parameters["@file_id"].Value = fileID;
                cmd.ExecuteNonQuery();

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("database", "Database operation failed");
                throw new FaultException<FileAccessServiceException>(exc, "File acccess invocation failed at the DBMS side", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));
            }
            catch (SystemException ex1)
            {
                FileAccessServiceException exc = new FileAccessServiceException("system", "File access failure: " + ex1.Message);
                throw new FaultException<FileAccessServiceException>(exc, "File access failure", FaultCode.CreateReceiverFaultCode(new FaultCode("ReplaceFile")));
            }
            finally
            {
                CloseConnection();
            }
        }
    }

}

