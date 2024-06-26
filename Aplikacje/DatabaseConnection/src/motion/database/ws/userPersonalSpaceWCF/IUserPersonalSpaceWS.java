
package motion.database.ws.userPersonalSpaceWCF;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IUserPersonalSpaceWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IUserPersonalSpaceWS {


    /**
     * 
     * @param filter
     * @throws IUserPersonalSpaceWSUpdateStoredFiltersUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "UpdateStoredFilters", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/UpdateStoredFilters")
    @RequestWrapper(localName = "UpdateStoredFilters", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.UpdateStoredFilters")
    @ResponseWrapper(localName = "UpdateStoredFiltersResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.UpdateStoredFiltersResponse")
    public void updateStoredFilters(
        @WebParam(name = "filter", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        ArrayOfFilterPredicate filter)
        throws IUserPersonalSpaceWSUpdateStoredFiltersUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListStoredFiltersResponse.ListStoredFiltersResult
     * @throws IUserPersonalSpaceWSListStoredFiltersQueryExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListStoredFilters", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListStoredFilters")
    @WebResult(name = "ListStoredFiltersResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListStoredFilters", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListStoredFilters")
    @ResponseWrapper(localName = "ListStoredFiltersResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListStoredFiltersResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListStoredFiltersResponse.ListStoredFiltersResult listStoredFilters()
        throws IUserPersonalSpaceWSListStoredFiltersQueryExceptionFaultFaultMessage
    ;

    /**
     * 
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListUserBasketsResponse.ListUserBasketsResult
     * @throws IUserPersonalSpaceWSListUserBasketsQueryExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListUserBaskets", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListUserBaskets")
    @WebResult(name = "ListUserBasketsResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListUserBaskets", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListUserBaskets")
    @ResponseWrapper(localName = "ListUserBasketsResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListUserBasketsResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListUserBasketsResponse.ListUserBasketsResult listUserBaskets()
        throws IUserPersonalSpaceWSListUserBasketsQueryExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param basketName
     * @throws IUserPersonalSpaceWSCreateBasketUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateBasket", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/CreateBasket")
    @RequestWrapper(localName = "CreateBasket", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.CreateBasket")
    @ResponseWrapper(localName = "CreateBasketResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.CreateBasketResponse")
    public void createBasket(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName)
        throws IUserPersonalSpaceWSCreateBasketUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param basketName
     * @throws IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "RemoveBasket", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/RemoveBasket")
    @RequestWrapper(localName = "RemoveBasket", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.RemoveBasket")
    @ResponseWrapper(localName = "RemoveBasketResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.RemoveBasketResponse")
    public void removeBasket(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName)
        throws IUserPersonalSpaceWSRemoveBasketUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param resourceID
     * @param basketName
     * @param entity
     * @throws IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "AddEntityToBasket", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/AddEntityToBasket")
    @RequestWrapper(localName = "AddEntityToBasket", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.AddEntityToBasket")
    @ResponseWrapper(localName = "AddEntityToBasketResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.AddEntityToBasketResponse")
    public void addEntityToBasket(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName,
        @WebParam(name = "resourceID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        int resourceID,
        @WebParam(name = "entity", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String entity)
        throws IUserPersonalSpaceWSAddEntityToBasketUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param resourceID
     * @param basketName
     * @param entity
     * @throws IUserPersonalSpaceWSRemoveEntityFromBasketUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "RemoveEntityFromBasket", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/RemoveEntityFromBasket")
    @RequestWrapper(localName = "RemoveEntityFromBasket", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.RemoveEntityFromBasket")
    @ResponseWrapper(localName = "RemoveEntityFromBasketResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.RemoveEntityFromBasketResponse")
    public void removeEntityFromBasket(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName,
        @WebParam(name = "resourceID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        int resourceID,
        @WebParam(name = "entity", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String entity)
        throws IUserPersonalSpaceWSRemoveEntityFromBasketUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param basketName
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult
     * @throws IUserPersonalSpaceWSListBasketPerformersWithAttributesXMLUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListBasketPerformersWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListBasketPerformersWithAttributesXML")
    @WebResult(name = "ListBasketPerformersWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListBasketPerformersWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXML")
    @ResponseWrapper(localName = "ListBasketPerformersWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXMLResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult listBasketPerformersWithAttributesXML(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName)
        throws IUserPersonalSpaceWSListBasketPerformersWithAttributesXMLUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param basketName
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult
     * @throws IUserPersonalSpaceWSListBasketSessionsWithAttributesXMLUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListBasketSessionsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListBasketSessionsWithAttributesXML")
    @WebResult(name = "ListBasketSessionsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListBasketSessionsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXML")
    @ResponseWrapper(localName = "ListBasketSessionsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXMLResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult listBasketSessionsWithAttributesXML(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName)
        throws IUserPersonalSpaceWSListBasketSessionsWithAttributesXMLUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param basketName
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult
     * @throws IUserPersonalSpaceWSListBasketTrialsWithAttributesXMLUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListBasketTrialsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListBasketTrialsWithAttributesXML")
    @WebResult(name = "ListBasketTrialsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListBasketTrialsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXML")
    @ResponseWrapper(localName = "ListBasketTrialsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXMLResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult listBasketTrialsWithAttributesXML(
        @WebParam(name = "basketName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        String basketName)
        throws IUserPersonalSpaceWSListBasketTrialsWithAttributesXMLUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param attrViewSettings
     * @param attrGroupViewSettings
     * @throws IUserPersonalSpaceWSUpdateViewConfigurationUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "UpdateViewConfiguration", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/UpdateViewConfiguration")
    @RequestWrapper(localName = "UpdateViewConfiguration", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.UpdateViewConfiguration")
    @ResponseWrapper(localName = "UpdateViewConfigurationResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.UpdateViewConfigurationResponse")
    public void updateViewConfiguration(
        @WebParam(name = "attrGroupViewSettings", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        ArrayOfAttributeGroupViewSetting attrGroupViewSettings,
        @WebParam(name = "attrViewSettings", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
        ArrayOfAttributeViewSetting attrViewSettings)
        throws IUserPersonalSpaceWSUpdateViewConfigurationUPSExceptionFaultFaultMessage
    ;

    /**
     * 
     * @return
     *     returns motion.database.ws.userPersonalSpaceWCF.ListViewConfigurationResponse.ListViewConfigurationResult
     * @throws IUserPersonalSpaceWSListViewConfigurationUPSExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListViewConfiguration", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService/IUserPersonalSpaceWS/ListViewConfiguration")
    @WebResult(name = "ListViewConfigurationResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
    @RequestWrapper(localName = "ListViewConfiguration", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListViewConfiguration")
    @ResponseWrapper(localName = "ListViewConfigurationResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", className = "motion.database.ws.userPersonalSpaceWCF.ListViewConfigurationResponse")
    public motion.database.ws.userPersonalSpaceWCF.ListViewConfigurationResponse.ListViewConfigurationResult listViewConfiguration()
        throws IUserPersonalSpaceWSListViewConfigurationUPSExceptionFaultFaultMessage
    ;

}
