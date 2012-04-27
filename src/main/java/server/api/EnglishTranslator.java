package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import server.core.WordExtractor;
import server.model.User;
import server.model.UserExcludeWord;
import server.model.Word;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * User: kzimnick
 * Date: 18.03.12
 * Time: 18:29
 */
@Controller
public class EnglishTranslator {

    @Autowired
    WordExtractor wordExtractor;

    @Autowired
    CommonDao commonDao;

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.POST, value = "/extractWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public List<String> extractWords(@RequestBody DataToTranslate data) {
        List<String> wordsToTranslate = new ArrayList<String>();
        try {
            wordExtractor.analyseText(data.getText());
            List<String> words = wordExtractor.getWordsToTranslate();
            wordsToTranslate = wordExtractor.filterWordsToTranslate(words, data.getMinFrequency(), data.getMaxFrequency());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsToTranslate;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/extractWordsWithFrequency", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public Map<String, WordDetails> extractWordsWithFrequency(@RequestBody DataToTranslate data) {
        System.err.println(Thread.currentThread().getName());
        System.err.println(SecurityContextHolder.getContext().getAuthentication());
        Map<String, WordDetails> wordsToTranslate = new HashMap<String, WordDetails>();
        try {
            wordExtractor.analyseText(data.getText());
            List<String> words = wordExtractor.getWordsToTranslate();
            wordsToTranslate = wordExtractor.filterWordsToTranslateWithFrequency(words, data.getMinFrequency(), data.getMaxFrequency());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsToTranslate;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveTranslatedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public void saveTranslatedWords(@RequestBody Map<String, String> translatedWords) throws UnsupportedEncodingException {
        Set<Map.Entry<String, String>> entries = translatedWords.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(new String(entry.getValue().getBytes("ISO-8859-1")));
            System.out.println(new String(entry.getValue().getBytes("ISO8859_1")));
            System.out.println(new String(entry.getValue().getBytes("UTF-8")));
            System.out.println(new String(entry.getValue().getBytes("UTF8")));
            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "UTF-8"));
            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "UTF8"));
            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "ISO-8859-1"));
            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "ISO8859_1"));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveExcludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public void saveExcludeWords(@RequestBody List<String> excludedWords) throws IOException {
        for (String wordValue : excludedWords) {
            User user = loginService.getLoggedUser();
            UserExcludeWord userExcludeWord = new UserExcludeWord();
            userExcludeWord.setUser(user);
            Word word = getPersistenWord(wordValue);
            if (word == null) {
                word = new Word();
                word.setValue(wordValue);
                commonDao.saveOrUpdate(word);
            }
            userExcludeWord.setWord(word);
            try {
                commonDao.saveOrUpdate(userExcludeWord);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private Word getPersistenWord(String wordValue) {
        return (Word) commonDao.getFirstByHQL("FROM Word w WHERE w.value = :wordValue", "wordValue", wordValue);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginServiceResult register(@RequestBody User user) throws IOException {
        return loginService.register(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginServiceResult login(@RequestBody User user) throws IOException {
        return loginService.login(user);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public List<String> excludedWords() throws IOException {
        User user = loginService.getLoggedUser();
        List<UserExcludeWord> userExcludeWords = (List<UserExcludeWord>) commonDao.getByHQL("FROM UserExcludeWord uew WHERE uew.user = :user", "user", user);
        List<String> words = new ArrayList<String>(userExcludeWords.size());
        for (UserExcludeWord userExcludeWord : userExcludeWords) {
            words.add(userExcludeWord.getWord().getValue());
        }
        return words;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/removeExcludedWord", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    public void removeExcludedWord(@RequestBody String wordValue) throws IOException {
        User user = loginService.getLoggedUser();
        Word word = getPersistenWord(wordValue);
        UserExcludeWord userExcludeWord = (UserExcludeWord) commonDao.getFirstByHQL("FROM UserExcludeWord uew WHERE uew.user = :user AND uew.word = :word", new String[]{"user", "word"}, new Object[]{user, word});
        commonDao.delete(userExcludeWord);
    }


}
