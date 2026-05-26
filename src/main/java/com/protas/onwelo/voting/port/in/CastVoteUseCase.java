package com.protas.onwelo.voting.port.in;

import com.protas.onwelo.voting.domain.Vote;

import java.util.UUID;

public interface CastVoteUseCase {

    record CastVoteCommand(UUID voterId, UUID electionId, UUID optionId) {}

    Vote cast(CastVoteCommand command);
}
