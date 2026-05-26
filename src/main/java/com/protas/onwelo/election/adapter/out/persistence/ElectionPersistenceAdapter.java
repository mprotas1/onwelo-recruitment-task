package com.protas.onwelo.election.adapter.out.persistence;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.port.out.ElectionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ElectionPersistenceAdapter implements ElectionRepository {

    private final ElectionJpaRepository jpaRepository;

    @Override
    public Election save(Election election) {
        return jpaRepository.save(ElectionEntity.from(election)).toDomain();
    }

    @Override
    public Optional<Election> findById(ElectionId id) {
        return jpaRepository.findById(id.value()).map(ElectionEntity::toDomain);
    }

    @Override
    public Optional<Election> findByName(String name) {
        return jpaRepository.findByNameIgnoreCase(name)
                .map(ElectionEntity::toDomain);
    }

    @Override
    public boolean existsById(ElectionId id) {
        return jpaRepository.existsById(id.value());
    }
}
