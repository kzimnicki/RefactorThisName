package test.java.server.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.api.*;
import server.core.WordExtractor;
import server.model.newModel.PhrasalVerb;
import server.model.newModel.User;
import server.model.newModel.WordFamily;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
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
//        commonDao.executeHQL("DELETE FROM UserExcludeWord uew", null, null);
//        commonDao.executeHQL("DELETE FROM Word w", null, null);
        commonDao.executeSQL("DELETE FROM user_excludedphrasalverbs");
        commonDao.executeSQL("DELETE FROM user_includedphrasalverbs");
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user_excludedwords");
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user");
//        commonDao.executeHQL("DELETE FROM User u WHERE u.username=:username", "username", "testuser3@gmail.com");

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
        data.setMinFrequency(0);
        data.setMaxFrequency(90);
        data.setTextUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


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
        data.setMinFrequency(0);
        data.setMaxFrequency(90);
        data.setTextUrl("http://www.nytimes.com/2012/06/02/business/jobs-report-makes-federal-reserve-more-likely-to-act.html?hp");


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
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveExcludeWords(excludedwords);

        List<WordFamily> loadedExcludedWords = englishTranslator.getExcludedWords();
        assertEquals(4, loadedExcludedWords.size());
        assertEquals("car", loadedExcludedWords.get(0).getRoot().getValue());
        assertEquals("cat", loadedExcludedWords.get(1).getRoot().getValue());
        assertEquals("dog", loadedExcludedWords.get(2).getRoot().getValue());
        assertEquals("ship", loadedExcludedWords.get(3).getRoot().getValue());
    }


    @Test
    public void testSaveIncludedWords() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveIncludedWords(includedWords);

        List<WordFamily> loadedIncludedWords = englishTranslator.loadIncludedWords();
        assertEquals(4, loadedIncludedWords.size());
        assertEquals("car", loadedIncludedWords.get(0).getRoot().getValue());
        assertEquals("cat", loadedIncludedWords.get(1).getRoot().getValue());
        assertEquals("dog", loadedIncludedWords.get(2).getRoot().getValue());
        assertEquals("ship", loadedIncludedWords.get(3).getRoot().getValue());
    }

    @Test
    public void testSaveIncludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> phrasalVerbs = Arrays.asList(new String[]{
                "move in", "move out", "get rid of"
        });
        englishTranslator.saveIncludedPhrasalVerbs(phrasalVerbs);

         List<PhrasalVerb> includedPhrasalVerbs = englishTranslator.loadIncludedPhrasalVerbs();
         assertEquals(3,  includedPhrasalVerbs.size());

        assertEquals("move", includedPhrasalVerbs.get(1).getVerb().getRoot().getValue());
        assertEquals("in", includedPhrasalVerbs.get(1).getSuffix1().getValue());

        assertEquals("move", includedPhrasalVerbs.get(2).getVerb().getRoot().getValue());
        assertEquals("out", includedPhrasalVerbs.get(2).getSuffix1().getValue());

        assertEquals("get", includedPhrasalVerbs.get(0).getVerb().getRoot().getValue());
        assertEquals("rid", includedPhrasalVerbs.get(0).getSuffix1().getValue());
        assertEquals("of", includedPhrasalVerbs.get(0).getSuffix2().getValue());
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

        List<WordFamily> loadedExcludedWords = englishTranslator.getExcludedWords();

        assertEquals(3, loadedExcludedWords.size());
        assertEquals("car", loadedExcludedWords.get(0).getRoot().getValue());
        assertEquals("cat", loadedExcludedWords.get(1).getRoot().getValue());
        assertEquals("ship", loadedExcludedWords.get(2).getRoot().getValue());

    }

    @Test
    public void testRemoveIncludedWord() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "car", "dog", "cat", "ship"
        });
        englishTranslator.saveIncludedWords(includedWords);
        englishTranslator.removeIncludedWords("dog");

        List<WordFamily> loadedIncludedWords = englishTranslator.loadIncludedWords();

        assertEquals(3, loadedIncludedWords.size());
        assertEquals("car", loadedIncludedWords.get(0).getRoot().getValue());
        assertEquals("cat", loadedIncludedWords.get(1).getRoot().getValue());
        assertEquals("ship", loadedIncludedWords.get(2).getRoot().getValue());
    }

     @Test
    public void testRemoveIncludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> includedWords = Arrays.asList(new String[]{
                "move in", "move out", "get rid of"
        });
        englishTranslator.saveIncludedPhrasalVerbs(includedWords);
        englishTranslator.removeIncludedPhrasalVerbs("move out");

        List<PhrasalVerb> includedPhrasalVerbs = englishTranslator.loadIncludedPhrasalVerbs();

         assertEquals(2, includedPhrasalVerbs.size());

        assertEquals("move", includedPhrasalVerbs.get(1).getVerb().getRoot().getValue());
        assertEquals("in", includedPhrasalVerbs.get(1).getSuffix1().getValue());

        assertEquals("get", includedPhrasalVerbs.get(0).getVerb().getRoot().getValue());
        assertEquals("rid", includedPhrasalVerbs.get(0).getSuffix1().getValue());
        assertEquals("of", includedPhrasalVerbs.get(0).getSuffix2().getValue());
    }

     @Test
    public void testRemoveExcludedPhrasalVerb() throws Exception {
        createRegisterAndLoginUser();
        List<String> excludedWords = Arrays.asList(new String[]{
                "move in", "move out", "get rid of"
        });
        englishTranslator.saveExcludedPhrasalVerbs(excludedWords);
        englishTranslator.removeExcludedPhrasalVerbs("get rid of");

        List<PhrasalVerb> excludedPhrasalVerbs = englishTranslator.loadExcludedPhrasalVerbs();

         assertEquals(2, excludedPhrasalVerbs.size());

        assertEquals("move", excludedPhrasalVerbs.get(1).getVerb().getRoot().getValue());
        assertEquals("out", excludedPhrasalVerbs.get(1).getSuffix1().getValue());

        assertEquals("move", excludedPhrasalVerbs.get(0).getVerb().getRoot().getValue());
        assertEquals("in", excludedPhrasalVerbs.get(0).getSuffix1().getValue());
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
