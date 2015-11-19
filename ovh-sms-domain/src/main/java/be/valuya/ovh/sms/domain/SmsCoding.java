package be.valuya.ovh.sms.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum SmsCoding {

    @XmlEnumValue("7bit")
    SEVEN_BIT,
    @XmlEnumValue("8bit")
    EIGHT_BIT;

}
