package br.com.treinaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.treinaai", "br.com.planejaai.framework"})
@ComponentScan(basePackages = {"br.com.treinaai", "br.com.planejaai.framework"})
@EntityScan({"br.com.treinaai.entities", "br.com.planejaai.framework.entity"})
@EnableJpaRepositories({"br.com.treinaai.repositories", "br.com.planejaai.framework.repository"})
public class TreinaAiApplication {
  public static void main(String[] args) {
    SpringApplication.run(TreinaAiApplication.class, args);
  }
}
