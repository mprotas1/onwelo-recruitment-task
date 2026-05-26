package com.protas.onwelo.voter.port.out;

import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;

import java.util.Optional;

public interface VoterRepository {

    Voter save(Voter voter);

    Optional<Voter> findById(VoterId id);

    boolean existsByEmail(String email);
}
