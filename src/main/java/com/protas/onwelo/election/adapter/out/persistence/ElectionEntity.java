package com.protas.onwelo.election.adapter.out.persistence;

import com.protas.onwelo.election.domain.Election;
import com.protas.onwelo.election.domain.ElectionId;
import com.protas.onwelo.election.domain.ElectionOption;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "elections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ElectionEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ElectionOptionEntity> options = new HashSet<>();

    static ElectionEntity from(Election election) {
        ElectionEntity entity = new ElectionEntity(
                election.getId().value(),
                election.getName(),
                election.getDescription(),
                new HashSet<>()
        );
        election.getOptions().forEach(entity::addOption);
        return entity;
    }

    Election toDomain() {
        return Election.reconstitute(
                ElectionId.of(id),
                name,
                description,
                options.stream()
                        .map(ElectionOptionEntity::toDomain)
                        .collect(Collectors.toSet())
        );
    }

    private void addOption(ElectionOption option) {
        options.add(ElectionOptionEntity.from(option, this));
    }
}
