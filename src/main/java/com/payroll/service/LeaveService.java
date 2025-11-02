package com.payroll.service;


import com.payroll.dao.LeaveDao;
import com.payroll.model.LeaveRequest;


import java.time.LocalDate;
import java.util.List;


public class LeaveService {
    private LeaveDao dao = new LeaveDao();


    public int applyLeave(int empId, LocalDate from, LocalDate to, String reason) throws Exception {
        LeaveRequest lr = new LeaveRequest();
        lr.setEmpId(empId);
        lr.setFromDate(from);
        lr.setToDate(to);
        lr.setReason(reason);
        lr.setStatus("PENDING");
        return dao.create(lr);
    }


    public List<LeaveRequest> getPending() throws Exception {
        return dao.findPending();
    }


    public void respondLeave(int leaveId, boolean approve) throws Exception {
        dao.updateStatus(leaveId, approve ? "APPROVED" : "REJECTED");
    }


    public int countApprovedLeavesInYear(int empId, String monthYear) throws Exception {
        return dao.countLeaveDaysForMonth(empId, monthYear);
    }
}