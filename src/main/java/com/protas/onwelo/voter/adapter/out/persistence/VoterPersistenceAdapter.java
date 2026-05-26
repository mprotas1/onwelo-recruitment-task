package com.protas.onwelo.voter.adapter.out.persistence;

import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;
import com.protas.onwelo.voter.port.out.VoterRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class VoterPersistenceAdapter implements VoterRepository {

    private final VoterJpaRepository jpaRepository;

    @Override
    public Voter save(Voter voter) {
        return jpaRepository.save(VoterEntity.from(voter)).toDomain();
    }

    @Override
    public Optional<Voter> findById(VoterId id) {
        return jpaRepository.findById(id.value()).map(VoterEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
