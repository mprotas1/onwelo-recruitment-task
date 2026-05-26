package com.protas.onwelo.voter.adapter.in.web;

import com.protas.onwelo.voter.adapter.in.web.dto.RegisterVoterRequest;
import com.protas.onwelo.voter.adapter.in.web.dto.VoterResponse;
import com.protas.onwelo.voter.port.in.BlockVoterUseCase;
import com.protas.onwelo.voter.port.in.RegisterVoterUseCase;
import com.protas.onwelo.voter.port.in.RegisterVoterUseCase.RegisterVoterCommand;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
class VoterController {

    private final RegisterVoterUseCase registerVoterUseCase;
    private final BlockVoterUseCase blockVoterUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VoterResponse register(@Valid @RequestBody RegisterVoterRequest request) {
        var voter = registerVoterUseCase.register(
                new RegisterVoterCommand(request.firstName(), request.lastName(), request.email())
        );
        return VoterResponse.from(voter);
    }

    @PatchMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void block(@PathVariable UUID id) {
        blockVoterUseCase.block(id);
    }

    @PatchMapping("/{id}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unblock(@PathVariable UUID id) {
        blockVoterUseCase.unblock(id);
    }
}
