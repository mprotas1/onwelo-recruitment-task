package com.protas.onwelo.voter;

import com.protas.onwelo.voter.domain.VoterId;
import com.protas.onwelo.voter.port.in.EligibilityUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoterFacade {

    private final EligibilityUseCase eligibilityUseCase;

    public boolean isVoterEligible(UUID voterId) {
        return eligibilityUseCase.isEligible(VoterId.of(voterId));
    }
}
