package com.protas.onwelo.election.exception;

import com.protas.onwelo.common.ConflictException;

public final class DuplicateOptionForElectionException extends ConflictException {
    public DuplicateOptionForElectionException(String name) {
        super(String.format("Option with name %s already exists for this election", name));
    }
}
