package com.protas.onwelo.election.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AddElectionOptionRequest(
        @NotBlank String name,
        String description
) {}
