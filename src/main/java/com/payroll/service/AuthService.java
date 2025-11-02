package com.payroll.service;


import com.payroll.dao.AdminDao;
import com.payroll.model.Admin;
import com.payroll.util.PasswordUtil;


public class AuthService {
    private AdminDao adminDao = new AdminDao();


    public boolean authenticateAdmin(String username, String password) throws Exception {
        Admin a = adminDao.findByUsername(username);
        if (a == null) return false;
        return PasswordUtil.verify(password, a.getPasswordHash());
    }


    public void createAdminIfNotExists(String username, String password) throws Exception {
        Admin a = adminDao.findByUsername(username);
        if (a == null) {
            adminDao.createAdmin(username, PasswordUtil.hashPassword(password));
        }
    }
}