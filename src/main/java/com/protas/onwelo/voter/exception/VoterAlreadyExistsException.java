package com.protas.onwelo.voter.exception;

import com.protas.onwelo.common.ConflictException;

public final class VoterAlreadyExistsException extends ConflictException {
    public VoterAlreadyExistsException(String email) {
        super(String.format("Voter with email %s already exists", email));
    }
}
