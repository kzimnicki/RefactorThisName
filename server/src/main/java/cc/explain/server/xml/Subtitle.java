package cc.explain.server.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: kzimnick
 * Date: 27.11.12
 * Time: 21:32
 */
@XmlRootElement(name = "Subtitle")
public class Subtitle {

    private String id;

    private boolean linked;
    private String imdb;
    private int season;
    private int episode;
    private String language;
    private int discs;
    private int downloads;
    private int reports;
    private int votes;
    private double rate;
    private String publisher;
    private String published;

      @XmlElementWrapper(name = "Releases")
    @XmlElement(name="Release")
    private Release[] releases;
//      <Releases>
//        <Release>Madagascar.3.Europes.Most.Wanted.2012.DVDRip.XViD-PLAYNOW</Release>
//      </Releases>


    @XmlElement(name = "Id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "Linked")
    public boolean getLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    @XmlElement(name = "IMDb")
    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @XmlElement(name = "Language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @XmlElement(name = "Discs")
    public int getDiscs() {
        return discs;
    }

    public void setDiscs(int discs) {
        this.discs = discs;
    }

    @XmlElement(name = "Downloads")
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @XmlElement(name = "Reports")
    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    @XmlElement(name = "Votes")
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @XmlElement(name = "Rate")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @XmlElement(name = "Publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @XmlElement(name = "Published")
    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    @XmlElement(name = "Season")
    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @XmlElement(name = "Episode")
    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }
}
