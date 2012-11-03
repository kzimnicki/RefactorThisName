package cc.explain.server.api;

import cc.explain.server.model.newModel.Configuration;
import cc.explain.server.model.newModel.RootWord;
import cc.explain.server.model.newModel.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 18.03.12
 * Time: 18:29
 */
@Controller
public class ExplainCCApi {

    private static Logger LOG = LoggerFactory.getLogger(ExplainCCApi.class);

    @Autowired
    TextService textService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/extractWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String, WordDetails> extractWords(@RequestBody DataToTranslate data) throws IOException {
        User user = userService.getLoggedUser();
        Map<String, WordDetails> wordsToTranslate = textService.getWordsToTranslate(user, data.getText());
        return wordsToTranslate;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveExcludeWords(@RequestBody List<String> excludedWords) throws IOException {
        User user = userService.getLoggedUser();
        for (String wordValue : excludedWords) {
            RootWord rootWord = textService.getRootWord(wordValue);
            if (rootWord != null) {
                user.getExcludedWords().add(rootWord);
            }
        }
        userService.save(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginServiceResult register(@RequestBody User user) throws IOException {
        return userService.register(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginServiceResult login(@RequestBody User user) throws IOException {
        return userService.login(user);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String, Set<String>> loadExcludedWords() throws IOException {
        User user = userService.getLoggedUser();
        return textService.getExcludedWords(user);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/excludedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeExcludedWord(@RequestBody String wordValue) throws IOException {
        User user = userService.getLoggedUser();
        RootWord rootWord = textService.getRootWord(wordValue);
        user.getExcludedWords().remove(rootWord);
        userService.save(user);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Configuration loadOptions() {
        User user = userService.getLoggedUser();
        return user.getConfig();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveOptions(@RequestBody Configuration config) {
        User user = userService.getLoggedUser();
        userService.save(user, config);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void saveIncludedWords(@RequestBody List<String> includedWords) {
        User user = userService.getLoggedUser();
        for (String wordValue : includedWords) {
            RootWord rootWord = textService.getRootWord(wordValue);
            if (rootWord != null) {
                user.getIncludedWords().add(rootWord);
            }
        }
        userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public Map<String, Set<String>> loadIncludedWords() {
        User user = userService.getLoggedUser();
        return textService.getIncludedWords(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/includedWords", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public void removeIncludedWords(@RequestBody String wordValue) {
        User user = userService.getLoggedUser();
        RootWord rootWord = textService.getRootWord(wordValue);
        user.getIncludedWords().remove(rootWord);
        userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exportExcludedWords", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public String exportExcludedWords() {
        User user = userService.getLoggedUser();
        Map<String, Set<String>> excludedWords = textService.getExcludedWords(user);
        return textService.createCSVString(excludedWords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exportIncludedWords", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public String exportIncludedWords() {
        User user =  userService.getLoggedUser();
        Map<String, Set<String>> includedWords = textService.getIncludedWords(user);
        return textService.createCSVString(includedWords);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/translate")
    @ResponseBody
    @Secured(Role.USER)
    @Transactional
    public List<String[]> translate(@RequestBody List<String> words) {
        saveIncludedWords(words);
        return textService.getTranslatedWord(words);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleException(final ConstraintViolationException e, Writer writer) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> constraint : e.getConstraintViolations()) {
            builder.append(constraint.getMessage()).append("|");
        }
        writer.write(String.format("%s", builder.toString()));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleException(final AuthenticationCredentialsNotFoundException e, Writer writer) throws IOException {
        LOG.error(e.getMessage());
        writer.write(String.format("%s", "Please log in."));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleException(BadCredentialsException e, Writer writer) throws IOException {
        LOG.error(e.getMessage());
        writer.write(String.format("%s", "Bad login or password"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleException(final DataIntegrityViolationException e, Writer writer) throws IOException {
        LOG.error(e.getMessage());
        writer.write(String.format("%s", "User already exists"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleException(Exception e, Writer writer) throws IOException {
        LOG.error(e.getMessage());
        writer.write(String.format("%s", e.getMessage()));
    }
}