using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MotionDBWebServices
{
    public struct PlainSessionDetails
    {
        public int SessionID;
        public int UserID;
        public int LabID;
        public int MotionKindID;
        public int PerformerID;
        public DateTime SessionDate;
        public string SessionDescription;
    }
}