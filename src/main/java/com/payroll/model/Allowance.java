package com.payroll.model;


import java.math.BigDecimal;


public class Allowance {
    private int id;
    private int empId;
    private BigDecimal hra = BigDecimal.ZERO;
    private BigDecimal da = BigDecimal.ZERO;
    private BigDecimal travel = BigDecimal.ZERO;
    private BigDecimal medical = BigDecimal.ZERO;
    private BigDecimal washing = BigDecimal.ZERO;


    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public BigDecimal getHra() { return hra; }
    public void setHra(BigDecimal hra) { this.hra = hra; }
    public BigDecimal getDa() { return da; }
    public void setDa(BigDecimal da) { this.da = da; }
    public BigDecimal getTravel() { return travel; }
    public void setTravel(BigDecimal travel) { this.travel = travel; }
    public BigDecimal getMedical() { return medical; }
    public void setMedical(BigDecimal medical) { this.medical = medical; }
    public BigDecimal getWashing() { return washing; }
    public void setWashing(BigDecimal washing) { this.washing = washing; }
}