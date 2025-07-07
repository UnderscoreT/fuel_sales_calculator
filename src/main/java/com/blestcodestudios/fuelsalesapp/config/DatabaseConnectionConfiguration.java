package com.blestcodestudios.fuelsalesapp.config;

import com.blestcodestudios.fuelsalesapp.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(DatabaseConfiguration.class)
public class DatabaseConnectionConfiguration {

    private final DatabaseConfiguration databaseConfiguration;

    @Profile("dev")
    @Bean
    public String getDevConnectionConfig() {
        Logger.getLogger().printMessage("Configuration for Dev");
        var message = "URL: %s%nDriver Class Name: %s%nUsername: %s%nPassword: %s%n".formatted(
                databaseConfiguration.getUrl(),
                databaseConfiguration.getDriverClassName(),
                databaseConfiguration.getUsername(),
                databaseConfiguration.getPassword()
        );
        Logger.getLogger().printMessage(message);
        return  "Database Configured for development";
    }

    @Profile("prod")
    @Bean
    public String getProdConnectionConfig() {
        Logger.getLogger().printMessage("Configuration for Prod");
        var message = "URL: %s%nDriver Class Name: %s%nUsername: %s%nPassword: %s%n".formatted(
                databaseConfiguration.getUrl(),
                databaseConfiguration.getDriverClassName(),
                databaseConfiguration.getUsername(),
                databaseConfiguration.getPassword()
        );
        Logger.getLogger().printMessage(message);
        return  "Database Configured for production";
    }

    @Profile("default")
    @Bean
    public String getDefaultConnectionConfig() {
        Logger.getLogger().printMessage("Configuration for default");
        var message = "URL: %s%nDriver Class Name: %s%nUsername: %s%nPassword: %s%n".formatted(
                databaseConfiguration.getUrl(),
                databaseConfiguration.getDriverClassName(),
                databaseConfiguration.getUsername(),
                databaseConfiguration.getPassword()
        );
        Logger.getLogger().printMessage(message);
        return  "Database Configured for default.";
    }

}
