package com.payroll.dao;


import com.payroll.model.Admin;
import com.payroll.util.DbUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AdminDao {
    public Admin findByUsername(String username) throws Exception {
        String sql = "SELECT admin_id, username, password_hash FROM admin WHERE username = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("admin_id"));
                a.setUsername(rs.getString("username"));
                a.setPasswordHash(rs.getString("password_hash"));
                return a;
            }
            return null;
        }
    }


    public void createAdmin(String username, String passwordHash) throws Exception {
        String sql = "INSERT INTO admin(username, password_hash) VALUES(?, ?)";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
        }
    }
}