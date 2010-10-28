using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using Microsoft.SqlServer.Server;

namespace MotionDBWebServices
{
    public class AttributeViewSettingCollection : List<AttributeViewSetting>, IEnumerable<SqlDataRecord>
    {
        IEnumerator<SqlDataRecord> IEnumerable<SqlDataRecord>.GetEnumerator()
        {
            var sdr = new SqlDataRecord(
                    new SqlMetaData("AttributeName", SqlDbType.VarChar, 100),
                    new SqlMetaData("DescribedEntity", SqlDbType.VarChar, 20),
                    new SqlMetaData("Show", SqlDbType.Bit));
            foreach (AttributeViewSetting fp in this)
            {
                sdr.SetString(0, fp.AttributeName);
                sdr.SetString(1, fp.DescribedEntity);
                sdr.SetBoolean(2, fp.Show);
                yield return sdr;
            }

        }

    }
}
