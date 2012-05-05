using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;
using System.IO;
using MotionDBCommons;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "AdministrationWS" here, you must also update the reference to "AdministrationWS" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AdministrationService")]
    public class AdministrationWS : DatabaseAccessService, IAdministrationWS
    {

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void DefineAttriubeGroup(string groupName, string entity)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "define_attribute_group";
                cmd.Parameters.Add("@group_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@group_name"].Value = groupName;
                cmd.Parameters["@entity"].Value = entity;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                // log the exception
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttriubeGroup")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Name in use", "Group of this name already exists");
                        throw new FaultException<UpdateException>(exc, "This group name cannot be used", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttriubeGroup")));
                    case 2:
                        exc = new UpdateException("Wrong entity name", "Entity name wrong or entity does not support generic attributes");
                        throw new FaultException<UpdateException>(exc, "Wrong entity name", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttriubeGroup")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttriubeGroup")));

                }
            }

        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void RemoveAttributeGroup(string groupName, string entity)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "remove_attribute_group";
                cmd.Parameters.Add("@group_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@group_name"].Value = groupName;
                cmd.Parameters["@entity"].Value = entity;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttriubeGroup")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Name not found", "Group of this name not found");
                        throw new FaultException<UpdateException>(exc, "This group name could not be found", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttriubeGroup")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttriubeGroup")));

                }
            }

        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void DefineAttribute(string attributeName, string groupName, string entity, bool isEnum, string pluginDescriptor, 
            string type, string unit)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "define_attribute";
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@group_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@is_enum", SqlDbType.Bit);
                cmd.Parameters.Add("@plugin_desc", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@type", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@unit", SqlDbType.VarChar, 10);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@group_name"].Value = groupName;
                cmd.Parameters["@entity"].Value = entity;
                cmd.Parameters["@is_enum"].Value = isEnum ? 1 : 0;
                cmd.Parameters["@plugin_desc"].Value = pluginDescriptor;
                cmd.Parameters["@type"].Value = type;
                cmd.Parameters["@unit"].Value = unit;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Attriubte exists", "Attribute of this group and name already defined");
                        throw new FaultException<UpdateException>(exc, "Attribute already exists", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttribute")));

                    case 2:
                        exc = new UpdateException("Wrong group", "Group of this name could not be located");
                        throw new FaultException<UpdateException>(exc, "Could not find the group specified", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttribute")));

                    case 3:
                        exc = new UpdateException("Wrong type", "Storage type not supported: use integer, fload or string");
                        throw new FaultException<UpdateException>(exc, "Wrong storage type", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("DefineAttribute")));

                }
            }

        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void RemoveAttribute(string attributeName, string groupName, string entity)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "remove_attribute";
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@group_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@entity"].Value = entity;
                cmd.Parameters["@group_name"].Value = groupName;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttribute")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Attriubte not found", "Attribute of this group and name not found");
                        throw new FaultException<UpdateException>(exc, "Attribute not found", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttribute")));

                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveAttribute")));

                }
            }

        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void AddAttributeEnumValue(string attributeName, string groupName, string entity, string value, bool clearExisting)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "add_attribute_enum_value";
                cmd.Parameters.Add("@attr_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@group_name", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@value", SqlDbType.VarChar, 100);
                cmd.Parameters.Add("@replace_all", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@attr_name"].Value = attributeName;
                cmd.Parameters["@group_name"].Value = groupName;
                cmd.Parameters["@entity"].Value = entity;
                cmd.Parameters["@value"].Value = value;
                cmd.Parameters["@replace_all"].Value = clearExisting?1:0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("AddAttributeEnumValue")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Attriubte not found", "Attribute of this group and name not found");
                        throw new FaultException<UpdateException>(exc, "Attribute not found", FaultCode.CreateReceiverFaultCode(new FaultCode("AddAttributeEnumValue")));
                    case 2:
                        exc = new UpdateException("Non-enum attribute", "Attribute not declared as enum-typed");
                        throw new FaultException<UpdateException>(exc, "Not an enum attribute", FaultCode.CreateReceiverFaultCode(new FaultCode("AddAttributeEnumValue")));


                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("AddAttributeEnumValue")));

                }
            }

        }

        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void AlterUserToUserGroupAssignment(int userID, int userGroupID, bool assign)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "assign_user_to_user_group";
                cmd.Parameters.Add("@user_id", SqlDbType.Int);
                cmd.Parameters.Add("@user_group_id", SqlDbType.Int);
                cmd.Parameters.Add("@assigned", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@user_id"].Value = userID;
                cmd.Parameters["@user_group_id"].Value = userGroupID;
                cmd.Parameters["@assigned"].Value = assign ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserToUserGroupAssignment")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("User not found", "User of a given ID not found");
                        throw new FaultException<UpdateException>(exc, "User not found", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserToUserGroupAssignment")));
                    case 2:
                        exc = new UpdateException("User group not found", "User group of a given ID not found");
                        throw new FaultException<UpdateException>(exc, "User group not found", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserToUserGroupAssignment")));


                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserToUserGroupAssignment")));

                }
            }
        }


        [PrincipalPermission(SecurityAction.Demand, Role = @"motion_administrators")]
        public void AlterUserGroupToSessionGroupAssignment(int userGroupID, int sessionGroupID, bool assign)
        {
            int resultCode = 0;

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "AlterUserGroupToSessionGroupAssignment";
                cmd.Parameters.Add("@session_group_id", SqlDbType.Int);
                cmd.Parameters.Add("@user_group_id", SqlDbType.Int);
                cmd.Parameters.Add("@assigned", SqlDbType.Bit);
                SqlParameter resultCodeParameter =
                    new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@session_group_id"].Value = sessionGroupID;
                cmd.Parameters["@user_group_id"].Value = userGroupID;
                cmd.Parameters["@assigned"].Value = assign ? 1 : 0;

                cmd.ExecuteNonQuery();
                resultCode = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("Database fault", "Unknown database fault");
                throw new FaultException<UpdateException>(exc, "Unknown database fault", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserGroupToSessionGroupAssignment")));
            }
            finally
            {
                CloseConnection();
            }
            if (resultCode != 0)
            {
                UpdateException exc;
                switch (resultCode)
                {
                    case 1:
                        exc = new UpdateException("Session group not found", "Session group of a given ID not found");
                        throw new FaultException<UpdateException>(exc, "Session group not found", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserGroupToSessionGroupAssignment")));
                    case 2:
                        exc = new UpdateException("User group not found", "User group of a given ID not found");
                        throw new FaultException<UpdateException>(exc, "User group not found", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserGroupToSessionGroupAssignment")));


                    default:
                        exc = new UpdateException("Unknown error", "unknown error occured");
                        throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("AlterUserGroupToSessionGroupAssignment")));

                }
            }
        }



        [PrincipalPermission(SecurityAction.Demand, Role = "motion_administrators")]
        public void DownloadAreaCleanup(int olderThanMinutes)
        {
            string fileLocation = "";



            try
            {


                OpenConnection();
                cmd.CommandText = @"select Lokalizacja from Plik_udostepniony where DATEDIFF( MINUTE, Data_udostepnienia, GETDATE()) > @minutes";
                cmd.Parameters.Add("@minutes", SqlDbType.Int);
                cmd.Parameters["@minutes"].Value = olderThanMinutes;

                SqlDataReader reader = cmd.ExecuteReader();



                while (reader.Read())
                {
                    fileLocation = (string)reader.GetValue(0);
                    if (Directory.Exists(baseLocalFilePath + fileLocation))
                        Directory.Delete(baseLocalFilePath + fileLocation, true);

                }
                reader.Close();

                cmd.Parameters.Clear();
                cmd.Parameters.Add("@minutes", SqlDbType.Int);
                cmd.Parameters["@minutes"].Value = olderThanMinutes;
                cmd.CommandText = @"delete from Plik_udostepniony 
                                        where DATEDIFF( MINUTE, Data_udostepnienia, GETDATE()) > @minutes + 1";
                cmd.ExecuteNonQuery();

            }
            catch (SqlException ex)
            {
                // log the exception
                FileAccessServiceException exc = new FileAccessServiceException("database", "Cleanup failed at database layer");
                throw new FaultException<FileAccessServiceException>(exc, "SQL error", FaultCode.CreateReceiverFaultCode(new FaultCode("DownloadAreaCleanup")));


            }
            finally
            {
                CloseConnection();
            }

        }

    }
}
