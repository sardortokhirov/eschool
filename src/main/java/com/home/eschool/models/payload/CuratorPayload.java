package com.home.eschool.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuratorPayload {

    private UUID id;
    private String name;

    public CuratorPayload(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    private String avatar;
}
