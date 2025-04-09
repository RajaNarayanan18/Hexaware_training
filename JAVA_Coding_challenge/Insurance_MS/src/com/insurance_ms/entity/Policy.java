package com.insurance_ms.entity;

public class Policy {
    private int policyId;
    private String policyName;
    private double coverageAmount;
    private double premium;

    // Constructors
    public Policy() {}

    public Policy(int policyId, String policyName, double coverageAmount, double premium) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
    }

    public Policy(String policyName, double coverageAmount, double premium) {
        this.policyName = policyName;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
    }

    // Getters and Setters
    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    // toString
    @Override
    public String toString() {
        return "Policy [policyId=" + policyId + ", policyName=" + policyName + ", coverageAmount=" + coverageAmount + ", premium=" + premium + "]";
    }
}
