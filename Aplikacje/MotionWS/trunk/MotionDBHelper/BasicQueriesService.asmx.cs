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
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class BasicQueriesService : DatabaseAccessService
    {



        [WebMethod]
        public SessionDetails[] ListPerformerSessions(int performerID)
        {
            List<SessionDetails> sdl = new List<SessionDetails>();

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
                    SessionDetails sd = new SessionDetails();
                    sd.SessionID = int.Parse( dr[0].ToString());
                    sd.UserID = int.Parse( dr[1].ToString());
                    sd.LabID = int.Parse( dr[2].ToString());
                    sd.MotionKindID = int.Parse( dr[3].ToString());
                    sd.PerformerID = int.Parse( dr[4].ToString());
                    sd.SessionDate = DateTime.Parse( dr[5].ToString());
                    sd.SessionDescription = dr[6].ToString();

                    sdl.Add(sd);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                SessionDetails x = new SessionDetails();
                x.SessionDescription = ex.ToString();
                sdl.Add(x);
            }
            CloseConnection();
            //if(sdl.Count==0){
            //    SessionDetails x = new SessionDetails();
            //    x.SessionDescription = "pusto";
            //    sdl.Add(x);
            //}
            return sdl.ToArray();
        }

        [WebMethod]
        public FileDetails[] ListSessionFiles(int sessionID)
        {
            List<FileDetails> fdl = new List<FileDetails>();
            // server = DBPAWELL
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_files";
                SqlParameter sessId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = sessionID;
                SqlDataReader dr = cmd.ExecuteReader();

                while (dr.Read())
                {
                    FileDetails fd = new FileDetails();
                    fd.FileID = int.Parse(dr[0].ToString());
                    fd.FileName = dr[1].ToString();

                    fdl.Add(fd);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            //if(sdl.Count==0){
            //    SessionDetails x = new SessionDetails();
            //    x.SessionDescription = "pusto";
            //    sdl.Add(x);
            //}
            return fdl.ToArray();
        }


        [WebMethod]
        public XmlDocument ListPerformerSessionsXML(int performerID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions_xml";
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if(xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerSessionList"));
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }


        [WebMethod]
        public XmlDocument ListPerformerSessionsWithAttributesXML(int performerID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerSessionList"));
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }



        [WebMethod]
        public XmlDocument ListSessionFilesXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_files_xml";
                SqlParameter sessId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = sessionID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }


    }
}
