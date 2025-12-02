package com.viafoura.{{ cookiecutter.package_name }}.application.util.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing.MetricsPort;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Function;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PrometheusMetrics implements MetricsPort {

  final MeterRegistry meterRegistry;

  @Override
  public void inc(String metricName) {
    meterRegistry.counter(metricName).increment();
  }

  @Override
  public void inc(String metricName, String tagKey, String tagValue) {
    meterRegistry.counter(metricName, tagKey, tagValue).increment();
  }

  @Override
  public void record(String metricName, Double amount) {
    meterRegistry.summary(metricName).record(amount);
  }

  @Override
  public <T, R> R timed(String metricName, T parameter, Function<T, R> function) {
    var stopWatch = new StopWatch();
    stopWatch.start();
    var result = function.apply(parameter);
    stopWatch.stop();
    meterRegistry.timer(metricName).record(Duration.ofMillis(stopWatch.getTime()));
    return result;
  }
}
