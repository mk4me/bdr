using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Xml;
using System.Security.Permissions;

namespace MotionDBWebServices
{
    [ServiceBehavior (Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
 //   [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Required)] 
    public class BasicQueriesWS : DatabaseAccessService, IBasicQueriesWS
    {
        // GENERIC QUERIES
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement GenericQueryXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "evaluate_generic_query";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar,30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                SqlParameter showPerfPar = cmd.Parameters.Add("@perf", SqlDbType.Bit);
                showPerfPar.Direction = ParameterDirection.Input;
                showPerfPar.Value = entitiesToInclude.Contains("performer") ? 1 : 0;
                SqlParameter showSessfPar = cmd.Parameters.Add("@sess", SqlDbType.Bit);
                showSessfPar.Direction = ParameterDirection.Input;
                showSessfPar.Value = entitiesToInclude.Contains("session") ? 1 : 0;
                SqlParameter showTrialfPar = cmd.Parameters.Add("@trial", SqlDbType.Bit);
                showTrialfPar.Direction = ParameterDirection.Input;
                showTrialfPar.Value = entitiesToInclude.Contains("trial") ? 1 : 0;
                SqlParameter showSegmPar = cmd.Parameters.Add("@segm", SqlDbType.Bit);
                showSegmPar.Direction = ParameterDirection.Input;
                showSegmPar.Value = entitiesToInclude.Contains("segment") ? 1 : 0;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }

                dr.Close();
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("GenericQueryResult", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
                }

            }
            catch (SqlException ex)
            {
                QueryException exc = new QueryException("unknown", "Query execution error");
                throw new FaultException<QueryException>(exc, "Query invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("GenericQueryXML")));

            }
            finally
            { 
                CloseConnection(); 
            }
            return xd.DocumentElement;

        }
       [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement GenericQueryUniformXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "evaluate_generic_query_uniform";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar,30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                SqlParameter showPerfPar = cmd.Parameters.Add("@perf", SqlDbType.Bit);
                showPerfPar.Direction = ParameterDirection.Input;
                showPerfPar.Value = entitiesToInclude.Contains("performer") ? 1 : 0;
                SqlParameter showSessfPar = cmd.Parameters.Add("@sess", SqlDbType.Bit);
                showSessfPar.Direction = ParameterDirection.Input;
                showSessfPar.Value = entitiesToInclude.Contains("session") ? 1 : 0;
                SqlParameter showTrialfPar = cmd.Parameters.Add("@trial", SqlDbType.Bit);
                showTrialfPar.Direction = ParameterDirection.Input;
                showTrialfPar.Value = entitiesToInclude.Contains("trial") ? 1 : 0;
                SqlParameter showSegmPar = cmd.Parameters.Add("@segm", SqlDbType.Bit);
                showSegmPar.Direction = ParameterDirection.Input;
                showSegmPar.Value = entitiesToInclude.Contains("segment") ? 1 : 0;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }

                dr.Close();
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("GenericUniformAttributesQueryResult", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
                }

            }
            catch (SqlException ex)
            {
                QueryException exc = new QueryException("unknown", "Query execution error");
                
                throw new FaultException<QueryException>(exc, "Query invocation failure: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("GenericQueryUniformXML")));
            }
            CloseConnection();
            return xd.DocumentElement;

        } 
       
        // BY ID RETRIEVAL
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public  XmlElement GetPerformerByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_performer_by_id_xml";  // UWAGA - performer sam w sobie nie jest poki co zabezpieczany!
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                XmlReader dr = cmd.ExecuteXmlReader();
                //if (dr.) notFound = true;
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                else notFound = true;
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (notFound)
            {
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any performer");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetPerformerById")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return xd.DocumentElement;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement GetSessionByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_session_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                SqlParameter userLogin = cmd.Parameters.Add("@user_login", SqlDbType.VarChar,30);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                userLogin.Direction = ParameterDirection.Input;
                userLogin.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();

