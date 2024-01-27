package com.cj.exercise.strategies.impl;

import com.cj.exercise.constants.CJMessages;
import com.cj.exercise.entities.Employee;
import com.cj.exercise.exceptions.CJExceptions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayrollProcessorTest {

    PayrollProcessor<Employee> employeePayrollProcessor = new PayrollProcessor<>();

    @Test
    public void givenListEmployees_whenAmountIsNegative_thenReturnMessage() {

        assertThrows(
                CJExceptions.class,
                ()-> employeePayrollProcessor.getTotalAmountPayrollByEmployees(getSetEmployeeNegativeAmount()),
                CJMessages.MSG_NEGATIVE_TOTAL_AMOUNT);

    }

    @Test
    public void givenListEmployees_whenIdIsZero_thenEmployeeNotFound(){
        Employee employeeIdZero = new Employee(0,"Jhon Doe",100F,true);
        assertThrows(
                CJExceptions.class,
                ()-> employeePayrollProcessor.getTotalAmountPayrollByEmployees(Collections.singleton(employeeIdZero)),
                CJMessages.MSG_NOT_VALID_EMPLOYEE_ID);
    }

    @Test
    public void givenListEmployees_whenNameIsEmpty_thenReturnMessage() {
        Employee employeeEmptyName = new Employee(1,null,100F,true);
        assertThrows(
                CJExceptions.class,
                ()-> employeePayrollProcessor.getTotalAmountPayrollByEmployees(Collections.singleton(employeeEmptyName)),
                "Name of employee is null");
    }

    private Set<Employee> getSetEmployeeNegativeAmount(){
        Employee employee1 = new Employee(1,"Daniel Black",-100F,true);
        Employee employee2 = new Employee(2,"Rick Ness",-200F,true);

        Set<Employee> employeesSet = new HashSet<>();
        employeesSet.add(employee1);
        employeesSet.add(employee2);
        return employeesSet;
    }
}
