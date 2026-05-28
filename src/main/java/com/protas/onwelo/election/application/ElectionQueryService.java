package com.protas.onwelo.election.application;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOptionId;
import com.protas.onwelo.election.exception.ElectionNotFoundException;
import com.protas.onwelo.election.port.in.ElectionValidationUseCase;
import com.protas.onwelo.election.port.in.GetElectionUseCase;
import com.protas.onwelo.election.port.out.ElectionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ElectionQueryService implements GetElectionUseCase, ElectionValidationUseCase {

    private final ElectionRepository electionRepository;

    @Override
    @Transactional(readOnly = true)
    public Election getElection(UUID id) {
        return electionRepository.findWithOptionsById(ElectionId.of(id))
                .orElseThrow(() -> new ElectionNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesElectionExist(ElectionId id) {
        return electionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidOption(ElectionId electionId, ElectionOptionId optionId) {
        return electionRepository.findWithOptionsById(electionId)
                .map(election -> election.hasOption(optionId))
                .orElse(false);
    }
}
