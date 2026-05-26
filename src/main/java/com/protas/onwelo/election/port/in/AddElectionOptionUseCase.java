package com.protas.onwelo.election.port.in;

import com.protas.onwelo.election.domain.Election;

import java.util.UUID;

public interface AddElectionOptionUseCase {

    record AddOptionCommand(UUID electionId, String name, String description) {}

    Election addOption(AddOptionCommand command);
}
