package ru.shchetinin.groupmanager.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.shchetinin.groupmanager.enums.roles.RoleCheck;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        //password: 12345
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        PathRequest.toStaticResources().atCommonLocations()
                                )
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/registration", HttpMethod.POST.name()))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/login"))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/activation/*"))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/h2-console/**"))
                                .hasRole(RoleCheck.ADMIN.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/swagger-ui/index.html", HttpMethod.GET.name()))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/v3/api-docs", HttpMethod.GET.name()))
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/home", HttpMethod.GET.name() ))
                                .hasAnyRole(RoleCheck.USER.name(), RoleCheck.ADMIN.name())
                                .anyRequest().hasRole(RoleCheck.ADMIN.name())
                )
                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/home", true))
                .csrf(csrf -> {
                    csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"));
                })
                .cors(withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

}