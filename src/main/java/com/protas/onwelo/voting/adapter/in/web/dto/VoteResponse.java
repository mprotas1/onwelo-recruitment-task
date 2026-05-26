package com.protas.onwelo.voting.adapter.in.web.dto;

import com.protas.onwelo.voting.domain.Vote;

import java.time.Instant;
import java.util.UUID;

public record VoteResponse(UUID id, UUID voterId, UUID electionId, UUID optionId, Instant castAt) {

    public static VoteResponse from(Vote vote) {
        return new VoteResponse(
                vote.getId().value(),
                vote.getVoterId(),
                vote.getElectionId(),
                vote.getOptionId(),
                vote.getCastAt()
        );
    }
}
