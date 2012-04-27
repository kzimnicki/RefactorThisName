package server.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

/**
 * User: kzimnick
 * Date: 28.02.12
 * Time: 17:15
 */
public class WordTypeFrequencyContainer {

    private Map<String, Integer> wordFrequency;
    private Map<String, WordType> wordType;
    private Map<String, Set<String>> wordFamilyMap;

    public WordTypeFrequencyContainer() {
        this.wordFrequency = new HashMap<String, Integer>();
        this.wordType = new HashMap<String, WordType>();
        this.wordFamilyMap = new HashMap<String, Set<String>>();
    }

    public void put(String word, Integer frequency) {
        wordFrequency.put(word, frequency);
    }

    public void put(String word, WordType type) {
        wordType.put(word, type);
    }

    public void put(String word, Set<String> wordFamilyList) {
          wordFamilyMap.put(word, wordFamilyList);
    }

     public Set<String> getWordFamilyFor(String word) {
         Set<String> wordsFamily = wordFamilyMap.get(word);
         return wordsFamily;
     }

    public Integer getFrequnecyFor(String word) {
        Integer frequency = wordFrequency.get(word);
        return frequency != null ? frequency : Integer.valueOf(-1);
    }

    public WordType getWordTypeFor(String word) {
        return wordType.get(word);
    }


}

