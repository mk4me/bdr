using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MotionDBHelper
{
    public struct SessionDetails
    {
        public int SessionId;
        public int UserId;
        public int LabId;
        public int MotionKindId;
        public int PerformerId;
        public DateTime SessionDate;
        public string SessionDescription;
    }
}