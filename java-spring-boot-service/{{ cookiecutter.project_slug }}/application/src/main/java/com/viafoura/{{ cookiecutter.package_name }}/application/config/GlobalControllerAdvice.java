package com.viafoura.{{ cookiecutter.package_name }}.application.config;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import com.viafoura.{{ cookiecutter.package_name }}.application.model.responses.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

  /**
   * Handles uncaught exceptions in the applications and transforms to the {@code ErrorResponse}
   * object
   *
   * @param t Exception thrown by any process
   * @return {@code ErrorResponse} object with the error message
   */
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ErrorResponse> handleUncaughtException(Throwable t) {
    return buildErrorResponse(t, t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(WebExchangeBindException.class)
  @ResponseStatus(PRECONDITION_FAILED)
  public Mono<ResponseEntity<ErrorResponse>> invalidRequestErrorHandler(
      @NotNull final WebExchangeBindException e) {

    var errors =
        e.getBindingResult().getAllErrors().stream()
            .filter(Objects::nonNull)
            .map(this::getValidationErrorMessage)
            .collect(Collectors.toList());
    return buildErrorResponse(e, String.join(",", errors), PRECONDITION_FAILED)
        .map(errorResponse -> ResponseEntity.status(PRECONDITION_FAILED).body(errorResponse));
  }

  @NotNull
  private String getValidationErrorMessage(@NotNull final ObjectError error) {
    final var errorMessage = new StringBuilder();
    if (error instanceof FieldError) {
      var fe = (FieldError) error;
      errorMessage.append(fe.getField()).append(" - ");
    }
    errorMessage.append(error.getDefaultMessage());
    return errorMessage.toString();
  }

  /**
   * Builds the {@code ErrorResponse} object to serve all error request and response generic message
   *
   * @param e Exception thrown by the handler itself
   * @param message Message to be shown in the consumer request
   * @param httpStatus HTTP status to be sent it to the consumer
   * @return ErrorRepose
   */
  private Mono<ErrorResponse> buildErrorResponse(
      Throwable e, String message, HttpStatus httpStatus) {
    log.error(ExceptionUtils.getStackTrace(e));
    return Mono.just(new ErrorResponse(message, httpStatus.value()));
  }
}

