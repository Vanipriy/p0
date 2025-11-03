package com.payroll.dao;

import com.payroll.model.LeaveRequest;
import com.payroll.util.DbUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveDao {
    public int create(LeaveRequest lr) throws Exception {
        String sql = "INSERT INTO leaves(emp_id, from_date, to_date, reason, status) VALUES(?,?,?,?,?)";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, lr.getEmpId());
            ps.setDate(2, java.sql.Date.valueOf(lr.getFromDate()));
            ps.setDate(3, java.sql.Date.valueOf(lr.getToDate()));
            ps.setString(4, lr.getReason());
            ps.setString(5, lr.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            return -1;
        }
    }

    public List<LeaveRequest> findPending() throws Exception {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leaves WHERE status = 'PENDING'";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeaveId(rs.getInt("leave_id"));
                lr.setEmpId(rs.getInt("emp_id"));
                lr.setFromDate(rs.getDate("from_date").toLocalDate());
                lr.setToDate(rs.getDate("to_date").toLocalDate());
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                list.add(lr);
            }
        }
        return list;
    }


    public void updateStatus(int leaveId, String status) throws Exception {
        String sql = "UPDATE leaves SET status = ? WHERE leave_id = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            ps.executeUpdate();
        }
    }


    public int countLeaveDaysForMonth(int empId, String monthYear) throws Exception {
        String[] parts = monthYear.split("-");
        String monthName = parts[0];
        String year = parts[1];
        String sql = "SELECT COUNT(*) as cnt FROM leaves WHERE emp_id = ? AND status = 'APPROVED' AND YEAR(from_date) = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setString(2, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("cnt");
        }
        return 0;
    }
}