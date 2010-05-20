﻿using System;
using System.Runtime.Serialization;


namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IBasicQueriesWS" here, you must also update the reference to "IBasicQueriesWS" in Web.config.
    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")]
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
    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")]
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

    [DataContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")]
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

}