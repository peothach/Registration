package com.registration.ruleengine;

import java.util.function.Supplier;

public class Rule<T> {
  private Supplier<Boolean> condition = null;
  private Supplier<T> process = null;

  public Rule() {
  }

  public Rule(Supplier<Boolean> condition, Supplier<T> process) {
    this.condition = condition;
    this.process = process;
  }

  public Boolean isApplicable() {
    return condition.get();
  }

  public T applyProcess() {
    return process.get();
  }
}
