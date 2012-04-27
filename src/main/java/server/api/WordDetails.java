package server.api;

import java.util.List;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 23.04.12
 * Time: 21:23
 */
public class WordDetails {

    private String frequency;
    private Set<String> wordFamily;

    public WordDetails(String frequency, Set<String> wordFamily) {
        this.frequency = frequency;
        this.wordFamily = wordFamily;
    }

    public Set<String> getWordFamily() {
        return wordFamily;
    }

    public void setWordFamily(Set<String> wordFamily) {
        this.wordFamily = wordFamily;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
