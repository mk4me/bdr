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

        // non-XML sample queries

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

    // Session queries

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
            xd.DocumentElement.SetAttribute("xmlns","http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
            //            return (PerformerSessionListXML) xd;
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
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerSessionWithAttributesList"));
                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }


        // Trial queries

        [WebMethod]
        public XmlDocument ListSessionTrialsXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_trials_xml";
                SqlParameter perfId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = sessionID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionTrialList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
            //            return (PerformerSessionListXML) xd;
        }


        [WebMethod]
        public XmlDocument ListSessionTrialsWithAttributesXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_trials_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = sessionID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionTrialWithAttributesList"));
                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }

        // Segment queries

        [WebMethod]
        public XmlDocument ListTrialSegmentsXML(int trialID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_trial_segments_xml";
                SqlParameter perfId = cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = trialID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("TrailSegmentList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
            //            return (PerformerSessionListXML) xd;
        }


        [WebMethod]
        public XmlDocument ListTrialSegmentsWithAttributesXML(int trialID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_trial_segments_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = trialID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("TrailSegmentWithAttributesList"));
                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();

            return xd;
        }



        // File queries


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
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionFileList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument ListSessionFilesWithAttributesXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_files_attributes_xml";
                SqlParameter sessId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = sessionID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionFileWithAttributesList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument ListTrialFilesXML(int trialID)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_trial_files_xml";
                SqlParameter sessId = cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = trialID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("TrialFileList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument ListTrialFilesWithAttributesXML(int trialID)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_trial_files_attributes_xml";
                SqlParameter sessId = cmd.Parameters.Add("@trial_id", SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = trialID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("TrialFileWithAttributesList"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }



    // Provisional operations

        [WebMethod]
        public XmlDocument _PerformQuery(string query)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.Text;
                cmd.CommandText = query + " for XML AUTO, ELEMENTS, root ('Result')";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("Result"));

                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

    // Metadata queries

        [WebMethod]
        public XmlDocument ListAttributesDefined(string attributeGroupName, string entityKind)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_attributes_defined";
                cmd.Parameters.Add("@att_group", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity_kind", SqlDbType.VarChar, 20);
                cmd.Parameters["@att_group"].Value = attributeGroupName;
                cmd.Parameters["@entity_kind"].Value = entityKind;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("AttributeDefinitionList"));

                dr.Close();

            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }


    }
}
