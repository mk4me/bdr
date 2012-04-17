using System;
using System.Runtime.Serialization;


namespace MotionMedDBWebServices
{
    // NOTE: If you change the interface name "IBasicQueriesWS" here, you must also update the reference to "IBasicQueriesWS" in Web.config.
    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/BasicQueriesService")]
    public class QueryException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public QueryException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/BasicUpdatesService")]
    public class UpdateException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public UpdateException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/FileStoremanService")]
    public class FileAccessServiceException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public FileAccessServiceException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionMedDB/AccountFactoryService")]
    public class AccountFactoryException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public AccountFactoryException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")]
    public class AuthorizationException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public AuthorizationException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")]
    public class UPSException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public UPSException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }

    
        [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AdministrationService")]
    public class AdministrationOperationException
    {
        string _fault_source;
        string _details;

        [DataMember]
        public string IssueKind
        {
            get { return _fault_source; }
            set { _fault_source = value; }
        }
        [DataMember]
        public string Details
        {
            get { return _details; }
            set { _details = value; }
        }

        public AdministrationOperationException(string src, string det)
        {
            _fault_source = src;
            _details = det;
        }
    }
}