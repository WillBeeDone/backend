package de.willbeedone.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("fake-profile")
public class DOConfig {

    @Value("{LDB_USERNAME}")
    private String username;
    @Value("{LDB_PASSWORD}")
    private String password;
    @Value("{LDB_HOST}")
    private String host;
    @Value("{LDB_PORT}")
    private String port;
    @Value("{LDB_NAME}")
    private String dbName;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url("jdbc:mysql://" + host + ":" + port + "/" + dbName)
                .username(username)
                .password(password).build();
    }
}
