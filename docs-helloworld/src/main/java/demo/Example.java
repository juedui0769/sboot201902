package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * copy at 2019年04月08日10:37:21，
 * <p>
 * https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#getting-started-first-application
 * </p>
 * <p>
 * 11.3 Writing the Code
 * </p>
 */
@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }
}
