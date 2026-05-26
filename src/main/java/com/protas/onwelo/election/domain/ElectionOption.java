package com.protas.onwelo.election.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ElectionOption {

    private final ElectionOptionId id;
    private final String name;
    private final String description;

    public static ElectionOption create(String name, String description) {
        return new ElectionOption(ElectionOptionId.generate(), name, description);
    }

    public static ElectionOption reconstitute(ElectionOptionId id, String name, String description) {
        return new ElectionOption(id, name, description);
    }
}
