package com.protas.onwelo.election.domain;

import java.util.UUID;

public record ElectionOptionId(UUID value) {

    public static ElectionOptionId generate() {
        return new ElectionOptionId(UUID.randomUUID());
    }

    public static ElectionOptionId of(UUID value) {
        return new ElectionOptionId(value);
    }
}
