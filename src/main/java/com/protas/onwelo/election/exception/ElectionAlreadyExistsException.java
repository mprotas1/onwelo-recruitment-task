package com.protas.onwelo.election.exception;

import com.protas.onwelo.common.ConflictException;

public final class ElectionAlreadyExistsException extends ConflictException {
    public ElectionAlreadyExistsException(String name) {
        super(String.format("Election with name %s already exists", name));
    }
}
