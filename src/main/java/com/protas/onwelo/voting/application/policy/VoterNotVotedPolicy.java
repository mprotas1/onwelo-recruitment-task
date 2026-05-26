package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.exception.VoterAlreadyVotedException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import com.protas.onwelo.voting.port.out.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
class VoterNotVotedPolicy implements VotingPolicy {

    private final VoteRepository voteRepository;

    @Override
    public void validate(CastVoteCommand command) {
        if (voteRepository.existsByVoterIdAndElectionId(command.voterId(), command.electionId())) {
            throw new VoterAlreadyVotedException(command.voterId(), command.electionId());
        }
    }
}
