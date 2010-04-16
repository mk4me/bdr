using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MotionDBWebServices
{
    public struct FilterPredicate
    {
        public int PredicateID; // valid value is 1 or bigger
        public int ParentPredicate; // 0 for top-level predicates; enclosing group ID for nested ones
        public string ContextEntity; // performer, session, trial, segment, file, GROUP
        public int PrecedingPredicate; // optional; if absent - set to 0;  obligatory for GROUP
        public string PrecedingOperator; // valid and required only when NextPredicate is non-zero;
        public string FeatureName; // required, except for GROUP
        public string Operator; // can be empty only for GROUP or for Feature that is boolean-type
        public string Value;
        public string AggregateFunction;
        public string AggregateEntity; // applicable and required only when AggregateFunction is not empty

    }
}