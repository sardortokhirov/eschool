package com.home.eschool.models.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeachersPayloadForList extends TeachersPayload {

    private FilesPayload avatar;

    public TeachersPayloadForList(UUID id, String firstName, String lastName, String sureName, String dateOfBirth, String phoneNumber, String email, FilesPayload avatar) {
        super(id, firstName, lastName, sureName, dateOfBirth, phoneNumber, email);
        this.avatar = avatar;
    }
}
