using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using MotionDBCommons;

// TODO: wyodrebnic wspolne elementy projektowo BDR i mDBR

namespace MotionMedDBWebServices
{
    public class MedDatabaseAccessService : DatabaseAccessService
    {

        protected override string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion_Med";
        }


        public override void OpenConnection()
        {
            conn = new SqlConnection(@"server = .; integrated security = true; database = Motion_Med");
            conn.Open();
            cmd = conn.CreateCommand();
        }



    }
}
