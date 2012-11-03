package cc.explain.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * User: kzimnick
 * Date: 18.04.12
 * Time: 10:05
 */
@Configuration
@ComponentScan(basePackages = "server")
@ImportResource({"classpath:/spring-security.xml"})
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

}