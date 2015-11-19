package be.valuya.ovh.sms.domain;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsJob {

    private BigDecimal totalCreditsRemoved;
    private List<String> invalidReceivers;
    private List<Long> ids;
    private List<String> validReceivers;

    public BigDecimal getTotalCreditsRemoved() {
        return totalCreditsRemoved;
    }

    public void setTotalCreditsRemoved(BigDecimal totalCreditsRemoved) {
        this.totalCreditsRemoved = totalCreditsRemoved;
    }

    public List<String> getInvalidReceivers() {
        return invalidReceivers;
    }

    public void setInvalidReceivers(List<String> invalidReceivers) {
        this.invalidReceivers = invalidReceivers;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> getValidReceivers() {
        return validReceivers;
    }

    public void setValidReceivers(List<String> validReceivers) {
        this.validReceivers = validReceivers;
    }

}
