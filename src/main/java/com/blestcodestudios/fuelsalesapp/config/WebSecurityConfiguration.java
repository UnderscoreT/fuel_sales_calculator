package com.blestcodestudios.fuelsalesapp.config;

import com.blestcodestudios.fuelsalesapp.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/index", "/css/**", "/**", "/terms",
                                "/register/**", "/js/**", "/privacy", "/about", "/summary/pdf",
                                "/error", "/api/v1/registration/**", "/calculate", "/contact",
                                "/webjars/**").permitAll()
                        .requestMatchers("/acp/**").hasAnyRole("DEVELOPER", "OWNER")
                        .requestMatchers("/profile").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login") // You must have a Thymeleaf page mapped to /login
                        .permitAll()
                )

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Unified login page
                        .defaultSuccessUrl("/", true) // 🔥 Redirect to homepage after Google login
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(
                                new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)))
                        .permitAll()
                )

                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(getBCryptPasswordEncoder());
        return authProvider;
    }
}
