using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;

namespace MotionDBWebServices
{
    [XmlSchemaProvider("MySchema")]
    public class PerformerSessionListXML : XmlDocument, IXmlSerializable
    {
        private const string ns = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService";

        public PerformerSessionListXML() { }

        public static XmlQualifiedName MySchema(XmlSchemaSet xs)
        {
            // This method is called by the framework to get the schema for this type.
            // We return an existing schema from disk.

            XmlSerializer schemaSerializer = new XmlSerializer(typeof(XmlSchema));
            string xsdPath = null;
            // NOTE: replace the string with your own path.
            xsdPath = System.Web.HttpContext.Current.Server.MapPath("res/PerformerSessionList.xsd");
            XmlSchema s = (XmlSchema)schemaSerializer.Deserialize(
                new XmlTextReader(xsdPath), null);
            xs.XmlResolver = new XmlUrlResolver();
            xs.Add(s);

            return new XmlQualifiedName("PerformerSessionList", ns);
        }

        void IXmlSerializable.WriteXml(System.Xml.XmlWriter writer)
        {
            throw new System.NotImplementedException();
        }
        System.Xml.Schema.XmlSchema IXmlSerializable.GetSchema()
        {
            throw new System.NotImplementedException();
        }

        void IXmlSerializable.ReadXml(System.Xml.XmlReader reader)
        {
            throw new System.NotImplementedException();

        }



    }
}
