using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;
using MotionDBCommons;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "BasicUpdatesWS" here, you must also update the reference to "BasicUpdatesWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")]
    [ErrorLoggerBehaviorAttribute]
    public class BasicUpdatesWS : DatabaseAccessService, IBasicUpdatesWS
    {

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public int CreatePerformer(int PerformerID)
        {
            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Performer ( IdPerformer)
                                            values (@perf_id)";
                cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                cmd.Parameters["@perf_id"].Value = PerformerID;
                cmd.Prepare();
                cmd.ExecuteNonQuery();


            }
            catch (SqlException ex)
            {
                if (ex.ErrorCode == -2146232060)
                {
                    UpdateException exc = new UpdateException("Parameter", "Identifier value already exists");
                    throw new FaultException<UpdateException>(exc, "The PerformerID = "+PerformerID+" already exists", FaultCode.CreateReceiverFaultCode(new FaultCode("CreatePerformer")));

                }
                else
                {
                    UpdateException exc = new UpdateException("DB-Side", "DB-Side failure");
                    throw new FaultException<UpdateException>(exc, "DB-Side failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreatePerformer")));
                }
            }
            finally
            {
                CloseConnection();
            }
            return PerformerID;

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public int CreateSession(int labID, string motionKindName, DateTime sessionDate, string sessionName, string tags, string sessionDescription, int[] sessionGroupIDs)
        {
            int newSessionId = 0;
            int result = 0;
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;


            try
            {

                OpenConnection();

                SqlDataReader dr = null;
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "validate_session_group_id";
                SqlParameter idPar = cmd.Parameters.Add("@group_id", SqlDbType.Int);
                idPar.Direction = ParameterDirection.Input;
                if(sessionGroupIDs!=null) foreach (int sg in sessionGroupIDs) {
                    idPar.Value = sg;
                    dr = cmd.ExecuteReader();

                
                if (dr.Read())
                {
                   if(int.Parse(dr[0].ToString())!=1) {
                       UpdateException exc = new UpdateException("Invalid ID" , "Group with ID ="+sg.ToString()+" not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSession")));
                   }
                   dr.Dispose();

                }
                }
                if(dr!=null) dr.Close();
                //
                cmd.Parameters.Remove(idPar);

                cmd.CommandText = "create_session";
                cmd.Parameters.Add("@sess_user", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@sess_lab", SqlDbType.Int);
                cmd.Parameters.Add("@mk_name", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@sess_date", SqlDbType.DateTime);
                cmd.Parameters.Add("@sess_name", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@sess_tags", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@sess_desc", SqlDbType.VarChar, 100);

                SqlParameter sessionIdParameter =
                    new SqlParameter("@sess_id", SqlDbType.Int);
                sessionIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(sessionIdParameter);
                SqlParameter resultParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultParameter);
                userName = userName.Substring(userName.LastIndexOf('\\')+1);
                cmd.Parameters["@sess_user"].Value = userName;
                cmd.Parameters["@sess_lab"].Value = labID;
                cmd.Parameters["@mk_name"].Value = motionKindName;
                cmd.Parameters["@sess_date"].Value = sessionDate.Date;
                cmd.Parameters["@sess_name"].Value = sessionName;
                cmd.Parameters["@sess_tags"].Value = tags;
                cmd.Parameters["@sess_desc"].Value = sessionDescription;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                result = (int) resultParameter.Value;
                if(result==1) {
                       UpdateException exc = new UpdateException("Wrong motion kind name" , "Motion kind"+motionKindName+" not found");
                        throw new FaultException<UpdateException>(exc, "Illegal motion kind", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSession")));
                   }
                else if(result==2) {
                       UpdateException exc = new UpdateException("DB-side" , "DB-side failure");
                        throw new FaultException<UpdateException>(exc, "DB-side failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSession")));
                   }

                newSessionId = (int)sessionIdParameter.Value;



            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure: " + ex.Message + " for user: " + OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name, FaultCode.CreateReceiverFaultCode(new FaultCode("CreateSession")));

            }
            finally
            {
                CloseConnection();
            }
            foreach (int sg in sessionGroupIDs) AssignSessionToGroup(newSessionId, sg);

            return newSessionId;

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public int CreateTrial(int sessionID, string trialName, string trialDescription)
        {
            int newTrialId = 0;
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Obserwacja ( IdSesja, Opis_obserwacji, Nazwa)
                                    values (@trial_session, @trial_desc, @trial_name )
                                            set @trial_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@trial_session", SqlDbType.Int);
                cmd.Parameters.Add("@trial_name", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@trial_desc", SqlDbType.VarChar, 100);

                SqlParameter trialIdParameter =
                    new SqlParameter("@trial_id", SqlDbType.Int);
                trialIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(trialIdParameter);
                cmd.Parameters["@trial_session"].Value = sessionID;
                cmd.Parameters["@trial_name"].Value = trialName;
                cmd.Parameters["@trial_desc"].Value = trialDescription;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newTrialId = (int)trialIdParameter.Value;
            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("CreateTrial")));

            }
            finally
            {
                CloseConnection();
            }

            return newTrialId;

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public int CreateMeasurementConfiguration(string mcName, string mcKind, string mcDescription)
        {
            int newMeasurementConf = 0;
            try
            {
                OpenConnection();
                cmd.CommandText = @"insert into Konfiguracja_pomiarowa ( Nazwa, Opis, Rodzaj)
                                    values (@mc_name, @mc_desc, @mc_kind )
                                            set @mc_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@mc_name", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@mc_kind", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@mc_desc", SqlDbType.VarChar, 255);

                SqlParameter mcIdParameter =
                    new SqlParameter("@mc_id", SqlDbType.Int);
                mcIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(mcIdParameter);
                cmd.Parameters["@mc_name"].Value = mcName;
                cmd.Parameters["@mc_kind"].Value = mcKind;
                cmd.Parameters["@mc_desc"].Value = mcDescription;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
                newMeasurementConf = (int)mcIdParameter.Value;
            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Measurement configuration creation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateMeasurementConfiguration")));

            }
            finally
            {
                CloseConnection();
            }

            return newMeasurementConf;

        }

        // Group Assignment operations

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("AssignSessionToGroup")));
            }
            finally
            {
                CloseConnection();
            }
            result = true;
            return result;
        }

        // Performer to session assignment
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public int AssignPerformerToSession(int sessionID, int performerID)
        {
            int newPerfConfId = 0;
            int res = 0;

            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {

                OpenConnection();
                cmd.CommandText = "assign_performer_to_session";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@sess_id", SqlDbType.Int);
                cmd.Parameters.Add("@perf_id", SqlDbType.Int);
                SqlParameter pcIdParameter =
                    new SqlParameter("@perf_conf_id", SqlDbType.Int);
                pcIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(pcIdParameter);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@sess_id"].Value = sessionID;
                cmd.Parameters["@perf_id"].Value = performerID;
                cmd.ExecuteNonQuery();
                res = (int)resultCodeParameter.Value;
                if(res == 0) newPerfConfId = (int) pcIdParameter.Value;
            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "UpdateException");
            }
            finally
            {
                CloseConnection();
            }
            if (res == 1)
            {
                UpdateException exc = new UpdateException("privilege", "Session not available");
                throw new FaultException<UpdateException>(exc, "UpdateException");

            }
            if (res == 2)
            {
                UpdateException exc = new UpdateException("parameter", "Performer not found");
                throw new FaultException<UpdateException>(exc, "UpdateException");
            }

            if (res == 9)
            {
                UpdateException exc = new UpdateException("authorization", "Unknown user");
                throw new FaultException<UpdateException>(exc, "UpdateException");

            } 
            return newPerfConfId;
        }

       

        // Attribute update operations

        /*
        The result value code meaning:
        0 - attribute value set successfully
        1 - attribute of this name is not applicable to session
        2 - the value provided is not valid for this enum-type attribute
        3 - session of given session id does not exist
        4 - (not assigned)
        5 - value of this attribute for this session exists, while you called this operation in "no overwrite" mode
        6 - the value provided is not valid for this numeric-type attribute
        7 - other exception
        */
        // SECURE ME !!!
        public void SetPerformerAttribute(int performerID, string attributeName, string attributeValue, bool update)
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if(resultCode!=0){
                UpdateException exc;
                string resName = "performer";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to "+resName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value "+attributeValue+" is not valid for the enum-type attribute "+attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the "+resName+" of ID " + performerID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute "+attributeName+" for this "+resName+ " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value "+attributeValue+" provided is not valid for this numeric-type attribute "+attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));

                }
                }

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
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public void SetSessionAttribute(int sessionID, string attributeName, string attributeValue, bool update)
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                string resName = "session";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to " + resName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + sessionID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetSessionAttribute")));

                }
            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
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
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));
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
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + trialID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetTrialAttribute")));

                }
            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public void SetMeasurementConfAttribute(int measurementConfID, string attributeName, string attributeValue, bool update)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "set_measurement_conf_attribute";
                cmd.Parameters.Add("@mc_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@mc_id"].Value = measurementConfID;
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
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                string resName = "measurement_conf";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to " + resName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + measurementConfID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetMeasurementConfAttribute")));

                }
            }

        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
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
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));
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
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + performerConfID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerConfAttribute")));

                }
            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public void SetFileAttribute(int fileID, string attributeName, string attributeValue, bool update)
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                string resName = "file";

                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Invalid attribute", "Attribute of name " + attributeName + " is not applicable to " + resName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + fileID + "not found");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 5:
                        exc = new UpdateException("Value already exists", "value of attribute " + attributeName + " for this " + resName + " already exists, while you called this operation in no overwrite mode");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 6:
                        exc = new UpdateException("Invalid numeric value", "the value " + attributeValue + " provided is not valid for this numeric-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));

                }
            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public void ClearAttributeValue(int resourceID, string attributeName, string entity)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "clear_attribute_value";
                cmd.Parameters.Add("@res_id", SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters["@res_id"].Value = resourceID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@entity"].Value = entity;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("unknown", "Update failed: "+ex.Message);
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("ClearAttributeValue")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                exc = new UpdateException("Resource unavailable", "Resource does not exist or no authorization to update");
                throw new FaultException<UpdateException>(exc, "Cannot update this resource", FaultCode.CreateReceiverFaultCode(new FaultCode("ClearAttributeValue")));

            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionOperators")]
        public void SetFileTypedAttributeValue(int resourceID, string entity, string attributeName, int fileID, bool update)
        {
            // UWAGA: nie dopuszczono mozliwosci wprowadzania atrybutow plikowych dla encji PLIK !
            string operationName = "", paramName = "";
            try
            {
                switch (entity)
                {
                    case "performer":
                        operationName = "set_performer_attribute";
                        paramName = "@perf_id";
                        break;
                    case "session":
                        operationName = "set_session_attribute";
                        paramName = "@sess_id";
                        break;
                    case "trial":
                        operationName = "set_trial_attribute";
                        paramName = "@trial_id";
                        break;
                    case "measurement":
                        operationName = "set_measurement_attribute";
                        paramName = "@meas_id";
                        break;
                    case "measurement_conf":
                        operationName = "set_measurement_conf_attribute";
                        paramName = "@mc_id";
                        break;
                    case "performer_conf":
                        operationName = "set_performer_conf_attribute";
                        paramName = "@pc_id";
                        break;
                }
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = operationName;
                cmd.Parameters.Add(paramName, SqlDbType.Int);
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@attr_value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@update", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);

                cmd.Parameters[paramName].Value = resourceID;
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@attr_value"].Value = fileID.ToString();
                cmd.Parameters["@update"].Value = update?1:0;

                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("database", "Database-side failure");
                throw new FaultException<UpdateException>(exc, "Update invocation failed: "+ex.Message, FaultCode.CreateReceiverFaultCode(new FaultCode("SetFileTypedAttributeValue")));

            }
            finally
            {
                CloseConnection();
            }
        }

    }
}
