package com.viafoura.{{ cookiecutter.package_name }}.application.fitnessfunctions;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.viafoura.{{ cookiecutter.package_name }}.core.common.constants.FitnessFunctionsConstants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN)
public class ApplicationLayerRulesTest {

  static final String CONTROLLERS_PACKAGE = "..controllers..";
  static final String MAPPERS_PACKAGE = "..model.mappers..";

  @ArchTest
  static final ArchRule requests_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage("..model.requests..")
          .should()
          .haveSimpleNameEndingWith("Request");

  @ArchTest
  static final ArchRule responses_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage("..model.responses..")
          .should()
          .haveSimpleNameEndingWith("Response");

  @ArchTest
  static final ArchRule all_controller_should_reside_in_controller_package =
      classes()
          .that()
          .areAnnotatedWith(RestController.class)
          .should()
          .resideInAPackage(CONTROLLERS_PACKAGE);

  @ArchTest
  static final ArchRule controllers_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage(CONTROLLERS_PACKAGE)
          .should()
          .haveSimpleNameEndingWith("Controller");

  @ArchTest
  static final ArchRule all_controller_methods_should_return_mono_or_flux =
      methods()
          .that()
          .areAnnotatedWith(GetMapping.class)
          .and()
          .areAnnotatedWith(PostMapping.class)
          .and()
          .areAnnotatedWith(PutMapping.class)
          .and()
          .areAnnotatedWith(DeleteMapping.class)
          .should()
          .haveRawReturnType(Mono.class)
          .orShould()
          .haveRawReturnType(Flux.class)
          .allowEmptyShould(true);
}

