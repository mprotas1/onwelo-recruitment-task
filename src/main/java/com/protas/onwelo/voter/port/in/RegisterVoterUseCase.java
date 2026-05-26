package com.protas.onwelo.voter.port.in;

import com.protas.onwelo.voter.domain.Voter;

public interface RegisterVoterUseCase {

    record RegisterVoterCommand(String firstName, String lastName, String email) {}

    Voter register(RegisterVoterCommand command);
}
