package com.protas.onwelo.election.application;

import com.protas.onwelo.election.adapter.out.persistence.InMemoryElectionRepositoryAdapter;
import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionOption;
import com.protas.onwelo.election.exception.DuplicateOptionForElectionException;
import com.protas.onwelo.election.exception.ElectionAlreadyExistsException;
import com.protas.onwelo.election.exception.ElectionNotFoundException;
import com.protas.onwelo.election.port.in.AddElectionOptionUseCase;
import com.protas.onwelo.election.port.in.AddElectionOptionUseCase.AddOptionCommand;
import com.protas.onwelo.election.port.in.CreateElectionUseCase;
import com.protas.onwelo.election.port.in.CreateElectionUseCase.CreateElectionCommand;
import com.protas.onwelo.election.port.in.CreateElectionUseCase.CreateElectionCommand.OptionData;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElectionServiceTest {

    private InMemoryElectionRepositoryAdapter electionRepository;
    private CreateElectionUseCase createElectionUseCase;
    private AddElectionOptionUseCase addElectionOptionUseCase;

    @BeforeEach
    void setUp() {
        electionRepository = new InMemoryElectionRepositoryAdapter();
        createElectionUseCase = new ElectionService(electionRepository);
        addElectionOptionUseCase = new ElectionService(electionRepository);
        electionRepository.cleanUp();
    }

    @Test
    void shouldCreateElectionWithoutOptions() {
        var command = new CreateElectionCommand("Presidential Election", "2024 election", List.of());

        Election election = createElectionUseCase.create(command);

        assertNotNull(election.getId());
        assertEquals("Presidential Election", election.getName());
        assertEquals("2024 election", election.getDescription());
        assertTrue(election.getOptions().isEmpty());
    }

    @Test
    void shouldCreateElectionWithInitialOptions() {
        var options = List.of(
                new OptionData("Candidate A", "First candidate"),
                new OptionData("Candidate B", "Second candidate")
        );
        var command = new CreateElectionCommand("Presidential Election", "desc", options);

        Election election = createElectionUseCase.create(command);

        assertEquals(2, election.getOptions().size());
    }

    @Test
    void shouldThrowWhenCreatingElectionWithDuplicateName() {
        var command = new CreateElectionCommand("Presidential Election", "desc", List.of());
        createElectionUseCase.create(command);

        assertThrows(ElectionAlreadyExistsException.class, () -> createElectionUseCase.create(command));
    }

    @Test
    void shouldThrowWhenCreatingElectionWithDuplicateNameCaseInsensitive() {
        createElectionUseCase.create(new CreateElectionCommand("Presidential Election", "desc", List.of()));

        assertThrows(ElectionAlreadyExistsException.class,
                () -> createElectionUseCase.create(new CreateElectionCommand("presidential election", "desc", List.of())));
    }

    @Test
    void shouldAddOptionToExistingElection() {
        Election election = createElectionUseCase.create(new CreateElectionCommand("Election", "desc", List.of()));
        var addCommand = new AddOptionCommand(election.getId().value(), "Option A", "desc");

        Election updated = addElectionOptionUseCase.addOption(addCommand);

        assertEquals(1, updated.getOptions().size());
    }

    @Test
    void shouldThrowWhenAddingAlreadyExistingOptionToElection() {
        String optionName = "Paweł Kozioł";
        String optionDescription = "The best president of Poland";
        Election election = electionRepository.save(Election.create(
                "Election",
                "desc",
                List.of(
                        ElectionOption.create(optionName, optionDescription)
                ))
        );
        assertThrows(
                RuntimeException.class,
                () -> addElectionOptionUseCase.addOption(new AddOptionCommand(election.getId().value(), optionName, optionDescription))
        );
    }

    @Test
    void shouldThrowWhenAddingOptionToNonExistentElection() {
        var command = new AddOptionCommand(UUID.randomUUID(), "Option A", "desc");

        assertThrows(ElectionNotFoundException.class, () -> addElectionOptionUseCase.addOption(command));
    }

    @Test
    void shouldThrowWhenAddingDuplicateOptionNameToElection() {
        Election election = createElectionUseCase.create(new CreateElectionCommand("Election", "desc", List.of()));
        var addCommand = new AddOptionCommand(election.getId().value(), "Option A", "desc");
        addElectionOptionUseCase.addOption(addCommand);

        assertThrows(DuplicateOptionForElectionException.class, () -> addElectionOptionUseCase.addOption(addCommand));
    }

    @Test
    void shouldThrowWhenAddingDuplicateOptionNameCaseInsensitive() {
        Election election = createElectionUseCase.create(new CreateElectionCommand("Election", "desc", List.of()));
        addElectionOptionUseCase.addOption(new AddOptionCommand(election.getId().value(), "Option A", "desc"));

        assertThrows(DuplicateOptionForElectionException.class,
                () -> addElectionOptionUseCase.addOption(new AddOptionCommand(election.getId().value(), "option a", "desc")));
    }
}
