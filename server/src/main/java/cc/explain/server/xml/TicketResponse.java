package cc.explain.server.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: kzimnick
 * Date: 27.11.12
 * Time: 20:52
 */
@XmlRootElement(name = "Response")
public class TicketResponse {

    private String ticket;

    private int waitTime;

    @XmlElement(name = "Ticket")
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @XmlElement(name = "WaitTime")
    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
