package cc.explain.server.subtitle

import spock.lang.Specification
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import cc.explain.server.api.SubtitleService
import spock.lang.Unroll
import org.springframework.test.context.ContextConfiguration
import java.util.concurrent.ConcurrentHashMap
import cc.explain.server.xml.Subtitle

public class SubtitleServiceTest extends Specification {

//    @Autowired
//    SubtitleService subtitlesService;

    @Unroll
    def "Should add translations to SRT subtitles"() {
        given:
        def translations = ["started":"wystartował", "house":"dom"]
        def subtitle = """4
                          |00:00:10,622 --> 00:00:12,484
                          |The night started like any other.
                          |
                          |5
                          |00:00:12,584 --> 00:00:14,131
                          |We were downstairs at the Bar.
                          |
                          |6
                          |00:00:18,239 --> 00:00:19,408
                          |On the house.
                          """.stripMargin()

        when:
        def translatedSubtitle = new SubtitleService().addTranslation(subtitle, translations, "(@@TRANSLATED_WORD@@)")

        then:
        translatedSubtitle.stripMargin() == """4
                                              |00:00:10,622 --> 00:00:12,484
                                              |The night started (wystartował) like any other.
                                              |
                                              |
                                              |5
                                              |00:00:12,584 --> 00:00:14,131
                                              |We were downstairs at the Bar.
                                              |
                                              |6
                                              |00:00:18,239 --> 00:00:19,408
                                              |On the house(dom).
                                              """.stripMargin()


    }
}