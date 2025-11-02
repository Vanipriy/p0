package com.payroll.service;


import com.payroll.dao.AllowanceDao;
import com.payroll.dao.SalaryDao;
import com.payroll.model.Allowance;
import com.payroll.model.Employee;
import com.payroll.model.SalaryRecord;
import com.payroll.util.PdfUtil;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


public class PayrollService {
    private SalaryDao salaryDao = new SalaryDao();
    private AllowanceDao allowanceDao = new AllowanceDao();
    private LeaveService leaveService = new LeaveService();


    public int generateSalaryForEmployee(Employee e, String monthYear) throws Exception {
        Allowance a = allowanceDao.findByEmpId(e.getEmpId());
        if (a == null) a = new Allowance();


        BigDecimal basic = e.getBasicSalary();
        BigDecimal hra = Optional.ofNullable(a.getHra()).orElse(BigDecimal.ZERO);
        BigDecimal da = Optional.ofNullable(a.getDa()).orElse(BigDecimal.ZERO);
        BigDecimal travel = Optional.ofNullable(a.getTravel()).orElse(BigDecimal.ZERO);
        BigDecimal medical = Optional.ofNullable(a.getMedical()).orElse(BigDecimal.ZERO);
        BigDecimal washing = Optional.ofNullable(a.getWashing()).orElse(BigDecimal.ZERO);


        BigDecimal gross = basic.add(hra).add(da).add(travel).add(medical).add(washing);


// Simplified leave deduction: number of approved leave records in the year * basic/30
        int leaveCount = leaveService.countApprovedLeavesInYear(e.getEmpId(), monthYear);
        BigDecimal perDay = basic.divide(BigDecimal.valueOf(30), BigDecimal.ROUND_HALF_UP);
        BigDecimal deduction = perDay.multiply(BigDecimal.valueOf(leaveCount));


        BigDecimal net = gross.subtract(deduction);


        SalaryRecord s = new SalaryRecord();
        s.setEmpId(e.getEmpId());
        s.setMonthYear(monthYear);
        s.setGrossSalary(gross);
        s.setDeduction(deduction);
        s.setNetSalary(net);
        s.setGeneratedAt(LocalDateTime.now());


        int salaryId = salaryDao.create(s);


// Generate a simple PDF payslip (path example)
        String payslip = buildPayslipText(e, s);
        String path = "reports/payslip_" + e.getEmpId() + "_" + monthYear + ".pdf";
        PdfUtil.generatePaySlip(path, payslip);


        return salaryId;
    }


    private String buildPayslipText(Employee e, SalaryRecord s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Payslip for: ").append(e.getName()).append("\n");
        sb.append("Employee ID: ").append(e.getEmpId()).append("\n");
        sb.append("Month: ").append(s.getMonthYear()).append("\n\n");
        sb.append("Gross Salary: ").append(s.getGrossSalary()).append("\n");
        sb.append("Deductions: ").append(s.getDeduction()).append("\n");
        sb.append("Net Salary: ").append(s.getNetSalary()).append("\n");
        sb.append("Generated At: ").append(s.getGeneratedAt()).append("\n");
        return sb.toString();
    }
}