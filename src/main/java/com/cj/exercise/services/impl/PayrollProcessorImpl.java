package com.cj.exercise.services.impl;

import com.cj.exercise.constants.CJMessages;
import com.cj.exercise.entities.Employee;
import com.cj.exercise.exceptions.CJExceptions;
import com.cj.exercise.services.PayrollProcessor;
import com.cj.exercise.services.ProviderMemberPayroll;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class PayrollProcessorImpl implements PayrollProcessor {

    private final ProviderMemberPayroll providerMemberPayroll;

    public PayrollProcessorImpl(ProviderMemberPayroll providerMemberPayroll) {
        this.providerMemberPayroll =  providerMemberPayroll;
    }

    /**
     * Get the total payments by the List of active employees with the correct data
     * @return
     * @throws CJExceptions
     */
    @Override
    public Long processPayrollEmployees() throws CJExceptions {
        return this.getTotalAmountPayrollByEmployees(providerMemberPayroll.getEmployees());
    }

    private Long getTotalAmountPayrollByEmployees(Set<Employee> employees) throws CJExceptions {
        return this.validatesEmployeesSet(employees).stream()
                .map(Employee::monthlyAmount)
                .mapToLong(Float::longValue).sum();
    }

    /**
     * Validates the list of employees if they comply the requirements
     *
     * @param employees
     * @return a new Set of Employees filtered if they're active and have
     * a proper name
     * @throws CJExceptions
     */
    private Set<Employee> validatesEmployeesSet(Set<Employee> employees) throws CJExceptions {
        Predicate<Employee> filterActiveEmployee = Employee::active;
        Predicate<Employee> filterEmptyNames = t -> StringUtils.isEmpty(t.name());
        Predicate<Employee> filterNotValidIds = t -> t.id() <= 0;
        Predicate<Float> filterNegativeAmount = t -> Math.signum(t) <= 0;

        if (employees.stream().anyMatch(filterNotValidIds)) {
            throw new CJExceptions(CJMessages.MSG_NOT_VALID_EMPLOYEE_ID);
        }

        if (employees.stream().anyMatch(filterEmptyNames)) {
            throw new CJExceptions(CJMessages.MSG_NOT_VALID_EMPLOYEE_NAME);
        }

        if (employees.stream().map(Employee::monthlyAmount).anyMatch(filterNegativeAmount)) {
            throw new CJExceptions(CJMessages.MSG_NEGATIVE_TOTAL_AMOUNT);
        }

        return employees.stream()
                .filter(filterActiveEmployee).collect(Collectors.toSet());
    }

}
