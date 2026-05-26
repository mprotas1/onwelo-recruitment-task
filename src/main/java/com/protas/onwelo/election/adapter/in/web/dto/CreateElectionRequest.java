package com.protas.onwelo.election.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateElectionRequest(
        @NotBlank
        String name,
        String description,
        Set<@Valid CreateOptionRequest> options
) {

    public record CreateOptionRequest(
            @NotBlank
            String name,
            String description
    ) {}
}
