package com.damoim.restapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Configuration
@ConfigurationProperties(prefix = "damoim.file")
@Getter
@Setter
public class FileProperties {
    private String pathPrefix;
    private String pathLast;

    public String getFinalPath(){
        return pathPrefix + pathLast;
    }
}
