package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compensation {
    private String compensationId;
    private Employee employee;
    private BigDecimal salary;
    private LocalDate effectiveDate;

    public Compensation(){
    }

    public String getCompensationId(){
        return compensationId;
    }

    public void setCompensationId(String compensationId){
        this.compensationId = compensationId;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public BigDecimal getSalary(){
        return salary;
    }

    public void setSalary(BigDecimal salary){
        this.salary = salary;
    }

    public LocalDate getEffectiveDate(){
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectivDate){
        this.effectiveDate = effectivDate;
    }
}
