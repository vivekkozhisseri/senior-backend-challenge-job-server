package com.viafoura.{{ cookiecutter.package_name }}.application.fitnessfunctions;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.viafoura.{{ cookiecutter.package_name }}.core.common.constants.FitnessFunctionsConstants;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN)
public class HexagonalArchitectureRulesTest {

  @ArchTest
  public static final ArchRule project_should_follow_ports_and_adapters_architecture =
  onionArchitecture()
    .domainModels("..core.domains..")
    .domainServices("..core.ports.incoming..", "..core.ports.outgoing..", "..core.services..")
    .applicationServices("..application..")
    .adapter("rest", "..application.controllers..")
    .adapter("persistence", "..infrastructure.adapters.persistence..",
          "..infrastructure.mappers..", "..infrastructure.entities..", "..infrastructure.repositories..");
}
