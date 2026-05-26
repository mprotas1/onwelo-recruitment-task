package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.application.CompositeVotingPolicy;
import com.protas.onwelo.voting.port.out.ElectionPort;
import com.protas.onwelo.voting.port.out.VoteRepository;
import com.protas.onwelo.voting.port.out.VoterPort;

import java.util.List;

public final class VotingPolicyFactory {

    public static CompositeVotingPolicy create(VoterPort voterPort, ElectionPort electionPort, VoteRepository voteRepository) {
        return new CompositeVotingPolicy(List.of(
                new VoterEligibilityPolicy(voterPort),
                new ElectionExistsPolicy(electionPort),
                new ValidOptionPolicy(electionPort),
                new VoterNotVotedPolicy(voteRepository)
        ));
    }
}
