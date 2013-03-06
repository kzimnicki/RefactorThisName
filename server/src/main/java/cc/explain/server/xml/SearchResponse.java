package cc.explain.server.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: kzimnick
 * Date: 27.11.12
 * Time: 20:52
 */
@XmlRootElement(name = "Response")
public class SearchResponse {

    private Subtitle[] subtitle;

    @XmlElementWrapper(name = "Subtitles")
    @XmlElement(name="Subtitle")
    public Subtitle[] getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Subtitle[] subtitle) {
        this.subtitle = subtitle;
    }
}
