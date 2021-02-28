package com.damoim.restapi.fileUtil;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Component
@RequiredArgsConstructor
public class DamoimFileUtil {
    private  final FileProperties fileProperties;
    public String upload(MultipartFile file) throws FileNotSave {
        Path rootLocation = Paths.get(fileProperties.getFinalPath())
            .toAbsolutePath().normalize();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        File convFile;
        try {
            Files.createDirectories(rootLocation);
            Path targetPath = rootLocation.resolve(filename).normalize();
            convFile = new File(String.valueOf(targetPath));
            file.transferTo(convFile);
        } catch (IOException e) {
            //임시
            throw new FileNotSave();
        }
        return fileProperties.getPathLast() + file.getOriginalFilename();
    }
}
