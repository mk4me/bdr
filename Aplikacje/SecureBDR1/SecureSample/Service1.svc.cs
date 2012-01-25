using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace SecureSample
{
    // NOTE: If you change the class name "Service1" here, you must also update the reference to "Service1" in Web.config and in the associated .svc file.
    [ServiceBehavior(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/SecureSample/Service1")]
    public class Service1 : IService1
    {
        public string CallMe(int value)
        {


            string username = OperationContext.Current.ServiceSecurityContext.PrimaryIdentity.Name;
            return "Hello user \""+username+"\" you entered the value: "+value;
        }
        /*
        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        } */
    }
}
