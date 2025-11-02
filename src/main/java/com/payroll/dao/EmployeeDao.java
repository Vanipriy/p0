package com.payroll.dao;

import com.payroll.model.Employee;
import com.payroll.util.DbUtil;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public int create(Employee e) throws Exception {
        String sql = "INSERT INTO employee(name, department, basic_salary, username, password_hash) VALUES(?,?,?,?,?)";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getDepartment());
            ps.setBigDecimal(3, e.getBasicSalary());
            ps.setString(4, e.getUsername());
            ps.setString(5, e.getPasswordHash());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            return -1;
        }
    }

    public Employee findById(int id) throws Exception {
        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setDepartment(rs.getString("department"));
                e.setBasicSalary(rs.getBigDecimal("basic_salary"));
                e.setUsername(rs.getString("username"));
                e.setPasswordHash(rs.getString("password_hash"));
                return e;
            }
            return null;
        }
    }

    public List<Employee> findAll() throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setDepartment(rs.getString("department"));
                e.setBasicSalary(rs.getBigDecimal("basic_salary"));
                e.setUsername(rs.getString("username"));
                e.setPasswordHash(rs.getString("password_hash"));
                list.add(e);
            }
        }
        return list;
    }
}
