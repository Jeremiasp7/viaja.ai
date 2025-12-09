package br.com.estudaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.planejaai.framework", "br.com.estudaai"})
@EnableJpaRepositories(
    basePackages = {"br.com.planejaai.framework.repository", "br.com.estudaai.repository"})
@EntityScan(
    basePackages = {
      "br.com.planejaai.framework.entity",
      "br.com.estudaai.entity",
    })
@ComponentScan(basePackages = {"br.com.planejaai.framework", "br.com.estudaai"})
public class EstudaAiApplication {
  public static void main(String[] args) {
    SpringApplication.run(EstudaAiApplication.class, args);
  }
}
