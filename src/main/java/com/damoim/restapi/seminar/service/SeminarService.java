package com.damoim.restapi.seminar.service;

import com.damoim.restapi.config.DamoimFileUtil;
import com.damoim.restapi.seminar.dao.SeminarMapper;
import com.damoim.restapi.seminar.dao.SeminarRepository;
import com.damoim.restapi.seminar.entity.Address;
import com.damoim.restapi.seminar.entity.Seminar;
import com.damoim.restapi.seminar.model.ModifySeminarRequest;
import com.damoim.restapi.seminar.model.ReadSeminarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    private final SeminarMapper seminarMapper;

    public Long save(Seminar seminar) {
        seminarRepository.save(seminar);
        return seminar.getId();
    }

    public Optional<Seminar> findById(Long id) {
        return seminarRepository.findById(id);
    }

    public Long modify(Long id, ModifySeminarRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Optional<Seminar> seminar = seminarRepository.findById(id);
        seminar.ifPresent(existingSeminar -> {
            existingSeminar.setTitle(request.getTitle());
            existingSeminar.setContent(request.getContent());
            existingSeminar.setImage(request.getImage());
            existingSeminar.setAddress(address);
            existingSeminar.setSubject(request.getSubject());
            existingSeminar.setDamoimTag(request.getDamoimTag());
            existingSeminar.setEndDate(request.getEndDate());
            seminarRepository.save(existingSeminar);
        });

        return id;
    }

    public void delete(Long id) {
        seminarRepository.deleteById(id);
    }

    public List<Seminar> getList(ReadSeminarResponse response) {
        return seminarMapper.getSeminar(response);
    }

    public String saveUploadFile(MultipartFile upload_file) { return damoimFileUtil.upload(upload_file); }

}
