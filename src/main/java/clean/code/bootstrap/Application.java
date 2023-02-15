package clean.code.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "clean.code")
@EnableMongoRepositories(basePackages = "clean.code.server.repository")
public class Application {
    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", "/api/v1");
        SpringApplication.run(Application.class, args);
    }
}
