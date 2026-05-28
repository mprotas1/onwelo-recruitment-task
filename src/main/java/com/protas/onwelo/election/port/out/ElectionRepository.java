package com.protas.onwelo.election.port.out;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import java.util.Optional;

public interface ElectionRepository {

    Election save(Election election);

    Optional<Election> findWithOptionsById(ElectionId id);

    Optional<Election> findByName(String name);

    boolean existsById(ElectionId id);
}
