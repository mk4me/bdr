using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using System.Text.RegularExpressions;
using System.IO;

namespace LocalDBUtility
{
    public class UpdateException : Exception
    {
        string _fault_source;
        string _details;


        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }

        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public UpdateException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    public class FileAccessServiceException : Exception
    {
        string _fault_source;
        string _details;

        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }

        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public FileAccessServiceException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }


    public class DatabaseAccess
   
    {
        protected SqlConnection conn = null;
        public SqlCommand cmd = null;
        protected const bool debug = false;
        protected static string baseLocalFilePath = @"F:\FTPShare\"; // !!! change to F: in production!

        System.Globalization.NumberFormatInfo nfi = new System.Globalization.CultureInfo("en-US", false).NumberFormat;

        int maxFileSize = 40000000;
        byte[] fileData = null;

        SqlDataReader fileReader = null;

        protected int ExtractInt(string s)
        {
            return (int)Math.Round(decimal.Parse(s, nfi), MidpointRounding.AwayFromZero);
        }


        public static string GetConnectionString()
        {
            return @"server = 172.16.1.43; integrated security = true; database = Motion";
            // return @"server = .; integrated security = true; database = Motion";
            //"Data Source=192.168.0.5\SQL2008R2;Initial Catalog=MWDB;Persist Security Info=True;User ID=user;Password=password"
        }


        public virtual void OpenConnection()
        {
            conn = new SqlConnection(GetConnectionString());
            conn.Open();
            cmd = conn.CreateCommand();
            cmd.CommandTimeout = 120;
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

        public void SetTrialAttribute(int trialID, string attributeName, string attributeValue, bool update)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_trial_attribute";
                cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@trial_id"].Value = trialID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw exc;
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                string resName = "trial";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to " + resName);
                        throw exc;
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw exc;
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + trialID + "not found");
                        throw exc;
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw exc;
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw exc;

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw exc;

                }
            }

        }

        public bool AssignSessionToGroup(int sessionID, int groupID)
        {

            bool result = false;
            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Sesja_grupa_sesji ( IdSesja, IdGrupa_sesji)
                                            select @sess_id, @sess_group_id except select IdSesja, IdGrupa_sesji from Sesja_grupa_sesji";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@sess_group_id", SqlDbType.Int);
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@sess_group_id"].Value = groupID;
                cmd.Prepare();
                cmd.ExecuteNonQuery();

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database", "Update failed");
                throw exc;
            }
            finally
            {
                CloseConnection();
            }
            result = true;
            return result;
        }

        public void FeedAnthropometricData(string data)
        {

            


            int resultCode = 0;

            string[] atrybuty = null;

            int pid = 0;
            string sid = null;


            atrybuty = data.Split(';');

            pid = int.Parse(atrybuty[0].Substring(12, 4));
            sid = atrybuty[0];

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "feed_anthropometric_data";
                cmd.Parameters.Add("@pid", SqlDbType.Int);
                cmd.Parameters.Add("@sname", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@BodyMass", SqlDbType.Decimal,5);
                cmd.Parameters["@BodyMass"].Precision = 5;
                cmd.Parameters["@BodyMass"].Scale = 2;
                cmd.Parameters.Add("@Height", SqlDbType.Int);
                cmd.Parameters.Add("@InterAsisDistance", SqlDbType.Int);
                cmd.Parameters.Add("@LeftLegLength", SqlDbType.Int);
                cmd.Parameters.Add("@RightLegLenght", SqlDbType.Int);
                cmd.Parameters.Add("@LeftKneeWidth", SqlDbType.Int);
                cmd.Parameters.Add("@RightKneeWidth", SqlDbType.Int);
                cmd.Parameters.Add("@LeftAnkleWidth", SqlDbType.Int);
                cmd.Parameters.Add("@RightAnkleWidth", SqlDbType.Int);
                cmd.Parameters.Add("@LeftCircuitThigh", SqlDbType.Int);
                cmd.Parameters.Add("@RightCircuitThight", SqlDbType.Int);
                cmd.Parameters.Add("@LeftCircuitShank", SqlDbType.Int);
                cmd.Parameters.Add("@RightCircuitShank", SqlDbType.Int);
                cmd.Parameters.Add("@LeftShoulderOffset", SqlDbType.Int);
                cmd.Parameters.Add("@RightShoulderOffset", SqlDbType.Int);
                cmd.Parameters.Add("@LeftElbowWidth", SqlDbType.Int);
                cmd.Parameters.Add("@RightElbowWidth", SqlDbType.Int);
                cmd.Parameters.Add("@LeftWristWidth", SqlDbType.Int);
                cmd.Parameters.Add("@RightWristWidth", SqlDbType.Int);
                cmd.Parameters.Add("@LeftWristThickness", SqlDbType.Int);
                cmd.Parameters.Add("@RightWristThickness", SqlDbType.Int);
                cmd.Parameters.Add("@LeftHandWidth", SqlDbType.Int);
                cmd.Parameters.Add("@RightHandWidth", SqlDbType.Int);
                cmd.Parameters.Add("@LeftHandThickness", SqlDbType.Int);
                cmd.Parameters.Add("@RightHandThickness", SqlDbType.Int);
                
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@pid"].Value = pid;
                cmd.Parameters["@sname"].Value = sid;
                cmd.Parameters["@BodyMass"].Value = decimal.Parse(atrybuty[1], nfi);
                cmd.Parameters["@Height"].Value = ExtractInt(atrybuty[2]);
                cmd.Parameters["@InterAsisDistance"].Value = ExtractInt(atrybuty[3]); 
                cmd.Parameters["@LeftLegLength"].Value = ExtractInt(atrybuty[4]); 
                cmd.Parameters["@RightLegLenght"].Value = ExtractInt(atrybuty[5]); 
                cmd.Parameters["@LeftKneeWidth"].Value = ExtractInt(atrybuty[6]); 
                cmd.Parameters["@RightKneeWidth"].Value = ExtractInt(atrybuty[7]); 
                cmd.Parameters["@LeftAnkleWidth"].Value = ExtractInt(atrybuty[8]); 
                cmd.Parameters["@RightAnkleWidth"].Value = ExtractInt(atrybuty[9]); 
                cmd.Parameters["@LeftCircuitThigh"].Value = ExtractInt(atrybuty[10]); 
                cmd.Parameters["@RightCircuitThight"].Value = ExtractInt(atrybuty[11]); 
                cmd.Parameters["@LeftCircuitShank"].Value = ExtractInt(atrybuty[12]); 
                cmd.Parameters["@RightCircuitShank"].Value = ExtractInt(atrybuty[13]); 
                cmd.Parameters["@LeftShoulderOffset"].Value = ExtractInt(atrybuty[14]); 
                cmd.Parameters["@RightShoulderOffset"].Value = ExtractInt(atrybuty[15]); 
                cmd.Parameters["@LeftElbowWidth"].Value = ExtractInt(atrybuty[16]); 
                cmd.Parameters["@RightElbowWidth"].Value = ExtractInt(atrybuty[17]); 
                cmd.Parameters["@LeftWristWidth"].Value = ExtractInt(atrybuty[18]); 
                cmd.Parameters["@RightWristWidth"].Value = ExtractInt(atrybuty[19]); 
                cmd.Parameters["@LeftWristThickness"].Value = ExtractInt(atrybuty[20]); 
                cmd.Parameters["@RightWristThickness"].Value = ExtractInt(atrybuty[21]); 
                cmd.Parameters["@LeftHandWidth"].Value = ExtractInt(atrybuty[22]); 
                cmd.Parameters["@RightHandWidth"].Value = ExtractInt(atrybuty[23]); 
                cmd.Parameters["@LeftHandThickness"].Value = ExtractInt(atrybuty[24]); 
                cmd.Parameters["@RightHandThickness"].Value = ExtractInt(atrybuty[25]); 

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("Database", "Update failed on the SQL side ");
                throw exc;
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;


                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "One of the attributes assumed in this set is not defined in the database!");
                        throw exc;
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the performer configuration entry for session" + sid +" of subject " + pid + " not found");
                        throw exc;
                    case 5:
                        exc = new UpdateException("Value already exists", "value of some attribute for " + sid + " of subject " + pid + " already exists");
                        throw exc;
                    case 6:
                        exc = new UpdateException("Type cast", "value type casting error ocurred for " + sid + " of subject " + pid + ".");
                        throw exc;
                    case 11:
                        exc = new UpdateException("Invalid name format", "invalid name format for session " + sid + " of subject " + pid + ".");
                        throw exc;
                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw exc;

                }
            }

        }


        public void SetExerciseNo(string trialName, int exerciseNo, bool update){
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_trial_exercise_number";
                cmd.Parameters.Add("@trial_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@exercise_no", SqlDbType.Int);
                cmd.Parameters.Add("@overwrite", SqlDbType.Bit);

                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@trial_name"].Value = trialName;
                cmd.Parameters["@exercise_no"].Value = exerciseNo;
                cmd.Parameters["@overwrite"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("Database", "Update failed");
                throw exc;
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name ExerciseNo is not available for trial" + trialName);
                        throw exc;
                    case 2:
                        exc = new UpdateException("Invalid enum value", "The value " + exerciseNo + " is not valid for the enum-type attribute ExerciseNo");
                        throw exc;
                    case 3:
                        exc = new UpdateException("Invalid resource name", "The trial of name " + trialName + " not found");
                        throw exc;
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute ExerciseNo for this trial already exists, while you called this operation in no overwrite mode");
                        throw exc;
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + exerciseNo + " provided is not valid for this numeric-type attribute ExerciseNo");
                        throw exc;

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw exc;

                }
            }        
        }

        public void SetPerformerConfAttribute(int performerConfID, string attributeName, string attributeValue, bool update)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_performer_conf_attribute";
                cmd.Parameters.Add("@pc_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@pc_id"].Value = performerConfID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw exc;
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                string resName = "performer_conf";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to " + resName);
                        throw exc;
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw exc;
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + performerConfID + "not found");
                        throw exc;
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw exc;
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw exc;

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw exc;

                }
            }

        }
        public int CreateSessionFromFiles(string path)
        {

            // int labID, string motionKindName, DateTime sessionDate, string sessionName, string tags, string sessionDescription, int[] sessionGroupIDs
            
            DirectoryInfo di = new DirectoryInfo(path);
            int i = 0;
            foreach (FileInfo fi in di.GetFiles("????-??-??*-S??*.??*", SearchOption.AllDirectories))
            {
                // ????-??-??-[AB]????-S??*.??*";
                if (Regex.IsMatch(fi.Name, @"(\d{4}-\d{2}-\d{2}-[AB]\d{4}-S\d{2}(-T\d{2})?(\.\d+)?\.(asf|amc|c3d|avi|zip|vsk|mp))|(\d{4}-\d{2}-\d{2}-S\d{4}(-T\d{4})?\.(png|xml))"))
                i++;
            }


            if (i < 1)
            {
                FileAccessServiceException exc = new FileAccessServiceException("Invalid names", "File names do not match the supported pattern");
                throw exc;
            }
            
            
            string userName = "dpisko";
            
            int result = 0;
            int sessionId = 0;

            int fileCounter = -2;

            string fullPath = null;
            string fileName;
            string entity;
            int resId;
            int asfFileId = 0;


            SqlConnection connF;
            SqlCommand cmdF;

            SqlConnection connA;
            SqlCommand cmdA;

            FileNameEntryCollection fileNames = new FileNameEntryCollection();
            // Wczytanie nazw plikow ze wskazanego katalogu. Utworzenie sesji i triali wedlug nazw tych plikow. Poprzedzone walidacja.
            connF = new SqlConnection(GetConnectionString());
            connA = new SqlConnection(GetConnectionString());
            try
            {
                OpenConnection();
                connF.Open();
                connA.Open();


               // DirectoryInfo di = new DirectoryInfo(path);

                foreach (FileInfo fi in di.GetFiles("????-??-??*-S??*.??*", SearchOption.TopDirectoryOnly))
                {

                    if (Regex.IsMatch(fi.Name, @"(\d{4}-\d{2}-\d{2}-[AB]\d{4}-S\d{2}(-T\d{2})?(\.\d+)?\.(asf|amc|c3d|avi|zip|vsk|mp))|(\d{4}-\d{2}-\d{2}-S\d{4}(-T\d{4})?\.(png|xml))"))
                    {
                        FileNameEntry fne = new FileNameEntry();
                        fne.Name = fi.Name;
                        fileNames.Add(fne);
                    }
                }

                cmd = conn.CreateCommand();
                cmd.CommandTimeout = 120;
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
                        FileAccessServiceException exc = new FileAccessServiceException("validation", "File set validation error: "+result);
                        throw exc;
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
                    fileCounter++;
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

                    fullPath = path + @"\"+ fileName;

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

                // Directory.Delete(di.FullName, true);

            }
            catch (SqlException ex)
            {
                FileAccessServiceException exc = new FileAccessServiceException("Database access failure", "Other: " + ex.Message + " " + fileCounter);
                throw exc;
            }

            finally
            {
                connA.Close();
                connF.Close();
                CloseConnection();
            }
            return sessionId;
        }


    }
}
