
package motion.database.ws.userPersonalSpaceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="attrGroupViewSettings" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}ArrayOfAttributeGroupViewSetting" minOccurs="0"/>
 *         &lt;element name="attrViewSettings" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}ArrayOfAttributeViewSetting" minOccurs="0"/>
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
    "attrGroupViewSettings",
    "attrViewSettings"
})
@XmlRootElement(name = "UpdateViewConfiguration")
public class UpdateViewConfiguration {

    protected ArrayOfAttributeGroupViewSetting attrGroupViewSettings;
    protected ArrayOfAttributeViewSetting attrViewSettings;

    /**
     * Gets the value of the attrGroupViewSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttributeGroupViewSetting }
     *     
     */
    public ArrayOfAttributeGroupViewSetting getAttrGroupViewSettings() {
        return attrGroupViewSettings;
    }

    /**
     * Sets the value of the attrGroupViewSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttributeGroupViewSetting }
     *     
     */
    public void setAttrGroupViewSettings(ArrayOfAttributeGroupViewSetting value) {
        this.attrGroupViewSettings = value;
    }

    /**
     * Gets the value of the attrViewSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttributeViewSetting }
     *     
     */
    public ArrayOfAttributeViewSetting getAttrViewSettings() {
        return attrViewSettings;
    }

    /**
     * Sets the value of the attrViewSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttributeViewSetting }
     *     
     */
    public void setAttrViewSettings(ArrayOfAttributeViewSetting value) {
        this.attrViewSettings = value;
    }

}
