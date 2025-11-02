package com.payroll.service;


import com.payroll.dao.EmployeeDao;
import com.payroll.model.Employee;
import com.payroll.util.PasswordUtil;


import java.math.BigDecimal;
import java.util.List;


public class EmployeeService {
    private EmployeeDao dao = new EmployeeDao();


    public int addEmployee(String name, String dept, BigDecimal basic, String username, String password) throws Exception {
        Employee e = new Employee();
        e.setName(name);
        e.setDepartment(dept);
        e.setBasicSalary(basic);
        e.setUsername(username);
        e.setPasswordHash(PasswordUtil.hashPassword(password));
        return dao.create(e);
    }


    public Employee find(int id) throws Exception {
        return dao.findById(id);
    }


    public List<Employee> listAll() throws Exception {
        return dao.findAll();
    }
}