package com.protas.onwelo.voter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Voter {

    private final VoterId id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private boolean isBlocked;

    public static Voter register(String firstName, String lastName, String email) {
        return new Voter(VoterId.generate(), firstName, lastName, email, false);
    }

    public static Voter reconstitute(VoterId id, String firstName, String lastName, String email, boolean blocked) {
        return new Voter(id, firstName, lastName, email, blocked);
    }

    public void block() {
        this.isBlocked = true;
    }

    public void unblock() {
        this.isBlocked = false;
    }

    public boolean isEligible() {
        return !isBlocked;
    }
}
