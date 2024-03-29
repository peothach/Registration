package com.registration.ruleengine;

import com.registration.enums.UserType;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class RuleEngine {
  private RuleEngine() {
  }

  public static Map<UserType, Rule<UserType>> createRules(double salary) {
    EnumMap<UserType, Rule<UserType>> rules = new EnumMap<>(UserType.class);
    rules.put(UserType.SILVER, createRuleForUserSilver(salary));
    rules.put(UserType.GOLD, createRuleForUserGold(salary));
    rules.put(UserType.PLATINUM, createRuleForUserPlatinum(salary));
    return rules;
  }

  private static Rule<UserType> createRule(Supplier<Boolean> condition, Supplier<UserType> process) {
    return new Rule<>(condition, process);
  }

  private static Rule<UserType> createRuleForUserSilver(double salary) {
    return createRule(
        () -> salary >= 15000 && salary <= 30000,
        () -> UserType.SILVER);
  }

  private static Rule<UserType> createRuleForUserGold(double salary) {
    return createRule(
        () -> salary > 30000 && salary <= 50000,
        () -> UserType.GOLD);
  }

  private static Rule<UserType> createRuleForUserPlatinum(double salary) {
    return createRule(
        () -> salary > 50000,
        () -> UserType.PLATINUM);
  }
}
