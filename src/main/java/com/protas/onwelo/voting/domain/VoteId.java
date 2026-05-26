package com.protas.onwelo.voting.domain;

import java.util.UUID;

public record VoteId(UUID value) {

    public static VoteId generate() {
        return new VoteId(UUID.randomUUID());
    }
}
