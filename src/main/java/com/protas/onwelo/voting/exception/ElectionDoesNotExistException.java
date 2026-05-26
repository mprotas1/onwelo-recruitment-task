package com.protas.onwelo.voting.exception;

import com.protas.onwelo.common.EntityNotFoundException;
import java.util.UUID;

public class ElectionDoesNotExistException extends EntityNotFoundException {
    public ElectionDoesNotExistException(UUID electionId) {
        super(String.format("Election with id %s does not exist", electionId));
    }
}
