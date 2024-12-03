package com.example.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secret")
public class SecretKeyConfig {
    private String secretKey;

    public String getKey(){
            return secretKey;
    }
    public void setKey(String key) {
        this.secretKey = key;
    }
}
