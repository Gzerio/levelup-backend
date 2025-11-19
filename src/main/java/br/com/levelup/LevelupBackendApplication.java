package br.com.levelup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LevelupBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LevelupBackendApplication.class, args);
    }
}
