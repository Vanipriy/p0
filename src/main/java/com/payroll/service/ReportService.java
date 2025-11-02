package com.payroll.service;


import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.payroll.util.DbUtil;


public class ReportService {
    public String exportMonthlySalary(String monthYear) throws Exception {
        String out = "reports/salary_" + monthYear + ".csv";
        try (Connection c = DbUtil.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT e.emp_id, e.name, s.gross_salary, s.deduction, s.net_salary FROM salary s JOIN employee e ON s.emp_id = e.emp_id WHERE s.month_year = ?")) {
            ps.setString(1, monthYear);
            try (ResultSet rs = ps.executeQuery(); CSVWriter writer = new CSVWriter(new FileWriter(out))) {
                String[] header = {"emp_id","name","gross_salary","deduction","net_salary"};
                writer.writeNext(header);
                while (rs.next()) {
                    String[] row = {
                            String.valueOf(rs.getInt(1)),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    };
                    writer.writeNext(row);
                }
            }
        }
        return out;
    }
}