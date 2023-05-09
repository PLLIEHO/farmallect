package ru.nutsalhan87.farmallect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class FarmallectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmallectApplication.class, args);
    }
}
