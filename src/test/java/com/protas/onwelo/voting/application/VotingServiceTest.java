package com.protas.onwelo.voting.application;

import com.protas.onwelo.voting.adapter.out.election.StubElectionPort;
import com.protas.onwelo.voting.adapter.out.persistence.InMemoryVoteRepositoryAdapter;
import com.protas.onwelo.voting.adapter.out.voter.StubVoterPort;
import com.protas.onwelo.voting.application.policy.VotingPolicyFactory;
import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.exception.ElectionDoesNotExistException;
import com.protas.onwelo.voting.exception.InvalidElectionOptionException;
import com.protas.onwelo.voting.exception.VoterAlreadyVotedException;
import com.protas.onwelo.voting.exception.VoterNotEligibleException;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VotingServiceTest {

    private static final Instant NOW = Instant.now();

    private VotingService votingService;
    private InMemoryVoteRepositoryAdapter voteRepository;
    private StubVoterPort voterPort;
    private StubElectionPort electionPort;

    @BeforeEach
    void setUp() {
        voteRepository = new InMemoryVoteRepositoryAdapter();
        voterPort = new StubVoterPort();
        electionPort = new StubElectionPort();
        var votingPolicy = VotingPolicyFactory.create(voterPort, electionPort, voteRepository);
        var clock = Clock.systemUTC();
        votingService = new VotingService(voteRepository, votingPolicy, clock);
    }

    @Test
    void shouldCastVote() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, optionId);

        Vote vote = votingService.cast(new CastVoteCommand(voterId, electionId, optionId));

        assertNotNull(vote.getId());
        assertEquals(voterId, vote.getVoterId());
        assertEquals(electionId, vote.getElectionId());
        assertEquals(optionId, vote.getOptionId());
        assertNotNull(vote.getCastAt());
    }

    @Test
    void shouldThrowWhenVoterIsNotEligible() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        electionPort.registerElection(electionId, optionId);

        assertThrows(VoterNotEligibleException.class,
                () -> votingService.cast(new CastVoteCommand(voterId, electionId, optionId)));
    }

    @Test
    void shouldThrowWhenElectionDoesNotExist() {
        UUID voterId = UUID.randomUUID();
        voterPort.registerEligible(voterId);

        assertThrows(ElectionDoesNotExistException.class,
                () -> votingService.cast(new CastVoteCommand(voterId, UUID.randomUUID(), UUID.randomUUID())));
    }

    @Test
    void shouldThrowWhenOptionDoesNotBelongToElection() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, UUID.randomUUID());

        assertThrows(InvalidElectionOptionException.class,
                () -> votingService.cast(new CastVoteCommand(voterId, electionId, UUID.randomUUID())));
    }

    @Test
    void shouldThrowWhenVoterAlreadyVotedInElection() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId, optionId);
        voteRepository.save(Vote.cast(voterId, electionId, optionId, NOW));

        assertThrows(VoterAlreadyVotedException.class,
                () -> votingService.cast(new CastVoteCommand(voterId, electionId, optionId)));
    }

    @Test
    void shouldAllowSameVoterToVoteInDifferentElections() {
        UUID voterId = UUID.randomUUID();
        UUID electionId1 = UUID.randomUUID();
        UUID electionId2 = UUID.randomUUID();
        UUID optionId1 = UUID.randomUUID();
        UUID optionId2 = UUID.randomUUID();
        voterPort.registerEligible(voterId);
        electionPort.registerElection(electionId1, optionId1);
        electionPort.registerElection(electionId2, optionId2);
        voteRepository.save(Vote.cast(voterId, electionId1, optionId1, NOW));

        assertDoesNotThrow(() -> votingService.cast(new CastVoteCommand(voterId, electionId2, optionId2)));
    }

    @Test
    void shouldAllowDifferentVotersToCastVoteInSameElection() {
        UUID voterId1 = UUID.randomUUID();
        UUID voterId2 = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();
        voterPort.registerEligible(voterId1);
        voterPort.registerEligible(voterId2);
        electionPort.registerElection(electionId, optionId);
        voteRepository.save(Vote.cast(voterId1, electionId, optionId, NOW));

        assertDoesNotThrow(() -> votingService.cast(new CastVoteCommand(voterId2, electionId, optionId)));
    }
}
