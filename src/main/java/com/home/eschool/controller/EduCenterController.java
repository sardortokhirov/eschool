package com.home.eschool.controller;

import com.home.eschool.models.dto.EduCenterDto;
import com.home.eschool.models.payload.EduCenterPayload;
import com.home.eschool.services.EduCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-11:40 AM (GMT+5)
 */

@RestController
@RequestMapping("/api/v1/edu-center")
@Tag(name = "Education Centers", description = "O'quv Markazlari  bo'yicha CRUD")
@CrossOrigin(origins = "*")
public class EduCenterController {

    private final EduCenterService eduCenterService;

    public EduCenterController(EduCenterService eduCenterService) {
        this.eduCenterService = eduCenterService;
    }

    @PostMapping("/create")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @Secured("ROLE_ADMIN")
    public void createEduCenter(@Valid @RequestBody EduCenterDto eduCentersDto) {
        eduCenterService.create(eduCentersDto);
    }

    @PostMapping("/update")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @Secured("ROLE_ADMIN")
    public void updateEduCenter(@Valid @RequestBody EduCenterDto eduCentersDto) {
        eduCenterService.update(eduCentersDto);
    }

    @GetMapping("/")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @Secured("ROLE_ADMIN")
    public List<EduCenterPayload> getAll(@RequestParam(required = false, name = "search", defaultValue = "") String search) {
        return eduCenterService.getAll(search);
    }

    @GetMapping("/getById/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @Secured("ROLE_ADMIN")
    public EduCenterPayload getById(@PathVariable("id") UUID id) {
        return eduCenterService.getById(id);
    }

//    @PostMapping("/delete")
//    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
//    @Secured("ROLE_ADMIN")
//    public void delete(@RequestBody List<UUID> eduCenters) {
//        eduCenterService.delete(eduCenters);
//    }

}
