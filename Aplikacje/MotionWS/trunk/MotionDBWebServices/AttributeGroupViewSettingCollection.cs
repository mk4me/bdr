using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using Microsoft.SqlServer.Server;

namespace MotionDBWebServices
{
    public class AttributeGroupViewSettingCollection : List<AttributeGroupViewSetting>, IEnumerable<SqlDataRecord>
    {
       IEnumerator<SqlDataRecord> IEnumerable<SqlDataRecord>.GetEnumerator()
        {
            var sdr = new SqlDataRecord(
                    new SqlMetaData("AttributeGroupName", SqlDbType.VarChar, 100),
                    new SqlMetaData("DescribedEntity", SqlDbType.VarChar, 20),
                    new SqlMetaData("Show", SqlDbType.Bit) );
            foreach (AttributeGroupViewSetting fp in this)
              {
                  sdr.SetString(0, fp.AttributeGroupName);
                  sdr.SetString(1, fp.DescribedEntity);
                  sdr.SetBoolean(2, fp.Show);
                  yield return sdr;
              }

        }

    }
}
