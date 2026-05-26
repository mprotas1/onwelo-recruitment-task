package com.protas.onwelo.voting.application.policy;

import com.protas.onwelo.voting.adapter.out.election.StubElectionPort;
import com.protas.onwelo.voting.adapter.out.persistence.InMemoryVoteRepositoryAdapter;
import com.protas.onwelo.voting.adapter.out.voter.StubVoterPort;
import com.protas.onwelo.voting.application.CompositeVotingPolicy;
import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.exception.ElectionDoesNotExistException;
import com.protas.onwelo.voting.exception.InvalidElectionOptionException;
import com.protas.onwelo.voting.exception.VoterAlreadyVotedException;
import com.protas.onwelo.voting.exception.VoterNotEligibleException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompositeVotingPolicyTest {

    private static final Instant NOW = Instant.now();

    private StubVoterPort voterPort;
    private StubElectionPort electionPort;
    private InMemoryVoteRepositoryAdapter voteRepository;
    private CompositeVotingPolicy policy;

    @BeforeEach
    void setUp() {
        voterPort = new StubVoterPort();
        electionPort = new StubElectionPort();
        voteRepository = new InMemoryVoteRepositoryAdapter();
        policy = VotingPolicyFactory.create(voterPort, electionPort, voteRepository);
    }

    @Test
    void shouldPassWhenAllRulesAreSatisfied() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, optionId);

        assertDoesNotThrow(() -> policy.validate(new CastVoteCommand(voterId, electionId, optionId)));
    }

    @Test
    void shouldThrowVoterNotEligibleExceptionWhenVoterIsBlocked() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        electionPort.registerElection(electionId, optionId);

        assertThrows(VoterNotEligibleException.class,
                () -> policy.validate(new CastVoteCommand(voterId, electionId, optionId)));
    }

    @Test
    void shouldThrowElectionNotFoundExceptionWhenElectionDoesNotExist() {
        UUID voterId = UUID.randomUUID();
        voterPort.registerEligible(voterId);

        assertThrows(ElectionDoesNotExistException.class,
                () -> policy.validate(new CastVoteCommand(voterId, UUID.randomUUID(), UUID.randomUUID())));
    }

    @Test
    void shouldThrowInvalidElectionOptionExceptionWhenOptionDoesNotBelongToElection() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, UUID.randomUUID());

        assertThrows(InvalidElectionOptionException.class,
                () -> policy.validate(new CastVoteCommand(voterId, electionId, UUID.randomUUID())));
    }

    @Test
    void shouldThrowVoterAlreadyVotedExceptionWhenVoterAlreadyVoted() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, optionId);
        voteRepository.save(Vote.cast(voterId, electionId, optionId, NOW));

        assertThrows(VoterAlreadyVotedException.class,
                () -> policy.validate(new CastVoteCommand(voterId, electionId, optionId)));
    }

    @Test
    void shouldCheckVoterEligibilityBeforeElectionExistence() {
        UUID voterId = UUID.randomUUID();

        assertThrows(VoterNotEligibleException.class,
                () -> policy.validate(new CastVoteCommand(voterId, UUID.randomUUID(), UUID.randomUUID())));
    }

    @Test
    void shouldCheckElectionExistenceBeforeOptionValidity() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);

        assertThrows(ElectionDoesNotExistException.class,
                () -> policy.validate(new CastVoteCommand(voterId, electionId, UUID.randomUUID())));
    }

    @Test
    void shouldCheckOptionValidityBeforeDuplicateVote() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, optionId);
        voteRepository.save(Vote.cast(voterId, electionId, UUID.randomUUID(), NOW));

        assertThrows(InvalidElectionOptionException.class,
                () -> policy.validate(new CastVoteCommand(voterId, electionId, UUID.randomUUID())));
    }
}
