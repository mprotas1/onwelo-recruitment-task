package com.protas.onwelo.voter.adapter.out.persistence;

import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;
import com.protas.onwelo.voter.port.out.VoterRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryVoterRepositoryAdapter implements VoterRepository {

    private final Map<UUID, Voter> voters = new HashMap<>();

    @Override
    public Voter save(Voter voter) {
        voters.put(voter.getId().value(), voter);
        return voter;
    }

    @Override
    public Optional<Voter> findById(VoterId id) {
        return Optional.ofNullable(voters.get(id.value()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return voters.values().stream()
                .anyMatch(v -> v.getEmail().equalsIgnoreCase(email));
    }
}
