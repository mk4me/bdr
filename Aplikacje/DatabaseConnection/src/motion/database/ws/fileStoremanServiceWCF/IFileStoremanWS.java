
package motion.database.ws.fileStoremanServiceWCF;

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
@WebService(name = "IFileStoremanWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IFileStoremanWS {


    /**
     * 
     * @param description
     * @param mcID
     * @param path
     * @param filename
     * @return
     *     returns int
     * @throws IFileStoremanWSStoreMeasurementConfFileFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreMeasurementConfFile", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreMeasurementConfFile")
    @WebResult(name = "StoreMeasurementConfFileResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    @RequestWrapper(localName = "StoreMeasurementConfFile", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreMeasurementConfFile")
    @ResponseWrapper(localName = "StoreMeasurementConfFileResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreMeasurementConfFileResponse")
    public int storeMeasurementConfFile(
        @WebParam(name = "mcID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int mcID,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description,
        @WebParam(name = "filename", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String filename)
        throws IFileStoremanWSStoreMeasurementConfFileFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionId
     * @param description
     * @param path
     * @param filename
     * @return
     *     returns int
     * @throws IFileStoremanWSStoreSessionFileFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreSessionFile", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreSessionFile")
    @WebResult(name = "StoreSessionFileResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    @RequestWrapper(localName = "StoreSessionFile", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreSessionFile")
    @ResponseWrapper(localName = "StoreSessionFileResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreSessionFileResponse")
    public int storeSessionFile(
        @WebParam(name = "sessionId", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int sessionId,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description,
        @WebParam(name = "filename", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String filename)
        throws IFileStoremanWSStoreSessionFileFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param description
     * @param path
     * @param filename
     * @param trialID
     * @return
     *     returns int
     * @throws IFileStoremanWSStoreTrialFileFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreTrialFile", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreTrialFile")
    @WebResult(name = "StoreTrialFileResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    @RequestWrapper(localName = "StoreTrialFile", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreTrialFile")
    @ResponseWrapper(localName = "StoreTrialFileResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreTrialFileResponse")
    public int storeTrialFile(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int trialID,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description,
        @WebParam(name = "filename", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String filename)
        throws IFileStoremanWSStoreTrialFileFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param description
     * @param mcID
     * @param path
     * @throws IFileStoremanWSStoreMeasurementConfFilesFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreMeasurementConfFiles", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreMeasurementConfFiles")
    @RequestWrapper(localName = "StoreMeasurementConfFiles", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreMeasurementConfFiles")
    @ResponseWrapper(localName = "StoreMeasurementConfFilesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreMeasurementConfFilesResponse")
    public void storeMeasurementConfFiles(
        @WebParam(name = "mcID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int mcID,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description)
        throws IFileStoremanWSStoreMeasurementConfFilesFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @param description
     * @param path
     * @throws IFileStoremanWSStoreSessionFilesFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreSessionFiles", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreSessionFiles")
    @RequestWrapper(localName = "StoreSessionFiles", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreSessionFiles")
    @ResponseWrapper(localName = "StoreSessionFilesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreSessionFilesResponse")
    public void storeSessionFiles(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int sessionID,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description)
        throws IFileStoremanWSStoreSessionFilesFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param description
     * @param path
     * @param trialId
     * @throws IFileStoremanWSStoreTrialFilesFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "StoreTrialFiles", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/StoreTrialFiles")
    @RequestWrapper(localName = "StoreTrialFiles", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreTrialFiles")
    @ResponseWrapper(localName = "StoreTrialFilesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.StoreTrialFilesResponse")
    public void storeTrialFiles(
        @WebParam(name = "trialId", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int trialId,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path,
        @WebParam(name = "description", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String description)
        throws IFileStoremanWSStoreTrialFilesFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param fileID
     * @param path
     * @throws IFileStoremanWSDownloadCompleteFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "DownloadComplete", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/DownloadComplete")
    @RequestWrapper(localName = "DownloadComplete", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.DownloadComplete")
    @ResponseWrapper(localName = "DownloadCompleteResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.DownloadCompleteResponse")
    public void downloadComplete(
        @WebParam(name = "fileID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int fileID,
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path)
        throws IFileStoremanWSDownloadCompleteFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param fileID
     * @return
     *     returns motion.database.ws.fileStoremanServiceWCF.FileData
     * @throws IFileStoremanWSRetrieveFileFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "RetrieveFile", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/RetrieveFile")
    @WebResult(name = "RetrieveFileResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    @RequestWrapper(localName = "RetrieveFile", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.RetrieveFile")
    @ResponseWrapper(localName = "RetrieveFileResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.RetrieveFileResponse")
    public FileData retrieveFile(
        @WebParam(name = "fileID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        int fileID)
        throws IFileStoremanWSRetrieveFileFileAccessServiceExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param path
     * @return
     *     returns int
     * @throws IFileStoremanWSCreateSessionFromFilesFileAccessServiceExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateSessionFromFiles", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService/IFileStoremanWS/CreateSessionFromFiles")
    @WebResult(name = "CreateSessionFromFilesResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    @RequestWrapper(localName = "CreateSessionFromFiles", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.CreateSessionFromFiles")
    @ResponseWrapper(localName = "CreateSessionFromFilesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", className = "motion.database.ws.fileStoremanServiceWCF.CreateSessionFromFilesResponse")
    public int createSessionFromFiles(
        @WebParam(name = "path", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
        String path)
        throws IFileStoremanWSCreateSessionFromFilesFileAccessServiceExceptionFaultFaultMessage
    ;

}
