package com.protas.onwelo.election.adapter.out.persistence;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOption;
import com.protas.onwelo.election.port.out.ElectionRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryElectionRepositoryAdapter implements ElectionRepository {

    private final Map<UUID, Election> elections = new HashMap<>();

    @Override
    public Election save(Election election) {
        elections.put(election.getId().value(), election);
        return election;
    }

    @Override
    public Optional<Election> findWithOptionsById(ElectionId id) {
        return Optional.ofNullable(elections.get(id.value()));
    }

    @Override
    public Optional<Election> findByName(String name) {
        return elections.values().stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean existsById(ElectionId id) {
        return elections.containsKey(id.value());
    }

    public void cleanUp() {
        elections.clear();
    }

    public void addOption(ElectionId electionId, ElectionOption option) {
        findWithOptionsById(electionId).ifPresent(election -> election.addOption(option));
    }
}
