package com.damoim.restapi.seminar.service;

import com.damoim.restapi.config.DamoimFileUtil;
import com.damoim.restapi.seminar.dao.SeminarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Service
@Transactional
@RequiredArgsConstructor
public class SeminarService {
    private final SeminarRepository seminarRepository;
    private final DamoimFileUtil damoimFileUtil;

    public String saveUploadFile(MultipartFile upload_file) { return damoimFileUtil.upload(upload_file); }

}
