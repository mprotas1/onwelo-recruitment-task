package com.protas.onwelo.voting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
public class Vote {

    private final VoteId id;
    private final UUID voterId;
    private final UUID electionId;
    private final UUID optionId;
    private final Instant castAt;

    public static Vote cast(UUID voterId, UUID electionId, UUID optionId, Instant castAt) {
        return new Vote(VoteId.generate(), voterId, electionId, optionId, castAt);
    }

    public static Vote reconstitute(VoteId id, UUID voterId, UUID electionId, UUID optionId, Instant castAt) {
        return new Vote(id, voterId, electionId, optionId, castAt);
    }
}
