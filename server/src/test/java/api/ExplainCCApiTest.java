package api;

import cc.explain.server.api.*;
import cc.explain.server.core.CommonDao;
import cc.explain.server.model.Configuration;
import cc.explain.server.model.User;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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
public class ExplainCCApiTest {

    @Autowired
    ExplainCCApi api;

    @Autowired
    TextService textService;

    @Autowired
    CommonDao commonDao;

    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        cleanDatabase();
    }

    private void cleanDatabase() {
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
    public void testExtractWordsWithoutLogin() throws Exception {
        String text = "What began as a one-time jolt in 2008, an unprecedented effort to revive economic activity, " +
                "has become an uncomfortable status quo, an enduring reality in which savers are punished and borrowers rewarded by a permafrost of low interest rates." +
                "And the Fed, acutely uneasy with this new role in the American economy, " +
                "may now find itself unable to avoid doubling down." +
                "Although Fed officials have said repeatedly that they were reluctant to expand what has already been a substantial campaign to stimulate growth, " +
                "the slowing rate of job creation suggests that they have not done enough. And there’s little prospect that Congress will rise to the occasion.";
        DataToTranslate data = new DataToTranslate();
        data.setText(text);
        data.setUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");
        userService.clearAutentication();

        Map<String, WordDetails> extractedWords = api.extractWords(data);

        assertEquals(14, extractedWords.size());
        assertEquals(76, extractedWords.get("quo").getFrequency().intValue());
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
        data.setUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


        Map<String, WordDetails> extractedWords = api.extractWords(data);

        assertEquals(14, extractedWords.size());
        assertEquals(76, extractedWords.get("quo").getFrequency().intValue());
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
        data.setUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


        List<String> excludedwords = Arrays.asList(new String[]{
                "quo"
        });
        api.saveExcludeWords(excludedwords);
        Map<String, WordDetails> extractedWords = api.extractWords(data);

        assertEquals(13, extractedWords.size());
    }


    @Test
    public void testSaveExcludeWords() throws Exception {
        createRegisterAndLoginUser();

        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship", "getting"
        });
        api.saveExcludeWords(excludedwords);

        Map<String, Set<String>> loadedExcludedWords = api.loadExcludedWords();
        assertEquals(5, loadedExcludedWords.size());

        Assert.assertTrue(loadedExcludedWords.containsKey("car"));
        Assert.assertTrue(loadedExcludedWords.containsKey("cat"));
        Assert.assertTrue(loadedExcludedWords.containsKey("dog"));
        Assert.assertTrue(loadedExcludedWords.containsKey("ship"));
        Assert.assertTrue(loadedExcludedWords.containsKey("get"));
    }

    @Test
    public void testSaveExcludeWordsForWordWhichNotExists() throws Exception {
        createRegisterAndLoginUser();

        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "zxcxcxzczxcsadqweqqwe"
        });
        api.saveExcludeWords(excludedwords);

        Map<String, Set<String>> loadedExcludedWords = api.loadExcludedWords();
        assertEquals(1, loadedExcludedWords.size());

        Assert.assertTrue(loadedExcludedWords.containsKey("car"));
    }


    @Test
    public void testSaveIncludedWords() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        api.saveIncludedWords(includedWords);

        Map<String, Set<String>> loadedIncludedWords = api.loadIncludedWords();
        assertEquals(4, loadedIncludedWords.size());
        Assert.assertTrue(loadedIncludedWords.containsKey("car"));
        Assert.assertTrue(loadedIncludedWords.containsKey("cat"));
        Assert.assertTrue(loadedIncludedWords.containsKey("dog"));
        Assert.assertTrue(loadedIncludedWords.containsKey("ship"));
    }

    private void createRegisterAndLoginUser() throws IOException {
        User testUser = createTestUser();
        api.register(testUser);
        api.login(testUser);
    }

    @Test
    public void testRegister() throws Exception {
        User testUser = createTestUser();
        LoginServiceResult result = api.register(testUser);
        assertEquals(LoginServiceResult.SUCCESS, result);
    }

    @Test
    public void testLogin() throws Exception {
        User testUser = createTestUser();
        api.register(testUser);

        LoginServiceResult result = api.login(testUser);

        assertEquals(LoginServiceResult.SUCCESS, result);
    }

    @Test
    public void testRemoveExcludedWord() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        api.saveExcludeWords(excludedwords);
        api.removeExcludedWord("dog");

        Map<String, Set<String>> loadedExcludedWords = api.loadExcludedWords();

        assertEquals(3, loadedExcludedWords.size());
        Assert.assertTrue(loadedExcludedWords.containsKey("car"));
        Assert.assertTrue(loadedExcludedWords.containsKey("cat"));
        Assert.assertTrue(loadedExcludedWords.containsKey("ship"));
    }

     @Test
    public void testRemoveExcludedWordAndCheckIncludedWords() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        api.saveExcludeWords(excludedwords);
        api.removeExcludedWord("dog");

        Map<String, Set<String>> loadedIncludedWords = api.loadIncludedWords();

        assertEquals(1, loadedIncludedWords.size());
        Assert.assertTrue(loadedIncludedWords.containsKey("dog"));
    }

    @Test
    public void testRemoveIncludedWord() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        api.saveIncludedWords(includedWords);
        api.removeIncludedWords("dog");

        Map<String, Set<String>> loadedIncludedWords = api.loadIncludedWords();

        assertEquals(3, loadedIncludedWords.size());

        Assert.assertTrue(loadedIncludedWords.containsKey("car"));
        Assert.assertTrue(loadedIncludedWords.containsKey("cat"));
        Assert.assertTrue(loadedIncludedWords.containsKey("ship"));
    }


     @Test
    public void testRemoveIncludedWordAndCheckInExcluded() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        api.saveIncludedWords(includedWords);
        api.removeIncludedWords("dog");

        Map<String, Set<String>> loadedExcludedWords = api.loadExcludedWords();

        assertEquals(1, loadedExcludedWords.size());
        Assert.assertTrue(loadedExcludedWords.containsKey("dog"));
    }

    @Test
    public void testOptions() throws Exception {
        createRegisterAndLoginUser();
        Configuration config = new Configuration();
        config.setMax(98);
        config.setMin(12);
        config.setTextTemplate("txt");
        config.setSubtitleTemplate("sub");
        api.saveOptions(config);
        Configuration options = api.loadOptions();

        assertEquals(98, options.getMax());
        assertEquals(12, options.getMin());
        assertEquals("txt", options.getTextTemplate());
        assertEquals("sub", options.getSubtitleTemplate());
    }


    @Test
    public void testLoadOptionsWithoutAuthentication() throws Exception {
        userService.clearAutentication();

        Configuration options = api.loadOptions();

        assertEquals(89, options.getMax());
        assertEquals(5, options.getMin());
        assertEquals("(@@TRANSLATED_TEXT@@)", options.getTextTemplate());
        assertEquals("<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>", options.getSubtitleTemplate());
    }


    @Test
    public void testGetWordsAccordingToOptionsBoundaryTest() throws Exception {
        createRegisterAndLoginUser();
        Configuration config = new Configuration();
        config.setMax(87);
        config.setMin(12);
        config.setTextTemplate("txt");
        config.setSubtitleTemplate("sub");
        api.saveOptions(config);

        DataToTranslate dataToTranslate = new DataToTranslate();
        dataToTranslate.setText("department"); //frequency = 87


        Map<String, WordDetails> results = api.extractWords(dataToTranslate);

        assertNotNull(results.get("department"));
        assertEquals(87, results.get("department").getFrequency().intValue());
    }


    @Test
    public void testDatabaseStructureAfterRemove() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car"
        });
        api.saveIncludedWords(includedWords);
        api.removeIncludedWords("car");

        assertNotNull(textService.getRootWord("car").getId());
        assertEquals(3, textService.getWordFamily("car").size());
    }

    @Test
    public void testExportsAllExcludeWordsToCSVFormat() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedwords = Arrays.asList(new String[]{
                "car", "cat"
        });
        api.saveExcludeWords(excludedwords);

        String exportedExcludedWords = api.exportExcludedWords();

        assertTrue(exportedExcludedWords.contains("car;car cars;\n"));
        assertTrue(exportedExcludedWords.contains("cat;cats cat;\n"));
    }

    @Test
    public void testTranslateWord() throws Exception {
        createRegisterAndLoginUser();

        List<String> words = Arrays.asList(new String[]{
                "donors", "donkeys"
        });

        List<String[]> translatedWords = api.translate(words);

        assertEquals("dawców", translatedWords.get(0)[0]);
        assertEquals("osły", translatedWords.get(1)[0]);
    }

    @Test
    public void testTranslateWordWithoutAuthentication() throws Exception {
        List<String> words = Arrays.asList(new String[]{
                "donors", "donkeys"
        });
        userService.clearAutentication();

        List<String[]> translatedWords = api.translate(words);

        assertEquals("dawców", translatedWords.get(0)[0]);
        assertEquals("osły", translatedWords.get(1)[0]);
    }


    @Test
    public void testExportsAllIncludeWordsToCSVFormat() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedwords = Arrays.asList(new String[]{
                "truck", "ship"
        });
        api.saveIncludedWords(excludedwords);

        String exportedExcludedWords = api.exportIncludedWords();

        assertTrue(exportedExcludedWords.contains("truck;trucks truck trucked trucking;\n"));
        assertTrue(exportedExcludedWords.contains("ship;shipping shipped ship ships;\n"));
    }

//
//    //TODO dodac test w stylu: moving in => a loaded should be move in (base form).
    // ToDO NASTEPNY TEST czy on filtruje po wordfamily ? o okreslnym frequency. Np. cat ma freq 88 cats 92 w takim razie cats nie powinien przejsc.
}
