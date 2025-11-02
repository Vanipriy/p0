package com.payroll.dao;


import com.payroll.model.Allowance;
import com.payroll.util.DbUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AllowanceDao {
    public void upsert(Allowance a) throws Exception {
        String check = "SELECT id FROM allowances WHERE emp_id = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(check)) {
            ps.setInt(1, a.getEmpId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String upd = "UPDATE allowances SET hra=?, da=?, travel=?, medical=?, washing=? WHERE emp_id=?";
                try (PreparedStatement ps2 = c.prepareStatement(upd)) {
                    ps2.setBigDecimal(1, a.getHra());
                    ps2.setBigDecimal(2, a.getDa());
                    ps2.setBigDecimal(3, a.getTravel());
                    ps2.setBigDecimal(4, a.getMedical());
                    ps2.setBigDecimal(5, a.getWashing());
                    ps2.setInt(6, a.getEmpId());
                    ps2.executeUpdate();
                }
            } else {
                String ins = "INSERT INTO allowances(emp_id, hra, da, travel, medical, washing) VALUES(?,?,?,?,?,?)";
                try (PreparedStatement ps2 = c.prepareStatement(ins)) {
                    ps2.setInt(1, a.getEmpId());
                    ps2.setBigDecimal(2, a.getHra());
                    ps2.setBigDecimal(3, a.getDa());
                    ps2.setBigDecimal(4, a.getTravel());
                    ps2.setBigDecimal(5, a.getMedical());
                    ps2.setBigDecimal(6, a.getWashing());
                    ps2.executeUpdate();
                }
            }
        }
    }


    public Allowance findByEmpId(int empId) throws Exception {
        String sql = "SELECT * FROM allowances WHERE emp_id = ?";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Allowance a = new Allowance();
                a.setId(rs.getInt("id"));
                a.setEmpId(rs.getInt("emp_id"));
                a.setHra(rs.getBigDecimal("hra"));
                a.setDa(rs.getBigDecimal("da"));
                a.setTravel(rs.getBigDecimal("travel"));
                a.setMedical(rs.getBigDecimal("medical"));
                a.setWashing(rs.getBigDecimal("washing"));
                return a;
            }
        }
        return null;
    }
}