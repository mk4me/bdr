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

namespace MotionMedDBWebServices
{
    // NOTE: If you change the class name "FileStoremanWS" here, you must also update the reference to "FileStoremanWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/FileStoremanService")]
    [ErrorLoggerBehaviorAttribute]

    public class FileStoremanWS : MotionDBCommons.DatabaseAccessService, IFileStoremanWS
    {

        int maxFileSize = 40000000;
        byte[] fileData = null;


        static string localReadDirSuffix = "MED/";
        static string localReadDir = baseLocalFilePath + localReadDirSuffix;
        static string localWriteDirSuffix = localReadDirSuffix + "w/";
        static string localWriteDir = localReadDir + localWriteDirSuffix;

        SqlDataReader fileReader = null;


        protected string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion_Med";
        }


        protected void OpenConnection()
        {
            conn = new SqlConnection(@"server = .; integrated security = true; database = Motion_Med");
            conn.Open();
            cmd = conn.CreateCommand();
        }

        // [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public void DownloadComplete(int resourceID, string resourceType, string path)
        {

            string fileLocation = "NOT_FOUND";
            path = path.Substring(0, path.LastIndexOf('/'));

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);


            if ((resourceID == 0) && path.Contains(userName)) // Tymczasowe; zastapic docelowym sposobem rejestrowania w Zasob udostepniony
            {
                if (Directory.Exists(baseLocalFilePath + path))
                    Directory.Delete(baseLocalFilePath + path, true);
                return;
            }

            try
            {
                OpenConnection();
                cmd.CommandText = @"select Lokalizacja from Zdjecie_udostepnione where IdPacjent = @file_id and Lokalizacja = @file_path";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = resourceID;
                cmd.Parameters.Add("@file_path", SqlDbType.VarChar, 80);
                cmd.Parameters["@file_path"].Value = path;

                fileReader = cmd.ExecuteReader();

                while (fileReader.Read())
                {
                    fileLocation = (string)fileReader.GetValue(0);
                }
                fileReader.Close();

                cmd.CommandText = @"delete from Zdjecie_udostepnione 
                                        where IdPacjent = @file_id and Lokalizacja = @file_path";
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

        //[PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public FileData RetrievePhoto(int photoID)
        {
            string relativePath = "";
            string fileName = "NOT_FOUND";
            string filePath = "";
            string fileLocation = "";
            FileData fData = new FileData();
            bool found = false;

            fileData = null;
            fileName = "";
            relativePath = DateTime.Now.Ticks.ToString();
            try
            {
                // TO DO: generowanie losowej nazwy katalogu
                // TO DO: jeśli plik jest juz wystawiony - zamiast pobierac z bazy - odzyskac lokalizacje i odswiezyc date

                OpenConnection();
                cmd.CommandText = @"select Zdjecie from Pacjent where IdPacjent = @file_id";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = photoID;
                fileReader = cmd.ExecuteReader();


                while (fileReader.Read())
                {
                    fileData = (byte[])fileReader.GetValue(0);
                    fileName = photoID+".png";
                    found = true;
                }

                if (!found)
                {
                    FileAccessServiceException exc = new FileAccessServiceException("not found", "File does not exist or not permitted to retrieve");
                    throw new FaultException<FileAccessServiceException>(exc, "Cannot retrieve this file", FaultCode.CreateReceiverFaultCode(new FaultCode("RetrieveFile")));
                }

                if (!Directory.Exists(localReadDir + relativePath))
                    Directory.CreateDirectory(localReadDir + relativePath);

                fileName = fileName.Substring(fileName.LastIndexOf('\\') + 1);
                fileName = fileName.Substring(fileName.LastIndexOf('/') + 1);

                FileStream fs = File.Create(localReadDir + relativePath + @"\" + fileName);
                BinaryWriter sw = new BinaryWriter(fs);
                sw.Write(fileData);
                fileLocation = relativePath + @"\" + fileName;
                fileReader.Close();
                cmd.Parameters.Clear();
                cmd.CommandText = @"insert into Zdjecie_udostepnione ( IdPacjent, Data_udostepnienia, Lokalizacja )
                                        values ( @file_id, getdate(), @relative_path)";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters.Add("@relative_path", SqlDbType.VarChar, 80);

                cmd.Parameters["@file_id"].Value = photoID;
                cmd.Parameters["@relative_path"].Value = localReadDirSuffix + relativePath;
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
            fData.FileLocation = localReadDirSuffix + relativePath + "/" + fileName;
            fData.SubdirPath = filePath;
            return fData;
        }

        //[PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public string GetShallowCopy()
        {
            string filePath = "";
            string fileName = "medicalRecords.xml";
            string fileLocation = "";
            Random r = new Random();
            StringBuilder b = new StringBuilder();

            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument();
            string subdirName = "/dump";
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            for(int i=0; i<20; i++) b.Append( Convert.ToChar( Convert.ToInt32 ( Math.Floor(26 * r.NextDouble()+65))));

            subdirName ="/"+b.ToString();

            filePath = userName + subdirName;

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "m_get_patient_list";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;

                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB");
                xd1.LoadXml(xd.OuterXml);

                xd.Save(localReadDir + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("unknown", "Shallow copy dump failed: "+ex.Message);
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetShallowCopy")));
            }
            finally
            {
                CloseConnection();
            }

            return localReadDirSuffix + fileLocation;
        }

        //[PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public string GetMetadata()
        {
            string filePath = "";
            string fileName = "medMetadata.xml";
            string fileLocation = "";



            XmlDocument xd = new XmlDocument();
            XmlDocument xd1 = new XmlDocument(); // --
            // XmlElement xe = xd.CreateElement("Metadata", "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB");

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/" + DateTime.Now.Ticks.ToString();

            if (!Directory.Exists(localReadDir + filePath))
                Directory.CreateDirectory(localReadDir + filePath);

            fileLocation = filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_med_metadata";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();
                /*
                while (xd.DocumentElement.HasChildNodes)
                {
                    xe.AppendChild(xd.DocumentElement.ChildNodes[0]);
                }
                xd.RemoveChild(xd.DocumentElement);
                xd.AppendChild(xe);

                xmldecl = xd.CreateXmlDeclaration("1.0", null, null);
                xd.InsertBefore(xmldecl, xd.DocumentElement);
                */
                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB");
                xd1.LoadXml(xd.OuterXml);

                xd1.Save(localReadDir + fileLocation);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("DB-side", "Shallow copy dump failed");
                throw new FaultException<FileAccessServiceException>(exc, "Shallow copy dump failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetMetadata")));
            }
            finally
            {
                CloseConnection();
            }

            return localReadDirSuffix + fileLocation;
        }



    }

}

