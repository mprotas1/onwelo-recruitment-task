package com.protas.onwelo.voter.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface VoterJpaRepository extends JpaRepository<VoterEntity, UUID> {

    boolean existsByEmail(String email);
}
