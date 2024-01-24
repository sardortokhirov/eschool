package com.home.eschool.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDto {

    private String fullName;
    private String login;
    private String password;
    private UUID role_id;
}