                //if (dr.) notFound = true;
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                else notFound = true;
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (notFound)
            {
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any session");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetSessionByIdXML")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return xd.DocumentElement;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public string GetSessionLabel(int id)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            bool found = false;
            string res = "";

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.Text;
                cmd.CommandText = "select * from session_label(@sess_id)";
                SqlParameter resId = cmd.Parameters.Add("@user_login", SqlDbType.VarChar ,30);
                SqlParameter userLogin = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                resId.Value = id;
                userLogin.Value = userName;

                SqlDataReader dr = cmd.ExecuteReader();

                while (dr.Read())
                {
                    res = dr[0].ToString();
                    found = true;
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (!found)
            {
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any session");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetSessionLabel")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return res; // +"(retrieved by: " + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name + ")";
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement GetTrialByIdXML(int id)  // UWAGA - docelowo nalezaloby zabronic pobrania danych Trial-a z niedostepnej danemu uzytkownikowi sesji!
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_trial_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                XmlReader dr = cmd.ExecuteXmlReader();
                //if (dr.) notFound = true;
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                else notFound = true;
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (notFound)
            {
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any trial");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetTrialByIdXML")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return xd.DocumentElement;
        }

        public XmlElement GetSegmentByIdXML(int id)// UWAGA - docelowo nalezaloby zabronic pobrania danych z segmentu pochodzacego z Trial-a z niedostepnej danemu uzytkownikowi sesji!
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_segment_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                XmlReader dr = cmd.ExecuteXmlReader();
                //if (dr.) notFound = true;
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                else notFound = true;
                dr.Close();
            }
            catch (SqlException ex)
            {
                // report exception
            }
            CloseConnection();
            if (notFound)
            {
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any segment");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetSegmentById")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return xd.DocumentElement;
        }




        // PERFORMER QUERIES
        public XmlElement ListPerformersXML()  // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("PerformerList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;

        }

        public XmlElement ListPerformersWithAttributesXML() // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("PerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListLabPerformersWithAttributesXML(int labID) // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("PerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

            return xd.DocumentElement;
        }

    // SESSION QUERIES
        public XmlElement ListPerformerSessionsXML(int performerID)
        {
            XmlDocument xd = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions_xml";
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter userLogin = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("PerformerSessionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }
       // [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement ListPerformerSessionsWithAttributesXML(int performerID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("PerformerSessionWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

            return xd.DocumentElement;
        }
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public XmlElement ListLabSessionsWithAttributesXML(int labID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_lab_sessions_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@lab_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = labID;
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("LabSessionWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

            return xd.DocumentElement;
        }

        // TRIAL QUERIES
        public XmlElement ListSessionTrialsXML(int sessionID)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("SessionTrialList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListSessionTrialsWithAttributesXML(int sessionID)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("SessionTrialWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
                }
                xd.DocumentElement.SetAttribute("xmlns", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService");
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

            return xd.DocumentElement;
        }
        // SEGMENT QUERIES
        public XmlElement ListTrialSegmentsXML(int trialID)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("TrailSegmentList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListTrialSegmentsWithAttributesXML(int trialID)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("TrailSegmentWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

            return xd.DocumentElement;
        }

        // FILE QUERIES
        public XmlElement ListFilesXML(int subjectID, string subjectType)
        {
            XmlDocument xd = new XmlDocument();

            string operationName = "";
            string paramName = "";

            switch (subjectType)
            {
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("FileList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListFilesWithAttributesXML(int subjectID, string subjectType)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("FileWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        // METADATA QUERIES
        public XmlElement ListAttributesDefined(string attributeGroupName, string entityKind)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("AttributeDefinitionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListAttributeGroupsDefined(string entityKind)
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("AttributeGroupDefinitionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListSessionGroupsDefined()
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("SessionGroupDefinitionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

        public XmlElement ListMotionKindsDefined()
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
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("MotionKindDefinitionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            return xd.DocumentElement;
        }

    }



}
