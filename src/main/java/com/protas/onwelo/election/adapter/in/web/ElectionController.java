package com.protas.onwelo.election.adapter.in.web;

import com.protas.onwelo.election.adapter.in.web.dto.AddElectionOptionRequest;
import com.protas.onwelo.election.adapter.in.web.dto.CreateElectionRequest;
import com.protas.onwelo.election.adapter.in.web.dto.ElectionResponse;
import com.protas.onwelo.election.adapter.in.web.mapper.ElectionMapper;
import com.protas.onwelo.election.port.in.AddElectionOptionUseCase;
import com.protas.onwelo.election.port.in.AddElectionOptionUseCase.AddOptionCommand;
import com.protas.onwelo.election.port.in.CreateElectionUseCase;
import com.protas.onwelo.election.port.in.GetElectionUseCase;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
class ElectionController {

    private final CreateElectionUseCase createElectionUseCase;
    private final AddElectionOptionUseCase addElectionOptionUseCase;
    private final GetElectionUseCase getElectionUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ElectionResponse create(@Valid @RequestBody CreateElectionRequest request) {
        var command = ElectionMapper.commandOf(request);
        var election = createElectionUseCase.create(command);
        return ElectionResponse.from(election);
    }

    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    ElectionResponse addOption(@PathVariable UUID id, @Valid @RequestBody AddElectionOptionRequest request) {
        var election = addElectionOptionUseCase.addOption(new AddOptionCommand(id, request.name(), request.description()));
        return ElectionResponse.from(election);
    }

    @GetMapping("/{id}")
    ElectionResponse getElection(@PathVariable UUID id) {
        var election = getElectionUseCase.getElection(id);
        return ElectionResponse.from(election);
    }
}
