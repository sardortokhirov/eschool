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
public class ClassesPayloadV2 extends ClassesPayload {

    private boolean curator;

    public ClassesPayloadV2(UUID id, String name, boolean curator) {
        super(id, name);
        this.curator = curator;
    }
}
