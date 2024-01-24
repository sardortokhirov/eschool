package com.home.eschool.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDto {

    private UUID id;

    @NotNull(message = "Name is mandatory")
    private String name;

    private UUID curator_id;
}
