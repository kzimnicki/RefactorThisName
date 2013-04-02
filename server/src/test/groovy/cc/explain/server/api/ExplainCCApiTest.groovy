package cc.explain.server.api

import spock.lang.Unroll
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired

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
class ExplainCCApiTest {

    @Autowired
    ExplainCCApi api;

    @Unroll
   def "Should translate SRT subtitles using quick method"() {
        String subtitle = "What began as a one-time jolt in 2008, an unprecedented effort to revive economic activity, " +
                "has become an uncomfortable status quo, an enduring reality in which savers are punished and borrowers rewarded by a permafrost of low interest rates." +
                "And the Fed, acutely uneasy with this new role in the American economy, " +
                "may now find itself unable to avoid doubling down." +
                "Although Fed officials have said repeatedly that they were reluctant to expand what has already been a substantial campaign to stimulate growth, " +
                "the slowing rate of job creation suggests that they have not done enough. And there’s little prospect that Congress will rise to the occasion.";
        createRegisterAndLoginUser();


        String translated = api.quickSubtitleTranslate(subtitle);

        assertEquals(13, extractedWords.size());
    }
}
