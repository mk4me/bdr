
package motion.database.ws.authorizationWCF;

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
@WebService(name = "IAuthorizationWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IAuthorizationWS {


    /**
     * 
     * @return
     *     returns boolean
     * @throws IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CheckUserAccount", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/CheckUserAccount")
    @WebResult(name = "CheckUserAccountResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
    @RequestWrapper(localName = "CheckUserAccount", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.CheckUserAccount")
    @ResponseWrapper(localName = "CheckUserAccountResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.CheckUserAccountResponse")
    public boolean checkUserAccount()
        throws IAuthorizationWSCheckUserAccountAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param lastName
     * @param firstName
     * @throws IAuthorizationWSCreateUserAccountAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateUserAccount", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/CreateUserAccount")
    @RequestWrapper(localName = "CreateUserAccount", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.CreateUserAccount")
    @ResponseWrapper(localName = "CreateUserAccountResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.CreateUserAccountResponse")
    public void createUserAccount(
        @WebParam(name = "firstName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String firstName,
        @WebParam(name = "lastName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String lastName)
        throws IAuthorizationWSCreateUserAccountAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @param write
     * @param grantedUserLogin
     * @param grantedUserDomain
     * @throws IAuthorizationWSGrantSessionPrivilegesAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "GrantSessionPrivileges", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/GrantSessionPrivileges")
    @RequestWrapper(localName = "GrantSessionPrivileges", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.GrantSessionPrivileges")
    @ResponseWrapper(localName = "GrantSessionPrivilegesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.GrantSessionPrivilegesResponse")
    public void grantSessionPrivileges(
        @WebParam(name = "grantedUserLogin", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String grantedUserLogin,
        @WebParam(name = "grantedUserDomain", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String grantedUserDomain,
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        int sessionID,
        @WebParam(name = "write", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        boolean write)
        throws IAuthorizationWSGrantSessionPrivilegesAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @param grantedUserLogin
     * @param grantedUserDomain
     * @throws IAuthorizationWSRemoveSessionPrivilegesAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "RemoveSessionPrivileges", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/RemoveSessionPrivileges")
    @RequestWrapper(localName = "RemoveSessionPrivileges", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.RemoveSessionPrivileges")
    @ResponseWrapper(localName = "RemoveSessionPrivilegesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.RemoveSessionPrivilegesResponse")
    public void removeSessionPrivileges(
        @WebParam(name = "grantedUserLogin", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String grantedUserLogin,
        @WebParam(name = "grantedUserDomain", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        String grantedUserDomain,
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        int sessionID)
        throws IAuthorizationWSRemoveSessionPrivilegesAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @param isWritable
     * @param idPublic
     * @throws IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "AlterSessionVisibility", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/AlterSessionVisibility")
    @RequestWrapper(localName = "AlterSessionVisibility", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.AlterSessionVisibility")
    @ResponseWrapper(localName = "AlterSessionVisibilityResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.AlterSessionVisibilityResponse")
    public void alterSessionVisibility(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        int sessionID,
        @WebParam(name = "idPublic", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        boolean idPublic,
        @WebParam(name = "isWritable", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        boolean isWritable)
        throws IAuthorizationWSAlterSessionVisibilityAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @return
     *     returns motion.database.ws.authorizationWCF.ListUsersResponse.ListUsersResult
     * @throws IAuthorizationWSListUsersAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListUsers", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/ListUsers")
    @WebResult(name = "ListUsersResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
    @RequestWrapper(localName = "ListUsers", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.ListUsers")
    @ResponseWrapper(localName = "ListUsersResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.ListUsersResponse")
    public motion.database.ws.authorizationWCF.ListUsersResponse.ListUsersResult listUsers()
        throws IAuthorizationWSListUsersAuthorizationExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @return
     *     returns motion.database.ws.authorizationWCF.ListSessionPrivilegesResponse.ListSessionPrivilegesResult
     * @throws IAuthorizationWSListSessionPrivilegesAuthorizationExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ListSessionPrivileges", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService/IAuthorizationWS/ListSessionPrivileges")
    @WebResult(name = "ListSessionPrivilegesResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
    @RequestWrapper(localName = "ListSessionPrivileges", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.ListSessionPrivileges")
    @ResponseWrapper(localName = "ListSessionPrivilegesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", className = "motion.database.ws.authorizationWCF.ListSessionPrivilegesResponse")
    public motion.database.ws.authorizationWCF.ListSessionPrivilegesResponse.ListSessionPrivilegesResult listSessionPrivileges(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService")
        int sessionID)
        throws IAuthorizationWSListSessionPrivilegesAuthorizationExceptionFaultFaultMessage
    ;

}
