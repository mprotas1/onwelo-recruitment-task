package com.protas.onwelo.voting.port.out;

import java.util.UUID;

public interface ElectionPort {

    boolean doesElectionExist(UUID electionId);

    boolean isValidOption(UUID electionId, UUID optionId);
}
