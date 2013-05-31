package cc.explain.server.api

import cc.explain.server.core.CommonDao
import cc.explain.server.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll
import cc.explain.server.subtitle.SubtitleProcessor

/**
 * User: kzimnick
 * Date: 02.04.13
 * Time: 08:59
 */
@ContextConfiguration(locations = [
"classpath:spring-context.xml",
"classpath:spring-dao.xml",
"classpath:test-spring-dataSource.xml",
"classpath:spring-security.xml",
"classpath:spring-tx.xml"
])
class ExplainCCApiGroovyTest extends Specification {

    @Autowired
    ExplainCCApi api;

    @Autowired
    CommonDao commonDao;

    @Autowired
    UserService userService;

    def setup() {
        cleanDatabase()
        createRegisterAndLoginUser()
    }

    def cleanDatabase() {
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user_excludedwords");
        commonDao.executeSQL("DELETE FROM user_includedwords");
        commonDao.executeSQL("DELETE FROM user");

    }

    def cleanup() {
        cleanDatabase()
    }

    def createRegisterAndLoginUser() {
        User testUser = createTestUser();
        api.register(testUser);
        api.login(testUser);
        def config = testUser.getConfig()
        config.setPhrasalVerbAdded(true);
        api.saveOptions(config);
    }

    def createTestUser() {
        User user = new User();
        user.setUsername("testuser3@gmail.com");
        user.setPassword("123456");
        return user;
    }



    @Unroll
    def "Should translate SRT subtitles using quick method"() {
        given:
        def subtitle = """4
                          |00:00:10,622 --> 00:00:12,484
                          |Please get up like any other.
                          |
                          |5
                          |00:00:12,584 --> 00:00:14,131
                          |We were downstairs at the Bar.
                          |
                          |6
                          |00:00:18,239 --> 00:00:19,408
                          |On the house.
                          |
                          |
                          """.stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """4
          |00:00:10,622 --> 00:00:12,484
          |Please get up like any other.
          | <font color="red">get up = wstać</font>
          |
          |5
          |00:00:12,584 --> 00:00:14,131
          |We were downstairs <font color="yellow">(na dół)</font> at the Bar.
          |
          |6
          |00:00:18,239 --> 00:00:19,408
          |On the house.
          |
          |""".stripMargin() == translated.stripMargin()
    }

    @Unroll
    def "Should translate SRT subtitles and add phrasal verbs only for case insesitive"() {
        given:
        def subtitle = """4
                          |00:00:10,622 --> 00:00:12,484
                          |Please get up like any other.
                          |
                          |5
                          |00:00:12,584 --> 00:00:14,131
                          |We were downstairs at the Bar, so Get up man!.
                          |
                          |6
                          |00:00:18,239 --> 00:00:19,408
                          |On the house.
                          |
                          |
                          """.stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """4
          |00:00:10,622 --> 00:00:12,484
          |Please get up like any other.
          | <font color="red">get up = wstać</font>
          |
          |5
          |00:00:12,584 --> 00:00:14,131
          |We were downstairs <font color="yellow">(na dół)</font> at the Bar, so Get up man!.
          | <font color="red">get up = wstać</font>
          |
          |6
          |00:00:18,239 --> 00:00:19,408
          |On the house.
          |
          |""".stripMargin() == translated.stripMargin()
    }

    @Unroll
    def "Should translate SRT subtitles and add phrasal verbs only for certain form: 'get up' not 'getting up'"() {
        given:
        def subtitle = """4
                          |00:00:10,622 --> 00:00:12,484
                          |Please get up like any other.
                          |
                          |5
                          |00:00:12,584 --> 00:00:14,131
                          |We were downstairs at the Bar, so getting up
                          |
                          |6
                          |00:00:18,239 --> 00:00:19,408
                          |On the house.
                          |
                          |
                          """.stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """4
          |00:00:10,622 --> 00:00:12,484
          |Please get up like any other.
          | <font color="red">get up = wstać</font>
          |
          |5
          |00:00:12,584 --> 00:00:14,131
          |We were downstairs <font color="yellow">(na dół)</font> at the Bar, so getting up
          | <font color="red">getting up = wstawania</font>
          |
          |6
          |00:00:18,239 --> 00:00:19,408
          |On the house.
          |
          |""".stripMargin() == translated.stripMargin()
    }

    @Unroll
    def "Should translate only words for user with turn off phrasal vebs in configuration ' "() {
        given:
        def user = userService.getLoggedUser()
        def config = user.getConfig();
        config.setPhrasalVerbAdded(false);
        api.saveOptions(config);

        def subtitle = """4
                           |00:00:10,622 --> 00:00:12,484
                           |Please get up like any other.
                           |
                           |5
                           |00:00:12,584 --> 00:00:14,131
                           |We were downstairs at the Bar, so getting up
                           |
                           |6
                           |00:00:18,239 --> 00:00:19,408
                           |On the house.
                           |
                           |
                           """.stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """4
           |00:00:10,622 --> 00:00:12,484
           |Please get up like any other.
           |
           |5
           |00:00:12,584 --> 00:00:14,131
           |We were downstairs <font color="yellow">(na dół)</font> at the Bar, so getting up
           |
           |6
           |00:00:18,239 --> 00:00:19,408
           |On the house.
           |
           |""".stripMargin() == translated.stripMargin()
    }

    @Unroll
    def "Should translate 'staring down' not 'staring'"() {

        given:
        def subtitle = """9
                        |00:00:25,004 --> 00:00:27,789
                        |Right now, Howard's
                        |staring down at our boat
                        |""".stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """9
            |00:00:25,004 --> 00:00:27,789
            |Right now, Howard's
            |staring down at our boat
            | <font color="red">staring down = patrząc w dół</font>
            |
            |""".stripMargin() == translated.stripMargin()
    }

    @Unroll
    def "Should quick translate using ONLY TRANSLATION configuration option"() {
        given:
        def user = userService.getLoggedUser()
        def config = user.getConfig();
        config.setPhrasalVerbAdded(false);
        config.setSubtitleProcessor(SubtitleProcessor.ONLY_TRANSLATION);
        api.saveOptions(config);

        def subtitle = """5
                           |00:00:12,584 --> 00:00:14,131
                           |We were downstairs at the Bar, so getting up
                           |
                           """.stripMargin()

        when:
        String translated = api.quickSubtitleTranslate(subtitle);

        then:
        """5
           |00:00:12,584 --> 00:00:14,131
           |downstairs = na dół
           |
           |
           |""".stripMargin() == translated.stripMargin()
    }


}
