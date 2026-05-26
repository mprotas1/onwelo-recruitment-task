package com.protas.onwelo.voter.port.in;

import com.protas.onwelo.voter.domain.VoterId;

public interface EligibilityUseCase {
    boolean isEligible(VoterId voterId);
}
