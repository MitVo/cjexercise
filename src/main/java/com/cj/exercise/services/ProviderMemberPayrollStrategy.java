package com.cj.exercise.services;


import java.util.Set;

public interface ProviderMemberPayrollStrategy<T> {

    public Set<T> getMembersByType(T t);
}
