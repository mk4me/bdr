using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;
using System.Data;
using System.Data.SqlClient;
using System.Security.Permissions;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "UserPersonalSpace" here, you must also update the reference to "UserPersonalSpace" in Web.config.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")]

    public class UserPersonalSpace : DatabaseAccessService, IUserPersonalSpaceWS
    {


        public void UpdateStoredFilters(FilterPredicateCollection filter)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "update_stored_filters";
                SqlParameter usernamePar = cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                usernamePar.Direction = ParameterDirection.Input;
                usernamePar.Value = userName;
                SqlParameter filterPar = cmd.Parameters.Add("@filter", SqlDbType.Structured);
                filterPar.Direction = ParameterDirection.Input;
                filterPar.Value = filter;
                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                QueryException exc = new QueryException("unknown", "Filter saving error");
                throw new FaultException<QueryException>(exc, "Filter saving failure", FaultCode.CreateReceiverFaultCode(new FaultCode("UpdateStoredFilters")));
            }
            finally
            {
                CloseConnection();
            }
        }


        public XmlElement ListStoredFilters()
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_user_filters_xml";
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
                    xd.AppendChild(xd.CreateElement("FilterList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("unknown", "Filter retrieval error");
                throw new FaultException<UPSException>(exc, "Filter retrieval failed at DB layer", FaultCode.CreateReceiverFaultCode(new FaultCode("ListStoredFilters")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }


        public XmlElement ListUserBaskets()
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_user_baskets";
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
                    xd.AppendChild(xd.CreateElement("BasketDefinitionList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("unknown", "Basket list retrieval error");
                throw new FaultException<UPSException>(exc, "Basket list retrieval failed at DB layer", FaultCode.CreateReceiverFaultCode(new FaultCode("ListUserBaskets")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }


        public void CreateBasket(string basketName)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int res = 0;
            try
            {
                    OpenConnection();
                    cmd.CommandText = @"create_basket";
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                    SqlParameter resultCodeParameter = new SqlParameter("@result", SqlDbType.Int);
                    resultCodeParameter.Direction = ParameterDirection.Output;
                    cmd.Parameters.Add(resultCodeParameter);
                    cmd.Parameters["@user_login"].Value = userName;
                    cmd.Parameters["@basket_name"].Value = basketName;
                    cmd.ExecuteNonQuery();
                    res = (int)resultCodeParameter.Value;

                }
                catch (SqlException ex)
                {
                    UpdateException exc = new UpdateException("unknown", "Update failed");
                    throw new FaultException<UpdateException>(exc, "DB layer failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateBasket")));
                }
                finally
                {
                    CloseConnection();
                }
            if (res != 0)
            {
                string cause = "";
                switch (res)
                {
                    case 1:
                        cause = "User unknown";
                        break;
                    case 2:
                        cause = "Basket of name "+ basketName +" already exists";
                        break;
                    default:
                        cause = "Unknown DB-side fault";
                        break;
                }

                UPSException exc = new UPSException("illegal parameter", cause);
                throw new FaultException<UPSException>(exc, "Invalid parameters", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateBasket")));
            }        
        }


        public void RemoveBasket(string basketName)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int res = 0;
            try
            {
                    OpenConnection();
                    cmd.CommandText = @"remove_basket";
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                    cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                    SqlParameter resultCodeParameter = new SqlParameter("@result", SqlDbType.Int);
                    resultCodeParameter.Direction = ParameterDirection.Output;
                    cmd.Parameters.Add(resultCodeParameter);
                    cmd.Parameters["@user_login"].Value = userName;
                    cmd.Parameters["@basket_name"].Value = basketName;
                    cmd.ExecuteNonQuery();
                    res = (int)resultCodeParameter.Value;

                }
                catch (SqlException ex)
                {
                    UpdateException exc = new UpdateException("unknown", "Update failed");
                    throw new FaultException<UpdateException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateBasket")));
                }
                finally
                {
                    CloseConnection();
                }
            if (res != 0)
            {
                string cause = "";
                switch (res)
                {
                    case 1:
                        cause = "User unknown";
                        break;
                    case 2:
                        cause = "Basket of name " + basketName + " does not exist";
                        break;
                    default:
                        cause = "Unknown DB-side fault";
                        break;
                }

                UPSException exc = new UPSException("illegal parameter", cause);
                throw new FaultException<UPSException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("CreateBasket")));
            }        
        }



        public void AddEntityToBasket(string basketName, int resourceID, string entity)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int res = 0;
            try
            {
                OpenConnection();
                cmd.CommandText = @"add_entity_to_basket";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@res_id", SqlDbType.Int);
                SqlParameter resultCodeParameter = new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                cmd.Parameters["@entity"].Value = entity;
                cmd.Parameters["@res_id"].Value = resourceID;
                cmd.ExecuteNonQuery();
                res = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Basket update failed at DB layer", FaultCode.CreateReceiverFaultCode(new FaultCode("AddEntityToBasket")));
            }
            finally
            {
                CloseConnection();
            }
            if (res != 0)
            {
                string cause = "";
                switch (res)
                {
                    case 1:
                        cause = "User unknown";
                        break;
                    case 2:
                        cause = "Basket of name " + basketName + " does not exist";
                        break;
                    case 3:
                        cause = "Resource " + entity + " not available to user " + userName;
                        break;
                    default:
                        cause = "Unknown DB-side fault";
                        break;
                }

                UPSException exc = new UPSException("illegal parameter", cause);
                throw new FaultException<UPSException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("AddEntityToBasket")));
            }
        }

        public void RemoveEntityFromBasket(string basketName, int resourceID, string entity)
        {
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);
            int res = 0;
            try
            {
                OpenConnection();
                cmd.CommandText = @"remove_entity_from_basket";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@entity", SqlDbType.VarChar, 20);
                cmd.Parameters.Add("@res_id", SqlDbType.Int);
                SqlParameter resultCodeParameter = new SqlParameter("@result", SqlDbType.Int);
                resultCodeParameter.Direction = ParameterDirection.Output;
                cmd.Parameters.Add(resultCodeParameter);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                cmd.Parameters["@entity"].Value = entity;
                cmd.Parameters["@res_id"].Value = resourceID;
                cmd.ExecuteNonQuery();
                res = (int)resultCodeParameter.Value;

            }
            catch (SqlException ex)
            {
                UpdateException exc = new UpdateException("unknown", "Update failed");
                throw new FaultException<UpdateException>(exc, "Basket update failed at DB layer", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveEntityFromBasket")));
            }
            finally
            {
                CloseConnection();
            }
            if (res != 0)
            {
                string cause = "";
                switch (res)
                {
                    case 1:
                        cause = "User unknown";
                        break;
                    case 2:
                        cause = "Basket of name " + basketName + " does not exist";
                        break;
                    default:
                        cause = "Unknown DB-side fault";
                        break;
                }

                UPSException exc = new UPSException("illegal parameter", cause);
                throw new FaultException<UPSException>(exc, "Update invocation failure", FaultCode.CreateReceiverFaultCode(new FaultCode("RemoveEntityFromBasket")));
            }
        }

        public XmlElement ListBasketPerformersWithAttributesXML(string basketName)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_basket_performers_attributes_xml";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("BasketPerformerWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("database error", "Basked content could not be retrieved");
                throw new FaultException<UPSException>(exc, "Basked content could not be retrieved", FaultCode.CreateReceiverFaultCode(new FaultCode("ListBasketPerformersWithAttributesXML")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }

        public XmlElement ListBasketSessionsWithAttributesXML(string basketName)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_basket_sessions_attributes_xml";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("BasketSessionWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("database error", "Basked content could not be retrieved");
                throw new FaultException<UPSException>(exc, "Basked content could not be retrieved", FaultCode.CreateReceiverFaultCode(new FaultCode("ListBasketSessionsWithAttributesXML")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }


        public XmlElement ListBasketTrialsWithAttributesXML(string basketName)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_basket_trials_attributes_xml";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("BasketTrialWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("database error", "Basked content could not be retrieved");
                throw new FaultException<UPSException>(exc, "Basked content could not be retrieved", FaultCode.CreateReceiverFaultCode(new FaultCode("ListBasketTrialsWithAttributesXML")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }          

        public XmlElement ListBasketSegmentsWithAttributesXML(string basketName)
        {
            XmlDocument xd = new XmlDocument();
            string userName = OperationContext.Current.ServiceSecurityContext.WindowsIdentity.Name;
            userName = userName.Substring(userName.LastIndexOf('\\') + 1);

            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "list_basket_segments_attributes_xml";
                cmd.Parameters.Add("@user_login", SqlDbType.VarChar, 30);
                cmd.Parameters.Add("@basket_name", SqlDbType.VarChar, 30);
                cmd.Parameters["@user_login"].Value = userName;
                cmd.Parameters["@basket_name"].Value = basketName;
                XmlReader dr = cmd.ExecuteXmlReader();
                if (dr.Read())
                {
                    xd.Load(dr);
                }
                if (xd.DocumentElement == null)
                {
                    xd.AppendChild(xd.CreateElement("BasketSegmentWithAttributesList", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"));
                }
                dr.Close();
            }
            catch (SqlException ex)
            {
                UPSException exc = new UPSException("database error", "Basked content could not be retrieved");
                throw new FaultException<UPSException>(exc, "Basked content could not be retrieved", FaultCode.CreateReceiverFaultCode(new FaultCode("ListBasketSegmentsWithAttributesXML")));
            }
            finally
            {
                CloseConnection();
            }

            return xd.DocumentElement;
        }


    }
}
