package cc.explain.server.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: kzimnick
 * Date: 27.11.12
 * Time: 22:01
 */
@XmlRootElement(name = "Releases")
public class Release {

    private String release;

    @XmlElement(name = "Release")
    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
