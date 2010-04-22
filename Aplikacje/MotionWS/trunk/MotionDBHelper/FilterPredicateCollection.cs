using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using Microsoft.SqlServer.Server;

namespace MotionDBWebServices
{
    public class FilterPredicateCollection : List<FilterPredicate>, IEnumerable<SqlDataRecord>
    {
       IEnumerator<SqlDataRecord> IEnumerable<SqlDataRecord>.GetEnumerator()
        {
            var sdr = new SqlDataRecord(
                    new SqlMetaData("PredicateID",SqlDbType.Int),
                    new SqlMetaData("ParentPredicate",SqlDbType.Int),
                    new SqlMetaData("ContextEntity",SqlDbType.VarChar,20),
            	    new SqlMetaData("PreviousPredicate",SqlDbType.Int),
	                new SqlMetaData("NextOperator", SqlDbType.VarChar,5),
	                new SqlMetaData("FeatureName", SqlDbType.VarChar,100),
	                new SqlMetaData("Operator", SqlDbType.VarChar,5),
	                new SqlMetaData("Value", SqlDbType.VarChar,100),
	                new SqlMetaData("AggregateFunction", SqlDbType.VarChar,10),
	                new SqlMetaData("AggregateEntity", SqlDbType.VarChar,20) );
              foreach (FilterPredicate fp in this)
              {
                  sdr.SetInt32(0, fp.PredicateID);
                  sdr.SetInt32(1, fp.ParentPredicate);
                  sdr.SetString(2, fp.ContextEntity );
                  sdr.SetInt32(3, fp.PreviousPredicate);
                  sdr.SetString(4, fp.NextOperator);
                  sdr.SetString(5, fp.FeatureName);
                  sdr.SetString(6, fp.Operator);
                  sdr.SetString(7, fp.Value);
                  sdr.SetString(8, fp.AggregateFunction);
                  sdr.SetString(9, fp.AggregateEntity);

                  yield return sdr;
              }

        }

    }
}
