using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Xml;

namespace MotionDBWebServices
{
    [ServiceBehavior (Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
    public class BasicQueriesWS : DatabaseAccessService, IBasicQueriesWS
    {
        public  XmlElement GetPerformerByIdXML(int id)
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
                // report exception
            }
            CloseConnection();
            if (notFound)
            {
                QueryException exc = new QueryException("Wrong value", "The id provided does not match any performer");
                throw new FaultException<QueryException>(exc, "ID not found", FaultCode.CreateReceiverFaultCode(new FaultCode("GetPerformerById")));
            }
            //if (xd.DocumentElement == null) xd.AppendChild(xd.CreateElement("PerformerDetailsWithAttributes", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService"));
            return xd.DocumentElement;
        }

    }



}
