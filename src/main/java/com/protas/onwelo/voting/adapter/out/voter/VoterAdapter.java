package com.protas.onwelo.voting.adapter.out.voter;

import com.protas.onwelo.voter.VoterFacade;
import com.protas.onwelo.voting.port.out.VoterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class VoterAdapter implements VoterPort {

    private final VoterFacade voterFacade;

    @Override
    public boolean isVoterEligible(UUID voterId) {
        return voterFacade.isVoterEligible(voterId);
    }
}
