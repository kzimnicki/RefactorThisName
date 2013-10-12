package cc.explain.server.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 30.05.13
 * Time: 22:15
 */
public class WordDetailDTO {
    private String rootWord;
    private int frequency;
    private Set<String> wordFamily = new HashSet<String>();
    private String addDate;

    public String getRootWord() {
        return rootWord;
    }

    public void setRootWord(String rootWord) {
        this.rootWord = rootWord;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public Set<String> getWordFamily() {
        return wordFamily;
    }

    public void addWordFamily(String wordFamily) {
        getWordFamily().add(wordFamily);
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
