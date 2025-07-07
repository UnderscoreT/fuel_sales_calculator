package com.blestcodestudios.fuelsalesapp.config;

import com.blestcodestudios.fuelsalesapp.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/home", "/index", "/css/**",
                                "/images/**", "/terms", "/register/**", "/js/**",
                                "/terms", "/privacy","/about","/**",
                                "/error", "/api/v1/registration/**").permitAll()
                        // Resources
                        .requestMatchers("/css/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/calculate","/contact").permitAll()
                        .requestMatchers("/acp/**").hasAnyRole("DEVELOPER", "OWNER")
                        .requestMatchers("/showBreakDownForm","/makeBreakDown").authenticated()
                        .requestMatchers("/profile").hasRole("USER")
                        .anyRequest().authenticated()
                );

        http.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout(logout -> logout.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES))));

        http.headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(getBCryptPasswordEncoder());
        return authProvider;
    }
}