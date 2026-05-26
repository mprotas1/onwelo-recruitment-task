package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.exception.ElectionDoesNotExistException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import com.protas.onwelo.voting.port.out.ElectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
class ElectionExistsPolicy implements VotingPolicy {

    private final ElectionPort electionPort;

    @Override
    public void validate(CastVoteCommand command) {
        if (!electionPort.doesElectionExist(command.electionId())) {
            throw new ElectionDoesNotExistException(command.electionId());
        }
    }
}
