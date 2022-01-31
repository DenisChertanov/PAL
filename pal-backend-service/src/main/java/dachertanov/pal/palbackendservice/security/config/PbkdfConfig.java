package dachertanov.pal.palbackendservice.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class PbkdfConfig {
    @Value("${pal.security.pbkdf.secret-key}")
    private String secretKey = "pepper";

    @Value("${pal.security.pbkdf.iterations}")
    private int iterations;

    @Value("${pal.security.pbkdf.hash-width}")
    private int hashWidth;

    @Bean
    public Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(secretKey, iterations, hashWidth);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);

        return pbkdf2PasswordEncoder;
    }
}
