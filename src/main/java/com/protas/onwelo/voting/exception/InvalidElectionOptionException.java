package com.protas.onwelo.voting.exception;

import com.protas.onwelo.common.BusinessRuleViolationException;

import java.util.UUID;

public final class InvalidElectionOptionException extends BusinessRuleViolationException {
    public InvalidElectionOptionException(UUID optionId, UUID electionId) {
        super(String.format("Option %s does not belong to election %s", optionId, electionId));
    }
}
