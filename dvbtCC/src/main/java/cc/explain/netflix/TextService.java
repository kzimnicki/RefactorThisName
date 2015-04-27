package cc.explain.netflix;

import cc.explain.lucene.LuceneService;
import cc.explain.netflix.redis.Language;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Created by kz on 4/27/15.
 */
public class TextService {

    private LuceneService luceneService = new LuceneService();


    public Set<String> getWords(String text, Language from){
//        if (line1 != null) {
//            wordsToTranslate.addAll(luceneService.getGermanWords(excludedWords, line1));
//        }
        return Sets.newHashSet();
    }

    public boolean areSimilar(final String word, final String translation) {
        int distance = LevenshteinDistance.distance(StringUtils.lowerCase(word), StringUtils.lowerCase(translation));
        return (word.length() <5 && distance > 1) || (word.length() >= 5 && distance > 2);
    }
}
