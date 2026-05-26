package com.protas.onwelo.election.application;

import com.protas.onwelo.election.adapter.out.persistence.InMemoryElectionRepositoryAdapter;
import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOption;
import com.protas.onwelo.election.domain.ElectionOptionId;
import com.protas.onwelo.election.port.in.ElectionValidationUseCase;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElectionQueryServiceTest {

    private InMemoryElectionRepositoryAdapter electionRepository;
    private ElectionValidationUseCase validationUseCase;

    @BeforeEach
    void setUp() {
        electionRepository = new InMemoryElectionRepositoryAdapter();
        validationUseCase = new ElectionQueryService(electionRepository);
        electionRepository.cleanUp();
    }

    @Test
    void shouldReturnTrueWhenElectionExists() {
        Election election = electionRepository.save(Election.create("Election", "desc", List.of()));
        assertTrue(validationUseCase.doesElectionExist(election.getId()));
    }

    @Test
    void shouldReturnFalseWhenElectionDoesNotExist() {
        ElectionId nonExistingElectionId = ElectionId.of(UUID.randomUUID());
        assertFalse(validationUseCase.doesElectionExist(nonExistingElectionId));
    }

    @Test
    void shouldReturnTrueWhenOptionBelongsToElection() {
        Election election = electionRepository.save(Election.create("Election", "desc", List.of()));
        electionRepository.addOption(election.getId(), ElectionOption.create("Option A", "desc"));

        ElectionOptionId optionId = electionRepository.findById(election.getId())
                .map(Election::getOptions)
                .orElseThrow(() -> new AssertionError("Could not match election"))
                .iterator().next().getId();

        assertTrue(validationUseCase.isValidOption(election.getId(), optionId));
    }

    @Test
    void shouldReturnFalseWhenOptionDoesNotBelongToElection() {
        Election election = electionRepository.save(Election.create("Election", "desc", List.of()));

        assertFalse(validationUseCase.isValidOption(election.getId(), ElectionOptionId.of(UUID.randomUUID())));
    }

    @Test
    void shouldReturnFalseWhenElectionDoesNotExistForOptionValidation() {
        assertFalse(validationUseCase.isValidOption(ElectionId.of(UUID.randomUUID()), ElectionOptionId.of(UUID.randomUUID())));
    }
}
