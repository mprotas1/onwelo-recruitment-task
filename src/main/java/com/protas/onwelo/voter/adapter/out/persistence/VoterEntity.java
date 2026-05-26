package com.protas.onwelo.voter.adapter.out.persistence;

import com.protas.onwelo.voter.domain.Voter;
import com.protas.onwelo.voter.domain.VoterId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "voters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class VoterEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isBlocked;

    static VoterEntity from(Voter voter) {
        return new VoterEntity(
                voter.getId().value(),
                voter.getFirstName(),
                voter.getLastName(),
                voter.getEmail(),
                voter.isBlocked()
        );
    }

    Voter toDomain() {
        return Voter.reconstitute(VoterId.of(id), firstName, lastName, email, isBlocked);
    }
}
