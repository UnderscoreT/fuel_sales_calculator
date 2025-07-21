package com.blestcodestudios.fuelsalesapp.config;

import com.blestcodestudios.fuelsalesapp.security.JwtAuthenticationFilter;
import com.blestcodestudios.fuelsalesapp.security.JwtUtil;
import com.blestcodestudios.fuelsalesapp.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                // –– Ensure Spring uses your DAO + BCrypt provider ––
                .authenticationProvider(authenticationProvider())

                // –– JWT filter for /api/** ––
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                )

                // –– URL rules ––
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/", "/home", "/index", "/css/**", "/**", "/terms",
                                "/register/**", "/js/**", "/privacy", "/about",
                                "/summary/pdf","/error", "/api/v1/registration/**",
                                "/calculate", "/contact", "/webjars/**","/**","/*.png"
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated()

                        .requestMatchers("/acp/**").hasAnyRole("DEVELOPER","OWNER")
                        .requestMatchers("/profile").hasRole("USER")
                        .anyRequest().authenticated()
                )

                // –– Form‑login for your UI ––
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")    // <-- new
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // –– OAuth2 for your UI ––
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )

                // –– Logout ––
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(
                                new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)
                        ))
                        .permitAll()
                )

                // –– Frames (H2‑console etc.) ––
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // –– Session policy (JWT stateless on /api, but form‑login still works) ––
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
        ;

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
