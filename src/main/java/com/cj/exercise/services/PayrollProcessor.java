package com.cj.exercise.services;

import com.cj.exercise.exceptions.CJExceptions;

public interface PayrollProcessor {

    public Long processPayrollEmployees() throws CJExceptions;
}
