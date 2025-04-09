package com.insurance_ms.dao;

import com.insurance_ms.entity.Policy;
import com.insurance_ms.exception.PolicyNotFoundException;
import com.insurance_ms.util.DBConnUtil;

import java.sql.*;
import java.util.*;

public class InsuranceServiceImpl implements IPolicyService {

    private Connection connection;

    public InsuranceServiceImpl() {
        try {
            connection = DBConnUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createPolicy(Policy policy) {
        String sql = "INSERT INTO Policy (policyId, policyName, coverageAmount, premium) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, policy.getPolicyId());
            ps.setString(2, policy.getPolicyName());
            ps.setDouble(3, policy.getCoverageAmount());
            ps.setDouble(4, policy.getPremium());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        String sql = "SELECT * FROM Policy WHERE policyId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, policyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Policy(
                    rs.getInt("policyId"),
                    rs.getString("policyName"),
                    rs.getDouble("coverageAmount"),
                    rs.getDouble("premium")
                );
            } else {
                throw new PolicyNotFoundException("Policy ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error retrieving policy.");
        }
    }

    @Override
    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        String sql = "SELECT * FROM Policy";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                policies.add(new Policy(
                        rs.getInt("policyId"),
                        rs.getString("policyName"),
                        rs.getDouble("coverageAmount"),
                        rs.getDouble("premium")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        String sql = "UPDATE Policy SET coverageAmount=?, premium=? WHERE policyId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, policy.getCoverageAmount());
            ps.setDouble(2, policy.getPremium());
            ps.setInt(3, policy.getPolicyId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolicy(int policyId) {
        String sql = "DELETE FROM Policy WHERE policyId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, policyId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
