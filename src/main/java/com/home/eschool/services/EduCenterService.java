package com.home.eschool.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.home.eschool.entity.EduCenter;
import com.home.eschool.models.dto.EduCenterDto;

import com.home.eschool.models.payload.EduCenterPayload;

import com.home.eschool.repository.EduCenterRepo;
import com.home.eschool.utils.Settings;
import com.home.eschool.utils.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-11:38 AM (GMT+5)
 */
@Service
public class EduCenterService {
    private final EduCenterRepo eduCenterRepo;

    public EduCenterService(EduCenterRepo eduCenterRepo) {
        this.eduCenterRepo = eduCenterRepo;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void create(EduCenterDto eduCenterDto) {
        EduCenter eduCenter = new EduCenter();
        eduCenter.setId(UUID.randomUUID());
        eduCenter.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        eduCenter.setCreateUser(Settings.getCurrentUser());
        eduCenter.setContacts(eduCenterDto.getContacts());
        eduCenter.setLocation(eduCenterDto.getLocation());
        eduCenter.setAddress(eduCenterDto.getAddress());
        eduCenter.setTitle(eduCenterDto.getTitle());

        if (eduCenterDto.getLinks() != null) {
            eduCenter.setLinks(eduCenterDto.getLinks().toString());
        }
        eduCenterRepo.save(eduCenter);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void update(EduCenterDto eduCenterDto) {

        EduCenter eduCenter = eduCenterRepo.findById(eduCenterDto.getId()).orElse(null);
        if (eduCenter == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect Education Center Id");
        }
        eduCenter.setChangeDate(Timestamp.valueOf(LocalDateTime.now()));
        eduCenter.setChangeUser(Settings.getCurrentUser());
        eduCenter.setLocation(eduCenterDto.getLocation());
        eduCenter.setContacts(eduCenterDto.getContacts());
        eduCenter.setAddress(eduCenterDto.getAddress());
        eduCenter.setTitle(eduCenterDto.getTitle());

        if (eduCenterDto.getLinks() != null) {
            eduCenter.setLinks(eduCenterDto.getLinks().toString());
        }
        eduCenterRepo.save(eduCenter);
    }

    public List<EduCenter> getAll(String search) {
        if (!Utils.isEmpty(search))
            return eduCenterRepo.findByTitleContainingIgnoreCase(search);
        return eduCenterRepo.findAll();
    }

    public EduCenterPayload getById(UUID id) {
        EduCenter eduCenter = eduCenterRepo.findById(id).orElse(null);
        if (eduCenter == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect Education Center Id");
        }
        return new EduCenterPayload(
                eduCenter.getId(), eduCenter.getTitle(), eduCenter.getAddress(), eduCenter.getContacts(), eduCenter.getLocation(), Utils.convertToObject(eduCenter.getLinks(), JsonNode.class)
        );
    }
//    @Transactional(rollbackFor = Throwable.class)
//    public void delete(List<UUID> eduCenters) {
//
//    }
}
