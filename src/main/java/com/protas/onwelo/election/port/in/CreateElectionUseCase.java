package com.protas.onwelo.election.port.in;

import com.protas.onwelo.election.domain.Election;
import java.util.List;

public interface CreateElectionUseCase {

    record CreateElectionCommand(
            String name,
            String description,
            List<OptionData> options
    ) {
        public record OptionData(String name, String description) {}
    }

    Election create(CreateElectionCommand command);
}
