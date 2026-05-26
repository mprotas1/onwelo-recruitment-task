package com.protas.onwelo.voting.adapter.out.persistence;

import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.port.out.VoteRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class InMemoryVoteRepositoryAdapter implements VoteRepository {

    private final Map<UUID, Vote> votes = new HashMap<>();

    @Override
    public Vote save(Vote vote) {
        votes.put(vote.getId().value(), vote);
        return vote;
    }

    @Override
    public boolean existsByVoterIdAndElectionId(UUID voterId, UUID electionId) {
        return votes.values().stream()
                .anyMatch(v -> v.getVoterId().equals(voterId) && v.getElectionId().equals(electionId));
    }
}
