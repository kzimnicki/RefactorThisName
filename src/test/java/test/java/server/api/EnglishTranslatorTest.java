package test.java.server.api;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.api.*;
import server.core.WordExtractor;
import server.model.newModel.Configuration;
import server.model.newModel.PhrasalVerb;
import server.model.newModel.User;
import server.model.newModel.WordFamily;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * User: kzimnick
 * Date: 02.06.12
 * Time: 13:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class EnglishTranslatorTest {

    @Autowired
    EnglishTranslator englishTranslator;

    @Autowired
    WordExtractor wordExtractor;

    @Autowired
    CommonDao commonDao;

    @Before
    public void setUp() throws Exception {
        cleanDatabase();
    }

    private void cleanDatabase() {
        commonDao.executeSQL("DELETE FROM user_excludedphrasalverbs");
        commonDao.executeSQL("DELETE FROM user_includedphrasalverbs");
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user_excludedwords");
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user");

    }

    @After
    public void tearDown() throws Exception {
        cleanDatabase();
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser3@gmail.com");
        user.setPassword("123456");
        return user;
    }

    @Test
    public void testExtractWordsWithFrequency() throws Exception {
        String text = "What began as a one-time jolt in 2008, an unprecedented effort to revive economic activity, " +
                "has become an uncomfortable status quo, an enduring reality in which savers are punished and borrowers rewarded by a permafrost of low interest rates." +
                "And the Fed, acutely uneasy with this new role in the American economy, " +
                "may now find itself unable to avoid doubling down." +
                "Although Fed officials have said repeatedly that they were reluctant to expand what has already been a substantial campaign to stimulate growth, " +
                "the slowing rate of job creation suggests that they have not done enough. And there’s little prospect that Congress will rise to the occasion.";


        createRegisterAndLoginUser();

        DataToTranslate data = new DataToTranslate();
        data.setText(text);
//        data.setMinFrequency(0);             //TODO refactor to get max and min from options.
//        data.setMaxFrequency(90);
        data.setUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


        Map<String, WordDetails> extractedWords = englishTranslator.extractWordsWithFrequency(data);

        assertEquals(9, extractedWords.size());
        assertEquals("76", extractedWords.get("quo").getFrequency());
        assertEquals(2, extractedWords.get("quo").getWordFamily().size());

        assertEquals("PV", extractedWords.get("doubling down").getFrequency());
        assertEquals(null, extractedWords.get("doubling down").getWordFamily());
    }

    @Test
    public void testExtractWordsWithFrequencyAfterExcludeOne() throws Exception {
        String text = "What began as a one-time jolt in 2008, an unprecedented effort to revive economic activity, " +
                "has become an uncomfortable status quo, an enduring reality in which savers are punished and borrowers rewarded by a permafrost of low interest rates." +
                "And the Fed, acutely uneasy with this new role in the American economy, " +
                "may now find itself unable to avoid doubling down." +
                "Although Fed officials have said repeatedly that they were reluctant to expand what has already been a substantial campaign to stimulate growth, " +
                "the slowing rate of job creation suggests that they have not done enough. And there’s little prospect that Congress will rise to the occasion.";


        createRegisterAndLoginUser();

        DataToTranslate data = new DataToTranslate();
        data.setText(text);
//        data.setMinFrequency(0); //TODO refactor to get max and min from options.
//        data.setMaxFrequency(90);
        data.setUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


        List<String> excludedwords = Arrays.asList(new String[]{
                "quo"
        });
        englishTranslator.saveExcludeWords(excludedwords);
        Map<String, WordDetails> extractedWords = englishTranslator.extractWordsWithFrequency(data);

        assertEquals(8, extractedWords.size());
    }


    @Test
    public void testSaveExcludeWords() throws Exception {
        createRegisterAndLoginUser();

        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship", "getting"
        });
        englishTranslator.saveExcludeWords(excludedwords);

        Set<WordFamily> loadedExcludedWords = englishTranslator.loadExcludedWords();
        assertEquals(5, loadedExcludedWords.size());

        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("car")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("cat")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("dog")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("ship")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("get")));
    }


    @Test
    public void testSaveIncludedWords() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveIncludedWords(includedWords);

        Set<WordFamily> loadedIncludedWords = englishTranslator.loadIncludedWords();
        assertEquals(4, loadedIncludedWords.size());
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("car")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("cat")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("dog")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("ship")));
    }

