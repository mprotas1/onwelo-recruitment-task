package com.protas.onwelo.election.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface ElectionJpaRepository extends JpaRepository<ElectionEntity, UUID> {

    Optional<ElectionEntity> findByNameIgnoreCase(String name);
}
