package com.cj.exercise.strategies;


import java.util.Set;

public interface ProviderMemberPayrollStrategy<T> {

    Set<T> getMembersByType (T t);
}
