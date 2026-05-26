package com.protas.onwelo.voting.port.out;

import java.util.UUID;

public interface VoterPort {

    boolean isVoterEligible(UUID voterId);
}
