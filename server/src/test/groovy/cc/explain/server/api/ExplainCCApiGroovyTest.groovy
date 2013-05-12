package cc.explain.server.api

import spock.lang.Unroll
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

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
class ExplainCCApiGroovyTest extends Specification{

    @Autowired
    ExplainCCApi api;

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
          |""".stripMargin()  == translated.stripMargin()
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
          |""".stripMargin()  == translated.stripMargin()
    }


    @Unroll
   def """Should translate 'staring down' not 'staring' """() {

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
            |""".stripMargin()  == translated.stripMargin()
    }



}
