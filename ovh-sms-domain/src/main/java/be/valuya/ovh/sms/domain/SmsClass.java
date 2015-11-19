package be.valuya.ovh.sms.domain;

import javax.xml.bind.annotation.XmlEnumValue;

public enum SmsClass {

    @XmlEnumValue("flash")
    FLASH,
    @XmlEnumValue("phoneDisplay")
    PHONE_DISPLAY,
    @XmlEnumValue("sim")
    SIM,
    @XmlEnumValue("toolkit")
    TOOLKIT;

}
