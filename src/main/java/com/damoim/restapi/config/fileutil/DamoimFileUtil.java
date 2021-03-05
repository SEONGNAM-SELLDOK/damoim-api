package com.damoim.restapi.config.fileutil;

import com.damoim.restapi.config.fileutil.error.FileNotSaveException;
import com.damoim.restapi.config.fileutil.error.NotSupportedTypeException;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gisung go
 * @since 2021-02-22
 */
@Component
@RequiredArgsConstructor
public class DamoimFileUtil {

    private final FileProperties fileProperties;


    @Deprecated
    public String upload(MultipartFile file) {

        fileValidation(file);

        Path rootLocation = Paths.get(fileProperties.getFinalPath())
            .toAbsolutePath().normalize();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        File convFile;

        try {
            Files.createDirectories(rootLocation);
            Path targetPath = rootLocation.resolve(filename).normalize();
            convFile = new File(String.valueOf(targetPath));
            file.transferTo(convFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileProperties.getPathLast() + file.getOriginalFilename();
    }

    public String upload(RequestFile requestFile) {
        MultipartFile file = requestFile.getFile();

        fileValidation(file);

        Path rootLocation = Paths.get(fileProperties.getFinalPath() + requestFile.getRoot())
            .toAbsolutePath().normalize();
        String filename = getFilename(file);
        File convFile;

        try {
            Files.createDirectories(rootLocation);
            Path targetPath = rootLocation.resolve(filename).normalize();
            convFile = new File(String.valueOf(targetPath));
            file.transferTo(convFile);
        } catch (Exception e) {
            throw new FileNotSaveException(
                "[File Not Save] file name:" + file.getOriginalFilename());
        }
        return fileProperties.getPathLast() + requestFile.getRoot() + filename;
    }

    private String getFilename(MultipartFile file) {
        return StringUtils.cleanPath(UUID.randomUUID() + file.getOriginalFilename());
    }

    private void fileValidation(MultipartFile file) throws NotSupportedTypeException {
        String contentType = file.getContentType();
        List<String> supType = List
            .of(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE);

        if (!supType.contains(contentType)) {
            throw new NotSupportedTypeException(contentType, supType,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        }
    }
}