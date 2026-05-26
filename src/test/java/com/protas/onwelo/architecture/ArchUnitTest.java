package com.protas.onwelo.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchUnitTest {

    private static final String ELECTION_MODULE = "com.protas.onwelo.election..";
    private static final String VOTING_MODULE = "com.protas.onwelo.voting..";
    private static final String VOTER_MODULE = "com.protas.onwelo.voter..";

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.protas.onwelo");
    }

    @Test
    void electionModuleShouldNotDependOnVotingModule() {
        noClasses()
                .that().resideInAPackage(ELECTION_MODULE)
                .should().dependOnClassesThat().resideInAPackage(VOTING_MODULE)
                .because("Election module must not know about voting module")
                .check(classes);
    }

    @Test
    void electionModuleShouldNotDependOnVoterModule() {
        noClasses()
                .that().resideInAPackage(ELECTION_MODULE)
                .should().dependOnClassesThat().resideInAPackage(VOTER_MODULE)
                .because("Election module must not know about voter module")
                .check(classes);
    }

    @Test
    void voterModuleShouldNotDependOnVotingModule() {
        noClasses()
                .that().resideInAPackage(VOTER_MODULE)
                .should().dependOnClassesThat().resideInAPackage(VOTING_MODULE)
                .because("Voter module must not know about voting module")
                .check(classes);
    }

    @Test
    void voterModuleShouldNotDependOnElectionModule() {
        noClasses()
                .that().resideInAPackage(VOTER_MODULE)
                .should().dependOnClassesThat().resideInAPackage(ELECTION_MODULE)
                .because("Voter module must not know about election module")
                .check(classes);
    }

    @Test
    void domainClassesShouldNotDependOnSpring() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                .because("Domain must be free of framework dependencies")
                .check(classes);
    }

    @Test
    void domainClassesShouldNotDependOnJpa() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("jakarta.persistence..")
                .because("Domain must be free of persistence dependencies")
                .check(classes);
    }

    @Test
    void portsShouldNotDependOnAdapters() {
        noClasses()
                .that().resideInAPackage("..port..")
                .should().dependOnClassesThat().resideInAPackage("..adapter..")
                .because("Ports must not depend on their adapters — dependency flows inward")
                .check(classes);
    }

    @Test
    void adaptersShouldNotDependOnOtherAdapters() {
        noClasses()
                .that().resideInAPackage("..adapter.in..")
                .should().dependOnClassesThat().resideInAPackage("..adapter.out..")
                .because("Inbound adapters must not depend on outbound adapters")
                .check(classes);
    }
}
