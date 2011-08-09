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

namespace MotionMedDBWebServices
{
    // NOTE: If you change the class name "FileStoremanWS" here, you must also update the reference to "FileStoremanWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")]
    [ErrorLoggerBehaviorAttribute]
    [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
    public class FileStoremanWS : DatabaseAccessService, IFileStoremanWS
    {

        int maxFileSize = 40000000;
        byte[] fileData = null;

        SqlDataReader fileReader = null;

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public void DownloadComplete(int photoID, string path)
        {

            string fileLocation = "NOT_FOUND";
            path = path.Substring(0, path.LastIndexOf('/'));

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            if ((photoID == 0) && path.Contains("dump"))
            {
                if (Directory.Exists(baseLocalFilePath + userName + @"\dump"))
                    Directory.Delete(baseLocalFilePath + userName + @"\dump", true);
                return;
            }

            try
            {
                OpenConnection();
                cmd.CommandText = @"select Lokalizacja from Zdjecie_udostepnione where IdPacjent = @file_id and Lokalizacja = @file_path";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters["@file_id"].Value = photoID;
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
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
                    fileName = photoID+".jpg";
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
                cmd.CommandText = @"insert into Zdjecie_udostepnione ( IdPacjent, Data_udostepnienia, Lokalizacja )
                                        values ( @file_id, getdate(), @relative_path)";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters.Add("@relative_path", SqlDbType.VarChar, 80);

                // can be used for recoring of several files
                cmd.Parameters["@file_id"].Value = photoID;
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public string GetShallowCopy()
        {
            string filePath = "";
            string fileName = "patientList.xml";
            string fileLocation = "";
            XmlDeclaration xmldecl;

            XmlDocument xd = new XmlDocument();
            XmlElement xe = xd.CreateElement("ShallowCopy", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/dump";

            if (!Directory.Exists(baseLocalFilePath + filePath))
                Directory.CreateDirectory(baseLocalFilePath + filePath);

            fileLocation = filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "m_get_patient_list";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                while (xd.DocumentElement.HasChildNodes)
                {
                    xe.AppendChild(xd.DocumentElement.ChildNodes[0]);
                }
                xd.RemoveChild(xd.DocumentElement);
                xd.AppendChild(xe);
                xmldecl = xd.CreateXmlDeclaration("1.0", null, null);
                xd.InsertBefore(xmldecl, xd.DocumentElement);
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionMedUsers")]
        public string GetMetadata()
        {
            string filePath = "";
            string fileName = "metadata.xml";
            string fileLocation = "";
            XmlDeclaration xmldecl;


            XmlDocument xd = new XmlDocument();
            XmlElement xe = xd.CreateElement("Metadata", "http://ruch.bytom.pjwstk.edu.pl/MotionDB");

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            filePath = userName + "/dump";

            if (!Directory.Exists(baseLocalFilePath + filePath))
                Directory.CreateDirectory(baseLocalFilePath + filePath);

            fileLocation = filePath + "/" + fileName;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_med_metadata";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();

                while (xd.DocumentElement.HasChildNodes)
                {
                    xe.AppendChild(xd.DocumentElement.ChildNodes[0]);
                }
                xd.RemoveChild(xd.DocumentElement);
                xd.AppendChild(xe);

                xmldecl = xd.CreateXmlDeclaration("1.0", null, null);
                xd.InsertBefore(xmldecl, xd.DocumentElement);


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



    }

}

