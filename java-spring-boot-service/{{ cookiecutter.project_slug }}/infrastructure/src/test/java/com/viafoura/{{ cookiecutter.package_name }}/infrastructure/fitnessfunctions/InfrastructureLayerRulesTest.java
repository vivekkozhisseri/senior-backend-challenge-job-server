package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.fitnessfunctions;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import javax.persistence.Entity;
import com.viafoura.{{ cookiecutter.package_name }}.core.common.constants.FitnessFunctionsConstants;
import org.springframework.stereotype.Repository;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN)
public class InfrastructureLayerRulesTest {

  static final String REPOSITORIES_PACKAGE = "..infrastructure.repositories..";
  static final String ENTITIES_PACKAGE = "..entities..";

  @ArchTest
  static final ArchRule repositories_must_have_repository_annotation =
      classes()
          .that()
          .resideInAPackage(REPOSITORIES_PACKAGE)
          .should()
          .beAnnotatedWith(Repository.class);

  @ArchTest
  static final ArchRule repositories_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage(REPOSITORIES_PACKAGE)
          .should()
          .haveSimpleNameEndingWith("Repository");

  @ArchTest
  static final ArchRule entities_must_reside_in_a_domain_package =
      classes()
          .that()
          .areAnnotatedWith(Entity.class)
          .should()
          .resideInAPackage(ENTITIES_PACKAGE)
          .as("Entities should reside in a package " + ENTITIES_PACKAGE);

  @ArchTest
  static final ArchRule entities_should_be_suffixed =
      classes()
          .that()
          .resideInAPackage(ENTITIES_PACKAGE)
          .should()
          .haveSimpleNameEndingWith("Entity");
}
