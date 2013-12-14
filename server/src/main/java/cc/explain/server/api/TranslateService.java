package cc.explain.server.api;

import cc.explain.server.model.Translation;
import cc.explain.server.rest.Rest;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: kzimnick
 * Date: 27.04.13
 * Time: 18:22
 */
public class TranslateService {

    @Autowired
    TextDAO textDAO;

    private static final String GOOGLE_API_URL_PATTERN = "http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=%s&sl=%s&ie=UTF-8&oe=UTF-8";
    private static final String GOOGLE_API_URL_ENGLISH = String.format(GOOGLE_API_URL_PATTERN, "pl","en");
    private static final String GOOGLE_API_URL_GERMAN = String.format(GOOGLE_API_URL_PATTERN, "pl","de");
    private static final String PARAMETER_KEY = "q";

    public String[] googleTranslate(String[] englishWords){
        return translate(englishWords, GOOGLE_API_URL_ENGLISH);
    }

    private String[] translate(String[] englishWords, String apiUrl) {
        String[] results = {};
        if(englishWords.length > 0){
            Rest rest = Rest.get()
            .url(apiUrl);
            for (String word : englishWords){
                rest.addParameter(PARAMETER_KEY, word);
            }
            results = rest.execute();
        }
        return results;
    }

    public String[] googleTranslateFromGerman(String[] germanWords){
        return translate(germanWords, GOOGLE_API_URL_GERMAN);
    }

    public Map<String, String> translate(List<String> englishWords){
        String[] translated = googleTranslate(Iterables.toArray(englishWords, String.class));
        HashMap<String,String> translatedWords = Maps.newHashMap();
        for (int i = 0; i< translated.length; i++){
            translatedWords.put(englishWords.get(i), translated[i]);
        }
        return translatedWords;
    }

    public Map<String, String> getTranslatedWord(List<String> words) {
        Map<String,String> translatedWords = Maps.newHashMap();

        List<Translation> translations = textDAO.findTranslations(words);
        for (Translation t : translations) {
            translatedWords.put(t.getSourceWord(), t.getTransWord());
        }
        return translatedWords;
    }
}