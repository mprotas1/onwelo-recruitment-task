package com.protas.onwelo.election.domain;

import java.util.UUID;

public record ElectionId(UUID value) {

    public static ElectionId generate() {
        return new ElectionId(UUID.randomUUID());
    }

    public static ElectionId of(UUID value) {
        return new ElectionId(value);
    }
}
