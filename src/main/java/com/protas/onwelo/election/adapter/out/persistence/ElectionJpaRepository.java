package com.protas.onwelo.election.adapter.out.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface ElectionJpaRepository extends JpaRepository<ElectionEntity, UUID> {

    @EntityGraph(attributePaths = "options")
    Optional<ElectionEntity> findById(UUID id);

    Optional<ElectionEntity> findByNameIgnoreCase(String name);
}
