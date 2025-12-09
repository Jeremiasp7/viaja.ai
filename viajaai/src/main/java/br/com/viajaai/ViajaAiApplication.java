package br.com.viajaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "br.com.viajaai",
    "br.com.planejaai.framework.entity"
})
@ComponentScan(basePackages = {
    "br.com.viajaai",
    "br.com.planejaai.framework"
})
@EnableJpaRepositories(basePackages = {
    "br.com.viajaai",
    "br.com.planejaai.framework.repository"
})
public class ViajaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajaAiApplication.class, args);
    }
}