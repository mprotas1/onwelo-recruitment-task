package com.protas.onwelo.voter.application;

import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;
import com.protas.onwelo.voter.exception.VoterAlreadyExistsException;
import com.protas.onwelo.voter.exception.VoterNotFoundException;
import com.protas.onwelo.voter.port.in.BlockVoterUseCase;
import com.protas.onwelo.voter.port.in.EligibilityUseCase;
import com.protas.onwelo.voter.port.in.RegisterVoterUseCase;
import com.protas.onwelo.voter.port.out.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
class VoterService implements RegisterVoterUseCase, BlockVoterUseCase, EligibilityUseCase {

    private final VoterRepository voterRepository;

    @Override
    public Voter register(RegisterVoterCommand command) {
        if (voterRepository.existsByEmail(command.email())) {
            throw new VoterAlreadyExistsException(command.email());
        }
        var voter = Voter.register(command.firstName(), command.lastName(), command.email());
        return voterRepository.save(voter);
    }

    @Override
    public void block(UUID voterId) {
        var voter = voterRepository.findById(VoterId.of(voterId))
                .orElseThrow(() -> new VoterNotFoundException(voterId));
        voter.block();
        voterRepository.save(voter);
    }

    @Override
    public void unblock(UUID voterId) {
        var voter = voterRepository.findById(VoterId.of(voterId))
                .orElseThrow(() -> new VoterNotFoundException(voterId));
        voter.unblock();
        voterRepository.save(voter);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEligible(VoterId voterId) {
        return voterRepository.findById(voterId)
                .map(Voter::isEligible)
                .orElse(false);
    }
}
