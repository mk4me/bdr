using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;

namespace MotionDBWebServices
{
    public partial class Download : System.Web.UI.Page
    {
        public const int DEFAULT_BUFFER_SIZE = 4096;
        protected void Page_Load(object sender, EventArgs e)
        {
            Context.Response.ContentType = "application/zip";
            Context.Response.AddHeader("Content-disposition", "attachment; filename=Test.zip");

            FileStream stream = File.Open(@"F:\FTPShare\Test.zip", FileMode.Open);

            writeStream(stream);

            Context.Response.End();
        }

        private void writeStream(Stream stream)
        {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

            try
            {
                int bytesRead;
                while ((bytesRead = stream.Read(buffer, 0, buffer.Length)) > 0)
                {
                    Context.Response.OutputStream.Write(buffer, 0, bytesRead);
                }
            }
            finally
            {
                stream.Close();
            }
        }
    }
}