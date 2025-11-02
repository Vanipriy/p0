package com.payroll;


import com.payroll.service.EmployeeService;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;


public class EmployeeServiceTest {
    @Test
    public void testAddAndFindEmployee() throws Exception {
        EmployeeService es = new EmployeeService();
        int id = es.addEmployee("TestUser", "QA", BigDecimal.valueOf(30000), "testuser", "testpass");
        assertTrue(id > 0);
        var e = es.find(id);
        assertEquals("TestUser", e.getName());
    }
}