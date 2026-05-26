CREATE TABLE voters
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    is_blocked BOOLEAN      NOT NULL,
    CONSTRAINT pk_voters PRIMARY KEY (id),
    CONSTRAINT uq_voters_email UNIQUE (email)
);

CREATE TABLE elections
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_elections PRIMARY KEY (id),
    CONSTRAINT uq_elections_name UNIQUE (name)
);

CREATE TABLE election_options
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    election_id UUID         NOT NULL,
    CONSTRAINT pk_election_options PRIMARY KEY (id),
    CONSTRAINT uq_election_options_name_election UNIQUE (name, election_id),
    CONSTRAINT fk_election_options_election FOREIGN KEY (election_id) REFERENCES elections (id)
);

CREATE TABLE votes
(
    id          UUID                     NOT NULL,
    voter_id    UUID                     NOT NULL,
    election_id UUID                     NOT NULL,
    option_id   UUID                     NOT NULL,
    cast_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_votes PRIMARY KEY (id),
    CONSTRAINT uq_votes_voter_election UNIQUE (voter_id, election_id)
);
