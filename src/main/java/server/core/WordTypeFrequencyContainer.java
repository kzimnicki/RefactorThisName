package server.core;

import server.model.newModel.Word;
import server.model.newModel.WordFamily;

import java.util.*;

/**
 * User: kzimnick
 * Date: 28.02.12
 * Time: 17:15
 */
public class WordTypeFrequencyContainer {

    private Map<String, Word> words;
    private Map<Word, WordFamily> wordFamilies;

    public WordTypeFrequencyContainer() {
        this.words = new HashMap<String, Word>();
        this.wordFamilies = new HashMap<Word, WordFamily>();
    }

    public void put(String value, Word word) {
        words.put(value, word);
    }

    public void put(Word word, WordFamily wordFamily) {
        wordFamilies.put(word, wordFamily);
    }

    public List<Word> getWordFamilyFor(String wordValue) {
        Word word = words.get(wordValue);
        if (word != null) {
            for (WordFamily wordFamily : wordFamilies.values()) {
                if (wordFamily.getFamily().contains(word)){
                    return wordFamily.getFamily();
                }
            }
        }
        return Collections.emptyList();
    }
    
    public WordFamily getWordFamilyFor(Word word) {
        if (word != null) {
            return wordFamilies.get(word);
        }
        return WordFamily.EMPTY;
    }

    public Integer getFrequnecyFor(String wordValue) {
        Word word = words.get(wordValue);
        return word != null ? word.getFrequency() : Integer.valueOf(-1);
    }

    public WordType getWordTypeFor(String wordValue) {
        Word word = words.get(wordValue);
        return word != null ? word.getWordType() : WordType.undefined;
    }

    public Word getWord(String wordValue) {
        return  words.get(wordValue);
    }

    public Map<Word, WordFamily> getWordFamilies(){
        return wordFamilies;
    }
}

