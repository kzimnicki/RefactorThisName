package cc.explain.server.api;

import cc.explain.server.core.CommonDao;
import cc.explain.server.model.RootWord;
import cc.explain.server.model.Translation;
import cc.explain.server.model.Word;
import cc.explain.server.model.WordRelation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 03.11.12
 * Time: 08:30
 */
public class TextDAO {

    @Autowired
    CommonDao commonDao;

    public List<Word> findWordByWordValues(List<String> wordValues){
         return (List<Word>) commonDao.getByHQL("FROM Word w " +
                                                "WHERE w.value IN :wordValues " +
                                                "GROUP BY w.value, w.id", "wordValues",
                                                wordValues);
    }

    public List<WordRelation> findWordRelationByWordValue(String wordValue){
        List<WordRelation> wordRelations = (List<WordRelation>) commonDao.getByHQL("FROM WordRelation wr WHERE wr.word.value = :wordValue", "wordValue", wordValue);
        return wordRelations;
    }

    public List<Translation> findTranslations(String wordValue){
        List<Translation> translatedWords = (List<Translation>) commonDao.getByHQL("FROM Translation t WHERE t.sourceWord= :word", "word", wordValue);
        return translatedWords;
    }

    public List<Translation> findTranslations(List<String> words){
        List<Translation> translatedWords = (List<Translation>) commonDao.getByHQL("FROM Translation t WHERE t.sourceWord IN :words", "words", words);
        return translatedWords;
    }

    public List<WordRelation> findWordRelationByRootWord(RootWord rootWord){
        List<WordRelation> rootWordrelations = (List<WordRelation>)commonDao.getByHQLObject("FROM WordRelation wr WHERE wr.rootWord = :rootWord", "rootWord", rootWord);
        return rootWordrelations;
    }

    public List<WordRelation> findWordRelationsByRootWordValues(Set<String> wordValues){
        List<WordRelation> wordRelations = (List<WordRelation>) commonDao.getByHQL("FROM WordRelation wr JOIN FETCH wr.word w JOIN FETCH wr.rootWord WHERE wr.rootWord.rootWord.value IN :wordValues ORDER BY wr.rootWord.rootWord", "wordValues", wordValues);
        return wordRelations;
    }

    public List<WordRelation> findWordRelationByRootWordValue(String wordValue){
        List<WordRelation> wordRelations = (List<WordRelation>)commonDao.getByHQL("FROM WordRelation wr WHERE wr.rootWord.rootWord.value = :wordValue", "wordValue", wordValue);
        return wordRelations;
    }


}
