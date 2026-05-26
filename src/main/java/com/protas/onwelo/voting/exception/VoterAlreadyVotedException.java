package com.protas.onwelo.voting.exception;

import com.protas.onwelo.common.ConflictException;

import java.util.UUID;

public final class VoterAlreadyVotedException extends ConflictException {
    public VoterAlreadyVotedException(UUID voterId, UUID electionId) {
        super(String.format("Voter %s has already voted in election %s", voterId, electionId));
    }
}
