package com.protas.onwelo.election.adapter.out.persistence;

import com.protas.onwelo.election.domain.ElectionOption;
import com.protas.onwelo.election.domain.ElectionOptionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "election_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ElectionOptionEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private ElectionEntity election;

    static ElectionOptionEntity from(ElectionOption option, ElectionEntity election) {
        return new ElectionOptionEntity(option.getId().value(), option.getName(), option.getDescription(), election);
    }

    ElectionOption toDomain() {
        return ElectionOption.reconstitute(ElectionOptionId.of(id), name, description);
    }
}
