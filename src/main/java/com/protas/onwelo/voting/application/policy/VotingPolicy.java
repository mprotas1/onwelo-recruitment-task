package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;

public interface VotingPolicy {

    void validate(CastVoteCommand command);
}
