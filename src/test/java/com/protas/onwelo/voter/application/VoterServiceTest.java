package com.protas.onwelo.voter.application;

import com.protas.onwelo.voter.adapter.out.persistence.InMemoryVoterRepositoryAdapter;
import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;
import com.protas.onwelo.voter.exception.VoterAlreadyExistsException;
import com.protas.onwelo.voter.exception.VoterNotFoundException;
import com.protas.onwelo.voter.port.in.RegisterVoterUseCase.RegisterVoterCommand;
import com.protas.onwelo.voter.port.out.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoterServiceTest {

    private VoterRepository voterRepository;
    private VoterService voterService;

    @BeforeEach
    void setUp() {
        voterRepository = new InMemoryVoterRepositoryAdapter();
        voterService = new VoterService(voterRepository);
    }

    @Test
    void shouldRegisterVoter() {
        var command = new RegisterVoterCommand("John", "Doe", "john@example.com");

        Voter voter = voterService.register(command);

        assertNotNull(voter.getId());
        assertEquals("John", voter.getFirstName());
        assertEquals("Doe", voter.getLastName());
        assertEquals("john@example.com", voter.getEmail());
    }

    @Test
    void shouldRegisterVoterAsEligibleByDefault() {
        var command = new RegisterVoterCommand("John", "Doe", "john@example.com");

        Voter voter = voterService.register(command);

        assertFalse(voter.isBlocked());
        assertTrue(voter.isEligible());
    }

    @Test
    void shouldThrowWhenRegisteringWithDuplicateEmail() {
        Voter existing = Voter.register("Jane", "Smith", "john@example.com");
        voterRepository.save(existing);

        var command = new RegisterVoterCommand("John", "Doe", "john@example.com");

        assertThrows(VoterAlreadyExistsException.class, () -> voterService.register(command));
    }

    @Test
    void shouldBlockVoter() {
        Voter voter = Voter.register("John", "Doe", "john@example.com");
        voterRepository.save(voter);

        voterService.block(voter.getId().value());

        Voter loaded = voterRepository.findById(voter.getId()).orElseThrow();
        assertTrue(loaded.isBlocked());
        assertFalse(loaded.isEligible());
    }

    @Test
    void shouldThrowWhenBlockingNonExistentVoter() {
        assertThrows(VoterNotFoundException.class, () -> voterService.block(UUID.randomUUID()));
    }

    @Test
    void shouldUnblockVoter() {
        Voter voter = Voter.register("John", "Doe", "john@example.com");
        voter.block();
        voterRepository.save(voter);

        voterService.unblock(voter.getId().value());

        Voter loaded = voterRepository.findById(voter.getId()).orElseThrow();
        assertFalse(loaded.isBlocked());
        assertTrue(loaded.isEligible());
    }

    @Test
    void shouldThrowWhenUnblockingNonExistentVoter() {
        assertThrows(VoterNotFoundException.class, () -> voterService.unblock(UUID.randomUUID()));
    }

    @Test
    void shouldReturnTrueWhenVoterIsEligible() {
        Voter voter = Voter.register("John", "Doe", "john@example.com");
        voterRepository.save(voter);

        assertTrue(voterService.isEligible(voter.getId()));
    }

    @Test
    void shouldReturnFalseWhenVoterIsBlocked() {
        Voter voter = Voter.register("John", "Doe", "john@example.com");
        voter.block();
        voterRepository.save(voter);

        assertFalse(voterService.isEligible(voter.getId()));
    }

    @Test
    void shouldReturnFalseForEligibilityWhenVoterDoesNotExist() {
        VoterId nonExistentId = new VoterId(UUID.randomUUID());
        assertFalse(voterService.isEligible(nonExistentId));
    }

    @Test
    void shouldRestoreEligibilityAfterUnblock() {
        Voter voter = Voter.register("John", "Doe", "john@example.com");
        voter.block();
        voterRepository.save(voter);

        voterService.unblock(voter.getId().value());

        assertTrue(voterService.isEligible(voter.getId()));
    }
}
