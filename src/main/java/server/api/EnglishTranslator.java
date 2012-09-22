package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.core.CommonDao;
import server.model.newModel.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
    @Transactional
    public Map<String, WordDetails> extractWordsWithFrequency(@RequestBody DataToTranslate data) {
        User user = loginService.getLoggedUser();
        Map<String, WordDetails> wordsToTranslate = new HashMap<String, WordDetails>();
        try {
            wordExtractor.analyseText(data.getText());
            List<String> words = wordExtractor.getWordsToTranslate();
            wordsToTranslate = wordExtractor.filterWordsToTranslateWithFrequency(words,
                    user.getConfig().getMin(),
                    user.getConfig().getMax());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsToTranslate;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/translatedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveTranslatedWords(@RequestBody Map<String,String> test) throws UnsupportedEncodingException {
//        Set<Map.Entry<String, String>> entries = translatedWords.entrySet();
//        ArrayList<String> includedWords = new ArrayList<String>(entries.size());
//        for (Map.Entry<String, String> entry : entries) {
//            System.err.println(entry.getValue());
//            System.out.println(new String(entry.getValue().getBytes("ISO-8859-1")));
//            System.out.println(new String(entry.getValue().getBytes("ISO8859_1")));
//            System.out.println(new String(entry.getValue().getBytes("UTF-8")));
//            System.out.println(new String(entry.getValue().getBytes("UTF8")));
//            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "UTF-8"));
//            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "UTF8"));
//            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "ISO-8859-1"));
//            System.out.println(entry.getKey() + " - " + java.net.URLDecoder.decode(entry.getValue(), "ISO8859_1"));
//            includedWords.add(entry.getKey());
//        }
//        saveIncludedWords(includedWords);
       System.err.println(test);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveExcludeWords(@RequestBody List<String> excludedWords) throws IOException {
        User user = loginService.getLoggedUser();
        for (String wordValue : excludedWords) {
            RootWord rootWord = wordExtractor.getRootWord(wordValue);
            if(rootWord!=null){
                user.getExcludedWords().add(rootWord);
            }
        }
        commonDao.saveOrUpdate(user);
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


    @RequestMapping(method = RequestMethod.GET, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String,Set<String>> loadExcludedWords() throws IOException {
        return wordExtractor.getExcludedWords();
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeExcludedWord(@RequestBody String wordValue) throws IOException {
        User user = loginService.getLoggedUser();
        RootWord rootWord = wordExtractor.getRootWord(wordValue);
        user.getExcludedWords().remove(rootWord);
        commonDao.saveOrUpdate(user);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Configuration loadOptions() {
        User user = loginService.getLoggedUser();
        return user.getConfig();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveOptions(@RequestBody Configuration config) {
        User user = loginService.getLoggedUser();
        user.getConfig().setMax(config.getMax());
        user.getConfig().setMin(config.getMin());
        user.getConfig().setSubtitleTemplate(config.getSubtitleTemplate());
        user.getConfig().setTextTemplate(config.getTextTemplate());
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveIncludedWords(@RequestBody List<String> includedWords) {
        User user = loginService.getLoggedUser();
        for (String wordValue : includedWords) {
            RootWord rootWord = wordExtractor.getRootWord(wordValue);
            if(rootWord != null){
                user.getIncludedWords().add(rootWord);
            }
        }
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String, Set<String>> loadIncludedWords() {
        return wordExtractor.getIncludedWords();
    }

    private HashMap<String, Set<String>> createMap(Set<RootWord> words) {
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        for (RootWord rootWord : words){
            Set<String> wordFamily = wordExtractor.getStringWordFamily(rootWord);
            map.put(rootWord.getRootWord().getValue(), wordFamily);
        }
        return map;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeIncludedWords(@RequestBody String wordValue) {
        User user = loginService.getLoggedUser();
        RootWord rootWord = wordExtractor.getRootWord(wordValue);
        user.getIncludedWords().remove(rootWord);
        commonDao.saveOrUpdate(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exportExcludedWords", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public String exportExcludedWords() {
        Map<String, Set<String>> excludedWords = wordExtractor.getExcludedWords();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : excludedWords.entrySet()){
            builder.append(entry.getKey());
            builder.append(";");
            builder.append(StringUtils.join(entry.getValue(), " "));
            builder.append(";\n");
        }
        return builder.toString();

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleException(final ConstraintViolationException e, final HttpServletResponse response, Writer writer) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        StringBuilder builder = new StringBuilder();
        for(ConstraintViolation<?> constraint : e.getConstraintViolations()){
            builder.append(constraint.getMessage()).append("");
        }
        writer.write("\"Exceptipon\"");
    }



//    @RequestMapping(method = RequestMethod.DELETE, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public void removeIncludedWords(@RequestBody String wordValue) {
//        User user = loginService.getLoggedUser();
//        RootWord rootWord = wordExtractor.getRootWord(wordValue);
//        user.getIncludedWords().remove(rootWord);
//        commonDao.saveOrUpdate(user);
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public Set<PhrasalVerb> loadExcludedPhrasalVerbs() {
//        User user = loginService.getLoggedUser();
//        Hibernate.initialize(user.getExcludedPhrasalVerbs()); //TODO zrobic cos z tymi initializami.
//        return user.getExcludedPhrasalVerbs();
//    }

//    @RequestMapping(method = RequestMethod.POST, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public void saveExcludedPhrasalVerbs(@RequestBody List<String> phrasalVerbs) {
//        User user = loginService.getLoggedUser();
//        for (String pv : phrasalVerbs){
//            PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(pv);
//            user.getExcludedPhrasalVerbs().add(phrasalVerb);
//        }
//        commonDao.saveOrUpdate(user);
//    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/excludedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public void removeExcludedPhrasalVerbs(@RequestBody String phrasalVerbValue) {
//        User user = loginService.getLoggedUser();
//        PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(phrasalVerbValue);
//        user.getExcludedPhrasalVerbs().remove(phrasalVerb);
//        commonDao.saveOrUpdate(user);
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public Set<PhrasalVerb> loadIncludedPhrasalVerbs() {
//            User user = loginService.getLoggedUser();
//            Hibernate.initialize(user.getIncludedPhrasalVerbs()); //TODO zrobic cos z tymi initializami.
//            return user.getIncludedPhrasalVerbs();
//    }

//    @RequestMapping(method = RequestMethod.POST, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public void saveIncludedPhrasalVerbs(@RequestBody List<String> phrasalVerbs) {
//       User user = loginService.getLoggedUser();
//        for (String pv : phrasalVerbs){
//            PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(pv);
//            user.getIncludedPhrasalVerbs().add(phrasalVerb);
//        }
//        commonDao.saveOrUpdate(user);
//    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/includedPhrasalVerbs", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Secured(Role.USER)
//    @Transactional
//    public void removeIncludedPhrasalVerbs(@RequestBody String phrasalVerbValue) {
//        User user = loginService.getLoggedUser();
//        PhrasalVerb phrasalVerb = wordExtractor.getPhrasalVerb(phrasalVerbValue);
//        user.getIncludedPhrasalVerbs().remove(phrasalVerb);
//        commonDao.saveOrUpdate(user);
//    }
}
