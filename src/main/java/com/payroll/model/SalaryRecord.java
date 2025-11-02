package com.payroll.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class SalaryRecord {
    private int salaryId;
    private int empId;
    private String monthYear; // e.g., "October-2025"
    private BigDecimal grossSalary;
    private BigDecimal deduction;
    private BigDecimal netSalary;
    private LocalDateTime generatedAt;


    // getters and setters
    public int getSalaryId() { return salaryId; }
    public void setSalaryId(int salaryId) { this.salaryId = salaryId; }
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public void setGrossSalary(BigDecimal grossSalary) { this.grossSalary = grossSalary; }
    public BigDecimal getDeduction() { return deduction; }
    public void setDeduction(BigDecimal deduction) { this.deduction = deduction; }
    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}