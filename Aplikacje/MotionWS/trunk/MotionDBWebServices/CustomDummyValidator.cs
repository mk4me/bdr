using System;
using System.ServiceModel;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IdentityModel.Selectors;
using System.IdentityModel.Tokens;
using System.Data;
using System.Data.SqlClient;
using MotionDBCommons;

namespace MotionDBWebServices
{


    public class CustomDummyValidator : System.IdentityModel.Selectors.UserNamePasswordValidator
    {
 

        public override void Validate(string userName, string password)
        {
            if (userName == null || password == null)
            {
                throw new ArgumentNullException();
            }
            if (!(userName == "hmdbServiceUser" && password == "4accountCreation"))
            {
                throw new SecurityTokenException("Invalid Username or Password");
            } 


        }

    }

}
