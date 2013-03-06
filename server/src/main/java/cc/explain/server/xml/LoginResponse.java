package cc.explain.server.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: kzimnick
 * Date: 26.11.12
 * Time: 21:01
 */
@XmlRootElement(name = "Response")
public class LoginResponse {

    private String session;

    @XmlElement(name = "Session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
