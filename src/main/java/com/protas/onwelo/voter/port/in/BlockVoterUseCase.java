package com.protas.onwelo.voter.port.in;

import java.util.UUID;

public interface BlockVoterUseCase {

    void block(UUID voterId);

    void unblock(UUID voterId);
}
