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

        [WebMethod]
        public PlainFileDetails[] ListSessionFiles(int sessionID)
        {
            List<PlainFileDetails> fdl = new List<PlainFileDetails>();
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
                    PlainFileDetails fd = new PlainFileDetails();
                    fd.FileID = int.Parse(dr[0].ToString());
                    fd.FileName = dr[1].ToString();
                    fd.FileDescription = dr[2].ToString();

                    fdl.Add(fd);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            finally
            {
                CloseConnection();
            }
            return fdl.ToArray();
        }




        // Generic query

        [WebMethod]
        public XmlDocument GenericQueryXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "evaluate_generic_query";
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                SqlParameter showPerfPar = cmd.Parameters.Add("@perf", SqlDbType.Bit);
                showPerfPar.Direction = ParameterDirection.Input;
                showPerfPar.Value = entitiesToInclude.Contains("performer")?1:0;
                SqlParameter showSessfPar = cmd.Parameters.Add("@sess", SqlDbType.Bit);
                showSessfPar.Direction = ParameterDirection.Input;
                showSessfPar.Value = entitiesToInclude.Contains("session")?1:0;
                SqlParameter showTrialfPar = cmd.Parameters.Add("@trial", SqlDbType.Bit);
                showTrialfPar.Direction = ParameterDirection.Input;
                showTrialfPar.Value = entitiesToInclude.Contains("trial")?1:0;
                SqlParameter showSegmPar = cmd.Parameters.Add("@segm", SqlDbType.Bit);
                showSegmPar.Direction = ParameterDirection.Input;
                showSegmPar.Value = entitiesToInclude.Contains("segment")?1:0;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }

                dr.Close();
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("GenericQueryResult"));

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
        public XmlDocument GenericQueryUniformXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "evaluate_generic_query_uniform";
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                SqlParameter showPerfPar = cmd.Parameters.Add("@perf", SqlDbType.Bit);
                showPerfPar.Direction = ParameterDirection.Input;
                showPerfPar.Value = entitiesToInclude.Contains("performer")?1:0;
                SqlParameter showSessfPar = cmd.Parameters.Add("@sess", SqlDbType.Bit);
                showSessfPar.Direction = ParameterDirection.Input;
                showSessfPar.Value = entitiesToInclude.Contains("session")?1:0;
                SqlParameter showTrialfPar = cmd.Parameters.Add("@trial", SqlDbType.Bit);
                showTrialfPar.Direction = ParameterDirection.Input;
                showTrialfPar.Value = entitiesToInclude.Contains("trial")?1:0;
                SqlParameter showSegmPar = cmd.Parameters.Add("@segm", SqlDbType.Bit);
                showSegmPar.Direction = ParameterDirection.Input;
                showSegmPar.Value = entitiesToInclude.Contains("segment")?1:0;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }

                dr.Close();
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("GenericUniformAttributesQueryResult"));

            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
            
        }        
        /*
        [WebMethod]
        public XmlDocument GenericQueryXML1()
        {
            XmlDocument xd = new XmlDocument();

            FilterPredicateCollection filter = new FilterPredicateCollection();

            FilterPredicate p1 = new FilterPredicate();

            p1.PredicateID = 1;
            p1.ParentPredicate = 0;
            p1.ContextEntity = "performer";
            p1.PreviousPredicate = 0;
            p1.NextOperator = "";
            p1.FeatureName = "LastName";
            p1.Operator = "=";
            p1.Value = "Kowalski";
            p1.AggregateFunction = "";
            p1.AggregateEntity = "";

            filter.Add(p1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "evaluate_generic_query";
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                SqlParameter showPerfPar = cmd.Parameters.Add("@perf", SqlDbType.Bit);
                showPerfPar.Direction = ParameterDirection.Input;
                showPerfPar.Value = 1;
                SqlParameter showSessfPar = cmd.Parameters.Add("@sess", SqlDbType.Bit);
                showSessfPar.Direction = ParameterDirection.Input;
                showSessfPar.Value = 1;
                SqlParameter showTrialfPar = cmd.Parameters.Add("@trial", SqlDbType.Bit);
                showTrialfPar.Direction = ParameterDirection.Input;
                showTrialfPar.Value = 0;
                SqlParameter showSegmPar = cmd.Parameters.Add("@segm", SqlDbType.Bit);
                showSegmPar.Direction = ParameterDirection.Input;
                showSegmPar.Value = 0;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("GenericQueryResult"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
            //            return (PerformerSessionListXML) xd;
        }

        */
        // By ID lookup

        [WebMethod]
        public XmlDocument GetPerformerByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_performer_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument GetSessionByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_session_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionDetailsWithAttributes"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }
        [WebMethod]
        public XmlDocument GetTrialByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_trial_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("TrialDetailsWithAttributes"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument GetSegmentByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_segment_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SegmentDetailsWithAttributes"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }


        // Performer queries

        [WebMethod]
        public XmlDocument ListPerformersXML()
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performers_xml";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerList"));

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
        public XmlDocument ListPerformersWithAttributesXML()
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performers_attributes_xml";
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerWithAttributesList"));
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


        [WebMethod]
        public XmlDocument ListLabPerformersWithAttributesXML(int labID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_lab_performers_attributes_xml";
                SqlParameter labId = cmd.Parameters.Add("@lab_id", SqlDbType.Int);
                labId.Direction = ParameterDirection.Input;
                labId.Value = labID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("LabPerformerWithAttributesList"));
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

        [WebMethod]
        public XmlDocument ListLabSessionsWithAttributesXML(int labID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_lab_sessions_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@lab_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = labID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("LabSessionWithAttributesList"));
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
        public XmlDocument ListFilesXML(int subjectID, string subjectType)
        {
            XmlDocument xd = new XmlDocument();

            string operationName = "";
            string paramName = "";

            switch ( subjectType ){
                case "performer": 
                    operationName = "list_performer_files_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_files_xml";
                    paramName = "@sess_id";
                    break;
                case "trial": 
                    operationName = "list_trial_files_xml";
                    paramName = "@trial_id";
                    break;
            }
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                SqlParameter sessId = cmd.Parameters.Add(paramName, SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = subjectID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileList"));

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
        public XmlDocument ListFilesWithAttributesXML(int subjectID, string subjectType)
        {
            XmlDocument xd = new XmlDocument();

            string operationName = "";
            string paramName = "";

            switch (subjectType)
            {
                case "performer":
                    operationName = "list_performer_files_attributes_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_files_attributes_xml";
                    paramName = "@sess_id";
                    break;
                case "trial": 
                    operationName = "list_trial_files_attributes_xml";
                    paramName = "@trial_id";
                    break;
            }
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                SqlParameter sessId = cmd.Parameters.Add(paramName, SqlDbType.Int);
                sessId.Direction = ParameterDirection.Input;
                sessId.Value = subjectID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileList"));

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

        /*

                [WebMethod]
                public XmlDocument ListPerformerFilesXML(int performerID)
                {
                    XmlDocument xd = new XmlDocument();
                    try
                    {
                        OpenConnection();

                        SqlCommand cmd = conn.CreateCommand();
                        cmd.CommandType = CommandType.StoredProcedure;
                        cmd.CommandText = "list_performer_files_xml";
                        SqlParameter sessId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                        sessId.Direction = ParameterDirection.Input;
                        sessId.Value = performerID;
                        XmlReader dr = cmd.ExecuteXmlReader();
                        if (dr.Read())
                        {
                            xd.Load(dr);
                        }
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileList"));

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
                public XmlDocument ListPerformerFilesWithAttributesXML(int performerID)
                {
                    XmlDocument xd = new XmlDocument();
                    try
                    {
                        OpenConnection();

                        SqlCommand cmd = conn.CreateCommand();
                        cmd.CommandType = CommandType.StoredProcedure;
                        cmd.CommandText = "list_performer_files_attributes_xml";
                        SqlParameter sessId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                        sessId.Direction = ParameterDirection.Input;
                        sessId.Value = performerID;
                        XmlReader dr = cmd.ExecuteXmlReader();
                        if (dr.Read())
                        {
                            xd.Load(dr);
                        }
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileWithAttributesList"));

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
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileList"));

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
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileWithAttributesList"));

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
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileList"));

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
                        if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("FileWithAttributesList"));

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

        */

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

                dr.Close();

            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("AttributeDefinitionList"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }


        [WebMethod]
        public XmlDocument ListAttributeGroupsDefined(string entityKind)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_attribute_groups_defined";
                cmd.Parameters.Add("@entity_kind", SqlDbType.VarChar, 20);
                cmd.Parameters["@entity_kind"].Value = entityKind;
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("AttributeGroupDefinitionList"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument ListSessionGroupsDefined()
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();
                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandText = "list_session_groups_defined";
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("SessionGroupDefinitionList"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }

        [WebMethod]
        public XmlDocument ListMotionKindsDefined()
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_motion_kinds_defined";
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
            if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("MotionKindDefinitionList"));
            xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
            return xd;
        }


    }
}