package com.home.eschool.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-3:20 PM (GMT+5)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EduCenterPayload {
    private UUID id;

    private String title;
    private String address;

    private String contacts;

    private String location;

    private Object links;
}
