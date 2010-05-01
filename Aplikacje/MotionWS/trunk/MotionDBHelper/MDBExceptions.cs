using System;
using System.Runtime.Serialization;


namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IBasicQueriesWS" here, you must also update the reference to "IBasicQueriesWS" in Web.config.
    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
    public class QueryException
    {
        string _issue_kind;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _issue_kind; }
            set { _issue_kind = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public QueryException(string res, string det)
        {
            _issue_kind = res;
            _details = det;
        }
    }

}