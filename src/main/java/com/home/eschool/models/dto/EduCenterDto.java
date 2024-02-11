package com.home.eschool.models.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-11:44 AM (GMT+5)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EduCenterDto {

    private UUID id;

    @NotNull
    @Schema(required = true)
    private String title;
    @NotNull
    @Schema(required = true)
    private String address;

    @NotNull
    @Schema(required = true)
    private String contacts;

    @NotNull
    @Schema(required = true)
    private String location;

    private JsonNode links;
}
