package com.viafoura.{{ cookiecutter.package_name }}.application.fitnessfunctions;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import com.viafoura.{{ cookiecutter.package_name }}.core.common.constants.FitnessFunctionsConstants;
import org.slf4j.Logger;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN)
public class CodingRulesTest {

  @ArchTest
  public static final ArchRule
      no_classes_should_access_standard_streams_or_throw_generic_exceptions =
          CompositeArchRule.of(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS)
              .and(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS);

  @ArchTest
  public static final ArchRule no_access_to_standard_streams =
      NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

  @ArchTest
  public static final ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  public static final ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

  @ArchTest public static final ArchRule no_joda_time = NO_CLASSES_SHOULD_USE_JODATIME;

  @ArchTest public static final ArchRule no_field_injection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

  @ArchTest
  public static final ArchRule loggers_should_be_private_static_final =
      fields()
          .that()
          .haveRawType(Logger.class)
          .should()
          .bePrivate()
          .andShould()
          .beStatic()
          .andShould()
          .beFinal()
          .allowEmptyShould(true)
          .because("we agreed on this convention");

  @ArchTest
  public static void no_access_to_standard_streams_as_method(JavaClasses classes) {
    noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
  }
}

