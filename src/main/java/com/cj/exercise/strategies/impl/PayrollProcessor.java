package com.cj.exercise.strategies.impl;

import com.cj.exercise.constants.CJMessages;
import com.cj.exercise.entities.Employee;
import com.cj.exercise.exceptions.CJExceptions;
import com.cj.exercise.strategies.ProviderMemberPayrollStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.function.Predicate;

@Slf4j
public class PayrollProcessor<T> implements ProviderMemberPayrollStrategy<T> {
    @Override
    public Set<T> getMembersByType(T t) {
        //TODO logic to get the employees
        return null;
    }

    /**
     * Get the total payments by the List of active employees with the correct data
     * @return total amount
     */
    public Long getTotalAmountPayrollByEmployees(Set<Employee> employees) throws CJExceptions {
        try {
            Predicate<Employee> filterActiveEmployee = Employee::active;
            Predicate<Employee> filterEmptyNames = t -> !t.name().isEmpty();
            Predicate<Employee> filterNotValidIds = t -> t.id() <= 0;

            if(employees.stream().anyMatch(filterNotValidIds)){
                throw new CJExceptions(CJMessages.MSG_NOT_VALID_EMPLOYEE_ID);
            }

            long totalAmount = employees.stream()
                    .filter(filterActiveEmployee.and(filterEmptyNames))
                    .map(Employee::monthlyAmount)
                    .mapToLong(Float::longValue).sum();

            if (Math.signum((float) totalAmount) >= 0){
                return totalAmount;
            }

            throw new CJExceptions(CJMessages.MSG_NEGATIVE_TOTAL_AMOUNT);
        } catch (Exception e){
            log.error("An error occur while processing the payroll:", e);
            throw new CJExceptions("Error at getTotalAmountPayrollByEmployees: " + e.getMessage());
        }
    }

}
