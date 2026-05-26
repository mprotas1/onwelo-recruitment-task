package com.protas.onwelo.voting.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface VoteJpaRepository extends JpaRepository<VoteEntity, UUID> {

    boolean existsByVoterIdAndElectionId(UUID voterId, UUID electionId);
}
