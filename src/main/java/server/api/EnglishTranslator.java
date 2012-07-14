package server.api;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import server.core.WordExtractor;
import server.model.newModel.*;

import javax.print.DocFlavor;
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
         //TODO pomyslec nad zmiana nazwy tych rzeczy do procesowania.
    @RequestMapping(method = RequestMethod.POST, value = "/extractWordsWithFrequency", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String, WordDetails> extractWordsWithFrequency(@RequestBody DataToTranslate data) {
        System.err.println(Thread.currentThread().getName());
        System.err.println(SecurityContextHolder.getContext().getAuthentication());
        Map<String, WordDetails> wordsToTranslate = new HashMap<String, WordDetails>();
        try {
            wordExtractor.analyseText(data.getText());
            List<String> words = wordExtractor.getWordsToTranslate();
            wordsToTranslate = wordExtractor.filterWordsToTranslateWithFrequency(words, data.getMinFrequency(), data.getMaxFrequency());
            wordsToTranslate = wordExtractor.addPhrasalVerbs(wordsToTranslate, data.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsToTranslate;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/translatedWords", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(method = RequestMethod.POST, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveExcludeWords(@RequestBody List<String> excludedWords) throws IOException {
        User user = loginService.getLoggedUser();
        for (String wordValue : excludedWords) {
            WordFamily wordFamily = wordExtractor.getWordFamily(wordValue);
            user.getExcludedWords().add(wordFamily);
            commonDao.saveOrUpdate(user);
        }
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
    @Transactional
    public List<WordFamily> getExcludedWords() throws IOException {
        User user = loginService.getLoggedUser();
        Hibernate.initialize(user.getExcludedWords());
        List<WordFamily> excludedWords = user.getExcludedWords();
        return excludedWords;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeExcludedWord(@RequestBody String wordValue) throws IOException {
        User user = loginService.getLoggedUser();
        WordFamily wordFamily = wordExtractor.getWordFamily(wordValue);
        user.getExcludedWords().remove(wordFamily);
        commonDao.saveOrUpdate(user);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Configuration getOptions() {
        User user = loginService.getLoggedUser();
        return user.getConfig();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void setOptions(@RequestBody Configuration config) {
        User user = loginService.getLoggedUser();
        user.setConfig(config);
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveIncludedWords(@RequestBody List<String> includedWords) {
        User user = loginService.getLoggedUser();
        for (String wordValue : includedWords) {
            WordFamily wordFamily = wordExtractor.getWordFamily(wordValue);
            user.getIncludedWords().add(wordFamily);
            commonDao.saveOrUpdate(user);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public List<WordFamily> loadIncludedWords() {
        User user = loginService.getLoggedUser();
        Hibernate.initialize(user.getIncludedWords()); //TODO zrobic cos z tymi initializami.
        return user.getIncludedWords();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeIncludedWords(@RequestBody String wordValue) {
        User user = loginService.getLoggedUser();
        WordFamily wordFamily = wordExtractor.getWordFamily(wordValue);
        user.getIncludedWords().remove(wordFamily);
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public List<PhrasalVerb> loadExcludedPhrasalVerbs() {
        User user = loginService.getLoggedUser();
        Hibernate.initialize(user.getExcludedPhrasalVerbs()); //TODO zrobic cos z tymi initializami.
        return user.getExcludedPhrasalVerbs();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveExcludedPhrasalVerbs(@RequestBody List<String> phrasalVerbs) {
        User user = loginService.getLoggedUser();
        for (String pv : phrasalVerbs){
            PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(pv);
            user.getExcludedPhrasalVerbs().add(phrasalVerb);
        }
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeExcludedPhrasalVerbs(@RequestBody String phrasalVerbValue) {
        User user = loginService.getLoggedUser();
        PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(phrasalVerbValue);
        user.getExcludedPhrasalVerbs().remove(phrasalVerb);
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public List<PhrasalVerb> loadIncludedPhrasalVerbs() {
            User user = loginService.getLoggedUser();
            Hibernate.initialize(user.getIncludedPhrasalVerbs()); //TODO zrobic cos z tymi initializami.
            return user.getIncludedPhrasalVerbs();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveIncludedPhrasalVerbs(@RequestBody List<String> phrasalVerbs) {
       User user = loginService.getLoggedUser();
        for (String pv : phrasalVerbs){
            PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(pv);
            user.getIncludedPhrasalVerbs().add(phrasalVerb);
        }
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeIncludedPhrasalVerbs(@RequestBody String phrasalVerbValue) {
        User user = loginService.getLoggedUser();
        PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(phrasalVerbValue);
        user.getIncludedPhrasalVerbs().remove(phrasalVerb);
        commonDao.saveOrUpdate(user);
    }
}
