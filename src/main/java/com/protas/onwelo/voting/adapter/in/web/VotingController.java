package com.protas.onwelo.voting.adapter.in.web;

import com.protas.onwelo.voting.adapter.in.web.dto.CastVoteRequest;
import com.protas.onwelo.voting.adapter.in.web.dto.VoteResponse;
import com.protas.onwelo.voting.port.in.CastVoteUseCase;
import com.protas.onwelo.voting.port.in.CastVoteUseCase.CastVoteCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
class VotingController {

    private final CastVoteUseCase castVoteUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VoteResponse cast(@Valid @RequestBody CastVoteRequest request) {
        var vote = castVoteUseCase.cast(new CastVoteCommand(request.voterId(), request.electionId(), request.optionId()));
        return VoteResponse.from(vote);
    }
}
