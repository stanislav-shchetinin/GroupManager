package ru.shchetinin.groupmanager;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(
                        new AntPathRequestMatcher("/actuator/**")
                ).permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")
                ).permitAll()
                .anyRequest().authenticated()
        );
        http.csrf((csrf) ->
                csrf.ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")
                ).csrfTokenRepository(
                        CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
        );
        http.headers((headers) -> headers
                .frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable
                )
        );
        return http.build();
    }
}
