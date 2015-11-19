package be.valuya.ovh.sms.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum SmsPriority {

    @XmlEnumValue("high")
    HIGH,
    @XmlEnumValue("low")
    LOW,
    @XmlEnumValue("medium")
    MEDIUM,
    @XmlEnumValue("veryLow")
    VERY_LOW

}
