package cc.explain.server.api;

import cc.explain.server.model.Translation;
import cc.explain.server.rest.Rest;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    public static final String GOOGLE_API_URL = "http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl&sl=en&ie=UTF-8&oe=UTF-8";
    public static final String PARAMETER_KEY = "q";

    public String[] translate(String[] englishWords) {
        String[] translated = googleTranslate(englishWords);
        for(String w : translated){
            System.out.println(w);
        }
        return translated;
    }

    public String[] googleTranslate(String[] englishWords){
        Rest rest = Rest.get()
            .url(GOOGLE_API_URL);
        for (String word : englishWords){
            rest.addParameter(PARAMETER_KEY, word);
        }
        return rest.execute();
    }

    public Map<String, String> translate(List<String> englishWords){
        String[] translated = translate(Iterables.toArray(englishWords, String.class));
        HashMap<String,String> translatedWords = Maps.newHashMap();
        for (int i = 0; i< translated.length; i++){
            translatedWords.put(englishWords.get(i), translated[i]);
        }
        return translatedWords;
    }

    public String getTranslatedWord(String word) {
        List<Translation> translatedWords = textDAO.findTranslations(word);
        if (translatedWords.size() > 0) {
            return translatedWords.get(0).getTransWord();
        }
        return word;
    }

    public List<String[]> getTranslatedWord(List<String> words) {
        ArrayList<String[]> translatedWords = new ArrayList<String[]>(words.size());
        for (String w : words) {
            translatedWords.add(new String[]{getTranslatedWord(w)});
        }
        return translatedWords;
    }
}