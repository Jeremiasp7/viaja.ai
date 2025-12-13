package br.com.viajaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "br.com.viajaai",
    "br.com.viajaai.controllers",
    "br.com.viajaai.services",
    "br.com.viajaai.configs",
    "br.com.planejaai.framework"
})
@EntityScan({
    "br.com.viajaai.entities",
    "br.com.planejaai.framework.entity"
})
@EnableJpaRepositories({
    "br.com.viajaai.repositories",
    "br.com.planejaai.framework.repository"
})
public class ViajaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajaAiApplication.class, args);
    }
}