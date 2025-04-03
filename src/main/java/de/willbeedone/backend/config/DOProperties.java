package de.willbeedone.backend.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "do")
@NoArgsConstructor
@Getter
@Setter
public class DOProperties {

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String region;
}
