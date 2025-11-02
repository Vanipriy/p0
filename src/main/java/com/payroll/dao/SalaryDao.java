package com.payroll.dao;

import com.payroll.model.SalaryRecord;
import com.payroll.util.DbUtil;

import java.sql.*;

public class SalaryDao {

    // ✅ Check if salary already generated
    public boolean exists(int empId, String monthYear) throws Exception {
        String sql = "SELECT COUNT(*) FROM salary WHERE emp_id=? AND month_year=?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    // ✅ Insert salary only if not exists
    public int create(SalaryRecord s) throws Exception {

        // 🚫 Prevent duplicate salary entry
        if (exists(s.getEmpId(), s.getMonthYear())) {
            System.out.println("❌ Salary already generated for this employee & month!\n");
            return -1;
        }

        String sql = "INSERT INTO salary(emp_id, month_year, gross_salary, deduction, net_salary) VALUES(?,?,?,?,?)";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getEmpId());
            ps.setString(2, s.getMonthYear());
            ps.setBigDecimal(3, s.getGrossSalary());
            ps.setBigDecimal(4, s.getDeduction());
            ps.setBigDecimal(5, s.getNetSalary());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }
}
