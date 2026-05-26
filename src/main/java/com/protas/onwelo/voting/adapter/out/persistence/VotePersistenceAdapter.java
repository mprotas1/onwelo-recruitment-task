package com.protas.onwelo.voting.adapter.out.persistence;

import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.port.out.VoteRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class VotePersistenceAdapter implements VoteRepository {

    private final VoteJpaRepository jpaRepository;

    @Override
    public Vote save(Vote vote) {
        return jpaRepository.save(VoteEntity.from(vote)).toDomain();
    }

    @Override
    public boolean existsByVoterIdAndElectionId(UUID voterId, UUID electionId) {
        return jpaRepository.existsByVoterIdAndElectionId(voterId, electionId);
    }
}
