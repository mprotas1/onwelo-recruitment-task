package com.protas.onwelo.election.adapter.in.web.mapper;

import com.protas.onwelo.election.adapter.in.web.dto.CreateElectionRequest;
import com.protas.onwelo.election.port.in.CreateElectionUseCase;

public final class ElectionMapper {

    public static CreateElectionUseCase.CreateElectionCommand commandOf(CreateElectionRequest request) {
        return new CreateElectionUseCase.CreateElectionCommand(
                request.name(),
                request.description(),
                request.options().stream()
                        .map(optionRequest -> new CreateElectionUseCase.CreateElectionCommand.OptionData(optionRequest.name(), optionRequest.description()))
                        .toList()
        );
    }
}
