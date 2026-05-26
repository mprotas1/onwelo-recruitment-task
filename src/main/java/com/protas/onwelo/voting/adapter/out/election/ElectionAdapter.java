package com.protas.onwelo.voting.adapter.out.election;

import com.protas.onwelo.election.ElectionFacade;
import com.protas.onwelo.voting.port.out.ElectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class ElectionAdapter implements ElectionPort {

    private final ElectionFacade electionFacade;

    @Override
    public boolean doesElectionExist(UUID electionId) {
        return electionFacade.doesElectionExist(electionId);
    }

    @Override
    public boolean isValidOption(UUID electionId, UUID optionId) {
        return electionFacade.isValidOption(electionId, optionId);
    }
}
