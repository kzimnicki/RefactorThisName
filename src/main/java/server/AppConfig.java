package server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import server.api.EnglishTranslator;
import server.core.WordExtractor;

/**
 * User: kzimnick
 * Date: 26.03.12
 * Time: 21:21
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "server")
public class AppConfig {

    @Bean
    public WordExtractor wordExtractor() {
        return new WordExtractor();
    }
}