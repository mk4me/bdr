using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data;
using System.Data.SqlClient;

namespace MotionDBWebServices
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class BasicUpdatesService : DatabaseAccessService
    {

        // Core entities creation operations

        [WebMethod]
        public int CreatePerformer(PerformerData performerData)
        {
            int newPerformerId = 0;
           
            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Performer ( Imie, Nazwisko)
                                            values (@perf_name, @perf_surname)
                                            set @perf_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@perf_name", SqlDbType.VarChar,30);
                cmd.Parameters.Add("@perf_surname", SqlDbType.VarChar,50);
                SqlParameter performerIdParameter =
                    new SqlParameter("@perf_id", SqlDbType.Int);
                performerIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(performerIdParameter);
                cmd.Parameters["@perf_name"].Value = performerData.Name;
                cmd.Parameters["@perf_surname"].Value = performerData.Surname;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newPerformerId = (int)performerIdParameter.Value;


                }
                catch (SqlException ex)
                {
                    // log the exception

                }
                finally
                {
                    CloseConnection();
                }
            return newPerformerId;

        }

        [WebMethod]
        public int CreateSession(int userID, int labID, string motionKindName, int performerID, DateTime sessionDate, string sessionDescription, int[] sessionGroupIDs)
        {
            int newSessionId = 0;

            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Sesja ( IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, IdPerformer, Data, Opis_sesji)
                                            values (@sess_user, @sess_lab, (select top(1) IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa = @motion_kind_name), @sess_perf, @sess_date, @sess_desc )
                                            set @sess_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@sess_user", SqlDbType.Int);
                cmd.Parameters.Add("@sess_lab", SqlDbType.Int);
                cmd.Parameters.Add("@motion_kind_name", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@sess_perf", SqlDbType.Int);
                cmd.Parameters.Add("@sess_date", SqlDbType.DateTime);
                cmd.Parameters.Add("@sess_desc", SqlDbType.VarChar, 100);               
                
                SqlParameter sessionIdParameter =
                    new SqlParameter("@sess_id", SqlDbType.Int);
                sessionIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(sessionIdParameter);
                cmd.Parameters["@sess_user"].Value = userID;
                cmd.Parameters["@sess_lab"].Value = labID;
                cmd.Parameters["@motion_kind_name"].Value = motionKindName;
                cmd.Parameters["@sess_perf"].Value = performerID;
                cmd.Parameters["@sess_date"].Value = sessionDate;
                cmd.Parameters["@sess_desc"].Value = sessionDescription;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newSessionId = (int)sessionIdParameter.Value;

                

            }
            catch (SqlException ex)
            {
                // log the exception


            }
            finally
            {
                CloseConnection();
            }
            foreach (int sg in sessionGroupIDs) AssignSessionToGroup(newSessionId, sg);

            return newSessionId;

        }

        [WebMethod]
        public int CreateTrial(int sessionID, string trialDescription, int trialDuration)
        {
            int newTrialId = 0;          
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Obserwacja ( IdSesja, Opis_obserwacji, Czas_trwania)
                                    values (@trial_session, @trial_desc, @trial_duration )
                                            set @trial_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@trial_session", SqlDbType.Int);
                cmd.Parameters.Add("@trial_desc", SqlDbType.VarChar,100);
                cmd.Parameters.Add("@trial_duration", SqlDbType.Int);

                SqlParameter trialIdParameter =
                    new SqlParameter("@trial_id", SqlDbType.Int);
                trialIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(trialIdParameter);
                cmd.Parameters["@trial_session"].Value = sessionID;
                cmd.Parameters["@trial_desc"].Value = trialDescription;
                cmd.Parameters["@trial_duration"].Value = trialDuration;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newTrialId = (int)trialIdParameter.Value;
            }
            catch (SqlException ex)
            {
                // log the exception

            }
            finally
            {
                CloseConnection();
            }

            return newTrialId;

        }

        [WebMethod]
        public int DefineTrialSegment(int trialID, string segmentName, int startTime, int endTime)
        {
            int newSegmentId = 0;
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Segment ( IdObserwacja, Nazwa, Czas_poczatku, Czas_konca)
                                    values (@segment_trial, @segment_name, @start_time, @end_time )
                                            set @segment_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@segment_trial", SqlDbType.Int);
                cmd.Parameters.Add("@segment_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@start_time", SqlDbType.Int);
                cmd.Parameters.Add("@end_time", SqlDbType.Int);
                SqlParameter segmentIdParameter =
                    new SqlParameter("@segment_id", SqlDbType.Int);
                segmentIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(segmentIdParameter);
                cmd.Parameters["@segment_trial"].Value = trialID;
                cmd.Parameters["@segment_name"].Value = segmentName;
                cmd.Parameters["@start_time"].Value = startTime;
                cmd.Parameters["@end_time"].Value = endTime;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newSegmentId = (int)segmentIdParameter.Value;
            }
            catch (SqlException ex)
            {
                // log the exception

            }
            finally
            {
                CloseConnection();
            }

            return newSegmentId;

        }

        // Group Assignment operations

        [WebMethod]
        public bool AssignSessionToGroup(int sessionID, int groupID)
        {

            bool result = false;
            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Sesja_grupa_sesji ( IdSesja, IdGrupa_sesji)
                                            values (@sess_id, @sess_group_id )";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@sess_group_id", SqlDbType.Int);
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@sess_group_id"].Value = groupID;
                cmd.Prepare();
                cmd.ExecuteNonQuery();

            }
            catch (SqlException ex)
            {
                // log the exception

            }
            finally
            {
                CloseConnection();
            }
            result = true;
            return result;
        }

        // Attribute update operations

        /*
        The result value code meaning:
        0 - attribute value set successfully
        1 - attribute of this name is not applicable to session
        2 - the value provided is not valid for this enum-type attribute
        3 - session of given session id does not exist
        4 - (not assigned)
        5 - value of this attribute for this session exists, whille you called this operation in "no overwrite" mode
        6 - the value provided is not valid for this numeric-type attribute
        7 - other exception
        */
        [WebMethod]
        public int SetPerformerAttribute(int performerID, string attributeName, string attributeValue, bool update)
        {

            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_performer_attribute";
                cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@perf_id"].Value = performerID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                resultCode = 7;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }

        /*
        The result value code meaning:
        0 - attribute value set successfully
        1 - attribute of this name is not applicable to session
        2 - the value provided is not valid for this enum-type attribute
        3 - session of given session id does not exist
        4 - (not assigned)
        5 - value of this attribute for this session exists, whille you called this operation in "no overwrite" mode
        6 - the value provided is not valid for this numeric-type attribute
        7 - other exception
        */
        [WebMethod]
        public int SetSessionAttribute(int sessionID, string attributeName, string attributeValue, bool update)
        {

            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_session_attribute";
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0; 

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                resultCode = 7;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }        

        [WebMethod]
        public int SetTrialAttribute(int trialID, string attributeName, string attributeValue, bool update)
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
                resultCode = 7;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }

        [WebMethod]
        public int SetSegmentAttribute(int segmentID, string attributeName, string attributeValue, bool update)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_segment_attribute";
                cmd.Parameters.Add("@segment_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@segment_id"].Value = segmentID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                resultCode = 7;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }

        [WebMethod]
        public int SetFileAttribute(int fileID, string attributeName, string attributeValue, bool update)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_file_attribute";
                cmd.Parameters.Add("@file_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@file_id"].Value = fileID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = attributeValue;
                cmd.Parameters["@update"].Value = update ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                resultCode = 7;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }


    }
}
