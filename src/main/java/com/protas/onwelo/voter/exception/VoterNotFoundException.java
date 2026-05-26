package com.protas.onwelo.voter.exception;

import com.protas.onwelo.common.EntityNotFoundException;

import java.util.UUID;

public final class VoterNotFoundException extends EntityNotFoundException {
    public VoterNotFoundException(UUID id) {
        super(String.format("Voter with id %s not found", id));
    }
}
