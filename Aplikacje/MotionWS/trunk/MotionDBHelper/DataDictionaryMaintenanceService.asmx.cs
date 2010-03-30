using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data;
using System.Data.SqlClient;
using System.Xml;

namespace MotionDBWebServices
{
    /// <summary>
    /// Summary description for DataDictionaryMaintenanceService
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/DataDictionaryMaintenanceService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class DataDictionaryMaintenanceService : DatabaseAccessService
    {

        [WebMethod]
        public PlainSessionDetails[] ListPerformerSessions(int performerID)
        {
            List<PlainSessionDetails> sdl = new List<PlainSessionDetails>();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions";
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
                SqlDataReader dr = cmd.ExecuteReader();

                while (dr.Read())
                {
                    PlainSessionDetails sd = new PlainSessionDetails();
                    sd.SessionID = int.Parse(dr["SessionID"].ToString());
                    sd.UserID = int.Parse(dr["UserID"].ToString());
                    sd.LabID = int.Parse(dr["LabID"].ToString());
                    sd.MotionKindID = (dr["MotionKindID"] == DBNull.Value) ? 0 : int.Parse(dr["MotionKindID"].ToString());
                    sd.PerformerID = int.Parse(dr["PerformerID"].ToString());
                    sd.SessionDate = DateTime.Parse(dr["SessionDate"].ToString());
                    sd.SessionDescription = (dr["SessionDescription"] == DBNull.Value) ? "" : dr["SessionDescription"].ToString();

                    sdl.Add(sd);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report problem
            }
            finally
            {
                CloseConnection();
            }
            //if(sdl.Count==0){
            //    SessionDetails x = new SessionDetails();
            //    x.SessionDescription = "pusto";
            //    sdl.Add(x);
            //}
            return sdl.ToArray();
        }  
}
}


