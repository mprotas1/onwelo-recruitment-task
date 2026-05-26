package com.protas.onwelo.voting.adapter.out.voter;

import com.protas.onwelo.voting.port.out.VoterPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class StubVoterPort implements VoterPort {

    private final Set<UUID> eligibleVoters = new HashSet<>();

    public void registerEligible(UUID voterId) {
        eligibleVoters.add(voterId);
    }

    @Override
    public boolean isVoterEligible(UUID voterId) {
        return eligibleVoters.contains(voterId);
    }
}