@Test
    public void testSaveIncludedWordsFor2NullIdWordFamily() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "fungal", "fusarium"
        });
        englishTranslator.saveIncludedWords(includedWords);

        Set<WordFamily> loadedIncludedWords = englishTranslator.loadIncludedWords();
        assertEquals(2, loadedIncludedWords.size());
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("fungal")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("fusarium")));
    }


    @Test
    public void testSaveIncludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> phrasalVerbs = Arrays.asList(new String[]{
                "moving in", "move out", "get rid of"
        });
        englishTranslator.saveIncludedPhrasalVerbs(phrasalVerbs);

        Set<PhrasalVerb> includedPhrasalVerbs = englishTranslator.loadIncludedPhrasalVerbs();
        assertEquals(3, includedPhrasalVerbs.size());

        Assert.assertTrue(includedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("move in")));
        Assert.assertTrue(includedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("move out")));
        Assert.assertTrue(includedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("get rid of")));
    }

    private void createRegisterAndLoginUser() throws IOException {
        User testUser = createTestUser();
        englishTranslator.register(testUser);
        englishTranslator.login(testUser);
    }

    //
    @Test
    public void testRegister() throws Exception {
        User testUser = createTestUser();
        LoginServiceResult result = englishTranslator.register(testUser);
        assertEquals(LoginServiceResult.SUCCESS, result);
    }

    @Test
    public void testLogin() throws Exception {
        User testUser = createTestUser();
        englishTranslator.register(testUser);

        LoginServiceResult result = englishTranslator.login(testUser);

        assertEquals(LoginServiceResult.SUCCESS, result);
    }

    @Test
    public void testRemoveExcludedWord() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveExcludeWords(excludedwords);
        englishTranslator.removeExcludedWord("dog");

        Set<WordFamily> loadedExcludedWords = englishTranslator.loadExcludedWords();

        assertEquals(3, loadedExcludedWords.size());
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("car")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("cat")));
        Assert.assertTrue(loadedExcludedWords.contains(wordExtractor.getWordFamily("ship")));
    }

    @Test
    public void testRemoveIncludedWord() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveIncludedWords(includedWords);
        englishTranslator.removeIncludedWords("dog");

        Set<WordFamily> loadedIncludedWords = englishTranslator.loadIncludedWords();

        assertEquals(3, loadedIncludedWords.size());

        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("car")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("cat")));
        Assert.assertTrue(loadedIncludedWords.contains(wordExtractor.getWordFamily("ship")));
    }

    @Test
    public void testRemoveIncludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "move in", "move out", "getting rid of"
        });
        englishTranslator.saveIncludedPhrasalVerbs(includedWords);
        englishTranslator.removeIncludedPhrasalVerbs("move out");

        Set<PhrasalVerb> includedPhrasalVerbs = englishTranslator.loadIncludedPhrasalVerbs();

        assertEquals(2, includedPhrasalVerbs.size());

        Assert.assertTrue(includedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("move in")));
        Assert.assertTrue(includedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("get rid of")));
    }

    @Test
    public void testRemoveExcludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedWords = Arrays.asList(new String[]{
                "moving in", "move out", "get rid of"
        });
        englishTranslator.saveExcludedPhrasalVerbs(excludedWords);
        englishTranslator.removeExcludedPhrasalVerbs("get rid of");

        Set<PhrasalVerb> excludedPhrasalVerbs = englishTranslator.loadExcludedPhrasalVerbs();

        assertEquals(2, excludedPhrasalVerbs.size());
        Assert.assertTrue(excludedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("move in")));
        Assert.assertTrue(excludedPhrasalVerbs.contains(wordExtractor.getPhrasalVerb("move out")));
    }

    @Test
    public void testOptions() throws Exception {
        createRegisterAndLoginUser();
        Configuration config = new Configuration();
        config.setMax(98);
        config.setMin(12);
        config.setTextTemplate("txt");
        config.setSubtitleTemplate("sub");
        englishTranslator.saveOptions(config);
        englishTranslator.saveOptions(config);
        englishTranslator.saveOptions(config);
        Configuration options = englishTranslator.loadOptions();

        Assert.assertEquals(98, options.getMax());
        Assert.assertEquals(12, options.getMin());
        Assert.assertEquals("txt", options.getTextTemplate());
        Assert.assertEquals("sub", options.getSubtitleTemplate());
    }


    @Test
    public void testDatabaseStructureAfterRemove() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car"
        });
        englishTranslator.saveIncludedWords(includedWords);
        englishTranslator.removeIncludedWords("car");

        assertNotNull(wordExtractor.getWordFamily("car").getId());
        assertEquals(2, wordExtractor.getWordFamily("car").getFamily().size());
    }

    //TODO dodac test w stylu: moving in => a loaded should be move in (base form).
}
