package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.exception.VoterNotEligibleException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import com.protas.onwelo.voting.port.out.VoterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
class VoterEligibilityPolicy implements VotingPolicy {

    private final VoterPort voterPort;

    @Override
    public void validate(CastVoteCommand command) {
        if (!voterPort.isVoterEligible(command.voterId())) {
            throw new VoterNotEligibleException(command.voterId());
        }
    }
}
