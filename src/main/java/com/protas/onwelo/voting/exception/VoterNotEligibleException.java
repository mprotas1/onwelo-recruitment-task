package com.protas.onwelo.voting.exception;

import com.protas.onwelo.common.BusinessRuleViolationException;

import java.util.UUID;

public final class VoterNotEligibleException extends BusinessRuleViolationException {
    public VoterNotEligibleException(UUID voterId) {
        super(String.format("Voter %s is not eligible to vote", voterId));
    }
}
