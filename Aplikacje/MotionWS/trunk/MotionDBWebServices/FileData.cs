using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MotionDBWebServices
{
    public struct FileData
    {
        public string FileLocation;
        public string SubdirPath;
    }

    public struct FileByteData
    {
        public string FileName;
        public byte[] FileData;
    }
}
