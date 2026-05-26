package com.protas.onwelo.election.port.in;

import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOptionId;

public interface ElectionValidationUseCase {
    boolean doesElectionExist(ElectionId id);
    boolean isValidOption(ElectionId electionId, ElectionOptionId optionId);
}
