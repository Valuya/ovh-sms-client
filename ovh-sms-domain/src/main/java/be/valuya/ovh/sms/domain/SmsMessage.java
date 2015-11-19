package be.valuya.ovh.sms.domain;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsMessage {

    private Charset charset = StandardCharsets.UTF_8; // The sms coding
    @XmlElement(name = "class")
    private SmsClass smsClass = SmsClass.PHONE_DISPLAY; // The sms class
    @XmlElement(name = "coding")
    private SmsCoding smsCoding = SmsCoding.SEVEN_BIT; // The sms coding
    @XmlElement(name = "differedPeriod")
    private Long differedMinutes; // The time -in minute(s)- to wait before sending the message
    private String message; // The sms message
    private boolean noStopClause = true; // Do not display STOP clause in the message, this requires that this is not an advertising message
    @XmlElement(name = "priority")
    private SmsPriority smsPriority = SmsPriority.HIGH; // The priority of the message
    private List<String> receivers; // The receivers list
    private String sender; // The sender
    private String tag; // The identifier group tag
    @XmlElement(name = "validityPeriod")
    private long validityMinutes = 2880L; // The maximum time -in minute(s)- before the message is dropped
    private boolean senderForResponse; // Set the flag to send a special sms which can be reply by the receiver (smsResponse).

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SmsClass getSmsClass() {
        return smsClass;
    }

    public void setSmsClass(SmsClass smsClass) {
        this.smsClass = smsClass;
    }

    public SmsCoding getSmsCoding() {
        return smsCoding;
    }

    public void setSmsCoding(SmsCoding smsCoding) {
        this.smsCoding = smsCoding;
    }

    public Long getDifferedMinutes() {
        return differedMinutes;
    }

    public void setDifferedMinutes(Long differedMinutes) {
        this.differedMinutes = differedMinutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNoStopClause() {
        return noStopClause;
    }

    public void setNoStopClause(boolean noStopClause) {
        this.noStopClause = noStopClause;
    }

    public SmsPriority getSmsPriority() {
        return smsPriority;
    }

    public void setSmsPriority(SmsPriority smsPriority) {
        this.smsPriority = smsPriority;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getValidityMinutes() {
        return validityMinutes;
    }

    public void setValidityMinutes(long validityMinutes) {
        this.validityMinutes = validityMinutes;
    }

    public boolean isSenderForResponse() {
        return senderForResponse;
    }

    public void setSenderForResponse(boolean senderForResponse) {
        this.senderForResponse = senderForResponse;
    }

}
