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
public class ClassesCuratorPayload extends ClassesPayload {

    private CuratorPayload curator;

    public ClassesCuratorPayload(UUID id, String name, CuratorPayload curator) {
        super(id, name);
        this.curator = curator;
    }
}
