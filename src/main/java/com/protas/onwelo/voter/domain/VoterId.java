package com.protas.onwelo.voter.domain;

import java.util.UUID;

public record VoterId(UUID value) {

    public static VoterId generate() {
        return new VoterId(UUID.randomUUID());
    }

    public static VoterId of(UUID value) {
        return new VoterId(value);
    }
}
