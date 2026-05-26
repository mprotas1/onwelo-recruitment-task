package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.exception.InvalidElectionOptionException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import com.protas.onwelo.voting.port.out.ElectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
class ValidOptionPolicy implements VotingPolicy {

    private final ElectionPort electionPort;

    @Override
    public void validate(CastVoteCommand command) {
        if (!electionPort.isValidOption(command.electionId(), command.optionId())) {
            throw new InvalidElectionOptionException(command.optionId(), command.electionId());
        }
    }
}
