package br.com.viajaai.config;

import br.com.planejaai.framework.config.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ViajaAiSecurityConfig {

  private final SecurityConfig frameworkSecurityConfig;

  public ViajaAiSecurityConfig(SecurityConfig frameworkSecurityConfig) {
    this.frameworkSecurityConfig = frameworkSecurityConfig;
  }

  @Bean
  @Primary
  public SecurityFilterChain viajaAiFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(frameworkSecurityConfig.corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    
                    .anyRequest().authenticated());
    
    return http.build();
  }
}