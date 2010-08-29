﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "BasicUpdatesWS" here, you must also update the reference to "BasicUpdatesWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")]
    [ErrorLoggerBehaviorAttribute]
    public class BasicUpdatesWS : DatabaseAccessService, IBasicUpdatesWS
    {

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int CreatePerformer(PerformerData performerData)
        {
            int newPerformerId = 0;

            try
            {

                OpenConnection();
                cmd.CommandText = @"insert into Performer ( Imie, Nazwisko)
                                            values (@perf_name, @perf_surname)
                                            set @perf_id = SCOPE_IDENTITY()";
                cmd.Parameters.Add("@perf_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@perf_surname", SqlDbType.VarChar, 50);
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreatePerformer")));
            }
            finally
            {
                CloseConnection();
            }
            return newPerformerId;

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
        public int CreateSession(int labID, string motionKindName, DateTime sessionDate, string sessionDescription, int[] sessionGroupIDs)
        {
            int newSessionId = 0;
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

                cmd.CommandType = CommandType.Text;


//                @"BEGIN TRY
//    insert into Sesja ( IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, IdPerformer, Data, Opis_sesji)
//                                            values (@sess_user, @sess_lab, (select top(1) IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa = @motion_kind_name), @sess_perf, @sess_date, @sess_desc )
//                                            set @sess_id = SCOPE_IDENTITY()END TRY
//    BEGIN CATCH
//        insert into Blad ( TekstBledu ) 
//        values (' Message: '+ERROR_MESSAGE() )
//    END CATCH;";


                cmd.CommandText = @"BEGIN TRY insert into Sesja ( IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, Data, Opis_sesji)
                                            values ((select top(1) IdUzytkownik from Uzytkownik where Login = @sess_user), @sess_lab, (select top(1) IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa = @motion_kind_name), @sess_date, @sess_desc )
                                            set @sess_id = SCOPE_IDENTITY()     END TRY
BEGIN CATCH
    insert into Blad ( NrBledu, Dotkliwosc, Stan, Procedura, Linia, Komunikat )
    values ( ERROR_NUMBER() , ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE() )
END CATCH;";
                cmd.Parameters.Add("@sess_user", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@sess_lab", SqlDbType.Int);
                cmd.Parameters.Add("@motion_kind_name", SqlDbType.VarChar, 50);
                cmd.Parameters.Add("@sess_date", SqlDbType.DateTime);
                cmd.Parameters.Add("@sess_desc", SqlDbType.VarChar, 100);

                SqlParameter sessionIdParameter =
                    new SqlParameter("@sess_id", SqlDbType.Int);
                sessionIdParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(sessionIdParameter);
                userName = userName.Substring(userName.LastIndexOf('\\')+1);
                cmd.Parameters["@sess_user"].Value = userName;
                cmd.Parameters["@sess_lab"].Value = labID;
                cmd.Parameters["@motion_kind_name"].Value = motionKindName;
                cmd.Parameters["@sess_date"].Value = sessionDate;
                cmd.Parameters["@sess_desc"].Value = sessionDescription;
                cmd.Prepare();
                cmd.ExecuteNonQuery();
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
                cmd.Parameters.Add("@trial_desc", SqlDbType.VarChar, 100);
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
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateTrial")));

            }
            finally
            {
                CloseConnection();
            }

            return newTrialId;

        }

        // Group Assignment operations

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
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
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + sessionID + "not found");
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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
                throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
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
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 2:
                        exc = new UpdateException("Invalid enum value", "the value " + attributeValue + " is not valid for the enum-type attribute " + attributeName);
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("SetPerformerAttribute")));
                    case 3:
                        exc = new UpdateException("Invalid resource ID", "the " + resName + " of ID " + trialID + "not found");
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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

        [PrincipalPermission(SecurityAction.Demand, Role = @"MotionUsers")]
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

    }
}
