package com.protas.onwelo.voting.port.out;

import com.protas.onwelo.voting.domain.Vote;

import java.util.UUID;

public interface VoteRepository {

    Vote save(Vote vote);

    boolean existsByVoterIdAndElectionId(UUID voterId, UUID electionId);
}
