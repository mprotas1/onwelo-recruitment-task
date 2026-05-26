package com.protas.onwelo.election.exception;

import com.protas.onwelo.common.EntityNotFoundException;
import java.util.UUID;

public final class ElectionNotFoundException extends EntityNotFoundException {
    public ElectionNotFoundException(UUID id) {
        super(String.format("Election with id %s not found", id));
    }
}
