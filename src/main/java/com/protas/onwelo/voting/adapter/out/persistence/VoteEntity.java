package com.protas.onwelo.voting.adapter.out.persistence;

import com.protas.onwelo.voting.domain.Vote;
import com.protas.onwelo.voting.domain.VoteId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class VoteEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID voterId;

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID electionId;

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID optionId;

    @Column(nullable = false)
    private Instant castAt;

    static VoteEntity from(Vote vote) {
        return new VoteEntity(vote.getId().value(), vote.getVoterId(), vote.getElectionId(), vote.getOptionId(), vote.getCastAt());
    }

    Vote toDomain() {
        return Vote.reconstitute(new VoteId(id), voterId, electionId, optionId, castAt);
    }
}
