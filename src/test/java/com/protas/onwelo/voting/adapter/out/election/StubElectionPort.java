package com.protas.onwelo.voting.adapter.out.election;

import com.protas.onwelo.voting.port.out.ElectionPort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class StubElectionPort implements ElectionPort {

    private final Map<UUID, Set<UUID>> electionOptions = new HashMap<>();

    public void registerElection(UUID electionId, UUID... optionIds) {
        electionOptions.put(electionId, new HashSet<>(Arrays.asList(optionIds)));
    }

    @Override
    public boolean doesElectionExist(UUID electionId) {
        return electionOptions.containsKey(electionId);
    }

    @Override
    public boolean isValidOption(UUID electionId, UUID optionId) {
        return electionOptions.getOrDefault(electionId, Set.of()).contains(optionId);
    }
}
