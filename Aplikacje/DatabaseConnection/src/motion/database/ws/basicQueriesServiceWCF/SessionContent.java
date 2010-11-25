
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionDetailsWithAttributes"/>
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList" minOccurs="0"/>
 *         &lt;element name="TrialContentList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TrialContent" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialDetailsWithAttributes"/>
 *                             &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionDetailsWithAttributes",
    "fileWithAttributesList",
    "trialContentList"
})
@XmlRootElement(name = "SessionContent")
public class SessionContent {

    @XmlElement(name = "SessionDetailsWithAttributes", required = true)
    protected SessionDetailsWithAttributes sessionDetailsWithAttributes;
    @XmlElement(name = "FileWithAttributesList")
    protected FileWithAttributesList fileWithAttributesList;
    @XmlElement(name = "TrialContentList", required = true)
    protected SessionContent.TrialContentList trialContentList;

    /**
     * Gets the value of the sessionDetailsWithAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link SessionDetailsWithAttributes }
     *     
     */
    public SessionDetailsWithAttributes getSessionDetailsWithAttributes() {
        return sessionDetailsWithAttributes;
    }

    /**
     * Sets the value of the sessionDetailsWithAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionDetailsWithAttributes }
     *     
     */
    public void setSessionDetailsWithAttributes(SessionDetailsWithAttributes value) {
        this.sessionDetailsWithAttributes = value;
    }

    /**
     * Gets the value of the fileWithAttributesList property.
     * 
     * @return
     *     possible object is
     *     {@link FileWithAttributesList }
     *     
     */
    public FileWithAttributesList getFileWithAttributesList() {
        return fileWithAttributesList;
    }

    /**
     * Sets the value of the fileWithAttributesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileWithAttributesList }
     *     
     */
    public void setFileWithAttributesList(FileWithAttributesList value) {
        this.fileWithAttributesList = value;
    }

    /**
     * Gets the value of the trialContentList property.
     * 
     * @return
     *     possible object is
     *     {@link SessionContent.TrialContentList }
     *     
     */
    public SessionContent.TrialContentList getTrialContentList() {
        return trialContentList;
    }

    /**
     * Sets the value of the trialContentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionContent.TrialContentList }
     *     
     */
    public void setTrialContentList(SessionContent.TrialContentList value) {
        this.trialContentList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TrialContent" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialDetailsWithAttributes"/>
     *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "trialContent"
    })
    public static class TrialContentList {

        @XmlElement(name = "TrialContent")
        protected List<SessionContent.TrialContentList.TrialContent> trialContent;

        /**
         * Gets the value of the trialContent property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the trialContent property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTrialContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SessionContent.TrialContentList.TrialContent }
         * 
         * 
         */
        public List<SessionContent.TrialContentList.TrialContent> getTrialContent() {
            if (trialContent == null) {
                trialContent = new ArrayList<SessionContent.TrialContentList.TrialContent>();
            }
            return this.trialContent;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialDetailsWithAttributes"/>
         *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "trialDetailsWithAttributes",
            "fileWithAttributesList"
        })
        public static class TrialContent {

            @XmlElement(name = "TrialDetailsWithAttributes", required = true)
            protected TrialDetailsWithAttributes trialDetailsWithAttributes;
            @XmlElement(name = "FileWithAttributesList")
            protected FileWithAttributesList fileWithAttributesList;

            /**
             * Gets the value of the trialDetailsWithAttributes property.
             * 
             * @return
             *     possible object is
             *     {@link TrialDetailsWithAttributes }
             *     
             */
            public TrialDetailsWithAttributes getTrialDetailsWithAttributes() {
                return trialDetailsWithAttributes;
            }

            /**
             * Sets the value of the trialDetailsWithAttributes property.
             * 
             * @param value
             *     allowed object is
             *     {@link TrialDetailsWithAttributes }
             *     
             */
            public void setTrialDetailsWithAttributes(TrialDetailsWithAttributes value) {
                this.trialDetailsWithAttributes = value;
            }

            /**
             * Gets the value of the fileWithAttributesList property.
             * 
             * @return
             *     possible object is
             *     {@link FileWithAttributesList }
             *     
             */
            public FileWithAttributesList getFileWithAttributesList() {
                return fileWithAttributesList;
            }

            /**
             * Sets the value of the fileWithAttributesList property.
             * 
             * @param value
             *     allowed object is
             *     {@link FileWithAttributesList }
             *     
             */
            public void setFileWithAttributesList(FileWithAttributesList value) {
                this.fileWithAttributesList = value;
            }

        }

    }

}
