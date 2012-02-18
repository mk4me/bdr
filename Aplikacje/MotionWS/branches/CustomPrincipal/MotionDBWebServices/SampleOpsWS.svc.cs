using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;

namespace MotionDBWebServices
{
    // NOTE: If you change the class name "SampleOpsWS" here, you must also update the reference to "SampleOpsWS" in Web.config.


    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SampleOpsService")]   
    public class SampleOpsWS : ISampleOpsWS
    {

     

        public XmlElement ListPerformersXML()  // UWAGA - moze okazac sie potrzebne filtrowanie performerow wg uprawnien!
        {
            XmlDocument xd = new XmlDocument();
            XmlElement emp1 = xd.CreateElement("PerformerDetails", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SampleOpsService");
            XmlElement emp2 = xd.CreateElement("PerformerDetails", "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SampleOpsService");
            XmlElement list = xd.CreateElement("PerformerList",    "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SampleOpsService");

            emp1.SetAttribute("PerformerID", "1");
            emp1.SetAttribute("FirstName", "Jan");
            emp1.SetAttribute("LastName", "Kowalski");

            emp2.SetAttribute("PerformerID", "2");
            emp2.SetAttribute("FirstName", "Anna");
            emp2.SetAttribute("LastName", "Nowak");

            list.AppendChild(emp1);
            list.AppendChild(emp2);
         

            xd.AppendChild(list);
                                



            return xd.DocumentElement;

        }


    }
}
