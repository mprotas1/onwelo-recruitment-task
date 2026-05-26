package com.protas.onwelo.election.adapter.in.web.dto;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionOption;
import java.util.Collection;
import java.util.UUID;

public record ElectionResponse(UUID id, String name, String description, Collection<OptionResponse> options) {

    public record OptionResponse(UUID id, String name, String description) {
        static OptionResponse from(ElectionOption option) {
            return new OptionResponse(option.getId().value(), option.getName(), option.getDescription());
        }
    }

    public static ElectionResponse from(Election election) {
        return new ElectionResponse(
                election.getId().value(),
                election.getName(),
                election.getDescription(),
                election.getOptions().stream()
                        .map(OptionResponse::from)
                        .toList()
        );
    }
}
