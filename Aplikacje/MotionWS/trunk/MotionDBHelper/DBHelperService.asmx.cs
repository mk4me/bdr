using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace MotionDBHelper
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/DBHelperService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class DBHelperService : System.Web.Services.WebService
    {

        [WebMethod]
        public int CreatePerformer(PerformerData pd)
        {
            return 0;
        }
        // todo: update performerdata fields. Checki SQL - CS primitive type compliance

        [WebMethod]
        public int CreateSession(int performerId, int[] sessionGroupIds, SessionData sd)
        {
            return 0;
        }

        [WebMethod]
        public int CreateTrial(int sessionId, TrialData td)
        {
            return 0;
        }

        [WebMethod]
        public bool AssignSessionToGroup(int sessionId, int groupId)
        {
            return false;
        }

        [WebMethod]
        public bool SetSessionAttribute(int sessionId, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        }        

        [WebMethod]
        public bool SetTrialAttribute(int trialId, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        }

        [WebMethod]
        public bool SetPerformerAttribute(int performerId, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        } 

        [WebMethod]
        public bool SetFileAttribute(int fileId, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        } 

        [WebMethod]
        public int _PerformGenericQuery(string query)
        {
            return 0;
        }


    }
}
