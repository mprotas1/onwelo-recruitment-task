package com.protas.onwelo.voting.application;

import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.port.in.CastVoteUseCase;
import com.protas.onwelo.voting.port.out.VoteRepository;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class VotingService implements CastVoteUseCase {

    private final VoteRepository voteRepository;
    private final CompositeVotingPolicy votingPolicy;
    private final Clock clock;

    @Override
    @Transactional
    public Vote cast(CastVoteCommand command) {
        log.info("Casting vote for voter: [{}], election: [{}], option: [{}]", command.voterId(), command.electionId(), command.optionId());
        votingPolicy.validate(command);
        Vote voteCast = Vote.cast(command.voterId(), command.electionId(), command.optionId(), clock.instant());
        return voteRepository.save(voteCast);
    }
}
