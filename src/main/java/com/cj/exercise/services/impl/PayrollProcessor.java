package com.cj.exercise.services.impl;

import com.cj.exercise.constants.CJMessages;
import com.cj.exercise.entities.Employee;
import com.cj.exercise.exceptions.CJExceptions;
import com.cj.exercise.services.ProviderMemberPayroll;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class PayrollProcessor<T> extends ProviderMemberPayroll<T> {


    /**
     * Get the total payments by the List of active employees with the correct data
     *
     * @return total amount
     */
    public Long getTotalAmountPayrollByEmployees(Set<Employee> employees) throws CJExceptions {
        long totalAmount = this.validatesEmployeesSet(employees).stream()
                .map(Employee::monthlyAmount)
                .mapToLong(Float::longValue).sum();

        if (Math.signum((float) totalAmount) >= 0) {
            return totalAmount;
        }
        throw new CJExceptions(CJMessages.MSG_NEGATIVE_TOTAL_AMOUNT);
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

        if (employees.stream().anyMatch(filterNotValidIds)) {
            throw new CJExceptions(CJMessages.MSG_NOT_VALID_EMPLOYEE_ID);
        }

        if (employees.stream().anyMatch(filterEmptyNames)) {
            throw new CJExceptions(CJMessages.MSG_NOT_VALID_EMPLOYEE_NAME);
        }

        return employees.stream()
                .filter(filterActiveEmployee).collect(Collectors.toSet());
    }

    @Override
    protected Set<T> getMembersByType(T t) {
        return null;
    }
}
