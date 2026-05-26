package com.protas.onwelo.election.port.in;

import com.protas.onwelo.election.domain.Election;
import java.util.UUID;

public interface GetElectionUseCase {

    Election getElection(UUID id);
}
