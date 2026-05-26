package com.protas.onwelo.election.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Election {

    private final ElectionId id;
    private final String name;
    private final String description;
    private final Set<ElectionOption> options;

    public static Election create(String name, String description, Collection<ElectionOption> options) {
        return new Election(ElectionId.generate(), name, description, new HashSet<>(options));
    }

    public static Election reconstitute(ElectionId id, String name, String description, Set<ElectionOption> options) {
        return new Election(id, name, description, options);
    }

    public void addOption(ElectionOption option) {
        this.options.add(option);
    }

    public Set<ElectionOption> getOptions() {
        return Collections.unmodifiableSet(options);
    }

    public boolean hasOption(ElectionOptionId optionId) {
        return options.stream().anyMatch(o -> o.getId().equals(optionId));
    }

    public boolean alreadyHasOption(String name) {
        Predicate<ElectionOption> predicate = o -> o.getName().equalsIgnoreCase(name);
        return options.stream().anyMatch(predicate);
    }
}
