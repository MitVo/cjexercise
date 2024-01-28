package com.cj.exercise.entities;

public record Employee(Integer id, String name, Float monthlyAmount, Boolean active) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Employee employee)) {
            return false;
        }
        return employee.id.equals(this.id) && employee.name.equals(this.name);
    }
}
