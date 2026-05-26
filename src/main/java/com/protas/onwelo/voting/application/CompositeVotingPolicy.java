package com.protas.onwelo.voting.application;

import com.protas.onwelo.voting.application.policy.VotingPolicy;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompositeVotingPolicy {

    private final List<VotingPolicy> policies;

    public void validate(CastVoteCommand command) {
        policies.forEach(policy -> policy.validate(command));
    }
}
