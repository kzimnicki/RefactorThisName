package server.api;

import lombok.Data;
import server.model.newModel.Word;

import java.util.List;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 23.04.12
 * Time: 21:23
 */
@Data
public class WordDetails {

    private String frequency;
//    private Set<Word> wordFamily;

    public WordDetails(String frequency/*, Set<Word> wordFamily*/) {
        this.frequency = frequency;
//        this.wordFamily = wordFamily;
    }
}
