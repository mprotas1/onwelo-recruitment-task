package com.protas.onwelo.voter.adapter.in.web.dto;

import com.protas.onwelo.voter.domain.Voter;

import java.util.UUID;

public record VoterResponse(UUID id, String firstName, String lastName, String email, boolean isBlocked) {

    public static VoterResponse from(Voter voter) {
        return new VoterResponse(
                voter.getId().value(),
                voter.getFirstName(),
                voter.getLastName(),
                voter.getEmail(),
                voter.isBlocked()
        );
    }
}
