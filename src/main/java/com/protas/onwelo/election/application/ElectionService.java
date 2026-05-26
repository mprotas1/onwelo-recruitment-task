package com.protas.onwelo.election.application;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOption;
import com.protas.onwelo.election.exception.DuplicateOptionForElectionException;
import com.protas.onwelo.election.exception.ElectionAlreadyExistsException;
import com.protas.onwelo.election.exception.ElectionNotFoundException;
import com.protas.onwelo.election.port.in.AddElectionOptionUseCase;
import com.protas.onwelo.election.port.in.CreateElectionUseCase;
import com.protas.onwelo.election.port.out.ElectionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
class ElectionService implements CreateElectionUseCase, AddElectionOptionUseCase {

    private final ElectionRepository electionRepository;

    @Override
    public Election create(CreateElectionCommand command) {
        validateElectionExistence(command);
        log.info("Creating election: [{}, {}]", command.name(), command.description());
        var options = toDomainOptions(command);
        return electionRepository.save(Election.create(command.name(), command.description(), options));
    }

    @Override
    public Election addOption(AddOptionCommand command) {
        Election election = electionRepository.findById(ElectionId.of(command.electionId()))
                .orElseThrow(() -> new ElectionNotFoundException(command.electionId()));
        validateDuplicateCommand(command, election);
        ElectionOption option = ElectionOption.create(command.name(), command.description());
        election.addOption(option);
        log.info("Adding voting option: [{}] to election: [{}]", option.getName(), election.getName());
        return electionRepository.save(election);
    }

    private void validateElectionExistence(CreateElectionCommand command) {
        electionRepository.findByName(command.name()).ifPresent(e -> {
            log.error("Election with name already exists: [{}]", command.name());
            throw new ElectionAlreadyExistsException(command.name());
        });
    }

    private @NonNull List<ElectionOption> toDomainOptions(CreateElectionCommand command) {
        return command.options().stream()
                .map(option -> ElectionOption.create(option.name(), option.description()))
                .toList();
    }

    private void validateDuplicateCommand(AddOptionCommand command, Election election) {
        if(election.alreadyHasOption(command.name())) {
            throw new DuplicateOptionForElectionException(command.name());
        }
    }
}
