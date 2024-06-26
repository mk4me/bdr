﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Xml;
using System.Security.Permissions;
using MotionDBCommons;

namespace MotionDBWebServices
{
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", IncludeExceptionDetailInFaults = true)]
    
    [ErrorLoggerBehaviorAttribute]
 //   [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Required)] 
    public class BasicQueriesWS : DatabaseAccessService, IBasicQueriesWS
    {
        // GENERIC QUERIES

        public XmlElement GenericQueryXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
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

                SqlParameter showMCPar = cmd.Parameters.Add("@mc", SqlDbType.Bit);
                showMCPar.Direction = ParameterDirection.Input;
                showMCPar.Value = entitiesToInclude.Contains("measurement_conf") ? 1 : 0;
                SqlParameter showPCPar = cmd.Parameters.Add("@pc", SqlDbType.Bit);
                showPCPar.Direction = ParameterDirection.Input;
                showPCPar.Value = entitiesToInclude.Contains("performer_conf") ? 1 : 0;
                SqlParameter showSGPar = cmd.Parameters.Add("@sg", SqlDbType.Bit);
                showSGPar.Direction = ParameterDirection.Input;
                showSGPar.Value = entitiesToInclude.Contains("session_group") ? 1 : 0;
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
 
        public XmlElement GenericQueryUniformXML(FilterPredicateCollection filter, string[] entitiesToInclude)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
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
                SqlParameter showMCPar = cmd.Parameters.Add("@mc", SqlDbType.Bit);
                showMCPar.Direction = ParameterDirection.Input;
                showMCPar.Value = entitiesToInclude.Contains("measurement_conf") ? 1 : 0;
                SqlParameter showPCPar = cmd.Parameters.Add("@pc", SqlDbType.Bit);
                showPCPar.Direction = ParameterDirection.Input;
                showPCPar.Value = entitiesToInclude.Contains("performer_conf") ? 1 : 0;
                SqlParameter showSGPar = cmd.Parameters.Add("@sg", SqlDbType.Bit);
                showSGPar.Direction = ParameterDirection.Input;
                showSGPar.Value = entitiesToInclude.Contains("session_group") ? 1 : 0;
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
        // SECURE ME !!!
       public XmlElement GetPerformerByIdXML(int id) // UWAGA - performer sam w sobie nie jest poki co zabezpieczany!
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;


            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_performer_by_id_xml";  
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
                QueryException exc = new QueryException("database", "Database-side failure");
                throw new FaultException<QueryException>(exc, "Query invocation failed", FaultCode.CreateReceiverFaultCode(new FaultCode("GetPerformerByIdXML")));

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

        public XmlElement GetSessionByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
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


        public string GetSessionLabel(int id)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            bool found = false;
            string res = "";

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.Text;
                cmd.CommandText = "select * from session_label(@user_login, @sess_id)";
                SqlParameter userLogin = cmd.Parameters.Add("@user_login", SqlDbType.VarChar ,30);
                SqlParameter resId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                userLogin.Value = userName;
                resId.Value = id;


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
            return res; // +"(retrieved by: " + OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name + ")";
        }



        public XmlElement GetSessionContent(int id)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_session_content_xml";
                SqlParameter userLogin = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                SqlParameter resId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                userLogin.Value = userName;
                resId.Value = id;


