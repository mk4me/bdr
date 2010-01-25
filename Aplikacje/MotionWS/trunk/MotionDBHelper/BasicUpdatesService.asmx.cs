using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace MotionDBWebServices
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class BasicUpdatesService : System.Web.Services.WebService
    {

        [WebMethod]
        public int CreatePerformer(PerformerData performerData)
        {
            return 0;
        }
        // todo: update performerdata fields. Checki SQL - CS primitive type compliance

        [WebMethod]
        public int CreateSession(int performerID, int[] sessionGroupIDs, SessionData sessionData)
        {
            return 0;
        }

        [WebMethod]
        public int CreateTrial(int sessionID, TrialData trialData)
        {
            return 0;
        }

        [WebMethod]
        public bool AssignSessionToGroup(int sessionID, int groupID)
        {
            return false;
        }

        [WebMethod]
        public bool SetPerformerAttribute(int performerID, int attributeID, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        } 
        [WebMethod]
        public bool SetSessionAttribute(int sessionID, int attributeID, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        }        

        [WebMethod]
        public bool SetTrialAttribute(int trialID, int attributeID, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        }


        [WebMethod]
        public bool SetFileAttribute(int fileID, int attributeId, string attributeValue)
        {
            /* Type checking based on attr type declared in the database - to be performed here.
             Respectively: string, integer or float needs to be provided. */
            return false;

        } 


    }
}
