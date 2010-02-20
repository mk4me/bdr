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
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class BasicUpdatesService : DatabaseAccessService
    {

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
                    return 0;

                }
                finally
                {
                    CloseConnection();
                }
            return newPerformerId;

        }
 

        [WebMethod]
        public int CreateSession(int performerID, int[] sessionGroupIDs, SessionData sessionData)
        {
            return 0;
        }

        [WebMethod]
        public int CreateTrial(int sessionID, TrialData trialData)
        {
            return 0;
        }

        [WebMethod]
        public bool AssignSessionToGroup(int sessionID, int groupID)
        {
            return false;
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
                return 6;

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
                return 6;

            }
            finally
            {
                CloseConnection();
            }
            return resultCode;

        }        

        [WebMethod]
        public bool SetTrialAttribute(int trialID, int attributeID, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        }


        [WebMethod]
        public bool SetFileAttribute(int fileID, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        } 


    }
}