                XmlReader dr = cmd.ExecuteXmlReader();

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
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any measurement");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetSessionContent")));
            }
            return xd.DocumentElement;
        }



        // SECURE ME !!!
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

        public XmlElement GetMeasurementConfigurationByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_measurement_configuration_by_id_xml";
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
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any measurement configuration");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetMeasurementConfigurationByIdXML")));
            }
            return xd.DocumentElement;
        }

        // SECURE ME !!!
        public XmlElement GetPerformerConfigurationByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_performer_configuration_by_id_xml";
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
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any performer configuration");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetPerformerConfigurationByIdXML")));
            }
            return xd.DocumentElement;
        }


        // SECURE ME !!!
        public XmlElement GetFileDataByIdXML(int id)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_file_data_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                XmlReader dr = cmd.ExecuteXmlReader();

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
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any file");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetFileDataByIdXML")));
            }
            return xd.DocumentElement;
        }

        // PERFORMER QUERIES
        // SECURE ME !!!
        public XmlElement ListPerformersXML()  // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
        {
            XmlDocument xd = new XmlDocument();
            xd.AppendChild(xd.CreateElement("PerformerList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
/*                else
                {
                    XmlAttribute nsAttr = xd.CreateAttribute("xmlns");
                    nsAttr.InnerText = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService";
                    xd.DocumentElement.Attributes.Append(nsAttr);
                } */
                dr.Close();
            }
            catch (Exception ex)
            {
                // report exception
            }
            finally
            {
                CloseConnection();
            }
            return xd.DocumentElement;

        }
        // SECURE ME !!!
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


        public XmlElement ListSessionPerformersWithAttributesXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_performers_attributes_xml";
                SqlParameter labId = cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                labId.Direction = ParameterDirection.Input;
                labId.Value = sessionID;
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
                    xd.AppendChild(xd.CreateElement("SessionPerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);



            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_lab_performers_attributes_xml";
                SqlParameter labId = cmd.Parameters.Add("@lab_id", SqlDbType.Int);
                SqlParameter userLogin = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                labId.Direction = ParameterDirection.Input;
                labId.Value = labID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("LabPerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

        // SECURE ME !!!
        // sprawdzic !!!
        public XmlElement ListMeasurementPerformersWithAttributesXML(int measurementID)
        {
            XmlDocument xd = new XmlDocument();

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_measurement_performers_attributes_xml";
                SqlParameter measId = cmd.Parameters.Add("@meas_id", SqlDbType.Int);
                measId.Direction = ParameterDirection.Input;
                measId.Value = measurementID;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("MeasurementPerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_performer_sessions_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter perfId = cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                perfId.Direction = ParameterDirection.Input;
                perfId.Value = performerID;
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


        public XmlElement ListPerformerSessionsWithAttributesXML(int performerID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
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
                QueryException exc = new QueryException("database", "Other error in DBMS layer");
                throw new FaultException<QueryException>(exc, "Query invocation failure: " + ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("ListPerformerSessionsWithAttributesXML")));

            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }

        // XP **************


        public XmlElement ListLabSessionsWithAttributesXML(int labID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name; //ServiceSecurityContext.PrimaryIdentity.Name;
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


       

        public XmlElement  ListMeasurementConfSessionsWithAttributesXML(int labID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_measurement_conf_sessions_attributes_xml";
                SqlParameter perfId = cmd.Parameters.Add("@mc_id", SqlDbType.Int);
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
                    xd.AppendChild(xd.CreateElement("MeasurementConfSessionWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
        

        public XmlElement ListGroupSessionsWithAttributesXML(int sessionGroupID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_group_sessions_attributes_xml";
                SqlParameter resfId = cmd.Parameters.Add("@group_id", SqlDbType.Int);
                resfId.Direction = ParameterDirection.Input;
                resfId.Value = sessionGroupID;
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
                    xd.AppendChild(xd.CreateElement("GroupSessionWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

        public XmlElement ListSessionContents(int pageSize, int pageNo)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_contents_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter pageSizePar = cmd.Parameters.Add("@page_size", SqlDbType.Int);
                SqlParameter pageNoPar = cmd.Parameters.Add("@page_no", SqlDbType.Int);
                pageSizePar.Value = pageSize;
                pageNoPar.Value = pageNo;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("SessionContentList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

        
        public XmlElement ListSessionSessionGroups(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_session_groups_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
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
                    xd.AppendChild(xd.CreateElement("SessionSessionGroupList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_trials_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
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
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_trials_attributes_xml";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
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

        // PERFORMER CONFIGURATION QUERIES

        public XmlElement ListSessionPerformerConfsWithAttributesXML(int sessionID)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_session_performer_confs_attributes_xml"; 
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
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
                    xd.AppendChild(xd.CreateElement("SessionPerformerConfWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
        
         // MEASUREMENT CONFIGURATION LISTING QUERY
        public XmlElement ListMeasurementConfigurationsWithAttributesXML() // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_measurement_configurations_attributes_xml";
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
                    xd.AppendChild(xd.CreateElement("MeasurementConfListWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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
        public XmlElement ListFileAttributeDataXML(int subjectID, string subjectEntity)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            string operationName = "";
            string paramName = "";
            bool addLogin = false;

            SqlParameter usernamePar;

            switch (subjectEntity)
            {
                case "performer":
                    operationName = "list_performer_attr_files_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_attr_files_xml";
                    paramName = "@sess_id";
                    addLogin = true;
                    break;
                case "trial":
                    operationName = "list_trial_attr_files_xml";
                    paramName = "@trial_id";
                    addLogin = true;
                    break;
                case "measurement":
                    operationName = "list_measurement_attr_files_xml";
                    paramName = "@meas_id";
                    addLogin = true;
                    break;
                case "measurement_conf":
                    operationName = "list_measurement_conf_attr_files_xml";
                    paramName = "@mc_id";
                    addLogin = true;
                    break;
                case "performer_conf":
                    operationName = "list_performer_conf_attr_files_xml";
                    paramName = "@pc_id";
                    addLogin = true;
                    break;
            }
            try
            {
                OpenConnection();
                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                if (addLogin)
                {
                    usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    usernamePar.Direction = ParameterDirection.Input;
                    usernamePar.Value = userName;
                }
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


        public XmlElement ListFileAttributeDataWithAttributesXML(int subjectID, string subjectType)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            SqlParameter usernamePar;
            string operationName = "";
            string paramName = "";

            bool addLogin = false;

            switch (subjectType)
            {
                case "performer":
                    operationName = "list_performer_attr_files_attributes_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_attr_files_attributes_xml";
                    paramName = "@sess_id";
                    addLogin = true;
                    break;
                case "trial":
                    operationName = "list_trial_attr_files_attributes_xml";
                    paramName = "@trial_id";
                    addLogin = true;
                    break;
                case "measurement":
                    operationName = "list_measurement_attr_files_attributes_xml";
                    paramName = "@meas_id";
                    addLogin = true;
                    break;
                case "measurement_conf":
                    operationName = "list_measurement_conf_attr_files_attributes_xml";
                    paramName = "@mc_id";
                    addLogin = true;
                    break;
                case "performer_conf":
                    operationName = "list_performer_conf_attr_files_attributes_xml";
                    paramName = "@pc_id";
                    addLogin = true;
                    break;
            }
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                if (addLogin)
                {
                    usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    usernamePar.Direction = ParameterDirection.Input;
                    usernamePar.Value = userName;
                }
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

        public XmlElement ListFilesXML(int subjectID, string subjectType)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            string operationName = "";
            string paramName = "";
            bool addLogin = false;

            SqlParameter usernamePar;

            switch (subjectType)
            {
                case "measurement_configuration":
                    operationName = "list_measurement_conf_files_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_files_xml";
                    paramName = "@sess_id";
                    addLogin = true;

                    break;
                case "trial":
                    operationName = "list_trial_files_xml";
                    paramName = "@trial_id";
                    addLogin = true;

                    break;
            }
            try
            {
                OpenConnection();
                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                if (addLogin)
                {
                    usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    usernamePar.Direction = ParameterDirection.Input;
                    usernamePar.Value = userName;
                }
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
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            SqlParameter usernamePar;
            string operationName = "";
            string paramName = "";

            bool addLogin = false;

            switch (subjectType)
            {
                case "measurement_configuration":
                    operationName = "list_measurement_conf_files_attributes_xml";
                    paramName = "@perf_id";
                    break;
                case "session":
                    operationName = "list_session_files_attributes_xml";
                    paramName = "@sess_id";
                    addLogin = true;
                    break;
                case "trial":
                    operationName = "list_trial_files_attributes_xml";
                    paramName = "@trial_id";
                    addLogin = true;
                    break;
            }
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                if (addLogin)
                {
                    usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    usernamePar.Direction = ParameterDirection.Input;
                    usernamePar.Value = userName;
                }
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


 
/* ODLOZONE
        // SECURE ME !!!
        public XmlElement GetAttributeFileDataXML(int resourceID, string entity, string attributeName)
        {
            XmlDocument xd = new XmlDocument();
            bool notFound = false;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.Text;

                cmd.CommandText = "get_attribute_file_by_id_xml";
                SqlParameter resId = cmd.Parameters.Add("@res_id", SqlDbType.Int);
                resId.Direction = ParameterDirection.Input;
                resId.Value = resourceID;
                SqlParameter resId = cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                resId.Direction = ParameterDirection.Input;

                SqlParameter resId = cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                resId.Direction = ParameterDirection.Input;
                resId.Value = id;
                
                switch (subjectType)
                {
                    case "performer":
                        resId.Value = "list_performer_files_xml";
                        paramName = "@perf_id";
                        break;
                    case "session":
                        operationName = "list_session_files_xml";
                        paramName = "@sess_id";
                        addLogin = true;
                        break;
                    case "trial":
                        operationName = "list_trial_files_xml";
                        paramName = "@trial_id";
                        addLogin = true;
                        break;
                    case "measurement":
                        operationName = "list_measurement_files_xml";
                        paramName = "@meas_id";
                        addLogin = true;
                        break;
                    case "measurement_conf":
                        operationName = "list_measurement_conf_files_xml";
                        paramName = "@mc_id";
                        addLogin = true;
                        break;
                }
 
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
                QueryException exc = new QueryException(id.ToString(), "The id provided does not match any measurement configuration");
                throw new FaultException<QueryException>(exc, "Wrong identifier", FaultCode.CreateReceiverFaultCode(new FaultCode("GetAttributeFileDataXML")));
            }
            return xd.DocumentElement;
        }
*/
        // METADATA QUERIES
        public XmlElement ListAttributesDefined(string attributeGroupName, string entityKind)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_attributes_defined_with_enums";

                SqlParameter usernamePar;
                usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;

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
            string userName = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_attribute_groups_defined";

                SqlParameter usernamePar;
                usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;

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
        // SECURE ME !!!
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

        public XmlElement ListEnumValues(string attributeName, string entityKind)
        {
            XmlDocument xd = new XmlDocument();
            try
            {
                OpenConnection();

                SqlCommand cmd = conn.CreateCommand();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_attribute_enum_values";
                cmd.Parameters.Add("@att_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity_kind", SqlDbType.VarChar, 20);
                cmd.Parameters["@att_name"].Value = attributeName;
                cmd.Parameters["@entity_kind"].Value = entityKind;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("EnumValueList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
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

        // Wizard-used validation operation
        // DOUBLE SECURE ME !!!
        public XmlElement ValidateSessionFileSet(FileNameEntryCollection fileNames)
        {
            XmlDocument xd = new XmlDocument();
            int _before = 0;
            int _after = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "validate_file_list_xml";
                SqlParameter fileListPar = cmd.Parameters.Add("@files", SqlDbType.Structured);
                fileListPar.Direction = ParameterDirection.Input;

                // filter-out ignored files

                _before = fileNames.Count;
                fileNames.RemoveAll(IgnoredFiles);
                _after = fileNames.Count;

                fileListPar.Value = fileNames;
                XmlReader dr = cmd.ExecuteXmlReader();

                if (dr.Read())
                {
                    xd.Load(dr);
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                QueryException exc = new QueryException("DB-side", "Stored procedure execution error: " + ex.Message);
                throw new FaultException<QueryException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("ValidateSessionFileSet")));
            }
            catch (Exception ex1)
            {
                QueryException exc = new QueryException("parameter", "Parameter processing error: " + ex1.Message+" "+ex1.Source);
                throw new FaultException<QueryException>(exc, "Parameter error", FaultCode.CreateReceiverFaultCode(new FaultCode("ValidateSessionFileSet")));
            }

            CloseConnection();            
            return xd.DocumentElement;
        }

        // Querying for the last update - needed for the shallow copy retrieval

        public DateTime GetDBTimestamp()
        {
            DateTime stamp = DateTime.Now;
            SqlDataReader dr;
             try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "time_stamp";
                dr = cmd.ExecuteReader();
                if (dr.Read())
                {
                    stamp = DateTime.Parse(dr[0].ToString());
                }
                dr.Close();

            }
             catch (SqlException ex)
             {
                 QueryException exc = new QueryException("DB-side", "Stored procedure execution error: " + ex.Message);
                 throw new FaultException<QueryException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("GetDBTimestamp")));
             }
             catch (Exception ex1)
             {
                 QueryException exc = new QueryException("unknown", "Other error: " + ex1.Message + " " + ex1.Source);
                 throw new FaultException<QueryException>(exc, "Other error", FaultCode.CreateReceiverFaultCode(new FaultCode("GetDBTimestamp")));
             }
            finally
            {
                CloseConnection();
            }

            return stamp;
        }


        public DateTime GetMetadataTimestamp()
        {
            DateTime stamp = DateTime.Now;
            SqlDataReader dr;
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "metadata_time_stamp";
                dr = cmd.ExecuteReader();
                if (dr.Read())
                {
                    stamp = DateTime.Parse(dr[0].ToString());
                }
                dr.Close();

            }
            catch (SqlException ex)
            {
                QueryException exc = new QueryException("DB-side", "Stored procedure execution error: " + ex.Message);
                throw new FaultException<QueryException>(exc, "Database-side error", FaultCode.CreateReceiverFaultCode(new FaultCode("GetMetadataTimestamp")));
            }
            catch (Exception ex1)
            {
                QueryException exc = new QueryException("unknown", "Other error: " + ex1.Message + " " + ex1.Source);
                throw new FaultException<QueryException>(exc, "Other error", FaultCode.CreateReceiverFaultCode(new FaultCode("GetMetadataTimestamp")));
            }
            finally
            {
                CloseConnection();
            }

            return stamp;
        }


        private static bool IgnoredFiles(FileNameEntry fne)
        {
            string s = fne.Name;
            // return false;
            return !(System.Text.RegularExpressions.Regex.IsMatch(s, @"(\d{4}-\d{2}-\d{2}-B\d{4}-S\d{2}(-T\d{2})?(\.\d+)?\.(asf|amc|c3d|avi|zip|mp|vsk))"));

        }

    }
}
