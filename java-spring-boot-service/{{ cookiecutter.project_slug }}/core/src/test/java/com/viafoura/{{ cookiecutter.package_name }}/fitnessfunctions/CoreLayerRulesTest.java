package com.viafoura.{{ cookiecutter.package_name }}.fitnessfunctions;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.concurrent.CompletableFuture;
import com.viafoura.{{ cookiecutter.package_name }}.core.common.constants.FitnessFunctionsConstants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN)
public class CoreLayerRulesTest {

  static final String INCOMING_PORT_PACKAGE = "..ports.incoming";

  @ArchTest
  static final ArchRule outgoing_ports_should_be_interfaces =
      classes().that().resideInAPackage("..ports.outgoing").should().beInterfaces();

  @ArchTest
  static final ArchRule incoming_ports_should_be_interfaces =
      classes().that().resideInAPackage(INCOMING_PORT_PACKAGE).should().beInterfaces();

  @ArchTest
  static final ArchRule services_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage("..services..")
          .should()
          .haveSimpleNameEndingWith("Service");

  @ArchTest
  static final ArchRule incoming_ports_should_return_flux_or_mono =
      methods()
          .that()
          .areDeclaredInClassesThat()
          .resideInAPackage(INCOMING_PORT_PACKAGE)
          .should()
          .haveRawReturnType(Flux.class)
          .orShould()
          .haveRawReturnType(Mono.class)
          .orShould()
          .haveRawReturnType(CompletableFuture.class);
}