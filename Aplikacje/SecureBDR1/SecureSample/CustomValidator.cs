using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ServiceModel;
using System.IdentityModel.Selectors;
using System.IdentityModel.Tokens;

namespace SecureSample
{
    public class CustomValidator : System.IdentityModel.Selectors.UserNamePasswordValidator
    {
        public override void Validate(string userName, string password)
        {
            if (userName == null || password == null)
            {
                throw new ArgumentNullException();
            }
            if (!(userName == "hml" && password == "hml"))
            {
                throw new SecurityTokenException("Unknown Username or Password");
            }
        }

    }
}
