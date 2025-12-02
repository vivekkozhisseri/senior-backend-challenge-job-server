package com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing;

import java.util.function.Function;

public interface MetricsPort {

  void inc(String metricName);

  void inc(String metricName, String tagKey, String tagValue);

  void record(String metricName, Double amount);

  <T, R> R timed(String metricName, T parameter, Function<T, R> function);

}