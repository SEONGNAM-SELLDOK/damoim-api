package com.damoim.restapi.config.fileutil;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gisung go
 * @since 2021-02-22
 */

@Getter
@Setter
@Component
@ConfigurationProperties("damoim.file")
public class FileProperties {

    private String pathPrefix;
    private String pathLast;

    public String getFinalPath() {
        return pathPrefix + pathLast;
    }
}
