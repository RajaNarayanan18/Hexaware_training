package com.insurance_ms.main;

import com.insurance_ms.util.DBConnUtil;
import com.insurance_ms.dao.InsuranceServiceImpl;
import com.insurance_ms.entity.Policy;
import com.insurance_ms.exception.PolicyNotFoundException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             Scanner scanner = new Scanner(System.in)) {

            // Table creation
            String userTable = "CREATE TABLE IF NOT EXISTS User (" +
                    "userId INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50)," +
                    "password VARCHAR(50)," +
                    "role VARCHAR(50))";

            String policyTable = "CREATE TABLE IF NOT EXISTS Policy (" +
                    "policyId INT PRIMARY KEY AUTO_INCREMENT," +
                    "policyName VARCHAR(100)," +
                    "coverageAmount DOUBLE," +
                    "premium DOUBLE)";

            String clientTable = "CREATE TABLE IF NOT EXISTS Client (" +
                    "clientId INT PRIMARY KEY AUTO_INCREMENT," +
                    "clientName VARCHAR(100)," +
                    "contactInfo VARCHAR(100)," +
                    "policyId INT," +
                    "FOREIGN KEY (policyId) REFERENCES Policy(policyId))";

            String claimTable = "CREATE TABLE IF NOT EXISTS Claim (" +
                    "claimId INT PRIMARY KEY AUTO_INCREMENT," +
                    "claimNumber VARCHAR(50)," +
                    "dateFiled DATE," +
                    "claimAmount DOUBLE," +
                    "status VARCHAR(50)," +
                    "policyId INT," +
                    "clientId INT," +
                    "FOREIGN KEY (policyId) REFERENCES Policy(policyId)," +
                    "FOREIGN KEY (clientId) REFERENCES Client(clientId))";

            String paymentTable = "CREATE TABLE IF NOT EXISTS Payment (" +
                    "paymentId INT PRIMARY KEY AUTO_INCREMENT," +
                    "paymentDate DATE," +
                    "paymentAmount DOUBLE," +
                    "clientId INT," +
                    "FOREIGN KEY (clientId) REFERENCES Client(clientId))";

            // Execute creation
            stmt.execute(userTable);
            stmt.execute(policyTable);
            stmt.execute(clientTable);
            stmt.execute(claimTable);
            stmt.execute(paymentTable);

            System.out.println("All tables created successfully!\n");

            InsuranceServiceImpl service = new InsuranceServiceImpl();
            
         // Menu
            while (true) {
                System.out.println("\nInsurance Management System");
                System.out.println("1. Create Policy");
                System.out.println("2. View Policy by ID");
                System.out.println("3. View All Policies");
                System.out.println("4. Update Policy");
                System.out.println("5. Delete Policy");
                System.out.println("6. Add Client");
                System.out.println("7. Add Claim");
                System.out.println("8. Add Payment");
                System.out.println("9. Add User");
                System.out.println("10. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter policy name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter coverage amount: ");
                        double coverage = scanner.nextDouble();
                        System.out.print("Enter premium: ");
                        double premium = scanner.nextDouble();
                        Policy policy = new Policy(0, name, coverage, premium); // auto-increment ID
                        if (service.createPolicy(policy)) {
                            System.out.println("Policy created successfully.");
                        } else {
                            System.out.println("Failed to create policy.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter policy ID to view: ");
                        int id = scanner.nextInt();
                        try {
                            Policy fetched = service.getPolicy(id);
                            System.out.println(fetched);
                        } catch (PolicyNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        List<Policy> allPolicies = service.getAllPolicies();
                        allPolicies.forEach(System.out::println);
                        break;

                    case 4:
                        System.out.print("Enter policy ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            Policy toUpdate = service.getPolicy(updateId);
                            System.out.print("Enter new coverage amount: ");
                            double newCoverage = scanner.nextDouble();
                            System.out.print("Enter new premium: ");
                            double newPremium = scanner.nextDouble();
                            toUpdate.setCoverageAmount(newCoverage);
                            toUpdate.setPremium(newPremium);
                            if (service.updatePolicy(toUpdate)) {
                                System.out.println("Policy updated successfully.");
                            } else {
                                System.out.println("Failed to update policy.");
                            }
                        } catch (PolicyNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.print("Enter policy ID to delete: ");
                        int deleteId = scanner.nextInt();
                        if (service.deletePolicy(deleteId)) {
                            System.out.println("Policy deleted successfully.");
                        } else {
                            System.out.println("Failed to delete policy.");
                        }
                        break;

                    case 6:
                        System.out.print("Enter client name: ");
                        String clientName = scanner.nextLine();
                        System.out.print("Enter contact info: ");
                        String contactInfo = scanner.nextLine();
                        System.out.print("Enter associated policy ID: ");
                        int clientPolicyId = scanner.nextInt();
                        System.out.println("Client added successfully ");
                        // TODO: Call service.addClient()
                        break;

                    case 7:
                        System.out.print("Enter claim number: ");
                        String claimNumber = scanner.nextLine();
                        System.out.print("Enter claim amount: ");
                        double claimAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter claim status: ");
                        String status = scanner.nextLine();
                        System.out.print("Enter associated policy ID: ");
                        int claimPolicyId = scanner.nextInt();
                        System.out.print("Enter associated client ID: ");
                        int claimClientId = scanner.nextInt();
                        System.out.println("Claim added successfully");
                        // TODO: Call service.addClaim()
                        break;

                    case 8:
                        System.out.print("Enter payment amount: ");
                        double paymentAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter client ID: ");
                        int payClientId = scanner.nextInt();
                        System.out.println("Payment added successfully ");
                        // TODO: Call service.addPayment()
                        break;

                    case 9:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter role (admin/user): ");
                        String role = scanner.nextLine();
                        System.out.println("User added successfully ");
                        // TODO: Call service.addUser()
                        break;

                    case 10:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }}}}


         
