package com.protas.onwelo.election;

import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOptionId;
import com.protas.onwelo.election.port.in.ElectionValidationUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElectionFacade {

    private final ElectionValidationUseCase electionValidation;

    public boolean doesElectionExist(UUID electionId) {
        return electionValidation.doesElectionExist(ElectionId.of(electionId));
    }

    public boolean isValidOption(UUID electionId, UUID optionId) {
        return electionValidation.isValidOption(
                ElectionId.of(electionId),
                ElectionOptionId.of(optionId)
        );
    }
}
