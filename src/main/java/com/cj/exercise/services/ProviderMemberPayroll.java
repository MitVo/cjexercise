package com.cj.exercise.services;


import java.util.Set;

public abstract class ProviderMemberPayroll<T> {

    protected abstract Set<T> getMembersByType(T t);
}
