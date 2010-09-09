using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml;

namespace MotionDBWebServices
{
    // NOTE: If you change the interface name "IUserPersonalSpace" here, you must also update the reference to "IUserPersonalSpace" in Web.config.
    [ServiceContract(Namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService"), XmlSerializerFormat(Style = OperationFormatStyle.Document, Use = OperationFormatUse.Literal)]
    public interface IUserPersonalSpaceWS
    {
        [OperationContract]
        [FaultContract(typeof(UPSException))]
        void UpdateStoredFilters(FilterPredicateCollection filter);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListStoredFilters();

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListUserBaskets();

        [OperationContract]
        [FaultContract(typeof(UPSException))]
        void CreateBasket(string basketName);

        [OperationContract]
        [FaultContract(typeof(UPSException))]
        void RemoveBasket(string basketName);

        [OperationContract]
        [FaultContract(typeof(UPSException))]
        void AddEntityToBasket(string basketName, int resourceID, string entity);

        [OperationContract]
        [FaultContract(typeof(UPSException))]
        void RemoveEntityFromBasket(string basketName, int resourceID, string entity);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListBasketPerformersWithAttributesXML(string basketName);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListBasketSessionsWithAttributesXML(string basketName);

        [OperationContract]
        [FaultContract(typeof(QueryException))]
        XmlElement ListBasketTrialsWithAttributesXML(string basketName);

    }
}
