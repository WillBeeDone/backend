package de.willbeedone.backend.config;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {

    @Bean
    public freemarker.template.Configuration freemarkerConfiguration() {
        Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        config.setClassForTemplateLoading(this.getClass(), "/mail/");
        config.setDefaultEncoding("UTF-8");
        return config;
    }

}
