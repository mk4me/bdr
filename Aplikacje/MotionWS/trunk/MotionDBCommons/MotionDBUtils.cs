using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Text;


namespace MotionDBCommons
{
    public class MotionDBUtils
    {
        public string ProduceRandomCode(int len)
        {
            Random r = new Random();
            StringBuilder b = new StringBuilder();

            for (int i = 0; i < len; i++) b.Append(Convert.ToChar(Convert.ToInt32(Math.Floor(26 * r.NextDouble() + 65))));
            return b.ToString();
        }
    }
}
