package com.payroll;


import com.payroll.service.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static AuthService auth = new AuthService();
    private static EmployeeService empService = new EmployeeService();
    private static LeaveService leaveService = new LeaveService();
    private static PayrollService payrollService = new PayrollService();
    private static ReportService reportService = new ReportService();

    public static void main(String[] args) throws Exception {
// Ensure default admin
        auth.createAdminIfNotExists("admin", "admin123");


        Scanner sc = new Scanner(System.in);
        System.out.println("=== Payroll Backend CLI ===");


// Simple admin login
        System.out.print("Enter admin username: ");
        String user = sc.nextLine();
        System.out.print("Enter admin password: ");
        String pass = sc.nextLine();


        if (!auth.authenticateAdmin(user, pass)) {
            System.out.println("Authentication failed. Exiting.");
            return;
        }


        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Employee\n2. List Employees\n3. Apply Leave (as Employee)\n4. Approve Leave\n5. Set Allowances\n6. Generate Salary\n7. Export Monthly Salary CSV\n8. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Basic Salary: ");
                    BigDecimal basic = new BigDecimal(sc.nextLine());
                    System.out.print("Username: ");
                    String uname = sc.nextLine();
                    System.out.print("Password: ");
                    String pwd = sc.nextLine();
                    int id = empService.addEmployee(name, dept, basic, uname, pwd);
                    System.out.println("Employee added with ID: " + id);
                    break;
                case 2:
                    List<com.payroll.model.Employee> list = empService.listAll();
                    System.out.println("-- Employees --");
                    for (var e : list) {
                        System.out.println(e.getEmpId() + " | " + e.getName() + " | " + e.getDepartment() + " | " + e.getBasicSalary());
                    }
                    break;
                case 3:
                    System.out.print("Employee ID: ");
                    int empId = Integer.parseInt(sc.nextLine());
                    System.out.print("From (yyyy-mm-dd): ");
                    LocalDate from = LocalDate.parse(sc.nextLine());
                    System.out.print("To (yyyy-mm-dd): ");
                    LocalDate to = LocalDate.parse(sc.nextLine());
                    System.out.print("Reason: ");
                    String reason = sc.nextLine();
                    int leaveId = leaveService.applyLeave(empId, from, to, reason);
                    System.out.println("Leave applied. ID: " + leaveId);
                    break;
                case 4:
                    var pending = leaveService.getPending();
                    System.out.println("Pending Leaves:");
                    for (var p : pending) {
                        System.out.println(p.getLeaveId() + " | Emp: " + p.getEmpId() + " | " + p.getFromDate() + " to " + p.getToDate() + " | " + p.getReason());
                    }
                    System.out.print("Enter Leave ID to approve: ");
                    int lid = Integer.parseInt(sc.nextLine());
                    leaveService.respondLeave(lid, true);
                    System.out.println("Leave approved.");
                    break;
                case 5:
                    System.out.print("Employee ID: ");
                    int ae = Integer.parseInt(sc.nextLine());
                    System.out.print("HRA: ");
                    BigDecimal hra = new BigDecimal(sc.nextLine());
                    System.out.print("DA: ");
                    BigDecimal da = new BigDecimal(sc.nextLine());
                    System.out.print("Travel: ");
                    BigDecimal travel = new BigDecimal(sc.nextLine());
                    System.out.print("Medical: ");
                    BigDecimal med = new BigDecimal(sc.nextLine());
                    System.out.print("Washing: ");
                    BigDecimal wash = new BigDecimal(sc.nextLine());
                    com.payroll.model.Allowance al = new com.payroll.model.Allowance();
                    al.setEmpId(ae); al.setHra(hra); al.setDa(da); al.setTravel(travel); al.setMedical(med); al.setWashing(wash);
                    new com.payroll.dao.AllowanceDao().upsert(al);
                    System.out.println("Allowances set.");
                    break;
                case 6:
                    System.out.print("Employee ID: ");
                    int ge = Integer.parseInt(sc.nextLine());
                    System.out.print("Month-Year (e.g., October-2025): ");
                    String my = sc.nextLine();
                    var emp = empService.find(ge);
                    if (emp == null) { System.out.println("Employee not found"); break; }
                    int sid = payrollService.generateSalaryForEmployee(emp, my);
                    System.out.println("Salary generated, record id: " + sid);
                    break;
                case 7:
                    System.out.print("Month-Year (e.g., October-2025): ");
                    String my2 = sc.nextLine();
                    String path = reportService.exportMonthlySalary(my2);
                    System.out.println("Report exported to: " + path);
                    break;
                case 8:
                    running = false; break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
        System.out.println("Exiting...");
    }
}