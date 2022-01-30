package dachertanov.pal.palbackendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"dachertanov.pal"})
@SpringBootApplication
public class PalBackendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalBackendServiceApplication.class, args);
    }

}
