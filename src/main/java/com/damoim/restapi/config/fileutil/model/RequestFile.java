package com.damoim.restapi.config.fileutil.model;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RequestFile {

    private final String root;
    private final MultipartFile file;

    private RequestFile(String root, MultipartFile file) {
        if (!root.startsWith("/")) {
            root = "/" + root;
        }
        if (!root.endsWith("/")) {
            root = root + "/";
        }
        this.root = root;
        this.file = file;
    }

    public static RequestFile of(String root, MultipartFile file) {
        return new RequestFile(root, file);
    }
}
