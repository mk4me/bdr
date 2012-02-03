using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using Microsoft.SqlServer.Server;

namespace MotionDBWebServices
{
    public class FileNameEntryCollection : List<FileNameEntry>, IEnumerable<SqlDataRecord>
    {
        IEnumerator<SqlDataRecord> IEnumerable<SqlDataRecord>.GetEnumerator()
        {
            var sdr = new SqlDataRecord(
                    new SqlMetaData("Name", SqlDbType.VarChar, 100));
            foreach (FileNameEntry fn in this)
            {
                sdr.SetString(0, fn.Name);
                yield return sdr;
            }

        }

    }
}
